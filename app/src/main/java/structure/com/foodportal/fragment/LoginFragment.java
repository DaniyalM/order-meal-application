package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentLoginBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.UserModel;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    FragmentLoginBinding binding;

    public LoginFragment() {
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(registrationActivity.getResources().getString(R.string.sign_in));
        titlebar.showTitlebar();
        titlebar.showBackButton(registrationActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignin:
                if (isValidated()) {
                    serviceHelper.enqueueCall(webService.loginUser(binding.etEmail.getText().toString(), binding.etPassword.getText().toString(), "0",
                            AppConstant.DEVICE_TYPE, preferenceHelper.getDeviceToken()), AppConstant.USER_LOGIN);
                }
                break;
            case R.id.tvForgotPassword:
                //UIHelper.showToast(registrationActivity, getResources().getString(R.string.implementdialog));
                replaceFragment(new ForgotPasswordEmailFragment(), true, true);
                break;

            case R.id.btnSignup:
                //  UIHelper.showToast(registrationActivity, getResources().getString(R.string.implementdialog));
                replaceFragment(new SignupFragment(), true, false);
                break;
            case R.id.tvGuestSignin:
                registrationActivity.showMainActivity();
                break;

            case R.id.btnSignupFb:
                UIHelper.showToast(registrationActivity, getResources().getString(R.string.implementdialog));
                break;

            case R.id.btnSignupGoogle:
                UIHelper.showToast(registrationActivity, getResources().getString(R.string.implementdialog));
                break;
        }
    }

    private void init() {
        binding.btnSignin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.tvForgotPassword.setOnClickListener(this);
        binding.btnSignupFb.setOnClickListener(this);
        binding.btnSignupGoogle.setOnClickListener(this);
        binding.tvGuestSignin.setOnClickListener(this);
        binding.ctEmail.setErrorEnabled();
        binding.ctPassword.setErrorEnabled();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.USER_LOGIN:
                UserModel model = (UserModel) result;

                if (model.getIs_verified() == 1) {
                    UIHelper.showToast(registrationActivity, getString(R.string.login_success));
                    preferenceHelper.putUser(model);
                    preferenceHelper.setLoginStatus(true);
                    registrationActivity.showMainActivity();
                } else {

                    PinFragment pinFragment = new PinFragment();
                    pinFragment.setUserId(String.valueOf(model.getId()));
                    replaceFragment(pinFragment, true, true);
                }
                break;
        }
    }

    private boolean isValidated() {
        UIHelper.hideSoftKeyboards(registrationActivity);

        if (binding.etEmail.getText().toString().trim().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.repuired_email));
            return false;
        }

        if (binding.etEmail.getText().toString().trim().length() > 0) {
            if (!CustomValidation.isValidEmail(binding.etEmail.getText().toString())) {
                UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.err_email));
                return false;
            }
        }

        if (binding.etPassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_required));
            return false;
        }

        if (binding.etPassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters));
            return false;
        }

        /*
        if (CustomValidation.validateLength(binding.etEmail, binding.ctEmail, registrationActivity.getString(R.string.repuired_email), "1", "50"))
            if (CustomValidation.validateEmail(binding.etEmail, binding.ctEmail, getString(R.string.err_email)))
                if (CustomValidation.validatePassword(binding.etPassword, binding.ctPassword, getString(R.string.err_password))) {
                    if (CustomValidation.validateLength(binding.etPassword, binding.ctPassword, "Length must be greater than 4", "5", "15")) {
                        return true;
                    }
                }
        return false;
        */
        return true;
    }

}
