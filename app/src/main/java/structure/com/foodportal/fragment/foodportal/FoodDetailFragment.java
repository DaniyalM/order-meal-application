package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.ExpandableListAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentProductDetailFoodportalBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.helper.UniversalVideoView;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Step;
import structure.com.foodportal.singleton.CarelessSingleton;

public class FoodDetailFragment extends BaseFragment implements
        View.OnClickListener, FoodDetailListner, UniversalVideoView.VideoViewCallback {

    FoodIngredientsAdapter foodIngredientsAdapter;
    FoodPreparationAdapter foodPreparationAdapter;
    FragmentProductDetailFoodportalBinding binding;

    LinearLayoutManager linearLayoutManagerIngredients;
    LinearLayoutManager linearLayoutManagerPreparation;


    ArrayList<Step> steps;
    ArrayList<String> title;
    ArrayList<CustomIngredient> ingredients;
    HashMap<String, ArrayList<Ingredient>> subingrdeints;


    View mBottomLayout;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    FoodDetailModelWrapper foodDetailModel;


    public void setFoodDetailModel(FoodDetailModelWrapper foodDetailModel) {

        this.foodDetailModel = foodDetailModel;

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

        }
        //  getDetails();
    }

    private void initAdapters() {
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoViewCallback(this);
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
        ingredients = new ArrayList<>();
        title = new ArrayList<>();
        subingrdeints = new HashMap<>();





        foodPreparationAdapter = new FoodPreparationAdapter(steps, mainActivity, this);
        binding.rvPreparations.setAdapter(foodPreparationAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStepByStep:

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

                foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);

                if (foodDetailModel != null) {

                    setData(foodDetailModel.getData());

                }


                break;

        }
    }

    private void setData(FoodDetailModel foodDetailModel) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Bitmap bmp = null;
        int videoHeight, videoWidth;
        try {
            retriever.setDataSource(AppConstant.VIDEO_URL + foodDetailModel.getVideo_url());
            bmp = retriever.getFrameAtTime();
            videoHeight = bmp.getHeight();
            videoWidth = bmp.getWidth();
            mVideoLayout.setMinimumHeight(videoWidth);
        } catch (Exception ignored) {

        }


        if (foodDetailModel.getVideo_url() != null) {
            //   binding.vvProductmainVideo.setMediaController(new MediaController(mainActivity));
            binding.videoView.setVideoPath(
                    AppConstant.VIDEO_URL + foodDetailModel.getVideo_url().replace("1080.mp4","640.mp4"));
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
        binding.tvServingDetails.setText("Serving for " + foodDetailModel.getServing_for() + " Person(s)");
        binding.tvServingTime.setText("Cooking Time " + foodDetailModel.getCook_time() + "Min(s)");
        binding.tvPreparationTime.setText("Preparation Time " + foodDetailModel.getPreparation_time() + "Min(s)");
        binding.tvfoodDiscount.setText("" + foodDetailModel.getGallery().getDescription_en());


        steps.addAll(foodDetailModel.getSteps());
        for (int i = 0; i < foodDetailModel.getIngredient().size(); i++) {
            //For Header
            ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getTag_en()==null? foodDetailModel.getIngredient().get(i).getIngredient_en()+" "+ foodDetailModel.getIngredient().get(i).getQuantity():
                    foodDetailModel.getIngredient().get(i).getTag_en(),1,
                    ""+foodDetailModel.getIngredient().get(i).getQuantity(),
                    foodDetailModel.getIngredient().get(i).getQuantity()==null?null:""

                    ));

            for(int k =0 ;k <foodDetailModel.getIngredient().get(i).getSub_ingredients().size();k++){


                //For SubList

                ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getIngredient_en()+" "+
                        foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity()+" "+
                        foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type(),0,
                        foodDetailModel.getIngredient().get(i).getQuantity()==null?null:"",
                        foodDetailModel.getIngredient().get(i).getQuantity()!=null?foodDetailModel.getIngredient().get(i).getQuantity()+""+foodDetailModel.getIngredient().get(i).getQuantity_type():null


                        ));


            }

        }






        foodIngredientsAdapter = new FoodIngredientsAdapter(ingredients, title, mainActivity);
        binding.rvIngredients.setAdapter(foodIngredientsAdapter);


        foodPreparationAdapter.notifyDataSetChanged();
        foodIngredientsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStepClick(Step step, int position) {
        binding.videoView.stopPlayback();
        binding.videoView.closePlayer();
        CarelessSingleton.instance.setState(foodDetailModel.getData(), position);
//        StepFragment stepFragment = new StepFragment();
//        stepFragment.setVideoData(position, foodDetailModel.getData());
//        mainActivity.addFragment(stepFragment, true, true);
//        //  Toast.makeText(mainActivity, "Will be implement later", Toast.LENGTH_SHORT).show();




        StepByStepFragment stepByStepFragment =new StepByStepFragment();
        stepByStepFragment.setVideoData(foodDetailModel.getData(),position);
        mainActivity.addFragment(stepByStepFragment,true,true);


    }

    @Override
    public void onPageChanged(Step step, int position) {

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
        mainActivity.getTitleBar().showTitlebar();
        mainActivity.getTitleBar().showMenuButton(mainActivity);
      //  mainActivity.getTitleBar().setTitle("Cooking Food");
    }
}
