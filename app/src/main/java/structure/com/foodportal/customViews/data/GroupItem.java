package structure.com.foodportal.customViews.data;

/**
 * Created by awidiyadew on 12/09/16.
 */
public class GroupItem extends BaseItem {
    private int mLevel;

    public GroupItem(String name,String image) {
        super(name,image);
        mLevel = 0;
    }

    public void setLevel(int level){
        mLevel = level;
    }

    public int getLevel(){
        return mLevel;
    }
}
