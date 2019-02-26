package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class Section {



    String section_name_ur;

    public String getSection_name_en() {
        return section_name_en;
    }

    public void setSection_name_en(String section_name_en) {
        this.section_name_en = section_name_en;
    }

    String section_name_en;

    public String getSection_name_ur() {
        return section_name_ur;
    }

    public void setSection_name_ur(String section_name_ur) {
        this.section_name_ur = section_name_ur;
    }

    public ArrayList<Sections> getSection_list() {
        return section_list;
    }

    public void setSection_list(ArrayList<Sections> section_list) {
        this.section_list = section_list;
    }

    ArrayList<Sections> section_list;
}
