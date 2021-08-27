package kevin.le.learnandroid.view.components.circular_slider;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;


public class TrackPath {

    public Path path;
    private RectF bounds;
    private final AngleRange angleRange;
    private float radius;
    private Point centerPoint;

    public TrackPath(AngleRange angleRange) {
        this.angleRange = angleRange;
        this.path = new Path();
    }

    public void updateBounds(RectF bounds) {
        this.bounds = bounds;
        this.centerPoint = new Point((int) (bounds.centerX()), (int) (bounds.centerY()));
        this.radius = bounds.width()/2;
        updatePath();
    }

    public int getAngleFromPoint(Point point) {
        Point origin = new Point(point.x - centerPoint.x, point.y - centerPoint.y);
        double bearingRadians = Math.atan2(origin.y, origin.x);
        double bearingDegrees = (bearingRadians) * (180.0 / Math.PI);
        return (int) (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees));
    }

    public int getOriginAngle(int angle) {
        if (angle < this.angleRange.begin.value) {
            return angle + 360 - this.angleRange.begin.value;
        }

        return angle - this.angleRange.begin.value;
    }

    public Point getPointFromAngle(int angle) {
        double x = radius * Math.cos(new Angle(angle).radians);
        double y = radius * Math.sin(new Angle(angle).radians);

        return new Point((int) (centerPoint.x + x), (int) (centerPoint.y + y));
    }

    private void updatePath() {
        this.path.arcTo(bounds, angleRange.begin.value, angleRange.sweep.value);
    }
}
