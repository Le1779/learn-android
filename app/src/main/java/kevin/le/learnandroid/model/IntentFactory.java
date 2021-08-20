package kevin.le.learnandroid.model;

import android.content.Context;
import android.content.Intent;

import kevin.le.learnandroid.view.ButtonsActivity;
import kevin.le.learnandroid.view.LearnBLEActivity;

public class IntentFactory {
    public Intent make(Context context, String activityName) {
        if ("LearnBLEActivity".equals(activityName)) {
            return new Intent(context, LearnBLEActivity.class);
        } else if ("ButtonsActivity".equals(activityName)) {
            return new Intent(context, ButtonsActivity.class);
        }

        return null;
    }
}
