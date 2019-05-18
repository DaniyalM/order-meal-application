package structure.com.foodportal.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import structure.com.foodportal.helper.BasePreferenceHelper;

//
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//import structure.com.foodportal.helper.BasePreferenceHelper;
//
//
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FireBaseIdRefreshToken";
    BasePreferenceHelper prefHelper;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        prefHelper.putDeviceToken(token);

    }

}
//
//    public static final String TAG = "MyFirebase";
//    BasePreferenceHelper prefHelper;
//
//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
//        prefHelper = new BasePreferenceHelper(getApplicationContext());
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());
//    }
//
//    /**
//     * Persist token to third-party servers.
//     * <p>
//     * Modify this method to associate the user's FCM InstanceID token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
//    private void sendRegistrationToServer(String token) {
//        // TODO: Implement this method to send token to your app server.
//
//        prefHelper.putDeviceToken(token);
//
//    }
//
//}
//
