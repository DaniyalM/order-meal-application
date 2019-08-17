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

import com.like.LikeButton;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Recipe;
import structure.com.foodportal.models.foodModels.Sections;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class FoodCookingGuidesAdapter extends RecyclerView.Adapter<FoodCookingGuidesAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    Context context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;

    public FoodCookingGuidesAdapter(ArrayList<Sections> sections, Context context, FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodCookingGuidesAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cooking_guides, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodCookingGuidesAdapter.PlanetViewHolder holder, int position) {
        holder.text.setText(getTitleBySelectedLanguage(position));
        UIHelper.setImageWithGlide(context, holder.circleImageView, sections.get(position).getBlog_thumb_image_path_size());

        // UIHelper.setImageWithGlide(context,holder.circleImageView,sections.size()>0 ? sections.get(position).getGallery().getPhotos().get(0).getImage_path() : null);
        //     setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(view -> foodHomeListner.masterTechniquesClick(position));
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

    public void addAllToAdapter(ArrayList<Sections> sections) {
        this.sections = sections;
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