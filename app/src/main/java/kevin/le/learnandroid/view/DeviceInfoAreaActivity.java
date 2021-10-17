package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.device_info_area.LightInfoArea;

public class DeviceInfoAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info_area);

        LightInfoArea lightInfoArea = findViewById(R.id.lightInfoArea);
        lightInfoArea.setBrightness(39);
        lightInfoArea.setColorTemperature(70);
    }
}