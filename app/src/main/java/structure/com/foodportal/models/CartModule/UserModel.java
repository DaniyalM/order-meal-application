package structure.com.foodportal.models.CartModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

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
private Object deviceId;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("cart_product")
@Expose
private CartProduct cartProduct;

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

public Object getDeviceId() {
return deviceId;
}

public void setDeviceId(Object deviceId) {
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

public CartProduct getCartProduct() {
return cartProduct;
}

public void setCartProduct(CartProduct cartProduct) {
this.cartProduct = cartProduct;
}

}