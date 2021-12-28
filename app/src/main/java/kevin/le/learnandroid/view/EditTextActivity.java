package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.auto_complete.LeAutoCompleteTextView;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());

        LeAutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        List<String> list = new ArrayList<>();
        list.add("Kevin");
        list.add("kevin le");
        list.add("Le");
        list.add("Andy");
        autoCompleteTextView.setData(list);
        autoCompleteTextView.setCallback(list::remove);
    }
}