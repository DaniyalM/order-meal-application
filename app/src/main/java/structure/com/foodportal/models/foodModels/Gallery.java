package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class Gallery {

    private String name_ur;

    private String description_en;

    private int id;

    private String description_ur;

    private ArrayList<Photos> photos;

    public String getName_ur() {
        return name_ur;
    }

    public void setName_ur(String name_ur) {
        this.name_ur = name_ur;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription_ur() {
        return description_ur;
    }

    public void setDescription_ur(String description_ur) {
        this.description_ur = description_ur;
    }

    public ArrayList<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photos> photos) {
        this.photos = photos;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    private String name_en;

}
