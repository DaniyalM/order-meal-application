package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("sender_id")
    @Expose
    private int senderId;
    @SerializedName("reciever_id")
    @Expose
    private int recieverId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ar_message")
    @Expose
    private String arMessage;
    @SerializedName("action_type")
    @Expose
    private String actionType;
    @SerializedName("action_id")
    @Expose
    private int actionId;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("state")
    @Expose
    private int state;
    @SerializedName("reciever_detail")
    @Expose
    private UserModel reciever_detail;
    @SerializedName("product_detail")
    @Expose
    private ProductModelAPI product_detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArMessage() {
        return arMessage;
    }

    public void setArMessage(String arMessage) {
        this.arMessage = arMessage;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public UserModel getReciever_detail() {
        return reciever_detail;
    }

    public void setReciever_detail(UserModel reciever_detail) {
        this.reciever_detail = reciever_detail;
    }

    public ProductModelAPI getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(ProductModelAPI product_detail) {
        this.product_detail = product_detail;
    }
}
