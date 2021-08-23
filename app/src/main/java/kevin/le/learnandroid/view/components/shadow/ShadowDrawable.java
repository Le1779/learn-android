package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ShadowDrawable extends Drawable {

    private Context context;
    private float cornerRadiusRatio = 2.0f;
    private int backgroundColor = Color.parseColor("#F4BF71");
    private int shadowColor = Color.parseColor("#F4BF71");
    private int padding = 29;
    private Path boundsPath;
    private Bitmap shadowBitmap;
    private final Paint boundsPathPaint;
    private int shadowRadius = 25;

    public ShadowDrawable(Context context) {
        this.context = context;

        boundsPathPaint = new Paint(ANTI_ALIAS_FLAG);
        boundsPathPaint.setStyle(Paint.Style.FILL);
        boundsPathPaint.setColor(backgroundColor);
    }

    public ShadowDrawable(Context context, int shadowRadius) {
        this(context);
        this.shadowRadius = shadowRadius;
    }

    public void setCornerRadiusRatio(float ratio) {
        this.cornerRadiusRatio = ratio;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setShadowRadius(int radius) {
        this.shadowRadius = radius;
        updateBoundsPath();
        updateShadowBitmap();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updateBoundsPath();
        updateShadowBitmap();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        //clipCanvas(canvas);
        if (shadowBitmap != null) {
            canvas.drawBitmap(shadowBitmap, 0f, 0f, null);
        }

        canvas.restore();

        canvas.drawPath(boundsPath, boundsPathPaint);
    }

    @Override
    public void setAlpha(int i) {}

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private void clipCanvas(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(boundsPath);
        } else {
            canvas.clipPath(boundsPath, Region.Op.DIFFERENCE);
        }
    }

    private void updateBoundsPath() {
        Path path = new Path();
        float left = padding;
        float top = padding;
        float right = this.getBounds().width() - padding;
        float bottom = this.getBounds().height() - padding;
        float radius = getCornerRadius();

        path.addRoundRect(left, top, right, bottom, radius, radius, Path.Direction.CW);
        path.close();
        boundsPath = path;
    }

    private void updateShadowBitmap() {
        if (this.getBounds().width() == 0 || this.getBounds().height() == 0) {
            return;
        }

        int offset = 4;
        float scale = 0.7f;
        int width = (int) (this.getBounds().width()*scale);
        int height = (int) (this.getBounds().height()*scale);
        int padding = (int) (this.padding * scale);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(shadowColor);
        drawable.setBounds(new Rect(padding, padding + offset, width - padding, height - padding + offset));
        drawable.setCornerRadius(getCornerRadius());

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);

        bitmap = BlurProvider.blur(context, bitmap, shadowRadius);
        shadowBitmap = Bitmap.createScaledBitmap(bitmap, this.getBounds().width(), this.getBounds().height(), true);
    }

    private float getCornerRadius() {
        int width = this.getBounds().width();
        int height = this.getBounds().height();
        return Math.min(width*cornerRadiusRatio, height*cornerRadiusRatio);
    }
}
