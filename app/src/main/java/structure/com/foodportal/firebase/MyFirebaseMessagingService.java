package structure.com.foodportal.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.activity.SplashActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.interfaces.DataLoadedListener;
import structure.com.foodportal.models.FCMPayload;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static int NOTIFICATION_ID = 1;
    DataLoadedListener dataLoadedListener;


    @Override
    public void onNewToken(String s) {
//        super.onNewToken(s);
        BasePreferenceHelper.setDeviceToken(getApplicationContext(), s.trim());
        Log.i("Firebase Token", ""+s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);


        makepush(remoteMessage);
       // sendPushNotification(remoteMessage);
        Log.i("testing", "onMessageReceived"+remoteMessage.getData());
    }
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private void makepush(RemoteMessage message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.add(FLAG_ACTIVITY_SINGLE_TOP );
        FCMPayload fcmPayload =new FCMPayload();
//        fcmPayload =(FCMPayload) message.getData() ;

        if(message.getData() != null){
            if(message.getData().get("action_type") != null){
                //  builder.setContentTitle(message.getData().get("action_type"));
                fcmPayload.setAction_type(message.getData().get("action_type"));
            }
            if(message.getData().get("ref_id") != null){
                //  builder.setContentText(message.getData().get("ref_id"));
                fcmPayload.setRef_id(Integer.valueOf(message.getData().get("ref_id")));
            }
            if(message.getData().get("slug") != null){
                fcmPayload.setSlug(message.getData().get("slug"));
            }
            if(message.getData().get("action_type") != null){
                fcmPayload.setAction_type(message.getData().get("action_type"));
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.FcmHelper.FCM_DATA_PAYLOAD, fcmPayload);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,   PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.mirchiblack)
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.foodtribune);
            String description = getString(R.string.foodDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

//    private void sendPushNotification(RemoteMessage remoteMessage){
//        FCMPayload fcmPayload = new FCMPayload();
//        Bundle bundle = new Bundle();
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setSmallIcon(R.drawable.notification);
//        if(remoteMessage.getData() != null){
//            if(remoteMessage.getData().get("title") != null){
//                mBuilder.setContentTitle(remoteMessage.getData().get("title"));
//                fcmPayload.setTitle(remoteMessage.getData().get("title"));
//            }
//            if(remoteMessage.getData().get("message") != null){
//                mBuilder.setContentText(remoteMessage.getData().get("message"));
//                fcmPayload.setMessage(remoteMessage.getData().get("message"));
//            }
//            if(remoteMessage.getData().get("action_id") != null){
//                fcmPayload.setAction_id(Integer.parseInt(remoteMessage.getData().get("action_id")));
//            }
//            if(remoteMessage.getData().get("action_type") != null){
//                fcmPayload.setAction_type(remoteMessage.getData().get("action_type"));
//            }
//        }
//        mBuilder.setAutoCancel(true);
//
//        bundle.putSerializable(AppConstant.FcmHelper.FCM_DATA_PAYLOAD, fcmPayload);
//        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pendingIntent);
//
//        if(notificationManager != null){
//            notificationManager.cancel(NOTIFICATION_ID);
//            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//        }
//    }
}

