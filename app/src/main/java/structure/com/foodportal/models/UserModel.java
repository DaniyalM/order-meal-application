package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("registration_type")
    @Expose
    private String registrationType;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("about")
    @Expose
    private String about;
    public String getAbout() { return about; }

    public void setAbout(String about) { this.about = about; }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    public Integer getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Integer is_verified) {
        this.is_verified = is_verified;
    }

    @SerializedName("is_active")
    @Expose
    private Integer is_active;

    @SerializedName("is_verified")
    @Expose
    private Integer is_verified;


    //    @SerializedName("is_active")
//    @Expose
//    private int isActive;
//    @SerializedName("is_verified")
//    @Expose
//    private boolean isVerified;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("reset_code")
    @Expose
    private Object resetCode;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Notification")
    @Expose
    private String notification;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("userSettings")
    @Expose
    private UserSettings userSettings;
    @SerializedName("verification")
    @Expose
    private Verification verification;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("rating")
    @Expose
    private int rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return (String) location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    public int getIsActive() {
//        return isActive=0;
//    }
//
//    public void setIsActive(int isActive) {
//        this.isActive = isActive;
//    }

//    public boolean getIsVerified() {
//        return isVerified;
//    }
//
//    public void setIsVerified(boolean isVerified) {
//        this.isVerified = isVerified;
//    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Object getResetCode() {
        return resetCode;
    }

    public void setResetCode(Object resetCode) {
        this.resetCode = resetCode;
    }

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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}