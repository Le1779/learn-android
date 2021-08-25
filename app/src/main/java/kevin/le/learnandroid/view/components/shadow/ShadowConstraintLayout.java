package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import kevin.le.learnandroid.R;

public class ShadowConstraintLayout extends ConstraintLayout {

    public ShadowDrawable shadowDrawable;
    public ShadowAttribute shadowAttribute;
    public final int shadowColor;
    public final float cornerRadiusRatio;

    public ShadowConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int darkGray = ResourcesCompat.getColor(getResources(), R.color.dark_gray_1, null);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowConstraintLayout);
        shadowColor = typedArray.getColor(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_shadow_color, darkGray);
        int backgroundColor = typedArray.getColor(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_background_color, Color.WHITE);
        int shadowRadius = typedArray.getInt(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_shadow_radius, 25);
        cornerRadiusRatio = typedArray.getFloat(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_corner_radius_ratio, 0.5f);
        int offsetY = typedArray.getDimensionPixelOffset(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_offset_y, 4);
        typedArray.recycle();

        shadowAttribute = new ShadowAttribute(shadowColor, shadowRadius, new Point(0, offsetY));
        shadowDrawable = new ShadowDrawable(context, shadowAttribute, backgroundColor, cornerRadiusRatio);
        this.setBackground(shadowDrawable);

        int padding = shadowDrawable.getPadding();
        this.setPadding(padding, padding, padding, padding);
    }
}
