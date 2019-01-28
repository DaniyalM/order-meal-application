package structure.com.foodportal.webservice;

import android.app.Activity;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.activity.RegistrationActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.UIHelper;


public class WebApiRequest {

    private static webservice apiService;
    private static Activity currentActivity;
    private static WebApiRequest ourInstance = new WebApiRequest();

    private WebApiRequest() {
    }

    public static WebApiRequest getInstance(Activity activity) {
        apiService = WebServiceFactory.getInstance(activity);
        currentActivity = activity;

        return ourInstance;
    }

    public static WebApiRequest getInstance(Activity activity, String baseUrl) {
        apiService = WebServiceFactory.getInstance( baseUrl);
        currentActivity = activity;

        return ourInstance;
    }

    public void genricArrayRequest(HashMap paramMap, String key, final APIRequestDataCallBack apiRequestDataCallBack) {
        showLoader();
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            hideLoader();
            UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.no_connection));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Array_Response<JsonObject>> call = null;
        switch (key) {
//            case AppConstant.ServerAPICalls.CUISINES:
//                call = apiService.cusines(paramMap);
//                break;
//            case AppConstant.ServerAPICalls.RESTAURANTS:
//                call = apiService.restuanrant();
//                break;
            case AppConstant.SUB_CATEGORYS:
                call = apiService.getSubCategory(Integer.valueOf(paramMap.get("category_id").toString()));
                break;


        }

        call.enqueue(new Callback<Api_Array_Response<JsonObject>>() {

            @Override
            public void onResponse(Call<Api_Array_Response<JsonObject>> pCall, Response<Api_Array_Response<JsonObject>> response) {

                hideLoader();

                if (response != null && response.body() != null) {
                    if (response.body().getResponse() == 2000) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        UIHelper.showToast(currentActivity, "Server Error");
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Array_Response<JsonObject>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                hideLoader();
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void genricObjectRequest(HashMap paramMap, String key, final APIRequestObjectCallBack apiRequestDataCallBack) {
        showLoader();
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            hideLoader();
            UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.no_connection));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response<JsonObject>> call = null;
        switch (key) {



            case AppConstant.ALL_CATEGORYS:
                call = apiService.getAllCategory();
                break;



        }

        call.enqueue(new Callback<Api_Response<JsonObject>>() {

            @Override
            public void onResponse(Call<Api_Response<JsonObject>> pCall, Response<Api_Response<JsonObject>> response) {

                hideLoader();

                if (response != null && response.body() != null) {
                    if (response.body().getResponse() == 2000) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        UIHelper.showToast(currentActivity, "Server Error");
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<JsonObject>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                hideLoader();
                apiRequestDataCallBack.onError();
            }
        });

    }

    private void showLoader() {
        if (currentActivity instanceof MainActivity)
            ((MainActivity) currentActivity).showLoader();
        else
            ((RegistrationActivity) currentActivity).showLoader();
    }

    private void hideLoader() {
        if (currentActivity instanceof MainActivity)
            ((MainActivity) currentActivity).hideLoader();
        else
            ((RegistrationActivity) currentActivity).hideLoader();
    }

    public interface APIRequestDataCallBack {
        void onSuccess(Api_Array_Response response);

        void onError();

        void onNoNetwork();
    }

    public interface APIRequestObjectCallBack {
        void onSuccess(Api_Response response);

        void onError();

        void onNoNetwork();
    }

}