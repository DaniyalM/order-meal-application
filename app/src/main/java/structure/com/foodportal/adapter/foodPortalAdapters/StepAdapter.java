package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.FoodPreparationViewHolder> {

    ArrayList<Step> steps;
    FoodDetailListner foodDetailListner;

    public StepAdapter(ArrayList<Step> steps, Context context, FoodDetailListner foodDetailListner) {
        this.steps = steps;
        this.foodDetailListner = foodDetailListner;
    }

    @Override
    public StepAdapter.FoodPreparationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        FoodPreparationViewHolder viewHolder = new FoodPreparationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.FoodPreparationViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.tvStepDetail.setText(""+steps.get(position).getSteps_en());
        holder.textnum.setText(""+(position+1));


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
        }
    }
}