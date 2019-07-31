package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodBannerAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodBetterForBitesAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodBlogAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCategoryAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodFeaturedAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodMasterTechniquesAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPopularRecipeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodRecentlyViewedAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodRecommendedRecipeAdapter;
import structure.com.foodportal.databinding.FragmentHomefoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.DataLoadedListener;
import structure.com.foodportal.interfaces.foodInterfaces.FoodBannerListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.models.foodModels.Banner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.FoodHomeModelWrapper;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.Sections;

@SuppressLint("ValidFragment")
public class FoodHomeFragment extends BaseFragment implements View.OnClickListener, FoodBannerListner, FoodHomeListner {


    FragmentHomefoodBinding binding;
    FoodCategoryAdapter foodCategoryAdapter;
    FoodPopularRecipeAdapter foodPopularRecipeAdapter;
    FoodRecommendedRecipeAdapter foodRecommendedRecipeAdapter;
    FoodFeaturedAdapter foodFeaturedAdapter;
    FoodMasterTechniquesAdapter foodMasterTechniquesAdapter;
    FoodRecentlyViewedAdapter foodRecenltyViewedAdapter;
    FoodBetterForBitesAdapter foodBetterForBitesAdapter;
    FoodBlogAdapter foodBlogAdapter;
    FoodBannerAdapter foodBannerAdapter;
    ArrayList<CategorySlider> categorySliders;
    ArrayList<Banner> banners;
    ArrayList<Sections> sectionsPopular;
    ArrayList<Sections> sectionsRecommended;
    ArrayList<Sections> sectionsFeatured;
    ArrayList<Sections> masterTechniques;
    ArrayList<Sections> recentlyViewed;
    ArrayList<Sections> sectionsBetterForBites;
    ArrayList<Sections> sectionsLatest;
    ArrayList<Sections> dummysection = new ArrayList<>();
    ArrayList<Sections> dummySectionLatest = new ArrayList<>();

    String story_slug = "";
    String type, slug;
    DataLoadedListener dataLoadedListener;
    String navSection = "Home";
    GridLayoutManager mLayoutManagerRecommended, mLayoutManagerLatest;
    private boolean isFirst = true;

    public void setDataLoadedListener(DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
    }


    public void setTypeandSlug(String type, String slug) {
        this.type = type;
        this.slug = slug;

    }
//    public FoodHomeFragment(String type, String slug) {
//
//        this.type = type;
//        this.slug = slug;
//
//    }


    public FoodHomeFragment(String i) {

        this.navSection = i;

    }

    KenBurnsView ksp;
    KenBurnsView ksf;

    TextView tvp;
    TextView tvf;

    CardView featurecv, popularcv, cvRecommendedSection, cvLatestSection, cvRecommendedImageCard;
    FrameLayout pframe, fframe;
    View vp;
    View vf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homefood, container, false);
        setListners();

        vp = binding.getRoot().findViewById(R.id.bannerPopular);
        vf = binding.getRoot().findViewById(R.id.bannerFeatured);

        pframe = vp.findViewById(R.id.homeBanner);
        fframe = vf.findViewById(R.id.homeBanner);


        popularcv = binding.getRoot().findViewById(R.id.popularCardView);
        featurecv = binding.getRoot().findViewById(R.id.featureCardView);
        cvRecommendedSection = binding.getRoot().findViewById(R.id.cvRecommendedSection);
        cvLatestSection = binding.getRoot().findViewById(R.id.cvLatestSection);
        cvRecommendedImageCard = binding.getRoot().findViewById(R.id.cvRecommendedImageCard);


        ksp = vp.findViewById(R.id.ivBanner);
        ksf = vf.findViewById(R.id.ivBanner);


        tvp = vp.findViewById(R.id.tvRecipename);
        tvf = vf.findViewById(R.id.tvRecipename);

        mainActivity.hideBottombar();
        // binding.content.startRippleAnimation();
        if (dataLoadedListener != null) {
            dataLoadedListener.onDataLoaded();
        }


        gethomeDetails();


        return binding.getRoot();
    }

    private final static int FADE_DURATION = 1000;

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setListners() {
        //  mainActivity.getleftSidemmenu().setListner(preferenceHelper);
        //binding.btnView.setonClickListner(this);
        binding.tvPopularRecipe.setOnClickListener(this);
        binding.tvFeaturedRecipes.setOnClickListener(this);
        categorySliders = new ArrayList<>();
        sectionsPopular = new ArrayList<>();
        sectionsRecommended = new ArrayList<>();
        sectionsFeatured = new ArrayList<>();
        masterTechniques = new ArrayList<>();
        recentlyViewed = new ArrayList<>();
        sectionsBetterForBites = new ArrayList<>();
        sectionsLatest = new ArrayList<>();
        banners = new ArrayList<>();

        foodCategoryAdapter = new FoodCategoryAdapter(categorySliders, mainActivity, this);
        foodBannerAdapter = new FoodBannerAdapter(banners, mainActivity, this);
        foodPopularRecipeAdapter = new FoodPopularRecipeAdapter(sectionsPopular, mainActivity, this);
        foodRecommendedRecipeAdapter = new FoodRecommendedRecipeAdapter(sectionsRecommended, mainActivity, this);
        foodFeaturedAdapter = new FoodFeaturedAdapter(sectionsFeatured, mainActivity, this);
        foodMasterTechniquesAdapter = new FoodMasterTechniquesAdapter(masterTechniques, mainActivity, this);
        foodBetterForBitesAdapter = new FoodBetterForBitesAdapter(sectionsBetterForBites, mainActivity, this);
        foodBlogAdapter = new FoodBlogAdapter(sectionsLatest, mainActivity, this);

        mLayoutManagerRecommended = new GridLayoutManager(mainActivity, 2);
       /* RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);*/
        //  binding.rvRecommendedRecipe.setItemAnimator(itemAnimator);
        binding.rvRecommendedRecipe.setLayoutManager(mLayoutManagerRecommended);
        binding.rvRecommendedRecipe.setAdapter(foodRecommendedRecipeAdapter);

        // ViewCompat.setNestedScrollingEnabled(binding.rvRecommendedRecipe, true);
        setPagination(mLayoutManagerRecommended, foodRecommendedRecipeAdapter.getItemCount() - 1);
        //binding.rvRecommendedRecipe.showShimmerAdapter();
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

        mLayoutManagerLatest = new GridLayoutManager(mainActivity, 2);
        binding.rvBlogs.setLayoutManager(mLayoutManagerLatest);
        binding.rvBlogs.setAdapter(foodBlogAdapter);

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


                if (navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.HOME) || navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES)) {
                    seeAllRecipersFragment = new SeeAllRecipersFragment();
                    seeAllRecipersFragment.setbool(true);
                    mainActivity.addFragment(seeAllRecipersFragment, true, true);
                }
                break;

            case R.id.tvFeaturedRecipes:
                if (navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.HOME) || navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES)) {
                    seeAllRecipersFragment = new SeeAllRecipersFragment();
                    seeAllRecipersFragment.setbool(false);
                    mainActivity.addFragment(seeAllRecipersFragment, true, true);
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
                    serviceHelper.enqueueCall(webService.gethome(String.valueOf(preferenceHelper.getUserFood().getId()), 16), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME);
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
        String forNUM = Tag;
        if (forNUM.matches(".*\\d.*")) {
            if (Tag.startsWith(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECOMMENDED)) {
                totalPagesRecommended = Integer.valueOf(forNUM.replaceAll("\\D+", ""));
            } else if (Tag.startsWith(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME)) {
                totalPagesLatest = Integer.valueOf(forNUM.replaceAll("\\D+", ""));
            }
            Tag = Tag.replaceAll("[0-9]", "");

        }

        switch (Tag) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECOMMENDED:

                dummysection.addAll(((Section) result).getSection_list());
//                for (int i = 0; i < dummysection.size(); i++) {
//
//                    foodRecommendedRecipeAdapter.insert(sectionsRecommended.size(),dummysection.get(i));
//                    //seeAllRecipesAdapter.notifyItemInserted(sections.size() - 1);
//                    // seeAllRecipesAdapter.notifyItemInserted(seeAllRecipesAdapter.getItemCount()+1);
//                }
//               // foodRecommendedRecipeAdapter.notifyItemRangeChanged(sectionsRecommended.size()>15?sectionsRecommended.size()-15:sectionsRecommended.size()-1,15);
//                dummysection.clear();
                for (int i = 0; i < dummysection.size(); i++) {

                    sectionsRecommended.add(dummysection.get(i));
                    //seeAllRecipesAdapter.notifyItemInserted(sections.size() - 1);
                    // seeAllRecipesAdapter.notifyItemInserted(seeAllRecipesAdapter.getItemCount()+1);
                }
                foodRecommendedRecipeAdapter.notifyItemRangeChanged(sectionsRecommended.size() > 15 ? sectionsRecommended.size() - 15 : sectionsRecommended.size() - 1, 15);
                dummysection.clear();


                if (mLayoutManagerRecommended != null)
                    mLayoutManagerRecommended.scrollToPositionWithOffset(scrollToRecommended, 0);

                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY:


                break;


            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME:

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


            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME:
                foodhomeModel = (FoodHomeModelWrapper) JsonHelpers.convertToModelClass(result, FoodHomeModelWrapper.class);

                if (!navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG)) {
                    if (foodhomeModel != null) {
                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //  LocalDataHelper.writeToFile(result.toString(), mainActivity, "Home");
                                setData(foodhomeModel);
                            }
                        });
                    }
                }
                else if (navSection.equals(AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG)) {
                    if (isFirst) {
                        if (foodhomeModel != null) {
                            mainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //  LocalDataHelper.writeToFile(result.toString(), mainActivity, "Home");
                                    setData(foodhomeModel);
                                }
                            });
                        }
                    }
                    else {
                        dummySectionLatest.addAll(foodhomeModel.getSection().get(0).getSection_list());
                        for (int i = 0; i < dummySectionLatest.size(); i++) {
                            sectionsLatest.add(dummySectionLatest.get(i));
                        }
                        foodBlogAdapter.notifyItemRangeChanged(sectionsLatest.size() > 15 ? sectionsLatest.size() - 15 : sectionsLatest.size() - 1, 15);
                        dummySectionLatest.clear();

                        if (mLayoutManagerLatest != null)
                            mLayoutManagerLatest.scrollToPositionWithOffset(scrollToLatest, 0);
                    }
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

    String popularslug;
    String featuredslug;

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
        setScaleAnimation(binding.rvCategoryslider);
        setScaleAnimation(binding.cvRecipe);
        binding.cvRecipe.setVisibility(View.VISIBLE);


        switch (navSection) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.HOME:
            case AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES:
                // cvRecommendedSection.setVisibility(View.VISIBLE);
                popularcv.setVisibility(View.VISIBLE);
                featurecv.setVisibility(View.VISIBLE);
                cvRecommendedSection.setVisibility(View.VISIBLE);
                cvLatestSection.setVisibility(View.GONE);
                // cvRecommendedImageCard.setVisibility(View.VISIBLE);
                popularslug = foodHomeModelWrapper.getSection().get(0).getSection_list().get(0).getSlug();
                featuredslug = foodHomeModelWrapper.getSection().get(1).getSection_list().get(0).getSlug();


                pframe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next(popularslug);
                    }
                });

                fframe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next(featuredslug);

                    }
                });

                UIHelper.setImageWithGlide(mainActivity, ksp, foodHomeModelWrapper.getSection().get(0).getSection_list().get(0).getFeatured_image_path());
                UIHelper.setImageWithGlide(mainActivity, ksf, foodHomeModelWrapper.getSection().get(1).getSection_list().get(0).getFeatured_image_path());
                tvp.setText(foodHomeModelWrapper.getSection().get(0).getSection_list().get(0).getTitle_en());
                tvf.setText(foodHomeModelWrapper.getSection().get(1).getSection_list().get(0).getTitle_en());
                //  binding.cvSectionFive.setVisibility(View.GONE);


                printList("ListPopularBefore", foodHomeModelWrapper.getSection().get(0).getSection_list());
                printList("ListFeaturedBefore", foodHomeModelWrapper.getSection().get(1).getSection_list());

                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
                binding.tvFeaturedRecipes.setText(foodHomeModelWrapper.getSection().get(1).getSection_name_en().replaceAll("_", " "));
                binding.tvBetterforBites.setText(foodHomeModelWrapper.getSection().get(3).getSection_name_en().replaceAll("_", " "));
                binding.tvtechniques.setText(/*foodHomeModelWrapper.getSection().get(4).getSection_name_en().replaceAll("_", " ")*/"Tutorials");
                binding.tvtipDay.setText(foodHomeModelWrapper.getSection().get(2).getSection_list().get(0).getContent_en());
                foodHomeModelWrapper.getSection().get(0).getSection_list().remove(0);
                sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
                foodHomeModelWrapper.getSection().get(1).getSection_list().remove(0);
                sectionsFeatured.addAll(foodHomeModelWrapper.getSection().get(1).getSection_list());
                sectionsBetterForBites.addAll(foodHomeModelWrapper.getSection().get(3).getSection_list());
                masterTechniques.addAll(foodHomeModelWrapper.getSection().get(4).getSection_list());
                // sectionsRecommended.addAllToAdapter(foodHomeModelWrapper.getSection().get(5).getSection_list());
                categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
                banners.addAll(foodHomeModelWrapper.getBanner());

                getRecommendedRecipes(0);

                printList("ListPopularAfter", sectionsPopular);
                printList("ListFeaturedAfter", sectionsFeatured);

                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS:

                binding.lltipoftheday.setVisibility(View.GONE);
                binding.cvSectionFive.setVisibility(View.GONE);

                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
                if (foodHomeModelWrapper.getSection().get(1).getSection_list().size() > 0) {

                    binding.tvFeaturedRecipes.setText(foodHomeModelWrapper.getSection().get(1).getSection_name_en().replaceAll("_", " "));
                } else {
                    binding.tvFeaturedRecipes.setVisibility(View.GONE);
                }
                binding.tvBetterforBites.setText(foodHomeModelWrapper.getSection().get(2).getSection_name_en().replaceAll("_", " "));

                sectionsPopular.addAll(foodHomeModelWrapper.getSection().get(0).getSection_list());
                sectionsFeatured.addAll(foodHomeModelWrapper.getSection().get(1).getSection_list());
                sectionsBetterForBites.addAll(foodHomeModelWrapper.getSection().get(2).getSection_list());
                categorySliders.addAll(foodHomeModelWrapper.getCategory_slider());
                banners.add(foodHomeModelWrapper.getFeature_type());
                foodPopularRecipeAdapter.setWidth(291);
                foodBetterForBitesAdapter.setWidth(291);

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
                foodPopularRecipeAdapter.setWidth(291);


                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG:
//                binding.lltipoftheday.setVisibility(View.GONE);
//                binding.cvSectionTwo.setVisibility(View.GONE);
//                binding.cvSectionThree.setVisibility(View.GONE);
//                binding.cvSectionFour.setVisibility(View.GONE);
//                binding.cvSectionFive.setVisibility(View.GONE);
//                binding.rvCategoryslider.setVisibility(View.GONE);
//
//                binding.tvPopularRecipe.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
//                sectionsPopular.addAllToAdapter(foodHomeModelWrapper.getSection().get(0).getSection_list());
//                banners.add(foodHomeModelWrapper.getFeature_type());
//                foodPopularRecipeAdapter.setWidth(291);

                setBlogPagination(mLayoutManagerLatest, foodBlogAdapter.getItemCount() - 1);

                binding.lltipoftheday.setVisibility(View.GONE);
                binding.cvSectionOne.setVisibility(View.GONE);
                binding.cvSectionTwo.setVisibility(View.GONE);
                binding.cvSectionThree.setVisibility(View.GONE);
                binding.cvSectionFour.setVisibility(View.GONE);
                binding.cvSectionFive.setVisibility(View.GONE);
                binding.rvCategoryslider.setVisibility(View.GONE);
                cvRecommendedSection.setVisibility(View.GONE);
                cvLatestSection.setVisibility(View.VISIBLE);

                binding.tvLatest.setText(foodHomeModelWrapper.getSection().get(0).getSection_name_en().replaceAll("_", " "));
//                sectionsLatest.addAllToAdapter(foodHomeModelWrapper.getSection().get(0).getSection_list());
                banners.add(foodHomeModelWrapper.getFeature_type());

                isFirst = false;

                loadMoreBlogs(0);

                break;


        }
        foodBannerAdapter.notifyDataSetChanged();
        foodCategoryAdapter.notifyDataSetChanged();
        foodPopularRecipeAdapter.notifyDataSetChanged();
        foodFeaturedAdapter.notifyDataSetChanged();
        foodBetterForBitesAdapter.notifyDataSetChanged();
        foodMasterTechniquesAdapter.notifyDataSetChanged();
        // foodRecommendedRecipeAdapter.notifyDataSetChanged();
        // foodBlogAdapter.notifyDataSetChanged();


        if (type != null) {

            switch (type) {

                case "recipe":
                    next(slug);
                    break;
                case "cleaning":
                    next(slug);
                    break;
                case "tutorial":
                    next(slug);
                    break;
                case "blog":
                    next(slug);
                    break;
                case "special_recipe":
                    getSpecialRecipe(slug, preferenceHelper.getUserFood().getId());//
                    break;


            }


        }

    }

    private List<Sections> printList(String tag, List<Sections> sectionsList) {
        for (int i = 0; i < sectionsList.size(); i++) {
            Log.d(tag, i + " -- " + sectionsList.get(i).getTitle_en());
        }
        return sectionsList;
    }

    int currentPageRecommended = 1, totalPagesRecommended, currentPageLatest = 1, totalPagesLatest;
    int scrollToRecommended = 0, scrollToLatest = 0;

    private synchronized void getRecommendedRecipes(int scrollPosition) {
        scrollToRecommended = scrollPosition;
        serviceHelper.enqueueCall(webService.getRecommendedRecipes(currentPageRecommended, 20), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECOMMENDED);
    }

    private synchronized void loadMoreBlogs(int scrollPosition) {
        scrollToLatest = scrollPosition;
        serviceHelper.enqueueCall(webService.gettutorial("blog", currentPageLatest), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME);

    }

    @Override
    public void onBannerClick(int positon) {

        if (banners.get(positon).getSpecial_recipes_id() > 0) {
            getSpecialRecipe(banners.get(positon).getSpecial_recipes_slug(), preferenceHelper.getUserFood().getId());
        } else {
            next(banners.get(positon).getSlug());
        }
    }

    @Override
    public void onBlogClick(int pos) {
        next(sectionsLatest.get(pos).getSlug());
    }

    @Override
    public void popularrecipe(int pos) {
        next(sectionsPopular.get(pos).getSlug());
    }

    @Override
    public void recommendedrecipe(int pos) {
        next(sectionsRecommended.get(pos).getSlug());
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
//            FoodDetailModelWrapper foodDetailModelii = g.fromJson(LocalDataHelper.readFromFile(mainActivity, "Detail"), FoodDetailModelWrapper.class);
//            FoodDetailFragment detailFragment = new FoodDetailFragment();
//            detailFragment.setFoodDetailModel(foodDetailModel);
//            mainActivity.addFragment(detailFragment, true, true);
//
//        }


    }

    public void getSpecialRecipe(String slug, String user_id) {
        serviceHelper.enqueueCall(webService.getfoodSpecialblog(slug, user_id), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SPECIAL_RECIPE);
    }

    private void setPagination(GridLayoutManager gridLayoutManager, final int i) {

        binding.nestedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.nestedScroll.getChildAt(binding.nestedScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.nestedScroll.getHeight() + binding.nestedScroll
                        .getScrollY()));

                if (diff == 0) {

                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == foodRecommendedRecipeAdapter.getItemCount() - 1) {

                        if (totalPagesRecommended > currentPageRecommended) {
                            currentPageRecommended++;
                            getRecommendedRecipes(gridLayoutManager.findLastCompletelyVisibleItemPosition() - 1);
                        }
                    }

                    // your pagination code
                }
            }
        });
    }

    private void setBlogPagination(GridLayoutManager gridLayoutManager, final int i) {

        binding.nestedScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.nestedScroll.getChildAt(binding.nestedScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.nestedScroll.getHeight() + binding.nestedScroll
                        .getScrollY()));

                if (diff == 0) {

                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == foodBlogAdapter.getItemCount() - 1) {

                        if (totalPagesLatest > currentPageLatest) {
                            currentPageLatest++;
                            loadMoreBlogs(gridLayoutManager.findLastCompletelyVisibleItemPosition() - 1);
                        }
                    }

                    // your pagination code
                }
            }
        });
    }

}
