package structure.com.foodportal.adapter.foodPortalAdapters;

import android.support.v7.widget.CardView;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;

import java.util.ArrayList;

import info.androidhive.fontawesome.FontTextView;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodPopularRecipeAdapter extends RecyclerView.Adapter<FoodPopularRecipeAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    MainActivity context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;

    public FoodPopularRecipeAdapter(ArrayList<Sections> sections, MainActivity context, FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodPopularRecipeAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_popular, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    @Override
    public void onBindViewHolder(FoodPopularRecipeAdapter.PlanetViewHolder holder, int position) {


        if(width==0){


        }else{
            ViewGroup.LayoutParams params = holder.circleImageView.getLayoutParams();
// Changes the height and width to the specified *pixels*
            params.height =    dpToPx(195);
            params.width =    dpToPx(291);

            holder.circleImageView.setLayoutParams(params);


        }

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
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.text.setText("" + sections.get(position).getTitle());
        if (sections.get(position).getFeatured_image_path() != null) {

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
                foodHomeListner.popularrecipe(position);
            }
        });
        if (sections.get(position).getIs_save() == 1) {
            holder.likeButton.setTextColor(context.getResources().getColor(R.color.colorRed));

        } else {
            holder.likeButton.setTextColor(context.getResources().getColor(R.color.white));
            //holder.likeButton.setLiked(false);

        }


  /*      if (sections.get(position).getIs_save() == 1) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);
        }*/
      /*  if (context.prefHelper.getLoginStatus() == false) {

            holder.checkbox.setEnabled(false);
            holder.checkbox.setClickable(false);
        } else {
            holder.checkbox.setEnabled(true);
            holder.checkbox.setClickable(true);


        }*/


        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context.prefHelper.getUserFood().getId().equals("293")) {

                    Toast.makeText(context, "Please Login to proceed", Toast.LENGTH_SHORT).show();

                } else {

//                    if (holder.likeButton.getCurrentTextColor()== context.getResources().getColor(R.color.white)) {
//                        foodHomeListner.onSaveRecipe(sections.get(position).getId());
//                        holder.likeButton.setTextColor(context.getResources().getColor(R.color.colorRed));
//                        // holder.likeButton.setLiked(true);
//
//                    } else {
//                        foodHomeListner.onSaveRecipe(sections.get(position).getId());
//                        holder.likeButton.setTextColor(context.getResources().getColor(R.color.white));
//                        //   holder.likeButton.setLiked(false);
//
//                    }

                    if (holder.likeButton.getCurrentTextColor()== context.getResources().getColor(R.color.white)) {
                        // foodHomeListner.onSaveRecipe(sections.get(position).getId());
                        foodHomeListner.onFavoriteRecipe(sections.get(position).getId());
                        holder.likeButton.setTextColor(context.getResources().getColor(R.color.colorRed));

                    } else {
                        // foodHomeListner.onSaveRecipe(sections.get(position).getId());
                        foodHomeListner.onFavoriteRecipe(sections.get(position).getId());
                        holder.likeButton.setTextColor(context.getResources().getColor(R.color.white));

                    }

                }


            }
        });
  /*      holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {





*//*

                if (b) {

                    foodHomeListner.onSaveRecipe(sections.get(position).getId());
                    holder.checkbox.setChecked(true);


                } else {
                    foodHomeListner.onSaveRecipe(sections.get(position).getId());
                    holder.checkbox.setChecked(false);

                }
*//*


            }
        });*/


        setScaleAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > 0) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    private void setScaleAnimation(View view, int position) {
        if (position > 0) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
            lastPosition = position;
        }
    }
     int width=0;
    public void setWidth(int width) {

        this.width=width;

    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text, tvPopularRecipeServes, tvPopularRecipeCookingTime;
        ImageView circleImageView;
        FontTextView likeButton;
        CheckBox checkbox;
        FrameLayout cardView;
        LinearLayout linearSize;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            text = (TextView) itemView.findViewById(R.id.tvPopularRecipe);
            tvPopularRecipeServes = (TextView) itemView.findViewById(R.id.tvPopularRecipeServes);
            tvPopularRecipeCookingTime = (TextView) itemView.findViewById(R.id.tvPopularRecipeCookingTime);
            circleImageView = (ImageView) itemView.findViewById(R.id.ivPopularRecipe);
            likeButton = (FontTextView) itemView.findViewById(R.id.lkFav);
            cardView = (FrameLayout) itemView.findViewById(R.id.cardViewBAck);
            linearSize = (LinearLayout) itemView.findViewById(R.id.linearSize);



        }
    }
}