package structure.com.foodportal.customViews.data;

import structure.com.foodportal.helper.Spanny;

/**
 * Created by awidiyadew on 12/09/16.
 */
public class BaseItem {
    private String mName;
    private String mimage;
    public String getImage() {
        return mimage;
    }

    public String getName() {
        return mName;
    }




    public BaseItem(String name,String image) {

        mName = name;
        mimage = image;
    }


}
