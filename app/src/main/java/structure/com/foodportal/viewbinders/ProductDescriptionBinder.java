package structure.com.foodportal.viewbinders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.models.ProductDescriptionModel;
import structure.com.foodportal.viewbinders.abstracts.RecyclerViewBinder;

public class ProductDescriptionBinder extends RecyclerViewBinder<ProductDescriptionModel> {
    private int itemHeight = 0;

    public ProductDescriptionBinder() {
        super(R.layout.item_product_condition);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ProductDescriptionModel entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        /*if (itemHeight == 0) {
            DisplayMetrics matrics = context.getResources().getDisplayMetrics();
            itemHeight = (matrics.widthPixels / 2) - (Math.round(context.getResources().getDimension(R.dimen.dp10)) * 2);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(holder.productClassification.getLayoutParams());
        layoutParams.height = itemHeight;
        layoutParams.width = itemHeight;
        holder.productClassification.setLayoutParams(layoutParams);*/
        String sourceString;

        switch (entity.getType()) {
            case 1:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.models, 0, 0);
                sourceString = context.getResources().getString(R.string.model) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 2:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.condition_perfect, 0, 0);
                sourceString = context.getResources().getString(R.string.condition) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 3:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.used_years, 0, 0);
                sourceString = context.getResources().getString(R.string.yearUsed) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 4:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.accessories_original, 0, 0);
                sourceString = context.getResources().getString(R.string.accessories) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;

            case 5:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.kilometers, 0, 0);
                sourceString = context.getResources().getString(R.string.kilometers) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;

            case 6:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.megapixel, 0, 0);
                sourceString = context.getResources().getString(R.string.megapixel) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;

            case 7:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.screensize, 0, 0);
                sourceString = context.getResources().getString(R.string.screensize) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 8:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.colorblack, 0, 0);
                sourceString = context.getResources().getString(R.string.color) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 9:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.smartfeatures, 0, 0);
                String smart = entity.getDescrition().equals("0") ? "No" : "Yes";
                sourceString = context.getResources().getString(R.string.smart_features) + " " + "<b>" + /*entity.getDescrition()*/smart + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 10:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fueltype, 0, 0);
                sourceString = context.getResources().getString(R.string.fueltype) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 11:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.furnituretype, 0, 0);
                sourceString = context.getResources().getString(R.string.type) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 12:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bedroom, 0, 0);
                sourceString = context.getResources().getString(R.string.bedroom) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 13:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bathroom, 0, 0);
                sourceString = context.getResources().getString(R.string.bathroom) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 14:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.warranty, 0, 0);
                sourceString = context.getResources().getString(R.string.warranty) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 15:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.balcony, 0, 0);
                sourceString = context.getResources().getString(R.string.balconies) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 16:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.sellertype, 0, 0);
                sourceString = context.getResources().getString(R.string.sellertype) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 17:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.doors, 0, 0);
                sourceString = context.getResources().getString(R.string.doors) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 18:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.expandable_memory, 0, 0);
                String expand = entity.getDescrition().equals("0") ? "No" : "Yes";
                sourceString = context.getResources().getString(R.string.expandable_memory) + " " + "<b>" + expand + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 19:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.operating_system, 0, 0);
                sourceString = context.getResources().getString(R.string.operating_system) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 20:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.processor, 0, 0);
                sourceString = context.getResources().getString(R.string.processor) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 21:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cellular_type, 0, 0);
                sourceString = context.getResources().getString(R.string.cellulartype) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 22:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.petsallowed, 0, 0);
                sourceString = context.getResources().getString(R.string.pets_allowed) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 23:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cheques, 0, 0);
                sourceString = context.getResources().getString(R.string.total_cheques) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 24:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.totalarea, 0, 0);
                sourceString = context.getResources().getString(R.string.total_area) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
            case 25:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.age, 0, 0);
                sourceString = context.getResources().getString(R.string.age) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
                case 26:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.title, 0, 0);
                sourceString = context.getResources().getString(R.string.title) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
                case 27:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cellular_type, 0, 0);
                sourceString = context.getResources().getString(R.string.cellulartype) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
                case 28:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.make_model, 0, 0);
                sourceString = context.getResources().getString(R.string.make) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;
                case 29:
                holder.productClassification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.brand, 0, 0);
                sourceString = context.getResources().getString(R.string.brand) + " " + "<b>" + entity.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productClassification.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.productClassification.setText(Html.fromHtml(sourceString));
                }
                break;

        }


    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.product_classification)
        TextView productClassification;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
