package structure.com.foodportal.webservice;


import java.util.ArrayList;

public class Api_Array_Response<T> {


    private Boolean success;
    private Boolean is_blocked;
    private ArrayList<T> Result;
    private String Message;
    private int Response;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    private int Code;




    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(Boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public ArrayList<T> getResult() {
        return Result;
    }

    public void setResult(ArrayList<T> result) {
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
