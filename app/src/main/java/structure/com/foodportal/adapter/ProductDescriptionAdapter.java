package structure.com.foodportal.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.models.ProductDescriptionModel;

/**
 * Created by Addi.
 */
public class ProductDescriptionAdapter extends RecyclerView.Adapter<ProductDescriptionAdapter.VH> {
    private final MainActivity mainActivity;
    private final LayoutInflater inflater;
    ArrayList<ProductDescriptionModel> productDescriptionModels;

    public ProductDescriptionAdapter(MainActivity mainActivity, ArrayList<ProductDescriptionModel> productDescriptionModels) {
        this.productDescriptionModels = productDescriptionModels;
        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product_condition, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemCount() {
        return productDescriptionModels.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ProductDescriptionModel model = productDescriptionModels.get(position);
        String sourceString;

        switch (model.getType()) {
            case 1:
                holder.description.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.conditionperfect, 0, 0);
                sourceString = mainActivity.getString(R.string.model) + " " + "<b>" + model.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.description.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.description.setText(Html.fromHtml(sourceString));
                }
                break;
            case 2:
                holder.description.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.conditionperfect, 0, 0);
                sourceString = mainActivity.getString(R.string.condition) + " " + "<b>" + model.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.description.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.description.setText(Html.fromHtml(sourceString));
                }
                break;
            case 3:
                holder.description.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.used_logo, 0, 0);
                sourceString = mainActivity.getString(R.string.yearUsed) + " " + "<b>" + model.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.description.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.description.setText(Html.fromHtml(sourceString));
                }
                break;
            case 4:
                holder.description.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.used_logo, 0, 0);
                sourceString = mainActivity.getString(R.string.accessories) + " " + "<b>" + model.getDescrition() + "</b> ";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.description.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    holder.description.setText(Html.fromHtml(sourceString));
                }
                break;

        }


    }

    class VH extends RecyclerView.ViewHolder {


        TextView description;


        public VH(View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.product_classification);


        }
    }
}
