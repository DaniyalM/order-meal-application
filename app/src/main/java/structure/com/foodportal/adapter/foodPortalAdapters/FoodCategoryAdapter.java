package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Ingredient;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.PlanetViewHolder> {

    ArrayList<CategorySlider> ingredientList;
    Context context;

    public FoodCategoryAdapter(ArrayList<CategorySlider> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @Override
    public FoodCategoryAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_food, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodCategoryAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.text.setText(""+ingredientList.get(position).getCategory_title_en());
        UIHelper.setImageWithGlide(context,holder.circleImageView,ingredientList.get(position).getGallery().getPhotos().get(0).getImage_path());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text;
        CircleImageView circleImageView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvDishName);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.cvDishView);
        }
    }
}