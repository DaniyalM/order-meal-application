package structure.com.foodportal.singleton;

import structure.com.foodportal.models.foodModels.FoodDetailModel;

public class CarelessSingleton{
    public static final CarelessSingleton instance = new CarelessSingleton();
    private FoodDetailModel someState;
    private int positon;

    private CarelessSingleton() {}
    public void setState(FoodDetailModel state,int positon) {
        this.someState = state;
        this.positon = positon;
    }
    public FoodDetailModel getState() {
        return someState;

    }
    public int getStateposition() {
        return positon;

    }
}
