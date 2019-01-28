package structure.com.foodportal.models;

import java.io.Serializable;

public class ProductDescriptionModel implements Serializable {

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    String descrition;

    public ProductDescriptionModel(int type, String descrition) {
        this.type = type;
        this.descrition = descrition;
    }
}
