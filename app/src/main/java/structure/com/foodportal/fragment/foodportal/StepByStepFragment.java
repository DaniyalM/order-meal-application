package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentStepbystepFoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.Step;
import structure.com.foodportal.singleton.CarelessSingleton;

public class StepByStepFragment extends BaseFragment implements View.OnClickListener, FoodDetailListner {


    FragmentStepbystepFoodBinding binding;
    FoodDetailModel foodDetailModel;
    ArrayList<Integer> startTime;
    ArrayList<Integer> endTime;
    int position;
    StepbyStepAdapter stepbyStepAdapter;
    float init;
    float onestep = 0;
    ProgressBar storiesProgressView;

    int value = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stepbystep_food, container, false);
        storiesProgressView = binding.getRoot().findViewById(R.id.progressView);
        setlistners();
        return binding.getRoot();
    }

    private void setlistners() {
        value = CarelessSingleton.instance.getStateposition();
     //   stepbyStepAdapter = new StepbyStepAdapter(foodDetailModel.getSteps(), mainActivity, this, foodDetailModel.getVideo_url());
        binding.rvStepbyStep.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

        binding.rvStepbyStep.setAdapter(stepbyStepAdapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.rvStepbyStep);


        onestep = 100 / foodDetailModel.getSteps().size();
        storiesProgressView.setProgress((int) onestep);

    }

    public void setVideoData(FoodDetailModel foodDetailModel, int position, ArrayList<Integer> startTime, ArrayList<Integer> endTime) {
        this.foodDetailModel = foodDetailModel;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;

    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
        titlebar.setTitle(" ");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //mainActivity.getTitleBar().setTitle("Detail");
        mainActivity.getTitleBar().showBackButton(mainActivity);


    }

    @Override
    public void onStepClick(Step step, int position) {

    }

    @Override
    public void onPageChanged(Step step, StepbyStepAdapter.FoodPreparationViewHolder holder, int position) {

        storiesProgressView.setProgress((int) (onestep * (1 + position)));


        if (value < 0) {
            mainActivity.onBackPressed();
            return;
        }

        if (value > 0) {
            //value = value-1;
         //   playvideo(holder);
            //  binding.tvStepDetail.setText(foodDetailModel.getSteps().get(value).getSteps_en());
            //  binding.tvSteps.setText("Step "+(value+1));
        }


        if (value > foodDetailModel.getSteps().size()) {
            value = 0;
            ///playvideo(holder);
            return;

        }


    }




}
