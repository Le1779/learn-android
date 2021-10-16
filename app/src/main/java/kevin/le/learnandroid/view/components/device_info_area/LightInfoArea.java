package kevin.le.learnandroid.view.components.device_info_area;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.model.Utils;

public class LightInfoArea extends InfoArea {

    private int brightness = 0;
    private int colorTemperature = 0;

    private TextView splitSymbolTextView;
    private TextView brightnessTextView;
    private TextView colorTemperatureTextView;

    public LightInfoArea(@NonNull Context context) {
        super(context);
    }

    public LightInfoArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LightInfoArea(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LightInfoArea(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getIconId() {
        return R.drawable.ic_light;
    }

    @Override
    public int getIconBackgroundColor() {
        return ContextCompat.getColor(getContext(), R.color.orange);
    }

    @Override
    public void initSubview() {
        initSplitSymbolTextView();
    }

    @Override
    public void updateSubview() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.connect(splitSymbolTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, (int) (getHeight()*0.25));
        constraintSet.applyTo(centerContainer);

        float fontSize = Utils.pxToSp(centerContainer.getWidth() * 0.097f);
        splitSymbolTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColorTemperature() {
        return colorTemperature;
    }

    public void setColorTemperature(int colorTemperature) {
        this.colorTemperature = colorTemperature;
    }

    private void initSplitSymbolTextView() {
        splitSymbolTextView = new TextView(getContext());
        splitSymbolTextView.setId(View.generateViewId());
        splitSymbolTextView.setText("|");
        splitSymbolTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray_2));
        splitSymbolTextView.setTypeface(splitSymbolTextView.getTypeface(), Typeface.BOLD);
        centerContainer.addView(splitSymbolTextView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.centerHorizontally(splitSymbolTextView.getId(), ConstraintSet.PARENT_ID);
        constraintSet.applyTo(centerContainer);
    }
}
