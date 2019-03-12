package structure.com.foodportal.interfaces.foodInterfaces;

import org.json.JSONException;
import org.json.JSONObject;

import structure.com.foodportal.models.foodModels.User;

public interface DataListner {

    void getdata(JSONObject jsonObject) throws JSONException;
    void getdataGOOGLE(User user) throws JSONException;
}
