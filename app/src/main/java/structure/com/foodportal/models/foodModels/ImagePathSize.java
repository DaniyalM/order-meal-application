package structure.com.foodportal.models.foodModels;

import com.google.gson.annotations.SerializedName;

public class ImagePathSize {

    @SerializedName("320")
    private String var_320;

    @SerializedName("default")
    private String var_default;

    public String getVar_320() {
        return var_320;
    }

    public String getVar_default() {
        return var_default;
    }

    public void setVar_320(String var_320) {
        this.var_320 = var_320;
    }

    public void setVar_default(String var_default) {
        this.var_default = var_default;
    }
}
