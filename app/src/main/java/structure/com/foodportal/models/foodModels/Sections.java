package structure.com.foodportal.models.foodModels;

public class Sections {

    int id;
    String title_en;
    String slug;
    String video_path;

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

    public String getContent_en() {
        return content_en;
    }

    public void setContent_en(String content_en) {
        this.content_en = content_en;
    }

    String content_en;


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


}
