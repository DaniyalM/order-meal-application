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
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.SavedRecipe;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.Sections;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;

public class SeeAllRecipesAdapter  extends RecyclerView.Adapter<SeeAllRecipesAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    Context context;
    SubCategoryListner subCategoryListner;
    private int lastPosition = -1;
    BasePreferenceHelper mPreferenceHelper;

    public SeeAllRecipesAdapter(ArrayList<Sections> sections, Context context, SubCategoryListner subCategoryListner) {
        this.sections = sections;
        this.context = context;
        this.subCategoryListner = subCategoryListner;
    }

    @Override
    public SeeAllRecipesAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allrecipes, parent, false);
        SeeAllRecipesAdapter.PlanetViewHolder viewHolder = new SeeAllRecipesAdapter.PlanetViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(SeeAllRecipesAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);


        holder.text.setText(mPreferenceHelper.getSelectedLanguage() == ENGLISH ? sections.get(position).getTitle_en() : sections.get(position).getTitle_ur());
        UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getFeatured_image_path());
        // setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subCategoryListner.onSubCategoryClick(position);

            }
        });

     //   setAnimation(holder.itemView,position);
    }

    public void setPreferenceHelper(BasePreferenceHelper preferenceHelper) {
        mPreferenceHelper = preferenceHelper;
    }

    private void setScaleAnimation(View view, int position) {
        if (position > 0) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
            lastPosition = position;
        }
    }    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

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

    public void addAll(ArrayList<Sections> categorySliderWrapper) {
        this.sections =categorySliderWrapper;
        notifyDataSetChanged();

    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView circleImageView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.recipe);
            circleImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}