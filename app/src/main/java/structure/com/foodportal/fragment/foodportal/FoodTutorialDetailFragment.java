package structure.com.foodportal.fragment.foodportal;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.Html;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import structure.com.foodportal.MyApplication;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentProductDetailFoodportalBinding;
import structure.com.foodportal.databinding.FragmentTutorialDetailBinding;
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

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.VIDEO_URL;

public class FoodTutorialDetailFragment extends BaseFragment implements UniversalVideoView.VideoViewCallback, FoodHomeListner, CacheListener, View.OnClickListener {


    FoodPopularRecipeAdapter foodRelatedAdapter;
    FragmentTutorialDetailBinding binding;
    ArrayList<Sections> related;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    FoodDetailModelWrapper foodDetailModel;
    private int mLang;

    public void setFoodDetailModel(FoodDetailModelWrapper foodDetailModel) {
        this.foodDetailModel = foodDetailModel;
    }

    ImageView sharing;

    private void setValuesByLanguage() {
        switch (mLang) {
            case ENGLISH:
            default:
                binding.linearLayoutMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                binding.todisplay.setText(getString(R.string.you_have_to_en));
                binding.tvRelatedTutorials.setText(getString(R.string.related_tutorials_en));
                break;

            case AppConstant.Language.URDU:
                binding.linearLayoutMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                binding.todisplay.setText(getString(R.string.you_have_to_ur));
                binding.tvRelatedTutorials.setText(getString(R.string.related_tutorials_ur));
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutorial_detail, container, false);
        mVideoView = (UniversalVideoView) binding.getRoot().findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);

        mLang = preferenceHelper.getSelectedLanguage();
        setValuesByLanguage();

        setListners();
        return binding.getRoot();
    }

    private void setListners() {
        mainActivity.hideBottombar();
        sharing = (ImageView) binding.getRoot().findViewById(R.id.sharing);
        sharing.setOnClickListener(this::onClick);
        initAdapters();


        if (foodDetailModel != null) {

            setData(foodDetailModel.getData());

            if (foodDetailModel.getRelated().size() > 0) {

                related.addAll(foodDetailModel.getRelated());

                foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this);
                foodRelatedAdapter.setPreferenceHelper(preferenceHelper);

                binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
                foodRelatedAdapter.notifyDataSetChanged();
                binding.llRelated.setVisibility(View.VISIBLE);

            } else {
                binding.llRelated.setVisibility(View.GONE);

            }

            String content = mLang == ENGLISH ? foodDetailModel.getData().getContent_en() : foodDetailModel.getData().getContent_ur();

            if (content != null) {

                Log.d("ContentTest", "setListners:\n" + content);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


                    WebSettings settings = binding.myWebView.getSettings();
                    settings.setMinimumFontSize(18);
                    settings.setLoadWithOverviewMode(true);
                    settings.setUseWideViewPort(true);
                    settings.setBuiltInZoomControls(false);
                    settings.setDisplayZoomControls(false);
                    binding.myWebView.setWebChromeClient(new WebChromeClient());
                    String changeFontHtml = changedHeaderHtml(content);
                    binding.myWebView.loadDataWithBaseURL(null, changeFontHtml,
                            "text/html", "UTF-8", null);
                    binding.myWebView.getSettings().setSupportMultipleWindows(true);
                    //  binding.myWebView.loadDataWithBaseURL("", foodDetailModel.getData().getContent_en(), "text/html", "UTF-8", "");


                } else {
                    WebSettings settings = binding.myWebView.getSettings();
                    settings.setMinimumFontSize(18);
                    settings.setLoadWithOverviewMode(true);
                    settings.setUseWideViewPort(true);
                    settings.setBuiltInZoomControls(false);
                    settings.setDisplayZoomControls(false);
                    binding.myWebView.setWebChromeClient(new WebChromeClient());
                    String changeFontHtml = changedHeaderHtml(content);
                    binding.myWebView.loadDataWithBaseURL(null, changeFontHtml,
                            "text/html", "UTF-8", null);
                    //  binding.myWebView.loadDataWithBaseURL("", foodDetailModel.getData().getContent_en(), "text/html", "UTF-8", "");
                    binding.myWebView.getSettings().setSupportMultipleWindows(true);

                }

            }


        }
        //  getDetails();
    }

//    public static String changedHeaderHtml(String htmlText) {
//        String jsTag = "<style> p img { width:100%; height:auto;} </style>";
//        // String head = "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>";
//        String head = "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\">" + jsTag + "</head>";
//
//        String closedTag = "</body></html>";
//        String changeFontHtml = head + htmlText + closedTag;
//        return changeFontHtml;
//    }

    public static String changedHeaderHtml(String htmlText) {
        String styleTag = "<style> p img { width:100%; height:auto;} h2 img { width:100%; height:auto;} </style>";
        String htmlStart = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\">" + styleTag + "</head><body> ";
        String htmlEnd = "</body></html>";
        String transformedHtml = htmlStart + htmlText + htmlEnd;
        return transformedHtml;
    }


    private void initAdapters() {
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoViewCallback(this);
        binding.rvRelatedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        related = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sharing:
                String locale = mLang == ENGLISH ? "en" : "ur";
                String shareBody = "https://food.tribune.com.pk/" + locale + "/tutorial/" + foodDetailModel.getData().getSlug();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "www.SubjectHere.com");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));


                //showMenuPopup(sharing);
                //onButtonShowPopupWindowClick(sharing);
//                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//                whatsappIntent.setType("text/plain");
//                whatsappIntent.setPackage("com.whatsapp");
//                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//                try {
//                    mainActivity.startActivity(whatsappIntent);
//                } catch (android.content.ActivityNotFoundException ex) {
//
//
//                    Toast.makeText(mainActivity, "Unable to find watsapp", Toast.LENGTH_SHORT).show();
//                }

//                String shareBody = "Here is the share content body";
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));


//                String shareBody = "Here is the share content body";
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "www.SubjectHere.com");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));

                break;

        }


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
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);


                if (foodDetailModel != null) {
                    related.clear();
                    setData(foodDetailModel.getData());

                }
                if (foodDetailModel.getRelated().size() > 0) {
                    related.clear();
                    related.addAll(foodDetailModel.getRelated());
                    foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this);
                    foodRelatedAdapter.setPreferenceHelper(preferenceHelper);

                    binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
                    foodRelatedAdapter.notifyDataSetChanged();
                    binding.llRelated.setVisibility(View.VISIBLE);

                } else {
                    binding.llRelated.setVisibility(View.GONE);

                }

                String content = mLang == ENGLISH ? foodDetailModel.getData().getContent_en() : foodDetailModel.getData().getContent_ur();

                if (content != null) {

                    Log.d("ContentTest", "ResponseSuccess:\n" + content);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        WebSettings settings = binding.myWebView.getSettings();
                        settings.setMinimumFontSize(18);
                        settings.setLoadWithOverviewMode(true);
                        settings.setUseWideViewPort(true);
                        settings.setBuiltInZoomControls(false);
                        settings.setDisplayZoomControls(false);
                        binding.myWebView.setWebChromeClient(new WebChromeClient());
                        String changeFontHtml = changedHeaderHtml(content);
                        binding.myWebView.loadDataWithBaseURL(null, changeFontHtml,
                                "text/html", "UTF-8", null);
                        //  binding.myWebView.loadDataWithBaseURL("", foodDetailModel.getData().getContent_en(), "text/html", "UTF-8", "");
                        binding.myWebView.getSettings().setSupportMultipleWindows(true);


                    } else {
                        WebSettings settings = binding.myWebView.getSettings();
                        settings.setMinimumFontSize(18);
                        settings.setLoadWithOverviewMode(true);
                        settings.setUseWideViewPort(true);
                        settings.setBuiltInZoomControls(false);
                        settings.setDisplayZoomControls(false);
                        binding.myWebView.setWebChromeClient(new WebChromeClient());
                        String changeFontHtml = changedHeaderHtml(content);
                        binding.myWebView.loadDataWithBaseURL(null, changeFontHtml,
                                "text/html", "UTF-8", null);
                        //  binding.myWebView.loadDataWithBaseURL("", foodDetailModel.getData().getContent_en(), "text/html", "UTF-8", "");
                        binding.myWebView.getSettings().setSupportMultipleWindows(true);

                    }

                }

                this.foodDetailModel = foodDetailModel;


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
            if (preferenceHelper.getAutoPlay()) {
                binding.videoView.start();
            } else {


            }
            //binding.videoView.start();

        }

        UIHelper.setImageWithGlide(mainActivity, binding.ivDishImage, foodDetailModel.getBlog_thumb_image_path());
        binding.tvfoodName.setText(mLang == ENGLISH ? foodDetailModel.getTitle_en() : foodDetailModel.getTitle_ur());
        binding.tvServingDetails.setText("" + foodDetailModel.getCountFavorites() + " likes");
        binding.tvServingTime.setText("" + foodDetailModel.getTotalViews() + " views");
        binding.tvPreparationTime.setText("" + foodDetailModel.getCook_time());
        binding.tvfoodDiscount.setText(mLang == ENGLISH ? foodDetailModel.getExcerpt_en() : foodDetailModel.getExcerpt_ur());


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
    public void onBlogClick(int pos) {

    }

    @Override
    public void popularrecipe(int pos) {

        next(related.get(pos).getSlug());

    }

    @Override
    public void recommendedrecipe(int pos) {

    }

    @Override
    public void featuredrecipe(int pos) {

    }

    @Override
    public void betterforurbites(int pos) {

    }

    @Override
    public void recentlyViewed(int pos) {

    }

    @Override
    public void categorySliderClick(int position) {

    }

    @Override
    public void masterTechniquesClick(int position) {

    }

    @Override
    public void onSaveRecipe(int slug) {

    }

    @Override
    public void onFavoriteRecipe(int slug) {

    }

    public void next(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfoodtutorialdetail(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS);
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
