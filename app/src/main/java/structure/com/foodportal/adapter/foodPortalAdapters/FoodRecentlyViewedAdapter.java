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
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodRecentlyViewedAdapter  extends RecyclerView.Adapter<FoodRecentlyViewedAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    Context context;
    private int lastPosition = -1;
    FoodHomeListner foodHomeListner;
    public FoodRecentlyViewedAdapter(ArrayList<Sections> sections, Context context,FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodRecentlyViewedAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_recipe, parent, false);
        FoodRecentlyViewedAdapter.PlanetViewHolder viewHolder = new FoodRecentlyViewedAdapter.PlanetViewHolder(v);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(FoodRecentlyViewedAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.text.setText(""+sections.get(position).getTitle());
        if(sections.get(position).getFeatured_image_path()!=null){

            UIHelper.setImageWithGlide(context,holder.circleImageView, sections.get(position).getFeatured_image_path());

        }else
        if(sections.get(position).getBlog_thumb_image()!=null ||sections.get(position).getVideo_thumb()!=null){

            UIHelper.setImageWithGlide(context,holder.circleImageView, sections.get(position).getBlog_thumb_image()!=null? AppConstant.BASE_URL_IMAGE+sections.get(position).getBlog_thumb_image(): AppConstant.BASE_URL_IMAGE+sections.get(position).getVideo_thumb());

        }else{
            UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getGallery().getPhotos().get(0).getImage_path());

        }

        //        UIHelper.setImageWithGlide(context,holder.circleImageView,sections.get(position).getGallery().getPhotos().get(0).getImage_path());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                foodHomeListner.recentlyViewed(position);
            }
        });
        if(sections.get(position).getIs_favorite()==1){
            holder.likeButton.setLiked(true);

        }else{
            holder.likeButton.setLiked(false);

        }
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
        LikeButton likeButton;
        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvPopularRecipe);
            circleImageView = (ImageView) itemView.findViewById(R.id.ivPopularRecipe);
            likeButton = (LikeButton) itemView.findViewById(R.id.lkFav);

        }
    }
}
