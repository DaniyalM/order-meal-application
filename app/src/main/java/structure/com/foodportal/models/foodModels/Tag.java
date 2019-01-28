package structure.com.foodportal.models.foodModels;

public class Tag {

    public String getName_ur() {
        return name_ur;
    }

    public void setName_ur(String name_ur) {
        this.name_ur = name_ur;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    private String name_ur;

    private int is_active;

    private String updated_at;

    private String created_at;

    private Pivot pivot;

    private int id;

    private String deleted_at;

    private String slug;

    private String name_en;


   public class Pivot {

        int story_id;

       public int getStory_id() {
           return story_id;
       }

       public void setStory_id(int story_id) {
           this.story_id = story_id;
       }

       public int getTag_id() {
           return tag_id;
       }

       public void setTag_id(int tag_id) {
           this.tag_id = tag_id;
       }

       int tag_id;


    }

}
