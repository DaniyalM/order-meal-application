package structure.com.foodportal.firebase;

import java.io.Serializable;

/**
 * Created by khanubaid on 1/3/2018.
 */

public class GcmDataObject implements Serializable {

    String title = "";
    String message = "";
    String action_type = "";
    String thread_id = "";
    String sender_name = "";
    String sender_image = "";

    String ref_id = "";
    String sender_id = "";

    public ExtraPayload getExtra_payload() {
        return extra_payload;
    }

    public void setExtra_payload(ExtraPayload extra_payload) {
        this.extra_payload = extra_payload;
    }

    ExtraPayload extra_payload;

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_image() {
        return sender_image;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }



    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getAction_id() {
        return ref_id;
    }

    public void setAction_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
