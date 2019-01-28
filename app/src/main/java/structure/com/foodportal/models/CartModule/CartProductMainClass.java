package structure.com.foodportal.models.CartModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartProductMainClass {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("cart_product")
    @Expose
    private CartProduct cart_product;

    @SerializedName("rent_type_id")
    @Expose
    private String rent_type_id;

    public String getRent_type_id() {
        return rent_type_id;
    }

    public void setRent_type_id(String rent_type_id) {
        this.rent_type_id = rent_type_id;
    }


    public CartProduct getCart_product() {
        return cart_product;
    }

    public void setCart_product(CartProduct cart_product) {
        this.cart_product = cart_product;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

}