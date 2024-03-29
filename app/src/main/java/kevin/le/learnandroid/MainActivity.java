package kevin.le.learnandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kevin.le.learnandroid.model.IntentFactory;
import kevin.le.learnandroid.model.SubpageData;
import kevin.le.learnandroid.view.EditTextActivity;
import kevin.le.learnandroid.view.NFCActivity;
import kevin.le.learnandroid.view.SliderActivity;
import kevin.le.learnandroid.view.SpinnerActivity;
import kevin.le.learnandroid.view.StringConditionActivity;
import kevin.le.learnandroid.view.subpage_adapter.ChildItem;
import kevin.le.learnandroid.view.subpage_adapter.GroupItem;
import kevin.le.learnandroid.view.subpage_adapter.ListItem;
import kevin.le.learnandroid.view.subpage_adapter.OnItemClickListener;
import kevin.le.learnandroid.view.subpage_adapter.SubpageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        startActivity(new Intent(this, SliderActivity.class));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_subpage);
        recyclerView.setAdapter(getAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private SubpageAdapter getAdapter() {
        return new SubpageAdapter(getItems(), getListener());
    }

    private OnItemClickListener getListener() {
        return name -> {
            Intent intent = new IntentFactory().make(this, name);
            if (intent != null) {
                startActivity(intent);
            }
        };
    }

    private List<ListItem> getItems() {
        List<ListItem> items = new ArrayList<>();
        items.add(new GroupItem("Wireless communication"));
        items.add(new ChildItem(new SubpageData("LearnBLEActivity", "Feature", "Learn BLE")));
        items.add(new ChildItem(new SubpageData("NFCActivity", "Feature", "Learn NFC")));

        items.add(new GroupItem("UI"));
        items.add(new ChildItem(new SubpageData("ButtonsActivity", "View", "Buttons")));
        items.add(new ChildItem(new SubpageData("EditTextActivity", "View", "EditText")));
        items.add(new ChildItem(new SubpageData("SliderActivity", "View", "Slider")));
        items.add(new ChildItem(new SubpageData("ScaleActivity", "View", "Scale")));
        items.add(new ChildItem(new SubpageData("ShadowImageViewActivity", "View", "Shadow ImageView")));
        items.add(new ChildItem(new SubpageData("DeviceListActivity", "View", "Device List")));
        items.add(new ChildItem(new SubpageData("DeviceInfoAreaActivity", "View", "Device Info Area")));
        items.add(new ChildItem(new SubpageData("WebViewActivity", "View", "WebView")));
        items.add(new ChildItem(new SubpageData("SpinnerActivity", "View", "Spinner")));

        items.add(new GroupItem("Misc"));
        items.add(new ChildItem(new SubpageData("StringConditionActivity", "Feature", "String Condition")));
        return items;
    }
}
