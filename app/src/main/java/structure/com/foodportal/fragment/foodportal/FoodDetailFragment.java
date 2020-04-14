package structure.com.foodportal.fragment.foodportal;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.graphics.Paint.Align.CENTER;
import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;

public class FoodDetailFragment extends BaseFragment implements
        View.OnClickListener, FoodDetailListner, SimpleExoPlayer.EventListener, UniversalVideoView.VideoViewCallback, FoodHomeListner, CacheListener, CommentClickListner {

    FoodIngredientsAdapter foodIngredientsAdapter;
    FoodPreparationAdapter foodPreparationAdapter;
    FoodPopularRecipeAdapter foodRelatedAdapter;
    FoodCommentsAdapter foodCommentsAdapter;
    FragmentProductDetailFoodportalBinding binding;

    LinearLayoutManager linearLayoutManagerIngredients;
    LinearLayoutManager linearLayoutManagerRelated;
    LinearLayoutManager linearLayoutManagerPreparation;
    LinearLayoutManager linearLayoutManagerComment;


    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime;
    ArrayList<Step> steps;
    ArrayList<String> title;
    ArrayList<CustomIngredient> ingredients;
    ArrayList<Sections> related;
    ArrayList<Comments> comments;
    HashMap<String, ArrayList<Ingredient>> subingrdeints;
    Button sa;
    TextView tv;

    View mBottomLayout;
    View mVideoLayout;
    // UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    FoodDetailModelWrapper foodDetailModel;
    FoodDetailModel foodDetailModelSpecial;
    FoodDetailModel foodDetailModelSaved;
    SimpleExoPlayer player;
    MediaSource mediaSource;
    SimpleExoPlayerView videoView;
    TextView servings;
    private boolean b = false;
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

    Boolean from = false;

    public void setFromSearch(boolean from) {
        this.from = from;

    }

    public void setFoodDetailModelSpecial(FoodDetailModel foodDetailModel) {

        this.foodDetailModelSpecial = foodDetailModel;
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

    public void setscreensize() {

        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.x;
        videoView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        // binding.videoView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail_foodportal, container, false);
        //   mVideoView = (UniversalVideoView) binding.getRoot().findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) binding.getRoot().findViewById(R.id.media_controller);
        videoView = (SimpleExoPlayerView) binding.getRoot().findViewById(R.id.videoView);
        btnMute = (ImageView) binding.getRoot().findViewById(R.id.mutebtn);
//        savebtn = (Button) binding.getRoot().findViewById(R.id.savebtn);
        savebtn = (TextView) binding.getRoot().findViewById(R.id.savebtn);
        tvShowall = (TextView) binding.getRoot().findViewById(R.id.tvShowall);
        likebtn = (LikeButton) binding.getRoot().findViewById(R.id.lkFav);
        sharing = (LinearLayout) binding.getRoot().findViewById(R.id.sharing);
        servings = (TextView) binding.getRoot().findViewById(R.id.servings);

        sharing.setOnClickListener(this);
        tvShowall.setOnClickListener(this);
        btnMute.setOnClickListener(this);
        savebtn.setOnClickListener(this);
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

            } else {


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

//
//            if (foodDetailModel.getAllReviews().size() > 0) {
//
//                comments.addAllToAdapter(foodDetailModel.getAllReviews());
//                foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, true,false);
//                binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
//                foodCommentsAdapter.notifyDataSetChanged();
//
//            } else {
//
//
//            }


        }


        //  getDetails();
    }

    private void initAdapters() {
        // mVideoView.setMediaController(mMediaController);
        //   mVideoView.setVideoViewCallback(this);

        binding.rvRelatedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));

        linearLayoutManagerIngredients = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerPreparation = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerComment = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);

        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);


        binding.rvIngredients.setLayoutManager(linearLayoutManagerIngredients);
        binding.rvPreparations.setLayoutManager(linearLayoutManagerPreparation);
        binding.rvCommentsSection.setLayoutManager(linearLayoutManagerComment);

        binding.rvIngredients.setItemAnimator(defaultItemAnimator);
        binding.rvPreparations.setItemAnimator(defaultItemAnimator);
        binding.rvCommentsSection.setItemAnimator(defaultItemAnimator);

        steps = new ArrayList<>();
        related = new ArrayList<>();
        ingredients = new ArrayList<>();
        title = new ArrayList<>();
        comments = new ArrayList<>();
        subingrdeints = new HashMap<>();


        foodPreparationAdapter = new FoodPreparationAdapter(steps, mainActivity, this);
        foodPreparationAdapter.setPreferenceHelper(preferenceHelper);

        binding.rvPreparations.setAdapter(foodPreparationAdapter);
        Log.d("Token", preferenceHelper.getDeviceToken());
        setscreensize();

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

    public void showback(boolean b) {
        this.b = b;
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
                String shareBody = "https://food.tribune.com.pk/" + locale + "/recipe/" + foodDetailModel.getData().getSlug();
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
                CarelessSingleton.instance.setState(foodDetailModel.getData(), 0);
                stepFragment.setVideoData(0, foodDetailModel.getData(), startTime, endTime, (int) player.getCurrentPosition());
                mainActivity.addFragment(stepFragment, true, true);

                break;

            case R.id.savebtn:
                if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                    Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

                } else {

                    serviceHelper.enqueueCall(webService.sacvestory(String.valueOf(preferenceHelper.getUserFood().getId()), "story", String.valueOf(foodDetailModel.getData().getFeature_type_id()), String.valueOf(foodDetailModel.getData().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY);
                }

                break;

            case R.id.lkFav:
                if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                    Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

                } else {

                    serviceHelper.enqueueCall(webService.markfavorite(preferenceHelper.getUserFood().getFacebook_id(), "story", String.valueOf(foodDetailModel.getData().getFeature_type_id()), String.valueOf(foodDetailModel.getData().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MARK_FAVORITE);
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
                    commentsFragment.setArrayComments(foodDetailModel, false);
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
                player.stop();
                player.stop(true);
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);


                if (foodDetailModel != null) {
                    ingredients.clear();
                    subingrdeints.clear();
                    steps.clear();
                    related.clear();
                    comments.clear();
                    setData(foodDetailModel.getData());

                }
                if (foodDetailModel.getRelated().size() > 0) {
                    related.clear();
                    related.addAll(foodDetailModel.getRelated());
                    foodRelatedAdapter = new FoodPopularRecipeAdapter(related, mainActivity, this ,"");
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

                binding.nestedScroll.fullScroll(ScrollView.FOCUS_UP);
                binding.nestedScroll.smoothScrollTo(0, 0);
                binding.tvfoodName.requestFocus();
//
//                    LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
//                    FoodDetailFragment detailFragment = new FoodDetailFragment();
//                    detailFragment.setFoodDetailModel(foodDetailModel);
//                    mainActivity.replaceFragment(detailFragment, true, true);
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MARK_FAVORITE:

                if (likebtn.isLiked()) {
                    likebtn.setLiked(false);
                } else {


                    likebtn.setLiked(true);
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

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW:
                binding.etComments.setText("");
                String slug = this.foodDetailModel.getData().getSlug();
                next(slug);

                break;

        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void setData(FoodDetailModel foodDetailModel) {
        binding.nestedScroll.fullScroll(ScrollView.FOCUS_UP);
        binding.nestedScroll.smoothScrollTo(0, 0);
        binding.tvfoodName.requestFocus();
        servings.setText(mLang == ENGLISH ? "for " + foodDetailModel.getServing_for() + " servings" : foodDetailModel.getServing_for() + " " + getString(R.string.for_servings_ur));
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
            if (preferenceHelper.getAutoPlay()) {
                player.setPlayWhenReady(true);
            } else {
                player.setPlayWhenReady(false);

            }
            //  player.setPlayWhenReady(true);
            player.getPlaybackLooper();
            player.addListener(this);
            player.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
            Uri uri = Uri.parse(foodDetailModel.getVideo_path().replace("1080.mp4", "320.mp4"));

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
            player.seekTo(1);

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


    /*    for (int i = 0; i < foodDetailModel.getIngredient().size(); i++) {
            //For Header
            ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getTag_en() == null ? (foodDetailModel.getIngredient().get(i).getIngredient_en() != null ? foodDetailModel.getIngredient().get(i).getIngredient_en() : " ") + " " + (foodDetailModel.getIngredient().get(i).getQuantity_en() != null ? foodDetailModel.getIngredient().get(i).getQuantity_en() : " ") :
                    foodDetailModel.getIngredient().get(i).getTag_en() != null ? foodDetailModel.getIngredient().get(i).getTag_en() : " ", 1,
                    foodDetailModel.getIngredient().get(i).getQuantity_en() != null ? foodDetailModel.getIngredient().get(i).getQuantity_en() : "",
                    foodDetailModel.getIngredient().get(i).getQuantity_en() == null ? " " : " " + (foodDetailModel.getIngredient().get(i).getQuantity_type_en() != null ? foodDetailModel.getIngredient().get(i).getQuantity_type_en() : " ")

            ));

            for (int k = 0; k < foodDetailModel.getIngredient().get(i).getSub_ingredients().size(); k++) {


                //For SubList

                ingredients.add(new CustomIngredient(foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getIngredient_en() + " " +
                        (foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_en() != null ? foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_en() : " ") +
                        (foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type_en() != null ?
                                foodDetailModel.getIngredient().get(i).getSub_ingredients().get(k).getQuantity_type_en() : " "), 0,
                        foodDetailModel.getIngredient().get(i).getQuantity_en() == null ? " " : foodDetailModel.getIngredient().get(i).getQuantity_en(),
                        foodDetailModel.getIngredient().get(i).getQuantity_en() != null ? foodDetailModel.getIngredient().get(i).getQuantity_en() + "" + foodDetailModel.getIngredient().get(i).getQuantity_type_en() : " "));


            }


        }*/


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
        EventBus.getDefault().register(this);
        //  stopPosition = binding.videoView.getCurrentPosition();
        //  EventBus.getDefault().register(this);
        // binding.videoView.stopPlayback();
        //binding.videoView.closePlayer();
        CarelessSingleton.instance.setState(foodDetailModel.getData(), position);
        StepFragment stepFragment = new StepFragment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stepFragment.setSharedElementEnterTransition(new DetailsTransition());
            stepFragment.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            stepFragment.setSharedElementReturnTransition(new DetailsTransition());
        }

        stepFragment.setVideoData(position, foodDetailModel.getData(), startTime, endTime, (int) player.getCurrentPosition());
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
            if (from) {
                mainActivity.getTitleBar().showBackButton(mainActivity);

            } else {

                mainActivity.getTitleBar().showMenuButton(mainActivity);
            }
        } else {
            if (from) {
                mainActivity.getTitleBar().showBackButton(mainActivity);

            } else {

                mainActivity.getTitleBar().showMenuButton(mainActivity);
            }


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
        next(related.get(pos).getSlug());
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

    private void showMenuPopup(View v) {
        /** Instantiating PopupMenu class */
        PopupMenu popup = new PopupMenu(mainActivity, v);

        /** Adding menu items to the popumenu */
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        /** Defining menu item click listener for the popup menu */
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.actionprice:
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                        try {
                            mainActivity.startActivity(whatsappIntent);
                        } catch (android.content.ActivityNotFoundException ex) {


                            Toast.makeText(mainActivity, "Unable to find watsapp", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.actionLocation:
                        String shareBody = "Here is the share content body";
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "www.SubjectHere.com");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));


                        break;

                    case R.id.actionRating:
                        String shareBod = "Here is the share content body";
                        Intent sharingInten = new Intent(android.content.Intent.ACTION_SEND);
                        sharingInten.setType("text/plain");
                        sharingInten.putExtra(android.content.Intent.EXTRA_SUBJECT, "www.SubjectHere.com");
                        sharingInten.putExtra(android.content.Intent.EXTRA_TEXT, shareBod);
                        startActivity(Intent.createChooser(sharingInten, getResources().getString(R.string.share_via)));


                        break;
                }

                return true;
            }
        });

        /** Showing the popup menu */
        popup.show();
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
    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.END | Gravity.TOP, 0, 0);
        // dismiss the popup window when touched
        popupWindow.setAnimationStyle(R.style.Animation);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
