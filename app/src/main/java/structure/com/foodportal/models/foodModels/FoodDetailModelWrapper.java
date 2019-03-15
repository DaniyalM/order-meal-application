package structure.com.foodportal.models.foodModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodDetailModelWrapper implements Serializable{

    public FoodDetailModel getData() {
        return data;
    }

    public void setData(FoodDetailModel data) {
        this.data = data;
    }

    @SerializedName("data")
    FoodDetailModel data;

    public FoodDetailModel getStory() {
        return story;
    }

    public void setStory(FoodDetailModel story) {
        this.story = story;
    }

    @SerializedName("story")
    FoodDetailModel story;

    public ArrayList<Comments> getAllReviews() {
        return allReviews;
    }

    public void setAllReviews(ArrayList<Comments> allReviews) {
        this.allReviews = allReviews;
    }

    ArrayList<Comments> allReviews;


    public ArrayList<Sections> getRelated() {
        return related;
    }

    public void setRelated(ArrayList<Sections> related) {
        this.related = related;
    }

    @SerializedName("related")
    ArrayList<Sections> related;


}
