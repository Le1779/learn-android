package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.button.AlertButton;
import kevin.le.learnandroid.view.components.button.ClockDirectionButton;
import kevin.le.learnandroid.view.components.button.LightPowerButton;
import kevin.le.learnandroid.view.components.button.NaturalWindButton;
import kevin.le.learnandroid.view.components.button.NightLightPowerButton;
import kevin.le.learnandroid.view.components.button.OnOffButton;
import kevin.le.learnandroid.view.components.button.TimerButton;
import kevin.le.learnandroid.view.components.button.UVCButton;
import kevin.le.learnandroid.view.dialog.TimerBottomSheetDialog;

public class ButtonsActivity extends AppCompatActivity {

    private OnOffButton onOffButton;
    private UVCButton uvcButton;
    private LightPowerButton lightPowerButton;
    private NightLightPowerButton nightLightPowerButton;
    private AlertButton alertButton;
    private NaturalWindButton naturalWindButton;
    private ClockDirectionButton clockDirectionButton;
    private TimerButton timerButton;
    private FragmentManager fragmentManager;
    private TimerBottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        onOffButton = findViewById(R.id.onOffButton);
        uvcButton = findViewById(R.id.uvcButton);
        lightPowerButton = findViewById(R.id.lightPowerButton);
        nightLightPowerButton = findViewById(R.id.nightLightPowerButton);
        alertButton = findViewById(R.id.alertButton);
        naturalWindButton = findViewById(R.id.naturalWindButton);
        clockDirectionButton = findViewById(R.id.clockDirectionButton);

        View.OnClickListener listener = getStatusButtonClickListener();
        onOffButton.setOnClickListener(listener);
        uvcButton.setOnClickListener(listener);
        lightPowerButton.setOnClickListener(listener);
        nightLightPowerButton.setOnClickListener(listener);
        alertButton.setOnClickListener(listener);
        naturalWindButton.setOnClickListener(listener);
        clockDirectionButton.setOnClickListener(listener);

        findViewById(R.id.brightnessAndTemperatureButton).setOnClickListener(listener);
        findViewById(R.id.fanButton).setOnClickListener(listener);

        timerButton = findViewById(R.id.timerButton);
        fragmentManager = getSupportFragmentManager();
        dialog = new TimerBottomSheetDialog();
        timerButton.setOnClickListener(view -> {
            dialog.setListener(new TimerBottomSheetDialog.OnTimerChangeListener() {
                @Override
                public void onChange(long startMillis, long endMillis) {
                    timerButton.setTime(startMillis, endMillis);
                }

                @Override
                public void onClean() {
                    timerButton.setTime(0, 0);
                }
            });
            dialog.show(fragmentManager, "TimerBottomSheetDialog");
        });
    }

    private View.OnClickListener getStatusButtonClickListener() {
        return view -> {
            onOffButton.setOn(!onOffButton.isOn());
            uvcButton.setOn(!uvcButton.isOn());
            lightPowerButton.setOn(!lightPowerButton.isOn());
            nightLightPowerButton.setOn(!nightLightPowerButton.isOn());
            alertButton.setOn(!alertButton.isOn());
            naturalWindButton.setOn(!naturalWindButton.isOn());
            clockDirectionButton.setOn(!clockDirectionButton.isOn());
            clockDirectionButton.setClockwise(!clockDirectionButton.isClockwise());
        };
    }
}