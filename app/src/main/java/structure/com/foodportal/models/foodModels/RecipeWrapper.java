package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class RecipeWrapper {

    String section_name_ur;
    String section_name_en;

    public String getSection_name_ur() {
        return section_name_ur;
    }

    public void setSection_name_ur(String section_name_ur) {
        this.section_name_ur = section_name_ur;
    }

    public String getSection_name_en() {
        return section_name_en;
    }

    public void setSection_name_en(String section_name_en) {
        this.section_name_en = section_name_en;
    }

    public CategorySlider getSection_detail() {
        return section_detail;
    }

    public void setSection_detail(CategorySlider section_detail) {
        this.section_detail = section_detail;
    }

    public ArrayList<CategorySlider> getSection_list() {
        return section_list;
    }

    public void setSection_list(ArrayList<CategorySlider> section_list) {
        this.section_list = section_list;
    }

    CategorySlider section_detail;
    ArrayList<CategorySlider> section_list;
}
