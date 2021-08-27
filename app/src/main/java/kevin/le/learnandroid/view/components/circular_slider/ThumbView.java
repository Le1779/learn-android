package kevin.le.learnandroid.view.components.circular_slider;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kevin.le.learnandroid.view.components.shadow.ShadowConstraintLayout;

public class ThumbView  extends View {//} implements View.OnTouchListener  {

    public ThumbView(@NonNull Context context) {
        this(context, null);
    }

    public ThumbView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.BLACK);
    }

    //@Override
    //public boolean onTouch(View view, MotionEvent motionEvent) {
    //    return false;
    //}
}
