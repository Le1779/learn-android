package kevin.le.learnandroid.view.components.device_info_area;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.model.Utils;

public class LightInfoArea extends InfoArea {

    private TextView splitSymbolTextView;
    private TextView brightnessTextView;
    private TextView brightnessTitleTextView;
    private TextView colorTemperatureTextView;
    private TextView colorTemperatureTitleTextView;

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
        initBrightnessField();
        initColorTemperatureField();
    }

    @Override
    public void updateSubview() {
        int margin = (int) (getWidth()*0.025f);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.connect(splitSymbolTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, (int) (getHeight()*0.25));
        constraintSet.connect(brightnessTextView.getId(), ConstraintSet.RIGHT, splitSymbolTextView.getId(), ConstraintSet.LEFT, margin);
        constraintSet.connect(colorTemperatureTextView.getId(), ConstraintSet.LEFT, splitSymbolTextView.getId(), ConstraintSet.RIGHT, margin);
        constraintSet.applyTo(centerContainer);

        float fontSize = Utils.pxToSp(centerContainer.getWidth() * 0.1f);
        splitSymbolTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        brightnessTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        colorTemperatureTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        float titleFontSize = fontSize * 0.6f;
        brightnessTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleFontSize);
        colorTemperatureTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleFontSize);
    }

    public void setBrightness(int brightness) {
        brightnessTextView.setText(String.format(getResources().getString(R.string.percent), brightness));
    }

    public void setColorTemperature(int colorTemperature) {
        colorTemperatureTextView.setText(String.format(getResources().getString(R.string.percent), colorTemperature));
    }

    /**
     * 分隔符號
     */
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

    /**
     * 亮度欄位
     */
    private void initBrightnessField() {
        brightnessTextView = generateValueTextView();
        brightnessTitleTextView = generateTitleTextView(R.string.brightness);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.connect(brightnessTextView.getId(), ConstraintSet.TOP, splitSymbolTextView.getId(), ConstraintSet.TOP);
        constraintSet.connect(brightnessTextView.getId(), ConstraintSet.BOTTOM, splitSymbolTextView.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(brightnessTitleTextView.getId(), ConstraintSet.BOTTOM, brightnessTextView.getId(), ConstraintSet.TOP);
        constraintSet.connect(brightnessTitleTextView.getId(), ConstraintSet.RIGHT, brightnessTextView.getId(), ConstraintSet.RIGHT);
        constraintSet.applyTo(centerContainer);
    }

    /**
     * 色溫欄位
     */
    private void initColorTemperatureField() {
        colorTemperatureTextView = generateValueTextView();
        colorTemperatureTitleTextView = generateTitleTextView(R.string.color_temperature);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.connect(colorTemperatureTextView.getId(), ConstraintSet.TOP, splitSymbolTextView.getId(), ConstraintSet.TOP);
        constraintSet.connect(colorTemperatureTextView.getId(), ConstraintSet.BOTTOM, splitSymbolTextView.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(colorTemperatureTitleTextView.getId(), ConstraintSet.BOTTOM, colorTemperatureTextView.getId(), ConstraintSet.TOP);
        constraintSet.connect(colorTemperatureTitleTextView.getId(), ConstraintSet.LEFT, colorTemperatureTextView.getId(), ConstraintSet.LEFT);
        constraintSet.applyTo(centerContainer);
    }

    /**
     * 產生Value TextView
     * @return TextView
     */
    private TextView generateValueTextView() {
        TextView textView = new TextView(getContext());
        textView.setId(View.generateViewId());
        textView.setText("-");
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray_2));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        centerContainer.addView(textView);
        return textView;
    }

    /**
     * 產生Title TextView
     * @param stringId 要顯示的Resource id
     * @return TextView
     */
    private TextView generateTitleTextView(@StringRes int stringId) {
        TextView textView = new TextView(getContext());
        textView.setId(View.generateViewId());
        textView.setText(stringId);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray_4));
        centerContainer.addView(textView);
        return textView;
    }
}
