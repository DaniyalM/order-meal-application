package structure.com.foodportal.models.CartModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductUser {

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
private String location;
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
@SerializedName("is_active")
@Expose
private Integer isActive;
@SerializedName("is_verified")
@Expose
private Integer isVerified;
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
private String resetCode;
@SerializedName("city")
@Expose
private String city;
@SerializedName("country")
@Expose
private String country;
@SerializedName("profile_image")
@Expose
private String profileImage;

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
return location;
}

public void setLocation(String location) {
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

public Integer getIsActive() {
return isActive;
}

public void setIsActive(Integer isActive) {
this.isActive = isActive;
}

public Integer getIsVerified() {
return isVerified;
}

public void setIsVerified(Integer isVerified) {
this.isVerified = isVerified;
}

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

public String getResetCode() {
return resetCode;
}

public void setResetCode(String resetCode) {
this.resetCode = resetCode;
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

public String getProfileImage() {
return profileImage;
}

public void setProfileImage(String profileImage) {
this.profileImage = profileImage;
}

}