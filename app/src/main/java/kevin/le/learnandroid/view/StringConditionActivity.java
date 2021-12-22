package kevin.le.learnandroid.view;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kevin.le.learnandroid.R;

public class StringConditionActivity extends AppCompatActivity {

    private EditText conditionEditText, valueEditText;
    private Button evaluateButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_condition);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());

        conditionEditText = findViewById(R.id.editText_condition);
        valueEditText = findViewById(R.id.editText_value);
        evaluateButton = findViewById(R.id.button_evaluate_condition);
        resultTextView = findViewById(R.id.textView_result);

        evaluateButton.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
            String condition = conditionEditText.getText().toString();
            String value = valueEditText.getText().toString();
            boolean result = evaluate(condition, value);
            resultTextView.setText(result ? "True" : "False");
        });
    }

    private boolean evaluate(String condition, String value) {
        value = "\"" + value + "\"";
        Log.d(this.getClass().getName(), "Condition: " + condition + ", value: " + value);

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");

        try {
            engine.eval("value = " + value);
            return (Boolean) engine.eval(condition);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return false;
    }
}