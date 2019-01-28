package structure.com.foodportal.models.CartModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImage {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("image")
@Expose
private String image;
@SerializedName("product_id")
@Expose
private Integer productId;
@SerializedName("is_main")
@Expose
private Integer isMain;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("product_image")
@Expose
private String productImage;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public Integer getProductId() {
return productId;
}

public void setProductId(Integer productId) {
this.productId = productId;
}

public Integer getIsMain() {
return isMain;
}

public void setIsMain(Integer isMain) {
this.isMain = isMain;
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

public String getProductImage() {
return productImage;
}

public void setProductImage(String productImage) {
this.productImage = productImage;
}

}