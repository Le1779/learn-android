package kevin.le.learnandroid.view.components.auto_complete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kevin.le.learnandroid.R;

/**
 * Created by Kevin Le on 2021/12/22.
 */
public class LeAutoCompleteTextViewAdapter extends ArrayAdapter<String> {

    private final List<String> items;
    private final ListFilter listFilter = new ListFilter();
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnDeleteButtonClickListener {
        void onClick(String deleteItem);
    }

    public LeAutoCompleteTextViewAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.items = new ArrayList<>(objects);
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener onDeleteButtonClickListener) {
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.auto_complete_dropdown, parent, false);
        }

        String text = this.getItem(position);
        TextView textView = convertView.findViewById(R.id.textView_auto_complete);
        textView.setText(text);

        ImageButton imageButton = convertView.findViewById(R.id.imageButton_auto_complete);
        imageButton.setOnClickListener(view -> {
            remove(text);
            items.remove(text);
            notifyDataSetChanged();
            if (onDeleteButtonClickListener != null) {
                onDeleteButtonClickListener.onClick(text);
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        @Override
        public String convertResultToString(Object resultValue) {
            return (String) resultValue;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            if (prefix != null) {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                ArrayList<String> matchValues = new ArrayList<>();
                for (String dataItem : items) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = matchValues;
                filterResults.count = matchValues.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                return;
            }

            clear();
            addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    }
}
