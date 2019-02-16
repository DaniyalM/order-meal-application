package structure.com.foodportal.fragment.foodportal;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.AbsListViewBindingAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodBannerAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodBetterForBitesAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCategoryAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodFeaturedAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.databinding.FragmentHomefoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodBannerListner;
import structure.com.foodportal.models.foodModels.Banner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.FoodHomeModelWrapper;
import structure.com.foodportal.models.foodModels.Photos;
import structure.com.foodportal.models.foodModels.Sections;

public class FoodHomeFragment extends BaseFragment implements View.OnClickListener, FoodBannerListner {


    FragmentHomefoodBinding binding;

    FoodCategoryAdapter foodCategoryAdapter;
    FoodPopularRecipeAdapter foodPopularRecipeAdapter;
    FoodFeaturedAdapter foodFeaturedAdapter;
    FoodBetterForBitesAdapter foodBetterForBitesAdapter;
    FoodBannerAdapter foodBannerAdapter;
    ArrayList<CategorySlider> categorySliders;
    ArrayList<Banner> banners;
    ArrayList<Sections> sectionsPopular;
    ArrayList<Sections> sectionsFeatured;
    ArrayList<Sections> sectionsBetterForBites;
    String story_slug = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homefood, container, false);
        setListners();
        mainActivity.hideBottombar();


        gethomeDetails();
        return binding.getRoot();
    }

    private void setListners() {
        //  mainActivity.getleftSidemmenu().setListner(preferenceHelper);
        //binding.btnView.setonClickListner(this);
        categorySliders = new ArrayList<>();
        sectionsPopular = new ArrayList<>();
        sectionsFeatured = new ArrayList<>();
        sectionsBetterForBites = new ArrayList<>();
        banners = new ArrayList<>();

        foodCategoryAdapter = new FoodCategoryAdapter(categorySliders, mainActivity);
        foodBannerAdapter = new FoodBannerAdapter(banners, mainActivity, this);
        foodPopularRecipeAdapter = new FoodPopularRecipeAdapter(sectionsPopular, mainActivity);
        foodFeaturedAdapter = new FoodFeaturedAdapter(sectionsFeatured, mainActivity);
        foodBetterForBitesAdapter = new FoodBetterForBitesAdapter(sectionsBetterForBites, mainActivity);


        binding.rvCategoryslider.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        binding.rvPopularRecipe.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.rvFeaturedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.slider.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.rvBetterforBites.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));


        binding.rvCategoryslider.setAdapter(foodCategoryAdapter);
        binding.rvPopularRecipe.setAdapter(foodPopularRecipeAdapter);
        binding.rvFeaturedRecipes.setAdapter(foodFeaturedAdapter);
        binding.slider.setAdapter(foodBannerAdapter);
        binding.rvBetterforBites.setAdapter(foodBetterForBitesAdapter);


        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.slider);

        binding.cvRecipe.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvRecipe:

                break;
        }
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        //titlebar.setTitle(getString(R.string.cooking_food));
        titlebar.showMenuButton(mainActivity);


    }

    public void gethomeDetails() {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.gethome(), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME);
        else if (LocalDataHelper.readFromFile(mainActivity,"Home").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity,"Home").equalsIgnoreCase("")) {

            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();


        } else {
            Gson g = new Gson();
            FoodHomeModelWrapper p = g.fromJson(LocalDataHelper.readFromFile(mainActivity,"Home"), FoodHomeModelWrapper.class);
             setData(p);

        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME:
                FoodHomeModelWrapper foodhomeModel = (FoodHomeModelWrapper) JsonHelpers.convertToModelClass(result, FoodHomeModelWrapper.class);
                if (foodhomeModel != null) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            LocalDataHelper.writeToFile(result.toString(), mainActivity,"Home");
                            setData(foodhomeModel);

                        }
                    });


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



    public void setData(FoodHomeModelWrapper foodHomeModelWrapper) {

        story_slug = foodHomeModelWrapper.getBanner().get(0).getSlug();

        sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
        sectionsFeatured.addAll(foodHomeModelWrapper.getSection().get(1).getSection_list());
        sectionsBetterForBites.addAll(foodHomeModelWrapper.getSection().get(3).getSection_list());
        categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
        banners.addAll(foodHomeModelWrapper.getBanner());

        foodBannerAdapter.notifyDataSetChanged();
        foodCategoryAdapter.notifyDataSetChanged();
        foodPopularRecipeAdapter.notifyDataSetChanged();
        foodFeaturedAdapter.notifyDataSetChanged();
        foodBetterForBitesAdapter.notifyDataSetChanged();


        YoYo.with(Techniques.FadeOut).duration(1000).playOn(binding.viewShimmerCategorySlider);
        YoYo.with(Techniques.FadeOut).duration(1000).playOn(binding.viewShimmerbanner);

        binding.viewShimmerCategorySlider.setVisibility(View.GONE);
        binding.viewShimmerbanner.setVisibility(View.GONE);


        YoYo.with(Techniques.FadeIn).duration(1000).playOn(binding.rvCategoryslider);
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(binding.cvRecipe);

        binding.rvCategoryslider.setVisibility(View.VISIBLE);
        binding.cvRecipe.setVisibility(View.VISIBLE);

        binding.tvtipDay.setText(foodHomeModelWrapper.getSection().get(2).getSection_list().get(0).getContent_en());

    }

    @Override
    public void onBannerClick(int positon) {


        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(banners.get(positon).getSlug()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
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

