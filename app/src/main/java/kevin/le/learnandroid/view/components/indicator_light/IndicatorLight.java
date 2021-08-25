package kevin.le.learnandroid.view.components.indicator_light;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowDrawable;

public class IndicatorLight extends View {

    private final ShadowDrawable drawable;
    private ShadowAttribute onShadowAttribute;
    private ShadowAttribute offShadowAttribute;

    public IndicatorLight(Context context) {
        this(context, null);
    }

    public IndicatorLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        generateOnShadowAttribute();
        generateOffShadowAttribute();

        drawable = new ShadowDrawable(getContext(), onShadowAttribute, onShadowAttribute.getColor(), 0.5f);
        this.setBackground(drawable);

        int padding = drawable.getPadding();
        this.setPadding(padding, padding, padding, padding);
    }

    public void isOn(boolean isOn) {
        ShadowAttribute attribute;
        if (isOn) {
            attribute = onShadowAttribute;
        } else {
            attribute = offShadowAttribute;
        }

        drawable.setShadowAttribute(attribute);
        drawable.setBackgroundColor(attribute.getColor());
        invalidate();
    }

    private void generateOnShadowAttribute() {
        int color = Color.parseColor("#4CAF50");
        onShadowAttribute = new ShadowAttribute(color, 25, new Point(0, 0));
    }

    private void generateOffShadowAttribute() {
        int color = Color.parseColor("#E8E8E8");
        offShadowAttribute = new ShadowAttribute(color, 0, new Point(0, 0));
    }
}
