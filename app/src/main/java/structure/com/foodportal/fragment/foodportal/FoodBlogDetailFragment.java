package structure.com.foodportal.fragment.foodportal;


import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCommentsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentBlogBinding;
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
import structure.com.foodportal.interfaces.foodInterfaces.CommentClickListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Comments;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Sections;
import structure.com.foodportal.models.foodModels.Step;
import structure.com.foodportal.singleton.CarelessSingleton;

import static structure.com.foodportal.helper.AppConstant.VIDEO_URL;

public class FoodBlogDetailFragment extends BaseFragment implements FoodHomeListner, CommentClickListner, View.OnClickListener {


    FoodPopularRecipeAdapter foodRelatedAdapter;
    FragmentBlogBinding binding;
    ArrayList<Sections> related;
    FoodDetailModelWrapper foodDetailModel;
    ArrayList<Comments> comments;
    FoodCommentsAdapter foodCommentsAdapter;

    public void setFoodDetailModel(FoodDetailModelWrapper foodDetailModel) {
        this.foodDetailModel = foodDetailModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false);


        setListners();
        return binding.getRoot();
    }

    private void initAdapters() {

        binding.rvRelatedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        related = new ArrayList<>();
    }

    private void setListners() {
        mainActivity.hideBottombar();
        initAdapters();
        binding.tvShowall.setOnClickListener(this);


        if (foodDetailModel != null) {

            setData(foodDetailModel);

            if (foodDetailModel.getRelated().size() > 0) {

                related.addAll(foodDetailModel.getRelated());

                foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this);
                binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
                foodRelatedAdapter.notifyDataSetChanged();
                binding.llRelated.setVisibility(View.VISIBLE);

            } else {
                binding.llRelated.setVisibility(View.GONE);

            }

            WebSettings settings = binding.myWebView.getSettings();
            settings.setMinimumFontSize(18);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setBuiltInZoomControls(false);
            settings.setDisplayZoomControls(false);
            settings.setSupportMultipleWindows(true);

            binding.myWebView.setHorizontalScrollBarEnabled(false);
            binding.myWebView.setWebChromeClient(new WebChromeClient());

            String content = getTransformedBlogContent(foodDetailModel.getData().getContent_en());
            binding.myWebView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
        }

    }

    public static String getTransformedBlogContent(String htmlText) {
        String styleTag = "<style>  p img { width:100%; height:auto;} </style>";
        String htmlStart = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\">" + styleTag + "</head><body> ";
        String htmlEnd = "</body></html>";
        String transformedHtml = htmlStart + htmlText + htmlEnd;
        return transformedHtml;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        // titlebar.setTitle(foodDetailModel.getData().getTitle_en());
        titlebar.showBackButton(mainActivity);
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_BLOG_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodDetailModel != null) {
                    setData(foodDetailModel);
                }
                break;

        }
    }

    private void setData(FoodDetailModelWrapper foodDetailModel) {
        binding.nestedScroll.fullScroll(ScrollView.FOCUS_UP);
        binding.nestedScroll.smoothScrollTo(0, 0);
        binding.tvfoodName.requestFocus();
        comments = new ArrayList<>();


        UIHelper.setImageWithGlide(mainActivity, binding.ivDishImage, foodDetailModel.getData().getBlog_thumb_image_path());
        binding.tvfoodName.setText("" + foodDetailModel.getData().getTitle_en());
        binding.tvServingDetails.setText("" + foodDetailModel.getData().getCountFavorites() + " likes");
        binding.tvServingTime.setText("" + foodDetailModel.getData().getTotalViews() + " views");
        //binding.tvfoodDiscount.setText("" + foodDetailModel.getGallery().getDescription_en());
        binding.tvfoodDiscount.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManagerComment;
        linearLayoutManagerComment = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        binding.rvCommentsSection.setLayoutManager(linearLayoutManagerComment);

        if (this.foodDetailModel.getAllReviews().size() > 0) {

            comments.addAll(this.foodDetailModel.getAllReviews());
            foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, true, false);
            binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
            foodCommentsAdapter.notifyDataSetChanged();

        } else {


        }


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

    public void next(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfoodblog(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_BLOG_DETAILS);
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
    public void onReplyClick(int positon) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvShowall:
                //   player.stop();
                //player.stop(true);
                //  stopPosition = binding.videoView.getCurrentPosition();
                //  EventBus.getDefault().register(this);

                if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                    Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

                } else {
                    CommentsFragment commentsFragment = new CommentsFragment();
                    commentsFragment.setArrayComments(foodDetailModel, false);
                    mainActivity.addFragment(commentsFragment, true, true);
                }
                break;

        }

    }
}
