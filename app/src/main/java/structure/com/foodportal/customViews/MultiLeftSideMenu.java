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
import structure.com.foodportal.fragment.foodportal.SavedRecipesFragment;
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
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
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
        binder.content.startRippleAnimation();

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


        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {


                expandableListView.scrollTo(parent.getPositionForView(v), parent.getPositionForView(v));
                v.requestFocus();
                parent.requestFocus();
                switch (listDataHeader.get(groupPosition)) {
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
                        mainActivity.clearBackStack();
                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES), true, false);
                        break;
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS), true, false);
                        break;
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING), true, false);
                        break;
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG), true, false);
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

                expandableListView.smoothScrollToPosition(groupPosition);


            }
        });

        // Listview Group collasped listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

               // expandableListView.collapseGroup(groupPosition);
                SubCategoryFragment subCategoryFragment;
                switch (listDataHeader.get(groupPosition)) {
                      case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                         subCategoryFragment = new SubCategoryFragment();
                        subCategoryFragment.setModel(listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition),AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES);
                        mainActivity.addFragment(subCategoryFragment, true, false);
                        break;
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                         subCategoryFragment = new SubCategoryFragment();
                        subCategoryFragment.setModel(listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition),AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS);
                        mainActivity.addFragment(subCategoryFragment, true, false);


                        break;
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                        subCategoryFragment = new SubCategoryFragment();
                        subCategoryFragment.setModel(listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition),AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING);
                        mainActivity.addFragment(subCategoryFragment, true, false);



                        break;
                    case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
                        mainActivity.closeDrawer();
                        mainActivity.clearBackStack();
                        subCategoryFragment = new SubCategoryFragment();
                        subCategoryFragment.setModel(listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition),AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG);
                        mainActivity.addFragment(subCategoryFragment, true, false);

                        break;

                }





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

        TextView footersave = (TextView) footerView.findViewById(R.id.savedrecipes);//set the text to Footer View
        expandableListView.addFooterView(footerView);//Add view to list view as footer view



        headerTitle.setOnClickListener(view -> {
            mainActivity.clearBackStack();
            mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES), true, false);

            //updateLeftDrawer("home");

        });
        footerTitle.setOnClickListener(view -> {

            mainActivity.prefHelper.putUserFood(null);
            mainActivity.prefHelper.setLoginStatus(false);
            mainActivity.finish();
            mainActivity.showRegistrationActivity();
            // updateLeftDrawer("logout");

        });
        footerView.getRootView().findViewById(R.id.savedrecipes).setOnClickListener(view -> {
            mainActivity.clearBackStack();
            mainActivity.addFragment(new SavedRecipesFragment(),true,false);
            // updateLeftDrawer("logout");

        });


    }

    private int lastPosition = -1;

    private void confMenu() {

        setMenu();
        binder.txtUsername.setText(preferenceHelper.getUserFood().getName_en());
        switch (preferenceHelper.getUserFood().getAcct_type()){
            case 1:
                UIHelper.setImageWithGlide(mainActivity,binder.imgBackground,AppConstant.BASE_URL_IMAGE+preferenceHelper.getUserFood().getProfile_picture());

            break;
            case 2:
                UIHelper.setImageWithGlide(mainActivity,binder.imgBackground,preferenceHelper.getUserFood().getProfile_picture());

            break;
            case 3:
                UIHelper.setImageWithGlide(mainActivity,binder.imgBackground,preferenceHelper.getUserFood().getProfile_picture());

            break;


        }

        if (headerWrapper != null && headerWrapper.size() > 0) {
            String sourceString = "Home";


            if (headerWrapper.get(0).getCategories().size() > 0) {
                recipes.addAll(headerWrapper.get(0).getCategories());
                sourceString = headerWrapper.get(0).getTitle_en();
                listDataHeader.add(sourceString);
                listDataChild.put(sourceString, recipes);

            }

            if (headerWrapper.size()>=2 ) {


              if(headerWrapper.get(1).getSlug().equalsIgnoreCase("tutorial")){

                  tutorials.addAll(headerWrapper.get(1).getCategories());
                  sourceString = headerWrapper.get(1).getTitle_en();
                  listDataHeader.add(sourceString);
                  listDataChild.put(sourceString, tutorials);

              }
              else if(headerWrapper.get(1).getSlug().equalsIgnoreCase("cleaning")){

                  cleaning.addAll(headerWrapper.get(1).getCategories());

                  sourceString = headerWrapper.get(1).getTitle_en();
                  listDataHeader.add(sourceString);
                  listDataChild.put(sourceString, cleaning);
                }
              else if(headerWrapper.get(1).getSlug().equalsIgnoreCase("blog")){

                 // blog.addAll(headerWrapper.get(1).getCategories());

                  sourceString = headerWrapper.get(1).getTitle_en();
                  listDataHeader.add(sourceString);
                  listDataChild.put(sourceString, blog);
                }


            }


            if (headerWrapper.size()>=3 ) {



                if(headerWrapper.get(2).getSlug().equalsIgnoreCase("tutorial")){

                    tutorials.addAll(headerWrapper.get(2).getCategories());
                    sourceString = headerWrapper.get(2).getTitle_en();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, tutorials);

                }
                else if(headerWrapper.get(2).getSlug().equalsIgnoreCase("cleaning")){

                    cleaning.addAll(headerWrapper.get(2).getCategories());

                    sourceString = headerWrapper.get(2).getTitle_en();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, cleaning);
                }
                else if(headerWrapper.get(2).getSlug().equalsIgnoreCase("blog")){

                   // blog.addAll(headerWrapper.get(2).getCategories());

                    sourceString = headerWrapper.get(2).getTitle_en();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, blog);
                }








            }
            if (headerWrapper.size()>= 4 ) {




                if(headerWrapper.get(3).getSlug().equalsIgnoreCase("tutorial")){

                    tutorials.addAll(headerWrapper.get(3).getCategories());
                    sourceString = headerWrapper.get(3).getTitle_en();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, tutorials);

                }
                else if(headerWrapper.get(3).getSlug().equalsIgnoreCase("cleaning")){

                    cleaning.addAll(headerWrapper.get(3).getCategories());

                    sourceString = headerWrapper.get(3).getTitle_en();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, cleaning);
                }
                else if(headerWrapper.get(3).getSlug().equalsIgnoreCase("blog")){

                   // blog.addAll(headerWrapper.get(3).getCategories());

                    sourceString = headerWrapper.get(3).getTitle_en();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, blog);
                }






            }


        }

        listAdapter = new ExpandableListAdapter(mainActivity, listDataHeader, listDataChild, expandableListView);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


    }


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

