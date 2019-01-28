package structure.com.foodportal.models.foodModels;

public class Step {

    private String start_time;

    private String steps_en;

    private int is_active;

    private String updated_at;

    private String steps_ur;

    private int step_type;

    private int feature_type_id;

    private int story_id;

    private String end_time;

    private String created_at;

    private int id;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSteps_en() {
        return steps_en;
    }

    public void setSteps_en(String steps_en) {
        this.steps_en = steps_en;
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

    public String getSteps_ur() {
        return steps_ur;
    }

    public void setSteps_ur(String steps_ur) {
        this.steps_ur = steps_ur;
    }

    public int getStep_type() {
        return step_type;
    }

    public void setStep_type(int step_type) {
        this.step_type = step_type;
    }

    public int getFeature_type_id() {
        return feature_type_id;
    }

    public void setFeature_type_id(int feature_type_id) {
        this.feature_type_id = feature_type_id;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    private String deleted_at;
}
