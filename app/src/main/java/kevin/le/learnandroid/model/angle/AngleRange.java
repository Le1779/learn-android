package kevin.le.learnandroid.model.angle;

public class AngleRange {
    public Angle begin, sweep;

    public AngleRange(int begin, int sweep) {
        this.begin = new Angle(begin);
        this.sweep = new Angle(sweep);
    }
}
