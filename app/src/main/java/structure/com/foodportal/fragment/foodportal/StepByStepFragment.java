package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.SignupAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentStepbystepFoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.models.foodModels.FoodDetailModel;

public class StepByStepFragment extends BaseFragment implements View.OnClickListener {


    FragmentStepbystepFoodBinding binding;
    FoodDetailModel foodDetailModel;
    ArrayList<Integer> startTime = new ArrayList<>();
    ArrayList<Integer> endTime = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stepbystep_food, container, false);
       // init();

        return binding.getRoot();
    }

    public void setVideoData(FoodDetailModel foodDetailModel) {
    this.foodDetailModel =foodDetailModel;
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
        titlebar.setTitle("Step by Step");
    }

    private void init() {
        StepbyStepAdapter stepbyStepAdapter =new StepbyStepAdapter(mainActivity.getSupportFragmentManager(),foodDetailModel);
        binding.vpSignup.setAdapter(stepbyStepAdapter);
        // binding.indicator.setViewPager(binding.vpSignup);
        mainActivity.setSignupPager(binding.vpSignup);
        // binding.vpSignup.setAllowedSwipeDirection(SwipeDirection.left);
        binding.vpSignup.setOffscreenPageLimit(0);
    }
}
