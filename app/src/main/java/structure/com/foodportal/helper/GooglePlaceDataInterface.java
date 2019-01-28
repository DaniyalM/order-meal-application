package structure.com.foodportal.helper;


import java.io.IOException;

/**
 * Created by ahsanali on 6/17/2017.
 */

public interface GooglePlaceDataInterface {
    void onPlaceActivityResult(double longitude, double latitude, String address, String country, String city) throws IOException;
    void onError(String error);
}
