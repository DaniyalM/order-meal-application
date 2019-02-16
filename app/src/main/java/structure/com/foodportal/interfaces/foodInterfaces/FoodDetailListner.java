package structure.com.foodportal.interfaces.foodInterfaces;

import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Step;

public interface FoodDetailListner {

    void onStepClick(Step step,int position);
    void onPageChanged(Step step,int position);
}
