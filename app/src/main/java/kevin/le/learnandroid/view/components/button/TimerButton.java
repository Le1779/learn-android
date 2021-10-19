package kevin.le.learnandroid.view.components.button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.model.Utils;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowButton;

public class TimerButton extends ShadowButton {

    private ImageView icon;
    private TextView title;
    private TextView settingTime;

    public TimerButton(Context context) {
        super(context, null);
        init();
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            updateConstraint();
        }
    }

    private void init() {
        initShadow();
        initIcon();
        initTitle();
        initSettingTime();
    }

    private void initShadow() {
        int shadowColor = Color.parseColor("#20000000");
        shadowAttribute = new ShadowAttribute(shadowColor, 25, new Point(0, 4));
        pressedShadowAttribute = new ShadowAttribute(shadowColor, 20, new Point(0, 0));
        shadowDrawable.setShadowAttribute(shadowAttribute);
    }

    private void initIcon() {
        icon = new ImageView(getContext());
        icon.setId(View.generateViewId());
        icon.setImageResource(R.drawable.ic_timer);
        icon.setColorFilter(ContextCompat.getColor(getContext(), R.color.dark_gray_3));
        this.addView(icon);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.constrainDefaultWidth(icon.getId(), 24);
        constraintSet.setDimensionRatio(icon.getId(), "1:1");
        constraintSet.applyTo(this);
    }

    private void initTitle() {
        title = new TextView(getContext());
        title.setId(View.generateViewId());
        title.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray_2));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setText(R.string.timer);
        this.addView(title);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(title.getId(), ConstraintSet.TOP, icon.getId(), ConstraintSet.TOP);
        constraintSet.connect(title.getId(), ConstraintSet.LEFT, icon.getId(), ConstraintSet.RIGHT, Utils.dpToPx(8));
        constraintSet.applyTo(this);
    }

    private void initSettingTime() {
        settingTime = new TextView(getContext());
        settingTime.setId(View.generateViewId());
        settingTime.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray_2));
        settingTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        settingTime.setText(R.string.not_setting);
        this.addView(settingTime);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(settingTime.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(settingTime.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(settingTime.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(settingTime.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.setVerticalBias(settingTime.getId(), 0.2f);
        constraintSet.applyTo(this);
    }

    private void updateConstraint() {
        int margin = (int) Math.min(getWidth()*cornerRadiusRatio, getHeight()*cornerRadiusRatio);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(icon.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, margin);
        constraintSet.connect(icon.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, margin);
        constraintSet.applyTo(this);
    }
}
