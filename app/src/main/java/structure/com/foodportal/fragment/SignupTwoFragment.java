package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.databinding.FragmentSignupTwoBinding;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

public class SignupTwoFragment extends BaseFragment {

    FragmentSignupTwoBinding binding;


    public SignupTwoFragment() {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_two, container, false);
        init();
        return binding.getRoot();
    }

    public void init() {
        binding.ctEmail.setErrorEnabled();
        binding.ctPassword.setErrorEnabled();
        binding.ctConfirmPassword.setErrorEnabled();

        binding.btnNext.setOnClickListener(view -> validate());
        binding.tvTerms.setOnClickListener(view -> {
            showDialog();


        });
        binding.tvPolicy.setOnClickListener(view -> {
            showPrivayDialog();
        });

    }

    private void validate() {
        UIHelper.hideSoftKeyboards(registrationActivity);
//        if (CustomValidation.validateEmail(binding.etEmail, binding.ctEmail, getString(R.string.err_email)))
//            if (CustomValidation.validateLength(binding.etPassword, binding.ctPassword, "Length must be greater than 4", "5", "15"))
//                if (CustomValidation.validateNewConfirmPassword(binding.etPassword, binding.etConfirmPassword, binding.ctConfirmPassword, registrationActivity))
        if (binding.etEmail.getText().toString().trim().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.email_required_en));
            return;
        }

        if (binding.etEmail.getText().toString().trim().length() > 0) {
            if (!CustomValidation.isValidEmail(binding.etEmail.getText().toString())) {
                UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.invalid_email_en));
                return;
            }
        }

        if (binding.etPassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_required_en));
            return;
        }

        if (binding.etPassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters_en));
            return;
        }

        if (binding.etConfirmPassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.confirm_password_required_en));
            return;
        }

        if (binding.etConfirmPassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters_en));
            return;
        }

        if (!binding.etPassword.getText().toString().equals(binding.etConfirmPassword.getText().toString())) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.pass_do_not_match_en));
            return;
        }

        UserSignUp.getInstance().setEmail(binding.etEmail.getText().toString());
        UserSignUp.getInstance().userSignUpModel.withemail(binding.etEmail.getText().toString()).withpassword(binding.etPassword.getText().toString());
        registrationActivity.getSignupPager().setCurrentItem(2);
    }

    void showDialog() {

        DialogFactory.createSingleButtonDialog(registrationActivity, (dialogInterface, i) -> {

            dialogInterface.dismiss();
        }, registrationActivity.getString(R.string.dummy_text), registrationActivity.getString(R.string.terms_condition)).show();


    }

    void showPrivayDialog() {
        DialogFactory.createSingleButtonDialog(registrationActivity, (dialogInterface, i) -> {

            dialogInterface.dismiss();
        }, registrationActivity.getString(R.string.dummy_textsecond), registrationActivity.getString(R.string.privacy_policy)).show();


    }


}
