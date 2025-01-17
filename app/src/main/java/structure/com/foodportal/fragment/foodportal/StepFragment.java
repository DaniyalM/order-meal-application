package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.databinding.FragmentStepBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.DataSyncEvent;
import structure.com.foodportal.helper.OnSwipeTouchListner;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.singleton.CarelessSingleton;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.VIDEO_URL;

public class StepFragment extends BaseFragment implements View.OnClickListener, SimpleExoPlayer.EventListener {


    FoodDetailModel foodDetailModel;
    int positon;
    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime;
    int value = 0;
    int tobeplayed = 0;
    ArrayList<CustomIngredient> ingredients;
    LinearLayoutManager linearLayoutManagerIngredients;
    FoodIngredientsAdapter foodIngredientsAdapter;
    public StepFragment() {
    }

    public void setVideoData(int positon, FoodDetailModel foodDetailModel, ArrayList<Integer> startTime, ArrayList<Integer> endTime, int tobeplayed) {
        this.positon = positon;
        value = positon;
        if (foodDetailModel.getSteps() != null) {

            this.foodDetailModel = foodDetailModel;
        } else {
            this.foodDetailModel = foodDetailModel.getSpecial_recipe_story();

        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.tobeplayed = tobeplayed;


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
                        if (player.getCurrentPosition() > (((endTime.get(value) * 1000)))) {

                            //  SeekParameters seekParameters =new SeekParameters(startTime.get(value)*1000,endTime.get(value)*1000);


                            //player.setSeekParameters(seekParameters);

                            player.seekTo(startTime.get(value) * 1000);
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
    int onestep = 0;
    LinearLayout llmainingredients;
    View vingredients;
    RecyclerView rvIngredients;
    TextView tvingredients;
    private int mLang;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setscreensize() {

        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.x;
        binding.videoView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        // binding.videoView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        setscreensize();
        storiesProgressView = binding.getRoot().findViewById(R.id.progressView);
        llmainingredients = binding.getRoot().findViewById(R.id.llmainingredients);
        vingredients = binding.getRoot().findViewById(R.id.vingredients);
        rvIngredients = binding.getRoot().findViewById(R.id.rvIngredients);
        tvingredients = binding.getRoot().findViewById(R.id.tvingredients);
        linearLayoutManagerIngredients = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.video_layout), "stepbystep");

        tvingredients.setOnClickListener(this);
        vingredients.setOnClickListener(this);


        foodDetailModel = (FoodDetailModel) CarelessSingleton.instance.getState();
        value = CarelessSingleton.instance.getStateposition();
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);

        onestep = 100 / foodDetailModel.getSteps().size();
        storiesProgressView.setProgress((int) onestep * value + 1);

        binding.videoView.requestFocus();
        binding.videoView.hideController();
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mainActivity),
                new DefaultTrackSelector(), new DefaultLoadControl());
        player.setVolume(0f);
        binding.videoView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.seekTo(startTime.get(value), endTime.get(value));
        player.addListener(this);
        player.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
        Uri uri = Uri.parse(foodDetailModel.getVideo_path().replace("1080.mp4", "320.mp4"));

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


        mLang = preferenceHelper.getSelectedLanguage();
        tvingredients.setText(mLang == ENGLISH ? getString(R.string.ingredients_en) : getString(R.string.ingredients_ur));
        binding.layoutRoot.setLayoutDirection(mLang == ENGLISH ? View.LAYOUT_DIRECTION_LTR : View.LAYOUT_DIRECTION_RTL);
        binding.llmainingredients.setLayoutDirection(mLang == ENGLISH ? View.LAYOUT_DIRECTION_LTR : View.LAYOUT_DIRECTION_RTL);

        //timerCounter(positon);


//        binding.videoView.setVideoPath(
//                AppConstant.VIDEO_URL + foodDetailModel.getVideo_url());
        UIHelper.setImageWithGlide(mainActivity, binding.imageforrecplace, foodDetailModel.getGallery().getPhotos().get(0).getImage_path());

        //  playvideo(value);
        binding.tvStepDetail.setText(mLang == ENGLISH ? foodDetailModel.getSteps().get(value).getSteps_en() : foodDetailModel.getSteps().get(value).getSteps_ur());
        binding.tvSteps.setText((value + 1) +" of "+ foodDetailModel.getSteps().size());

        binding.videoLayout.setOnTouchListener(new OnSwipeTouchListner(mainActivity) {
            public void onSwipeTop() {

                //   Toast.makeText(mainActivity, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {

                if (value == 0) {
                    mainActivity.onBackPressed();
                    return;
                }
                value--;
                if (value >= 0) {
                    // storiesProgressView.setProgress((int) (onestep * (1 + value)));

                    ProgressBarAnimation anim = new ProgressBarAnimation(storiesProgressView, storiesProgressView.getProgress(), (int) (onestep * (1 + value)));
                    anim.setDuration(1200);
                    storiesProgressView.startAnimation(anim);

                    //value = value-1;
                    playvideo();
                    binding.tvStepDetail.setText(mLang == ENGLISH ? foodDetailModel.getSteps().get(value).getSteps_en() : foodDetailModel.getSteps().get(value).getSteps_ur());
                    binding.tvSteps.setText((value + 1) +" of "+ foodDetailModel.getSteps().size());
                }
                //  Toast.makeText(mainActivity, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {

                if (value < 0) {

                    return;
                } else if (value == foodDetailModel.getSteps().size() - 1) {

                    value = 0;
                    ProgressBarAnimation anim = new ProgressBarAnimation(storiesProgressView, storiesProgressView.getProgress(), (int) (onestep * (1 + value)));
                    anim.setDuration(1200);
                    storiesProgressView.startAnimation(anim);
                    binding.tvStepDetail.setText(mLang == ENGLISH ? foodDetailModel.getSteps().get(value).getSteps_en() : foodDetailModel.getSteps().get(value).getSteps_ur());
                    binding.tvSteps.setText((value + 1) +" of "+ foodDetailModel.getSteps().size());
                    //  storiesProgressView.setProgress((int) (onestep * (1 + value)));
                    playvideo();
                    return;

                }


                value++;

                if (value > 0) {
                    //  value = value+1;
                    ProgressBarAnimation anim = new ProgressBarAnimation(storiesProgressView, storiesProgressView.getProgress(), (int) (onestep * (1 + value)));
                    anim.setDuration(1200);
                    storiesProgressView.startAnimation(anim);

                    // storiesProgressView.setProgress((int) (onestep * (1 + value)));

                    playvideo();
                    binding.tvStepDetail.setText(mLang == ENGLISH ? foodDetailModel.getSteps().get(value).getSteps_en() : foodDetailModel.getSteps().get(value).getSteps_ur());
                    binding.tvSteps.setText((value + 1) +" of "+ foodDetailModel.getSteps().size());
                }
                // Toast.makeText(mainActivity, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                //  Toast.makeText(mainActivity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        setingrdient(foodDetailModel);
        return binding.getRoot();


    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().post(new DataSyncEvent(tobeplayed));
        super.onDestroyView();
        // mainActivity.getTitleBar().showTitlebar();
        //   mainActivity.getTitleBar().showBackButton(mainActivity);


        //EventBus.getDefault().post(new HelloWorldEvent(mediaplayer.getCurrentPosition()));
      if(timer!=null){

          timer.cancel();
          timer.purge();
          task.cancel();
          player.stop();
          player.stop(true);
      }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void playvideo() {


        player.seekTo((startTime.get(value)) * 1000);
        timerCounter(positon);


    }

    @Override
    public void onClick(View view) {



        switch (view.getId()){


            case R.id.tvingredients:

                llmainingredients.setVisibility(View.VISIBLE);
                break;

            case R.id.vingredients:
                llmainingredients.setVisibility(View.GONE);
                break;

        }

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
        private float to;

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

    public  void setingrdient(FoodDetailModel foodDetailModel){
        linearLayoutManagerIngredients = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        ingredients =new ArrayList<>();
//        for (int i = 0; i < foodDetailModel.getIngredient().size(); i++) {
//            //For Header
//
//            if(foodDetailModel.getIngredient().get(i).getTag_en()!=null){
//
//                ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getTag_en(),1, " "," "));
//
//            }else{
//
//                ingredients.add(new CustomIngredient(" ",0, foodDetailModel.getIngredient().get(i).getIngredient_en(),foodDetailModel.getIngredient().get(i).getQuantity_en()+" "+(
//                        foodDetailModel.getIngredient().get(i).getQuantity_type_en()!=null ? foodDetailModel.getIngredient().get(i).getQuantity_type_en(): " ")));
//            }
//
//            for (int k = 0; k < foodDetailModel.getIngredient().get(i).getSub_ingredients().size(); k++) {
//
//
//                //For SubList
//
//                ingredients.add(new CustomIngredient(" ",
//                        0,
//                        foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getIngredient_en(),
//                        foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_en()+" "+(
//                                foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type_en()!=null ? foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type_en(): " ")));
//
//
//            }
//
//
//        }

        for (int i = 0; i < foodDetailModel.getIngredient().size(); i++) {
            //For Header
            String tag = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getTag_en() : foodDetailModel.getIngredient().get(i).getTag_ur();
            String ingredient = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getIngredient_en() : foodDetailModel.getIngredient().get(i).getIngredient_ur();
            String quantity = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getQuantity_en() : foodDetailModel.getIngredient().get(i).getQuantity_ur();
            String quantityType = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getQuantity_type_en() : foodDetailModel.getIngredient().get(i).getQuantity_type_ur();

            if (tag != null) {

                ingredients.add(new CustomIngredient(tag, 1, " ", " "));

            } else {

                ingredients.add(new CustomIngredient(" ", 0, ingredient,
                        (quantity != null ? quantity : " ") + " " + (quantityType != null ? quantityType : " ")));
            }

            for (int k = 0; k < foodDetailModel.getIngredient().get(i).getSub_ingredients().size(); k++) {

                //For SubList
                ingredient = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getIngredient_en() : foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getIngredient_ur();
                quantity = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_en() : foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_ur();
                quantityType = mLang == ENGLISH ? foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type_en() : foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type_ur();

                ingredients.add(new CustomIngredient(" ", 0,ingredient,
                        (quantity != null ? quantity : " ") + " " + (quantityType != null ? quantityType : " ")));


            }


        }

        foodIngredientsAdapter = new FoodIngredientsAdapter(ingredients, null, mainActivity);
        rvIngredients.setLayoutManager(linearLayoutManagerIngredients);
       rvIngredients.setAdapter(foodIngredientsAdapter);
       //foodIngredientsAdapter.notifyDataSetChanged();

    }


    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
       // timerCounter(value);
        startPlayer();

        player.seekTo((startTime.get(value)) * 1000);
    }
    private void pausePlayer(){
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }
    private void startPlayer(){
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }
}
