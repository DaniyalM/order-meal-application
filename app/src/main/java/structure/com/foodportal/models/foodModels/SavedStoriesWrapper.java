package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class SavedStoriesWrapper {
    int id;

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

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    String contact_no;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public ArrayList<SavedRecipe> getSaved_stories() {
        return saved_stories;
    }

    public void setSaved_stories(ArrayList<SavedRecipe> saved_stories) {
        this.saved_stories = saved_stories;
    }

    ArrayList<SavedRecipe> saved_stories;



}
