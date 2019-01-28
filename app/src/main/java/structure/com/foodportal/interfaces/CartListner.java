package structure.com.foodportal.interfaces;

import structure.com.foodportal.models.CartModule.CartProductMainClass;

public interface CartListner {

    void removefromcart(CartProductMainClass cartProductMainClass, int pos);

    void onClickItem(CartProductMainClass cartProductMainClass, int position);
    void dropDownDaysSelecotr(CartProductMainClass cartProductMainClass, int position);

    void onDialogClick(String number);
}
