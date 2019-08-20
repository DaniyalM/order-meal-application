package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class Banner
{
    public ArrayList<CountFavorite> getCount_favorites() {
        return count_favorites;
    }

    public void setCount_favorites(ArrayList<CountFavorite> count_favorites) {
        this.count_favorites = count_favorites;
    }

    ArrayList<CountFavorite> count_favorites;

    private String featured_image_path;

    public String getFeatured_image_path() {
        return featured_image_path;
    }

    public void setFeatured_image_path(String featured_image_path) {
        this.featured_image_path = featured_image_path;
    }

    private String serving_for;
    private String banner_image_path;

    public String getBanner_image_path() {
        return banner_image_path;
    }

    public void setBanner_image_path(String banner_image_path) {
        this.banner_image_path = banner_image_path;
    }

    public String getLoop_video_path() {
        return loop_video_path;
    }

    public void setLoop_video_path(String loop_video_path) {
        this.loop_video_path = loop_video_path;
    }

    private String loop_video_path;

    public String getBlog_thumb_image() {
        return blog_thumb_image;
    }

    public void setBlog_thumb_image(String blog_thumb_image) {
        this.blog_thumb_image = blog_thumb_image;
    }

    private String blog_thumb_image;

    private String video_path;

    private String excerpt_en;


    private String title_ur;

    private String cook_time;

    private String difficulty;

    private String countFavorites;

    private String video_url;



    private String feature_type_id;

    public int getSpecial_recipes_id() {
        return special_recipes_id;
    }

    public void setSpecial_recipes_id(int special_recipes_id) {
        this.special_recipes_id = special_recipes_id;
    }

    public String getSpecial_recipes_slug() {
        return special_recipes_slug;
    }

    public void setSpecial_recipes_slug(String special_recipes_slug) {
        this.special_recipes_slug = special_recipes_slug;
    }

    private int special_recipes_id;
    private String special_recipes_slug;


    private String preparation_time;

    private String title_en;

    private String gallery_id;

    private String avgRating;

    private String totalViews;

    private int id;

    private String excerpt_ur;

    private String slug;

    private Gallery gallery;

    public String getServing_for ()
    {
        return serving_for;
    }

    public void setServing_for (String serving_for)
    {
        this.serving_for = serving_for;
    }

    public String getVideo_path ()
    {
        return video_path;
    }

    public void setVideo_path (String video_path)
    {
        this.video_path = video_path;
    }

    public String getExcerpt_en ()
    {
        return excerpt_en;
    }

    public void setExcerpt_en (String excerpt_en)
    {
        this.excerpt_en = excerpt_en;
    }



    public String getTitle_ur ()
    {
        return title_ur;
    }

    public void setTitle_ur (String title_ur)
    {
        this.title_ur = title_ur;
    }

    public String getCook_time ()
    {
        return cook_time;
    }

    public void setCook_time (String cook_time)
    {
        this.cook_time = cook_time;
    }

    public String getDifficulty ()
    {
        return difficulty;
    }

    public void setDifficulty (String difficulty)
    {
        this.difficulty = difficulty;
    }

    public String getCountFavorites ()
    {
        return countFavorites;
    }

    public void setCountFavorites (String countFavorites)
    {
        this.countFavorites = countFavorites;
    }

    public String getVideo_url ()
    {
        return video_url;
    }

    public void setVideo_url (String video_url)
    {
        this.video_url = video_url;
    }



    public String getFeature_type_id ()
    {
        return feature_type_id;
    }

    public void setFeature_type_id (String feature_type_id)
    {
        this.feature_type_id = feature_type_id;
    }



    public String getPreparation_time ()
    {
        return preparation_time;
    }

    public void setPreparation_time (String preparation_time)
    {
        this.preparation_time = preparation_time;
    }

    public String getTitle_en ()
    {
        return title_en;
    }

    public void setTitle_en (String title_en)
    {
        this.title_en = title_en;
    }

    public String getGallery_id ()
    {
        return gallery_id;
    }

    public void setGallery_id (String gallery_id)
    {
        this.gallery_id = gallery_id;
    }

    public String getAvgRating ()
    {
        return avgRating;
    }

    public void setAvgRating (String avgRating)
    {
        this.avgRating = avgRating;
    }

    public String getTotalViews ()
    {
        return totalViews;
    }

    public void setTotalViews (String totalViews)
    {
        this.totalViews = totalViews;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getExcerpt_ur ()
    {
        return excerpt_ur;
    }

    public void setExcerpt_ur (String excerpt_ur)
    {
        this.excerpt_ur = excerpt_ur;
    }

    public String getSlug ()
    {
        return slug;
    }

    public void setSlug (String slug)
    {
        this.slug = slug;
    }

    public Gallery getGallery ()
    {
        return gallery;
    }

    public void setGallery (Gallery gallery)
    {
        this.gallery = gallery;
    }




}