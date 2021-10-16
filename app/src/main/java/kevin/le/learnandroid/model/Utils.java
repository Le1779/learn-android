package kevin.le.learnandroid.model;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    public static float pxToSp(float px) {
        return px /  Resources.getSystem().getDisplayMetrics().scaledDensity;
    }
}
