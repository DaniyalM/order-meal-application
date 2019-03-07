package structure.com.foodportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import structure.com.foodportal.interfaces.foodInterfaces.DataListner;

public abstract class FacebookBaseFragment extends BaseActivity {

    public static  CallbackManager callbackManager;
    private AccessToken accessToken;

    public static boolean isConnected ;



    public abstract void onSocialInfoFetched(JSONObject data);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(this);
        //AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                fetchUserInfo(accessToken);
                isConnected=true;
                //Log.e("fb", accessToken.getUserId());

                //Log.e("accessToken", "accessToken "+accessToken.getToken());
                disconnectSocialNetworks();

                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token != null) {
                    Log.e("accessToken2", "accessToken2 "+token);
                }
            }

            @Override
            public void onCancel() {
                Log.e("onCancel", "onCancel");

            }

            @Override
            public void onError(FacebookException e) {

                Log.e("error", e.toString());
            }

        });

        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

    }


    public void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile,email"));
//		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }


    private void fetchUserInfo(AccessToken accessToken) {
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                    onSocialInfoFetched(jsonObject);
                    try {
                        dataListner.getdata(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    disconnectSocialNetworks();
                    //Log.e("FbUserData", jsonObject.toString());
                }
            });
            GraphRequest.executeBatchAsync(request);

            Bundle parameters = new Bundle();
            parameters.putString("fields","id,first_name,last_name,email");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }



    protected void logoutFromFacebook() {
        LoginManager.getInstance().logOut();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void disconnectSocialNetworks() {
        logoutFromFacebook();
    }

    DataListner dataListner;
    public  void setcontent(DataListner dataListner) {
        this.dataListner= dataListner;

    }
}
