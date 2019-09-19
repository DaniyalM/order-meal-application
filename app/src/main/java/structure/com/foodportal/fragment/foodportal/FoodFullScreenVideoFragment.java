package structure.com.foodportal.fragment.foodportal;

import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentFoodFullScreenVideoBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UniversalMediaController;
import structure.com.foodportal.models.foodModels.Sections;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;

public class FoodFullScreenVideoFragment extends BaseFragment {

    private FragmentFoodFullScreenVideoBinding mBinding;

    private Sections mVideoObject;
    private SimpleExoPlayer mExoPlayer;
    private MediaSource mMediaSource;
    private boolean mIsMute;
    private long mLastPosition;

    public void setVideoObject(Sections videoObject) {
        mVideoObject = videoObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_full_screen_video, container, false);
        init();
        return mBinding.getRoot();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        titlebar.hidesearch();
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mExoPlayer.stop(true);
        mExoPlayer.stop();
        if (mainActivity.getSupportFragmentManager().getFragments().size() > 3) {
            mainActivity.getTitleBar().showTitlebar();
            mainActivity.getTitleBar().showBackButton(mainActivity);
        } else {
            mainActivity.getTitleBar().showsearch(mainActivity);
            mainActivity.getTitleBar().showTitlebar();
            mainActivity.getTitleBar().showMenuButton(mainActivity);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        mExoPlayer.stop();
//        mExoPlayer.stop(true);
        if(mExoPlayer != null) {
            mLastPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        mExoPlayer.stop();
//        mExoPlayer.stop(true);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mExoPlayer != null && mMediaSource != null) {
//            mExoPlayer.prepare(mMediaSource, true, true);
//            mExoPlayer.seekTo(0);
//        }
        if(mExoPlayer != null) {
            mExoPlayer.seekTo(mLastPosition);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void setVideoSize() {
        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.x - 150;
        mBinding.videoView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    private void init() {
        mainActivity.hideBottombar();

        switch (preferenceHelper.getSelectedLanguage()) {
            case ENGLISH:
            default:
                mBinding.mainLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                break;

            case AppConstant.Language.URDU:
                mBinding.mainLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                break;
        }

        mBinding.ivMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExoPlayer != null) {
                    if (mIsMute) {
                        mIsMute = false;
                        mExoPlayer.setVolume(1f);
                        mBinding.ivMute.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    } else {
                        mIsMute = true;
                        mExoPlayer.setVolume(0f);
                        mBinding.ivMute.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed)));
                    }
                }
            }
        });
        setVideoSize();
        setData();
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void setData() {
        if (mVideoObject.getContentUrl() != null) {
            try {
                mBinding.videoView.hideController();

                mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(mainActivity),
                        new DefaultTrackSelector(), new DefaultLoadControl());

                mBinding.videoView.setPlayer(mExoPlayer);

                if (preferenceHelper.getAutoPlay()) {
                    mExoPlayer.setPlayWhenReady(true);
                } else {
                    mExoPlayer.setPlayWhenReady(false);

                }
                mExoPlayer.getPlaybackLooper();
                mExoPlayer.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);

                Uri uri = Uri.parse(mVideoObject.getContentUrl());
                mMediaSource = buildMediaSource(uri);

                mExoPlayer.prepare(mMediaSource, true, true);
                mExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);

                mBinding.videoView.showController();

                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(mVideoObject.getContentUrl());
                mExoPlayer.seekTo(1);
            } catch (Exception e) {

            }
        }
    }
}
