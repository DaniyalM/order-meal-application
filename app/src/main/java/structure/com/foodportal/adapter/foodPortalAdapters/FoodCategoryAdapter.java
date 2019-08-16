package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.fragment.foodportal.RecipeFragment;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Ingredient;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.PlanetViewHolder> {

    ArrayList<CategorySlider> ingredientList;
    Context context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;

    public FoodCategoryAdapter(ArrayList<CategorySlider> ingredientList, Context context, FoodHomeListner foodHomeListner) {
        this.ingredientList = ingredientList;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
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
        holder.text.setText("" + getTitleBySelectedLanguage(position, holder.text));
        UIHelper.setImageWithGlide(context, holder.circleImageView, "" + ingredientList.get(position).getSlider_path());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodHomeListner.categorySliderClick(position);

            }
        });
        setAnimation(holder.itemView, position);
    }

    private BasePreferenceHelper preferenceHelper;

    private String getTitleBySelectedLanguage(int position, TextView textView) {
        String title = ingredientList.get(position).getCategory_title_en();
        textView.setTextSize(10);

        if (preferenceHelper != null) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH) {
                textView.setTextSize(10);
                title = ingredientList.get(position).getCategory_title_en();
            } else if (preferenceHelper.getSelectedLanguage() == URDU) {
                textView.setTextSize(14);
                title = ingredientList.get(position).getCategory_title_ur();
            }
        }
        return title;
    }

    public void setPreferenceHelper(BasePreferenceHelper preferenceHelper) {
        this.preferenceHelper = preferenceHelper;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > 0) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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