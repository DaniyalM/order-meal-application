package structure.com.foodportal.models.CartModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartProduct {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_images")
    @Expose
    private ArrayList<ProductImage> product_images;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("product_on")
    @Expose
    private Integer productOn;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency_code")
    @Expose
    private Integer currencyCode;
    @SerializedName("rent_type_id")
    @Expose
    private Integer rentTypeId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("model_number")
    @Expose
    private String modelNumber;
    @SerializedName("built_year")
    @Expose
    private String builtYear;
    @SerializedName("product_condition_id")
    @Expose
    private Integer productConditionId;
    @SerializedName("accessory_type_id")
    @Expose
    private Integer accessoryTypeId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("is_featured")
    @Expose
    private Integer isFeatured;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("guidelines")
    @Expose
    private String guidelines;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("is_favourite")
    @Expose
    private Integer isFavourite;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("rent_type")
    @Expose
    private String rentType;
    @SerializedName("product_condition")
    @Expose
    private String productCondition;
    @SerializedName("accessory_type")
    @Expose
    private String accessoryType;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("is_view")
    @Expose
    private Integer isView;
    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;
    @SerializedName("product_category")
    @Expose
    private ProductCategory productCategory;
    @SerializedName("product_brand")
    @Expose
    private ProductBrand productBrand;
    @SerializedName("product_rent_type")
    @Expose
    private ProductRentType productRentType;
    @SerializedName("product_conditions")
    @Expose
    private ProductConditions productConditions;
    @SerializedName("product_accessory_type")
    @Expose
    private ProductAccessoryType productAccessoryType;
    @SerializedName("product_user")
    @Expose
    private ProductUser productUser;
    @SerializedName("vat")
    @Expose
    private int vat;


    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }


    public ArrayList<ProductImage> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(ArrayList<ProductImage> product_images) {
        this.product_images = product_images;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProductOn() {
        return productOn;
    }

    public void setProductOn(Integer productOn) {
        this.productOn = productOn;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getRentTypeId() {
        return rentTypeId;
    }

    public void setRentTypeId(Integer rentTypeId) {
        this.rentTypeId = rentTypeId;
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

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(String builtYear) {
        this.builtYear = builtYear;
    }

    public Integer getProductConditionId() {
        return productConditionId;
    }

    public void setProductConditionId(Integer productConditionId) {
        this.productConditionId = productConditionId;
    }

    public Integer getAccessoryTypeId() {
        return accessoryTypeId;
    }

    public void setAccessoryTypeId(Integer accessoryTypeId) {
        this.accessoryTypeId = accessoryTypeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Integer isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
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

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Integer isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsView() {
        return isView;
    }

    public void setIsView(Integer isView) {
        this.isView = isView;
    }

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }

    public ProductRentType getProductRentType() {
        return productRentType;
    }

    public void setProductRentType(ProductRentType productRentType) {
        this.productRentType = productRentType;
    }

    public ProductConditions getProductConditions() {
        return productConditions;
    }

    public void setProductConditions(ProductConditions productConditions) {
        this.productConditions = productConditions;
    }

    public ProductAccessoryType getProductAccessoryType() {
        return productAccessoryType;
    }

    public void setProductAccessoryType(ProductAccessoryType productAccessoryType) {
        this.productAccessoryType = productAccessoryType;
    }

    public ProductUser getProductUser() {
        return productUser;
    }

    public void setProductUser(ProductUser productUser) {
        this.productUser = productUser;
    }

}