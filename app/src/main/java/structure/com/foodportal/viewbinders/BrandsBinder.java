package structure.com.foodportal.viewbinders;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.viewbinders.abstracts.RecyclerViewBinder;

public class BrandsBinder extends RecyclerViewBinder<Integer> {
    private int itemHeight = 0;
    private RecyclerItemClickListener itemClickListener;
    public BrandsBinder(RecyclerItemClickListener mProductItemClickListener) {
        super(R.layout.row_item_brands);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Integer entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (itemHeight == 0) {
            DisplayMetrics matrics = context.getResources().getDisplayMetrics();
            itemHeight = (matrics.widthPixels / 3) - (Math.round(context.getResources().getDimension(R.dimen.dp10)) * 3);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(holder.imgBrands.getLayoutParams());
        layoutParams.height = itemHeight;
        holder.imgBrands.setLayoutParams(layoutParams);
        holder.imgBrands.setImageResource(entity);
        holder.itemView.setOnClickListener(view -> {
            if (itemClickListener!=null){
                itemClickListener.onItemViewClicked(entity,position,view.getId());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imgBrands)
        ImageView imgBrands;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
