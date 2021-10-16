package kevin.le.learnandroid.view.components.device_info_area;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.R;

public class LightInfoArea extends InfoArea {
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

    }
}
