package structure.com.foodportal.customViews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.DrawerAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.ExpandableListAdapter;
import structure.com.foodportal.customViews.data.BaseItem;
import structure.com.foodportal.customViews.data.CustomDataProvider;
import structure.com.foodportal.databinding.FragmentSidemenuBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.HomeFragment;
import structure.com.foodportal.fragment.foodportal.FoodHomeFragment;
import structure.com.foodportal.fragment.foodportal.SubCategoryFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Spanny;
import structure.com.foodportal.helper.Titlebar;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.DrawerItem;
import structure.com.foodportal.models.foodModels.HeaderWrapper;


public class MultiLeftSideMenu extends BaseFragment {
    private Context context;
    private FragmentSidemenuBinding binder;
    private DrawerAdapter drawerAdapter;
    ArrayList<DrawerItem> drawerItems;
    ArrayList<HeaderWrapper> headerWrapper;

    public static MultiLeftSideMenu newInstance() {

        return new MultiLeftSideMenu();
    }

    public MultiLeftSideMenu(ArrayList<HeaderWrapper> headerWrapper) {
        this.headerWrapper = headerWrapper;


    }

    public MultiLeftSideMenu() {


    }


    public void MultiLeftSideMenu(ArrayList<HeaderWrapper> headerWrapper) {
        this.headerWrapper = headerWrapper;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_sidemenu, container, false);
        // if(headerWrapper!=null && headerWrapper.size()>0){


        confMenu();


        //  }

        return binder.getRoot();
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    ExpandableListView expandableListView;
    TextView logout, home;

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<CategorySlider>> listDataChild;

    ArrayList<CategorySlider> recipes;
    ArrayList<CategorySlider> tutorials;
    ArrayList<CategorySlider> cleaning;
    ArrayList<CategorySlider> blog;

    public void setMenu() {

        recipes = new ArrayList<>();
        tutorials = new ArrayList<>();
        cleaning = new ArrayList<>();
        blog = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataHeader = new ArrayList<>();
        expandableListView = (ExpandableListView) binder.getRoot().findViewById(R.id.sideoptions);

//        home = (TextView) binder.getRoot().findViewById(R.id.home);
//        logout = (TextView) binder.getRoot().findViewById(R.id.logout);
//
//
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mainActivity.clearBackStack();
//                mainActivity.addFragment(new FoodHomeFragment(), true, false);
//
//            }
//        });
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mainActivity.finish();
//                mainActivity.showRegistrationActivity();
//
//
//            }
//        });

        //  expandableListView.addHeaderView(home);
        // expandableListView.addFooterView(logout);

        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();


                expandableListView.scrollTo(parent.getPositionForView(v),parent.getPositionForView(v));
                v.requestFocus();
                parent.requestFocus();
                switch (groupPosition){

                    case 0:
                        mainActivity.clearBackStack();
                mainActivity.addFragment(new FoodHomeFragment(), true, false);
                        break;
                    case 1:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                        mainActivity.addFragment(new FoodHomeFragment(),true,false);
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(1),true,false);

                        break;
                    case 2:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(2),true,false);
                        break; case 3:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(2),true,false);
                        break; case 4:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(2),true,false);
                        break; case 5:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(2),true,false);
                        break; case 6:

//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(2),true,false);
                        break;






                }


                return true;
            }
        });

        // expandableListView Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {


                if (lastPosition != -1
                        && groupPosition != lastPosition) {
                    expandableListView.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;


//                Toast.makeText(mainActivity,
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(mainActivity,
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub


                mainActivity.closeDrawer();

                mainActivity.clearBackStack();
                SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                subCategoryFragment.setModel(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                mainActivity.addFragment(subCategoryFragment, true, false);


//
//                Toast.makeText(
//                        mainActivity,
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });



        // preparing list data
        addHeaderFooterView();

    }
    private void addHeaderFooterView() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Header View
        View headerView = inflater.inflate(R.layout.home, null, false);
        TextView headerTitle = (TextView) headerView.findViewById(R.id.home);
        headerTitle.setText(getActivity().getResources().getString(R.string.home));//set the text to Header View
        expandableListView.addHeaderView(headerView);//Add view to list view as header view

        //Footer View
        View footerView = inflater.inflate(R.layout.logout, null, false);
        TextView footerTitle = (TextView) footerView.findViewById(R.id.logout);
        footerTitle.setText(getActivity().getResources().getString(R.string.logout));//set the text to Footer View
        expandableListView.addFooterView(footerView);//Add view to list view as footer view

        headerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.clearBackStack();
                mainActivity.addFragment(new FoodHomeFragment(), true, false);


            }
        });
        footerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.finish();
                mainActivity.showRegistrationActivity();

            }
        });


    }

    private int lastPosition = -1;

    private void confMenu() {

        setMenu();
//        // custom ListAdapter
//        drawerAdapter = new DrawerAdapter(mainActivity);
//
//        binder.sideoptions.setAdapter(drawerAdapter);
//      //  binder.sideoptions.setOnItemClickListener(mOnItemClickListener);
//        drawerItems = new ArrayList<>();
//        ArrayList<DrawerItem> chilItems = new ArrayList<>();
//        ArrayList<DrawerItem> beautyChildItems = new ArrayList<>();
//        ArrayList<DrawerItem> womenApparelChildItems = new ArrayList<>();
//        ArrayList<DrawerItem> healthAndLifeChildItems = new ArrayList<>();
//        ArrayList<DrawerItem> saleChildItems = new ArrayList<>();
//
        if (headerWrapper != null && headerWrapper.size() > 0) {
            String sourceString = "Home";
//
//           // drawerItems.add((new DrawerItem(new BaseItem(Html.fromHtml(sourceString).toString(),null),  AppConstant.DrawerItemType.ITEM, null,null)));
//            listDataHeader.add(sourceString);
//            listDataChild.put(sourceString,null);


//             if(headerWrapper.get(0).getCategories().size()>0){
//                 for(int k=0; k<headerWrapper.get(0).getCategories().size();k++){
//                     listDataChild.add(new DrawerItem(new BaseItem(headerWrapper.get(0).getCategories().get(k).getCategory_title_en(),
//                             headerWrapper.get(0).getCategories().get(k).getSlider_thumb_path()
//                             ), AppConstant.DrawerItemType.ITEM, null,headerWrapper.get(0).getCategories().get(k).getSlider_thumb_path()));
//                     }
//                 sourceString = "<b>"+headerWrapper.get(0).getTitle_en() +"</b> ";
//                 drawerItems.add((new DrawerItem(new BaseItem(Html.fromHtml(sourceString).toString(),null), headerWrapper.get(0).getCategories().size() > 0 ? AppConstant.DrawerItemType.GROUP : AppConstant.DrawerItemType.ITEM, chilItems,null)));
//
//            }
//
//            if(headerWrapper.get(1).getCategories().size()>0){
//                for(int k=0; k<headerWrapper.get(1).getCategories().size();k++){
//                    saleChildItems.add(new DrawerItem(new BaseItem(headerWrapper.get(1).getCategories().get(k).getCategory_title_en(),
//                            headerWrapper.get(1).getCategories().get(k).getSlider_thumb_path()
//                    ), AppConstant.DrawerItemType.ITEM, null,headerWrapper.get(1).getCategories().get(k).getSlider_thumb_path()));
//                }
//                sourceString = "<b>"+headerWrapper.get(1).getTitle_en() +"</b> ";
//                drawerItems.add((new DrawerItem(new BaseItem(Html.fromHtml(sourceString).toString(),null), headerWrapper.get(1).getCategories().size() > 0 ? AppConstant.DrawerItemType.GROUP : AppConstant.DrawerItemType.ITEM, saleChildItems,null)));
//
//            }

            if (headerWrapper.get(0).getCategories().size() > 0) {
                recipes.addAll(headerWrapper.get(0).getCategories());
                sourceString = headerWrapper.get(0).getTitle_en();
                listDataHeader.add(sourceString);
                listDataChild.put(sourceString, recipes);

            }

            if (headerWrapper.get(1).getCategories().size() > 0) {

                tutorials.addAll(headerWrapper.get(1).getCategories());

                sourceString = headerWrapper.get(1).getTitle_en();
                listDataHeader.add(sourceString);
                listDataChild.put(sourceString, tutorials);

            }


            if (headerWrapper.get(2).getCategories().size() > 0) {

                cleaning.addAll(headerWrapper.get(2).getCategories());

                sourceString = headerWrapper.get(2).getTitle_en();
                listDataHeader.add(sourceString);
                listDataChild.put(sourceString, cleaning);

            }
            if (headerWrapper.get(3).getCategories().size() > 0) {

                blog.addAll(headerWrapper.get(3).getCategories());

                sourceString = headerWrapper.get(3).getTitle_en();
                listDataHeader.add(sourceString);
                listDataChild.put(sourceString, blog);

            }


//             sourceString =  "Logout";
//            listDataChild.put(sourceString,null);
//            listDataHeader.add(sourceString);

            //  drawerAdapter.setDataItems(CustomDataProvider.getInitialItems(drawerItems));
            //drawerAdapter.setSelectedItem(mainActivity.getResources().getString(R.string.home));
        }

        listAdapter = new ExpandableListAdapter(mainActivity, listDataHeader, listDataChild,expandableListView);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        healthAndLifeChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        healthAndLifeChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        saleChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, beautyChildItems));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, womenApparelChildItems));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, healthAndLifeChildItems));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, saleChildItems));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.categories)), AppConstant.DrawerItemType.GROUP, chilItems));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.my_account)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));

    }

//
//    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
//
//        private void showItemDescription(Object object, ItemInfo itemInfo) {
//            StringBuilder builder = new StringBuilder("\"");
//            builder.append(((BaseItem) object).getName());
//            builder.append("\" clicked!\n");
//            builder.append(getItemInfoDsc(itemInfo));
//
//            Toast.makeText(mainActivity, builder.toString(), Toast.LENGTH_SHORT).show();
//
//            switch (((BaseItem) object).getName()) {
//
//
//            }
//        }
//
//        @Override
//        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
//            //showItemDescription(item, itemInfo);
//            mainActivity.closeDrawer();
//            if (itemInfo.getLevel() == 0) {
//                drawerAdapter.setSelectedItem(((BaseItem) item).getName());
//            }
//
//            switch (((BaseItem) item).getName()) {
//
////                case "Shop":activityContext
////                    .emptyBackStack();
////                    activityContext.initFragmentsWithData(new HomeFragment(), null);
////                    break;
////                case "Brands":
////
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(new BrandsFragment(), null);
////                    break;
////                case "Celebrities":
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(new CelebritiesFragment(), null);
////                    break;
////                case "Wishlist":
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(WishListFragment.newInstance(), null);
////                    break;
////                case "My Account":
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(new MyAccountFragment(), null);
////
////                    break;
////                case "Settings":
////
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(new SettingsFragment(), null);
////                    break;
////                case "Contact Us":
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(new ContactUsFragment(), null);
////                    break;
////                case "FAQ's":
////                    activityContext.emptyBackStack();
////                    activityContext.initFragmentsWithData(new FaqsFragment(), null);
////                    break;
////                case "Fragranance":
////
////                    break;
////                case "Bath and Body":
////
////                    break;
////                case "Skincare":
////
////                    break;
////                case "Haircare":
////
////                    break;
////                case "Nailcare":
////
////                    break;
//                default:
//
//                    //  activityContext.initFragmentsWithData(ProductsFragment.newInstance(((BaseItem) item).getName()), null);
//                    break;
//
//
//            }
//
//
//        }
//
//        @Override
//        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
//            // showItemDescription(item, itemInfo);
//            if (itemInfo.getLevel() == 0) {
//                drawerAdapter.setSelectedItem(((BaseItem) item).getName());
//
//
//            }
//        }
//    };
//
//
//    private String getItemInfoDsc(ItemInfo itemInfo) {
//        StringBuilder builder = new StringBuilder();
//
//        builder.append(String.format("level[%d], idx in level[%d/%d]",
//                itemInfo.getLevel() + 1, /*Indexing starts from 0*/
//                itemInfo.getIdxInLevel() + 1 /*Indexing starts from 0*/,
//                itemInfo.getLevelSize()));
//
//        if (itemInfo.isExpandable()) {
//            builder.append(String.format(", expanded[%b]", itemInfo.isExpanded()));
//        }
//        return builder.toString();
//    }

    public void updateLeftDrawer(String name) {

        drawerAdapter.setSelectedItem(name);

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void create() throws IOException {

        //   confMenu();
        //drawerAdapter.notifyDataSetChanged();
    }
}

