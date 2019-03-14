package structure.com.foodportal.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.FacebookBaseFragment;
import structure.com.foodportal.databinding.FragmentGetstartedBinding;
import structure.com.foodportal.fragment.foodportal.FoodLoginFragment;
import structure.com.foodportal.fragment.foodportal.FoodSignUpFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.DataListner;
import structure.com.foodportal.models.foodModels.User;

public class GetStartedFragment extends BaseFragment implements View.OnClickListener, DataListner {

    FragmentGetstartedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_getstarted, container, false);
        init();
        registrationActivity.setcontent(this);
        registrationActivity.setcontentFB(this);
        return binding.getRoot();
    }


    public void init() {

        try {
            PackageInfo info = registrationActivity.getPackageManager().getPackageInfo(
                    "structure.com.foodportal",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));


            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        binding.getStarted.setOnClickListener(this);
        binding.proceedAsGuest.setOnClickListener(this);
        binding.signIn.setOnClickListener(this);
        binding.tvWithEmail.setOnClickListener(this);
        binding.tvWithFacebok.setOnClickListener(this);
        binding.tvWithGoogle.setOnClickListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.getStarted:
//                //UIHelper.showToast(registrationActivity,getString(R.string.implementdialog));
//                //  registrationActivity.replaceFragment(new SignupFragment(),true,false);
//
//                break;
//            case R.id.proceedAsGuest:
//
//                registrationActivity.showMainActivity();
//
//                break;
//            case R.id.signIn:
//                UIHelper.showToast(registrationActivity, getString(R.string.implementdialog));
//                //  registrationActivity.replaceFragment(new LoginFragment(),true,false);
//
//                break;


            case R.id.tvWithFacebok:
                registrationActivity.loginWithFacebook();

                break;
            case R.id.tvWithGoogle:
                registrationActivity.signIn();
                break;
            case R.id.tvWithEmail:
                registrationActivity.replaceFragment(new FoodLoginFragment(), true, true);

                break;


        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {


        titlebar.resetView();
        titlebar.setVisibility(View.GONE);
    }

    @Override
    public void getdata(JSONObject jsonObject) throws JSONException {


        String fname = jsonObject.getString("first_name");
        String lname = jsonObject.getString("last_name");
        String email = jsonObject.getString("email");
        long id = jsonObject.getLong("id");
        User user = new User();
        user.setId(String.valueOf(id));
        user.setName_en(fname + " " + lname);
        user.setAcct_type(3);
        user.setEmail(email);
        user.setProfile_picture("https://graph.facebook.com/" + id + "/picture?type=large");
        facebooklogin(user);
//        preferenceHelper.putUserFood(user);
//        preferenceHelper.setLoginStatus(true);
//        registrationActivity.showMainActivity();
//        Toast.makeText(registrationActivity, "Login Successfully", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void getdataGOOGLE(User user) {

        googlelogin(user);


    }


    public void facebooklogin(User user) {
        serviceHelper.enqueueCall(webService.LoginFACEBOOK(user.getEmail(), String.valueOf(user.getId()), user.getName_en(), "facebook"), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_FACEBOOK);


    }

    public void googlelogin(User user) {
        serviceHelper.enqueueCall(webService.LoginGOOGLE(user.getEmail(), user.getId(), user.getName_en(), "google",user.getProfile_picture()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_FACEBOOK);


    }

    User user;
    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_FACEBOOK:

                 user = (User) JsonHelpers.convertToModelClass(result, User.class);
                preferenceHelper.putUserFood(user);
                Toast.makeText(registrationActivity, "Login Successfully", Toast.LENGTH_SHORT).show();
                preferenceHelper.setLoginStatus(true);
                registrationActivity.showMainActivity();

                break;
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_GOOGLE:

                 user = (User) JsonHelpers.convertToModelClass(result, User.class);
                preferenceHelper.putUserFood(user);
                Toast.makeText(registrationActivity, "Login Successfully", Toast.LENGTH_SHORT).show();
                preferenceHelper.setLoginStatus(true);
                registrationActivity.showMainActivity();

                break;


        }
    }

}
