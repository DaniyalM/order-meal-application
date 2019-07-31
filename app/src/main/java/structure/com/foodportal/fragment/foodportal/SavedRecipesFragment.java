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
import structure.com.foodportal.adapter.foodPortalAdapters.FoodRecipeAdapter;
import structure.com.foodportal.databinding.FragmentSubcategoryBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;

import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Recipe;

public class SavedRecipesFragment extends BaseFragment implements View.OnClickListener, SubCategoryListner {

    FoodRecipeAdapter foodRecipeAdapter;
    FragmentSubcategoryBinding binding;
    CategorySlider categorySlider;
    ArrayList<Recipe> recipes;
    ConnectionService service;
    String mode = "Home";
    Boolean savedRecipes = true;

    public void setSavedRecipes(boolean savedRecipes) {
        this.savedRecipes = savedRecipes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subcategory, container, false);
        setListners();
        mainActivity.hideBottombar();

        if (savedRecipes) {
            getSavedRecipes();
        } else {
            getRecentlyViewed();
        }
        return binding.getRoot();
    }

    private void getRecentlyViewed() {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueArrayCall(webService.getRecentlyViewedRecipes(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", "")), 1), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECENTLY_VIEWED_RECIPES);
    }

    private void setListners() {
        recipes = new ArrayList<>();
        foodRecipeAdapter = new FoodRecipeAdapter(recipes, mainActivity, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mainActivity, 2);
        binding.rvSubCategory.setLayoutManager(layoutManager);
        binding.rvSubCategory.setAdapter(foodRecipeAdapter);
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

    public void setData(ArrayList<Recipe> recipes) {

        // categorySliders.addAllToAdapter(categorySliderWrapper);

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foodRecipeAdapter.addAllToAdapter(recipes);
                binding.rvSubCategory.hideShimmerAdapter();
            }
        });

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

                recipes.addAll((ArrayList<Recipe>) result);

                if (recipes != null && recipes.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData(recipes);
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

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECENTLY_VIEWED_RECIPES:

                recipes.addAll((ArrayList<Recipe>) result);

                if (recipes != null && recipes.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData(recipes);
                        }
                    });
                } else {
                    binding.rvSubCategory.setVisibility(View.GONE);
                    binding.nodatafound.setVisibility(View.VISIBLE);
                    binding.rvSubCategory.hideShimmerAdapter();
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


}
