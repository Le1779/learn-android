package kevin.le.learnandroid.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import kevin.le.learnandroid.R;

public class TimerBottomSheetDialog extends BottomSheetDialogFragment {
    private TimePicker startTimePicker, endTimePicker;

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

    private void init(View view) {
        startTimePicker = view.findViewById(R.id.timePicker_start);
        endTimePicker = view.findViewById(R.id.timePicker_end);

        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
    }
}
