package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;

import java.text.ParseException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.customViews.CustomRatingBar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodMyReviewsListener;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Recipe;

public class FoodMyReviewsAdapter extends RecyclerView.Adapter<FoodMyReviewsAdapter.PlanetViewHolder> {

    ArrayList<Recipe> recipes;
    Context context;
    FoodMyReviewsListener foodMyReviewsListener;

    public FoodMyReviewsAdapter(ArrayList<Recipe> recipes, Context context,
                                FoodMyReviewsListener foodMyReviewsListener) {
        this.recipes = recipes;
        this.context = context;
        this.foodMyReviewsListener = foodMyReviewsListener;
    }

    @Override
    public FoodMyReviewsAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_review, parent, false);
        FoodMyReviewsAdapter.PlanetViewHolder viewHolder = new FoodMyReviewsAdapter.PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodMyReviewsAdapter.PlanetViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        if (recipe != null) {
            try {
                UIHelper.setImageWithGlide(context, holder.civRecipe,
                        recipe.getGallery().getPhotos().get(0).getImage_path());
                holder.tvTitle.setText(recipe.getTitle_en());
                holder.tvCreatedAt.setText(Utils.convertTime(recipe.getCreated_at()).toString());
                holder.rtvReview.setText(recipe.getReviews());
                holder.ratingBar.setScore(Float.valueOf(recipe.getAvgRating()));

                ArrayList<CategorySlider> categorySliders = recipe.getCategory_slider();
                FoodMyReviewsCategoriesAdapter adapter = new FoodMyReviewsCategoriesAdapter(categorySliders, position,
                        context, foodMyReviewsListener);
                holder.rvCategories.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL));
                holder.rvCategories.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foodMyReviewsListener.onReviewClick(position);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public void addAllToAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civRecipe;
        private TextView tvTitle, tvCreatedAt;
        private ReadMoreTextView rtvReview;
        private CustomRatingBar ratingBar;
        private RecyclerView rvCategories;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            civRecipe = (CircleImageView) itemView.findViewById(R.id.civRecipe);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            rtvReview = (ReadMoreTextView) itemView.findViewById(R.id.tvReview);
            ratingBar = (CustomRatingBar) itemView.findViewById(R.id.ratingBar);
            rvCategories = (RecyclerView) itemView.findViewById(R.id.rvCategories);
        }
    }
}