package structure.com.foodportal.models.foodModels;

class User {
    int id;
    int acct_type;
    String name_en;

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
}
