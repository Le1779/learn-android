package kevin.le.learnandroid.model;

import android.content.Context;
import android.content.Intent;

import kevin.le.learnandroid.view.ButtonsActivity;
import kevin.le.learnandroid.view.LearnBLEActivity;
import kevin.le.learnandroid.view.ScaleActivity;
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
        }

        return null;
    }
}
