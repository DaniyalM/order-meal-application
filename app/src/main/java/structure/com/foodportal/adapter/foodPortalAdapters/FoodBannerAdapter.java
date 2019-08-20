package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodBannerListner;
import structure.com.foodportal.models.foodModels.Banner;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class FoodBannerAdapter extends RecyclerView.Adapter<FoodBannerAdapter.PlanetViewHolder> {

    private final FoodBannerListner foodBannerListner;
    ArrayList<Banner> ingredientList;
    Context context;
    private int lastPosition = -1;

    public FoodBannerAdapter(ArrayList<Banner> ingredientList, Context context, FoodBannerListner foodBannerListner) {
        this.ingredientList = ingredientList;
        this.foodBannerListner = foodBannerListner;
        this.context = context;
    }

    @Override
    public FoodBannerAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_banner, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodBannerAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);

        holder.text.clearAnimation();
        holder.text.setText("" + getTitleBySelectedLanguage(position));
        if (ingredientList.get(position).getFeatured_image_path() != null) {
            UIHelper.setImageWithGlide(context, holder.circleImageView, ingredientList.get(position).getFeatured_image_path());
        } else {
            // For blog
            if (ingredientList.get(position).getId() == 4) {
                UIHelper.setImageWithGlide(context, holder.circleImageView, R.drawable.ic_blog_banner);
            }
            // For tutorial
            else if (ingredientList.get(position).getId() == 2) {
                UIHelper.setImageWithGlide(context, holder.circleImageView, ingredientList.get(position).getBanner_image_path());
            }
            // For other feature types
            else {
                UIHelper.setImageWithGlide(context, holder.circleImageView, ingredientList.get(position).getGallery().getPhotos().get(0).getImage_path());
            }
        }

        // setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingredientList.get(position).getBanner_image_path() != null) {


                } else {

                    foodBannerListner.onBannerClick(position);
                }

            }
        });

    }

    private BasePreferenceHelper preferenceHelper;

    private String getTitleBySelectedLanguage(int position) {
        String title = ingredientList.get(position).getTitle_en();
        if (preferenceHelper != null) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH) {
                title = ingredientList.get(position).getTitle_en();
            } else if (preferenceHelper.getSelectedLanguage() == URDU) {
                if (ingredientList.get(position).getId() == 2) {
                    title = context.getString(R.string.tutorials_ur);
                } else {
                    title = ingredientList.get(position).getTitle_ur();
                }
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
        KenBurnsView circleImageView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvRecipename);
            circleImageView = (KenBurnsView) itemView.findViewById(R.id.ivBanner);
        }
    }
}