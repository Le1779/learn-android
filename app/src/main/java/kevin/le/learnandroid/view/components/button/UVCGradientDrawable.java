package kevin.le.learnandroid.view.components.button;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class UVCGradientDrawable extends Drawable {

    private final float cornerRadiusRatio;
    private final Paint backgroundPaint;
    private final int padding;
    private Rect bounds = new Rect();
    private Path boundsPath;
    private boolean isOn = true;

    public UVCGradientDrawable(ShadowAttribute shadowAttribute, float cornerRadiusRatio) {
        Point offset = shadowAttribute.getOffset();
        this.padding = (int) ((shadowAttribute.getRadius() + Math.max(offset.x, offset.y))/shadowAttribute.getBitmapScale());

        backgroundPaint = new Paint(ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(8);

        this.cornerRadiusRatio = cornerRadiusRatio;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.bounds = bounds;
        updateBoundsPath();
        updatePaint();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (isOn) {
            canvas.drawPath(boundsPath, backgroundPaint);
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

    private void updatePaint() {
        Shader shader = new LinearGradient(0f, 0f, bounds.width(), bounds.height(), new int[] {
                Color.parseColor("#8CA066FF"),
                Color.parseColor("#8C75ABFB")
        }, null, Shader.TileMode.REPEAT);
        backgroundPaint.setShader(shader);
    }

    private float getCornerRadius() {
        int width = bounds.width();
        int height = bounds.height();
        return Math.min(width*cornerRadiusRatio, height*cornerRadiusRatio);
    }
}
