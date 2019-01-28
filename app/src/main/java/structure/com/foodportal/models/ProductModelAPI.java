package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import structure.com.foodportal.fragment.ProductDetailFragment;
import structure.com.foodportal.helper.DateHelper;

public class ProductModelAPI {
    @SerializedName("product_avail_days")
    @Expose
    private AvailableDays availableDays;
    @Expose
    @SerializedName("product_accessory_type")
    private ProductAccessoryType productAccessoryType;
    @Expose
    @SerializedName("product_conditions")
    private ProductConditions productConditions;
    @Expose
    @SerializedName("product_rent_type")
    private List<ProductRentType> productRentType;
    @Expose
    @SerializedName("product_brand")
    private ProductBrand productBrand;
    @Expose
    @SerializedName("product_category")
    private ProductCategory productCategory;
    @Expose
    @SerializedName("product_payment_types")
    private List<ProductPaymentTypes> productPaymentTypes;
    @Expose
    @SerializedName("reviews")
    private List<Reviews> reviews;
    @Expose
    @SerializedName("product_images")
    private List<ProductImages> productImages;
    @Expose
    @SerializedName("accessory_type")
    private String accessoryType;
    @Expose
    @SerializedName("product_condition")
    private String productCondition;
    @Expose
    @SerializedName("rent_type")
    private String rentType;
    @Expose
    @SerializedName("brand")
    private String brand;
    @Expose
    @SerializedName("category")
    private String category;
    @Expose
    @SerializedName("is_favourite")
    private int is_favourite;
    @Expose
    @SerializedName("rating")
    private int rating;
    @Expose
    @SerializedName("closing_time")
    private String closingTime;
    @Expose
    @SerializedName("opening_time")
    private String openingTime;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("guidelines")
    private String guidelines;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("is_active")
    private int isActive;
    @Expose
    @SerializedName("is_featured")
    private int isFeatured;
    @Expose
    @SerializedName("view_count")
    private int viewCount;
    @Expose
    @SerializedName("is_verified")
    private int isVerified;
    @Expose
    @SerializedName("user_id")
    private int userId;
    @Expose
    @SerializedName("accessory_type_id")
    private int accessoryTypeId;
    @Expose
    @SerializedName("product_condition_id")
    private int productConditionId;
    @Expose
    @SerializedName("built_year")
    private String builtYear;
    @Expose
    @SerializedName("model_number")
    private String modelNumber;
    @Expose
    @SerializedName("location")
    private String location;
    @Expose
    @SerializedName("longitude")
    private String longitude;
    @Expose
    @SerializedName("latitude")
    private String latitude;
    @Expose
    @SerializedName("rent_type_id")
    private int rentTypeId;
    @Expose
    @SerializedName("currency_code")
    private int currencyCode;
    @Expose
    @SerializedName("amount")
    private String amount;
    @Expose
    @SerializedName("brand_id")
    private int brandId;
    @Expose
    @SerializedName("category_id")
    private int categoryId;
    @Expose
    @SerializedName("product_on")
    private int productOn;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id")
    private int id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("product_user")
    @Expose
    private UserModel product_user;
    @SerializedName("is_view")
    @Expose
    private int is_view;
    @SerializedName("kilometers")
    @Expose
    private String kilometers;

    @SerializedName("bedrooms")
    @Expose
    private int bedrooms;
    @SerializedName("bathrooms")
    @Expose
    private int bathrooms;
    @SerializedName("fuel_type")
    @Expose
    private String fuel_type;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("warrenty")
    @Expose
    private String warrenty;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("smart_features")
    @Expose
    private int smart_features;
    @SerializedName("screen_size")
    @Expose
    private String screen_size;
    @SerializedName("mega_pixels")
    @Expose
    private String mega_pixels;
    @SerializedName("balconies")
    @Expose
    private int balconies;
    @SerializedName("seller_type")
    @Expose
    private String seller_type;
    @SerializedName("doors")
    @Expose
    private String doors;
    @SerializedName("expandable_memory")
    @Expose
    private int expandable_memory;
    @SerializedName("operating_system")
    @Expose
    private String operating_system;
    @SerializedName("processor")
    @Expose
    private String processor;
    @SerializedName("cellular_type")
    @Expose
    private String cellular_type;
    @SerializedName("pets_allowed")
    @Expose
    private int pets_allowed;
    @SerializedName("total_cheques")
    @Expose
    private int total_cheques;
    @SerializedName("total_area")
    @Expose
    private String total_area;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("make_model")
    @Expose
    private String make_model;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country_id")
    @Expose
    private String country_id;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @SerializedName("type_id")
    @Expose
    private String type_id;


    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    @SerializedName("city_id")
    @Expose
    private String city_id;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public int getMake_model_id() {
        return make_model_id;
    }

    public void setMake_model_id(int make_model_id) {
        this.make_model_id = make_model_id;
    }

    @SerializedName("make_model_id")
    @Expose
    private int make_model_id;

    public String getMake_model() {
        return make_model;
    }

    public void setMake_model(String make_model) {
        this.make_model = make_model;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    public int getPets_allowed() {
        return pets_allowed;
    }

    public void setPets_allowed(int pets_allowed) {
        this.pets_allowed = pets_allowed;
    }

    public int getTotal_cheques() {
        return total_cheques;
    }

    public void setTotal_cheques(int total_cheques) {
        this.total_cheques = total_cheques;
    }

    public String getTotal_area() {
        return total_area;
    }

    public void setTotal_area(String total_area) {
        this.total_area = total_area;
    }



    public int getExpandable_memory() {
        return expandable_memory;
    }

    public void setExpandable_memory(int expandable_memory) {
        this.expandable_memory = expandable_memory;
    }

    public String getOperating_system() {
        return operating_system;
    }

    public void setOperating_system(String operating_system) {
        this.operating_system = operating_system;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getProduct_cellular_type() {
        return cellular_type;
    }

    public void setProduct_cellular_type(String cellular_type) {
        this.cellular_type = cellular_type;
    }


    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }


    public int getBalconies() {
        return balconies;
    }

    public void setBalconies(int balconies) {
        this.balconies = balconies;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }


    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }


    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSmart_features() {
        return smart_features;
    }

    public void setSmart_features(int smart_features) {
        this.smart_features = smart_features;
    }

    public String getScreen_size() {
        return screen_size;
    }

    public void setScreen_size(String screen_size) {
        this.screen_size = screen_size;
    }

    public String getMega_pixels() {
        return mega_pixels;
    }

    public void setMega_pixels(String mega_pixels) {
        this.mega_pixels = mega_pixels;
    }


    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    @SerializedName("sub_category_id")
    @Expose
    private int sub_category_id;


    public UserModel getProduct_user() {
        return product_user;
    }

    public void setProduct_user(UserModel product_user) {
        this.product_user = product_user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public ProductAccessoryType getProductAccessoryType() {
        return productAccessoryType;
    }

    public ProductConditions getProductConditions() {
        return productConditions;
    }

    public List<ProductRentType> getProductRentType() {
        return productRentType;
    }

    public int getIs_favourite() {
        return is_favourite;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public List<ProductPaymentTypes> getProductPaymentTypes() {
        return productPaymentTypes;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public String getRentType() {
        return rentType;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public int getIsFavourite() {
        return is_favourite;
    }

    public void setIs_favourite(int is_favourite) {
        this.is_favourite = is_favourite;
    }

    public int getRating() {
        return rating;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getMomentsAgo() {
        return DateHelper.getTimeAgo(DateHelper.getLocalTimeDate(updatedAt).getTime());
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public String getDescription() {
        return description;
    }

    public int getIsActive() {
        return isActive;
    }

    public boolean getIsFeatured() {
        return isFeatured == 1;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public int getUserId() {
        return userId;
    }

    public int getAccessoryTypeId() {
        return accessoryTypeId;
    }

    public int getProductConditionId() {
        return productConditionId;
    }

    public String getBuiltYear() {
        return builtYear;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getRentTypeId() {
        return rentTypeId;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public int getBrandId() {
        return brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getProductOn() {
        return productOn;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public AvailableDays getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(AvailableDays availableDays) {
        this.availableDays = availableDays;
    }

    public int getIs_view() {
        return is_view;
    }

    public void setIs_view(int is_view) {
        this.is_view = is_view;
    }

    public static class ProductAccessoryType {
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getIsActive() {
            return isActive;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    public static class ProductConditions {
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getIsActive() {
            return isActive;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    public static class ProductRentType {
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;
        @SerializedName("rent_type")
        @Expose
        private ProductDetailFragment.Renttype renttype;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getIsActive() {
            return isActive;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public ProductDetailFragment.Renttype getRenttype() {
            return renttype;
        }

        public void setRenttype(ProductDetailFragment.Renttype renttype) {
            this.renttype = renttype;
        }
    }

    public static class ProductBrand {
        @Expose
        @SerializedName("category_image")
        private String categoryImage;
        @Expose
        @SerializedName("detail_image")
        private String detailImage;
        @Expose
        @SerializedName("deleted_at")
        private String deletedAt;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("parent_id")
        private int parentId;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("title")
        private String title;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public ProductBrand getBrand_category() {
            return brand_category;
        }

        public void setBrand_category(ProductBrand brand_category) {
            this.brand_category = brand_category;
        }

        @Expose
        @SerializedName("brand_category")
        private ProductBrand brand_category;

        public String getCategoryImage() {
            return categoryImage;
        }

        public String getDetailImage() {
            return detailImage;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getParentId() {
            return parentId;
        }

        public int getIsActive() {
            return isActive;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }
    }

    public static class ProductCategory {
        @Expose
        @SerializedName("category_image")
        private String categoryImage;
        @Expose
        @SerializedName("detail_image")
        private String detailImage;
        @Expose
        @SerializedName("deleted_at")
        private String deletedAt;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("parent_id")
        private int parentId;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("id")
        private int id;

        public String getCategoryImage() {
            return categoryImage;
        }

        public String getDetailImage() {
            return detailImage;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getParentId() {
            return parentId;
        }

        public int getIsActive() {
            return isActive;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }
    }

    public static class ProductPaymentTypes {
        @Expose
        @SerializedName("product_payment_type")
        private ProductPaymentType productPaymentType;
        @Expose
        @SerializedName("payment_type")
        private String paymentType;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("payment_type_id")
        private int paymentTypeId;
        @Expose
        @SerializedName("product_id")
        private int productId;
        @Expose
        @SerializedName("id")
        private int id;

        public ProductPaymentType getProductPaymentType() {
            return productPaymentType;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getPaymentTypeId() {
            return paymentTypeId;
        }

        public int getProductId() {
            return productId;
        }

        public int getId() {
            return id;
        }
    }

    public static class ProductPaymentType {
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getIsActive() {
            return isActive;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    public static class Reviews {
        @Expose
        @SerializedName("user_detail")
        private UserDetail userDetail;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("feedback")
        private String feedback;
        @Expose
        @SerializedName("rate")
        private int rate;
        @Expose
        @SerializedName("product_id")
        private int productId;
        @Expose
        @SerializedName("user_id")
        private int userId;
        @Expose
        @SerializedName("id")
        private int id;

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getFeedback() {
            return feedback;
        }

        public int getRate() {
            return rate;
        }

        public int getProductId() {
            return productId;
        }

        public int getUserId() {
            return userId;
        }

        public int getId() {
            return id;
        }
    }

    public static class UserDetail {
        @Expose
        @SerializedName("profile_image")
        private String profileImage;
        @Expose
        @SerializedName("reset_code")
        private String resetCode;
        @Expose
        @SerializedName("deleted_at")
        private String deletedAt;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("is_verified")
        private int isVerified;
        @Expose
        @SerializedName("is_active")
        private int isActive;
        @Expose
        @SerializedName("status")
        private int status;
        @Expose
        @SerializedName("profile_picture")
        private String profilePicture;
        @Expose
        @SerializedName("registration_type")
        private String registrationType;
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("full_address")
        private String fullAddress;
        @Expose
        @SerializedName("location")
        private String location;
        @Expose
        @SerializedName("longitude")
        private String longitude;
        @Expose
        @SerializedName("latitude")
        private String latitude;
        @Expose
        @SerializedName("zip_code")
        private String zipCode;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("role_id")
        private int roleId;
        @Expose
        @SerializedName("phone_no")
        private String phoneNo;
        @Expose
        @SerializedName("country_code")
        private String countryCode;
        @Expose
        @SerializedName("last_name")
        private String lastName;
        @Expose
        @SerializedName("first_name")
        private String firstName;
        @Expose
        @SerializedName("full_name")
        private String fullName;
        @Expose
        @SerializedName("id")
        private int id;

        public String getProfileImage() {
            return profileImage;
        }

        public String getResetCode() {
            return resetCode;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getIsVerified() {
            return isVerified;
        }

        public int getIsActive() {
            return isActive;
        }

        public int getStatus() {
            return status;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public String getRegistrationType() {
            return registrationType;
        }

        public String getPassword() {
            return password;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public String getLocation() {
            return location;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getZipCode() {
            return zipCode;
        }

        public String getEmail() {
            return email;
        }

        public int getRoleId() {
            return roleId;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getFullName() {
            return fullName;
        }

        public int getId() {
            return id;
        }
    }

    public static class ProductImages {
        @Expose
        @SerializedName("product_image")
        private String productImage;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("is_main")
        private int isMain;
        @Expose
        @SerializedName("product_id")
        private int productId;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("id")
        private int id;

        public String getProductImage() {
            return productImage;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getIsMain() {
            return isMain;
        }

        public int getProductId() {
            return productId;
        }

        public String getImage() {
            return image;
        }

        public int getId() {
            return id;
        }
    }

}
