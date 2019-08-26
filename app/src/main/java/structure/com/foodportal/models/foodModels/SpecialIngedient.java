package structure.com.foodportal.models.foodModels;

public class SpecialIngedient {
    int id;
    String title_en;
    String title_ur;

    public String getTitle_ur() {
        return title_ur;
    }

    public void setTitle_ur(String title_ur) {
        this.title_ur = title_ur;
    }

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

    public String getIngredient_en() {
        return ingredient_en;
    }

    public void setIngredient_en(String ingredient_en) {
        this.ingredient_en = ingredient_en;
    }

    public String getIngredient_ur() {
        return ingredient_ur;
    }

    public void setIngredient_ur(String ingredient_ur) {
        this.ingredient_ur = ingredient_ur;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    String ingredient_en;
    String ingredient_ur;
    String image_path;

}
