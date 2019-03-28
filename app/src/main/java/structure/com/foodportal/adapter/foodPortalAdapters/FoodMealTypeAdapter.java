package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.Spanny;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodChipsListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodSearchListner;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.Step;

public class FoodMealTypeAdapter extends RecyclerView.Adapter<FoodMealTypeAdapter.FoodSearchViewHolder> {

    ArrayList<String> steps;
    FoodChipsListner foodDetailListner;
    Context context;
    private Integer[] colors =new Integer[7];


    public FoodMealTypeAdapter(ArrayList<String> steps, Context context, FoodChipsListner foodDetailListner) {
        this.steps = steps;
        this.context = context;
        this.foodDetailListner = foodDetailListner;
        colors[0] = Integer.valueOf(context.getResources().getColor(R.color.accent));
        colors[1] =Integer.valueOf(context.getResources().getColor(R.color.colorAccent));
        colors[2] = Integer.valueOf(context.getResources().getColor(R.color.colorRed));
        colors[3] = Integer.valueOf(context.getResources().getColor(R.color.colorYellow));
        colors[4] =Integer.valueOf(context.getResources().getColor(R.color.com_facebook_blue));
        colors[5] =Integer.valueOf(context.getResources().getColor(R.color.colorPrimary));
        colors[6] = Integer.valueOf(context.getResources().getColor(R.color.colorDeepBrown));
    }

    @Override
    public FoodMealTypeAdapter.FoodSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip, parent, false);
        FoodSearchViewHolder viewHolder = new FoodSearchViewHolder(v);
        return viewHolder;
    }
    int minColor = 0;
    int maxColor = 6;
    @Override
    public void onBindViewHolder(FoodMealTypeAdapter.FoodSearchViewHolder holder, int position) {
        Random r = new Random();

        int R = r.nextInt(maxColor-minColor) + minColor;
        holder.chip.setTextColor(colors[R]);
        holder.chip.setText(steps.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodDetailListner.onMealTypeClick(position);
            }
        });
    }

    public void addAll(ArrayList<String> foodDetailModels){

        this.steps.clear();
        this.steps.addAll(foodDetailModels);
        notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class FoodSearchViewHolder extends RecyclerView.ViewHolder {

        protected TextView chip;
        CardView colorbackground;


        public FoodSearchViewHolder(View itemView) {
            super(itemView);

            chip = (TextView) itemView.findViewById(R.id.chip);
            colorbackground = (CardView) itemView.findViewById(R.id.changeColor);

        }
    }
}