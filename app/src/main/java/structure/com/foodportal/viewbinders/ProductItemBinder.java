package structure.com.foodportal.viewbinders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.viewbinders.abstracts.RecyclerViewBinder;

public class ProductItemBinder extends RecyclerViewBinder<ProductModelAPI> {
    private MainActivity mainActivity;
    private RecyclerItemClickListener itemClickListener;
    private ImageLoader imageLoader;
    private boolean isMyProduct;

    public ProductItemBinder(MainActivity mainActivity, RecyclerItemClickListener itemClickListener) {
        super(R.layout.row_item_product);
        this.mainActivity = mainActivity;
        this.itemClickListener = itemClickListener;
        this.imageLoader = ImageLoader.getInstance();

    }

    public ProductItemBinder(MainActivity mainActivity, RecyclerItemClickListener itemClickListener, boolean isMyProduct) {
        super(R.layout.row_item_product);
        this.mainActivity = mainActivity;
        this.itemClickListener = itemClickListener;
        this.imageLoader = ImageLoader.getInstance();
        this.isMyProduct = isMyProduct;

    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ProductModelAPI entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (entity.getIs_view() == 1) {
            holder.txtTagFeature.setVisibility(View.INVISIBLE);
        } else {
            holder.txtTagFeature.setVisibility(View.VISIBLE);
        }

        if (entity.getIsFavourite() == 1) {
            holder.btnFavorite.setChecked(true);
        } else {
            holder.btnFavorite.setChecked(false);
        }

        if (mainActivity.prefHelper.getLoginStatus()) {
            if (mainActivity.prefHelper.getUser() != null) {
                if (entity.getUserId() == mainActivity.prefHelper.getUser().getId()) {
                    holder.txtTagFeature.setVisibility(View.INVISIBLE);
                    holder.btnFavorite.setVisibility(View.INVISIBLE);
                }
            }
        }

        if (entity.getProductImages().size() > 0)
            imageLoader.displayImage(entity.getProductImages().get(0).getProductImage(), holder.imgProductImage);

        holder.txtProductBrand.setText(entity.getBrand());
        holder.txtProductName.setText(entity.getTitle());
        holder.txtProductFeature.setText(entity.getProductCategory()!=null ? entity.getProductCategory().getTitle() : "N/A");
        holder.txtProductPostedTime.setText(UIHelper.getFormattedDate(entity.getUpdatedAt(), AppConstant.SERVER_DATE_FORMAT, "d MMM yyyy"));
        holder.txtProductPrice.setText("AED " + entity.getAmount());
        /*
        if (entity.getProductRentType() != null) {
            if (entity.getProductRentType().size() > 0) {
                holder.txtProductRentType.setText(entity.getProductRentType().get(0).getName());
            }
        }
        */
        if (entity.getProductOn() == AppConstant.ProductOn.SALE) {
            holder.txtProductRentType.setVisibility(View.GONE);
        }
        holder.txtProductOwnerName.setText(entity.getUser_name());
        holder.itemView.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.onItemViewClicked(entity, position, view.getId());
            }
        });

        holder.btnFavorite.setOnClickListener(view -> {
            if (!mainActivity.prefHelper.getLoginStatus()) {
                UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.guest_favorite_unavailable));
            } else {
                if (itemClickListener != null) {
                    itemClickListener.onItemViewClicked(entity, position, view.getId());
                }
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtTagFeature)
        TextView txtTagFeature;
        @BindView(R.id.btnFavorite)
        CheckBox btnFavorite;
        @BindView(R.id.imgProductImage)
        ImageView imgProductImage;
        @BindView(R.id.txtProductBrand)
        TextView txtProductBrand;
        @BindView(R.id.txtProductName)
        TextView txtProductName;
        @BindView(R.id.txtProductFeature)
        TextView txtProductFeature;
        @BindView(R.id.txtProductPrice)
        TextView txtProductPrice;
        @BindView(R.id.txtProductRentType)
        TextView txtProductRentType;
        @BindView(R.id.txtProductPostedTime)
        TextView txtProductPostedTime;
        @BindView(R.id.txtProductOwnerName)
        TextView txtProductOwnerName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
