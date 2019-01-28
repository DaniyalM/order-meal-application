package structure.com.foodportal.viewbinders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.viewbinders.abstracts.RecyclerViewBinder;

public class SideMenuBinder extends RecyclerViewBinder<String> {
    private RecyclerItemClickListener itemClickListener;

    public SideMenuBinder(RecyclerItemClickListener itemClickListener) {
        super(R.layout.row_item_nav);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {
        ViewHolder itemViewHolder = (ViewHolder) viewHolder;
        itemViewHolder.txtHome.setText(entity);
        itemViewHolder.itemView.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.onItemViewClicked(entity, position, view.getId());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_home)
        TextView txtHome;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
