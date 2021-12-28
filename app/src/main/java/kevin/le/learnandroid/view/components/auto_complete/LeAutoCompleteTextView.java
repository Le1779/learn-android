package kevin.le.learnandroid.view.components.auto_complete;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by Kevin Le on 2021/12/22.
 */
public class LeAutoCompleteTextView extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    private Callback callback;

    public interface Callback {
        void onItemDelete(String value);
    }

    public LeAutoCompleteTextView(Context context) {
        super(context);
    }

    public LeAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(List<String> data) {
        LeAutoCompleteTextViewAdapter adapter = new LeAutoCompleteTextViewAdapter(getContext(), 0, data);
        adapter.setOnDeleteButtonClickListener(deleteItem -> {
            if (callback != null) {
                callback.onItemDelete(deleteItem);
            }
        });
        setAdapter(adapter);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
