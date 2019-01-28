package structure.com.foodportal.viewbinders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.models.AllCategory;
import structure.com.foodportal.viewbinders.abstracts.RecyclerViewBinder;

public class CategoryBinder extends RecyclerViewBinder<AllCategory> {
    private RecyclerItemClickListener itemClickListener;
    private ImageLoader imageLoader;
    public CategoryBinder(RecyclerItemClickListener itemClickListener) {
        super(R.layout.row_item_categories);
        this.itemClickListener = itemClickListener;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(AllCategory entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.txtProductCatogory.setText(entity.getTitle());
        imageLoader.displayImage(entity.getCategoryImage(),holder.imgBrands);
        holder.txtProductCatogory.setSelected(true);

        holder.itemView.setOnClickListener(view -> itemClickListener.onItemViewClicked(entity,position,view.getId()));

    }



    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imgBrands)
        ImageView imgBrands;
        @BindView(R.id.txtProductCatogory)
        TextView txtProductCatogory;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
