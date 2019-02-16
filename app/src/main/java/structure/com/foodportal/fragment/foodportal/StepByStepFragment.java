package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentStepbystepFoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.Step;

public class StepByStepFragment extends BaseFragment implements View.OnClickListener, FoodDetailListner {


    FragmentStepbystepFoodBinding binding;
    FoodDetailModel foodDetailModel;
    ArrayList<Integer> startTime = new ArrayList<>();
    ArrayList<Integer> endTime = new ArrayList<>();
    int position;
    StepbyStepAdapter stepbyStepAdapter;
    float init;
    float onestep = 0;
    ProgressBar storiesProgressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stepbystep_food, container, false);
        storiesProgressView = binding.getRoot().findViewById(R.id.progressView);
        setlistners();
        return binding.getRoot();
    }

    private void setlistners() {

        stepbyStepAdapter = new StepbyStepAdapter(foodDetailModel.getSteps(), mainActivity, this);
        binding.rvStepbyStep.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

        binding.rvStepbyStep.setAdapter(stepbyStepAdapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.rvStepbyStep);


        onestep = 100 / foodDetailModel.getSteps().size();
        storiesProgressView.setProgress((int) onestep);


    }

    public void setVideoData(FoodDetailModel foodDetailModel, int position) {
        this.foodDetailModel = foodDetailModel;
        this.position = position;
        startTime.add(3);
        startTime.add(21);
        startTime.add(50);
        startTime.add(64);
        startTime.add(93);
        startTime.add(112);

        endTime.add(20);
        endTime.add(49);
        endTime.add(63);
        endTime.add(88);
        endTime.add(111);
        endTime.add(122);
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
    public void onPageChanged(Step step, int position) {

        storiesProgressView.setProgress((int) (onestep * (1+position) ));


    }
}
