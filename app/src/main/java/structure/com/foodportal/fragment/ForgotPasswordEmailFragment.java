package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentForgotPasswordEmailBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.webservice.StringWarpper;

/**
 * Created by developer.ingic on 10/31/2018.
 */
public class ForgotPasswordEmailFragment extends BaseFragment implements View.OnClickListener {
    FragmentForgotPasswordEmailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password_email, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(getString(R.string.forgot_passwords));
        titlebar.showTitlebar();
        titlebar.showBackButton(registrationActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                if(isValidated())
                    serviceHelper.enqueueCall(webService.forgotPasswordEmail(binding.etEmail.getText().toString()), AppConstant.FORGOT_PASSWORD);
                break;
        }

    }

    public void init() {
        binding.ctEmail.setErrorEnabled();
        binding.btnSubmit.setOnClickListener(this);
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FORGOT_PASSWORD:
               String code= ((StringWarpper) result).getResetCode();
                ForgotChangePassword forgotChangePassword =new ForgotChangePassword();
                forgotChangePassword.setEmailandCode(binding.etEmail.getText().toString(),code);
                registrationActivity.replaceFragment(forgotChangePassword,true,true);

                break;
        }
    }

    private boolean isValidated() {
        if (binding.etEmail.getText().toString().trim().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.email_required_en));
            return false;
        }

        if (binding.etEmail.getText().toString().trim().length() > 0) {
            if (!CustomValidation.isValidEmail(binding.etEmail.getText().toString())) {
                UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.invalid_email_en));
                return false;
            }
        }

        return true;
    }



}
