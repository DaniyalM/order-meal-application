package structure.com.foodportal.models.foodModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe {

    private String id;

    private String title_ur;

    private String title_en;

    private String slug;

    private String special_recipes_slug;

    private int is_save;

    private int is_favorite;

    private Gallery gallery;

    private String blog_thumb_image_path_size_web;

    private String video_thumb_path;

    private String blog_thumb_image_path_size;

    private String blog_thumb_image_path;

    private String featured_image_path;

    private int avgRating;

    private String reviews;

    private String created_at;

    @SerializedName("categories")
    private ArrayList<CategorySlider> category_slider;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle_ur() {
        return title_ur;
    }

    public void setTitle_ur(String title_ur) {
        this.title_ur = title_ur;
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

    public String getSpecial_recipes_slug() {
        return special_recipes_slug;
    }

    public void setSpecial_recipes_slug(String special_recipes_slug) {
        this.special_recipes_slug = special_recipes_slug;
    }

    public int getIs_save() {
        return is_save;
    }

    public void setIs_save(int is_save) {
        this.is_save = is_save;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public String getBlog_thumb_image_path_size_web() {
        return blog_thumb_image_path_size_web;
    }

    public void setBlog_thumb_image_path_size_web(String blog_thumb_image_path_size_web) {
        this.blog_thumb_image_path_size_web = blog_thumb_image_path_size_web;
    }

    public String getVideo_thumb_path() {
        return video_thumb_path;
    }

    public void setVideo_thumb_path(String video_thumb_path) {
        this.video_thumb_path = video_thumb_path;
    }

    public String getBlog_thumb_image_path_size() {
        return blog_thumb_image_path_size;
    }

    public void setBlog_thumb_image_path_size(String blog_thumb_image_path_size) {
        this.blog_thumb_image_path_size = blog_thumb_image_path_size;
    }

    public String getBlog_thumb_image_path() {
        return blog_thumb_image_path;
    }

    public void setBlog_thumb_image_path(String blog_thumb_image_path) {
        this.blog_thumb_image_path = blog_thumb_image_path;
    }

    public String getFeatured_image_path() {
        return featured_image_path;
    }

    public void setFeatured_image_path(String featured_image_path) {
        this.featured_image_path = featured_image_path;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<CategorySlider> getCategory_slider() {
        return category_slider;
    }

    public void setCategory_slider(ArrayList<CategorySlider> category_slider) {
        this.category_slider = category_slider;
    }
}
