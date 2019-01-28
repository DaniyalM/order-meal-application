package structure.com.foodportal.interfaces;

import java.util.ArrayList;

import structure.com.foodportal.models.AllCategory;

public interface CategoryInterface {

    void onCategoryClicked(ArrayList<AllCategory> category, int position);
}
