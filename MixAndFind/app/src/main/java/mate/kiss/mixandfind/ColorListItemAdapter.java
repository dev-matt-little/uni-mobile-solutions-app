package mate.kiss.mixandfind;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ColorListItemAdapter extends RecyclerView.Adapter<ColorListItemAdapter.ViewHolder> {

    public List<ColorListItem> colorList;

    public ColorListItemAdapter(List<ColorListItem> colorList) {
        this.colorList = colorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_list_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getLabel().setText(colorList.get(position).name);
        viewHolder.getLayout().setBackgroundColor(Color.parseColor(colorList.get(position).colorCode));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView label;
        private final ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.itemLayout);
            label = view.findViewById(R.id.label);

            Button deleteButton = view.findViewById(R.id.deleteItemButton);
            deleteButton.setOnClickListener(v -> {
                colorList.remove((getAdapterPosition()));
                notifyItemRemoved(getAdapterPosition());
            });
        }

        public TextView getLabel() {
            return label;
        }

        public ConstraintLayout getLayout() {
            return layout;
        }
    }
}
