package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ShadowDrawable extends Drawable {

    private final Context context;
    private ShadowAttribute shadowAttribute;
    private float cornerRadiusRatio = 0.5f;
    private final int padding;
    private Rect bounds = new Rect();
    private Bitmap shadowBitmap;
    private Path boundsPath;
    private Paint boundsPathPaint;


    public ShadowDrawable(Context context, ShadowAttribute shadowAttribute) {
        this.context = context;
        this.shadowAttribute = shadowAttribute;
        Point offset = shadowAttribute.getOffset();
        this.padding = (int) ((shadowAttribute.getRadius() + Math.max(offset.x, offset.y))/shadowAttribute.getBitmapScale());
    }

    public ShadowDrawable(Context context, ShadowAttribute shadowAttribute, int backgroundColor) {
        this(context, shadowAttribute);
        boundsPathPaint = new Paint(ANTI_ALIAS_FLAG);
        boundsPathPaint.setStyle(Paint.Style.FILL);
        boundsPathPaint.setColor(backgroundColor);
    }

    public ShadowDrawable(Context context, ShadowAttribute shadowAttribute, int backgroundColor, float cornerRadiusRatio) {
        this(context, shadowAttribute, backgroundColor);
        this.cornerRadiusRatio = cornerRadiusRatio;
    }

    public void setShadowAttribute(ShadowAttribute shadowAttribute) {
        this.shadowAttribute = shadowAttribute;
        updateBoundsPath();
        updateShadowBitmap();
    }

    public void setBackgroundColor(int backgroundColor) {
        boundsPathPaint.setColor(backgroundColor);
    }

    public int getPadding() {
        return padding;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.bounds = bounds;
        updateBoundsPath();
        updateShadowBitmap();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        clipCanvas(canvas);

        if (shadowBitmap != null) {
            canvas.drawBitmap(shadowBitmap, 0f, 0f, null);
        }

        canvas.restore();

        if (boundsPathPaint != null) {
            canvas.drawPath(boundsPath, boundsPathPaint);
        }
    }

    @Override
    public void setAlpha(int i) {}

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 更新邊界，用來切割陰影與繪製元件背景。
     */
    private void updateBoundsPath() {
        if (bounds.width() == 0 || bounds.height() == 0) {
            return;
        }

        Path path = new Path();
        float right = bounds.width() - padding;
        float bottom = bounds.height() - padding;
        float radius = getCornerRadius();

        path.addRoundRect((float) padding, (float) padding, right, bottom, radius, radius, Path.Direction.CW);
        path.close();
        boundsPath = path;
    }

    /**
     * 更新陰影Bitmap，先縮小再放大(bitmapScale)讓陰影更真實。
     */
    private void updateShadowBitmap() {
        if (bounds.width() == 0 || bounds.height() == 0) {
            return;
        }

        if (shadowAttribute.getRadius() == 0) {
            shadowBitmap = null;
            return;
        }

        float bitmapScale = shadowAttribute.getBitmapScale();
        int width = (int) (bounds.width()*bitmapScale);
        int height = (int) (bounds.height()*bitmapScale);
        int padding = (int) (this.padding * bitmapScale);
        Point offset = shadowAttribute.getOffset();

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(shadowAttribute.getColor());
        drawable.setBounds(new Rect(
                padding + offset.x,
                padding + offset.y,
                width - padding + offset.x,
                height - padding + offset.y
        ));
        drawable.setCornerRadius(getCornerRadius());

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        bitmap = BlurProvider.blur(context, bitmap, shadowAttribute.getRadius());
        shadowBitmap = Bitmap.createScaledBitmap(bitmap, bounds.width(), bounds.height(), true);
    }

    private float getCornerRadius() {
        int width = bounds.width();
        int height = bounds.height();
        return Math.min(width*cornerRadiusRatio, height*cornerRadiusRatio);
    }

    private void clipCanvas(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(boundsPath);
        } else {
            canvas.clipPath(boundsPath, Region.Op.DIFFERENCE);
        }
    }
}
