package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

public class ShadowButton extends ShadowConstraintLayout implements View.OnTouchListener {

    private final int CLICK_ACTION_THRESHOLD = 200;
    private float startX, startY;
    private final ShadowAttribute pressedShadowAttribute;

    public ShadowButton(Context context, AttributeSet attrs) {
        super(context, attrs);


        pressedShadowAttribute = new ShadowAttribute(super.shadowColor, 15, new Point(0, 0));
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                shadowDrawable.setShadowAttribute(pressedShadowAttribute);
                invalidate();
                performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

                startX = e.getX();
                startY = e.getY();
                return true;
            case MotionEvent.ACTION_UP:
                shadowDrawable.setShadowAttribute(shadowAttribute);
                invalidate();

                float endX = e.getX();
                float endY = e.getY();

                if (isClick(startX, endX, startY, endY)) {
                    performClick();
                }
                break;
        }

        return false;
    }

    private boolean isClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESHOLD || differenceY > CLICK_ACTION_THRESHOLD);
    }
}
