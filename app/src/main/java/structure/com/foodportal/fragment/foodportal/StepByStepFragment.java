package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.SignupAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.StepbyStepAdapter;
import structure.com.foodportal.databinding.FragmentStepbystepFoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.SwipeDirection;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.models.foodModels.FoodDetailModel;

public class StepByStepFragment extends BaseFragment implements View.OnClickListener {


    FragmentStepbystepFoodBinding binding;
    FoodDetailModel foodDetailModel;
    ArrayList<Integer> startTime = new ArrayList<>();
    ArrayList<Integer> endTime = new ArrayList<>();
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stepbystep_food, container, false);
        initnew();
        return binding.getRoot();
    }

    public void setVideoData(FoodDetailModel foodDetailModel,int position) {
    this.foodDetailModel =foodDetailModel;
    this.position =position;
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
    StepbyStepAdapter stepbyStepAdapter;
 /*   private void init() {
         stepbyStepAdapter =new StepbyStepAdapter(mainActivity.getSupportFragmentManager(),foodDetailModel);
        binding.vpSignup.setAdapter(stepbyStepAdapter);
        // binding.indicator.setViewPager(binding.vpSignup);
        mainActivity.setSignupPager(binding.vpSignup);
         binding.vpSignup.setAllowedSwipeDirection(SwipeDirection.left);
         binding.vpSignup.setAllowedSwipeDirection(SwipeDirection.right);
        binding.vpSignup.setOffscreenPageLimit(0);
    }*/

    List<Fragment> fragments= new ArrayList<>();
    ArrayList<String> categories =new ArrayList<>();


    public void initnew(){

      fragments = buildFragments();
      for(int i =0 ;i <foodDetailModel.getSteps().size();i++){

          categories.add(foodDetailModel.getSteps().get(i).getSteps_en());

      }

        stepbyStepAdapter = new StepbyStepAdapter(mainActivity,this.mainActivity.getSupportFragmentManager(),fragments,categories,foodDetailModel);
        binding.vpSignup.setAdapter(stepbyStepAdapter);

//Add a new Fragment to the list with bundle
        Bundle b = new Bundle();
        b.putInt("position", position);
        String title = "Food";
        stepbyStepAdapter.add(StepFragment.class, title, b);
        stepbyStepAdapter.notifyDataSetChanged();

    }

    private List<android.support.v4.app.Fragment> buildFragments() {
        List<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
        for(int i = 0; i<foodDetailModel.getSteps().size(); i++) {
            Bundle b = new Bundle();
            b.putInt("position", i);
            fragments.add(Fragment.instantiate(mainActivity,StepFragment.class.getName(),b));
        }

        return fragments;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainActivity.getTitleBar().setTitle("Detail");
        mainActivity.getTitleBar().showMenuButton(mainActivity);



    }
}
