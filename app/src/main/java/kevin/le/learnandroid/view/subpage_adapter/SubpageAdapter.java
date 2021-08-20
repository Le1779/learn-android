package kevin.le.learnandroid.view.subpage_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kevin.le.learnandroid.R;

public class SubpageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_group_title);
        }
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        TextView title;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.textView_type);
            title = itemView.findViewById(R.id.textView_title);
        }
    }

    private final List<ListItem> items;
    private final OnItemClickListener listener;

    public SubpageAdapter(List<ListItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (viewType == ItemType.GROUP.ordinal()) {
            View view = inflater.inflate(R.layout.item_subpage_group, viewGroup, false);
            return new GroupViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_subpage_child, viewGroup, false);
            return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == ItemType.GROUP.ordinal()) {
            GroupViewHolder holder = (GroupViewHolder) viewHolder;
            GroupItem item = (GroupItem) items.get(i);
            holder.title.setText(item.getName());
        } else {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            final ChildItem item = (ChildItem) items.get(i);
            holder.type.setText(item.getSubpageData().getType());
            holder.title.setText(item.getSubpageData().getTitle());
            holder.itemView.setOnClickListener(view -> listener.onClick(item.getSubpageData().getName()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
