package kevin.le.learnandroid.view.components;

import android.content.Context;
import android.util.AttributeSet;

import kevin.le.learnandroid.view.components.shadow.ShadowDrawable;

public class ShadowButton extends androidx.appcompat.widget.AppCompatButton {

    public ShadowButton(Context context) {
        super(context);
    }

    public ShadowButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setBackground(new ShadowDrawable(context));
    }

}
