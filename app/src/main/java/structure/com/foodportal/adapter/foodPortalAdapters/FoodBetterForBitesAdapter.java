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
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodBetterForBitesAdapter  extends RecyclerView.Adapter<FoodBetterForBitesAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    Context context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;
    public FoodBetterForBitesAdapter(ArrayList<Sections> sections, Context context,FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodBetterForBitesAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_recipe, parent, false);
        FoodBetterForBitesAdapter.PlanetViewHolder viewHolder = new FoodBetterForBitesAdapter.PlanetViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(FoodBetterForBitesAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.text.setText(""+sections.get(position).getTitle());
        UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getGallery().getPhotos().get(0).getImage_path());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                foodHomeListner.betterforurbites(position);
            }
        });

        setAnimation(holder.itemView, position);


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

        protected TextView text;
        ImageView circleImageView;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvPopularRecipe);
            circleImageView = (ImageView) itemView.findViewById(R.id.ivPopularRecipe);
        }
    }
}
