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
import kevin.le.learnandroid.model.angle.AngleRange;

public class CircularSlider extends View implements View.OnTouchListener {

    private final Rect bounds;
    private ThumbView thumb;
    private TrackPath trackPath;
    private TrackDrawable trackDrawable;
    private AngleRange angleRange = new AngleRange(0, 180);
    private boolean clockwise = false;
    private OnCircularSliderChangeListener listener;
    private int strokeWidth;
    private float value = 0;

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

            angleRange = new AngleRange(beginAngle, sweepAngle);

            trackPath = new TrackPath(angleRange);
            trackDrawable = new TrackDrawable(strokeWidth, strokeColor);
            setBackground(trackDrawable);
        }
    }

    public void setValue(float value) {
        this.value = Math.round(value * 100) / 100f;
        updateThumbView();
    }

    public void setOnCircularSliderChangeListener(OnCircularSliderChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 在這個元件被載入到頁面上時，將Thumb新增至父容器中。
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        thumb = new ThumbView(getContext());
        ViewGroup parent = (ViewGroup) getParent();
        parent.addView(thumb);
        ViewGroup.LayoutParams layoutParams = thumb.getLayoutParams();
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

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (thumb != null) {
            thumb.setVisibility(enabled ? VISIBLE : INVISIBLE);
        }
    }

    /**
     * 觸碰事件，只有點擊在Thumb上面才觸發事件。
     * @param view 這個View
     * @param e Event
     * @return 是否被點擊
     */
    @Override
    public boolean onTouch(View view, MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        Point touchPoint = new Point((int) (e.getX()), (int) (e.getY()));
        int angle = trackPath.getAngleFromPoint(touchPoint);
        int originAngle = trackPath.getOriginAngle(angle);
        float rate = (float) originAngle / angleRange.sweep.value;
        if (!clockwise) {
            rate = 1 - rate;
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int relativeX = (int) (x + this.getX() + ((View)getParent()).getX());
                int relativeY = (int) (y + this.getY() + ((View)getParent()).getY());
                return thumb.getBounds().contains(relativeX, relativeY);
            case MotionEvent.ACTION_MOVE:
                if (rate < 0) {
                    rate = 0;
                } else if (rate > 1) {
                    rate = 1;
                }

                this.setValue(rate);
                if (listener != null) {
                    listener.onValueChange(this, this.value);
                }
                break;
        }

        return true;
    }

    private void updateSubview() {
        updatePath();
        updateThumbView();
    }

    /**
     * 更新拖曳軌跡的路徑。
     */
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

    /**
     * 更新Thumb的位置
     */
    private void updateThumbView() {
        if (thumb == null) {
            return;
        }

        int currentAngle;
        if (clockwise) {
            currentAngle = (int) (angleRange.begin.value + angleRange.sweep.value * value);
        } else {
            currentAngle = (int) (angleRange.begin.value + angleRange.sweep.value * (1 - value));
        }

        Point thumbCenterPointer = trackPath.getPointFromAngle(currentAngle);
        thumb.setX(thumbCenterPointer.x + this.getX() + ((View)getParent()).getX() - thumb.getLayoutParams().width/2f);
        thumb.setY(thumbCenterPointer.y + this.getY() + ((View)getParent()).getY() - thumb.getLayoutParams().height/2f);
    }
}
