package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;

import java.util.ArrayList;

import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodFeaturedAdapter extends RecyclerView.Adapter<FoodFeaturedAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    Context context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;
    public FoodFeaturedAdapter(ArrayList<Sections> sections, Context context,FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodFeaturedAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_featured, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(FoodFeaturedAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);


        if(sections.get(position).getFeature_type_id()==1){

         holder.tvPopularRecipeServes.setVisibility(View.VISIBLE);
            holder.tvPopularRecipeCookingTime.setVisibility(View.VISIBLE);
            holder.tvPopularRecipeServes.setText(sections.get(position).getServing_for()+" person(s)");
            holder.tvPopularRecipeCookingTime.setText(sections.get(position).getCook_time());


        }else{

           holder.tvPopularRecipeServes.setVisibility(View.GONE);
            holder.tvPopularRecipeCookingTime.setVisibility(View.GONE);

        }

        holder.text.setText(""+sections.get(position).getTitle());
        if(sections.get(position).getFeatured_image_path()!=null){

            UIHelper.setImageWithGlide(context,holder.circleImageView, sections.get(position).getFeatured_image_path());

        }else
        if(sections.get(position).getBlog_thumb_image()!=null ||sections.get(position).getVideo_thumb()!=null){

            UIHelper.setImageWithGlide(context,holder.circleImageView,  sections.get(position).getBlog_thumb_image()!=null? AppConstant.BASE_URL_IMAGE+sections.get(position).getBlog_thumb_image(): AppConstant.BASE_URL_IMAGE+sections.get(position).getVideo_thumb());

        }else{

            UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getGallery().getPhotos().get(0).getImage_path());

        }

       // UIHelper.setImageWithGlide(context,holder.circleImageView,sections.size()>0 ? sections.get(position).getGallery().getPhotos().get(0).getImage_path() : null);
   //     setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 foodHomeListner.featuredrecipe(position);

            }
        });
        if(sections.get(position).getIs_favorite()==1){
            holder.likeButton.setLiked(true);

        }else{
            holder.likeButton.setLiked(false);

        }

        if (sections.get(position).getIs_save() == 1) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);
        }

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    foodHomeListner.onSaveRecipe(sections.get(position).getId());
                    holder.checkbox.setChecked(true);


                } else {
                    foodHomeListner.onSaveRecipe(sections.get(position).getId());
                    holder.checkbox.setChecked(false);

                }

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

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {
        LikeButton likeButton;
        protected TextView text,tvPopularRecipeServes,tvPopularRecipeCookingTime;
        ImageView circleImageView;
        CheckBox checkbox;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            tvPopularRecipeServes = (TextView) itemView.findViewById(R.id.tvPopularRecipeServes);
            tvPopularRecipeCookingTime = (TextView) itemView.findViewById(R.id.tvPopularRecipeCookingTime);
            text = (TextView) itemView.findViewById(R.id.tvPopularRecipe);
            circleImageView = (ImageView) itemView.findViewById(R.id.ivPopularRecipe);
            likeButton = (LikeButton) itemView.findViewById(R.id.lkFav);

        }
    }
}