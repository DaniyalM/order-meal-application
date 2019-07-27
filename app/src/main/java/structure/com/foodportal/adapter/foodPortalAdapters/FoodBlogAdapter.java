package structure.com.foodportal.adapter.foodPortalAdapters;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.fontawesome.FontTextView;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodBlogAdapter extends RecyclerView.Adapter<FoodBlogAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    MainActivity context;
    FoodHomeListner foodHomeListner;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public FoodBlogAdapter(ArrayList<Sections> sections, MainActivity context, FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodBlogAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recommended, parent, false);
        FoodBlogAdapter.PlanetViewHolder viewHolder = new FoodBlogAdapter.PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodBlogAdapter.PlanetViewHolder holder, int position) {
        if (sections.get(position).getFeature_type_id() == 1) {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.likeButton.setVisibility(View.VISIBLE);
            holder.tvPopularRecipeServes.setVisibility(View.VISIBLE);
            holder.tvPopularRecipeCookingTime.setVisibility(View.VISIBLE);
            holder.tvPopularRecipeServes.setText(sections.get(position).getServing_for() + " person(s)");
            holder.tvPopularRecipeCookingTime.setText(sections.get(position).getCook_time());
        } else {
            holder.cardView.setVisibility(View.GONE);
            holder.likeButton.setVisibility(View.GONE);
            holder.tvPopularRecipeServes.setVisibility(View.GONE);
            holder.tvPopularRecipeCookingTime.setVisibility(View.GONE);
        }

        holder.text.setText("" + sections.get(position).getTitle());

        // For blog
        if (sections.get(position).getFeature_type_id() == 4 && sections.get(position).getBlog_thumb_image_path() != null) {
            UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getBlog_thumb_image_path());
        } else if (sections.get(position).getFeatured_image_path() != null) {
            UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getFeatured_image_path());
        } else if (sections.get(position).getGallery() != null && sections.get(position).getGallery().getPhotos().size() > 0) {
            UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getGallery().getPhotos().get(0).getImage_path());
        } else if (sections.get(position).getBlog_thumb_image() != null || sections.get(position).getVideo_thumb() != null) {
            UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getBlog_thumb_image() != null ? AppConstant.BASE_URL_IMAGE + sections.get(position).getBlog_thumb_image() : AppConstant.BASE_URL_IMAGE + sections.get(position).getVideo_thumb());
        } else {
            UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getGallery().getPhotos().get(0).getImage_path());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodHomeListner.onBlogClick(position);
            }
        });

        if (sections.get(position).getIs_save() == 1) {
            holder.likeButton.setTextColor(context.getResources().getColor(R.color.colorRed));
        } else {
            holder.likeButton.setTextColor(context.getResources().getColor(R.color.white));
        }

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context.prefHelper.getUserFood().getId().equals("293")) {

                    Toast.makeText(context, "Please Login to proceed", Toast.LENGTH_SHORT).show();

                } else {

                    if (holder.likeButton.getCurrentTextColor() == context.getResources().getColor(R.color.white)) {
                        foodHomeListner.onSaveRecipe(sections.get(position).getId());
                        holder.likeButton.setTextColor(context.getResources().getColor(R.color.colorRed));

                    } else {
                        foodHomeListner.onSaveRecipe(sections.get(position).getId());
                        holder.likeButton.setTextColor(context.getResources().getColor(R.color.white));

                    }

                }

            }
        });

        setFadeAnimation(holder.itemView);
        // setScaleAnimation(holder.itemView,position);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text, tvPopularRecipeServes, tvPopularRecipeCookingTime;
        ImageView circleImageView;
        FontTextView likeButton;
        CheckBox checkbox;
        FrameLayout cardView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            text = (TextView) itemView.findViewById(R.id.tvPopularRecipe);
            tvPopularRecipeServes = (TextView) itemView.findViewById(R.id.tvPopularRecipeServes);
            tvPopularRecipeCookingTime = (TextView) itemView.findViewById(R.id.tvPopularRecipeCookingTime);
            circleImageView = (ImageView) itemView.findViewById(R.id.ivPopularRecipe);
            cardView = (FrameLayout) itemView.findViewById(R.id.cardViewBAck);
            likeButton = (FontTextView) itemView.findViewById(R.id.lkFav);
        }
    }
}