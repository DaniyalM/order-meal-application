package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class Comments {

    int id;
    int story_id;
    int user_id;
    int feature_type_id;
    String reviews;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFeature_type_id() {
        return feature_type_id;
    }

    public void setFeature_type_id(int feature_type_id) {
        this.feature_type_id = feature_type_id;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    User user;


    public ArrayList<Comments> getChild_reviews() {
        return child_reviews;
    }

    public void setChild_reviews(ArrayList<Comments> child_reviews) {
        this.child_reviews = child_reviews;
    }

    ArrayList<Comments> child_reviews;


}
