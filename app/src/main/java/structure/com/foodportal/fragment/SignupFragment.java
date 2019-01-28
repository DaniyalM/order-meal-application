package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.SignupAdapter;
import structure.com.foodportal.databinding.FragmentSignupBinding;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.models.UserSignUpModel;


public class SignupFragment extends BaseFragment {

    FragmentSignupBinding binding;
    UserSignUpModel.Builder userSignUpModel;

    public SignupFragment() {
        // Required empty public constructor
    }

    public void putBuilder(UserSignUpModel.Builder userSignUpModel) {

        this.userSignUpModel = userSignUpModel;

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(getString(R.string.sign_up));
        titlebar.showTitlebar();
        titlebar.showBackButton(registrationActivity);
        //titlebar.showCrossButton(registrationActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        init();

        return binding.getRoot();
    }

    private void init() {
        binding.vpSignup.setAdapter(new SignupAdapter(registrationActivity.getSupportFragmentManager(), binding.indicator,""));
        binding.indicator.setViewPager(binding.vpSignup);
        registrationActivity.setSignupPager(binding.vpSignup);
        // binding.vpSignup.setAllowedSwipeDirection(SwipeDirection.left);
        binding.vpSignup.setOffscreenPageLimit(0);
    }
}
