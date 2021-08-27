package kevin.le.learnandroid.view.components.circular_slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import kevin.le.learnandroid.R;

public class CircularSlider extends ViewGroup implements View.OnTouchListener {

    private final Rect bounds;
    private ThumbView thumb;
    private TrackPath trackPath;
    private TrackDrawable trackDrawable;
    private AngleRange angleRange = new AngleRange(new Angle(110), new Angle(140));
    private boolean clockwise = false;
    private int currentAngle = 110;
    private float value = 0;
    private OnCircularSliderChangeListener listener;

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
    }

    public void setValue(float value) {
        this.value = Math.round(value*100)/100f;
        updateThumbView();
        if (listener != null) {
            listener.onValueChange(this, this.value);
        }
    }

    public void setOnCircularSliderChangeListener(OnCircularSliderChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        thumb = new ThumbView(getContext());
        ViewGroup parent = (ViewGroup) getParent();
        parent.addView(thumb);
        LayoutParams layoutParams = thumb.getLayoutParams();
        layoutParams.height = 120;
        layoutParams.width = 120;
        thumb.setLayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
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
        thumb.setX(thumbCenterPointer.x + this.getX() + ((View)getParent()).getX() - thumb.getLayoutParams().width/2f);
        thumb.setY(thumbCenterPointer.y + this.getY() + ((View)getParent()).getY() - thumb.getLayoutParams().height/2f);
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
                int relativeX = (int) (x + this.getX() + ((View)getParent()).getX());
                int relativeY = (int) (y + this.getY() + ((View)getParent()).getY());
                return thumb.getBounds().contains(relativeX, relativeY);
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
