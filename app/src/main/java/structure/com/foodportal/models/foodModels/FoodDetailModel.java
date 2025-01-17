package structure.com.foodportal.models.foodModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

public class FoodDetailModel implements Serializable {



    @SerializedName("serving_for")
    private int serving_for;

    @SerializedName("is_favorite")
    private int is_favorite;

    @SerializedName("video_path")
    private String video_path;

    @SerializedName("before_start_en")
    private String before_start_en;

    @SerializedName("before_start_ur")
    private String before_start_ur;

    @SerializedName("before_start_content_en")
    private String before_start_content_en;

    @SerializedName("before_start_content_ur")
    private String before_start_content_ur;

    @SerializedName("masalas_aromatics_en")
    private String masalas_aromatics_en;

    @SerializedName("masalas_aromatics_ur")
    private String masalas_aromatics_ur;

    public String getBefore_start_en() {
        return before_start_en;
    }

    public void setBefore_start_en(String before_start_en) {
        this.before_start_en = before_start_en;
    }

    public String getBefore_start_content_en() {
        return before_start_content_en;
    }

    public void setBefore_start_content_en(String before_start_content_en) {
        this.before_start_content_en = before_start_content_en;
    }

    public String getBefore_start_ur() {
        return before_start_ur;
    }

    public void setBefore_start_ur(String before_start_ur) {
        this.before_start_ur = before_start_ur;
    }

    public String getBefore_start_content_ur() {
        return before_start_content_ur;
    }

    public void setBefore_start_content_ur(String before_start_content_ur) {
        this.before_start_content_ur = before_start_content_ur;
    }

    public String getMasalas_aromatics_ur() {
        return masalas_aromatics_ur;
    }

    public void setMasalas_aromatics_ur(String masalas_aromatics_ur) {
        this.masalas_aromatics_ur = masalas_aromatics_ur;
    }

    public String getMasalas_aromatics_content_ur() {
        return masalas_aromatics_content_ur;
    }

    public void setMasalas_aromatics_content_ur(String masalas_aromatics_content_ur) {
        this.masalas_aromatics_content_ur = masalas_aromatics_content_ur;
    }

    public String getMasalas_aromatics_en() {
        return masalas_aromatics_en;
    }

    public void setMasalas_aromatics_en(String masalas_aromatics_en) {
        this.masalas_aromatics_en = masalas_aromatics_en;
    }

    public String getMasalas_aromatics_content_en() {
        return masalas_aromatics_content_en;
    }

    public void setMasalas_aromatics_content_en(String masalas_aromatics_content_en) {
        this.masalas_aromatics_content_en = masalas_aromatics_content_en;
    }

    @SerializedName("masalas_aromatics_content_en")
    private String masalas_aromatics_content_en;

    @SerializedName("masalas_aromatics_content_ur")
    private String masalas_aromatics_content_ur;

    public String getVideo_url_path() {
        return video_url_path;
    }

    public void setVideo_url_path(String video_url_path) {
        this.video_url_path = video_url_path;
    }

    @SerializedName("video_url_path")
    private String video_url_path;

    @SerializedName("content_ur")
    private String content_ur;

    @SerializedName("excerpt_en")
    private String excerpt_en;

    @SerializedName("content_en")
    private String content_en;

    @SerializedName("video_thumb")
    private String video_thumb;

    @SerializedName("title_ur")
    private String title_ur;

    @SerializedName("is_save")
    private int is_save;

    @SerializedName("cook_time")
    private String cook_time;

    @SerializedName("difficulty")
    private int difficulty;

    @SerializedName("countFavorites")
    private int countFavorites;

    @SerializedName("video_url")
    private String video_url;

    @SerializedName("feature_type_id")
    private int feature_type_id;

    @SerializedName("preparation_time")
    private String preparation_time;

    @SerializedName("title_en")
    private String title_en;

    @SerializedName("gallery_id")
    private int gallery_id;

    @SerializedName("avgRating")
    private int avgRating;

    @SerializedName("totalViews")
    private int totalViews;

    @SerializedName("id")
    private int id;

    @SerializedName("excerpt_ur")
    private String excerpt_ur;

    @SerializedName("slug")
    private String slug;

    public String getRecipe_type() {
        return recipe_type;
    }

    public void setRecipe_type(String recipe_type) {
        this.recipe_type = recipe_type;
    }

    @Nullable
    @SerializedName("recipe_type")
    private String recipe_type;

    @SerializedName("gallery")
    Gallery gallery;

    public String getBlog_thumb_image_path() {
        return blog_thumb_image_path;
    }

    public void setBlog_thumb_image_path(String blog_thumb_image_path) {
        this.blog_thumb_image_path = blog_thumb_image_path;
    }

    @SerializedName("blog_thumb_image_path")
    String blog_thumb_image_path;

    @SerializedName("schedules")
    ArrayList<Schedules> schedules;

    @SerializedName("ingredient")
    ArrayList<Ingredient> ingredient;

    @SerializedName("steps")
    ArrayList<Step> steps;

    @SerializedName("tag")
    ArrayList<Tag> tag;

    @SerializedName("chef")
    ArrayList<Chef> chef;

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public ArrayList<Schedules> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedules> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }

    public ArrayList<Chef> getChef() {
        return chef;
    }

    public void setChef(ArrayList<Chef> chef) {
        this.chef = chef;
    }

    public ArrayList<TotalViews> getTotal_views() {
        return total_views;
    }

    public void setTotal_views(ArrayList<TotalViews> total_views) {
        this.total_views = total_views;
    }

    ArrayList<TotalViews> total_views;

    public int getServing_for() {
        return serving_for;
    }

    public void setServing_for(int serving_for) {
        this.serving_for = serving_for;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getContent_ur() {
        return content_ur;
    }

    public void setContent_ur(String content_ur) {
        this.content_ur = content_ur;
    }

    public String getExcerpt_en() {
        return excerpt_en;
    }

    public void setExcerpt_en(String excerpt_en) {
        this.excerpt_en = excerpt_en;
    }

    public String getContent_en() {
        return content_en;
    }

    public void setContent_en(String content_en) {
        this.content_en = content_en;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getTitle_ur() {
        return title_ur;
    }

    public void setTitle_ur(String title_ur) {
        this.title_ur = title_ur;
    }

    public int getIs_save() {
        return is_save;
    }

    public void setIs_save(int is_save) {
        this.is_save = is_save;
    }

    public String getCook_time() {
        return cook_time;
    }

    public void setCook_time(String cook_time) {
        this.cook_time = cook_time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getCountFavorites() {
        return countFavorites;
    }

    public void setCountFavorites(int countFavorites) {
        this.countFavorites = countFavorites;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getFeature_type_id() {
        return feature_type_id;
    }

    public void setFeature_type_id(int feature_type_id) {
        this.feature_type_id = feature_type_id;
    }

    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public int getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(int gallery_id) {
        this.gallery_id = gallery_id;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExcerpt_ur() {
        return excerpt_ur;
    }

    public void setExcerpt_ur(String excerpt_ur) {
        this.excerpt_ur = excerpt_ur;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public FoodDetailModel getSpecial_recipe_story() {
        return special_recipe_story;
    }

    public void setSpecial_recipe_story(FoodDetailModel special_recipe_story) {
        this.special_recipe_story = special_recipe_story;
    }

    FoodDetailModel special_recipe_story;

    ArrayList<SpecialIngedient> special_ingredients;

    public ArrayList<SpecialIngedient> getSpecial_ingredients() {
        return special_ingredients;
    }

    public void setSpecial_ingredients(ArrayList<SpecialIngedient> special_ingredients) {
        this.special_ingredients = special_ingredients;
    }

    public ArrayList<SpecialIngredientSteps> getSpecial_recipe_step() {
        return special_recipe_step;
    }

    public void setSpecial_recipe_step(ArrayList<SpecialIngredientSteps> special_recipe_step) {
        this.special_recipe_step = special_recipe_step;
    }

    ArrayList<SpecialIngredientSteps> special_recipe_step;


}