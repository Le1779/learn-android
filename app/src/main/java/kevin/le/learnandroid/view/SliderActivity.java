package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.circular_slider.CircularSlider;
import kevin.le.learnandroid.view.components.circular_slider.OnCircularSliderChangeListener;

public class SliderActivity extends AppCompatActivity implements OnCircularSliderChangeListener {

    private TextView sliderValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());

        sliderValueTextView = findViewById(R.id.textView_slider_value);

        ((CircularSlider) findViewById(R.id.centerSlider)).setOnCircularSliderChangeListener(this);
        ((CircularSlider) findViewById(R.id.leftSlider)).setOnCircularSliderChangeListener(this);
        ((CircularSlider) findViewById(R.id.rightSlider)).setOnCircularSliderChangeListener(this);
        ((CircularSlider) findViewById(R.id.topSlider)).setOnCircularSliderChangeListener(this);
        ((CircularSlider) findViewById(R.id.bottomSlider)).setOnCircularSliderChangeListener(this);
    }

    @Override
    public void onValueChange(CircularSlider circularSlider, float value) {
        String name = "";
        int id = circularSlider.getId();
        if (id == R.id.centerSlider) {
            name = "Center Slider";
        } else if (id == R.id.leftSlider) {
            name = "Left Slider";
        } else if (id == R.id.rightSlider) {
            name = "Right Slider";
        } else if (id == R.id.topSlider) {
            name = "Top Slider";
        } else if (id == R.id.bottomSlider) {
            name = "Bottom Slider";
        }

        sliderValueTextView.setText(name + ", Value: " + value);
    }
}