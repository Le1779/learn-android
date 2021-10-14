package kevin.le.learnandroid.model;

import android.content.Context;
import android.content.Intent;

import kevin.le.learnandroid.view.ButtonsActivity;
import kevin.le.learnandroid.view.DeviceListActivity;
import kevin.le.learnandroid.view.LearnBLEActivity;
import kevin.le.learnandroid.view.ScaleActivity;
import kevin.le.learnandroid.view.ShadowImageViewActivity;
import kevin.le.learnandroid.view.SliderActivity;

public class IntentFactory {
    public Intent make(Context context, String activityName) {
        if ("LearnBLEActivity".equals(activityName)) {
            return new Intent(context, LearnBLEActivity.class);
        } else if ("ButtonsActivity".equals(activityName)) {
            return new Intent(context, ButtonsActivity.class);
        } else if ("SliderActivity".equals(activityName)) {
            return new Intent(context, SliderActivity.class);
        } else if ("ScaleActivity".equals(activityName)) {
            return new Intent(context, ScaleActivity.class);
        } else if ("DeviceListActivity".equals(activityName)) {
            return new Intent(context, DeviceListActivity.class);
        } else if ("ShadowImageViewActivity".equals(activityName)) {
            return new Intent(context, ShadowImageViewActivity.class);
        }

        return null;
    }
}
