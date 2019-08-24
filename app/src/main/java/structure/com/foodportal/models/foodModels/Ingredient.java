package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class Ingredient {

    private String quantity_en;

    private String quantity_ur;

    private int is_active;

    private int story_id;

    private String ingredient_ur;

    private String created_at;

    private int type;

    private String deleted_at;

    private String updated_at;

    private String tag_en;

    private String quantity_type_en;

    private String quantity_type_ur;

    private int id;

    private String tag_ur;

    private String ingredient_en;

    public String getQuantity_en() {
        return quantity_en;
    }

    public void setQuantity_en(String quantity_en) {
        this.quantity_en = quantity_en;
    }

    public String getQuantity_ur() {
        return quantity_ur;
    }

    public void setQuantity_ur(String quantity_ur) {
        this.quantity_ur = quantity_ur;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public String getIngredient_ur() {
        return ingredient_ur;
    }

    public void setIngredient_ur(String ingredient_ur) {
        this.ingredient_ur = ingredient_ur;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTag_en() {
        return tag_en;
    }

    public void setTag_en(String tag_en) {
        this.tag_en = tag_en;
    }

    public String getQuantity_type_en() {
        return quantity_type_en;
    }

    public void setQuantity_type_en(String quantity_type_en) {
        this.quantity_type_en = quantity_type_en;
    }

    public String getQuantity_type_ur() {
        return quantity_type_ur;
    }

    public void setQuantity_type_ur(String quantity_type_ur) {
        this.quantity_type_ur = quantity_type_ur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag_ur() {
        return tag_ur;
    }

    public void setTag_ur(String tag_ur) {
        this.tag_ur = tag_ur;
    }

    public String getIngredient_en() {
        return ingredient_en;
    }

    public void setIngredient_en(String ingredient_en) {
        this.ingredient_en = ingredient_en;
    }

//    public String[] getSub_ingredients() {
//        return sub_ingredients;
//    }
//
//    public void setSub_ingredients(String[] sub_ingredients) {
//        this.sub_ingredients = sub_ingredients;
//    }
//
//    private String[] sub_ingredients;

    public ArrayList<Ingredient> getSub_ingredients() {
        return sub_ingredients;
    }

    public void setSub_ingredients(ArrayList<Ingredient> sub_ingredients) {
        this.sub_ingredients = sub_ingredients;
    }

    ArrayList<Ingredient> sub_ingredients;



}
