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

        viewHolder.title.setText("Le Device " + i);
        viewHolder.type.setText("Debug Type");

        viewHolder.infoButton.setOnClickListener(view -> view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView type;
        ImageButton infoButton;

        ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.textView_device_list_item_name);
            type = v.findViewById(R.id.textView_device_list_item_type);
            infoButton = v.findViewById(R.id.imageButton_device_list_item_info);
        }
    }
}
