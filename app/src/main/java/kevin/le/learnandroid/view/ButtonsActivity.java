package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.OnOffButton;
import kevin.le.learnandroid.view.components.UVCButton;

public class ButtonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        findViewById(R.id.fanButton).setOnClickListener(view -> {
            Log.d(this.getClass().getName(), "Fan button click!");
        });

        findViewById(R.id.onOffButton).setOnClickListener(view -> {
            OnOffButton button = (OnOffButton) view;
            button.setOn(!button.isOn());
        });

        findViewById(R.id.uvcButton).setOnClickListener(view -> {
            UVCButton button = (UVCButton) view;
            button.setOn(!button.isOn());
        });
    }
}