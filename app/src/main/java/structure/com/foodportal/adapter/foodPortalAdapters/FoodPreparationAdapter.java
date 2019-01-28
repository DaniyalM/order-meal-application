package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.Step;

public class FoodPreparationAdapter extends RecyclerView.Adapter<FoodPreparationAdapter.FoodPreparationViewHolder> {

    ArrayList<Step> steps;
    FoodDetailListner foodDetailListner;

    public FoodPreparationAdapter(ArrayList<Step> steps, Context context, FoodDetailListner foodDetailListner) {
        this.steps = steps;
        this.foodDetailListner = foodDetailListner;
    }

    @Override
    public FoodPreparationAdapter.FoodPreparationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preparation, parent, false);
        FoodPreparationViewHolder viewHolder = new FoodPreparationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodPreparationAdapter.FoodPreparationViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.text.setText(""+steps.get(position).getSteps_en());
        holder.textnum.setText(""+(position+1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodDetailListner.onStepClick(steps.get(position),position);
                }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class FoodPreparationViewHolder extends RecyclerView.ViewHolder {

        protected TextView text,textnum;

        public FoodPreparationViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvsteps);
            textnum = (TextView) itemView.findViewById(R.id.tvStepsnum);
        }
    }
}