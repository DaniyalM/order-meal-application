package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCookingGuidesAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodRecipeAdapter;
import structure.com.foodportal.databinding.FragmentSubcategoryBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;

import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Recipe;
import structure.com.foodportal.models.foodModels.Sections;

public class SavedRecipesFragment extends BaseFragment implements View.OnClickListener, SubCategoryListner, FoodHomeListner {

    FoodRecipeAdapter foodRecipeAdapter;
    FoodCookingGuidesAdapter foodCookingGuidesAdapter;
    FragmentSubcategoryBinding binding;
    CategorySlider categorySlider;
    ArrayList<Recipe> recipes;
    ArrayList<Sections> tutorials;
    ConnectionService service;
    String mode = "Home";
    Boolean savedRecipes = true;

    private int mType;

    public void setType(int type) {
        mType = type;
    }

    public static final int TYPE_SAVED = 0, TYPE_RECENT = 1, TYPE_COOKING_GUIDES = 2, TYPE_FAVORITE = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subcategory, container, false);
        setListners();
        mainActivity.hideBottombar();

        if (mType == TYPE_SAVED) {
            getSavedRecipes();
        } else if (mType == TYPE_RECENT) {
            getRecentlyViewed();
        }
        else if (mType == TYPE_COOKING_GUIDES) {
            getCookingGuides();
        }
        else if (mType == TYPE_FAVORITE) {
            getFavoriteRecipes();
        }
        return binding.getRoot();
    }

    private void getCookingGuides() {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueArrayCall(webService.getCookingGuides(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", ""))), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_COOKING_GUIDES);
    }

    private void getRecentlyViewed() {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueArrayCall(webService.getRecentlyViewedRecipes(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", "")), 1), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECENTLY_VIEWED_RECIPES);
    }

    private void getFavoriteRecipes() {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueArrayCall(webService.getFavoriteRecipes(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", ""))), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_FAVORITE_RECIPES);
    }

    private void setListners() {
        recipes = new ArrayList<>();
        foodRecipeAdapter = new FoodRecipeAdapter(recipes, mainActivity, this);

        tutorials = new ArrayList<>();
        foodCookingGuidesAdapter = new FoodCookingGuidesAdapter(tutorials, getContext(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mainActivity, 2);
        binding.rvSubCategory.setLayoutManager(layoutManager);

        if (mType == TYPE_COOKING_GUIDES) {
            binding.rvSubCategory.setDemoLayoutReference(R.layout.layout_demo_grid_cooking_guide);
        }
        else {
            binding.rvSubCategory.setDemoLayoutReference(R.layout.layout_demo_grid);
        }

        binding.rvSubCategory.showShimmerAdapter();
    }

    public void setModel(CategorySlider categorySlider, String mode) {
        this.mode = mode;
        this.categorySlider = categorySlider;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showMenuButton(mainActivity);
    }

    @Override
    public void onClick(View view) {

    }

    public void getSavedRecipes() {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueArrayCall(webService.getSavedRecipes(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", ""))), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVED_RECIPES);
//            switch (mode){
//
//                case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
//                    serviceHelper.enqueueCall(webService.getSubCategory(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY);
//                    break;
//                case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:
//                    serviceHelper.enqueueCall(webService.getSubCategory(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY);
//                    break;
//                case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:
//                    serviceHelper.enqueueCall(webService.getSubCategory(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY);
//                    break;
//                case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
//                    serviceHelper.enqueueCall(webService.getSubCategory(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY);
//                    break;

        // }


//        else if (LocalDataHelper.readFromFile(mainActivity, "SubCategory").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity, "SubCategory").equalsIgnoreCase("")) {
//
//            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();
//
//
//        } else {
//            Gson g = new Gson();
//            CategorySliderWrapper p = g.fromJson(LocalDataHelper.readFromFile(mainActivity, "SubCategory"), CategorySliderWrapper.class);
//            setData(p.getData());
//
//        }
    }

    public void setData() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mType != TYPE_COOKING_GUIDES) {
                    binding.rvSubCategory.setAdapter(foodRecipeAdapter);
                    foodRecipeAdapter.addAllToAdapter(recipes);
                }
                else if (mType == TYPE_COOKING_GUIDES) {
                    binding.rvSubCategory.setAdapter(foodCookingGuidesAdapter);
                    foodCookingGuidesAdapter.addAllToAdapter(tutorials);
                }
                binding.rvSubCategory.hideShimmerAdapter();
            }
        });

        animateRecyclerView();
    }

    private void animateRecyclerView() {
        binding.rvSubCategory.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        binding.rvSubCategory.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < binding.rvSubCategory.getChildCount(); i++) {
                            View v = binding.rvSubCategory.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
//            case AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY:
//                categorySliders = ((SavedStoriesWrapper) result).getSaved_stories();
//
//                if (categorySliders != null && categorySliders.size() > 0) {
//
//                    mainActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
//                            setData(categorySliders);
//                        }
//                    });
//                } else {
//                    binding.rvSubCategory.setVisibility(View.GONE);
//                    binding.nodatafound.setVisibility(View.VISIBLE);
//                    binding.rvSubCategory.hideShimmerAdapter();
//                }
//                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVED_RECIPES:
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECENTLY_VIEWED_RECIPES:
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_FAVORITE_RECIPES:

                recipes.addAll((ArrayList<Recipe>) result);

                if (recipes != null && recipes.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData();
                        }
                    });
                } else {
                    binding.rvSubCategory.setVisibility(View.GONE);
                    binding.nodatafound.setVisibility(View.VISIBLE);
                    binding.rvSubCategory.hideShimmerAdapter();
                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_COOKING_GUIDES:

                tutorials.addAll((ArrayList<Sections>) result);

                if (tutorials != null && tutorials.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData();
                        }
                    });
                } else {
                    binding.rvSubCategory.setVisibility(View.GONE);
                    binding.nodatafound.setVisibility(View.VISIBLE);
                    binding.rvSubCategory.hideShimmerAdapter();
                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                FoodDetailFragment recipeFragment = new FoodDetailFragment();
                recipeFragment.setFoodDetailModel(foodDetailModel);
                mainActivity.addFragment(recipeFragment, true, true);
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS:
                FoodDetailModelWrapper foodDetailModeltwo = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodDetailModeltwo != null) {

                    //     LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
                    FoodTutorialDetailFragment detailFragment = new FoodTutorialDetailFragment();
                    detailFragment.setFoodDetailModel(foodDetailModeltwo);
                    mainActivity.addFragment(detailFragment, true, true);
                    //    setData(foodDetailModel.getData());

                }
                break;


        }

    }

    @Override
    public void ResponseFailure(String tag) {
        Log.e(SavedRecipesFragment.class.getSimpleName(), "ResponseFailure(): " + tag);
    }

    @Override
    public void onSubCategoryClick(int position) {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(recipes.get(position).getSlug(), String.valueOf(preferenceHelper.getUserFood().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
//        FoodDetailFragment recipeFragment = new FoodDetailFragment();
//        recipeFragment.setFoodDetailModel(categorySliders.get(position).getStories());
//        mainActivity.addFragment(recipeFragment, true, true);

        //mainActivity.addFragment(new );


    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onBlogClick(int pos) {

    }

    @Override
    public void popularrecipe(int pos) {

    }

    @Override
    public void recommendedrecipe(int pos) {

    }

    @Override
    public void featuredrecipe(int pos) {

    }

    @Override
    public void betterforurbites(int pos) {

    }

    @Override
    public void recentlyViewed(int pos) {

    }

    @Override
    public void categorySliderClick(int position) {

    }

    @Override
    public void masterTechniquesClick(int position) {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfoodtutorialdetail(tutorials.get(position).getSlug()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS);
    }

    @Override
    public void onSaveRecipe(int slug) {

    }

    @Override
    public void onFavoriteRecipe(int slug) {

    }
}
