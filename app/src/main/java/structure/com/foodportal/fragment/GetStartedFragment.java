package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentGetstartedBinding;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

public class GetStartedFragment extends BaseFragment implements View.OnClickListener {

    FragmentGetstartedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_getstarted, container, false);
        init();
        return binding.getRoot();
    }


    public void init() {
        binding.getStarted.setOnClickListener(this);
        binding.proceedAsGuest.setOnClickListener(this);
        binding.signIn.setOnClickListener(this);
        binding.tvWithEmail.setOnClickListener(this);
        binding.tvWithFacebok.setOnClickListener(this);
        binding.tvWithGoogle.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.getStarted:
//                //UIHelper.showToast(registrationActivity,getString(R.string.implementdialog));
//                //  registrationActivity.replaceFragment(new SignupFragment(),true,false);
//
//                break;
//            case R.id.proceedAsGuest:
//
//                registrationActivity.showMainActivity();
//
//                break;
//            case R.id.signIn:
//                UIHelper.showToast(registrationActivity, getString(R.string.implementdialog));
//                //  registrationActivity.replaceFragment(new LoginFragment(),true,false);
//
//                break;


            case R.id.tvWithFacebok:

                registrationActivity.showMainActivity();
                break;
            case R.id.tvWithGoogle:

                registrationActivity.showMainActivity();
                break;
            case R.id.tvWithEmail:
                UIHelper.showToast(registrationActivity, getString(R.string.implementdialog));
                break;


        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {


        titlebar.resetView();
        titlebar.setVisibility(View.GONE);
    }
}
