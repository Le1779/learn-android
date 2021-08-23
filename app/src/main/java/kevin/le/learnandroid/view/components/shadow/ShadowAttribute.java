package kevin.le.learnandroid.view.components.shadow;

import android.graphics.Point;

public class ShadowAttribute {
    private final int color;
    private int radius;
    private final Point offset;

    public ShadowAttribute(int color, int radius, Point offset) {
        this.color = color;
        this.offset = offset;
        setRadius(radius);
    }

    public int getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public Point getOffset() {
        return offset;
    }

    private void setRadius(int radius) {
        if (radius < 0) {
            radius = 0;
        } else if (radius > 25) {
            radius = 25;
        }
        this.radius = radius;
    }
}
