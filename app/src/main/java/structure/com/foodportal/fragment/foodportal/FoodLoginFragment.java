package structure.com.foodportal.fragment.foodportal;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;
import structure.com.foodportal.R;


import structure.com.foodportal.databinding.FragmentFoodLoginBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.GetStartedFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.foodModels.LoginBody;
import structure.com.foodportal.models.foodModels.User;

public class FoodLoginFragment extends BaseFragment implements View.OnClickListener {

    FragmentFoodLoginBinding binding;
    private int mLang;
    private String mEmailRequired, mInvalidEmail, mPasswordRequired, mPasswordCharacters;

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
    public void onStart() {
        super.onStart();
        mLang = preferenceHelper.getSelectedLanguage();
        setValuesByLanguage(mLang);
    }

    private void setValuesByLanguage(int language) {
        switch (language) {
            case AppConstant.Language.ENGLISH:
            default:
                binding.tvEmail.setText(registrationActivity.getString(R.string.email_en));
                binding.tvPassword.setText(registrationActivity.getString(R.string.password_en));
                binding.btnLogin.setText(registrationActivity.getString(R.string.login_en));
                binding.btnSignUp.setText(registrationActivity.getString(R.string.sign_up_en));
                binding.tvSocialLogin.setText(registrationActivity.getString(R.string.social_login_en));

                binding.etEmail.setGravity(Gravity.START);
                binding.etPassword.setGravity(Gravity.START);

                mEmailRequired = registrationActivity.getResources().getString(R.string.email_required_en);
                mInvalidEmail = registrationActivity.getResources().getString(R.string.invalid_email_en);
                mPasswordRequired = registrationActivity.getResources().getString(R.string.password_required_en);
                mPasswordCharacters = registrationActivity.getResources().getString(R.string.password_characters_en);
                break;

            case AppConstant.Language.URDU:
                binding.tvEmail.setText(registrationActivity.getString(R.string.email_ur));
                binding.tvPassword.setText(registrationActivity.getString(R.string.password_ur));
                binding.btnLogin.setText(registrationActivity.getString(R.string.login_ur));
                binding.btnSignUp.setText(registrationActivity.getString(R.string.sign_up_ur));
                binding.tvSocialLogin.setText(registrationActivity.getString(R.string.social_login_ur));

                binding.etEmail.setGravity(Gravity.END);
                binding.etPassword.setGravity(Gravity.END);

                mEmailRequired = registrationActivity.getResources().getString(R.string.email_required_ur);
                mInvalidEmail = registrationActivity.getResources().getString(R.string.invalid_email_ur);
                mPasswordRequired = registrationActivity.getResources().getString(R.string.password_required_ur);
                mPasswordCharacters = registrationActivity.getResources().getString(R.string.password_characters_ur);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btn_login:
                try {
                    validate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_social_login:
                registrationActivity.clearBackStack();
                registrationActivity.replaceFragment(new GetStartedFragment(), true, true);
                break;

            case R.id.btn_sign_up:
                registrationActivity.replaceFragment(new FoodSignUpFragment(), true, true);

                break;

        }
    }


    private void init() {
        binding.tvSocialLogin.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    private void validate() throws JSONException {

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

        if (binding.etPassword.getText().toString().length() == 0) {
            UIHelper.showToast(registrationActivity, mPasswordRequired);
            return;
        }

        if (binding.etPassword.getText().toString().length() < 6) {
            UIHelper.showToast(registrationActivity, mPasswordCharacters);
            return;
        }


        login();


    }


    public class LoginUser {

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

    public void login() throws JSONException {
        LoginUser mInfo = new LoginUser(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
        String request = new Gson().toJson(mInfo);

        //Here the json data is add to a hash map with key data
        Map<String, String> params = new HashMap<>();
        params.put("email", binding.etEmail.getText().toString());
        params.put("password", binding.etPassword.getText().toString());

        LoginBody loginBody = new LoginBody(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());


        JSONObject row = new JSONObject();
        row.put("email", binding.etEmail.getText().toString());
        row.put("password", binding.etPassword.getText().toString());

        serviceHelper.enqueueCall(webService.userlogin(binding.etEmail.getText().toString(), binding.etPassword.getText().toString(), "android", preferenceHelper.getDeviceToken()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_LOGIN);

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

                User foodblog = (User) JsonHelpers.convertToModelClass(result, User.class);
                preferenceHelper.putUserFood(foodblog);
                preferenceHelper.setLoginStatus(true);
                registrationActivity.showMainActivity();

                break;


        }
    }


}
