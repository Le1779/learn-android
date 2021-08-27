package kevin.le.learnandroid.view.components.circular_slider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowConstraintLayout;

public class ThumbView extends ShadowConstraintLayout {

    public ThumbView(@NonNull Context context) {
        this(context, null);
    }

    public ThumbView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        shadowAttribute = new ShadowAttribute(Color.parseColor("#40000000"), 40, new Point(0, 0));
        shadowDrawable.setShadowAttribute(shadowAttribute);
    }

    public Rect getBounds() {
        return new Rect(
                (int) (getX() + getLeft()),
                (int) (getY() + getTop()),
                (int) (getX() + getRight()),
                (int) (getY() + getBottom())
        );
    }
}
