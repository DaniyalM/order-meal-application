package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

import structure.com.foodportal.models.Category;

public class HeaderWrapper {

    int id;
    String title_en;
    String slug;
    String description_en;
    String banner_image_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getBanner_image_path() {
        return banner_image_path;
    }

    public void setBanner_image_path(String banner_image_path) {
        this.banner_image_path = banner_image_path;
    }

    public ArrayList<CategorySlider> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategorySlider> categories) {
        this.categories = categories;
    }

    ArrayList<CategorySlider> categories;
}
