package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.shadow.ShadowImageView;

public class ShadowImageViewActivity extends AppCompatActivity {

    private int times = 0;
    private ShadowImageView shadowImageView;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            shadowImageView.setImageResource(
                    times++ % 2 == 0 ?
                            R.drawable.support_device_debug_group :
                            R.drawable.support_device_debug_fan)
            ;
            handler.postDelayed(this, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_image_view);

        shadowImageView = findViewById(R.id.shadowImageView);
        runnable.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}