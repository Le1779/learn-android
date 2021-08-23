package kevin.le.learnandroid.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import kevin.le.learnandroid.view.components.shadow.ShadowDrawable;

public class ShadowButton extends ShadowConstraintLayout implements View.OnTouchListener {

    private final int CLICK_ACTION_THRESHOLD = 200;
    private float startX, startY;
    private final ShadowDrawable pressedShadowDrawable;

    public ShadowButton(Context context) {
        super(context);
        pressedShadowDrawable = new ShadowDrawable(context, 10);
        setOnTouchListener(this);
    }

    public ShadowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        pressedShadowDrawable = new ShadowDrawable(context, 10);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = e.getX();
                startY = e.getY();
                this.setBackground(pressedShadowDrawable);
                performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                return true;
            case MotionEvent.ACTION_UP:
                float endX = e.getX();
                float endY = e.getY();

                if (isClick(startX, endX, startY, endY)) {
                    performClick();
                    return true;
                }

                this.setBackground(shadowDrawable);
                return false;
        }

        return false;
    }

    private boolean isClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESHOLD || differenceY > CLICK_ACTION_THRESHOLD);
    }
}
