package kevin.le.learnandroid.view.components.shadow;

import android.graphics.Point;

public class ShadowAttribute {
    private int color;
    private int radius;
    private final Point offset;
    private float bitmapScale = 1;

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

    public float getBitmapScale() {
        return bitmapScale;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        if (radius < 0) {
            radius = 0;
        } else if (radius > 25) {
            bitmapScale = 25f/radius;
            radius = 25;
        }
        this.radius = radius;
    }
}
