package structure.com.foodportal.models.foodModels;

public  class User {
    String id;

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String facebook_id;
    String email;
    int acct_type;
    String name_en;
    String name_ur;

    public String getName_ur() {
        return name_ur;
    }

    public void setName_ur(String name_ur) {
        this.name_ur = name_ur;
    }

    String profile_picture_path;

    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    String contact_no;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAcct_type() {
        return acct_type;
    }

    public void setAcct_type(int acct_type) {
        this.acct_type = acct_type;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String profile_picture ;
    String rating ;
}
