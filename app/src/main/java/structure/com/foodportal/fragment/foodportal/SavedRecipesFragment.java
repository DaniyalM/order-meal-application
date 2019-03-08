package structure.com.foodportal.fragment.foodportal;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.ConnectionService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import structure.com.foodportal.MyApplication;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCategoryAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSavedRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSubCategory;
import structure.com.foodportal.databinding.FragmentSubcategoryBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.FragmentSubcategory;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;

import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.foodInterfaces.NetworkListner;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.CategorySliderWrapper;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.FoodHomeModelWrapper;
import structure.com.foodportal.models.foodModels.SavedRecipe;
import structure.com.foodportal.models.foodModels.SavedStoriesWrapper;

public class SavedRecipesFragment extends BaseFragment implements View.OnClickListener, SubCategoryListner {
    FoodSavedRecipeAdapter foodCategoryAdapter;
    FragmentSubcategoryBinding binding;
    CategorySlider categorySlider;
    ArrayList<SavedRecipe> categorySliders;
    ConnectionService service;
    String mode = "Home";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subcategory, container, false);
        setListners();
        mainActivity.hideBottombar();

        getSavedRecipes();


        return binding.getRoot();
    }

    private void setListners() {


        categorySliders = new ArrayList<>();
        foodCategoryAdapter = new FoodSavedRecipeAdapter(categorySliders, mainActivity, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mainActivity, 2);
        binding.rvSubCategory.setLayoutManager(mLayoutManager);
        binding.rvSubCategory.setAdapter(foodCategoryAdapter);
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
            serviceHelper.enqueueCall(webService.getSavedRecipes(preferenceHelper.getUserFood().getId()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVED_RECIPES);
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

    public void setData(ArrayList<SavedRecipe> categorySliderWrapper) {

        // categorySliders.addAll(categorySliderWrapper);


        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foodCategoryAdapter.addAll(categorySliderWrapper);
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
            case AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY:
                categorySliders = ((SavedStoriesWrapper) result).getSaved_stories();

                if (categorySliders != null && categorySliders.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData(categorySliders);
                        }
                    });
                } else {
                    binding.rvSubCategory.setVisibility(View.GONE);
                    binding.nodatafound.setVisibility(View.VISIBLE);
                    binding.rvSubCategory.hideShimmerAdapter();
                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVED_RECIPES:
                categorySliders = ((SavedStoriesWrapper) result).getSaved_stories();

                if (categorySliders != null && categorySliders.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData(categorySliders);
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


        }

    }


    @Override
    public void onSubCategoryClick(int position) {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(categorySliders.get(position).getStories().getSlug()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
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
