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
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCategoryAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSubCategory;
import structure.com.foodportal.databinding.FragmentSubcategoryBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.fragment.FragmentSubcategory;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.CategorySliderWrapper;
import structure.com.foodportal.models.foodModels.FoodHomeModelWrapper;

public class SubCategoryFragment extends BaseFragment implements View.OnClickListener, SubCategoryListner {
    FoodSubCategory foodCategoryAdapter;
    FragmentSubcategoryBinding binding;
    CategorySlider categorySlider;

    ArrayList<CategorySlider> categorySliders;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subcategory, container, false);
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
        binding.rvSubCategory.setLayoutManager(mLayoutManager);
        binding.rvSubCategory.setAdapter(foodCategoryAdapter);
        binding.rvSubCategory.showShimmerAdapter();


    }

    public void setModel(CategorySlider categorySlider) {

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

    public void getCategories(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getSubCategory(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY);
        else if (LocalDataHelper.readFromFile(mainActivity, "SubCategory").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity, "SubCategory").equalsIgnoreCase("")) {

            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();


        } else {
            Gson g = new Gson();
            CategorySliderWrapper p = g.fromJson(LocalDataHelper.readFromFile(mainActivity, "SubCategory"), CategorySliderWrapper.class);
            setData(p.getData());

        }


    }

    public void setData(ArrayList<CategorySlider> categorySliderWrapper)  {

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
                categorySliders = ((CategorySliderWrapper) result).getData();

                if (categorySliders != null && categorySliders.size() > 0) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LocalDataHelper.writeToFile(result.toString(), mainActivity, "SubCategory");
                            setData(categorySliders);
                        }
                    });
                }else{
                    binding.rvSubCategory.setVisibility(View.GONE);
                    binding.nodatafound.setVisibility(View.VISIBLE);
                    binding.rvSubCategory.hideShimmerAdapter();
                }
                break;

        }

    }


    @Override
    public void onSubCategoryClick(int position) {

        RecipeFragment recipeFragment =new RecipeFragment();
        recipeFragment.setModel(categorySliders.get(position));
        mainActivity.addFragment(recipeFragment,true,true);

        //mainActivity.addFragment(new );


    }
}
