package structure.com.foodportal.activity;

import structure.com.foodportal.models.UserSignUpModel;

public class UserSignUp {
    private static final UserSignUp ourInstance = new UserSignUp();

    public static UserSignUp getInstance() {
        return ourInstance;
    }

    private UserSignUp() {
    }

    public UserSignUpModel.Builder userSignUpModel =new UserSignUpModel.Builder();


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int user_id ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String code ="1111" ;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;







}
