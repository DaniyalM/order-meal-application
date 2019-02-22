package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentStepBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.SignupFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.OnSwipeTouchListner;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.helper.UniversalVideoView;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.singleton.CarelessSingleton;

public class StepFragment extends BaseFragment implements View.OnClickListener,SimpleExoPlayer.EventListener{


    FoodDetailModel foodDetailModel;
    int positon;
    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime ;
    int value = 0;

    public StepFragment() {
    }

    public void setVideoData(int positon, FoodDetailModel foodDetailModel,  ArrayList<Integer> startTime, ArrayList<Integer> endTime) {
        this.positon = positon;
        value = positon;
        this.foodDetailModel = foodDetailModel;
        this.startTime =startTime;
        this.endTime =endTime;



    }

    private Timer timer;
    TimerTask task;
    private void timerCounter(int positon) {
        timer = new Timer();

        //timer.purge();


         task = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        //Toast.makeText(mainActivity, "" + player.getCurrentPosition(), Toast.LENGTH_SHORT).show();
                        if(player.getCurrentPosition()>(((endTime.get(positon)*1000))))
                        {
                           // SeekParameters seekParameters =new SeekParameters(startTime.get(positon),endTime.get(positon));
                            player.seekTo(startTime.get(positon));
                            player.stop();
                            timer.cancel();
                            timer.purge();
                            task.cancel();
                            playvideo(positon);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);

        startTime.add(2);
        startTime.add(11);
        startTime.add(18);
        startTime.add(24);
        startTime.add(42);


        endTime.add(8);
        endTime.add(17);
        endTime.add(23);
        endTime.add(35);
        endTime.add(49);



        foodDetailModel = (FoodDetailModel) CarelessSingleton.instance.getState();
        value = CarelessSingleton.instance.getStateposition();
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);



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
        Uri uri = Uri.parse(AppConstant.VIDEO_URL + foodDetailModel.getVideo_url().replace("1080.mp4","320.mp4"));

         mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, true);
        playvideo(value);
        timerCounter(positon);






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
                value--;
              if(value>0)
              {
                  //value = value-1;
                playvideo(value);
                binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
                binding.tvSteps.setText("Step "+(value+1));
              }
              //  Toast.makeText(mainActivity, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                value++;
                if(value>0){
              //  value = value+1;
                playvideo(value);
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
        super.onDestroyView();
        mainActivity.getTitleBar().showTitlebar();
        mainActivity.getTitleBar().showBackButton(mainActivity);

        timer.cancel();
        timer.purge();
        task.cancel();
        player.stop();
        player.stop(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void playvideo(int positon) {


        player.seekTo((startTime.get(value)) * 1000);
        timerCounter(value);


//        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.videoView.start();
//                mediaPlayer.setLooping(true);
//                mediaPlayer.seekTo((startTime.get(positon)) * 1000);
//                mediaplayer = mediaPlayer;
//                 timerCounter(value);
//            }
//        });


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
        titlebar.setTitle(foodDetailModel.getTitle_en());
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
}
