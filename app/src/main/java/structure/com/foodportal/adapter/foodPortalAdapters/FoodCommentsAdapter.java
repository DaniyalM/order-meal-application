package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimeZone;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.customViews.CustomRatingBar;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.ItemTouchHelperAdapter;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.CommentClickListner;
import structure.com.foodportal.models.foodModels.Comments;

public class FoodCommentsAdapter extends RecyclerView.Adapter<FoodCommentsAdapter.PlanetViewHolder> implements ItemTouchHelperAdapter {

    ArrayList<Comments> sections= new ArrayList<>();
    MainActivity context;
    private int lastPosition = -1;
    CommentClickListner commentClickListner;
    boolean showTwo, main;

    public FoodCommentsAdapter(ArrayList<Comments> sections, MainActivity context, CommentClickListner commentClickListner, boolean showTwo, boolean main) {
        this.showTwo = showTwo;
        this.main = main;
        this.sections = sections;
        this.context = context;
        this.commentClickListner = commentClickListner;
    }

    @Override
    public FoodCommentsAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        FoodCommentsAdapter.PlanetViewHolder viewHolder = new FoodCommentsAdapter.PlanetViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(FoodCommentsAdapter.PlanetViewHolder holder, int position) {

        if(sections.size()>0){
        switch (sections.get(position).getUser().getAcct_type()) {

            case 1:
                UIHelper.setImageWithGlide(context, holder.userimage, AppConstant.BASE_URL_IMAGE + sections.get(position).getUser().getProfile_picture());
                break;
            case 2://gmail
                UIHelper.setImageWithGlide(context, holder.userimage, AppConstant.BASE_URL_IMAGE + sections.get(position).getUser().getProfile_picture());
                break;
            case 3://facebook
                UIHelper.setImageWithGlide(context, holder.userimage, "https://graph.facebook.com/" + sections.get(position).getUser().getProfile_picture() + "/picture?type=large");
                break;

        }

        holder.username.setText(sections.get(position).getUser().getName_en());
        holder.comment.setText(sections.get(position).getReviews());
            try {
                holder.time.setText( convertime(sections.get(position).getCreated_at()));
            } catch (ParseException e) {


            }

            if (sections.get(position).getChild_reviews().size() > 0) {


            if (sections.get(position).getChild_reviews().size() > 1) {
                holder.scroll.removeAllViews();
                for (int i = 0; i < sections.get(position).getChild_reviews().size(); i++) {


                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.subcomment_layout, null);
                    TextView sub_user_name = view.findViewById(R.id.tvUsernameSub);
                    TextView sub_user_time = view.findViewById(R.id.tvUserTimeSub);
                    TextView sub_comment = view.findViewById(R.id.tvSubCommment);
                    ImageView sub_user_image = view.findViewById(R.id.ivUserSub);

                    if (main) {
                        sub_user_name.setText(sections.get(position).getChild_reviews().get(i).getUser().getName_en());
                        sub_comment.setText(sections.get(position).getChild_reviews().get(i).getReviews());
                        try {
                            sub_user_time.setText( convertime(sections.get(position).getChild_reviews().get(i).getCreated_at()));
                        } catch (ParseException e) {


                        }


                        switch (sections.get(position).getChild_reviews().get(i).getUser().getAcct_type()) {

                            case 1:
                                UIHelper.setImageWithGlide(context, sub_user_image, AppConstant.BASE_URL_IMAGE + sections.get(position).getChild_reviews().get(i).getUser().getProfile_picture());
                                break;
                            case 2://gmail
                                UIHelper.setImageWithGlide(context, sub_user_image, AppConstant.BASE_URL_IMAGE + sections.get(position).getChild_reviews().get(i).getUser().getProfile_picture());
                                break;
                            case 3://facebook
                                UIHelper.setImageWithGlide(context, sub_user_image, "https://graph.facebook.com/" + sections.get(position).getChild_reviews().get(i).getUser().getProfile_picture() + "/picture?type=large");
                                break;

                        }


                        holder.scroll.addView(view, i);
                        //  holder.llsubcomment.setVisibility(View.VISIBLE);
                    } else {
                        sub_user_name.setText(sections.get(position).getChild_reviews().get(i).getUser().getName_en());
                        sub_comment.setText(sections.get(position).getChild_reviews().get(i).getReviews());
                        try {
                            sub_user_time.setText( convertime(sections.get(position).getChild_reviews().get(i).getCreated_at()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        switch (sections.get(position).getChild_reviews().get(i).getUser().getAcct_type()) {

                            case 1:
                                UIHelper.setImageWithGlide(context, sub_user_image, AppConstant.BASE_URL_IMAGE + sections.get(position).getChild_reviews().get(i).getUser().getProfile_picture());
                                break;
                            case 2://gmail
                                UIHelper.setImageWithGlide(context, sub_user_image, AppConstant.BASE_URL_IMAGE + sections.get(position).getChild_reviews().get(i).getUser().getProfile_picture());
                                break;
                            case 3://facebook
                                UIHelper.setImageWithGlide(context, sub_user_image, "https://graph.facebook.com/" + sections.get(position).getChild_reviews().get(i).getUser().getProfile_picture() + "/picture?type=large");
                                break;

                        }
                        holder.scroll.addView(view, i);
                        //  holder.llsubcomment.setVisibility(View.VISIBLE);
                        //  holder.scroll.addView(view,i);
                    }


                }

            }
            if (sections.get(position).getChild_reviews().size() == 1) {

                holder.scroll.removeAllViews();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.subcomment_layout, null);
                TextView sub_user_name = view.findViewById(R.id.tvUsernameSub);
                TextView sub_user_time = view.findViewById(R.id.tvUserTimeSub);
                TextView sub_comment = view.findViewById(R.id.tvSubCommment);
                ImageView sub_user_image = view.findViewById(R.id.ivUserSub);

                sub_user_name.setText(sections.get(position).getChild_reviews().get(0).getUser().getName_en());
                sub_comment.setText(sections.get(position).getChild_reviews().get(0).getReviews());
                try {
                    sub_user_time.setText(
                            convertime(
                            sections.get(position).getChild_reviews().get(0).getCreated_at()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                switch (sections.get(position).getChild_reviews().get(0).getUser().getAcct_type()) {

                    case 1:
                        UIHelper.setImagewithGlide(context, sub_user_image, AppConstant.BASE_URL_IMAGE + sections.get(position).getChild_reviews().get(0).getUser().getProfile_picture());
                        break;
                    case 2://gmail
                        UIHelper.setImagewithGlide(context, sub_user_image, AppConstant.BASE_URL_IMAGE + sections.get(position).getChild_reviews().get(0).getUser().getProfile_picture());
                        break;
                    case 3://facebook
                        UIHelper.setImagewithGlide(context, sub_user_image, "https://graph.facebook.com/" + sections.get(position).getChild_reviews().get(0).getUser().getProfile_picture() + "/picture?type=large");
                        break;

                }
                holder.scroll.addView(view, 0);
                //holder.llsubcomment.setVisibility(View.VISIBLE);
            }


//
//            if(sections.get(position).getChild_reviews().size()>1){
//                if(main){
//                    holder.subusername.setText(sections.get(position).getChild_reviews().get(0).getUser().getName_en());
//                    holder.subcomment.setText(sections.get(position).getChild_reviews().get(0).getReviews());
//                    holder.subtime.setText(sections.get(position).getChild_reviews().get(0).getCreated_at());
//                    UIHelper.setImageWithGlide(context,holder.subuserimage, AppConstant.BASE_URL_IMAGE+sections.get(position).getChild_reviews().get(0).getUser().getProfile_picture());
//
//                    holder.llsubcomment.setVisibility(View.VISIBLE);
//                }else{
//                    holder.subusername.setText(sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getUser().getName_en());
//                    holder.subcomment.setText(sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getReviews());
//                    holder.subtime.setText(sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getCreated_at());
//                    UIHelper.setImageWithGlide(context,holder.subuserimage, AppConstant.BASE_URL_IMAGE+sections.get(position).getChild_reviews().get(sections.get(position).getChild_reviews().size()-1).getUser().getProfile_picture());
//                    holder.viewall.setVisibility(View.VISIBLE);
//                    holder.llsubcomment.setVisibility(View.VISIBLE);
//
//                }
//
//            }else{
//                holder.subusername.setText(sections.get(position).getChild_reviews().get(0).getUser().getName_en());
//                holder.subcomment.setText(sections.get(position).getChild_reviews().get(0).getReviews());
//                holder.subtime.setText(sections.get(position).getChild_reviews().get(0).getCreated_at());
//                UIHelper.setImageWithGlide(context,holder.subuserimage, AppConstant.BASE_URL_IMAGE+sections.get(position).getChild_reviews().get(0).getUser().getProfile_picture());
//
//                holder.llsubcomment.setVisibility(View.VISIBLE);
//
//            }
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

        //setAnimation(holder.itemView, position);


    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > 0) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }



    public  void addAll(ArrayList<Comments>sections){
        this.sections.clear();
        this.sections.addAll(sections);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if (showTwo) {
            return sections.size()>0?1:0;
        } else return sections.size();

    }

    @Override
    public void onItemDismiss(int position) {
        sections.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(sections, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(sections, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView username, comment, time, reply;
        ImageView userimage;
        CustomRatingBar rating;


        TextView viewall;
        LinearLayout llsubcomment;
        LinearLayout scroll;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.starRating);
            username = itemView.findViewById(R.id.tvUsername);
            comment = itemView.findViewById(R.id.tvMainCommment);
            time = itemView.findViewById(R.id.tvUserTime);
            reply = itemView.findViewById(R.id.tvReply);
            userimage = itemView.findViewById(R.id.ivUser);
            viewall = itemView.findViewById(R.id.tvviewall);
            scroll = itemView.findViewById(R.id.llsubcomments);


        }
    }

   public CharSequence convertime(String createdat) throws ParseException {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
       long time = sdf.parse(createdat).getTime();
       long now = System.currentTimeMillis();

       CharSequence ago =
               DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);


       return ago;
    }

}