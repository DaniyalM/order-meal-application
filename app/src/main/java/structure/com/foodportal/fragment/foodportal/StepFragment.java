package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.helper.UniversalVideoView;
import structure.com.foodportal.models.foodModels.FoodDetailModel;

public class StepFragment extends BaseFragment implements View.OnClickListener, UniversalVideoView.VideoViewCallback {


    FoodDetailModel foodDetailModel;
    int positon;
    ArrayList<Integer> startTime = new ArrayList<>();
    ArrayList<Integer> endTime = new ArrayList<>();
    int value = 0;

    public StepFragment() {


    }

    private Timer timer;

    private void timerCounter(int positon) {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mainActivity, "" + mediaplayer.getDuration(), Toast.LENGTH_SHORT).show();

                        // updateUI();
                    }
                });
            }
        };
        timer.schedule(task, 0, (endTime.get(positon) - startTime.get(positon)));
    }

    private int duration = 0;

    private void setDuration() {
        duration = binding.videoView.getDuration();
    }

//    private void updateUI(){
//        if (binding.videoView.getProgress() >= 100) {
//            timer.cancel();
//        }
//        int current = binding.videoView.getCurrentPosition();
//        int progress = current * 100 / duration;
//        pbVideoView.setProgress(progress);
//    }


    public void setdata(FoodDetailModel foodDetailModel, int positon) {
        startTime.add(3);
        startTime.add(21);
        startTime.add(50);
        startTime.add(64);
        startTime.add(93);
        startTime.add(112);

        endTime.add(20);
        endTime.add(49);
        endTime.add(63);
        endTime.add(88);
        endTime.add(111);
        endTime.add(122);
        this.foodDetailModel = foodDetailModel;
        this.positon = positon;
        value =positon;


    }
    UniversalMediaController mMediaController;
    FragmentStepBinding binding;
    MediaPlayer mediaplayer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);
        binding.videoView.setVideoViewCallback(this);
        binding.videoView.setMediaController(mMediaController);
        binding.videoView.setVideoPath(
                AppConstant.VIDEO_URL + foodDetailModel.getVideo_url());
        playvideo(value);
        binding.tvStepDetail.setText(foodDetailModel.getSteps().get(positon).getSteps_en());
        binding.tvSteps.setText("Step "+(value+1));

        binding.videoView.setOnTouchListener(new OnSwipeTouchListner(mainActivity) {
            public void onSwipeTop() {

                Toast.makeText(mainActivity, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                value = -1;
                playvideo(value);
                binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
                Toast.makeText(mainActivity, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                value = +1;
                playvideo(value);
                binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
                Toast.makeText(mainActivity, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(mainActivity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


        return binding.getRoot();


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void playvideo(int positon) {
     //   binding.videoView.seekTo(startTime.get(positon));

        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo((startTime.get(positon))*1000);
                mediaPlayer.setLooping(true);
                mediaplayer = mediaPlayer;
               // timerCounter(value);
            }
        });
        binding.videoView.start();
        mediaplayer.setOnTimedMetaDataAvailableListener(new MediaPlayer.OnTimedMetaDataAvailableListener() {
            @Override
            public void onTimedMetaDataAvailable(MediaPlayer mediaPlayer, TimedMetaData timedMetaData) {

            }
        });
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

    @Override
    public void onScaleChange(boolean isFullscreen) {

    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {


    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

}
