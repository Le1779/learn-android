package kevin.le.learnandroid.view.components.circular_scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.model.angle.Angle;
import kevin.le.learnandroid.model.angle.AngleRange;

public class CircularScale extends View {

    private AngleRange angleRange;

    private float unitDivide;
    private int anchorPoint;

    private int scaleColor;
    private float scaleWidth;
    private float scaleHeight;
    private float scaleDeviation;

    public CircularScale(Context context) {
        this(context, null);
    }

    public CircularScale(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularScale);
            int beginAngle = typedArray.getInt(R.styleable.CircularScale_angle_begin, 0);
            int sweepAngle = typedArray.getInt(R.styleable.CircularScale_angle_sweep, 0);
            angleRange = new AngleRange(new Angle(beginAngle), new Angle(sweepAngle));

            unitDivide = typedArray.getFloat(R.styleable.CircularScale_unit_divide, 0.1f);
            anchorPoint = typedArray.getInt(R.styleable.CircularScale_anchor_point, 5);

            scaleWidth = typedArray.getDimension(R.styleable.CircularScale_scale_width, 1);
            scaleHeight = typedArray.getDimension(R.styleable.CircularScale_scale_height, 10);
            scaleColor = typedArray.getColor(R.styleable.CircularScale_scale_color, Color.BLACK);
            scaleDeviation = typedArray.getFloat(R.styleable.CircularScale_scale_deviation, 0.5f);
            typedArray.recycle();
        }

        updateBackground();
    }

    private void updateBackground() {
        CircularScaleDrawable drawable = new CircularScaleDrawable(
                angleRange,
                unitDivide,
                anchorPoint,
                scaleColor,
                scaleWidth,
                scaleHeight,
                scaleDeviation
        );

        this.setBackground(drawable);
    }
}
