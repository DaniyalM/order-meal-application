package com.ingic.boutiqueapp.customViews.NavigationDrawerDataViews.data;

import com.ingic.boutiqueapp.constant.AppConstant;
import com.ingic.boutiqueapp.models.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awidiyadew on 15/09/16.
 */
public class CustomDataProvider {

    private static final int MAX_LEVELS = 3;

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;

    private static List<BaseItem> mMenu = new ArrayList<>();
    static ArrayList<DrawerItem> childItems = new ArrayList<>();

    public static List<BaseItem> getInitialItems(ArrayList<DrawerItem> items) {
        //return getSubItems(new GroupItem("root"));

        List<BaseItem> rootMenu = new ArrayList<>();

        /*
        * ITEM = TANPA CHILD
        * GROUPITEM = DENGAN CHILD
        * */
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType().equals(AppConstant.DrawerItemType.GROUP)) {
                rootMenu.add(new GroupItem(items.get(i).getItem().getName()));
                childItems = items.get(i).getChildItem();
            } else {
                rootMenu.add(new Item(items.get(i).getItem().getName()));
            }
        }


        return rootMenu;
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {

        List<BaseItem> result = new ArrayList<>();
        int level = ((GroupItem) baseItem).getLevel() + 1;
        String menuItem = baseItem.getName();

        if (!(baseItem instanceof GroupItem)) {
            throw new IllegalArgumentException("GroupItem required");
        }

        GroupItem groupItem = (GroupItem) baseItem;
        if (groupItem.getLevel() >= MAX_LEVELS) {
            return null;
        }


        switch (level) {
            case LEVEL_1:
                      result=getListCategories();
                break;

            case LEVEL_2:
                /*switch (menuItem) {
                    case "Women Apparel":

                        for (int i = 0; i < childItems.size(); i++) {
                            if (childItems.get(i).getItem().getName().equals("Women Apparel")) {

                                result = getSubCategories(childItems.get(i).getChildItem());
                            }
                        }
                        break;
                    case "Beauty":

                        for (int i = 0; i < childItems.size(); i++) {
                            if (childItems.get(i).getItem().getName().equals("Beauty")) {

                                result = getSubCategories(childItems.get(i).getChildItem());
                            }
                        }
                        break;
                    case "Home and Lifestyle":
                        for (int i = 0; i < childItems.size(); i++) {
                            if (childItems.get(i).getItem().getName().equals("Home and Lifestyle"))
                                result = getSubCategories(childItems.get(i).getChildItem());
                        }
                        break;
                    case "HealthCare":
                        for (int i = 0; i < childItems.size(); i++) {
                            if (childItems.get(i).getItem().getName().equals("HealthCare"))
                                result = getSubCategories(childItems.get(i).getChildItem());
                        }
                        break;
                    case "Accessories":
                        for (int i = 0; i < childItems.size(); i++) {
                            if (childItems.get(i).getItem().getName().equals("Accessories"))
                                result = getSubCategories(childItems.get(i).getChildItem());
                        }
                        break;
                }*/
                for (int i = 0; i < childItems.size(); i++) {
                if (childItems.get(i).getItem().getName().equals(menuItem))
                    result = getSubCategories(childItems.get(i).getChildItem());
            }
                break;
        }

        return result;
    }

    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }

    private static List<BaseItem> getListCategories() {

        List<BaseItem> list = new ArrayList<>();

        for (int i = 0; i < childItems.size(); i++) {
            if (childItems.get(i).getType().equals(AppConstant.DrawerItemType.GROUP)) {
                GroupItem groupItem=new GroupItem(childItems.get(i).getItem().getName());
                groupItem.setLevel(groupItem.getLevel() + 1);
                list.add(groupItem);

            } else {
                list.add(new Item(childItems.get(i).getItem().getName()));
            }
        }

        return list;
    }


    private static List<BaseItem> getSubCategories(ArrayList<DrawerItem> subChildItems) {
        List<BaseItem> list = new ArrayList<>();
        if(subChildItems!=null)
            for (int i = 0; i <subChildItems.size() ; i++) {
            list.add(new Item(subChildItems.get(i).getItem().getName()));
        }


        return list;
    }


}
