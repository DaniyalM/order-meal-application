package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.Recipe;
import structure.com.foodportal.models.foodModels.SavedRecipe;

public class FoodRecipeAdapter extends RecyclerView.Adapter<FoodRecipeAdapter.PlanetViewHolder> {

    //    ArrayList<SavedRecipe> sections;
    ArrayList<Recipe> recipes;
    Context context;
    SubCategoryListner subCategoryListner;
    private int lastPosition = -1;

//    public FoodRecipeAdapter(ArrayList<SavedRecipe> sections, Context context, SubCategoryListner subCategoryListner) {
//        this.sections = sections;
//        this.context = context;
//        this.subCategoryListner = subCategoryListner;
//    }

    public FoodRecipeAdapter(ArrayList<Recipe> recipes, Context context, SubCategoryListner subCategoryListner) {
        this.recipes = recipes;
        this.context = context;
        this.subCategoryListner = subCategoryListner;
    }

    @Override
    public FoodRecipeAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent, false);
        FoodRecipeAdapter.PlanetViewHolder viewHolder = new FoodRecipeAdapter.PlanetViewHolder(v);
        return viewHolder;
    }

    private void setScaleAnimation(View view, int position) {
        if (position > 0) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
            lastPosition = position;
        }
    }

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    @Override
    public void onBindViewHolder(FoodRecipeAdapter.PlanetViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        if (recipe != null) {
            holder.text.setText(recipe.getTitle_en());

            UIHelper.setImageWithGlide(context, holder.circleImageView,
                    recipe.getGallery().getPhotos().get(0).getImage_path());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subCategoryListner.onSubCategoryClick(position);
                }
            });
        }

        setScaleAnimation(holder.itemView, position);
    }

//    @Override
//    public void onBindViewHolder(FoodRecipeAdapter.PlanetViewHolder holder, int position) {
//        //  holder.image.setImageResource(R.drawable.planetimage);
//        // Log.d("FOODLIST", ": "+position+ " "+ sections.get(position).getStory().getRecipe_type());
//        if (sections.get(position).getStories() == null && sections.get(position).getStory() == null) {
//
//
//        } else {
//
//            if (sections.get(position).getStory() != null) {
//
//                if (sections.get(position).getStory().getRecipe_type().equals("recipe")) {
//                    holder.text.setText(sections.get(position).getStories() != null ? sections.get(position).getStories().getTitle_en()
//                            : sections.get(position).getStory().getTitle_en());
//                    UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getStories() != null
//                            ? sections.get(position).getStories().getGallery().getPhotos().get(0).getImage_path() :
//                            sections.get(position).getStory().getGallery().getPhotos().get(0).getImage_path());
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            subCategoryListner.onSubCategoryClick(position);
//
//                        }
//                    });
//                } else {
//                    //  holder.itemView.setVisibility(View.GONE);
//
//                }
//
//            } else {
//
//                holder.text.setText(sections.get(position).getStories() != null ? sections.get(position).getStories().getTitle_en()
//                        : sections.get(position).getStory().getTitle_en());
//                UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getStories() != null
//                        ? sections.get(position).getStories().getGallery().getPhotos().get(0).getImage_path() :
//                        sections.get(position).getStory().getGallery().getPhotos().get(0).getImage_path());
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        subCategoryListner.onSubCategoryClick(position);
//
//                    }
//                });
//
//            }
//
//
//            setScaleAnimation(holder.itemView, position);
//
//
//        }
//
//
//    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > 0) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


//    @Override
//    public int getItemCount() {
//        return sections.size();
//    }
//
//    public void addAllToAdapter(ArrayList<SavedRecipe> categorySliderWrapper) {
//        this.sections = categorySliderWrapper;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void addAllToAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView circleImageView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tv_subcategory);
            circleImageView = (ImageView) itemView.findViewById(R.id.iv_subcategory);
        }
    }
}