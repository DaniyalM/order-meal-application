package structure.com.foodportal.models.foodModels;

public class Chef {
    private int is_active;

    private String last_name_ur;

    private String description_en;

    private String deleted_ta;

    private String created_at;

    private String profile_picture;

    private String description_ur;

    private String updated_at;

    private String first_name_en;

    private String phone;

    private String last_name_en;

    private String first_name_ur;

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getLast_name_ur() {
        return last_name_ur;
    }

    public void setLast_name_ur(String last_name_ur) {
        this.last_name_ur = last_name_ur;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getDeleted_ta() {
        return deleted_ta;
    }

    public void setDeleted_ta(String deleted_ta) {
        this.deleted_ta = deleted_ta;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getDescription_ur() {
        return description_ur;
    }

    public void setDescription_ur(String description_ur) {
        this.description_ur = description_ur;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFirst_name_en() {
        return first_name_en;
    }

    public void setFirst_name_en(String first_name_en) {
        this.first_name_en = first_name_en;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLast_name_en() {
        return last_name_en;
    }

    public void setLast_name_en(String last_name_en) {
        this.last_name_en = last_name_en;
    }

    public String getFirst_name_ur() {
        return first_name_ur;
    }

    public void setFirst_name_ur(String first_name_ur) {
        this.first_name_ur = first_name_ur;
    }

    public Tag.Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Tag.Pivot pivot) {
        this.pivot = pivot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Tag.Pivot pivot;

    private int id;
}
