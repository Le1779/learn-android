package kevin.le.learnandroid.view.components;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.indicator_light.IndicatorLight;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowButton;

public class OnOffButton extends ShadowButton {

    private Guideline guideLine;
    private IndicatorLight indicatorLight;
    private TextView statusTextView;
    private boolean isOn = true;

    public OnOffButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        shadowAttribute = new ShadowAttribute(super.shadowColor, 25, new Point(0, 3));
        shadowDrawable.setShadowAttribute(shadowAttribute);
        generateContainer();
        setOn(true);
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        indicatorLight.isOn(isOn);
        statusTextView.setText(isOn ? R.string.on : R.string.off);
    }

    public boolean isOn() {
        return isOn;
    }

    private void generateContainer() {
        generateGuideline();
        generateIndicatorLight();
        generateStatusTextView();
        setupConstraint();
    }

    /**
     * 生成引導線
     */
    private void generateGuideline() {
        guideLine = new Guideline(getContext());
        guideLine.setId(View.generateViewId());
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        lp.orientation = LayoutParams.HORIZONTAL;
        guideLine.setLayoutParams(lp);
        this.addView(guideLine);
        guideLine.setGuidelinePercent(0.4f);
    }

    /**
     * 生成指示燈
     */
    private void generateIndicatorLight() {
        indicatorLight = new IndicatorLight(getContext());
        indicatorLight.setId(View.generateViewId());
        indicatorLight.setLayoutParams(new ConstraintLayout.LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.MATCH_CONSTRAINT
        ));
        this.addView(indicatorLight);

        indicatorLight.isOn(isOn);
    }

    /**
     * 生成狀態文字
     */
    private void generateStatusTextView() {
        statusTextView = new TextView(getContext());
        statusTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.MATCH_CONSTRAINT
        ));
        statusTextView.setId(View.generateViewId());
        this.addView(statusTextView);

        statusTextView.setMaxLines(1);
        statusTextView.setTypeface(Typeface.DEFAULT_BOLD);
        statusTextView.setTextSize(20);
        statusTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    /**
     * 設定約束
     */
    private void setupConstraint() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.setGuidelinePercent(guideLine.getId(), 0.4f);

        constraintSet.connect(indicatorLight.getId(), ConstraintSet.BOTTOM, guideLine.getId(), ConstraintSet.TOP, 8);
        constraintSet.connect(indicatorLight.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(indicatorLight.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainPercentHeight(indicatorLight.getId(), 0.2f);
        constraintSet.constrainPercentWidth(indicatorLight.getId(), 0.7f);

        constraintSet.connect(statusTextView.getId(), ConstraintSet.TOP, guideLine.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(statusTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(statusTextView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(statusTextView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainPercentWidth(statusTextView.getId(), 0.5f);

        constraintSet.applyTo(this);
    }
}
