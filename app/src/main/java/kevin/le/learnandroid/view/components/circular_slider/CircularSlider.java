package kevin.le.learnandroid.view.components.circular_slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import kevin.le.learnandroid.R;

public class CircularSlider extends ViewGroup implements View.OnTouchListener {

    private Rect bounds;
    private ThumbView thumb;
    private TrackPath trackPath;
    private TrackDrawable trackDrawable;
    private AngleRange angleRange = new AngleRange(new Angle(110), new Angle(140));
    private boolean clockwise = false;
    private int currentAngle = 110;
    private float value = 0;

    private int strokeWidth;

    public CircularSlider(Context context) {
        this(context, null);
    }

    public CircularSlider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        bounds = new Rect(0, 0, 0, 0);
        trackPath = new TrackPath(angleRange);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularSlider);
            strokeWidth = typedArray.getInt(R.styleable.CircularSlider_stroke_width, 10);
            int strokeColor = typedArray.getColor(R.styleable.CircularSlider_stroke_color, Color.BLACK);
            int beginAngle = typedArray.getInt(R.styleable.CircularSlider_angle_begin, 0);
            int sweepAngle = typedArray.getInt(R.styleable.CircularSlider_angle_sweep, 0);
            clockwise = typedArray.getBoolean(R.styleable.CircularSlider_clockwise, false);
            typedArray.recycle();

            angleRange = new AngleRange(new Angle(beginAngle), new Angle(sweepAngle));
            currentAngle = clockwise ? (beginAngle + sweepAngle) : beginAngle;

            trackPath = new TrackPath(angleRange);
            trackDrawable = new TrackDrawable(strokeWidth, strokeColor);
            setBackground(trackDrawable);
        }

        thumb = new ThumbView(getContext());
        this.addView(thumb);
        LayoutParams layoutParams = thumb.getLayoutParams();
        layoutParams.height = 100;
        layoutParams.width = 100;
        thumb.setLayoutParams(layoutParams);
        Log.d(this.getClass().getName(), "height: " + thumb.getLayoutParams().height);
        Log.d(this.getClass().getName(), "height: " + thumb.getHeight());
    }

    public void setValue(float value) {
        this.value = Math.round(value*100)/100f;
        Log.d(this.getClass().getName(), "value update: " + this.value);
        updateThumbView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            Log.d(this.getClass().getName(), "onLayout left: " + l + " ,top: " + t + " ,right: " + r + " ,bottom: " + b);
            bounds.left = l;
            bounds.top = t;
            bounds.right = r;
            bounds.bottom = b;
            updateSubview();
        }
    }

    private void updateSubview() {
        updatePath();
        updateThumbView();
    }

    private void updatePath() {
        trackPath.updateBounds(new RectF(
                strokeWidth,
                strokeWidth,
                bounds.right - bounds.left - strokeWidth,
                bounds.bottom - bounds.top - strokeWidth
        ));

        trackDrawable.updateTrackPath(trackPath);
        invalidate();
    }

    private void updateThumbView() {
        if (thumb == null) {
            return;
        }

        Point thumbCenterPointer = trackPath.getPointFromAngle(currentAngle);
        thumb.setLeft(thumbCenterPointer.x - 20);
        thumb.setRight(thumbCenterPointer.x + 20);
        thumb.setTop(thumbCenterPointer.y - 20);
        thumb.setBottom(thumbCenterPointer.y + 20);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        Point touchPoint = new Point((int) (e.getX()), (int) (e.getY()));
        int angle = trackPath.getAngleFromPoint(touchPoint);
        int originAngle = trackPath.getOriginAngle(angle);
        float rate = (float) originAngle / angleRange.sweep.value;
        if (clockwise) {
            rate = 1 - rate;
        }
        //Log.d(this.getClass().getName(), "angle: " + angle);
        //Log.d(this.getClass().getName(), "originAngle: " + originAngle);


        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return x >= thumb.getLeft() && x <= thumb.getRight() && y >= thumb.getTop() && y <= thumb.getBottom();
            case MotionEvent.ACTION_MOVE:
                if (rate >= 0 && rate <=1) {
                    currentAngle = angle;
                    this.setValue(rate);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }
}
