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

import com.like.LikeButton;

import java.util.ArrayList;

import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.Sections;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class FoodMasterTechniquesAdapter extends RecyclerView.Adapter<FoodMasterTechniquesAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    Context context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;

    public FoodMasterTechniquesAdapter(ArrayList<Sections> sections, Context context, FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodMasterTechniquesAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_technique, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(FoodMasterTechniquesAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);


        holder.text.setText("" + getTitleBySelectedLanguage(position));
        UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getBlog_thumb_image_path_size());


        // UIHelper.setImageWithGlide(context,holder.circleImageView,sections.size()>0 ? sections.get(position).getGallery().getPhotos().get(0).getImage_path() : null);
        //     setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(view -> foodHomeListner.masterTechniquesClick(position));
        if (sections.get(position).getIs_favorite() == 1) {
            holder.likeButton.setLiked(true);

        } else {
            holder.likeButton.setLiked(false);

        }
    }

    private BasePreferenceHelper preferenceHelper;

    private String getTitleBySelectedLanguage(int position) {
        String title = sections.get(position).getTitle();
        if (preferenceHelper != null) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH) {
                title = sections.get(position).getTitle();
            } else if (preferenceHelper.getSelectedLanguage() == URDU) {
                title = sections.get(position).getTitle_ur();
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
        protected TextView text;
        ImageView circleImageView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvPopularRecipe);
            circleImageView = (ImageView) itemView.findViewById(R.id.ivPopularRecipe);
            likeButton = (LikeButton) itemView.findViewById(R.id.lkFav);

        }
    }
}