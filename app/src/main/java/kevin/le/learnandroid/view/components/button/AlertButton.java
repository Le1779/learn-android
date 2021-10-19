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

public class AlertButton extends ShadowButton {
    private AppCompatImageView image;
    private boolean isOn = true;

    public AlertButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        int shadowColor = Color.parseColor("#20000000");
        shadowAttribute = new ShadowAttribute(shadowColor, 36, new Point(0, 4));
        pressedShadowAttribute = new ShadowAttribute(shadowColor, 20, new Point(0, 0));
        shadowDrawable.setShadowAttribute(shadowAttribute);
        generateImage();
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        updateStatus();
    }

    public boolean isOn() {
        return isOn;
    }

    private void generateImage() {
        image = new AppCompatImageView(getContext());
        image.setId(View.generateViewId());
        image.setImageResource(R.drawable.ic_out_door);
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

    private void updateStatus() {
        int filterColor = ContextCompat.getColor(getContext(), isOn && isEnabled() ? R.color.dark_gray_3 : R.color.dark_gray_5);
        image.setColorFilter(filterColor);
    }
}
