package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
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
import structure.com.foodportal.databinding.FragmentSignupThreeBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.UserModel;
import structure.com.foodportal.models.UserSignUpModel;


public class SignupThreeFragment extends BaseFragment implements View.OnClickListener {

    FragmentSignupThreeBinding binding;

    public SignupThreeFragment() {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_three, container, false);
        init();
        return binding.getRoot();
    }

    private void getSetCode() {
        registrationActivity.getSetCode(new OnCountryPickerListener() {
            @Override
            public void onSelectCountry(Country country) {
                binding.tvCodePicker.setText(country.getDialCode());
                binding.tvCodePicker.setCompoundDrawablesWithIntrinsicBounds(country.getFlag(), 0, 0, 0);


            }
        });
    }
    TermsAndConditon termsAndConditon;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCodePicker:
                getSetCode();
                break;

            case R.id.btnNext:
                validate();
                break;
            case R.id.tvTerm:
                UIHelper.showToast(registrationActivity,registrationActivity.getString(R.string.implementdialog));
                 termsAndConditon =new TermsAndConditon();
                 termsAndConditon.setkey("Terms and Condition");
                registrationActivity.replaceFragment(termsAndConditon,true,true);
                break;
            case R.id.tvPrivacy:
                UIHelper.showToast(registrationActivity,registrationActivity.getString(R.string.implementdialog));
                termsAndConditon =new TermsAndConditon();
                termsAndConditon.setkey("Privacy Policy");
                registrationActivity.replaceFragment(termsAndConditon,true,true);
                break;


        }
    }

    private void init() {
        binding.tvCodePicker.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.tvTerm.setOnClickListener(this);
        binding.tvPrivacy.setOnClickListener(this);
        registrationActivity.hideLoader();
        binding.ctNumber.setErrorEnabled();
    }

    private void validate() {
        UIHelper.hideSoftKeyboards(registrationActivity);

        if (binding.etNumber.getText().toString().equals("")) {
            UIHelper.showToast(registrationActivity, getString(R.string.phone_number_required_en));
            return;
        }

        if (CustomValidation.validateLength(binding.etNumber, binding.ctNumber, getString(R.string.error_phone_number), "8", "15")) {
            UserSignUpModel userSignUpModel = UserSignUp.getInstance().userSignUpModel
                    .withcountryCode(binding.tvCodePicker.getText().toString())
                    .withphoneNo(binding.etNumber.getText().toString())
                    .withdeviceId(preferenceHelper.getDeviceToken())
                    .withdeviceType(AppConstant.DEVICE_TYPE)
                    .withfacebookAuthToken("")
                    .withlang("en_us")
                    .withzipCode("12345")
                    .withgoogleAuthToken("").build();

            register(userSignUpModel);
        }
        //  registrationActivity.getSignupPager().setCurrentItem(4);


    }

    public void register(UserSignUpModel userSignUpModel) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("full_name", createPartFromString(userSignUpModel.getFullName()));
        map.put("country_code", createPartFromString(userSignUpModel.getCountryCode()));
        map.put("phone_no", createPartFromString(userSignUpModel.getPhoneNo()));
        map.put("zip_code", createPartFromString(userSignUpModel.getZipCode()));
        map.put("full_address", createPartFromString(userSignUpModel.getFullAddress()));
        map.put("email", createPartFromString(userSignUpModel.getEmail()));
        map.put("password", createPartFromString(userSignUpModel.getPassword()));
        map.put("device_type", createPartFromString(userSignUpModel.getDeviceType()));
        map.put("device_id", createPartFromString(userSignUpModel.getDeviceId()));
        map.put("lang", createPartFromString(userSignUpModel.getLang()));
        map.put("GoogleAuthToken", createPartFromString(userSignUpModel.getGoogleAuthToken()));
        map.put("FacebookAuthToken", createPartFromString(userSignUpModel.getFacebookAuthToken()));
        serviceHelper.enqueueCall(webService.registerUser(map, null), WebServiceConstants.USER_SIGNUP);

    }


    @NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value);
    }

    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case WebServiceConstants.USER_SIGNUP:

                UserModel userModel = (UserModel) result;
                UserSignUp.getInstance().setUser_id(userModel.getId());
                registrationActivity.setUserModel(userModel.getVerification().getVerificationCode());

                ///   UserSignUp.getInstance().setCode(userModel.getVerification().getVerificationCode());

                registrationActivity.getSignupPager().setCurrentItem(4);
                break;


        }
    }


}
