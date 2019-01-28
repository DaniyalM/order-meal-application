package structure.com.foodportal.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.models.FCMPayload;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static int NOTIFICATION_ID = 1;

    @Override
    public void onNewToken(String s) {
//        super.onNewToken(s);
        BasePreferenceHelper.setDeviceToken(getApplicationContext(), s.trim());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

        sendPushNotification(remoteMessage);
        Log.i("testing", "onMessageReceived");
    }

    private void sendPushNotification(RemoteMessage remoteMessage){
        FCMPayload fcmPayload = new FCMPayload();
        Bundle bundle = new Bundle();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.notification);
        if(remoteMessage.getData() != null){
            if(remoteMessage.getData().get("title") != null){
                mBuilder.setContentTitle(remoteMessage.getData().get("title"));
                fcmPayload.setTitle(remoteMessage.getData().get("title"));
            }
            if(remoteMessage.getData().get("message") != null){
                mBuilder.setContentText(remoteMessage.getData().get("message"));
                fcmPayload.setMessage(remoteMessage.getData().get("message"));
            }
            if(remoteMessage.getData().get("action_id") != null){
                fcmPayload.setAction_id(Integer.parseInt(remoteMessage.getData().get("action_id")));
            }
            if(remoteMessage.getData().get("action_type") != null){
                fcmPayload.setAction_type(remoteMessage.getData().get("action_type"));
            }
        }
        mBuilder.setAutoCancel(true);

        bundle.putSerializable(AppConstant.FcmHelper.FCM_DATA_PAYLOAD, fcmPayload);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        if(notificationManager != null){
            notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }
}

