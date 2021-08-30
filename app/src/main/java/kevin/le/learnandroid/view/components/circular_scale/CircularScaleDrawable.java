package kevin.le.learnandroid.view.components.circular_scale;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kevin.le.learnandroid.model.angle.Angle;
import kevin.le.learnandroid.model.angle.AngleRange;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class CircularScaleDrawable extends Drawable {

    private final AngleRange angleRange;

    private final float unitDivide;
    private final int anchorPoint;

    private final int scaleColor;
    private final float scaleWidth;
    private final float scaleHeight;
    private final float scaleDeviation;

    private float radius;
    private Point centerPoint;
    private Paint paint;

    public CircularScaleDrawable(AngleRange angleRange, float unitDivide, int anchorPoint, int scaleColor, float scaleWidth, float scaleHeight, float scaleDeviation) {
        this.angleRange = angleRange;
        this.unitDivide = unitDivide;
        this.anchorPoint = anchorPoint;
        this.scaleColor = scaleColor;
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
        this.scaleDeviation = scaleDeviation;

        setupPaint();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.radius = Math.min(bounds.width(), bounds.height())/2f - scaleHeight*scaleDeviation;
        this.centerPoint = new Point(bounds.width()/2, bounds.height()/2);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Path path = new Path();
        float scaleAngle = this.angleRange.sweep.value * this.unitDivide;
        for (int i = 0; i <= (int) (1f/this.unitDivide); i++) {
            boolean isAnchorPoint = i % this.anchorPoint == 0;
            Angle angle = new Angle((int) (this.angleRange.begin.value + scaleAngle*i));
            Point pointA = angle.getPointFromCircle(radius - scaleHeight, centerPoint);
            path.moveTo(pointA.x, pointA.y);

            float radius = isAnchorPoint ? this.radius : this.radius - scaleHeight*scaleDeviation;
            Point pointB = angle.getPointFromCircle(radius, centerPoint);
            path.lineTo(pointB.x, pointB.y);
        }

        canvas.drawPath(path, paint);
    }

    @Override
    public void setAlpha(int i) {}

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private void setupPaint() {
        paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(scaleWidth);
        paint.setColor(scaleColor);
    }
}
