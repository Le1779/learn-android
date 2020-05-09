package kevin.le.learnandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.model.DeviceInfo;


public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ViewHolder>  {

    private Context context;
    private List<DeviceInfo> devices;
    private OnItemClickListener onItemClickListener;
    private ScanResultAdapter.ViewHolder clickViewHolder;
    private boolean isConnecting = false;

    public interface OnItemClickListener{
        void onDeviceClick(String address);
    }

    public ScanResultAdapter(Context context, List<DeviceInfo> devices){
        this.context = context;
        this.devices = devices;
    }

    public void setDevices(List<DeviceInfo> devices){
        this.devices = devices;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ScanResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_device_list, viewGroup, false);
        return new ScanResultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScanResultAdapter.ViewHolder viewHolder, int i) {
        final DeviceInfo deviceInfo = devices.get(i);
        viewHolder.textView_device_name.setText(deviceInfo.getName());
        viewHolder.textView_device_address.setText(deviceInfo.getAddress());
        viewHolder.textView_device_status.setText(String.valueOf(deviceInfo.getRssi()));
        viewHolder.progressBar_connecting.setVisibility(View.GONE);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null && !isConnecting){
                    clickViewHolder = viewHolder;
                    startConnect();
                    onItemClickListener.onDeviceClick(deviceInfo.getAddress());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices == null ? 0 : devices.size();
    }

    public void startConnect(){
        if(clickViewHolder == null){
            return;
        }

        isConnecting = true;
        clickViewHolder.textView_device_status.setText(context.getResources().getString(R.string.connecting));
        clickViewHolder.progressBar_connecting.setVisibility(View.VISIBLE);

    }

    public void stopConnect(){
        if(clickViewHolder == null){
            return;
        }

        isConnecting = false;
        clickViewHolder.textView_device_status.setText("");
        clickViewHolder.progressBar_connecting.setVisibility(View.GONE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_device_name;
        TextView textView_device_address;
        TextView textView_device_status;
        ProgressBar progressBar_connecting;

       ViewHolder(View v) {
            super(v);
           textView_device_name = v.findViewById(R.id.textView_device_name);
           textView_device_address = v.findViewById(R.id.textView_device_address);
           textView_device_status = v.findViewById(R.id.textView_device_status);
           progressBar_connecting = v.findViewById(R.id.progressBar_connecting);
        }
    }
}
