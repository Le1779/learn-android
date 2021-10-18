package kevin.le.learnandroid.view.components.button;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowButton;

public class UVCButton extends ShadowButton {

    private TextView uvcTextView;
    private final UVCGradientDrawable borderDrawable;
    private boolean isOn = true;

    public UVCButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        shadowAttribute = new ShadowAttribute(super.shadowColor, 25, new Point(0, 3));
        shadowDrawable.setShadowAttribute(shadowAttribute);
        borderDrawable = new UVCGradientDrawable(shadowAttribute, cornerRadiusRatio);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] {shadowDrawable, borderDrawable});
        setBackground(layerDrawable);

        generateUVCTextView();
        setupConstraint();
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        borderDrawable.setOn(isOn);
        invalidate();
    }

    public boolean isOn() {
        return isOn;
    }

    private void generateUVCTextView() {
        uvcTextView = new TextView(getContext());
        uvcTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.WRAP_CONTENT
        ));
        uvcTextView.setId(View.generateViewId());
        this.addView(uvcTextView);

        uvcTextView.setText(R.string.uvc);
        uvcTextView.setMaxLines(1);
        uvcTextView.setTypeface(Typeface.DEFAULT_BOLD);
        uvcTextView.setTextSize(20);
        uvcTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        uvcTextView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Log.d(this.getClass().getName(), "UVC layout change");
                updateUVCGradient();
            }
        });
    }

    /**
     * 設定約束
     */
    private void setupConstraint() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(uvcTextView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(uvcTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(uvcTextView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(uvcTextView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.constrainPercentWidth(uvcTextView.getId(), 0.5f);

        constraintSet.applyTo(this);
    }

    private void updateUVCGradient() {
        Paint paint = uvcTextView.getPaint();
        float width = paint.measureText(uvcTextView.getText().toString());
        Shader shader = new LinearGradient(0f, 0f, width, uvcTextView.getTextSize(), new int[] {
                Color.parseColor("#8CA066FF"),
                Color.parseColor("#8C75ABFB")
        }, null, Shader.TileMode.REPEAT);
        uvcTextView.getPaint().setShader(shader);
    }
}
