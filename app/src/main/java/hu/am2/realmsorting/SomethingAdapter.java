package hu.am2.realmsorting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SomethingAdapter extends RecyclerView.Adapter<SomethingAdapter.SomeView> {

    private List<Something> somethingList = new ArrayList<>();

    @NonNull
    @Override
    public SomeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.something_item, parent, false);
        return new SomeView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SomeView holder, int position) {
        holder.name.setText(somethingList.get(position).getSomeName());
    }

    @Override
    public int getItemCount() {
        return somethingList.size();
    }

    public void setSomethingList(List<Something> somethingList) {
        this.somethingList = somethingList;
        notifyDataSetChanged();
    }

    public class SomeView extends RecyclerView.ViewHolder {

        private final TextView name;

        public SomeView(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
