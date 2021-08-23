package kevin.le.learnandroid.view.components;

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
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowDrawable;

public class ShadowConstraintLayout extends ConstraintLayout {

    public ShadowDrawable shadowDrawable;
    public ShadowAttribute shadowAttribute;
    public final int shadowColor;

    public ShadowConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int darkGray = ResourcesCompat.getColor(getResources(), R.color.dark_gray_1, null);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowConstraintLayout);
        shadowColor = typedArray.getColor(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_shadow_color, darkGray);
        int backgroundColor = typedArray.getColor(R.styleable.ShadowConstraintLayout_shadow_constraint_layout_background_color, Color.WHITE);
        typedArray.recycle();

        shadowAttribute = new ShadowAttribute(shadowColor, 25, new Point(0, 4));
        shadowDrawable = new ShadowDrawable(context, shadowAttribute, backgroundColor);
        this.setBackground(shadowDrawable);
    }
}
