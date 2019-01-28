package structure.com.foodportal.webservice;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import structure.com.foodportal.helper.AppConstant;


public class WebServiceFactory {
    private static webservice webservice;
    private static webservice webservicechat;

    public static webservice getInstance(Activity activity) {

        if (webservice == null) {

          //  final BasePreferenceHelper preHelper = new BasePreferenceHelper(activity);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(3, TimeUnit.MINUTES);
            httpClient.readTimeout(3, TimeUnit.MINUTES);


            // add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request request = null;
                    try {
                        Request.Builder requestBuilder = original.newBuilder()
                                .addHeader("Authorization", "Bearer "/* + preHelper.getUserToken()*/);
                        request = requestBuilder.build();
                    } catch (Exception ex) {
//                        Request.Builder requestBuilder = original.newBuilder()
//                                .addHeader("Authorization", ""+preHelper.getUserToken());
//                        request = requestBuilder.build();
                    }

                    return chain.proceed(request);
                }
            });

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            httpClient.addInterceptor(logging);
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();



            webservice = retrofit.create(webservice.class);
        }

        return webservice;
    }

    public static webservice getInstance( String baseUrl) {

        if (webservicechat == null) {

          //  final BasePreferenceHelper preHelper = new BasePreferenceHelper(activity);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(3, TimeUnit.MINUTES);
            httpClient.readTimeout(3, TimeUnit.MINUTES);
            httpClient.addInterceptor(new HttpLoggingInterceptor());

            // add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request request = null;
                    try {
                        Request.Builder requestBuilder = original.newBuilder()
                                .addHeader("Authorization", "Bearer " /*+ preHelper.getUserToken()*/);
                        request = requestBuilder.build();
                    } catch (Exception ex) {
//                        Request.Builder requestBuilder = original.newBuilder()
//                                .addHeader("Authorization", ""+preHelper.getUserToken());
//                        request = requestBuilder.build();
                    }

                    return chain.proceed(request);
                }
            });

            Gson gson = new GsonBuilder()
//                    .setLenient()
                    .create();
            httpClient.addInterceptor(logging);
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(new Gson());
                    .client(httpClient.build())
                    .build();



            webservicechat = retrofit.create(webservice.class);
        }

        return webservicechat;
    }
}
