package kevin.le.learnandroid.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;

public class ShadowButton extends ShadowConstraintLayout implements View.OnTouchListener {

    private final int CLICK_ACTION_THRESHOLD = 200;
    private float startX, startY;
    private final ShadowAttribute pressedShadowAttribute;

    private final int iconResourceId;
    private final String title;
    private final float textSize;
    private final int tint;

    public ShadowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowButton);
        iconResourceId = typedArray.getResourceId(R.styleable.ShadowButton_shadow_button_icon, -1);
        title = typedArray.getString(R.styleable.ShadowButton_shadow_button_title);
        textSize = typedArray.getDimensionPixelSize(R.styleable.ShadowButton_shadow_button_text_size, -1);
        tint = typedArray.getColor(R.styleable.ShadowButton_shadow_button_tint, Color.BLACK);
        typedArray.recycle();

        pressedShadowAttribute = new ShadowAttribute(super.shadowColor, 10, new Point(0, 0));
        setOnTouchListener(this);
        generateSubview();
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

    /**
     * 產生按鈕的內容
     */
    private void generateSubview() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setId(View.generateViewId());
        this.addView(linearLayout);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(linearLayout.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        constraintSet.connect(linearLayout.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(linearLayout.getId(), ConstraintSet.LEFT, this.getId(), ConstraintSet.LEFT);
        constraintSet.connect(linearLayout.getId(), ConstraintSet.RIGHT, this.getId(), ConstraintSet.RIGHT);
        constraintSet.applyTo(this);

        generateIcon(linearLayout);
        generateTitle(linearLayout);
    }

    /**
     * 產生icon
     * @param container 容器
     */
    private void generateIcon(ViewGroup container) {
        if (iconResourceId == -1) {
            return;
        }

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(iconResourceId);
        imageView.setColorFilter(tint);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        container.addView(imageView);
    }

    /**
     * 產生title
     * @param container 容器
     */
    private void generateTitle(ViewGroup container) {
        if (title == null || title.isEmpty()) {
            return;
        }

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(tint);

        if (textSize != -1) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }

        container.addView(textView);
    }
}
