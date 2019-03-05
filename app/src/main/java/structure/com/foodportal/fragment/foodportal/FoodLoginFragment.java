package structure.com.foodportal.fragment.foodportal;



import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;
import structure.com.foodportal.R;


import structure.com.foodportal.databinding.FragmentFoodLoginBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.GetStartedFragment;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

public class FoodLoginFragment extends BaseFragment implements View.OnClickListener {

         FragmentFoodLoginBinding binding;

    public FoodLoginFragment() {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_login, container, false);
        init();
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btnSignin:
                validate();
                break;

            case R.id.tvsociallogin:
             registrationActivity.clearBackStack();
             registrationActivity.replaceFragment(new GetStartedFragment(),true,true);
                break;

            case R.id.btnSignup:
                registrationActivity.replaceFragment(new FoodSignUpFragment(),true,true);

                break;

        }
    }


    private void init() {
        binding.tvsociallogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.btnSignin.setOnClickListener(this);
    }

    private void validate() {
  if (binding.etemail.getText().toString().trim().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.repuired_email));
            return; }

        if (binding.etemail.getText().toString().trim().length() > 0) {
            if (!CustomValidation.isValidEmail(binding.etemail.getText().toString())) {
                UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.err_email));
                return; } }

        if (binding.etpassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_required));
            return; }

        if (binding.etpassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters));
            return; }




        login();


    }


    public class LoginUser{

        String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {

            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        String password;

        public LoginUser(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public void login() {
        LoginUser mInfo=new LoginUser(binding.etemail.getText().toString(),binding.etpassword.getText().toString());
        String request=new Gson().toJson(mInfo);

        //Here the json data is add to a hash map with key data
        Map<String,String> params = new HashMap<String, String>();
        params.put("email", binding.etemail.getText().toString());
        params.put("password", binding.etpassword.getText().toString());

        serviceHelper.enqueueCall(webService.userlogin(params), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_LOGIN);

    }
    @NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value);
    }
    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_LOGIN:



                break;


        }
    }


}
