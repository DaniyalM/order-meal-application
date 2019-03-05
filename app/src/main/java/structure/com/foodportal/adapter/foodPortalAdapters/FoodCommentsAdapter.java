package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.slf4j.helpers.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import structure.com.foodportal.R;
import structure.com.foodportal.customViews.CustomRatingBar;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.foodInterfaces.CommentClickListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Comments;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodCommentsAdapter extends RecyclerView.Adapter<FoodCommentsAdapter.PlanetViewHolder> {

    ArrayList<Comments> sections;
    Context context;
    private int lastPosition = -1;
    CommentClickListner commentClickListner;
    boolean showTwo;
    public FoodCommentsAdapter(ArrayList<Comments> sections, Context context, CommentClickListner commentClickListner,boolean showTwo) {
        this.showTwo = showTwo;
        this.sections = sections;
        this.context = context;
        this.commentClickListner =  commentClickListner;
    }

    @Override
    public FoodCommentsAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        FoodCommentsAdapter.PlanetViewHolder viewHolder = new FoodCommentsAdapter.PlanetViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(FoodCommentsAdapter.PlanetViewHolder holder, int position) {



        UIHelper.setImageWithGlide(context,holder.userimage, AppConstant.BASE_URL_IMAGE+sections.get(position).getUser().getProfile_picture());

        holder.username.setText(sections.get(position).getUser().getName_en());
        holder.comment.setText(sections.get(position).getReviews());
        holder.time.setText(sections.get(position).getCreated_at());


        if(sections.get(position).getChild_reviews().size()>0){

            if(sections.get(position).getChild_reviews().size()>1){

                holder.subusername.setText(sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getUser().getName_en());
                holder.subcomment.setText(sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getReviews());
                holder.subtime.setText(sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getCreated_at());
                UIHelper.setImageWithGlide(context,holder.subuserimage, AppConstant.BASE_URL_IMAGE+sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getUser().getProfile_picture());
                holder.viewall.setVisibility(View.VISIBLE);
                holder.llsubcomment.setVisibility(View.VISIBLE);

            }else{
                holder.subusername.setText(sections.get(position).getChild_reviews().get(0).getUser().getName_en());
                holder.subcomment.setText(sections.get(position).getChild_reviews().get(0).getReviews());
                holder.subtime.setText(sections.get(position).getChild_reviews().get(0).getCreated_at());
                UIHelper.setImageWithGlide(context,holder.subuserimage, AppConstant.BASE_URL_IMAGE+sections.get(position).getChild_reviews().get(0).getUser().getProfile_picture());

                holder.llsubcomment.setVisibility(View.VISIBLE);

            }


        }


        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                commentClickListner.onReplyClick(position);


            }
        });

        holder.viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                commentClickListner.onReplyClick(position);


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

        if(showTwo){
            return 2;
        }
      else return sections.size();

    }



    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView username,comment,time,reply;
        ImageView userimage;
        CustomRatingBar rating;



        TextView subusername,subcomment,subtime;
        ImageView subuserimage;

        TextView viewall;
        LinearLayout llsubcomment;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.starRating);
            username = itemView.findViewById(R.id.tvUsername);
            comment = itemView.findViewById(R.id.tvMainCommment);
            time = itemView.findViewById(R.id.tvUserTime);
            reply = itemView.findViewById(R.id.tvReply);
            userimage = itemView.findViewById(R.id.ivUser);

            subuserimage = itemView.findViewById(R.id.ivUserSub);
            viewall = itemView.findViewById(R.id.tvviewall);
            subusername = itemView.findViewById(R.id.tvUsernameSub);
            subtime = itemView.findViewById(R.id.tvUserTimeSub);
            subcomment = itemView.findViewById(R.id.tvSubCommment);
            llsubcomment = itemView.findViewById(R.id.llsubcomment);


        }
    }
}