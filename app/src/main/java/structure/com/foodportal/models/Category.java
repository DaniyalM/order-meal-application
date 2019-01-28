package structure.com.foodportal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {
    @SerializedName("categories")
    @Expose
    ArrayList<AllCategory> categories;

    @SerializedName("accessory_types")
    @Expose
    ArrayList<GeneralCategory> accessory_types;

    @SerializedName("payment_types")
    @Expose
    ArrayList<PaymentType> paymentTypes;

    @SerializedName("rent_types")
    @Expose
    ArrayList<ProductModelAPI.ProductRentType> paymentRentTypes;

    @SerializedName("condition_types")
    @Expose
    ArrayList<ConditionType> condition_types;

    @SerializedName("condition")
    @Expose
    ArrayList<GeneralCategory> condition;

    @SerializedName("doors")
    @Expose
    ArrayList<GeneralCategory> doors;

    @SerializedName("color")
    @Expose
    ArrayList<GeneralCategory> colors;

    @SerializedName("fuel_type")
    @Expose
    ArrayList<GeneralCategory> fuel_type;

    @SerializedName("warrenty")
    @Expose
    ArrayList<GeneralCategory> warrenty;

    @SerializedName("cellular_type")
    @Expose
    ArrayList<GeneralCategory> cellular_type;

    @SerializedName("seller_type")
    @Expose
    ArrayList<GeneralCategory> seller_type;

    @SerializedName("type")
    @Expose
    ArrayList<GeneralCategory> type;

    public ArrayList<AllCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<AllCategory> categories) {
        this.categories = categories;
    }

    public ArrayList<GeneralCategory> getAccessory_types() {
        return accessory_types;
    }

    public void setAccessory_types(ArrayList<GeneralCategory> accessory_types) {
        this.accessory_types = accessory_types;
    }

    public ArrayList<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(ArrayList<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public ArrayList<ProductModelAPI.ProductRentType> getPaymentRentTypes() {
        return paymentRentTypes;
    }

    public void setPaymentRentTypes(ArrayList<ProductModelAPI.ProductRentType> paymentRentTypes) {
        this.paymentRentTypes = paymentRentTypes;
    }

    public ArrayList<ConditionType> getCondition_types() {
        return condition_types;
    }

    public void setCondition_types(ArrayList<ConditionType> condition_types) {
        this.condition_types = condition_types;
    }

    public ArrayList<GeneralCategory> getCondition() {
        return condition;
    }

    public void setCondition(ArrayList<GeneralCategory> condition) {
        this.condition = condition;
    }

    public ArrayList<GeneralCategory> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<GeneralCategory> doors) {
        this.doors = doors;
    }

    public ArrayList<GeneralCategory> getColors() {
        return colors;
    }

    public void setColors(ArrayList<GeneralCategory> colors) {
        this.colors = colors;
    }

    public ArrayList<GeneralCategory> getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(ArrayList<GeneralCategory> fuel_type) {
        this.fuel_type = fuel_type;
    }

    public ArrayList<GeneralCategory> getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(ArrayList<GeneralCategory> warrenty) {
        this.warrenty = warrenty;
    }

    public ArrayList<GeneralCategory> getCellular_type() {
        return cellular_type;
    }

    public void setCellular_type(ArrayList<GeneralCategory> cellular_type) {
        this.cellular_type = cellular_type;
    }

    public ArrayList<GeneralCategory> getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(ArrayList<GeneralCategory> seller_type) {
        this.seller_type = seller_type;
    }

    public ArrayList<GeneralCategory> getType() {
        return type;
    }

    public void setType(ArrayList<GeneralCategory> type) {
        this.type = type;
    }
}
