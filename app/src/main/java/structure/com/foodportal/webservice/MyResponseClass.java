package structure.com.foodportal.webservice;

import com.google.gson.JsonObject;

import org.json.JSONObject;

class MyResponseClass<T> {

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    JsonObject data;
}
