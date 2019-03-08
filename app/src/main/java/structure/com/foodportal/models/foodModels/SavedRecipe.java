package structure.com.foodportal.models.foodModels;

public class SavedRecipe {

    int id;
    int story_id;
    int user_id;
    int feature_type_id;
    String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public FoodDetailModel getStories() {
        return stories;
    }

    public void setStories(FoodDetailModel stories) {
        this.stories = stories;
    }

    FoodDetailModel stories;
}
