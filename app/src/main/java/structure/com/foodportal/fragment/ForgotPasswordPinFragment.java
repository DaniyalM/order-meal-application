package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.databinding.FragmentPincodeBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.UserModel;

/**
 * Created by developer.ingic on 10/31/2018.
 */
public class ForgotPasswordPinFragment extends BaseFragment implements View.OnClickListener {

    FragmentPincodeBinding binding;
    private String code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pincode, container, false);
        init();

        return binding.getRoot();
    }

    public void setCode(String code){

        this.code=code;
    }

    private void init() {
        binding.btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnNext:
                if(binding.pinView.getText().length()<4){
                    UIHelper.showToast(registrationActivity,getString(R.string.enter_pin));
                    return;
                }
                verifyCode();
                break;


        }
    }

    private void verifyCode() {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("user_id",createPartFromString(String.valueOf(UserSignUp.getInstance().getUser_id())));
        map.put("verification_code",createPartFromString(binding.pinView.getText().toString()));
        serviceHelper.enqueueCall(webService.verifyUserCode(map), WebServiceConstants.VERIFY_PIN);

    }
    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case WebServiceConstants.VERIFY_PIN:
                UserModel userModel = (UserModel) result;
                preferenceHelper.putUser(userModel);
                break;


        }
    }


    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setTitle(getString(R.string.forgot_passwords));
        titlebar.showBackButton(registrationActivity);
        titlebar.showTitlebar();
    }


    @io.reactivex.annotations.NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value);
    }
}
