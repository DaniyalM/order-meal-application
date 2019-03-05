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

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentGetstartedBinding;
import structure.com.foodportal.fragment.foodportal.FoodLoginFragment;
import structure.com.foodportal.fragment.foodportal.FoodSignUpFragment;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

public class GetStartedFragment extends BaseFragment implements View.OnClickListener {

    FragmentGetstartedBinding binding;
    CallbackManager callbackManager;
    boolean isConnected;
     AccessToken accessToken;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_getstarted, container, false);
        init();
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

                registrationActivity.showMainActivity();
                break;
            case R.id.tvWithEmail:
                registrationActivity.replaceFragment(new FoodLoginFragment(),true,true);

                break;


        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {


        titlebar.resetView();
        titlebar.setVisibility(View.GONE);
    }
}
