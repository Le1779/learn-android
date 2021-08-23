package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import kevin.le.learnandroid.R;

public class ButtonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        findViewById(R.id.shadowButton).setOnClickListener(view -> {
            Log.d(this.getClass().getName(), "Button click!");
        });
    }
}