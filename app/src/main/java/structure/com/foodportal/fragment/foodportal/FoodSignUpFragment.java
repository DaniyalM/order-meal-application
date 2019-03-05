package structure.com.foodportal.fragment.foodportal;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.OnCountryPickerListener;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.databinding.FragmentFoodSignupBinding;
import structure.com.foodportal.databinding.FragmentSignupThreeBinding;

import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.models.UserModel;
import structure.com.foodportal.models.UserSignUpModel;


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

        if (binding.etfname.getText().toString().trim().equalsIgnoreCase("")) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.fname_require));
            return; }
        if (binding.etlname.getText().toString().trim().equalsIgnoreCase("")) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.lname_require));
            return; }
        if (binding.etemail.getText().toString().trim().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.repuired_email));
            return; }

        if (binding.etemail.getText().toString().trim().length() > 0) {
            if (!CustomValidation.isValidEmail(binding.etemail.getText().toString())) {
                UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.err_email));
                return; } }

        if (binding.etnumber.getText().toString().equals("")) {
            UIHelper.showToast(registrationActivity, getString(R.string.phone_number_reqired));
            return; }

        if (binding.etpassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_required));
            return; }

        if (binding.etpassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters));
            return; }

        if (binding.etconfirmpassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.confirm_password_required));
            return; }

        if (binding.etconfirmpassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters));
            return; }

        if (!binding.etpassword.getText().toString().equals(binding.etconfirmpassword.getText().toString())) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.samepass_error));
            return; }

        register();


    }


    public void register() {
        serviceHelper.enqueueCall(webService.userSignUp(binding.etfname.getText().toString() + binding.etlname.getText().toString(),
                binding.tvWithEmail.getText().toString(),
                binding.etnumber.getText().toString(),
                binding.etpassword.getText().toString(),
                1), WebServiceConstants.USER_SIGNUP);

    }

    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SIGNUP:



                break;


        }
    }


}
