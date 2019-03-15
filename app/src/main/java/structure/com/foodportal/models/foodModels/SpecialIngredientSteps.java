package structure.com.foodportal.models.foodModels;

public class SpecialIngredientSteps {

    int id;
    int special_recipe_id;
    String title_en;
    String steps_en;

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
