package structure.com.foodportal.helper;

import java.util.ArrayList;

import structure.com.foodportal.models.AddProductModel;
import structure.com.foodportal.models.PaymentType;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.models.WeekDay;

public class ProductCreation {
    private static ProductCreation Instance;
    private AddProductModel product;
    private ProductModelAPI currentProduct;
    private ArrayList<String> deletedImagesIds;
    private ArrayList<PaymentType> paymentTypes;
    private ArrayList<ProductModelAPI.ProductRentType> rentTypes;
    private ArrayList<WeekDay> days;
    private boolean editing;
    private int productOn;

    private ProductCreation(){}

    public static ProductCreation getInstance(){
        if(Instance == null){
            Instance = new ProductCreation();
        }
        return Instance;
    }

    public void reset(){
        this.product = null;
        this.currentProduct = null;
        this.deletedImagesIds = null;
        this.paymentTypes = null;
        this.rentTypes = null;
        this.editing = false;
        this.days = null;
        this.productOn = 0;
    }

    public AddProductModel getProduct() {
        return product;
    }

    public void setProduct(AddProductModel product) {
        this.product = product;
    }

    public ProductModelAPI getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(ProductModelAPI currentProduct) {
        this.currentProduct = currentProduct;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public ArrayList<String> getDeletedImagesIds() {
        return deletedImagesIds;
    }

    public void setDeletedImagesIds(ArrayList<String> deletedImagesIds) {
        this.deletedImagesIds = deletedImagesIds;
    }

    public ArrayList<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(ArrayList<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public int getProductOn() {
        return productOn;
    }

    public void setProductOn(int productOn) {
        this.productOn = productOn;
    }

    public ArrayList<WeekDay> getDays() {
        return days;
    }

    public void setDays(ArrayList<WeekDay> days) {
        this.days = days;
    }

    public ArrayList<ProductModelAPI.ProductRentType> getRentTypes() {
        return rentTypes;
    }

    public void setRentTypes(ArrayList<ProductModelAPI.ProductRentType> rentTypes) {
        this.rentTypes = rentTypes;
    }
}
