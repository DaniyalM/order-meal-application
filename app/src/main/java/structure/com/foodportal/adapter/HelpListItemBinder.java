package structure.com.foodportal.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.ExpandableListViewBinder;

public class HelpListItemBinder extends ExpandableListViewBinder<String, String> {

    MainActivity mainActivity;
    FoodCount foodCount;
    private ArrayList<ImageView> imgArrowColelction = new ArrayList<>();

    public HelpListItemBinder(FoodCount foodCount) {
        super(R.layout.layout_help_question, R.layout.layout_help_answer);
        this.foodCount = foodCount;
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new GroupViewHolder(view);
    }

    @Override
    public BaseChildViewHolder createChildViewHolder(View view) {
        return new ChildViewHolder(view);
    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, View view, Activity activity) {

        mainActivity = (MainActivity) activity;
        GroupViewHolder holder = (GroupViewHolder) view.getTag();
//        holder.txtQuestion.setTypeface(FontUtils.setMontserratLight(activity));
//        holder.txtQuestion.setText(entity.getQuestion());
        holder.txtQuestion.setText(entity);

        holder.imgArrow.setTag(position);
        if (imgArrowColelction.size() == position) {
            imgArrowColelction.add(holder.imgArrow);
        } else {
            imgArrowColelction.set(position, holder.imgArrow);
        }
    }

    @Override
    public void bindChildView(String entity, int position, int grpPosition, View view, Activity activity) {
        final ChildViewHolder holder = (ChildViewHolder) view.getTag();

        mainActivity = (MainActivity) activity;
//        holder.txtAnswer.setText(entity.getAnswer() + "");
        holder.txtAnswer.setText(entity + "");
        holder.txtTakeAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.layoutQuantity.setVisibility(View.VISIBLE);
                holder.txtTakeAway.setVisibility(View.GONE);
                foodCount.noFoodCount(1);
            }
        });

        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.amount = (Integer.parseInt(holder.tvQuantity.getText().toString()));
                holder.amount += 1;
                holder.tvQuantity.setText(holder.amount + "");
                foodCount.noFoodCount(1);
            }
        });

        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.amount = (Integer.parseInt(holder.tvQuantity.getText().toString()));
                foodCount.noFoodCount(-1);
                if (holder.amount > 1) {
                    holder.amount -= 1;
                    holder.tvQuantity.setText(holder.amount + "");
                } else {
                    holder.layoutQuantity.setVisibility(View.GONE);
                    holder.txtTakeAway.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onGroupExpand(int groupPosition) {
        ImageView imgArrow = imgArrowColelction.get(groupPosition);
        imgArrow.setImageResource(R.drawable.downarrow);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        ImageView imgArrow = imgArrowColelction.get(groupPosition);
        imgArrow.setImageResource(R.drawable.downarrow);
    }


    public interface FoodCount {
        public void noFoodCount(int amount);
    }

    public static class GroupViewHolder extends BaseGroupViewHolder {


        TextView txtQuestion;
        ImageView imgArrow;

        public GroupViewHolder(View view) {

            txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);

        }
    }

    public static class ChildViewHolder extends BaseChildViewHolder {


        TextView txtAnswer, txtAmount, txtTakeAway, txtDesc, tvAdd, tvQuantity, tvMinus;
        LinearLayout layoutQuantity;
        int amount = 0;

        public ChildViewHolder(View view) {

            txtAnswer = (TextView) view.findViewById(R.id.txtAnswer);
            txtAmount = (TextView) view.findViewById(R.id.txtAmount);
            txtTakeAway = (TextView) view.findViewById(R.id.txtTakeAway);
            txtDesc = (TextView) view.findViewById(R.id.txtDesc);


            tvMinus = (TextView) view.findViewById(R.id.tvMinus);
            tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
            tvAdd = (TextView) view.findViewById(R.id.tvAdd);
            layoutQuantity = view.findViewById(R.id.layoutQuantity);


        }
    }


}

