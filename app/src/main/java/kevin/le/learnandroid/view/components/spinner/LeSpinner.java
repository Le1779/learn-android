package kevin.le.learnandroid.view.components.spinner;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Le on 2021/11/29.
 */
public class LeSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    private LeSpinnerAdapter adapter;
    private boolean isMultiSelect = true;

    public LeSpinner(@NonNull Context context) {
        super(context);
    }

    public LeSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(String[] data) {
        List<LeSpinnerPojo> spinnerPojoList = new ArrayList<>();
        for (String text : data) {
            spinnerPojoList.add(new LeSpinnerPojo(false, text));
        }

        adapter = new LeSpinnerAdapter(getContext(), 0, spinnerPojoList);
        adapter.setMultiSelect(isMultiSelect);
        setAdapter(adapter);
    }

    public void setMultiSelect(boolean isMultiSelect) {
        this.isMultiSelect = isMultiSelect;
        if (adapter != null) {
            adapter.setMultiSelect(isMultiSelect);
        }
    }

    public void setListener(LeSpinnerAdapter.OnSelectListener listener) {
        if (adapter != null) {
            adapter.setListener(listener);
        }
    }
}
