package kevin.le.learnandroid.view.components.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kevin.le.learnandroid.R;

/**
 * Created by Kevin Le on 2021/11/30.
 */
public class LeSpinnerAdapter extends ArrayAdapter<LeSpinnerPojo> {

    public interface OnSelectListener {
        void onSelected(boolean[] selected);
    }

    private final List<LeSpinnerPojo> data;
    private boolean isMultiSelect = true;
    private OnSelectListener listener;

    public LeSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<LeSpinnerPojo> objects) {
        super(context, resource, objects);
        this.data = objects;
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return generateSpinnerView(convertView);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return generateDropDownView(position, convertView);
    }

    //region Generate View
    private View generateSpinnerView(@Nullable View convertView) {
        View returnView = convertView;
        if (convertView == null) {
            returnView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, null);
            CheckedTextView text = returnView.findViewById(android.R.id.text1);
            text.setText(getSelectedText());
        }

        return returnView;
    }

    private View generateDropDownView(int position, @Nullable View convertView) {
        ViewHolder viewHolder;

        View returnView = convertView;
        if (convertView == null) {
            returnView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_dropdown, null);
            viewHolder = new ViewHolder(returnView);
            returnView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LeSpinnerPojo pojo = data.get(position);
        viewHolder.text.setText(pojo.getText());
        viewHolder.checkBox.setChecked(pojo.isSelected());

        viewHolder.checkBox.setOnClickListener(view -> {
            if (!isMultiSelect) {
                uncheckedAll();
            }

            CheckBox it = (CheckBox) view;
            pojo.setSelected(it.isChecked());
            notifyListener();
            notifyDataSetChanged();
        });

        viewHolder.container.setOnClickListener(view -> viewHolder.checkBox.performClick());

        return returnView;
    }
    //endregion

    private void notifyListener() {
        if (listener == null) {
            return;
        }

        boolean[] selected = new boolean[data.size()];
        for (int i = 0; i < data.size(); i++) {
            selected[i] = data.get(i).isSelected();
        }

        listener.onSelected(selected);
    }

    /**
     * 取得目前選擇的結果描述
     * @return 描述
     */
    private String getSelectedText() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean someSelected = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                stringBuilder.append(data.get(i).getText());
                stringBuilder.append(", ");
                someSelected = true;
            }
        }

        String spinnerText;
        if (someSelected) {
            spinnerText = stringBuilder.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = "Select options";
        }

        return spinnerText;
    }

    /**
     * 取消所有選擇
     */
    private void uncheckedAll() {
        for (LeSpinnerPojo pojo : data) {
            pojo.setSelected(false);
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView text;
        ViewGroup container;

        public ViewHolder(View v) {
            checkBox = v.findViewById(R.id.checkBox_le_spinner);
            text = v.findViewById(R.id.textView_le_spinner);
            container = v.findViewById(R.id.container_le_spinner);
        }
    }
}
