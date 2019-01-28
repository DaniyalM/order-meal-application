package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSignUpModel

{
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("FacebookAuthToken")
    @Expose
    private String facebookAuthToken;
    @SerializedName("GoogleAuthToken")
    @Expose
    private String googleAuthToken;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getFacebookAuthToken() {
        return facebookAuthToken;
    }

    public void setFacebookAuthToken(String facebookAuthToken) {
        this.facebookAuthToken = facebookAuthToken;
    }

    public String getGoogleAuthToken() {
        return googleAuthToken;
    }

    public void setGoogleAuthToken(String googleAuthToken) {
        this.googleAuthToken = googleAuthToken;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


    public UserSignUpModel (Builder builder){
        latitude = builder.latitude;
        longitude = builder.longitude;
        fullName = builder.fullName;
        countryCode = builder.countryCode;
        phoneNo = builder.phoneNo;
        zipCode = builder.zipCode;
        fullAddress = builder.fullAddress;
        email = builder.email;
        password = builder.password;
        deviceType = builder.deviceType;
        deviceId = builder.deviceId;
        lang = builder.lang;
        facebookAuthToken = builder.facebookAuthToken;
        googleAuthToken = builder.googleAuthToken;
        profilePicture = builder.profilePicture;




    }
    public static class Builder {


        private String fullName;
        private String countryCode;
        private String phoneNo;
        private String zipCode;
        private String fullAddress;
        private String email;
        private String password;
        private String deviceType;
        private String deviceId;
        private String lang;
        private String facebookAuthToken;
        private String googleAuthToken;
        private String profilePicture;
        private double latitude;
        private double longitude;

        public Builder() {
        }

        public UserSignUpModel.Builder withlatitude(double val) {
            latitude = val;
            return this;
        }
        public UserSignUpModel.Builder withlongitude(double val) {
            longitude = val;
            return this;
        }

        public UserSignUpModel.Builder withfullName(String val) {
            fullName = val;
            return this;
        }

        public UserSignUpModel.Builder withcountryCode(String val) {
            countryCode = val;
            return this;
        }

        public UserSignUpModel.Builder withphoneNo(String val) {
            phoneNo = val;
            return this;
        }

        public UserSignUpModel.Builder withzipCode(String val) {
            zipCode = val;
            return this;
        }

        public UserSignUpModel.Builder withfullAddress(String val) {
            fullAddress = val;
            return this;
        }

        public UserSignUpModel.Builder withemail(String val) {
            email = val;
            return this;
        }

        public UserSignUpModel.Builder withpassword(String val) {
            password = val;
            return this;
        }

        public UserSignUpModel.Builder withdeviceType(String val) {
            deviceType = val;
            return this;
        }

        public UserSignUpModel.Builder withdeviceId(String val) {
            deviceId = val;
            return this;
        }

        public UserSignUpModel.Builder withfacebookAuthToken(String val) {
            facebookAuthToken = val;
            return this;
        }

        public UserSignUpModel.Builder withgoogleAuthToken(String val) {
            googleAuthToken = val;
            return this;
        }

        public UserSignUpModel.Builder withprofilePicture(String val) {
            profilePicture = val;
            return this;
        }

        public UserSignUpModel.Builder withlang(String val) {
            lang = val;
            return this;
        }


        public UserSignUpModel build() {
            return new UserSignUpModel(this);
        }
    }
}