package structure.com.foodportal.fragment.foodportal;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.text.Html;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.danikula.videocache.CacheListener;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.like.LikeButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCommentsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSpecialIngredientAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSpecialStepsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentFoodSpecialDetailBinding;
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
import structure.com.foodportal.interfaces.foodInterfaces.SpecialStepListner;
import structure.com.foodportal.models.foodModels.Comments;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Sections;
import structure.com.foodportal.models.foodModels.SpecialIngedient;
import structure.com.foodportal.models.foodModels.SpecialIngredientSteps;
import structure.com.foodportal.models.foodModels.Step;
import structure.com.foodportal.singleton.CarelessSingleton;

import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;

public class FoodSpecialDetailFragment extends BaseFragment implements
        View.OnClickListener, FoodDetailListner, SimpleExoPlayer.EventListener, UniversalVideoView.VideoViewCallback, FoodHomeListner, CacheListener, CommentClickListner, SpecialStepListner {

    FoodIngredientsAdapter foodIngredientsAdapter;
    FoodPreparationAdapter foodPreparationAdapter;
    FoodPopularRecipeAdapter foodRelatedAdapter;
    FoodCommentsAdapter foodCommentsAdapter;

    FoodSpecialIngredientAdapter foodSpecialIngredientAdapter;
    FoodSpecialStepsAdapter foodSpecialStepsAdapter;


    FragmentFoodSpecialDetailBinding binding;

    LinearLayoutManager linearLayoutManagerIngredients;
    LinearLayoutManager linearLayoutManagerRelated;
    LinearLayoutManager linearLayoutManagerPreparation;
    LinearLayoutManager linearLayoutManagerComment;
    LinearLayoutManager linearLayoutManagerSpecialSteps;

    ArrayList<SpecialIngedient> specialIngedients;
    ArrayList<SpecialIngredientSteps> specialIngredientSteps;


    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime;
    ArrayList<Step> steps;
    ArrayList<String> title;
    ArrayList<CustomIngredient> ingredients;
    ArrayList<Sections> related;
    ArrayList<Comments> comments;
    HashMap<String, ArrayList<Ingredient>> subingrdeints;


    View mBottomLayout;
    View mVideoLayout;
    // UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    FoodDetailModelWrapper foodDetailModel, foodDetailModelSpecialWrapper;
    FoodDetailModel foodDetailModelSpecial;
    FoodDetailModel foodDetailModelSaved;
    SimpleExoPlayer player;
    MediaSource mediaSource;
    SimpleExoPlayerView videoView;
    private ArrayList<Comments> allReviews;
    private int mLang;

    public void setFoodDetailModel(FoodDetailModelWrapper foodDetailModel) {

        this.foodDetailModel = foodDetailModel;
        startTime = new ArrayList<>();
        endTime = new ArrayList<>();

        for (int i = 0; i < foodDetailModel.getData().getSteps().size(); i++) {

            startTime.add(Utils.getTimeSeconds(foodDetailModel.getData().getSteps().get(i).getStart_time()));
            endTime.add(Utils.getTimeSeconds(foodDetailModel.getData().getSteps().get(i).getEnd_time()));

        }


    }

    public void setFoodDetailModelSpecial(FoodDetailModelWrapper wrapper, FoodDetailModel foodDetailModel, ArrayList<Comments> allReviews) {
        this.foodDetailModelSpecialWrapper = wrapper;
        this.foodDetailModelSpecial = foodDetailModel;
        this.allReviews = allReviews;
        startTime = new ArrayList<>();
        endTime = new ArrayList<>();

        for (int i = 0; i < foodDetailModel.getSpecial_recipe_story().getSteps().size(); i++) {

            startTime.add(Utils.getTimeSeconds(foodDetailModel.getSpecial_recipe_story().getSteps().get(i).getStart_time()));
            endTime.add(Utils.getTimeSeconds(foodDetailModel.getSpecial_recipe_story().getSteps().get(i).getEnd_time()));

        }


    }

    public void setFoodDetailModel(FoodDetailModel foodDetailModel) {

        this.foodDetailModelSaved = foodDetailModel;
//        startTime = new ArrayList<>();
//        endTime = new ArrayList<>();
//
//        for (int i = 0; i < this.foodDetailModel.getData().getSteps().size(); i++) {
//
//            startTime.add(Utils.getTimeSeconds(this.foodDetailModel.getData().getSteps().get(i).getStart_time()));
//            endTime.add(Utils.getTimeSeconds(this.foodDetailModel.getData().getSteps().get(i).getEnd_time()));
//
//        }


    }

    @Override
    public void onPause() {
        super.onPause();
        player.stop();
        player.stop(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
        player.stop(true);
    }

    private void setValuesByLanguage() {
        switch (mLang) {
            case ENGLISH:
            default:
                binding.linearLayoutMain.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                binding.tvIngredients.setText(getString(R.string.ingredients_en));
                binding.tvRelatedRecipes.setText(getString(R.string.related_recipes_en));
                binding.tvPreparations.setText(getString(R.string.preparations_en));
                binding.tvHowItTurnOut.setText(getString(R.string.how_it_turn_out_en));
                binding.tvShowall.setText(getString(R.string.show_all_en));
                binding.etComments.setTextDirection(View.TEXT_DIRECTION_LTR);
                binding.textInputComments.setHint(getString(R.string.write_comments_en));
                binding.btnStepByStep.setText(getString(R.string.step_by_step_mode_en));
                break;

            case AppConstant.Language.URDU:
                binding.linearLayoutMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                binding.tvIngredients.setText(getString(R.string.ingredients_ur));
                binding.tvRelatedRecipes.setText(getString(R.string.related_recipes_ur));
                binding.tvPreparations.setText(getString(R.string.preparations_ur));
                binding.tvHowItTurnOut.setText(getString(R.string.how_it_turn_out_ur));
                binding.tvShowall.setText(getString(R.string.show_all_ur));
                binding.etComments.setTextDirection(View.TEXT_DIRECTION_RTL);
                binding.textInputComments.setHint(getString(R.string.write_comments_ur));
                binding.btnStepByStep.setText(getString(R.string.step_by_step_mode_ur));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null && mediaSource != null) {

            player.prepare(mediaSource, true, true);
            player.seekTo(0);

        }
    }


    ImageView btnMute;
    LikeButton likebtn;
    //    Button savebtn;
    TextView savebtn;
    TextView tvShowall;
    LinearLayout sharing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_special_detail, container, false);
        //   mVideoView = (UniversalVideoView) binding.getRoot().findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);
        videoView = (SimpleExoPlayerView) binding.getRoot().findViewById(R.id.videoView);
        btnMute = (ImageView) binding.getRoot().findViewById(R.id.mutebtn);
//        savebtn = (Button) binding.getRoot().findViewById(R.id.savebtn);
        savebtn = (TextView) binding.getRoot().findViewById(R.id.savebtn);
        tvShowall = (TextView) binding.getRoot().findViewById(R.id.tvShowall);
        likebtn = (LikeButton) binding.getRoot().findViewById(R.id.lkFav);
        sharing = (LinearLayout) binding.getRoot().findViewById(R.id.sharing);

        sharing.setOnClickListener(this);
        tvShowall.setOnClickListener(this);
        btnMute.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        savebtn.setVisibility(View.VISIBLE);
        likebtn.setVisibility(View.VISIBLE);
        likebtn.setOnClickListener(this);

        mLang = preferenceHelper.getSelectedLanguage();
        setValuesByLanguage();

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

                foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this, "");
                foodRelatedAdapter.setPreferenceHelper(preferenceHelper);

                binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
                foodRelatedAdapter.notifyDataSetChanged();
                binding.llRelated.setVisibility(View.VISIBLE);

            } else {
                binding.llRelated.setVisibility(View.GONE);

            }


            if (foodDetailModel.getAllReviews().size() > 0) {

                comments.addAll(foodDetailModel.getAllReviews());
                foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, true, false);
                foodCommentsAdapter.setPreferenceHelper(preferenceHelper);

                binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
                foodCommentsAdapter.notifyDataSetChanged();

            } else if (allReviews.size() > 0) {

                comments.addAll(allReviews);
                foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, true, false);
                foodCommentsAdapter.setPreferenceHelper(preferenceHelper);

                binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
                foodCommentsAdapter.notifyDataSetChanged();
            }


        }


        if (foodDetailModelSpecial != null) {

            setData(foodDetailModelSpecial.getSpecial_recipe_story());

//            if (foodDetailModelSpecial.getRelated().size() > 0) {
//
//                related.addAllToAdapter(foodDetailModel.getRelated());
//
//                foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this);
//                binding.rvRelatedRecipes.setAdapter(foodRelatedAdapter);
//                foodRelatedAdapter.notifyDataSetChanged();
//                binding.llRelated.setVisibility(View.VISIBLE);
//
//            } else {
//                binding.llRelated.setVisibility(View.GONE);
//
//            }

            if (foodDetailModelSpecialWrapper.getAllReviews().size() > 0) {

                comments.addAll(foodDetailModelSpecialWrapper.getAllReviews());
                foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, true, false);
                foodCommentsAdapter.setPreferenceHelper(preferenceHelper);

                binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
                foodCommentsAdapter.notifyDataSetChanged();

            } else {


            }


        }


        //  getDetails();
    }

    private void initAdapters() {
        // mVideoView.setMediaController(mMediaController);
        //   mVideoView.setVideoViewCallback(this);

        binding.rvRelatedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));

        binding.rvSpecialIngredients.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));

        linearLayoutManagerIngredients = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerPreparation = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerComment = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerSpecialSteps = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);

        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);


        binding.rvSpecialSteps.setLayoutManager(linearLayoutManagerSpecialSteps);
        binding.rvIngredients.setLayoutManager(linearLayoutManagerIngredients);
        binding.rvPreparations.setLayoutManager(linearLayoutManagerPreparation);
        binding.rvCommentsSection.setLayoutManager(linearLayoutManagerComment);

        binding.rvSpecialSteps.setItemAnimator(defaultItemAnimator);
        binding.rvIngredients.setItemAnimator(defaultItemAnimator);
        binding.rvPreparations.setItemAnimator(defaultItemAnimator);
        binding.rvCommentsSection.setItemAnimator(defaultItemAnimator);

        specialIngedients = new ArrayList<>();
        specialIngredientSteps = new ArrayList<>();
        steps = new ArrayList<>();
        related = new ArrayList<>();
        ingredients = new ArrayList<>();
        title = new ArrayList<>();
        comments = new ArrayList<>();
        subingrdeints = new HashMap<>();


        foodPreparationAdapter = new FoodPreparationAdapter(steps, mainActivity, this);
        foodPreparationAdapter.setPreferenceHelper(preferenceHelper);

        binding.rvPreparations.setAdapter(foodPreparationAdapter);


        foodSpecialIngredientAdapter = new FoodSpecialIngredientAdapter(foodDetailModelSpecial.getSpecial_ingredients(), mainActivity, this);
        foodSpecialIngredientAdapter.setPreferenceHelper(preferenceHelper);

        binding.rvSpecialIngredients.setAdapter(foodSpecialIngredientAdapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvSpecialIngredients);

        foodSpecialStepsAdapter = new FoodSpecialStepsAdapter(foodDetailModelSpecial.getSpecial_recipe_step(), mainActivity);
        foodSpecialStepsAdapter.setPreferenceHelper(preferenceHelper);

        binding.rvSpecialSteps.setAdapter(foodSpecialStepsAdapter);


        binding.specialingrdient.setText(mLang == ENGLISH ? foodDetailModelSpecial.getSpecial_ingredients().get(0).getIngredient_en() : foodDetailModelSpecial.getSpecial_ingredients().get(0).getIngredient_ur());

        foodSpecialIngredientAdapter.notifyDataSetChanged();
        foodSpecialStepsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onReplyClick(int positon) {


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

    @Override
    public void specialClick(String data) {


        binding.specialingrdient.setText(data);
    }

    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }

    boolean mute = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sharing:
                String locale = mLang == ENGLISH ? "en" : "ur";
                String shareBody = "https://food.tribune.com.pk/" + locale + "/special-recipe/" + foodDetailModelSpecial.getSlug();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "www.SubjectHere.com");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));

                break;
            case R.id.mutebtn:

                if (mute) {
                    mute = false;
                    player.setVolume(1f);
                    btnMute.setImageTintList(ColorStateList.valueOf(mainActivity.getResources().getColor(R.color.colorAccent)));

                } else {
                    mute = true;
                    player.setVolume(0f);
                    btnMute.setImageTintList(ColorStateList.valueOf(mainActivity.getResources().getColor(R.color.colorRed)));


                }
                break;
            case R.id.btnStepByStep:
                player.stop();
                player.stop(true);
                EventBus.getDefault().register(this);
                // binding.videoView.stopPlayback();
                StepFragment stepFragment = new StepFragment();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    stepFragment.setSharedElementEnterTransition(new DetailsTransition());
                    stepFragment.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    stepFragment.setSharedElementReturnTransition(new DetailsTransition());
                }


                // binding.videoView.stopPlayback();
                //   binding.videoView.closePlayer();
                CarelessSingleton.instance.setState(foodDetailModelSpecial.getSpecial_recipe_story(), 0);
                stepFragment.setVideoData(0, foodDetailModelSpecial, startTime, endTime, (int) player.getCurrentPosition());
                mainActivity.addFragment(stepFragment, true, true);

                break;

            case R.id.savebtn:
                if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                    Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

                } else {
                    serviceHelper.enqueueCall(webService.sacvestory(String.valueOf(preferenceHelper.getUserFood().getId()), "special_story", String.valueOf(foodDetailModelSpecial.getSpecial_recipe_story().getFeature_type_id()), String.valueOf(foodDetailModelSpecial.getSpecial_recipe_story().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY);
                }
                break;

            case R.id.lkFav:
                if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                    Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

                } else {
                    serviceHelper.enqueueCall(webService.markfavorite(preferenceHelper.getUserFood().getFacebook_id(), "special_recipe", String.valueOf(foodDetailModelSpecial.getSpecial_recipe_story().getFeature_type_id()), String.valueOf(foodDetailModelSpecial.getSpecial_recipe_story().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MARK_FAVORITE);
                }
                break;


            case R.id.tvShowall:
                if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                    Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

                } else {

                    player.stop();
                    player.stop(true);
                    //  stopPosition = binding.videoView.getCurrentPosition();
                    EventBus.getDefault().register(this);
                    CommentsFragment commentsFragment = new CommentsFragment();
                    if (foodDetailModelSpecial != null) {


                        FoodDetailModelWrapper foodDetailModelWrapper = new FoodDetailModelWrapper();
                        foodDetailModelWrapper.setAllReviews(allReviews);
                        foodDetailModelWrapper.setData(foodDetailModelSpecial);
                        commentsFragment.setArrayComments(foodDetailModelWrapper, true);

                    } else {


                        commentsFragment.setArrayComments(foodDetailModel, false);
                    }
                    mainActivity.addFragment(commentsFragment, true, true);
                }
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
                    comments.clear();
                    setData(foodDetailModel.getData());

                    this.foodDetailModelSpecial = foodDetailModel.getStory();
                    this.allReviews = foodDetailModel.getAllReviews();
                }
                if (foodDetailModel.getRelated().size() > 0) {
                    related.clear();
                    related.addAll(foodDetailModel.getRelated());
                    foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this , "");
                    foodRelatedAdapter.setPreferenceHelper(preferenceHelper);

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

                if (foodDetailModel.getAllReviews().size() > 0) {

                    comments.addAll(foodDetailModel.getAllReviews());
                    foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, true, false);
                    foodCommentsAdapter.setPreferenceHelper(preferenceHelper);

                    binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
                    foodCommentsAdapter.notifyDataSetChanged();

                } else {


                }
//
//                    LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
//                    FoodDetailFragment detailFragment = new FoodDetailFragment();
//                    detailFragment.setFoodDetailModel(foodDetailModel);
//                    mainActivity.replaceFragment(detailFragment, true, true);
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MARK_FAVORITE:

                if (likebtn.isLiked()) {
                    likebtn.setLiked(false);
                    String strCount =  binding.tvServingDetails.getText().toString();

                    String[] separated = strCount.split(" ");
                    Integer count = Integer.parseInt(separated[0]);

                    count = count - 1;
                    binding.tvServingDetails.setText(String.valueOf(count) + " likes");


                } else {
                    likebtn.setLiked(true);

                    String strCount =  binding.tvServingDetails.getText().toString();

                    String[] separated = strCount.split(" ");
                    Integer count = Integer.parseInt(separated[0]);

                    count = count + 1;
                    binding.tvServingDetails.setText(String.valueOf(count) + " likes");

                }

                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY:

                if (savebtn.getText().equals(getString(R.string.saved_en)) || savebtn.getText().equals(getString(R.string.saved_ur))) {
                    savebtn.setTextColor(Color.WHITE);
//                    savebtn.setText("Save");
//                    savebtn.setWidth(mLang == ENGLISH ? dpToPx(60) : dpToPx(90));
                    savebtn.setText(mLang == ENGLISH ? getString(R.string.save_en) : getString(R.string.save_ur));
                } else {

                    savebtn.setTextColor(Color.RED);
//                    savebtn.setText("Saved");
                    savebtn.setText(mLang == ENGLISH ? getString(R.string.saved_en) : getString(R.string.saved_ur));
                }


                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW:
                binding.etComments.setText("");
                String slug = this.foodDetailModelSpecial.getSlug();
                next(slug);

                break;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void setData(FoodDetailModel foodDetailModel) {
        binding.nestedScroll.fullScroll(ScrollView.FOCUS_UP);
        binding.nestedScroll.smoothScrollTo(0, 0);
        binding.tvfoodName.requestFocus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.beforeStartContentEn.setText(Html.fromHtml(mLang == ENGLISH ? foodDetailModelSpecial.getBefore_start_content_en() : foodDetailModelSpecial.getBefore_start_content_ur(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.beforeStartContentEn.setText(Html.fromHtml(mLang == ENGLISH ? foodDetailModelSpecial.getBefore_start_content_en() : foodDetailModelSpecial.getBefore_start_content_ur()));
        }
        //  binding.beforeStartContentEn.setText(foodDetailModelSpecial.getBefore_start_content_en());
        binding.beforeStartEn.setText(mLang == ENGLISH ? foodDetailModelSpecial.getBefore_start_en() : foodDetailModelSpecial.getBefore_start_ur());
        binding.masalasAromaticsContentEn.setText(mLang == ENGLISH ? foodDetailModelSpecial.getMasalas_aromatics_content_en() : foodDetailModelSpecial.getMasalas_aromatics_content_ur());
        binding.masalasAromaticsEn.setText(mLang == ENGLISH ? foodDetailModelSpecial.getMasalas_aromatics_en() : foodDetailModelSpecial.getMasalas_aromatics_ur());


        if (foodDetailModel.getIs_favorite() == 1) {
            likebtn.setLiked(true);
        } else {


            likebtn.setLiked(false);
        }

        if (foodDetailModel.getIs_save() == 1) {

            savebtn.setTextColor(Color.RED);
            savebtn.setText(mLang == ENGLISH ? getString(R.string.saved_en) : getString(R.string.saved_ur));
        } else {


            savebtn.setTextColor(Color.WHITE);
//            savebtn.setWidth(mLang == ENGLISH ? dpToPx(60) : dpToPx(90));
            savebtn.setText(mLang == ENGLISH ? getString(R.string.save_en) : getString(R.string.save_ur));
        }

        if (foodDetailModel.getVideo_url() != null) {
//            videoView.requestFocus();
            videoView.hideController();
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(mainActivity),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            videoView.setPlayer(player);
            player.setPlayWhenReady(true);
            player.getPlaybackLooper();
            player.addListener(this);
            player.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
            Uri uri = Uri.parse(foodDetailModel.getVideo_path().replace("1080.mp4", "720.mp4"));

            mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, true);
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
            videoView.showController();

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            Bitmap bmp = null;
            int videoHeight, videoWidth;
            try {
                retriever.setDataSource(foodDetailModel.getVideo_path());
                bmp = retriever.getFrameAtTime();
                videoHeight = bmp.getHeight();
                videoWidth = bmp.getWidth();
                mVideoLayout.setMinimumHeight(videoWidth);
            } catch (Exception ignored) {

            }


        }

        UIHelper.setImageWithGlide(mainActivity, binding.ivDishImage, foodDetailModel.getGallery().getPhotos().get(0).getImage_path());
        binding.tvfoodName.setText(mLang == ENGLISH ? foodDetailModel.getTitle_en() : foodDetailModel.getTitle_ur());

        binding.tvPreparationTime.setGravity(mLang == ENGLISH ? Gravity.CENTER | START : Gravity.CENTER | END);
        binding.tvServingTime.setGravity(mLang == ENGLISH ? Gravity.CENTER | START : Gravity.CENTER | END);
        binding.tvServingDetails.setGravity(mLang == ENGLISH ? Gravity.CENTER | START : Gravity.CENTER | END);

        binding.tvServingDetails.setText(foodDetailModel.getCountFavorites() + " likes");
        binding.tvServingTime.setText(foodDetailModel.getTotalViews() + " views");
        binding.tvPreparationTime.setText(foodDetailModel.getCook_time());
        binding.tvfoodDiscount.setText(mLang == ENGLISH ? foodDetailModel.getGallery().getDescription_en() : foodDetailModel.getGallery().getDescription_ur());


        steps.addAll(foodDetailModel.getSteps());
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

                ingredients.add(new CustomIngredient(" ", 0, ingredient,
                        (quantity != null ? quantity : " ") + " " + (quantityType != null ? quantityType : " ")));

            }


        }
        foodIngredientsAdapter = new FoodIngredientsAdapter(ingredients, title, mainActivity);
        binding.rvIngredients.setAdapter(foodIngredientsAdapter);


        foodPreparationAdapter.notifyDataSetChanged();
        foodIngredientsAdapter.notifyDataSetChanged();

    }

    public static String changedHeaderHtml(String htmlText) {
        String jsTag = "<style> .easyimage img { width:80%;} </style>";
        // String head = "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>";
        String head = "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, shrink-to-fit=no\">" + jsTag + "</head>";

        String closedTag = "</body></html>";
        String changeFontHtml = head + htmlText + closedTag;
        return changeFontHtml;
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
        player.prepare(mediaSource, true, true);
        player.seekTo(syncStatusMessage.getSyncStatusMessage());
        EventBus.getDefault().unregister(this);

    }

    int stopPosition;

    @Override
    public void onStepClick(Step step, int position) {

        player.stop();
        player.stop(true);
        //  stopPosition = binding.videoView.getCurrentPosition();
        EventBus.getDefault().register(this);
        // binding.videoView.stopPlayback();
        //binding.videoView.closePlayer();
        CarelessSingleton.instance.setState(foodDetailModelSpecial.getSpecial_recipe_story(), position);
        StepFragment stepFragment = new StepFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stepFragment.setSharedElementEnterTransition(new DetailsTransition());
            stepFragment.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            stepFragment.setSharedElementReturnTransition(new DetailsTransition());
        }

        stepFragment.setVideoData(position, foodDetailModelSpecial.getSpecial_recipe_story(), startTime, endTime, (int) player.getCurrentPosition());
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
        player.stop(true);
        player.stop();
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
    public void popularrecipe(int pos , String screen) {

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

    @Override
    public void onLatestVideoClick(int pos) {

    }

    public void next(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(slug, String.valueOf(preferenceHelper.getUserFood().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
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

        if (url.equalsIgnoreCase(foodDetailModel.getData().getVideo_path().replace("1080.mp4", "320.mp4"))) {

//            binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//
//                    mediaPlayer.setLooping(true);
//                    try {
//
//                        mediaPlayer.setDataSource(cacheFile.getAbsolutePath());
//                        Log.d("Video  played", "playing: ");
//                    } catch (IOException e) {
//
//                        Log.d("Video Not played", "onPrepared: ");
//                    }
//
//                }
//            });
//            binding.videoView.start();


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
