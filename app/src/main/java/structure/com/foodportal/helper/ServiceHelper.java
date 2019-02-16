package structure.com.foodportal.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.activity.RegistrationActivity;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.interfaces.webServiceResponseLisener;
import structure.com.foodportal.webservice.Api_Array_Response;
import structure.com.foodportal.webservice.Api_Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private Activity currentActivity;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, Activity activity) {
        this.serviceResponseLisener = serviceResponseLisener;
        currentActivity = activity;
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

    @SuppressWarnings("ConstantConditions,unchecked")
    public void enqueueCall(Call<Api_Response<T>> call, final String tag) {
        showLoader();

        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            hideLoader();
            UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.no_connection));
            serviceResponseLisener.ResponseFailure(tag);
            return;
        }
        call.enqueue(new Callback<Api_Response<T>>() {
            @Override
            public void onResponse(@NonNull Call<Api_Response<T>> call, @NonNull Response<Api_Response<T>> response) {

                if (response.body() != null) {
                    if (response.body().getCode()== (WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                        serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag);
                        ///    UIHelper.showToast(currentActivity, response.body().getMessage());
                        switch (tag) {
                            case WebServiceConstants.USER_UPDATE:
                            case AppConstant.FORGOT_PASSWORD:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;

                            case AppConstant.DELETE_PRODUCT:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;


                            case AppConstant.RESEND_CODE:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;


                            case AppConstant.UPDATE_PASSWORD:
                                UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.password_reset));
                                break;

                            case AppConstant.USER_CHANGE_PASSWORD:
                                UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.password_changed));
                                break;

                            case AppConstant.ADD_TO_FAV:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;
                            case AppConstant.ADD_TO_CART:

                                UIHelper.showToast(currentActivity, response.body().getMessage());

                                break;
                                case AppConstant.REMOVE_FROM_CART:

                                UIHelper.showToast(currentActivity, response.body().getMessage());

                                break;  case AppConstant.MARK_ORDER:

                                UIHelper.showToast(currentActivity, response.body().getMessage());

                                break;


                        }

                    } else {
                        serviceResponseLisener.ResponseFailure(tag);
                        UIHelper.showToast(currentActivity, response.body().getMessage());
                    }
                } else {
                    serviceResponseLisener.ResponseFailure(tag);
                    UIHelper.showToast(currentActivity, response.body().getMessage());
                }
                hideLoader();
            }

            @Override
            public void onFailure(@NonNull Call<Api_Response<T>> call, @NonNull Throwable t) {
                hideLoader();
                serviceResponseLisener.ResponseFailure(tag);
                t.printStackTrace();
                Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
            }
        });
    }
    public void enqueueArrayCall(Call<Api_Array_Response<T>> call, final String tag) {
        showLoader();

        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            hideLoader();
            UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.no_connection));
            serviceResponseLisener.ResponseFailure(tag);
            return;
        }
        call.enqueue(new Callback<Api_Array_Response<T>>() {
            @Override
            public void onResponse(@NonNull Call<Api_Array_Response<T>> call, @NonNull Response<Api_Array_Response<T>> response) {

                if (response.body() != null) {
                    if (response.body().getCode()== (WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                        serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag);
                        ///    UIHelper.showToast(currentActivity, response.body().getMessage());
                        switch (tag) {
                            case WebServiceConstants.USER_UPDATE:
                            case AppConstant.FORGOT_PASSWORD:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;

                            case AppConstant.DELETE_PRODUCT:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;


                            case AppConstant.RESEND_CODE:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;


                            case AppConstant.UPDATE_PASSWORD:
                                UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.password_reset));
                                break;

                            case AppConstant.USER_CHANGE_PASSWORD:
                                UIHelper.showToast(currentActivity, currentActivity.getResources().getString(R.string.password_changed));
                                break;

                            case AppConstant.ADD_TO_FAV:
                                UIHelper.showToast(currentActivity, response.body().getMessage());
                                break;
                            case AppConstant.ADD_TO_CART:

                                UIHelper.showToast(currentActivity, response.body().getMessage());

                                break;
                                case AppConstant.REMOVE_FROM_CART:

                                UIHelper.showToast(currentActivity, response.body().getMessage());

                                break;  case AppConstant.MARK_ORDER:

                                UIHelper.showToast(currentActivity, response.body().getMessage());

                                break;


                        }

                    } else {
                        serviceResponseLisener.ResponseFailure(tag);
                        UIHelper.showToast(currentActivity, response.body().getMessage());
                    }
                } else {
                    serviceResponseLisener.ResponseFailure(tag);
                    UIHelper.showToast(currentActivity, response.body().getMessage());
                }
                hideLoader();
            }

            @Override
            public void onFailure(@NonNull Call<Api_Array_Response<T>> call, @NonNull Throwable t) {
                hideLoader();
                serviceResponseLisener.ResponseFailure(tag);
                t.printStackTrace();
                Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
            }
        });
    }


}
