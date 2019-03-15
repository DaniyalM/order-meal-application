package structure.com.foodportal.helper;

public interface ItemTouchHelperAdapter {


    void onItemMove(int fromPosition, int toPosition);


    void onItemDismiss(int position);
}