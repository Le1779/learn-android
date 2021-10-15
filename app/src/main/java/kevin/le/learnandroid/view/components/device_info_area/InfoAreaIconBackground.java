package kevin.le.learnandroid.view.components.device_info_area;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InfoAreaIconBackground extends View {
    public InfoAreaIconBackground(@NonNull Context context) {
        super(context);
        init();
    }

    public InfoAreaIconBackground(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfoAreaIconBackground(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);
        this.setBackground(backgroundDrawable);
    }

    private final GradientDrawable backgroundDrawable =  new GradientDrawable();
    private final Rect bounds = new Rect();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            bounds.left = left;
            bounds.top = top;
            bounds.right = right;
            bounds.bottom = bottom;
            backgroundDrawable.setCornerRadius(Math.min(bounds.width(), bounds.height())/2f);
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        backgroundDrawable.setColor(backgroundColor);
    }
}
