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
import kevin.le.learnandroid.view.ScaleActivity;
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
        startActivity(new Intent(this, ScaleActivity.class));
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
        items.add(new GroupItem("Bluetooth"));
        items.add(new ChildItem(new SubpageData("LearnBLEActivity", "Feature", "Learn BLE")));

        items.add(new GroupItem("UI"));
        items.add(new ChildItem(new SubpageData("ButtonsActivity", "View", "Buttons")));
        items.add(new ChildItem(new SubpageData("SliderActivity", "View", "Slider")));
        items.add(new ChildItem(new SubpageData("ScaleActivity", "View", "Scale")));
        return items;
    }
}
