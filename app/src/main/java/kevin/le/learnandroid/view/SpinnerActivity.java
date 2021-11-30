package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.spinner.LeSpinner;

public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());

        TextView result1TextView = findViewById(R.id.textView_spinner_result_1);
        TextView result2TextView = findViewById(R.id.textView_spinner_result_2);

        String[] data = new String[] {"Kevin", "Le", "Min"};

        LeSpinner leSpinner = findViewById(R.id.leSpinner);
        leSpinner.setData(data);
        leSpinner.setListener(selected -> {
            String text = "";
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    text += data[i] + " ";
                }
            }

            result1TextView.setText(text);
        });

        LeSpinner singleSpinner = findViewById(R.id.leSpinner_single);
        singleSpinner.setData(data);
        singleSpinner.setMultiSelect(false);
        singleSpinner.setListener(selected -> {
            String text = "";
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    text += data[i] + " ";
                }
            }

            result2TextView.setText(text);
        });
    }
}