package structure.com.foodportal.models.foodModels;

import java.util.ArrayList;

public class MainHeaderWrapper {

    public ArrayList<HeaderWrapper> getResult() {
        return Result;
    }

    public void setResult(ArrayList<HeaderWrapper> Result) {
        this.Result = Result;
    }

    ArrayList<HeaderWrapper> Result;
}
