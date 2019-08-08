package structure.com.foodportal.fragment.foodportal;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentFoodSignupBinding;

import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.GetStartedFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;


public class FoodSignUpFragment extends BaseFragment implements View.OnClickListener {

    FragmentFoodSignupBinding binding;

    public FoodSignUpFragment() {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_signup, container, false);
        init();
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.tvWithEmail:
                validate();
                break;

        }
    }


    private void init() {
        binding.tvWithEmail.setOnClickListener(this);
    }

    private void validate() {

        if (binding.etFname.getText().toString().trim().equalsIgnoreCase("")) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.fname_required_en));
            return;
        }
        if (binding.etLname.getText().toString().trim().equalsIgnoreCase("")) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.lname_required_en));
            return;
        }
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
        if (binding.etNumber.getText().toString().equals("")) {
            UIHelper.showToast(registrationActivity, getString(R.string.phone_number_required_en));
            return;
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
//        if (binding.etConfirmPassword.getText().toString().length() < 6) {
//            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters_en));
//            return;
//        }
        if (!binding.etPassword.getText().toString().equals(binding.etConfirmPassword.getText().toString())) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.pass_do_not_match_en));
            return;
        }

        register();
    }


    public void register() {
        serviceHelper.enqueueCall(webService.userSignUp(binding.etFname.getText().toString() + binding.etLname.getText().toString(),
                binding.etEmail.getText().toString(),
                binding.etNumber.getText().toString(),
                binding.etPassword.getText().toString(),
                1), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SIGNUP);

    }

    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SIGNUP:

                registrationActivity.clearBackStack();
                registrationActivity.replaceFragment(new GetStartedFragment(), true, true);
                registrationActivity.replaceFragment(new FoodLoginFragment(), true, true);
                // User user =(User) result;
                //    user.setAcct_type(1);
                //   preferenceHelper.putUserFood(user);
                //   preferenceHelper.setLoginStatus(true);
                //   registrationActivity.finish();
                // registrationActivity.showMainActivity();


                break;


        }
    }


}
