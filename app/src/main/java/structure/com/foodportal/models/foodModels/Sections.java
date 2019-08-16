package structure.com.foodportal.models.foodModels;

public class Sections {

    int id;
    String title_en;
    String title_ur;
    String slug;
    String video_path;
    int is_favorite;

    public String getBlog_thumb_image_path_size() {
        return blog_thumb_image_path_size;
    }

    public void setBlog_thumb_image_path_size(String blog_thumb_image_path_size) {
        this.blog_thumb_image_path_size = blog_thumb_image_path_size;
    }

    String blog_thumb_image_path_size;

    String blog_thumb_image_path;

    public String getBlog_thumb_image_path() {
        return blog_thumb_image_path;
    }

    public void setBlog_thumb_image_path(String blog_thumb_image_path) {
        this.blog_thumb_image_path = blog_thumb_image_path;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public int getIs_save() {
        return is_save;
    }

    public void setIs_save(int is_save) {
        this.is_save = is_save;
    }

    int is_save;

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    String video_thumb;

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getBlog_thumb_image() {
        return blog_thumb_image;
    }

    public void setBlog_thumb_image(String blog_thumb_image) {
        this.blog_thumb_image = blog_thumb_image;
    }

    String blog_thumb_image;

    public String getFeatured_image_path() {
        return featured_image_path;
    }

    public void setFeatured_image_path(String featured_image_path) {
        this.featured_image_path = featured_image_path;
    }

    String featured_image_path;

    public String getContent_en() {
        return content_en;
    }

    public void setContent_en(String content_en) {
        this.content_en = content_en;
    }

    String content_en;
    String content_ur;

    public String getContent_ur() {
        return content_ur;
    }

    public void setContent_ur(String content_ur) {
        this.content_ur = content_ur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title_en;
    }

    public void setTitle(String title_en) {
        this.title_en = title_en;
    }

    public void setTitle_ur(String title_ur) {
        this.title_ur = title_ur;
    }


    public String getTitle_ur() {
        return title_ur;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    Gallery gallery;

    public int getFeature_type_id() {
        return feature_type_id;
    }

    public void setFeature_type_id(int feature_type_id) {
        this.feature_type_id = feature_type_id;
    }

    int  feature_type_id;

    public int getServing_for() {
        return serving_for;
    }

    public void setServing_for(int serving_for) {
        this.serving_for = serving_for;
    }

    public String getCook_time() {
        return cook_time;
    }

    public void setCook_time(String cook_time) {
        this.cook_time = cook_time;
    }

    int  serving_for;
    String  cook_time;


}
