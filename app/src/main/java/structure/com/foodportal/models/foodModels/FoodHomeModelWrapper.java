package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class FoodHomeModelWrapper {

    ArrayList<Banner> banner;

    public ArrayList<Banner> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<Banner> banner) {
        this.banner = banner;
    }

    public ArrayList<CategorySlider> getCategory_slider() {
        return category_slider;
    }

    public void setCategory_slider(ArrayList<CategorySlider> category_slider) {
        this.category_slider = category_slider;
    }

    public ArrayList<Section> getSection() {
        return section;
    }

    public void setSection(ArrayList<Section> section) {
        this.section = section;
    }

    ArrayList<CategorySlider> category_slider;

    ArrayList<Section> section;


}
