package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.crashlytics.android.answers.EventLogger;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.video.VideoListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentStepBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.SignupFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DataSyncEvent;
import structure.com.foodportal.helper.OnSwipeTouchListner;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.helper.UniversalVideoView;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.singleton.CarelessSingleton;

import static structure.com.foodportal.helper.AppConstant.VIDEO_URL;

public class StepFragment extends BaseFragment implements View.OnClickListener,SimpleExoPlayer.EventListener{


    FoodDetailModel foodDetailModel;
    int positon;
    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime ;
    int value = 0;
    int tobeplayed= 0;
    public StepFragment() {
    }

    public void setVideoData(int positon, FoodDetailModel foodDetailModel,  ArrayList<Integer> startTime, ArrayList<Integer> endTime,int tobeplayed) {
        this.positon = positon;
        value = positon;
        this.foodDetailModel = foodDetailModel;
        this.startTime =startTime;
        this.endTime =endTime;
        this.tobeplayed =tobeplayed;



    }

    private Timer timer;
    TimerTask task;
    private void timerCounter(int positon) {

        if(timer != null) {
            task.cancel();
            task =null;
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
                        Log.d("Time", "Seconds: "+player.getCurrentPosition()/1000);
                        if(player.getCurrentPosition()>(((endTime.get(value)*1000))))
                        {

                         //  SeekParameters seekParameters =new SeekParameters(startTime.get(value)*1000,endTime.get(value)*1000);


                           //player.setSeekParameters(seekParameters);

                           player.seekTo(startTime.get(value)*1000);
                           // player.stop();
                           // timer.cancel();
                          //  timer.purge();
                           // task.cancel();
                          //  player.stop(true);
                            playvideo();
                        }

                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);
    }

    private int duration = 0;

    private void setDuration() {
     //   duration = binding.videoView.getDuration();
    }

    UniversalMediaController mMediaController;
    FragmentStepBinding binding;
    MediaPlayer mediaplayer;
    SimpleExoPlayer player;
    MediaSource mediaSource;
    ProgressBar storiesProgressView;
    int onestep =0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        storiesProgressView = binding.getRoot().findViewById(R.id.progressView);
        ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.video_layout), "stepbystep");




        foodDetailModel = (FoodDetailModel) CarelessSingleton.instance.getState();
        value = CarelessSingleton.instance.getStateposition();
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);

        onestep = 100 / foodDetailModel.getSteps().size();
        storiesProgressView.setProgress((int) onestep*value+1);

        binding.videoView.requestFocus();
        binding.videoView.hideController();
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mainActivity),
                new DefaultTrackSelector(), new DefaultLoadControl());
        binding.videoView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.seekTo(startTime.get(value), endTime.get(value));
        player.addListener(this);
        player.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
        Uri uri = Uri.parse(foodDetailModel.getVideo_path().replace("1080.mp4","720.mp4"));

         mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, true);

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Bitmap bmp = null;
        int videoHeight, videoWidth;
        try {
            retriever.setDataSource(VIDEO_URL + foodDetailModel.getVideo_url());
            bmp = retriever.getFrameAtTime();
            videoHeight = bmp.getHeight();
            videoWidth = bmp.getWidth();
            binding.videoLayout.setMinimumHeight(videoWidth);
        } catch (Exception ignored) {

        }

        playvideo();

        timerCounter(value);


        //timerCounter(positon);








//        binding.videoView.setVideoPath(
//                AppConstant.VIDEO_URL + foodDetailModel.getVideo_url());
        UIHelper.setImageWithGlide(mainActivity, binding.imageforrecplace,foodDetailModel.getGallery().getPhotos().get(0).getImage_path());

      //  playvideo(value);
        binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
        binding.tvSteps.setText("Step " + (value + 1));

        binding.videoLayout.setOnTouchListener(new OnSwipeTouchListner(mainActivity) {
            public void onSwipeTop() {

             //   Toast.makeText(mainActivity, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {

                if(value==0){
                    mainActivity.onBackPressed();
                    return;
                }
                value--;
              if(value >=0)
              {
                 // storiesProgressView.setProgress((int) (onestep * (1 + value)));

                  ProgressBarAnimation anim = new ProgressBarAnimation(storiesProgressView, storiesProgressView.getProgress(), (int) (onestep * (1 + value)));
                  anim.setDuration(1200);
                  storiesProgressView.startAnimation(anim);

                  //value = value-1;
                playvideo();
                binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
                binding.tvSteps.setText("Step "+(value+1));
              }
              //  Toast.makeText(mainActivity, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {

                if(value<0){

                    return;
                }else  if(value==foodDetailModel.getSteps().size()-1){

                    value=0;
                    ProgressBarAnimation anim = new ProgressBarAnimation(storiesProgressView, storiesProgressView.getProgress(), (int) (onestep * (1 + value)));
                    anim.setDuration(1200);
                    storiesProgressView.startAnimation(anim);
                    binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
                    binding.tvSteps.setText("Step "+(value+1));
                    //  storiesProgressView.setProgress((int) (onestep * (1 + value)));
                    playvideo();
                    return;

                }


                value++;

                if(value>0){
              //  value = value+1;
                    ProgressBarAnimation anim = new ProgressBarAnimation(storiesProgressView, storiesProgressView.getProgress(), (int) (onestep * (1 + value)));
                    anim.setDuration(1200);
                    storiesProgressView.startAnimation(anim);

                   // storiesProgressView.setProgress((int) (onestep * (1 + value)));

                    playvideo();
                binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
                binding.tvSteps.setText("Step "+(value+1));
                }
               // Toast.makeText(mainActivity, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
              //  Toast.makeText(mainActivity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


        return binding.getRoot();


    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().post(new DataSyncEvent(tobeplayed));
        super.onDestroyView();
       // mainActivity.getTitleBar().showTitlebar();
     //   mainActivity.getTitleBar().showBackButton(mainActivity);



                //EventBus.getDefault().post(new HelloWorldEvent(mediaplayer.getCurrentPosition()));
        timer.cancel();
        timer.purge();
        task.cancel();
        player.stop();
        player.stop(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void playvideo() {


        player.seekTo((startTime.get(value)) * 1000);
        timerCounter(positon);



    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
      //  titlebar.setTitle(foodDetailModel.getTitle_en());
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {



    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {


    }

    @Override
    public void onSeekProcessed() {



    }


    public class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }



}
