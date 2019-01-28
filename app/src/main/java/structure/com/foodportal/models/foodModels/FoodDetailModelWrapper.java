package structure.com.foodportal.models.foodModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodDetailModelWrapper implements Serializable{

    public FoodDetailModel getData() {
        return data;
    }

    public void setData(FoodDetailModel data) {
        this.data = data;
    }

    @SerializedName("data")
    FoodDetailModel data;



}
