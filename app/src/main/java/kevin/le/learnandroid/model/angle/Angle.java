package kevin.le.learnandroid.model.angle;

import android.graphics.Point;

public class Angle {
    public int value;
    public float radians;

    public Angle(int value) {
        this.value = value;
        if (value > 360) {
            this.value = value % 360;
        }

        radians = getRadians(value);
    }

    public Point getPointFromCircle(float radius, Point centerPoint) {
        double x = radius * Math.cos(this.radians);
        double y = radius * Math.sin(this.radians);

        return new Point((int) (centerPoint.x + x), (int) (centerPoint.y + y));
    }

    private float getRadians(int angle) {
        return (float) (angle * Math.PI / 180);
    }
}
