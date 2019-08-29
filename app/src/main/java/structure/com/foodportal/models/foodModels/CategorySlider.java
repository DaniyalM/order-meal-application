package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class CategorySlider
{
    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    String title_en;
    String title_ur;

    public String getTitle_ur() {
        return title_ur;
    }

    public void setTitle_ur(String title_ur) {
        this.title_ur = title_ur;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    String slug;

    private String layout_id;

    private String is_active;

    private String description_en;

    private String created_at;

    private String slider_image;

    private String description_ur;

    private String slider_thumb_image;

    private String deleted_at;

    private String banner_path;

    private String category_title_en;

    private String slider_path;

    private String updated_at;

    private String feature_type_id;

    private String parent_id;

    private String banner_thumb_image;

    private String slider_thumb_path;

    private String gallery_id;

    private String template_id;

    private String banner_thumb_path;

    private String id;

    private String category_title_ur;

    private String banner_image;

    private String category_slug;

    private String is_slider;

    private String featured_image_path;

    public String getLayout_id ()
    {
        return layout_id;
    }

    public void setLayout_id (String layout_id)
    {
        this.layout_id = layout_id;
    }

    public String getIs_active ()
    {
        return is_active;
    }

    public void setIs_active (String is_active)
    {
        this.is_active = is_active;
    }

    public String getDescription_en ()
    {
        return description_en;
    }

    public void setDescription_en (String description_en)
    {
        this.description_en = description_en;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getSlider_image ()
{
    return slider_image;
}

    public void setSlider_image (String slider_image)
    {
        this.slider_image = slider_image;
    }

    public String getDescription_ur ()
    {
        return description_ur;
    }

    public void setDescription_ur (String description_ur)
    {
        this.description_ur = description_ur;
    }

    public String getSlider_thumb_image ()
{
    return slider_thumb_image;
}

    public void setSlider_thumb_image (String slider_thumb_image)
    {
        this.slider_thumb_image = slider_thumb_image;
    }

    public String getDeleted_at ()
{
    return deleted_at;
}

    public void setDeleted_at (String deleted_at)
    {
        this.deleted_at = deleted_at;
    }

    public String getBanner_path ()
{
    return banner_path;
}

    public void setBanner_path (String banner_path)
    {
        this.banner_path = banner_path;
    }

    public String getCategory_title_en ()
    {
        return category_title_en;
    }

    public void setCategory_title_en (String category_title_en)
    {
        this.category_title_en = category_title_en;
    }

    public String getSlider_path ()
{
    return slider_path;
}

    public void setSlider_path (String slider_path)
    {
        this.slider_path = slider_path;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getFeature_type_id ()
    {
        return feature_type_id;
    }

    public void setFeature_type_id (String feature_type_id)
    {
        this.feature_type_id = feature_type_id;
    }

    public String getParent_id ()
    {
        return parent_id;
    }

    public void setParent_id (String parent_id)
    {
        this.parent_id = parent_id;
    }

    public String getBanner_thumb_image ()
{
    return banner_thumb_image;
}

    public void setBanner_thumb_image (String banner_thumb_image)
    {
        this.banner_thumb_image = banner_thumb_image;
    }

    public String getSlider_thumb_path ()
{
    return slider_thumb_path;
}

    public void setSlider_thumb_path (String slider_thumb_path)
    {
        this.slider_thumb_path = slider_thumb_path;
    }

    public String getGallery_id ()
    {
        return gallery_id;
    }

    public void setGallery_id (String gallery_id)
    {
        this.gallery_id = gallery_id;
    }

    public String getTemplate_id ()
    {
        return template_id;
    }

    public void setTemplate_id (String template_id)
    {
        this.template_id = template_id;
    }

    public String getBanner_thumb_path ()
{
    return banner_thumb_path;
}

    public void setBanner_thumb_path (String banner_thumb_path)
    {
        this.banner_thumb_path = banner_thumb_path;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCategory_title_ur ()
    {
        return category_title_ur;
    }

    public void setCategory_title_ur (String category_title_ur)
    {
        this.category_title_ur = category_title_ur;
    }

    public String getBanner_image ()
{
    return banner_image;
}

    public void setBanner_image (String banner_image)
    {
        this.banner_image = banner_image;
    }

    public String getCategory_slug ()
    {
        return category_slug;
    }

    public void setCategory_slug (String category_slug)
    {
        this.category_slug = category_slug;
    }

    public String getIs_slider ()
    {
        return is_slider;
    }

    public void setIs_slider (String is_slider)
    {
        this.is_slider = is_slider;
    }


    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    Gallery gallery;

    public String getFeatured_image_path() {
        return featured_image_path;
    }

    public void setFeatured_image_path(String featured_image_path) {
        this.featured_image_path = featured_image_path;
    }

    public ArrayList<CategorySlider> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategorySlider> categories) {
        this.categories = categories;
    }

    ArrayList<CategorySlider> categories;
}