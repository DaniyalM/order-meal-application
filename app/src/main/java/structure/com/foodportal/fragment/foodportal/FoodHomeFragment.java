package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.adapters.AbsListViewBindingAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.flaviofaria.kenburnsview.KenBurnsView;
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
import structure.com.foodportal.adapter.foodPortalAdapters.FoodMasterTechniquesAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodRecentlyViewedAdapter;
import structure.com.foodportal.databinding.FragmentHomefoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.DataLoadedListener;
import structure.com.foodportal.interfaces.foodInterfaces.FoodBannerListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Banner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.FoodHomeModelWrapper;
import structure.com.foodportal.models.foodModels.Photos;
import structure.com.foodportal.models.foodModels.Sections;

@SuppressLint("ValidFragment")
public class FoodHomeFragment extends BaseFragment implements View.OnClickListener, FoodBannerListner, FoodHomeListner {


    FragmentHomefoodBinding binding;
    FoodCategoryAdapter foodCategoryAdapter;
    FoodPopularRecipeAdapter foodPopularRecipeAdapter;
    FoodFeaturedAdapter foodFeaturedAdapter;
    FoodMasterTechniquesAdapter foodMasterTechniquesAdapter;
    FoodRecentlyViewedAdapter foodRecenltyViewedAdapter;
    FoodBetterForBitesAdapter foodBetterForBitesAdapter;
    FoodBannerAdapter foodBannerAdapter;
    ArrayList<CategorySlider> categorySliders;
    ArrayList<Banner> banners;
    ArrayList<Sections> sectionsPopular;
    ArrayList<Sections> sectionsFeatured;
    ArrayList<Sections> masterTechniques;
    ArrayList<Sections> recentlyViewed;
    ArrayList<Sections> sectionsBetterForBites;
    String story_slug = "";

    DataLoadedListener dataLoadedListener;
    String navSection = "Home";

    public void setDataLoadedListener(DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
    }

    public FoodHomeFragment() {


    }


    public FoodHomeFragment(String i) {

        this.navSection = i;

    }
    KenBurnsView ksp;
    KenBurnsView ksf;

    TextView tvp;
    TextView tvf;


    View vp;
    View vf;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homefood, container, false);
        setListners();
         vp = binding.getRoot().findViewById(R.id.bannerPopular);
         vf = binding.getRoot().findViewById(R.id.bannerFeatured);




         ksp = vp.findViewById(R.id.ivBanner);
         ksf = vf.findViewById(R.id.ivBanner);


         tvp = vp.findViewById(R.id.tvRecipename);
         tvf = vf.findViewById(R.id.tvRecipename);

        mainActivity.hideBottombar();
        binding.content.startRippleAnimation();
        if (dataLoadedListener != null) {
            dataLoadedListener.onDataLoaded();
        }
        gethomeDetails();
        return binding.getRoot();
    }

    private void setListners() {
        //  mainActivity.getleftSidemmenu().setListner(preferenceHelper);
        //binding.btnView.setonClickListner(this);
        binding.tvPopularRecipe.setOnClickListener(this);
        binding.tvFeaturedRecipes.setOnClickListener(this);
        categorySliders = new ArrayList<>();
        sectionsPopular = new ArrayList<>();
        sectionsFeatured = new ArrayList<>();
        masterTechniques = new ArrayList<>();
        recentlyViewed = new ArrayList<>();
        sectionsBetterForBites = new ArrayList<>();
        banners = new ArrayList<>();

        foodCategoryAdapter = new FoodCategoryAdapter(categorySliders, mainActivity, this);
        foodBannerAdapter = new FoodBannerAdapter(banners, mainActivity, this);
        foodPopularRecipeAdapter = new FoodPopularRecipeAdapter(sectionsPopular, mainActivity, this);
        foodFeaturedAdapter = new FoodFeaturedAdapter(sectionsFeatured, mainActivity, this);
        foodMasterTechniquesAdapter = new FoodMasterTechniquesAdapter(masterTechniques, mainActivity, this);
        foodBetterForBitesAdapter = new FoodBetterForBitesAdapter(sectionsBetterForBites, mainActivity, this);


        binding.rvCategoryslider.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        binding.rvPopularRecipe.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.rvFeaturedRecipes.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.rvtechniques.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.slider.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));
        binding.rvBetterforBites.setLayoutManager(new GridLayoutManager(mainActivity, 1, GridLayoutManager.HORIZONTAL, false));


        binding.rvtechniques.setAdapter(foodMasterTechniquesAdapter);
        binding.rvCategoryslider.setAdapter(foodCategoryAdapter);
        binding.rvPopularRecipe.setAdapter(foodPopularRecipeAdapter);
        binding.rvFeaturedRecipes.setAdapter(foodFeaturedAdapter);
        binding.slider.setAdapter(foodBannerAdapter);
        binding.rvBetterforBites.setAdapter(foodBetterForBitesAdapter);


        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.slider);

        binding.cvRecipe.setOnClickListener(this);
    }

    SeeAllRecipersFragment seeAllRecipersFragment;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvRecipe:

                break;

            case R.id.tvPopularRecipe:


                if(navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.HOME)|| navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES))
                {  seeAllRecipersFragment =new SeeAllRecipersFragment();
                seeAllRecipersFragment.setbool(true);
                mainActivity.addFragment(seeAllRecipersFragment,true,true);
                }
                break;

            case R.id.tvFeaturedRecipes:
                if(navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.HOME)|| navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES)){
                seeAllRecipersFragment =new SeeAllRecipersFragment();
                seeAllRecipersFragment.setbool(false);
                mainActivity.addFragment(seeAllRecipersFragment,true,true);
        }
                break;
        }
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        //titlebar.setTitle(getString(R.string.cooking_food));
        titlebar.showMenuButton(mainActivity);
        titlebar.showsearch(mainActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.replaceFragment(new FoodSearchFragment(), true, true);

            }
        });


    }

    public void gethomeDetails() {
        if (NetworkUtils.isNetworkAvailable(mainActivity))

            switch (navSection) {

                case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
                case AppConstant.FOODPORTAL_FOOD_DETAILS.HOME:
                    binding.cvSectionThree.setVisibility(View.VISIBLE);
                    serviceHelper.enqueueCall(webService.gethome(String.valueOf(preferenceHelper.getUserFood().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME);
                    break;

                case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:
                    serviceHelper.enqueueCall(webService.gettutorial("tutorial"), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME);
                    break;

                case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:
                    serviceHelper.enqueueCall(webService.gettutorial("cleaning"), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME);
                    break;

                case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
                    serviceHelper.enqueueCall(webService.gettutorial("blog"), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME);
                    break;


            }


//        else if (LocalDataHelper.readFromFile(mainActivity, "Home").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity, "Home").equalsIgnoreCase("")) {
//
//            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();
//
//
//        } else {
//            Gson g = new Gson();
//            FoodHomeModelWrapper p = g.fromJson(LocalDataHelper.readFromFile(mainActivity, "Home"), FoodHomeModelWrapper.class);
//            setData(p);
//
//        }

    }

    FoodHomeModelWrapper foodhomeModel;

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY:



                break;



            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME:
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME:


                foodhomeModel = (FoodHomeModelWrapper) JsonHelpers.convertToModelClass(result, FoodHomeModelWrapper.class);
                if (foodhomeModel != null) {

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            //  LocalDataHelper.writeToFile(result.toString(), mainActivity, "Home");
                            setData(foodhomeModel);

                        }
                    });


                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodDetailModel != null) {

                    LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
                    FoodDetailFragment detailFragment = new FoodDetailFragment();
                    detailFragment.setFoodDetailModel(foodDetailModel);
                    mainActivity.addFragment(detailFragment, true, true);
                    //    setData(foodDetailModel.getData());

                }
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


            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_BLOG_DETAILS:

                FoodDetailModelWrapper foodblog = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodblog != null) {

                    //     LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
                    FoodBlogDetailFragment detailFragment = new FoodBlogDetailFragment();
                    detailFragment.setFoodDetailModel(foodblog);
                    mainActivity.addFragment(detailFragment, true, true);
                    //    setData(foodDetailModel.getData());

                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SPECIAL_RECIPE:
                FoodDetailModelWrapper foodDetailModel2 = (FoodDetailModelWrapper) result;
                if (foodDetailModel2 != null) {

                    // LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
                    FoodSpecialDetailFragment detailFragment = new FoodSpecialDetailFragment();
                    detailFragment.setFoodDetailModelSpecial(foodDetailModel2.getStory(), foodDetailModel2.getAllReviews());
                    mainActivity.addFragment(detailFragment, true, true);
                    //    setData(foodDetailModel.getData());

                }
                break;

        }
    }

    public void setData(FoodHomeModelWrapper foodHomeModelWrapper) {


        if (foodHomeModelWrapper.getFeature_type() != null) {

            story_slug = foodHomeModelWrapper.getFeature_type().getSlug();
        } else {

            story_slug = foodHomeModelWrapper.getBanner().get(0).getSlug();

        }

        // YoYo.with(Techniques.FadeOut).duration(1000).playOn(binding.viewShimmerCategorySlider);
        //  YoYo.with(Techniques.FadeOut).duration(1000).playOn(binding.viewShimmerbanner);

        binding.viewShimmerCategorySlider.setVisibility(View.GONE);
        binding.viewShimmerbanner.setVisibility(View.GONE);


        //   YoYo.with(Techniques.FadeIn).duration(1000).playOn(binding.rvCategoryslider);
        //   YoYo.with(Techniques.FadeIn).duration(1000).playOn(binding.cvRecipe);

        binding.rvCategoryslider.setVisibility(View.VISIBLE);
        binding.cvRecipe.setVisibility(View.VISIBLE);


        switch (navSection) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.HOME:
            case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:



                UIHelper.setImageWithGlide(mainActivity,ksp,foodHomeModelWrapper.getSection().get(0).getSection_list().get(0).getFeatured_image_path());
                UIHelper.setImageWithGlide(mainActivity,ksf,foodHomeModelWrapper.getSection().get(1).getSection_list().get(0).getFeatured_image_path());
                tvp.setText(foodHomeModelWrapper.getSection().get(0).getSection_list().get(0).getTitle_en());
                tvf.setText(foodHomeModelWrapper.getSection().get(1).getSection_list().get(0).getTitle_en());
                //  binding.cvSectionFive.setVisibility(View.GONE);

                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
                binding.tvFeaturedRecipes.setText(foodHomeModelWrapper.getSection().get(1).getSection_name_en().replaceAll("_", " "));
                binding.tvBetterforBites.setText(foodHomeModelWrapper.getSection().get(3).getSection_name_en().replaceAll("_", " "));
                binding.tvtechniques.setText(/*foodHomeModelWrapper.getSection().get(4).getSection_name_en().replaceAll("_", " ")*/"Tutorials");
                binding.tvtipDay.setText(foodHomeModelWrapper.getSection().get(2).getSection_list().get(0).getContent_en());
                sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
                sectionsFeatured.addAll(foodHomeModelWrapper.getSection().get(1).getSection_list());
                sectionsBetterForBites.addAll(foodHomeModelWrapper.getSection().get(3).getSection_list());
                masterTechniques.addAll(foodHomeModelWrapper.getSection().get(4).getSection_list());
                categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
                banners.addAll(foodHomeModelWrapper.getBanner());


                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:

                binding.lltipoftheday.setVisibility(View.GONE);
                binding.cvSectionFive.setVisibility(View.GONE);

                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
                binding.tvFeaturedRecipes.setText(foodHomeModelWrapper.getSection().get(1).getSection_name_en().replaceAll("_", " "));
                binding.tvBetterforBites.setText(foodHomeModelWrapper.getSection().get(2).getSection_name_en().replaceAll("_", " "));

                sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
                sectionsFeatured.addAll(foodHomeModelWrapper.getSection().get(1).getSection_list());
                sectionsBetterForBites.addAll(foodHomeModelWrapper.getSection().get(2).getSection_list());
                categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
                banners.add(foodHomeModelWrapper.getFeature_type());

                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:


                binding.lltipoftheday.setVisibility(View.GONE);
                binding.cvSectionTwo.setVisibility(View.GONE);
                binding.cvSectionThree.setVisibility(View.GONE);
                binding.cvSectionFour.setVisibility(View.GONE);
                binding.cvSectionFive.setVisibility(View.GONE);


                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
                sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
                categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
                banners.addAll(foodHomeModelWrapper.getBanner());


                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
                binding.lltipoftheday.setVisibility(View.GONE);
                binding.cvSectionTwo.setVisibility(View.GONE);
                binding.cvSectionThree.setVisibility(View.GONE);
                binding.cvSectionFour.setVisibility(View.GONE);
                binding.cvSectionFive.setVisibility(View.GONE);
                binding.rvCategoryslider.setVisibility(View.GONE);

                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
                sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
                banners.add(foodHomeModelWrapper.getFeature_type());


                break;


        }
        foodBannerAdapter.notifyDataSetChanged();
        foodCategoryAdapter.notifyDataSetChanged();
        foodPopularRecipeAdapter.notifyDataSetChanged();
        foodFeaturedAdapter.notifyDataSetChanged();
        foodBetterForBitesAdapter.notifyDataSetChanged();
        foodMasterTechniquesAdapter.notifyDataSetChanged();


    }

    @Override
    public void onBannerClick(int positon) {


        next(banners.get(positon).getSlug());


    }

    @Override
    public void popularrecipe(int pos) {

        if (navSection.equalsIgnoreCase(AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG)) {
            next(foodhomeModel.getSection().get(0).getSection_list().get(pos).getSlug());
        } else {
            next(sectionsPopular.get(pos).getSlug());
        }
    }

    @Override
    public void featuredrecipe(int pos) {
        next(sectionsFeatured.get(pos).getSlug());
    }

    @Override
    public void betterforurbites(int pos) {


        if (AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES.equals(navSection) ||
                AppConstant.FOODPORTAL_FOOD_DETAILS.HOME.equals(navSection)) {

            getSpecialRecipe(sectionsBetterForBites.get(pos).getSlug(), preferenceHelper.getUserFood().getId());

        } else {

            next(sectionsBetterForBites.get(pos).getSlug());
        }


    }

    @Override
    public void recentlyViewed(int pos) {

        next(banners.get(pos).getSlug());

    }
    //next(sectionsBetterForBites.get(pos).getSlug());


    @Override
    public void categorySliderClick(int position) {
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setModel(categorySliders.get(position));
        mainActivity.addFragment(recipeFragment, true, true);
    }

    @Override
    public void masterTechniquesClick(int position) {

        serviceHelper.enqueueCall(webService.getfoodtutorialdetail(masterTechniques.get(position).getSlug()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS);

    }

    @Override
    public void onSaveRecipe(int slug) {
        if (preferenceHelper.getUserFood().getAcct_type() == 4) {
            Toast.makeText(mainActivity, "Please login to proceed", Toast.LENGTH_SHORT).show();

        } else {

            serviceHelper.enqueueCall(webService.sacvestory(String.valueOf(preferenceHelper.getUserFood().getId()), "story", "1", String.valueOf(slug)), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY);
        }

    }


    public void next(String slug) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            switch (navSection) {
                case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
                case AppConstant.FOODPORTAL_FOOD_DETAILS.HOME:

                    serviceHelper.enqueueCall(webService.getfooddetail(slug, String.valueOf(preferenceHelper.getUserFood().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
                    break;


                case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:
                    serviceHelper.enqueueCall(webService.getfoodtutorialdetail(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS);
                    break;

                case AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING:
                    serviceHelper.enqueueCall(webService.getfoodtutorialdetail(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS);
                    break;

                case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
                    serviceHelper.enqueueCall(webService.getfoodblog(slug), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_BLOG_DETAILS);
                    break;


            }


//        else if (LocalDataHelper.readFromFile(mainActivity, "Detail").equalsIgnoreCase(null) || LocalDataHelper.readFromFile(mainActivity, "Detail").equalsIgnoreCase("")) {
//
//            Toast.makeText(mainActivity, "No Data Found!", Toast.LENGTH_SHORT).show();
//
//
//        } else {
//            Gson g = new Gson();
//            FoodDetailModelWrapper foodDetailModel = g.fromJson(LocalDataHelper.readFromFile(mainActivity, "Detail"), FoodDetailModelWrapper.class);
//            FoodDetailFragment detailFragment = new FoodDetailFragment();
//            detailFragment.setFoodDetailModel(foodDetailModel);
//            mainActivity.addFragment(detailFragment, true, true);
//
//        }


    }

    public void getSpecialRecipe(String slug, String user_id) {

        serviceHelper.enqueueCall(webService.getfoodSpecialblog(slug, user_id), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SPECIAL_RECIPE);


    }

}

