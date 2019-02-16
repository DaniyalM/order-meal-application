package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.Step;

public class StepbyStepAdapter extends RecyclerView.Adapter<StepbyStepAdapter.FoodPreparationViewHolder>{


    private final Context context;
    ArrayList<Step> steps;
    FoodDetailListner foodDetailListner;

    public StepbyStepAdapter(ArrayList<Step> steps, Context context, FoodDetailListner foodDetailListner) {
        this.steps = steps;
        this.context = context;
        this.foodDetailListner = foodDetailListner;
    }

    @Override
    public StepbyStepAdapter.FoodPreparationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        StepbyStepAdapter.FoodPreparationViewHolder viewHolder = new StepbyStepAdapter.FoodPreparationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepbyStepAdapter.FoodPreparationViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.tvStepDetail.setText(""+steps.get(position).getSteps_en());

        holder.textnum.setText("Step "+(position+1));


    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class FoodPreparationViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvStepDetail,textnum;
        SimpleExoPlayerView videoView;

        public FoodPreparationViewHolder(View itemView) {
            super(itemView);

            tvStepDetail = (TextView) itemView.findViewById(R.id.tvStepDetail);
            videoView = (SimpleExoPlayerView) itemView.findViewById(R.id.videoView);
            textnum = (TextView) itemView.findViewById(R.id.tvSteps);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull FoodPreparationViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        Toast.makeText(context, ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

        foodDetailListner.onPageChanged(steps.get(holder.getAdapterPosition()),holder.getAdapterPosition());

    }
}
