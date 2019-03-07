package structure.com.foodportal.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;

import structure.com.foodportal.models.UserModel;
import structure.com.foodportal.models.foodModels.User;


public class BasePreferenceHelper extends PreferenceHelper {



    public static final String USER_TYPE= "type";
    public static final String USER_VENDER = "vendor";
    public static final String USER_BUYER = "user";

    public static final String USER_UNREGISTER = "unregister";
    public static final String KEY_MEMO= "memoList";
    public static final String KEY_CHAT= "chatList";
    public static final String KEY_POST= "postList";
    public static final String image_path = "image_path";
    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_USER = "user";
    public static final String KEY_DEVICE_TOKEN = "device_token";
    public static final String AUTHENTICATE_USER_TOKEN = "user_token";
    public static final String is_verified = "is_verified";
    public static final String LANGUAGE = "language";

    private Context context;
    private static final String FILENAME = "preferences";

    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

/*
    public void putPostList(Model value) {

        putStringPreference(context, FILENAME, KEY_POST,
                new Gson().toJson(value, new TypeToken<Model>() {
                }.getType()));
    }

    public Model getPostList() {
        Model list = new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_POST),
                new TypeToken<Model>() {
                }.getType());

        return list == null ? new Model() : list;
    }
    public void putMemoList(ArrayList<MemoItem> value) {

        putStringPreference(context, FILENAME, KEY_MEMO,
                new Gson().toJson(value, new TypeToken<ArrayList<MemoItem>>() {
                }.getType()));
    }

    public ArrayList<MemoItem> getMemoList() {
        ArrayList<MemoItem> list = new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_MEMO),
                new TypeToken<ArrayList<MemoItem>>() {
                }.getType());

        return list == null ? new ArrayList<MemoItem>() : list;
    }


    public void putChatList(ArrayList<ChatItem> value) {

        putStringPreference(context, FILENAME, KEY_CHAT,
                new Gson().toJson(value, new TypeToken<ArrayList<MemoItem>>() {
                }.getType()));
    }

    public ArrayList<ChatItem> getChatList() {
        ArrayList<ChatItem> list = new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_CHAT),
                new TypeToken<ArrayList<ChatItem>>() {
                }.getType());

        return list == null ? new ArrayList<ChatItem>() : list;
    }
*/
    public void setImage_path(String path) {
        putStringPreference(context, FILENAME, image_path, path);
    }

    public String getImage_path() {
        return getStringPreference(context, FILENAME, image_path);
    }
 public void setIs_verified(String path) {
        putStringPreference(context, FILENAME, image_path, path);
    }

    public String getIs_verified() {
        return getStringPreference(context, FILENAME, is_verified);
    }


    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }


    public void setStringPrefrence(String key, String value) {
        putStringPreference(context, FILENAME, key, value);
    }

    public String getStringPrefrence(String key) {
        return getStringPreference(context, FILENAME, key);
    }


    public void setIntegerPrefrence(String key, int value) {
        putIntegerPreference(context, FILENAME, key, value);
    }

    public int getIntegerPrefrence(String key) {
        return getIntegerPreference(context, FILENAME, key);
    }


    public void setBooleanPrefrence(String Key, boolean status) {
        putBooleanPreference(context, FILENAME, Key, status);
    }

    public boolean getBooleanPrefrence(String Key) {
        return getBooleanPreference(context, FILENAME, Key);
    }


    public boolean getLoginStatus() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public void putDeviceToken(String token) {
        putStringPreference(context, FILENAME, KEY_DEVICE_TOKEN, token);
    }


    public String getDeviceToken() {
        return getStringPreference(context, FILENAME, KEY_DEVICE_TOKEN);
    }


    public void putUserToken(String token) {
        putStringPreference(context, FILENAME, AUTHENTICATE_USER_TOKEN, token);
    }


    public String getUserToken() {
        return getStringPreference(context, FILENAME, AUTHENTICATE_USER_TOKEN);
    }

    public void putUser(UserModel user) {
        putStringPreference(context,
                FILENAME,
                KEY_USER,
                new GsonBuilder()
                        .create()
                        .toJson(user));
    }

    public UserModel getUser() {
        return new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), UserModel.class);
    }

    public void putUserFood(User user) {
        putStringPreference(context,
                FILENAME,
                KEY_USER,
                new GsonBuilder()
                        .create()
                        .toJson(user));
    }

    public User getUserFood() {
        return new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), User.class);
    }

    public void removeLoginPreference() {
        setLoginStatus(false);
        removePreference(context, FILENAME, KEY_USER);
        removePreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public static void setDeviceToken(Context context, String token) {
        BasePreferenceHelper preferenceHelper = new BasePreferenceHelper(context);
        preferenceHelper.putDeviceToken(token);
    }
}
