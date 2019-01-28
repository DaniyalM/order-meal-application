package structure.com.foodportal.models;

import java.util.ArrayList;

public class AddProductModel {
    private int id;
    private int product_id;
    private String prod_avail_days;
    private String closing_time;
    private String opening_time;
    private String guidelines;
    private String description;
    private String accessory_type_id;
    private String product_condition_id;
    private int built_year;
    private int user_id;

    public int getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(int subCategories) {
        this.subCategories = subCategories;
    }

    public int getModels() {
        return models;
    }

    public void setModels(int models) {
        this.models = models;
    }

    private int subCategories;
    private int models;
    private String currency_code;
    private String payment_type_id;
    private String amount;
    private ArrayList<ProductModelAPI.ProductRentType> rentTypes;

    public ArrayList<ProductModelAPI.ProductRentType> getRentTypes() {
        return rentTypes;
    }

    public void setRentTypes(ArrayList<ProductModelAPI.ProductRentType> rentTypes) {
        this.rentTypes = rentTypes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    ArrayList<PaymentType> paymentTypes = new ArrayList<>();
    public static ArrayList<String> selectedDays;

    public String getProd_avail_days() {
        return prod_avail_days;
    }

    public void setProd_avail_days(String prod_avail_days) {
        this.prod_avail_days = prod_avail_days;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessory_type_id() {
        return accessory_type_id;
    }

    public void setAccessory_type_id(String accessory_type_id) {
        this.accessory_type_id = accessory_type_id;
    }

    public String getProduct_condition_id() {
        return product_condition_id;
    }

    public void setProduct_condition_id(String product_condition_id) {
        this.product_condition_id = product_condition_id;
    }

    public int getBuilt_year() {
        return built_year;
    }

    public void setBuilt_year(int built_year) {
        this.built_year = built_year;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(String payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }

    public int getRent_type_id() {
        return rent_type_id;
    }

    public void setRent_type_id(int rent_type_id) {
        this.rent_type_id = rent_type_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getProduct_on() {
        return product_on;
    }

    public void setProduct_on(int product_on) {
        this.product_on = product_on;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagesList(ArrayList<Itemlistobject> imagesList) {
        this.imagesList = imagesList;
    }

    public ArrayList<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(ArrayList<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public static ArrayList<String> getSelectedDays() {
        return selectedDays;
    }

    public static void setSelectedDays(ArrayList<String> selectedDays) {
        AddProductModel.selectedDays = selectedDays;
    }

    private String model_number;
    private int rent_type_id;
    private String location;
    private String longitude;
    private String latitude;
    private int brand_id;
    private int category_id;
    private int product_on;
    private String title;
    private ArrayList<Itemlistobject> imagesList;



    public ArrayList<Itemlistobject> getImagesList() {
        return imagesList;
    }

    private AddProductModel(Builder builder) {
        prod_avail_days = builder.prodAvailDays;
        closing_time = builder.closingTime;
        opening_time = builder.openingTime;
        guidelines = builder.guidelines;
        description = builder.description;
        accessory_type_id = builder.accessoryTypeId;
        product_condition_id = builder.productConditionId;
        built_year = builder.builtYear;
        user_id = builder.userId;
        currency_code = builder.currencyCode;
        payment_type_id = builder.paymentTypeId;
        amount = builder.amount;
        model_number = builder.modelNumber;
        rent_type_id = builder.rentTypeId;
        location = builder.location;
        longitude = builder.longitude;
        latitude = builder.latitude;
        brand_id = builder.brandId;
        category_id = builder.categoryId;
        product_on = builder.productOn;
        title = builder.title;
        imagesList = builder.images;
        models = builder.models;
        subCategories = builder.subCategories;
    }

    public static class Builder {
        private String prodAvailDays;
        private String closingTime;
        private String openingTime;
        private String guidelines;
        private String description;
        private String accessoryTypeId;
        private String productConditionId;
        private int builtYear;
        private int userId;
        private int models;
        private int subCategories;
        private String currencyCode;
        private String paymentTypeId;
        private String amount;
        private String modelNumber;
        private int rentTypeId;
        private String location;
        private String longitude;
        private String latitude;
        private int brandId;
        private int categoryId;
        private int productOn;
        private String title;
        private ArrayList<Itemlistobject> images;
        ArrayList<PaymentType> paymentTypes = new ArrayList<>();
        public static ArrayList<String> selectedDays;


        public Builder() {
        }

        public Builder withSubCatgories(int val) {
            subCategories = val;
            return this;
        }
        public Builder withModels(int val) {
            models = val;
            return this;
        }
        public Builder withProdAvailDays(String val) {
            prodAvailDays = val;
            return this;
        }

        public Builder withClosingTime(String val) {
            closingTime = val;
            return this;
        }

        public Builder withOpeningTime(String val) {
            openingTime = val;
            return this;
        }

        public Builder withImages(ArrayList<Itemlistobject> imagesList) {
            this.images = imagesList;
            return this;
        }

        public Builder withGuidelines(String val) {
            guidelines = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withAccessoryTypeId(String val) {
            accessoryTypeId = val;
            return this;
        }

        public Builder withProductConditionId(String val) {
            productConditionId = val;
            return this;
        }

        public Builder withBuiltYear(int val) {
            builtYear = val;
            return this;
        }

        public Builder withUserId(int val) {
            userId = val;
            return this;
        }

        public Builder withCurrencyCode(String val) {
            currencyCode = val;
            return this;
        }

        public Builder withPaymentTypeId(String val) {
            paymentTypeId = val;
            return this;
        }

        public Builder withAmount(String val) {
            amount = val;
            return this;
        }

        public Builder withModelNumber(String val) {
            modelNumber = val;
            return this;
        }

        public Builder withRentTypeId(int val) {
            rentTypeId = val;
            return this;
        }

        public Builder withLocation(String val) {
            location = val;
            return this;
        }

        public Builder withLongitude(String val) {
            longitude = val;
            return this;
        }

        public Builder withLatitude(String val) {
            latitude = val;
            return this;
        }

        public Builder withBrandId(int val) {
            brandId = val;
            return this;
        }

        public Builder withCategoryId(int val) {
            categoryId = val;
            return this;
        }

        public Builder withProductOn(int val) {
            productOn = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withPaymentTypes(ArrayList<PaymentType> paymentType) {
            paymentTypes = paymentType;
            return this;
        }

        public Builder withSelectedDays(ArrayList<String> selectedDay) {
            this.selectedDays = selectedDay;
            return this;
        }

        public AddProductModel build() {
            return new AddProductModel(this);
        }
    }
}
