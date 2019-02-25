package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import structure.com.foodportal.MyApplication;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.ExpandableListAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentProductDetailFoodportalBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DataSyncEvent;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.helper.UniversalVideoView;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Sections;
import structure.com.foodportal.models.foodModels.Step;
import structure.com.foodportal.singleton.CarelessSingleton;

import static structure.com.foodportal.helper.AppConstant.VIDEO_URL;

public class FoodDetailFragment extends BaseFragment implements
        View.OnClickListener, FoodDetailListner, UniversalVideoView.VideoViewCallback, FoodHomeListner, CacheListener {

    FoodIngredientsAdapter foodIngredientsAdapter;
    FoodPreparationAdapter foodPreparationAdapter;
    FoodPopularRecipeAdapter foodRelatedAdapter;
    FragmentProductDetailFoodportalBinding binding;

    LinearLayoutManager linearLayoutManagerIngredients;
    LinearLayoutManager linearLayoutManagerRelated;
    LinearLayoutManager linearLayoutManagerPreparation;


    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime;
    ArrayList<Step> steps;
    ArrayList<String> title;
    ArrayList<CustomIngredient> ingredients;
    ArrayList<Sections> related;
    HashMap<String, ArrayList<Ingredient>> subingrdeints;


    View mBottomLayout;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    FoodDetailModelWrapper foodDetailModel;


    public void setFoodDetailModel(FoodDetailModelWrapper foodDetailModel) {

        this.foodDetailModel = foodDetailModel;
        startTime = new ArrayList<>();
        endTime = new ArrayList<>();

        for (int i = 0; i < foodDetailModel.getData().getSteps().size(); i++) {

            startTime.add(Utils.getTimeSeconds(foodDetailModel.getData().getSteps().get(i).getStart_time()));
            endTime.add(Utils.getTimeSeconds(foodDetailModel.getData().getSteps().get(i).getEnd_time()));

        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail_foodportal, container, false);
        mVideoView = (UniversalVideoView) binding.getRoot().findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);


        setListners();
        return binding.getRoot();
    }

    private void setListners() {
        mainActivity.hideBottombar();
        initAdapters();
        binding.btnStepByStep.setOnClickListener(this);

        if (foodDetailModel != null) {

            setData(foodDetailModel.getData());

            if (foodDetailModel.getRelated().size() > 0) {

                related.addAll(foodDetailModel.getRelated());

                foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this);
                binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
                foodRelatedAdapter.notifyDataSetChanged();
                binding.llRelated.setVisibility(View.VISIBLE);

            } else {
                binding.llRelated.setVisibility(View.GONE);

            }

        }
        //  getDetails();
    }

    private void initAdapters() {
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoViewCallback(this);

        binding.rvRelatedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));

        linearLayoutManagerIngredients = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerPreparation = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);

        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);


        binding.rvIngredients.setLayoutManager(linearLayoutManagerIngredients);
        binding.rvPreparations.setLayoutManager(linearLayoutManagerPreparation);

        binding.rvIngredients.setItemAnimator(defaultItemAnimator);
        binding.rvPreparations.setItemAnimator(defaultItemAnimator);

        steps = new ArrayList<>();
        related = new ArrayList<>();
        ingredients = new ArrayList<>();
        title = new ArrayList<>();
        subingrdeints = new HashMap<>();


        foodPreparationAdapter = new FoodPreparationAdapter(steps, mainActivity, this);
        binding.rvPreparations.setAdapter(foodPreparationAdapter);


    }

    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStepByStep:
                EventBus.getDefault().register(this);
                binding.videoView.stopPlayback();
                StepFragment stepFragment = new StepFragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    stepFragment.setSharedElementEnterTransition(new DetailsTransition());
                    stepFragment.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    stepFragment.setSharedElementReturnTransition(new DetailsTransition());
                }


                // binding.videoView.stopPlayback();
                //   binding.videoView.closePlayer();
                mMediaController.setMediaPlayer(new UniversalMediaController.MediaPlayerControl() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void pause() {

                    }

                    @Override
                    public int getDuration() {
                        return 0;
                    }

                    @Override
                    public int getCurrentPosition() {
                        return 0;
                    }

                    @Override
                    public void seekTo(int pos) {

                    }

                    @Override
                    public boolean isPlaying() {
                        return false;
                    }

                    @Override
                    public int getBufferPercentage() {
                        return 0;
                    }

                    @Override
                    public boolean canPause() {
                        return false;
                    }

                    @Override
                    public boolean canSeekBackward() {
                        return false;
                    }

                    @Override
                    public boolean canSeekForward() {
                        return false;
                    }

                    @Override
                    public void closePlayer() {

                    }

                    @Override
                    public void setFullscreen(boolean fullscreen) {

                    }

                    @Override
                    public void setFullscreen(boolean fullscreen, int screenOrientation) {

                    }
                });
                CarelessSingleton.instance.setState(foodDetailModel.getData(), 0);
                stepFragment.setVideoData(0, foodDetailModel.getData(), startTime, endTime, mVideoView.getCurrentPosition());
                mainActivity.addFragment(stepFragment, true, true);

                break;
        }
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        // titlebar.setTitle(foodDetailModel.getData().getTitle_en());
        titlebar.showBackButton(mainActivity);
    }

    public void getDetails() {

        // serviceHelper.enqueueCall(webService.getfooddetail(story_slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);


                if (foodDetailModel != null) {
                    ingredients.clear();
                    subingrdeints.clear();
                    steps.clear();
                    related.clear();
                    setData(foodDetailModel.getData());

                }
                if (foodDetailModel.getRelated().size() > 0) {
                    related.clear();
                    related.addAll(foodDetailModel.getRelated());
                    foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this);
                    binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
                    foodRelatedAdapter.notifyDataSetChanged();
                    binding.llRelated.setVisibility(View.VISIBLE);

                } else {
                    binding.llRelated.setVisibility(View.GONE);

                }
                startTime.clear();
                endTime.clear();
                this.foodDetailModel = foodDetailModel;
                for (int i = 0; i < foodDetailModel.getData().getSteps().size(); i++) {

                    startTime.add(Utils.getTimeSeconds(foodDetailModel.getData().getSteps().get(i).getStart_time()));
                    endTime.add(Utils.getTimeSeconds(foodDetailModel.getData().getSteps().get(i).getEnd_time()));

                }



//
//                    LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
//                    FoodDetailFragment detailFragment = new FoodDetailFragment();
//                    detailFragment.setFoodDetailModel(foodDetailModel);
//                    mainActivity.replaceFragment(detailFragment, true, true);


                break;

        }
    }

    private void setData(FoodDetailModel foodDetailModel) {
        binding.nestedScroll.fullScroll(ScrollView.FOCUS_UP);
        binding.nestedScroll.smoothScrollTo(0, 0);
        binding.tvfoodName.requestFocus();


        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Bitmap bmp = null;
        int videoHeight, videoWidth;
        try {
            retriever.setDataSource(VIDEO_URL + foodDetailModel.getVideo_url());
            bmp = retriever.getFrameAtTime();
            videoHeight = bmp.getHeight();
            videoWidth = bmp.getWidth();
            mVideoLayout.setMinimumHeight(videoWidth);
        } catch (Exception ignored) {

        }


        if (foodDetailModel.getVideo_url() != null) {

            HttpProxyCacheServer proxy = MyApplication.getProxy(mainActivity);
            String proxyUrl = proxy.getProxyUrl(VIDEO_URL + foodDetailModel.getVideo_url().replace("1080.mp4", "320.mp4"));
            binding.videoView.setVideoPath(proxyUrl);
            binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.setLooping(true);

                }
            });
            binding.videoView.start();

        }

        UIHelper.setImageWithGlide(mainActivity, binding.ivDishImage, foodDetailModel.getGallery().getPhotos().get(0).getImage_path());
        binding.tvfoodName.setText("" + foodDetailModel.getTitle_en());

        binding.tvServingDetails.setText("" + foodDetailModel.getCountFavorites() + " likes");
        binding.tvServingTime.setText("" + foodDetailModel.getTotalViews() + " views");
        binding.tvPreparationTime.setText("" + foodDetailModel.getCook_time());
        binding.tvfoodDiscount.setText("" + foodDetailModel.getGallery().getDescription_en());


        steps.addAll(foodDetailModel.getSteps());


        for (int i = 0; i < foodDetailModel.getIngredient().size(); i++) {
            //For Header
            ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getTag_en() == null ? foodDetailModel.getIngredient().get(i).getIngredient_en() + " " + foodDetailModel.getIngredient().get(i).getQuantity() :
                    foodDetailModel.getIngredient().get(i).getTag_en(), 1,
                    "" + foodDetailModel.getIngredient().get(i).getQuantity(),
                    foodDetailModel.getIngredient().get(i).getQuantity() == null ? null : ""

            ));

            for (int k = 0; k < foodDetailModel.getIngredient().get(i).getSub_ingredients().size(); k++) {


                //For SubList

                ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getIngredient_en() + " " +
                        foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity() + " " +
                        foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type(), 0,
                        foodDetailModel.getIngredient().get(i).getQuantity() == null ? null : "",
                        foodDetailModel.getIngredient().get(i).getQuantity() != null ? foodDetailModel.getIngredient().get(i).getQuantity() + "" + foodDetailModel.getIngredient().get(i).getQuantity_type() : null


                ));


            }


        }


        foodIngredientsAdapter = new FoodIngredientsAdapter(ingredients, title, mainActivity);
        binding.rvIngredients.setAdapter(foodIngredientsAdapter);


        foodPreparationAdapter.notifyDataSetChanged();
        foodIngredientsAdapter.notifyDataSetChanged();

    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1)
//    public void onHelloWorldEvent(HelloWorldEvent event){
//
//        binding.videoView.seekTo(event.getSeek());
//        binding.videoView.start(); //Or use resume() if it doesn't work. I'm not sure
//
//
//    }


    @Subscribe
    public void onEvent(DataSyncEvent syncStatusMessage) {
        binding.nestedScroll.fullScroll(ScrollView.FOCUS_UP);
        binding.nestedScroll.smoothScrollTo(0, 0);
        binding.tvfoodName.requestFocus();

        EventBus.getDefault().unregister(this);
        HttpProxyCacheServer proxy = MyApplication.getProxy(mainActivity);
        String proxyUrl = proxy.getProxyUrl(VIDEO_URL + foodDetailModel.getData().getVideo_url().replace("1080.mp4", "320.mp4"));
        binding.videoView.setVideoPath(proxyUrl);
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //mediaPlayer.seekTo(syncStatusMessage.getSyncStatusMessage());
                mediaPlayer.setLooping(true);
                mVideoView.seekTo(syncStatusMessage.getSyncStatusMessage());

            }
        });
        binding.videoView.start();
    }

    int stopPosition;

    @Override
    public void onStepClick(Step step, int position) {


        //  stopPosition = binding.videoView.getCurrentPosition();
        EventBus.getDefault().register(this);
        binding.videoView.stopPlayback();
        //binding.videoView.closePlayer();
        CarelessSingleton.instance.setState(foodDetailModel.getData(), position);
        StepFragment stepFragment = new StepFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stepFragment.setSharedElementEnterTransition(new DetailsTransition());
            stepFragment.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            stepFragment.setSharedElementReturnTransition(new DetailsTransition());
        }

        stepFragment.setVideoData(position, foodDetailModel.getData(), startTime, endTime, mVideoView.getCurrentPosition());
        mainActivity.addFragment(stepFragment, true, false);
        //  Toast.makeText(mainActivity, "Will be implement later", Toast.LENGTH_SHORT).show();


//
//        CarelessSingleton.instance.setState(foodDetailModel.getData(), position);
//        StepByStepFragment stepByStepFragment =new StepByStepFragment();
//        stepByStepFragment.setVideoData(foodDetailModel.getData(),position,startTime,endTime);
//        mainActivity.addFragment(stepByStepFragment,true,true);


    }

    @Override
    public void onPageChanged(Step step, StepbyStepAdapter.FoodPreparationViewHolder holder, int position) {


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
        //   mediaPlayer.stop();
        //   mainActivity.showLoader();
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        //   mediaPlayer.start();
        // mainActivity.hideLoader();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mainActivity.getSupportFragmentManager().getFragments().size() > 3) {
            mainActivity.getTitleBar().showTitlebar();
            mainActivity.getTitleBar().showBackButton(mainActivity);

        } else {

            mainActivity.getTitleBar().showTitlebar();
            mainActivity.getTitleBar().showMenuButton(mainActivity);
        }
        //  mainActivity.getTitleBar().setTitle("Cooking Food");
    }

    @Override
    public void popularrecipe(int pos) {

        next(related.get(pos).getSlug());

    }

    @Override
    public void featuredrecipe(int pos) {

    }

    @Override
    public void betterforurbites(int pos) {

    }

    @Override
    public void categorySliderClick(int position) {

    }

    public void next(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
        else if (LocalDataHelper.readFromFile(mainActivity, "Detail").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity, "Detail").equalsIgnoreCase("")) {

            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();


        } else {
//            Gson g = new Gson();
//            FoodDetailModelWrapper foodDetailModel = g.fromJson(LocalDataHelper.readFromFile(mainActivity,"Detail"), FoodDetailModelWrapper.class);
//            FoodDetailFragment detailFragment = new FoodDetailFragment();
//            detailFragment.setFoodDetailModel(foodDetailModel);
//            mainActivity.addFragment(detailFragment, true, true);

        }


    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {

        if (url.equalsIgnoreCase(VIDEO_URL + foodDetailModel.getData().getVideo_url().replace("1080.mp4", "320.mp4"))) {

            binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.setLooping(true);
                    try {

                        mediaPlayer.setDataSource(cacheFile.getAbsolutePath());
                        Log.d("Video  played", "playing: ");
                    } catch (IOException e) {

                        Log.d("Video Not played", "onPrepared: ");
                    }

                }
            });
            binding.videoView.start();


        }

    }
//    int stopPosition;
//    @Override
//    public void onPause() {
//       // Log.d(TAG, "onPause called");
//        super.onPause();
//         stopPosition = binding.videoView.getCurrentPosition(); //stopPosition is an int
//        binding.videoView.pause();
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//       // Log.d(TAG, "onResume called");
//        binding.videoView.seekTo(stopPosition);
//        binding.videoView.start(); //Or use resume() if it doesn't work. I'm not sure
//    }

}
