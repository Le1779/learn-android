package kevin.le.learnandroid.view.components;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import kevin.le.learnandroid.view.components.shadow.ShadowDrawable;

public class ShadowConstraintLayout extends ConstraintLayout {

    public ShadowConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public ShadowConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(new ShadowDrawable(context));
    }
}
