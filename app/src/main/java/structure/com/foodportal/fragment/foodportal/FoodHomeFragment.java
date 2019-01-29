package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCategoryAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodIngredientsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.databinding.FragmentHomefoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.FoodHomeModelWrapper;

public class FoodHomeFragment extends BaseFragment implements View.OnClickListener {


    FragmentHomefoodBinding binding;
    FoodCategoryAdapter foodCategoryAdapter;
    ArrayList<CategorySlider> categorySliders;
    String story_slug ="";
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
        foodCategoryAdapter = new FoodCategoryAdapter(categorySliders, mainActivity);
        binding.rvCategoryslider.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategoryslider.setAdapter(foodCategoryAdapter);
        binding.cvRecipe.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cvRecipe:


                serviceHelper.enqueueCall(webService.getfooddetail(story_slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);


                break;


        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        titlebar.setTitle(getString(R.string.cooking_food));
        titlebar.showMenuButton(mainActivity);


    }


    public void gethomeDetails() {

        serviceHelper.enqueueCall(webService.gethome(), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME);
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME:


                FoodHomeModelWrapper foodhomeModel = (FoodHomeModelWrapper) JsonHelpers.convertToModelClass(result, FoodHomeModelWrapper.class);

                if (foodhomeModel != null) {


                    setData(foodhomeModel);

                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:

                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);

                if (foodDetailModel != null) {

                    FoodDetailFragment detailFragment= new FoodDetailFragment();
                    detailFragment.setFoodDetailModel(foodDetailModel);
                    mainActivity.addFragment(detailFragment,true,true);

                //    setData(foodDetailModel.getData());

                }


                break;

        }
    }


    public void setData(FoodHomeModelWrapper foodHomeModelWrapper) {
        story_slug= foodHomeModelWrapper.getBanner().get(0).getSlug();
        binding.tvRecipename.setText(foodHomeModelWrapper.getBanner().get(0).getTitle_en());
        UIHelper.setImageWithGlide(mainActivity, binding.ivBanner,
                foodHomeModelWrapper.getBanner().get(0).getGallery().
                        getPhotos().get(0).getImage_path());


        categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
        foodCategoryAdapter.notifyDataSetChanged();


    }
}

