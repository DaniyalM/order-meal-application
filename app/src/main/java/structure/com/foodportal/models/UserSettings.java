package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSettings {

    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Notification")
    @Expose
    private String notification;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}