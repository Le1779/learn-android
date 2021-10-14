package kevin.le.learnandroid.view.components.device_list;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View v) {
            super(v);
        }
    }
}
