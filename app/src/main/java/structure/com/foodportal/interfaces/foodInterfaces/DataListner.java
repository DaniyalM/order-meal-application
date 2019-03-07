package structure.com.foodportal.interfaces.foodInterfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface DataListner {

    void getdata(JSONObject jsonObject) throws JSONException;
}
