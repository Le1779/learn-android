package kevin.le.learnandroid.view.components.device_list;

import android.content.res.Resources;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.indicator_light.IndicatorLight;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {

    @NonNull
    @Override
    public DeviceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_device_list2, viewGroup, false);
        return new DeviceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceListAdapter.ViewHolder viewHolder, int i) {
        int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        viewHolder.itemView.getLayoutParams().width = (int) (displayWidth * 0.7);

        viewHolder.titleTextView.setText("Le Device " + i);
        viewHolder.typeTextView.setText("Debug Type");
        viewHolder.statusTextView.setText("在連接範圍內");

        viewHolder.infoButton.setOnClickListener(view -> view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView typeTextView;
        ImageButton infoButton;
        IndicatorLight indicatorLight;
        TextView statusTextView;

        ViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.textView_device_list_item_name);
            typeTextView = v.findViewById(R.id.textView_device_list_item_type);
            infoButton = v.findViewById(R.id.imageButton_device_list_item_info);
            indicatorLight = v.findViewById(R.id.indicatorLight);
            statusTextView = v.findViewById(R.id.textView_device_list_item_status);
        }
    }
}
