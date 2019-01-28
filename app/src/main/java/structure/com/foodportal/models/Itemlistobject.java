package structure.com.foodportal.models;

/**
 * Created by syedghazanfar on 10/9/2017.
 */
public class Itemlistobject {
    private String name;
    private String imagePath;
    private String desc;
    private String photo;
    private String id;
    private boolean newImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Itemlistobject(String photo, String id) {
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.id = id;
    }

    public Itemlistobject(String photo, String id, boolean newImage) {
        this.newImage = newImage;
        this.photo = photo;
        this.id = id;
    }

    public Itemlistobject(String absolutePath) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isNewImage() {
        return newImage;
    }

    public void setNewImage(boolean newImage) {
        this.newImage = newImage;
    }
}