package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("payment_method_id")
    @Expose
    private int paymentMethodId;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("is_rated")
    @Expose
    private int isRated;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_user")
    @Expose
    private UserModel orderUser;
    @SerializedName("order_product")
    @Expose
    private ProductModelAPI orderProduct;
    @SerializedName("order_payment_method")
    @Expose
    private PaymentType orderPaymentMethod;
    @SerializedName("pickup_date")
    @Expose
    private String pickup_date;
    @SerializedName("rent_type_id")
    @Expose
    private Integer rent_type_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getIsRated() {
        return isRated;
    }

    public void setIsRated(int isRated) {
        this.isRated = isRated;
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

    public UserModel getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(UserModel orderUser) {
        this.orderUser = orderUser;
    }

    public ProductModelAPI getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(ProductModelAPI orderProduct) {
        this.orderProduct = orderProduct;
    }

    public PaymentType getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    public void setOrderPaymentMethod(PaymentType orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public Integer getRent_type_id() {
        return rent_type_id;
    }

    public void setRent_type_id(Integer rent_type_id) {
        this.rent_type_id = rent_type_id;
    }
}
