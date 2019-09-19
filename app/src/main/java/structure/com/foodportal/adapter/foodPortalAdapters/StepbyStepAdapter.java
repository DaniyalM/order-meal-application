package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.databinding.FragmentStepBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.Step;

public class StepbyStepAdapter extends RecyclerView.Adapter<StepbyStepAdapter.FoodPreparationViewHolder>{


    private final Context context;
    ArrayList<Step> steps;
    FoodDetailListner foodDetailListner;
    String videoUrl;
    ArrayList<Integer> starttime;
    ArrayList<Integer> endtime;
    MainActivity mainActivity;

    public StepbyStepAdapter(ArrayList<Step> steps, Context context, FoodDetailListner foodDetailListner, String videoUrl,
                             ArrayList<Integer> starttime , ArrayList<Integer> endtime, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.starttime = starttime;
        this.endtime = endtime;
        this.steps = steps;
        this.videoUrl = videoUrl;
        this.context = context;
        this.foodDetailListner = foodDetailListner;



    }

    @Override
    public StepbyStepAdapter.FoodPreparationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        StepbyStepAdapter.FoodPreparationViewHolder viewHolder = new StepbyStepAdapter.FoodPreparationViewHolder(v);
        return viewHolder;
    }
   ;
    StepbyStepAdapter.FoodPreparationViewHolder holder;
    @Override
    public void onBindViewHolder(StepbyStepAdapter.FoodPreparationViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        holder.tvStepDetail.setText(""+steps.get(position).getSteps_en());

        holder.textnum.setText("Step "+(position+1));
        this.holder =holder;


    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void playvideo() {



    }

    public static class FoodPreparationViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStepDetail,textnum;
        public SimpleExoPlayerView videoView;

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

        foodDetailListner.onPageChanged(steps.get(holder.getAdapterPosition()),holder,holder.getAdapterPosition());

    }



    SimpleExoPlayer player;
    MediaSource mediaSource;


    private void playvideo(StepbyStepAdapter.FoodPreparationViewHolder holder ,int value) {

        holder.videoView.requestFocus();
        holder.videoView.hideController();
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(), new DefaultLoadControl());
        holder.videoView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.seekTo(starttime.get(value), endtime.get(value));
        player.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
        Uri uri = Uri.parse(AppConstant.VIDEO_URL + videoUrl.replace("1080.mp4", "320.mp4"));

        mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, true);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private Timer timer;
    TimerTask task;

    private void timerCounter(int positon) {

        if (timer != null) {
            task.cancel();
            task = null;
            timer.cancel();
            timer.purge();
            timer = null;
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        Log.d("Time", "Seconds: " + player.getCurrentPosition() / 1000);
                        //if (mExoPlayer.getCurrentPosition() > (((endtime.get(value) * 1000)))) {

                            //  SeekParameters seekParameters =new SeekParameters(startTime.get(value)*1000,endTime.get(value)*1000);


                            //mExoPlayer.setSeekParameters(seekParameters);

                          ///  mExoPlayer.seekTo(endtime.get(value) * 1000);
                            // mExoPlayer.stop();
                            // timer.cancel();
                            //  timer.purge();
                            // task.cancel();
                            //  mExoPlayer.stop(true);
                            playvideo();
                       // }

                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);
    }


}
