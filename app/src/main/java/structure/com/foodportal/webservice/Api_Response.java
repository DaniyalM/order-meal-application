package structure.com.foodportal.webservice;


import com.google.gson.JsonObject;

import org.json.JSONObject;

public class Api_Response<T> {


    private Boolean is_blocked;
    private T Result;
    private String Message;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private int pages;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private int Response;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    private int Code;




    public Boolean getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(Boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResponse() {
        return Response;
    }

    public void setResponse(int response) {
        Response = response;
    }
}
