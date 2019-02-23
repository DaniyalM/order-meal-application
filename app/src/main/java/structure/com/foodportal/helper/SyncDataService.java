package structure.com.foodportal.helper;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

public class SyncDataService extends IntentService
    {
        public SyncDataService()
        {
            super("SyncDataService");
        }
        @Override
        protected void onHandleIntent(Intent intent)
        {
            //first check if internet is availabe or not.
//            if(DeviceManager.isInternetAvailableOnDevice())
//            {
////            try
////            {
////              //write the code to sync the data to Server
//                //post the event after sync complete
//                EventBus.getDefault().post(new DataSyncEvent("Data Sync SuccessFully"));
////            }
////            catch (RTITBException exception)
////            {
////                LogManager.getInstance().addLog(exception.getExceptionCode(),exception.getMessage(),exception);
////            }
//            }
        }
    }