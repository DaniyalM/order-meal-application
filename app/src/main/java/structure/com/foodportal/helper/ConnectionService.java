package structure.com.foodportal.helper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.Timer;
import java.util.TimerTask;

import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.interfaces.foodInterfaces.NetworkListner;


public class ConnectionService extends Service {

    // Constant
    public static String TAG_INTERVAL = "interval";
    public static String TAG_URL_PING = "url_ping";
    public static String TAG_ACTIVITY_NAME = "activity_name";

    private int interval;
    private String url_ping;
    private String activity_name;

    private Timer mTimer = null;

    ConnectionServiceCallback mConnectionServiceCallback;
    private MainActivity mainActivity;
    private NetworkListner networkListner;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public interface ConnectionServiceCallback {
        void hasInternetConnection();
        void hasNoInternetConnection();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        interval = intent.getIntExtra(TAG_INTERVAL, 10);
        url_ping = intent.getStringExtra(TAG_URL_PING);
        activity_name = intent.getStringExtra(TAG_ACTIVITY_NAME);

        try {
            mConnectionServiceCallback = (ConnectionServiceCallback) Class.forName(activity_name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new CheckForConnection(), 0, interval * 1000);

        return super.onStartCommand(intent, flags, startId);
    }

    class CheckForConnection extends TimerTask {
        @Override
        public void run() {
            isNetworkAvailable();
        }
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }



    private boolean isNetworkAvailable(){
       if(NetworkUtils.isNetworkAvailable(mainActivity)){
            mConnectionServiceCallback.hasInternetConnection();
           return true;

       }
        mConnectionServiceCallback.hasNoInternetConnection();
        return false;
    }

}