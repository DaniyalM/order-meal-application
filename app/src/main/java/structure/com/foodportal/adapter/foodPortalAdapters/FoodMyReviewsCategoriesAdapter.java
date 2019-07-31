package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodMyReviewsListener;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Recipe;

public class FoodMyReviewsCategoriesAdapter extends RecyclerView.Adapter<FoodMyReviewsCategoriesAdapter.PlanetViewHolder> {

    ArrayList<CategorySlider> categories;
    int reviewPosition;
    Context context;
    FoodMyReviewsListener foodMyReviewsListener;

    public FoodMyReviewsCategoriesAdapter(ArrayList<CategorySlider> categories, int reviewPosition, Context context,
                                          FoodMyReviewsListener foodMyReviewsListener) {
        this.reviewPosition = reviewPosition;
        this.categories = categories;
        this.context = context;
        this.foodMyReviewsListener = foodMyReviewsListener;
    }

    @Override
    public FoodMyReviewsCategoriesAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip_new, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodMyReviewsCategoriesAdapter.PlanetViewHolder holder, int position) {
        holder.chip.setText(categories.get(position).getCategory_title_en());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodMyReviewsListener.onCategoryClick(reviewPosition, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        private TextView chip;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            chip = (TextView) itemView.findViewById(R.id.chip);
        }
    }
}