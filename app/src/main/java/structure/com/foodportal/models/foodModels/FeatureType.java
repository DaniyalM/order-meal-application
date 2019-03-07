package structure.com.foodportal.models.foodModels;

public  class FeatureType {


    int id;
    String title_en;
    String slug;
    String banner_image_path;
    String created_at;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getBanner_image_path() {
        return banner_image_path;
    }

    public void setBanner_image_path(String banner_image_path) {
        this.banner_image_path = banner_image_path;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLoop_video_path() {
        return loop_video_path;
    }

    public void setLoop_video_path(String loop_video_path) {
        this.loop_video_path = loop_video_path;
    }

    String loop_video_path;

}
