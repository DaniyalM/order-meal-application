package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.SavedRecipe;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodSavedRecipeAdapter  extends RecyclerView.Adapter<FoodSavedRecipeAdapter.PlanetViewHolder> {

    ArrayList<SavedRecipe> sections;
    Context context;
    SubCategoryListner subCategoryListner;
    private int lastPosition = -1;
    public FoodSavedRecipeAdapter(ArrayList<SavedRecipe> sections, Context context, SubCategoryListner subCategoryListner) {
        this.sections = sections;
        this.context = context;
        this.subCategoryListner = subCategoryListner;
    }

    @Override
    public FoodSavedRecipeAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent, false);
        FoodSavedRecipeAdapter.PlanetViewHolder viewHolder = new FoodSavedRecipeAdapter.PlanetViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(FoodSavedRecipeAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
       // Log.d("FOODLIST", ": "+position+ " "+ sections.get(position).getStory().getRecipe_type());
        if(sections.get(position).getStories()==null && sections.get(position).getStory() ==null ){




        }

        else{

                if(sections.get(position).getStory() !=null ){

                    if(sections.get(position).getStory().getRecipe_type().equals("recipe")){
                        holder.text.setText(sections.get(position).getStories()!=null ?sections.get(position).getStories().getTitle_en()
                                : sections.get(position).getStory().getTitle_en());
                        UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getStories()!=null
                                ? sections.get(position).getStories().getGallery().getPhotos().get(0).getImage_path():
                                sections.get(position).getStory().getGallery().getPhotos().get(0).getImage_path());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                subCategoryListner.onSubCategoryClick(position);

                            }
                        });
                    }else{
                      //  holder.itemView.setVisibility(View.GONE);

                    }

                }else{

                    holder.text.setText(sections.get(position).getStories()!=null ?sections.get(position).getStories().getTitle_en()
                            : sections.get(position).getStory().getTitle_en());
                    UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getStories()!=null
                            ? sections.get(position).getStories().getGallery().getPhotos().get(0).getImage_path():
                            sections.get(position).getStory().getGallery().getPhotos().get(0).getImage_path());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            subCategoryListner.onSubCategoryClick(position);

                        }
                    });

                }




            // setAnimation(holder.itemView, position);




        }




    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > 0)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return sections.size();
    }

    public void addAll(ArrayList<SavedRecipe> categorySliderWrapper) {
        this.sections =categorySliderWrapper;
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