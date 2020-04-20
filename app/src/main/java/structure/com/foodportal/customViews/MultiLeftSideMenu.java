package structure.com.foodportal.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.DrawerAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.ExpandableListAdapter;
import structure.com.foodportal.databinding.FragmentSidemenuBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.foodportal.FoodHomeFragment;
import structure.com.foodportal.fragment.foodportal.FoodMyReviewsFragment;
import structure.com.foodportal.fragment.foodportal.SavedRecipesFragment;
import structure.com.foodportal.fragment.foodportal.SettingsFragment;
import structure.com.foodportal.fragment.foodportal.SubCategoryFragment;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.DrawerItem;
import structure.com.foodportal.models.foodModels.HeaderWrapper;

import static structure.com.foodportal.helper.AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG;
import static structure.com.foodportal.helper.AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING;
import static structure.com.foodportal.helper.AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES;
import static structure.com.foodportal.helper.AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS;
import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;


public class MultiLeftSideMenu extends BaseFragment {
    private Context context;
    private FragmentSidemenuBinding binder;
    private DrawerAdapter drawerAdapter;
    ArrayList<DrawerItem> drawerItems;
    ArrayList<HeaderWrapper> headerWrapper;
    String type;

    public static MultiLeftSideMenu newInstance() {

        return new MultiLeftSideMenu();
    }

    @SuppressLint("ValidFragment")
    public MultiLeftSideMenu(ArrayList<HeaderWrapper> headerWrapper, String type) {
        this.headerWrapper = headerWrapper;
        this.type = type;


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
      //  binder.content.startRippleAnimation();

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

                if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(RECIPES)) {
                    mainActivity.clearBackStack();
                    mainActivity.addFragment(new FoodHomeFragment(RECIPES), true, false);
                } else if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase("Tutorial")) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    mainActivity.addFragment(new FoodHomeFragment(TUTORIALS), true, false);
                } else if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(CLEANING)) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    mainActivity.addFragment(new FoodHomeFragment(CLEANING), true, false);
                } else if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(BLOG)) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    mainActivity.addFragment(new FoodHomeFragment(BLOG), true, false);
                }


//                switch (listDataHeader.get(groupPosition)) {
//                    case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES), true, false);
//                        break;
//                    case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS), true, false);
//                        break;
//                    case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING), true, false);
//                        break;
//                    case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        mainActivity.addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG), true, false);
//                        break;
//                }


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


        binder.txtUseraddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.closeDrawer();
                mainActivity.addFragment(new SettingsFragment(), true, true);


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

                if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(RECIPES)) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    subCategoryFragment = new SubCategoryFragment();
                    subCategoryFragment.setModel(listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition), RECIPES);
                    mainActivity.addFragment(subCategoryFragment, true, false);
                } else if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase("Tutorial")) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    subCategoryFragment = new SubCategoryFragment();
                    subCategoryFragment.setModel(listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition), TUTORIALS);
                    mainActivity.addFragment(subCategoryFragment, true, false);

                } else if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(CLEANING)) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    subCategoryFragment = new SubCategoryFragment();
                    subCategoryFragment.setModel(listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition), CLEANING);
                    mainActivity.addFragment(subCategoryFragment, true, false);


                } else if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(BLOG)) {
                    mainActivity.closeDrawer();
                    mainActivity.clearBackStack();
                    subCategoryFragment = new SubCategoryFragment();
                    subCategoryFragment.setModel(listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition), BLOG);
                    mainActivity.addFragment(subCategoryFragment, true, false);

                }

//                switch (listDataHeader.get(groupPosition)) {
//                    case RECIPES:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        subCategoryFragment = new SubCategoryFragment();
//                        subCategoryFragment.setModel(listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), RECIPES);
//                        mainActivity.addFragment(subCategoryFragment, true, false);
//                        break;
//                    case TUTORIALS:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        subCategoryFragment = new SubCategoryFragment();
//                        subCategoryFragment.setModel(listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), TUTORIALS);
//                        mainActivity.addFragment(subCategoryFragment, true, false);
//
//
//                        break;
//                    case CLEANING:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        subCategoryFragment = new SubCategoryFragment();
//                        subCategoryFragment.setModel(listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), CLEANING);
//                        mainActivity.addFragment(subCategoryFragment, true, false);
//
//
//                        break;
//                    case BLOG:
//                        mainActivity.closeDrawer();
//                        mainActivity.clearBackStack();
//                        subCategoryFragment = new SubCategoryFragment();
//                        subCategoryFragment.setModel(listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), BLOG);
//                        mainActivity.addFragment(subCategoryFragment, true, false);
//
//                        break;
//
//                }


                return false;
            }
        });


        // preparing list data
        addHeaderFooterView();

    }

    private void addHeaderFooterView() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Header View
        View headerView = inflater.inflate(R.layout.nav_header_item_layout, null, false);
        TextView tvHome = (TextView) headerView.findViewById(R.id.home);

        // Footer View
        View footerView = inflater.inflate(R.layout.nav_footer_items_layout, null, false);
        TextView tvSavedRecipes = (TextView) footerView.findViewById(R.id.savedrecipes);
        TextView tvCookingGuides = (TextView) footerView.findViewById(R.id.cookingGuides);
        TextView tvRecentlyViewed = (TextView) footerView.findViewById(R.id.recentlyViewed);
        TextView tvMyFavoriteRecipes = (TextView) footerView.findViewById(R.id.myFavoriteRecipes);
        TextView tvMyReviews = (TextView) footerView.findViewById(R.id.myReviews);
        TextView tvLogout = (TextView) footerView.findViewById(R.id.logout);

        int lang = preferenceHelper.getSelectedLanguage();
        switch (lang) {
            case ENGLISH:
            default:
                tvHome.setText(getString(R.string.home_en));
                tvSavedRecipes.setText(getString(R.string.my_saved_recipes_en));
                tvCookingGuides.setText(getString(R.string.cooking_guides_en));
                tvRecentlyViewed.setText(getString(R.string.recently_viewed_en));
                tvMyFavoriteRecipes.setText(getString(R.string.my_favorite_recipes_en));
                tvMyReviews.setText(getString(R.string.my_reviews_en));

                tvHome.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tvSavedRecipes.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tvCookingGuides.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tvRecentlyViewed.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tvMyFavoriteRecipes.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tvMyReviews.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                tvLogout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                break;

            case URDU:
                tvHome.setText(getString(R.string.home_ur));
                tvSavedRecipes.setText(getString(R.string.my_saved_recipes_ur));
                tvCookingGuides.setText(getString(R.string.cooking_guides_ur));
                tvRecentlyViewed.setText(getString(R.string.recently_viewed_ur));
                tvMyFavoriteRecipes.setText(getString(R.string.my_favorite_recipes_ur));
                tvMyReviews.setText(getString(R.string.my_reviews_ur));

                tvHome.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvSavedRecipes.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvCookingGuides.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvRecentlyViewed.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvMyFavoriteRecipes.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvMyReviews.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvLogout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                break;
        }

        expandableListView.addHeaderView(headerView); // Add view to list view as header view

        if (preferenceHelper.getLoginStatus() && (!preferenceHelper.getUserFood().getId().equals("293"))) {
            tvLogout.setText(lang == ENGLISH ? getString(R.string.log_out_en) : getString(R.string.log_out_ur));
        } else {
            footerView.getRootView().findViewById(R.id.savedrecipes).setVisibility(View.GONE);
            footerView.getRootView().findViewById(R.id.cookingGuides).setVisibility(View.GONE);
            footerView.getRootView().findViewById(R.id.recentlyViewed).setVisibility(View.GONE);
            footerView.getRootView().findViewById(R.id.myFavoriteRecipes).setVisibility(View.GONE);
            footerView.getRootView().findViewById(R.id.myReviews).setVisibility(View.GONE);
            tvLogout.setText(lang == ENGLISH ? getString(R.string.login_en) : getString(R.string.login_ur));
        }
        expandableListView.addFooterView(footerView); // Add view to list view as footer view

        tvHome.setOnClickListener(view -> {
            mainActivity.clearBackStack();
            mainActivity.addFragment(new FoodHomeFragment(RECIPES), true, false);

            //updateLeftDrawer("home");

        });

        tvLogout.setOnClickListener(view -> {

//            if (tvLogout.getText().toString().equals(getActivity().getResources().getString(R.string.logout))) {
//                mainActivity.prefHelper.putSelectedLanguage(-1);
//            }
            mainActivity.prefHelper.putUserToken(null);
            mainActivity.prefHelper.putUserFood(null);
            mainActivity.prefHelper.setLoginStatus(false);
            mainActivity.finish();
            mainActivity.showRegistrationActivity();

            // updateLeftDrawer("logout");

        });

        tvSavedRecipes.setOnClickListener(view -> {

            if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

            } else {
                mainActivity.clearBackStack();
                SavedRecipesFragment savedRecipesFragment = new SavedRecipesFragment();
                savedRecipesFragment.setType(SavedRecipesFragment.TYPE_SAVED);
                mainActivity.addFragment(savedRecipesFragment, true, false);
                // updateLeftDrawer("logout");
                //
            }

        });

        tvCookingGuides.setOnClickListener(view -> {

            if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(mainActivity, "Not implemented yet", Toast.LENGTH_SHORT).show();
                mainActivity.clearBackStack();
                SavedRecipesFragment savedRecipesFragment = new SavedRecipesFragment();
                savedRecipesFragment.setType(SavedRecipesFragment.TYPE_COOKING_GUIDES);
                mainActivity.addFragment(savedRecipesFragment, true, false);
            }

        });


        tvRecentlyViewed.setOnClickListener(view -> {

            if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

            } else {

                mainActivity.clearBackStack();
                SavedRecipesFragment savedRecipesFragment = new SavedRecipesFragment();
                savedRecipesFragment.setType(SavedRecipesFragment.TYPE_RECENT);
                mainActivity.addFragment(savedRecipesFragment, true, false);
                // updateLeftDrawer("logout");
                //
            }

        });

        tvMyFavoriteRecipes.setOnClickListener(view -> {

            if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(mainActivity, "Not implemented yet", Toast.LENGTH_SHORT).show();
                mainActivity.clearBackStack();
                SavedRecipesFragment savedRecipesFragment = new SavedRecipesFragment();
                savedRecipesFragment.setType(SavedRecipesFragment.TYPE_FAVORITE);
                mainActivity.addFragment(savedRecipesFragment, true, false);
            }

        });

        tvMyReviews.setOnClickListener(view -> {

            if (preferenceHelper.getUserFood().getAcct_type() == 4) {
                Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(mainActivity, "Not implemented yet", Toast.LENGTH_SHORT).show();
                mainActivity.clearBackStack();
                FoodMyReviewsFragment foodMyReviewsFragment = new FoodMyReviewsFragment();
                mainActivity.addFragment(foodMyReviewsFragment, true, false);
            }

        });
    }

    private int lastPosition = -1;

    private void confMenu() {
        String defaultProfilePicUrl = "http://kbae.com.au/images/no_user_image.png";
        setMenu();
        binder.txtUsername.setText(preferenceHelper.getUserFood().getName_en());
        switch (preferenceHelper.getUserFood().getAcct_type()) {
            case 1:
            case 2:
                if (preferenceHelper.getUserFood().getProfile_picture_path() != null) {
                    UIHelper.setImageWithGlide(mainActivity, binder.imgBackground, preferenceHelper.getUserFood().getProfile_picture_path());
                } else {
                    UIHelper.setImageWithGlide(mainActivity, binder.imgBackground, defaultProfilePicUrl);
                }

                break;
//            case 2:
//                UIHelper.setImageWithGlide(mainActivity, binder.imgBackground, preferenceHelper.getUserFood().getProfile_picture());
//
//                break;
            case 3:
                UIHelper.setImageWithGlide(mainActivity, binder.imgBackground,
                        "https://graph.facebook.com/" + preferenceHelper.getUserFood().getProfile_picture() + "/picture?type=large");
                break;

            case 4:
                UIHelper.setImageWithGlide(mainActivity, binder.imgBackground, preferenceHelper.getUserFood().getProfile_picture());
                break;

        }

        int lang = preferenceHelper.getSelectedLanguage();

        if (headerWrapper != null && headerWrapper.size() > 0) {
            String sourceString = "Home";

            if (headerWrapper.get(0).getCategories().size() > 0) {
                recipes.addAll(headerWrapper.get(0).getCategories());
                sourceString = lang == ENGLISH ? headerWrapper.get(0).getTitle_en() : headerWrapper.get(0).getTitle_ur();
                listDataHeader.add(sourceString);
                listDataChild.put(sourceString, recipes);

            }

            if (headerWrapper.size() >= 2) {


                if (headerWrapper.get(1).getSlug().equalsIgnoreCase("tutorial")) {

                    tutorials.addAll(headerWrapper.get(1).getCategories());
                    sourceString = lang == ENGLISH ? headerWrapper.get(1).getTitle_en() : headerWrapper.get(1).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, tutorials);

                } else if (headerWrapper.get(1).getSlug().equalsIgnoreCase("cleaning")) {

                    cleaning.addAll(headerWrapper.get(1).getCategories());

                    sourceString = lang == ENGLISH ? headerWrapper.get(1).getTitle_en() : headerWrapper.get(1).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, cleaning);
                } else if (headerWrapper.get(1).getSlug().equalsIgnoreCase("blog")) {

                    // blog.addAllToAdapter(headerWrapper.get(1).getCategories());

                    sourceString = lang == ENGLISH ? headerWrapper.get(1).getTitle_en() : headerWrapper.get(1).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, blog);
                }


            }


            if (headerWrapper.size() >= 3) {


                if (headerWrapper.get(2).getSlug().equalsIgnoreCase("tutorial")) {

                    tutorials.addAll(headerWrapper.get(2).getCategories());
                    sourceString = lang == ENGLISH ? headerWrapper.get(2).getTitle_en() : headerWrapper.get(2).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, tutorials);

                } else if (headerWrapper.get(2).getSlug().equalsIgnoreCase("cleaning")) {

                    cleaning.addAll(headerWrapper.get(2).getCategories());

                    sourceString = lang == ENGLISH ? headerWrapper.get(2).getTitle_en() : headerWrapper.get(2).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, cleaning);
                } else if (headerWrapper.get(2).getSlug().equalsIgnoreCase("blog")) {

                    // blog.addAllToAdapter(headerWrapper.get(2).getCategories());

                    sourceString = lang == ENGLISH ? headerWrapper.get(2).getTitle_en() : headerWrapper.get(2).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, blog);
                }


            }
            if (headerWrapper.size() >= 4) {


                if (headerWrapper.get(3).getSlug().equalsIgnoreCase("tutorial")) {

                    tutorials.addAll(headerWrapper.get(3).getCategories());
                    sourceString = lang == ENGLISH ? headerWrapper.get(3).getTitle_en() : headerWrapper.get(3).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, tutorials);

                } else if (headerWrapper.get(3).getSlug().equalsIgnoreCase("cleaning")) {

                    cleaning.addAll(headerWrapper.get(3).getCategories());

                    sourceString = lang == ENGLISH ? headerWrapper.get(3).getTitle_en() : headerWrapper.get(3).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, cleaning);
                } else if (headerWrapper.get(3).getSlug().equalsIgnoreCase("blog")) {

                    // blog.addAllToAdapter(headerWrapper.get(3).getCategories());

                    sourceString = lang == ENGLISH ? headerWrapper.get(3).getTitle_en() : headerWrapper.get(3).getTitle_ur();
                    listDataHeader.add(sourceString);
                    listDataChild.put(sourceString, blog);
                }


            }


        }

        listAdapter = new ExpandableListAdapter(mainActivity, preferenceHelper, headerWrapper, listDataHeader, listDataChild, expandableListView);

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

