package structure.com.foodportal.models.foodModels;


import java.util.ArrayList;

import structure.com.foodportal.customViews.data.BaseItem;

/**
 * Created by developer.ingic on 6/5/2018.
 */

public class DrawerItem {

    BaseItem item;
    String type;
    ArrayList<DrawerItem> childItem;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;

    public BaseItem getItem() {
        return item;
    }

    public void setItem(BaseItem item) {
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<DrawerItem> getChildItem() {
        return childItem;
    }

    public void setChildItem(ArrayList<DrawerItem> childItem) {
        this.childItem = childItem;
    }

    public DrawerItem(BaseItem item, String type, ArrayList<DrawerItem> childItem,String imagemage) {
        this.image = imagemage;
        this.item = item;
        this.type = type;
        this.childItem = childItem;
    }
}
