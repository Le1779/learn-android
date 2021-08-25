package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.OnOffButton;
import kevin.le.learnandroid.view.components.UVCButton;

public class ButtonsActivity extends AppCompatActivity {

    private OnOffButton onOffButton;
    private UVCButton uvcButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        onOffButton = findViewById(R.id.onOffButton);
        uvcButton = findViewById(R.id.uvcButton);

        View.OnClickListener listener = getStatusButtonClickListener();
        onOffButton.setOnClickListener(listener);
        uvcButton.setOnClickListener(listener);
        findViewById(R.id.brightnessAndTemperatureButton).setOnClickListener(listener);
        findViewById(R.id.fanButton).setOnClickListener(listener);
    }

    private View.OnClickListener getStatusButtonClickListener() {
        return view -> {
            onOffButton.setOn(!onOffButton.isOn());
            uvcButton.setOn(!uvcButton.isOn());
        };
    }
}