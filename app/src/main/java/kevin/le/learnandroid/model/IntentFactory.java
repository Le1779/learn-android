package kevin.le.learnandroid.model;

import android.content.Context;
import android.content.Intent;

import kevin.le.learnandroid.view.ButtonsActivity;
import kevin.le.learnandroid.view.DeviceInfoAreaActivity;
import kevin.le.learnandroid.view.DeviceListActivity;
import kevin.le.learnandroid.view.LearnBLEActivity;
import kevin.le.learnandroid.view.NFCActivity;
import kevin.le.learnandroid.view.ScaleActivity;
import kevin.le.learnandroid.view.ShadowImageViewActivity;
import kevin.le.learnandroid.view.SliderActivity;
import kevin.le.learnandroid.view.SpinnerActivity;
import kevin.le.learnandroid.view.StringConditionActivity;
import kevin.le.learnandroid.view.WebViewActivity;

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
        } else if ("DeviceInfoAreaActivity".equals(activityName)) {
            return new Intent(context, DeviceInfoAreaActivity.class);
        } else if ("WebViewActivity".equals(activityName)) {
            return new Intent(context, WebViewActivity.class);
        } else if ("StringConditionActivity".equals(activityName)) {
            return new Intent(context, StringConditionActivity.class);
        } else if ("SpinnerActivity".equals(activityName)) {
            return new Intent(context, SpinnerActivity.class);
        } else if ("NFCActivity".equals(activityName)) {
            return new Intent(context, NFCActivity.class);
        }

        return null;
    }
}
