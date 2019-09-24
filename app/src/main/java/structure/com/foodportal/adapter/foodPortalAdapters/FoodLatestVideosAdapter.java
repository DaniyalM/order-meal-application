package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.GlideException;
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

import java.util.ArrayList;

import info.androidhive.fontawesome.FontTextView;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodImageLoadListener;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.Sections;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class FoodLatestVideosAdapter extends RecyclerView.Adapter<FoodLatestVideosAdapter.PlanetViewHolder> {

    ArrayList<Sections> sections;
    MainActivity context;
    FoodHomeListner foodHomeListner;
    private boolean mIsMute;

    public FoodLatestVideosAdapter(ArrayList<Sections> sections, MainActivity context, FoodHomeListner foodHomeListner) {
        this.sections = sections;
        this.context = context;
        this.foodHomeListner = foodHomeListner;
    }

    @Override
    public FoodLatestVideosAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_video, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodLatestVideosAdapter.PlanetViewHolder holder, int position) {
        Sections obj = sections.get(position);

        if (obj != null) {

//            SimpleExoPlayer exoPlayer = null;
//            MediaSource mediaSource;
//
//            try {
//                if (obj.getContentUrl() != null) {
//                    holder.mVideoView.hideController();
//
//                    exoPlayer = ExoPlayerFactory.newSimpleInstance(
//                            new DefaultRenderersFactory(context),
//                            new DefaultTrackSelector(), new DefaultLoadControl());
//
//                    holder.mVideoView.setPlayer(exoPlayer);
//
//                    exoPlayer.getPlaybackLooper();
//                    exoPlayer.setRepeatMode(SimpleExoPlayer.DISCONTINUITY_REASON_SEEK);
//
//                    Uri uri = Uri.parse(obj.getContentUrl());
//                    mediaSource = buildMediaSource(uri);
//                    exoPlayer.prepare(mediaSource, true, true);
//                    exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
//
//                    holder.mVideoView.showController();
//
//                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//                    retriever.setDataSource(obj.getContentUrl());
//                    exoPlayer.seekTo(1);
//                }
//            } catch (Exception e) {
//
//            }

//            SimpleExoPlayer finalExoPlayer = exoPlayer;

            UIHelper.setImageWithGlideWithCallback(context, holder.mImageViewThumbnail,
                    sections.get(position).getThumbnailUrl(), new FoodImageLoadListener() {
                        @Override
                        public void onFailure(GlideException e) {
                            if (e != null)
                                e.printStackTrace();
                        }

                        @Override
                        public void onSuccess() {
                            holder.mRootFrameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlack));
                            holder.mImageViewPlay.setVisibility(View.VISIBLE);

                            holder.mThumbnailLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    holder.mThumbnailLayout.setVisibility(View.GONE);
//                                    holder.mImageViewPlay.setVisibility(View.GONE);
//                                    holder.mVideoLayout.setVisibility(View.VISIBLE);
//                                    finalExoPlayer.setPlayWhenReady(true);
                                    foodHomeListner.onLatestVideoClick(position);
                                }
                            });
                        }
                    });

            switch (preferenceHelper.getSelectedLanguage()) {
                case ENGLISH:
                default:
                    holder.mTextViewTitle.setText(sections.get(position).getTitle());
                    holder.mTextViewDesc.setText(sections.get(position).getDescription_en());
                    break;
                case URDU:
                    holder.mTextViewTitle.setText(sections.get(position).getTitle_ur());
                    holder.mTextViewDesc.setText(sections.get(position).getDescription_ur());
                    break;
            }

//            holder.mImageViewMute.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (finalExoPlayer != null) {
//                        if (mIsMute) {
//                            mIsMute = false;
//                            finalExoPlayer.setVolume(1f);
//                            holder.mImageViewMute.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
//                        } else {
//                            mIsMute = true;
//                            finalExoPlayer.setVolume(0f);
//                            holder.mImageViewMute.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorRed)));
//                        }
//                    }
//                }
//            });
        }
    }

    private BasePreferenceHelper preferenceHelper;

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public void setPreferenceHelper(BasePreferenceHelper preferenceHelper) {
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        SimpleExoPlayerView mVideoView;
        TextView mTextViewTitle, mTextViewDesc;
        ImageView mImageViewPlay, mImageViewMute, mImageViewThumbnail;
        FrameLayout mRootFrameLayout, mVideoLayout, mThumbnailLayout;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            mVideoView = (SimpleExoPlayerView) itemView.findViewById(R.id.videoView);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTextViewDesc = (TextView) itemView.findViewById(R.id.tvDescription);
            mImageViewPlay = (ImageView) itemView.findViewById(R.id.ivPlay);
            mImageViewMute = (ImageView) itemView.findViewById(R.id.ivMute);
            mImageViewThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            mRootFrameLayout = (FrameLayout) itemView.findViewById(R.id.rootFrameLayout);
            mVideoLayout = (FrameLayout) itemView.findViewById(R.id.video_layout);
            mThumbnailLayout = (FrameLayout) itemView.findViewById(R.id.thumbnail_layout);
        }
    }
}