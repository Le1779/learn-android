package kevin.le.learnandroid.view.components.circular_slider;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import kevin.le.learnandroid.model.angle.Angle;
import kevin.le.learnandroid.model.angle.AngleRange;

public class TrackPath {

    public Path path;
    private RectF bounds;
    private final AngleRange angleRange;
    private float radius;
    private Point centerPoint = new Point(0, 0);

    public TrackPath(AngleRange angleRange) {
        this.angleRange = angleRange;
    }

    /**
     * 更新外框。
     * @param bounds 外框
     */
    public void updateBounds(RectF bounds) {
        this.bounds = bounds;
        this.centerPoint = new Point((int) (bounds.centerX()), (int) (bounds.centerY()));
        this.radius = bounds.width()/2;
        updatePath();
    }

    /**
     * 透過一個點來取得與圓心的夾角。
     * @param point 某一個點
     * @return 與圓心的夾角
     */
    public int getAngleFromPoint(Point point) {
        Point origin = new Point(point.x - centerPoint.x, point.y - centerPoint.y);
        double bearingRadians = Math.atan2(origin.y, origin.x);
        double bearingDegrees = (bearingRadians) * (180.0 / Math.PI);
        return (int) (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees));
    }

    /**
     * 取得滑動的度數，以起始度數為0的角度。
     * @param angle 角度
     * @return 滑動的度數
     */
    public int getOriginAngle(int angle) {
        int originAngle;
        if (angle < this.angleRange.begin.value) {
            originAngle = angle + 360 - this.angleRange.begin.value;
        } else {
            originAngle = angle - this.angleRange.begin.value;
        }

        if (originAngle > this.angleRange.sweep.value) {
            int overvalue = 360 - this.angleRange.sweep.value;
            if (originAngle > (overvalue/2 + this.angleRange.sweep.value)) {
                originAngle = originAngle - 360;
            }
        }

        return originAngle;
    }

    /**
     * 透過角度取得線上的某一點。
     * @param angle 角度
     * @return 角度對應的點
     */
    public Point getPointFromAngle(int angle) {
        double x = radius * Math.cos(new Angle(angle).radians);
        double y = radius * Math.sin(new Angle(angle).radians);

        return new Point((int) (centerPoint.x + x), (int) (centerPoint.y + y));
    }

    private void updatePath() {
        this.path = new Path();
        this.path.arcTo(bounds, angleRange.begin.value, angleRange.sweep.value);
    }
}
