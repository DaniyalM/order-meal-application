package structure.com.foodportal.fragment.foodportal;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
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
    private int mLang;
    private String mFnameRequired, mLnameRequired, mEmailRequired, mInvalidEmail, mPhoneNumberRequired,
            mPasswordRequired, mPasswordCharacters, mConfirmPassRequired, mPassDoesNotMatch;

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
    public void onStart() {
        super.onStart();
        mLang = preferenceHelper.getSelectedLanguage();
        setValuesByLanguage(mLang);
    }

    private void setValuesByLanguage(int language) {
        binding.linearLayoutName.removeAllViews();

        switch (language) {
            case AppConstant.Language.ENGLISH:
                binding.tvCreateYourAccount.setText(registrationActivity.getString(R.string.create_your_account_en));
                binding.tvFname.setText(registrationActivity.getString(R.string.fname_en));
                binding.tvLname.setText(registrationActivity.getString(R.string.lname_en));
                binding.tvEmail.setText(registrationActivity.getString(R.string.email_en));
                binding.tvPhoneNumber.setText(registrationActivity.getString(R.string.phone_number_en));
                binding.tvPassword.setText(registrationActivity.getString(R.string.password_en));
                binding.tvConfirmPassword.setText(registrationActivity.getString(R.string.confirm_password_en));
                binding.btnCreateAccount.setText(registrationActivity.getString(R.string.create_account_en));

                binding.linearLayoutName.addView(binding.linearLayoutFirstName, 0);
                binding.linearLayoutName.addView(binding.viewSpace, 1);
                binding.linearLayoutName.addView(binding.linearLayoutLastName, 2);

                binding.etFname.setGravity(Gravity.START);
                binding.etLname.setGravity(Gravity.START);
                binding.etEmail.setGravity(Gravity.START);
                binding.etPhoneNumber.setGravity(Gravity.START);
                binding.etPassword.setGravity(Gravity.START);
                binding.etConfirmPassword.setGravity(Gravity.START);

                mFnameRequired = registrationActivity.getResources().getString(R.string.fname_required_en);
                mLnameRequired = registrationActivity.getResources().getString(R.string.lname_required_en);
                mEmailRequired = registrationActivity.getResources().getString(R.string.email_required_en);
                mInvalidEmail = registrationActivity.getResources().getString(R.string.invalid_email_en);
                mPhoneNumberRequired = registrationActivity.getResources().getString(R.string.phone_number_required_en);
                mPasswordRequired = registrationActivity.getResources().getString(R.string.password_required_en);
                mPasswordCharacters = registrationActivity.getResources().getString(R.string.password_characters_en);
                mConfirmPassRequired = registrationActivity.getResources().getString(R.string.confirm_password_required_en);
                mPassDoesNotMatch = registrationActivity.getResources().getString(R.string.pass_do_not_match_en);
                break;

            case AppConstant.Language.URDU:
                binding.tvCreateYourAccount.setText(registrationActivity.getString(R.string.create_your_account_ur));
                binding.tvFname.setText(registrationActivity.getString(R.string.fname_ur));
                binding.tvLname.setText(registrationActivity.getString(R.string.lname_ur));
                binding.tvEmail.setText(registrationActivity.getString(R.string.email_ur));
                binding.tvPhoneNumber.setText(registrationActivity.getString(R.string.phone_number_ur));
                binding.tvPassword.setText(registrationActivity.getString(R.string.password_ur));
                binding.tvConfirmPassword.setText(registrationActivity.getString(R.string.confirm_password_ur));
                binding.btnCreateAccount.setText(registrationActivity.getString(R.string.create_account_ur));

                binding.linearLayoutName.addView(binding.linearLayoutLastName, 0);
                binding.linearLayoutName.addView(binding.viewSpace, 1);
                binding.linearLayoutName.addView(binding.linearLayoutFirstName, 2);

                binding.etFname.setGravity(Gravity.END);
                binding.etLname.setGravity(Gravity.END);
                binding.etEmail.setGravity(Gravity.END);
                binding.etPhoneNumber.setGravity(Gravity.END);
                binding.etPassword.setGravity(Gravity.END);
                binding.etConfirmPassword.setGravity(Gravity.END);

                mFnameRequired = registrationActivity.getResources().getString(R.string.fname_required_ur);
                mLnameRequired = registrationActivity.getResources().getString(R.string.lname_required_ur);
                mEmailRequired = registrationActivity.getResources().getString(R.string.email_required_ur);
                mInvalidEmail = registrationActivity.getResources().getString(R.string.invalid_email_ur);
                mPhoneNumberRequired = registrationActivity.getResources().getString(R.string.phone_number_required_ur);
                mPasswordRequired = registrationActivity.getResources().getString(R.string.password_required_ur);
                mPasswordCharacters = registrationActivity.getResources().getString(R.string.password_characters_ur);
                mConfirmPassRequired = registrationActivity.getResources().getString(R.string.confirm_password_required_ur);
                mPassDoesNotMatch = registrationActivity.getResources().getString(R.string.pass_do_not_match_ur);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btnCreateAccount:
                validate();
                break;

        }
    }


    private void init() {
        binding.btnCreateAccount.setOnClickListener(this);
    }

    private void validate() {

        if (binding.etFname.getText().toString().trim().equalsIgnoreCase("")) {
            UIHelper.showToast(registrationActivity, mFnameRequired);
            return;
        }
        if (binding.etLname.getText().toString().trim().equalsIgnoreCase("")) {
            UIHelper.showToast(registrationActivity, mLnameRequired);
            return;
        }
        if (binding.etEmail.getText().toString().trim().length() == 0) {
            UIHelper.showToast(registrationActivity, mEmailRequired);
            return;
        }
        if (binding.etEmail.getText().toString().trim().length() > 0) {
            if (!CustomValidation.isValidEmail(binding.etEmail.getText().toString())) {
                UIHelper.showToast(registrationActivity, mInvalidEmail);
                return;
            }
        }
        if (binding.etPhoneNumber.getText().toString().equals("")) {
            UIHelper.showToast(registrationActivity, mPhoneNumberRequired);
            return;
        }
        if (binding.etPassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, mPasswordRequired);
            return;
        }
        if (binding.etPassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, mPasswordCharacters);
            return;
        }
        if (binding.etConfirmPassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, mConfirmPassRequired);
            return;
        }
//        if (binding.etConfirmPassword.getText().toString().length() < 6) {
//            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters_en));
//            return;
//        }
        if (!binding.etPassword.getText().toString().equals(binding.etConfirmPassword.getText().toString())) {
            UIHelper.showToast(registrationActivity, mPassDoesNotMatch);
            return;
        }

        register();
    }


    public void register() {
        serviceHelper.enqueueCall(webService.userSignUp(binding.etFname.getText().toString() + binding.etLname.getText().toString(),
                binding.etEmail.getText().toString(),
                binding.etPhoneNumber.getText().toString(),
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
