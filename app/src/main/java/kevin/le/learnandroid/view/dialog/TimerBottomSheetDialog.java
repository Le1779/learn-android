package kevin.le.learnandroid.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import kevin.le.learnandroid.R;

public class TimerBottomSheetDialog extends BottomSheetDialogFragment {

    public interface OnTimerChangeListener {
        void onChange(long startMillis, long endMillis);
        void onClean();
    }

    private TimePicker startTimePicker, endTimePicker;
    private OnTimerChangeListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_timer_bottom_sheet, null);
        init(view);
        dialog.setContentView(view);
        return dialog;
    }

    public void setListener(OnTimerChangeListener listener) {
        this.listener = listener;
    }

    private void init(View view) {
        initTimePicker(view);
        initButtons(view);
    }

    private void initTimePicker(View view) {
        startTimePicker = view.findViewById(R.id.timePicker_start);
        endTimePicker = view.findViewById(R.id.timePicker_end);

        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
    }

    private void initButtons(View view) {
        Button confirmButton = view.findViewById(R.id.button_confirm);
        Button cleanButton = view.findViewById(R.id.button_clean);

        confirmButton.setOnClickListener(v -> {
            if (listener != null) {
                long startMillis = getMillisecondsFromTimePicker(startTimePicker);
                long endMillis = getMillisecondsFromTimePicker(endTimePicker);
                listener.onChange(startMillis, endMillis);
                this.dismiss();
            }
        });

        cleanButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClean();
                this.dismiss();
            }
        });
    }

    private long getMillisecondsFromTimePicker(TimePicker timePicker) {
        return (timePicker.getHour() * 3600 + timePicker.getMinute() * 60) * 1000;
    }
}
