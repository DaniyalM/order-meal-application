package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.databinding.FragmentPincodeBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CountDownInterval;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.UserModel;

public class PinFragment extends BaseFragment implements View.OnClickListener {


    FragmentPincodeBinding binding;
    @BindView(R.id.tvResend)
    TextView tvResend;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    Unbinder unbinder;
    private String code;
    private CountDownTimer counttimer;

    String user_id;
    public void setUserId(String user_id){

    this.user_id=user_id;



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pincode, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
        init();
        setTimer();

        return binding.getRoot();
    }

    public void setCode(String code) {

        this.code = code;
    }

    private void init() {
        code = registrationActivity.getUserModel();
       // binding.pinView.setText(code);
        binding.btnNext.setOnClickListener(this);
        tvResend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnNext:
                UIHelper.hideSoftKeyboards(registrationActivity);
                if (binding.pinView.getText().length() < 4) {
                    UIHelper.showToast(registrationActivity, getString(R.string.enter_pin));
                    return;
                }
                verifyCode();

                break;

            case R.id.tvResend:
                UIHelper.hideSoftKeyboards(registrationActivity);
                resend();
                break;


        }
    }

    private void verifyCode() {
        Map<String, RequestBody> map = new HashMap<>();
//        map.put("full_name", createPartFromString(userSignUpModel.getFullAddress()));
//        map.put("country_code", createPartFromString(userSignUpModel.getCountryCode()));
//        serviceHelper.enqueueCall(webService.registerUser(map, body), WebServiceConstants.USER_SIGNUP);

       if(user_id!=null){

           map.put("user_id", createPartFromString(user_id));

       }else {

           map.put("user_id", createPartFromString(String.valueOf(UserSignUp.getInstance().getUser_id())));

       }

        map.put("verification_code", createPartFromString(binding.pinView.getText().toString()));
        serviceHelper.enqueueCall(webService.verifyUserCode(map), WebServiceConstants.VERIFY_PIN);

    }

    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case WebServiceConstants.VERIFY_PIN:
                UserModel userModel = (UserModel) result;
                preferenceHelper.putUser(userModel);
                preferenceHelper.setLoginStatus(true);
                UIHelper.hideSoftKeyboards(registrationActivity);
                if(user_id==null){

                    registrationActivity.getSignupPager().setCurrentItem(5);
                }else{

                    registrationActivity.showMainActivity();
                }
                //     initCategorySpinner(category.getCategories());
                break;

            case AppConstant.RESEND_CODE:
                UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.code_has_been_resend));
                setTimer();

                break;


        }
    }


    @Override
    protected void setTitle(Titlebar titlebar) {


    }


    @NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                MultipartBody.FORM, value);
    }

    private void setTimer() {
        tvResend.setEnabled(false);
        tvResend.setAlpha(0.5f);
        if (counttimer != null)
            counttimer.cancel();
        counttimer = new CountDownInterval(tvTimer, 1 * 60 * 1000, 400) {
            @Override
            public void onFinish() {
                tvResend.setAlpha(1f);
                tvResend.setEnabled(true);
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void resend(){

        serviceHelper.enqueueCall(webService.resendCode(UserSignUp.getInstance().getEmail()),AppConstant.RESEND_CODE);


    }

}
