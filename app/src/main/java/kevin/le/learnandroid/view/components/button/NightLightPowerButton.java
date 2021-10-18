package kevin.le.learnandroid.view.components.button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowButton;
import kevin.le.learnandroid.view.components.shadow.ShadowDrawable;

public class NightLightPowerButton extends ShadowButton {
    private AppCompatImageView image;
    private View lightView;
    private boolean isOn = true;

    public NightLightPowerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        int shadowColor = Color.parseColor("#20000000");
        shadowAttribute = new ShadowAttribute(shadowColor, 36, new Point(0, 4));
        pressedShadowAttribute = new ShadowAttribute(shadowColor, 20, new Point(0, 0));
        shadowDrawable.setShadowAttribute(shadowAttribute);
        generateImage();
        generateLightView();
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        updateStatus();
    }

    public boolean isOn() {
        return isOn;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        updateStatus();
    }

    private void generateImage() {
        image = new AppCompatImageView(getContext());
        image.setId(View.generateViewId());
        image.setImageResource(R.drawable.ic_nightlight);
        image.setColorFilter(ContextCompat.getColor(getContext(), R.color.dark_gray_3));
        this.addView(image);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(image.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(image.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(image.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(image.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainPercentWidth(image.getId(), 0.7f);

        constraintSet.applyTo(this);
    }

    private void generateLightView() {
        ShadowAttribute attribute = new ShadowAttribute(ContextCompat.getColor(getContext(), R.color.orange), 46, new Point(0, 0));
        ShadowDrawable drawable = new ShadowDrawable(getContext(), attribute, false);
        lightView = new View(getContext());
        lightView.setId(View.generateViewId());
        lightView.setBackground(drawable);
        this.addView(lightView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(lightView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(lightView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(lightView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(lightView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainPercentWidth(lightView.getId(), 0.7f);

        constraintSet.applyTo(this);
    }

    private void updateStatus() {
        int filterColor = ContextCompat.getColor(getContext(), isOn && isEnabled() ? R.color.dark_gray_3 : R.color.dark_gray_5);
        image.setColorFilter(filterColor);

        lightView.setVisibility(isOn && isEnabled() ? VISIBLE : INVISIBLE);
    }
}
