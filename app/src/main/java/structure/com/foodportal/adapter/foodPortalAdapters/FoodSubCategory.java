package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodSubCategory  extends RecyclerView.Adapter<FoodSubCategory.PlanetViewHolder> {

    ArrayList<CategorySlider> sections;
    Context context;
    SubCategoryListner subCategoryListner;
    private int lastPosition = -1;
    public FoodSubCategory(ArrayList<CategorySlider> sections, Context context, SubCategoryListner subCategoryListner) {
        this.sections = sections;
        this.context = context;
        this.subCategoryListner = subCategoryListner;
    }

    @Override
    public FoodSubCategory.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent, false);
        FoodSubCategory.PlanetViewHolder viewHolder = new FoodSubCategory.PlanetViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(FoodSubCategory.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);


        holder.text.setText(sections.get(position).getCategory_title_en()== null ? sections.get(position).getTitle_en():sections.get(position).getCategory_title_en());
        if(sections.get(position).getSlider_image()!=null ||sections.get(position).getSlider_thumb_image()!=null){

            UIHelper.setImageWithGlide(context,holder.circleImageView,  sections.get(position).getSlider_image()!=null? AppConstant.BASE_URL_IMAGE+sections.get(position).getSlider_image(): AppConstant.BASE_URL_IMAGE+sections.get(position).getSlider_thumb_image());

        }else{

            UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getGallery().getPhotos().get(0).getImage_path());

        }

        //UIHelper.setImageWithGlide(context,holder.circleImageView,sections.size()>0 ? sections.get(position).getGallery().getPhotos().get(0).getImage_path() : null);
            // setAnimation(holder.itemView, position);

             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                subCategoryListner.onSubCategoryClick(position);

                 }
             });


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

    public void addAll(ArrayList<CategorySlider> categorySliderWrapper) {
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