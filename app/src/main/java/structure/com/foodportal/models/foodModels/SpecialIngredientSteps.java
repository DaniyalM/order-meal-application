package structure.com.foodportal.models.foodModels;

public class SpecialIngredientSteps {

    int id;
    int special_recipe_id;
    String title_en;
    String steps_en;
    String title_ur;
    String steps_ur;

    public String getTitle_ur() {
        return title_ur;
    }

    public String getSteps_ur() {
        return steps_ur;
    }

    public void setTitle_ur(String title_ur) {
        this.title_ur = title_ur;
    }

    public void setSteps_ur(String steps_ur) {
        this.steps_ur = steps_ur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecial_recipe_id() {
        return special_recipe_id;
    }

    public void setSpecial_recipe_id(int special_recipe_id) {
        this.special_recipe_id = special_recipe_id;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getSteps_en() {
        return steps_en;
    }

    public void setSteps_en(String steps_en) {
        this.steps_en = steps_en;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    String image_path;
}
