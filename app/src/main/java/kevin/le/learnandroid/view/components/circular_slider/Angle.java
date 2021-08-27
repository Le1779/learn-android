package kevin.le.learnandroid.view.components.circular_slider;

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

    private float getRadians(int angle) {
        return (float) (angle * Math.PI / 180);
    }
}
