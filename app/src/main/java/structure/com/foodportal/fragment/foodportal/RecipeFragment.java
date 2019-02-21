package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSubCategory;
import structure.com.foodportal.databinding.FragmentRecipeBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.CategorySliderWrapper;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.RecipeWrapper;

public class RecipeFragment  extends BaseFragment implements SubCategoryListner{


    FoodSubCategory foodCategoryAdapter;

    CategorySlider categorySlider;

    ArrayList<CategorySlider> categorySliders;

    FragmentRecipeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
        setListners();
        mainActivity.hideBottombar();

        if (categorySlider != null) {


            getCategories(categorySlider.getCategory_slug());

        }

        return binding.getRoot();
    }

    private void setListners() {



        categorySliders = new ArrayList<>();
        foodCategoryAdapter = new FoodSubCategory(categorySliders, mainActivity, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mainActivity, 2);
        binding.rvSubCategoryRecipe.setLayoutManager(mLayoutManager);
        binding.rvSubCategoryRecipe.setAdapter(foodCategoryAdapter);
        binding.rvSubCategoryRecipe.showShimmerAdapter();


    }

    public void setModel(CategorySlider categorySlider) {

        this.categorySlider = categorySlider;

    }

    Titlebar titlebar;
    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showBackButton(mainActivity);
        this.titlebar=titlebar;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showMenuButton(mainActivity);

    }

    public void getCategories(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getRecipeCategory(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY_RECIPE);
        else if (LocalDataHelper.readFromFile(mainActivity, "RecipeCategory").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity, "RecipeCategory").equalsIgnoreCase("")) {

            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();


        } else {
            Gson g = new Gson();
            CategorySliderWrapper p = g.fromJson(LocalDataHelper.readFromFile(mainActivity, "RecipeCategory"), CategorySliderWrapper.class);
            setData(p.getData());

        }


    }

    public void setData(ArrayList<CategorySlider> categorySliderWrapper) {

        // categorySliders.addAll(categorySliderWrapper);


        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foodCategoryAdapter.addAll(categorySliderWrapper);
                binding.rvSubCategoryRecipe.hideShimmerAdapter();
            }
        });


        binding.rvSubCategoryRecipe.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        binding.rvSubCategoryRecipe.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < binding.rvSubCategoryRecipe.getChildCount(); i++) {
                            View v = binding.rvSubCategoryRecipe.getChildAt(i);
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
            case AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY_RECIPE:
                categorySliders = ((RecipeWrapper) result).getSection_list();

                if (categorySliders != null && categorySliders.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LocalDataHelper.writeToFile(result.toString(), mainActivity, "RecipeCategory");
                            setData(categorySliders);
                        }
                    });
                }else{
                    binding.nodatafound.setVisibility(View.VISIBLE);
                    binding.rvSubCategoryRecipe.setVisibility(View.GONE);
                    binding.rvSubCategoryRecipe.hideShimmerAdapter();
                }


                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:

                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodDetailModel != null) {

                    LocalDataHelper.writeToFile(result.toString(), mainActivity,"Detail");
                    FoodDetailFragment detailFragment = new FoodDetailFragment();
                    detailFragment.setFoodDetailModel(foodDetailModel);
                    mainActivity.addFragment(detailFragment, true, true);
                    //    setData(foodDetailModel.getData());

                }


                break;




        }

    }


    @Override
    public void onSubCategoryClick(int position) {


        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(categorySliders.get(position).getSlug()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
        else if (LocalDataHelper.readFromFile(mainActivity,"Detail").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity,"Detail").equalsIgnoreCase("")) {

            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();


        } else {
            Gson g = new Gson();
            FoodDetailModelWrapper foodDetailModel = g.fromJson(LocalDataHelper.readFromFile(mainActivity,"Detail"), FoodDetailModelWrapper.class);
            FoodDetailFragment detailFragment = new FoodDetailFragment();
            detailFragment.setFoodDetailModel(foodDetailModel);
            mainActivity.addFragment(detailFragment, true, true);

        }


    }
}