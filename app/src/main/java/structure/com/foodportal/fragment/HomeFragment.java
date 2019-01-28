package structure.com.foodportal.fragment;


import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.FeatureAdapter;
import structure.com.foodportal.adapter.FeaturedProductsAdapter;
import structure.com.foodportal.adapter.NearbyAdapter;
import structure.com.foodportal.customViews.CustomRecyclerView;
import structure.com.foodportal.databinding.FragmentHomeBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.DataLoadedListener;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.interfaces.ResturantClickInterface;
import structure.com.foodportal.models.AllCategory;
import structure.com.foodportal.models.CartModule.CartProductMainClass;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.City;
import structure.com.foodportal.models.Country;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.viewbinders.BrandsBinder;
import structure.com.foodportal.viewbinders.CategoryBinder;
import structure.com.foodportal.viewbinders.ProductItemBinder;
import structure.com.foodportal.webservice.Api_Response;
import structure.com.foodportal.webservice.WebApiRequest;

@SuppressWarnings("unchecked")
public class HomeFragment extends BaseFragment implements ResturantClickInterface, RecyclerItemClickListener {
    FragmentHomeBinding binding;
    FeatureAdapter featureAdapter;
    ArrayList<String> imgList;
    ArrayList<Integer> brandsList;
    ArrayList<AllCategory> categoriesList;
    NearbyAdapter nearbyAdapter;
    @BindView(R.id.rvForYou)
    CustomRecyclerView rvForYou;
    @BindView(R.id.rvJustIN)
    CustomRecyclerView rvJustIN;
    @BindView(R.id.rvTopViewed)
    CustomRecyclerView rvTopViewed;
    @BindView(R.id.rvCatogories)
    CustomRecyclerView rvCatogories;
    @BindView(R.id.rvBrands)
    CustomRecyclerView rvBrands;
    @BindView(R.id.tvViewCatogories)
    TextView tvViewCatogories;
    Unbinder unbinder;
    ArrayList<ProductModelAPI> homeProducts;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.etNewSearch)
    TextView etNewSearch;
    int currentCategoryTypeID = 0;

    FeaturedProductsAdapter featuredProductsAdapter;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.circlePageIndicator)
    CircleIndicator circleIndicator;

    DataLoadedListener dataLoadedListener;
    @BindView(R.id.spCountry)
    TextView spCountry;
    @BindView(R.id.spCity)
    TextView spCity;
    @BindView(R.id.btnApply)
    Button btnApply;
    @BindView(R.id.dismissView)
    View dismissView;
    @BindView(R.id.llFilter)
    LinearLayout llFilter;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private Country selectedCountry;
    private City selectedCity;
    String country, city;
    String countryID;
    int cityID =0;
    Titlebar titlebar;
    private RecyclerItemClickListener mProductItemClickListener = ((ent, position, id) -> {
        switch (id) {
            case R.id.itemProduct:
                ProductDetailFragment fragment = new ProductDetailFragment();
                fragment.setProductDetail((ProductModelAPI) ent, false);
                if (preferenceHelper.getLoginStatus()) {
                    if (((ProductModelAPI) ent).getUserId() == preferenceHelper.getUser().getId()) {
                        fragment.setProductDetail((ProductModelAPI) ent, true);
                    }
                }
                if (((ProductModelAPI) ent).getIs_view() == 0) {
                    ((ProductModelAPI) ent).setIs_view(1);
                    rvForYou.notifyItemChanged(position);
                    rvJustIN.notifyItemChanged(position);
                    rvTopViewed.notifyItemChanged(position);
                }
                mainActivity.replaceFragment(fragment, true, true);
                break;
            case R.id.itemBrand:
                willbeimplementedinfuture();
                break;
            case R.id.itemCategory:
                //  mainActivity.replaceFragment(ViewAllProducts.newInstance(((AllCategory) ent).getTitle(), ((AllCategory) ent).getId(), null), true, true);

                FragmentSubcategory fragmentSubcategory = new FragmentSubcategory();
                fragmentSubcategory.setCategory(((AllCategory) ent));
                mainActivity.replaceFragment(fragmentSubcategory, true, true);

                break;

            case R.id.btnFavorite:
                ProductModelAPI productModelAPI = (ProductModelAPI) ent;
                serviceHelper.enqueueCall(webService.addtoFavorite("" + productModelAPI.getId(), "" + preferenceHelper.getUser().getId()), AppConstant.ADD_TO_FAV);
                break;

        }
    });
    private ViewGroup viewgroup;

    public void setDataLoadedListener(DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
    }

    public HomeFragment() {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        // titlebar.resetView();
        this.titlebar = titlebar;
        titlebar.showTitlebar();
        //titlebar.setTitle(mainActivity.getResources().getString(R.string.homeTitle));
//        titlebar.showNotification(0);

        titlebar.showFilter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llFilter.getVisibility() == View.VISIBLE) {

                    UIHelper.animation(Techniques.FadeOut, 400, 0, llFilter);
                    llFilter.setVisibility(View.GONE);
                } else {
                    UIHelper.animation(Techniques.FadeIn, 400, 0, llFilter);
                    llFilter.setVisibility(View.VISIBLE);
                }

            }
        });
//        titlebar.setNotificationOnclickListener(view -> UIHelper.showToast(mainActivity, getString(R.string.implementdialog)));
        titlebar.showMenuButton(mainActivity);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, binding.getRoot());
        getCart();
        getCountries();
        new Handler().postDelayed(() -> {
            init();
            getHomeData();
        }, 100);

        featuredProductsAdapter = new FeaturedProductsAdapter(mainActivity);
        viewpager.setAdapter(featuredProductsAdapter);
        circleIndicator.setViewPager(viewpager);

        int spacingInPixels = mainActivity.getResources().getDimensionPixelSize(R.dimen.dp10);
        SpacesItemDecorationHome spacesItemDecorationHome = new SpacesItemDecorationHome(spacingInPixels);
        rvForYou.addItemDecoration(spacesItemDecorationHome);
        rvJustIN.addItemDecoration(spacesItemDecorationHome);
        rvTopViewed.addItemDecoration(spacesItemDecorationHome);

//
//        int resId = R.anim.animation_item;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(mainActivity, resId);
//        rvForYou.setLayoutAnimation(animation);
//        rvJustIN.setLayoutAnimation(animation);
//        rvTopViewed.setLayoutAnimation(animation);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getHomeData();
            }
        });
        return binding.getRoot();
    }

    private void getHomeData() {
        if (homeProducts != null && homeProducts.size() > 0) {
            bindHomeItemLists(homeProducts);
        } else {
            if (preferenceHelper.getLoginStatus()) {
                if (preferenceHelper.getUser() != null) {
                    serviceHelper.enqueueCall(webService.getAllProducts(null, null, null, null, null, preferenceHelper.getUser().getId()), WebServiceConstants.GET_ALL_PRODUCTS);
                } else {
                    serviceHelper.enqueueCall(webService.getAllProducts(null, null, null, null, null, 0), WebServiceConstants.GET_ALL_PRODUCTS);
                }
            } else {
                serviceHelper.enqueueCall(webService.getAllProducts(null, null, null, null, null, 0), WebServiceConstants.GET_ALL_PRODUCTS);
            }
        }


    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GET_ALL_PRODUCTS:


                homeProducts.clear();
                homeProducts.addAll((ArrayList<ProductModelAPI>) result);
                if (homeProducts.size() >= 4) {
                    featuredProductsAdapter.addAll(new ArrayList<>(homeProducts.subList(0, 4)));
                    featuredProductsAdapter.notifyDataSetChanged();
                }
//                bindHomeItemLists(homeProducts);
                setHomeCategorizedHome(AppConstant.ProductOn.RENT);

                break;

            case AppConstant.ADD_TO_FAV:
                break;

            case AppConstant.GET_COUNTRIES:
                countries = (ArrayList<Country>) result;
                break;

            case AppConstant.GET_CITIES:
                cities = (ArrayList<City>) result;
                break;

            case AppConstant.GET_CART:

                titlebar.showCart(((ArrayList<CartProductMainClass>) result).size()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainActivity.prefHelper.getLoginStatus()) {
                            mainActivity.replaceFragment(new CartFragment(), true, true);
                        } else {

                            UIHelper.showToast(mainActivity, mainActivity.getString(R.string.please_login_viewcart));

                        }
                    }
                });


        }
    }

    private void bindHomeItemLists(ArrayList<ProductModelAPI> result) {

        rvForYou.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener), new ArrayList(((result.size() == 1) || (result.size()==0)) ? result : result.subList(0, 2)), new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
        rvForYou.setNestedScrollingEnabled(false);
        rvForYou.setHasFixedSize(true);

        rvJustIN.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener), new ArrayList(((result.size() == 1) || (result.size()==0)) ? result : result.subList(0, 2)), new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
        rvJustIN.setNestedScrollingEnabled(false);

        if (result.size() > 4) {
            rvTopViewed.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener), new ArrayList(result.subList(2, 4)), new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
        } else if (result.size() > 2) {
            rvTopViewed.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener), new ArrayList(result.subList(0, 2)), new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
        } else if (result.size() == 1) {
            rvTopViewed.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener), new ArrayList(result.subList(0, 1)), new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
        }
        rvTopViewed.setNestedScrollingEnabled(false);

        if (dataLoadedListener != null) {
            dataLoadedListener.onDataLoaded();
        }

        mainActivity.showLoader();
        getParentCategory();
    }

    private void init() {
        mainActivity.showBottombar();
        new Handler().postDelayed(() -> setTitle(mainActivity.getTitleBar()), 1000);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.itemForRent), true);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.itemForSale), false);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setHomeCategorizedHome(AppConstant.ProductOn.RENT);
                        break;

                    case 1:
                        setHomeCategorizedHome(AppConstant.ProductOn.SALE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        brandsList = new ArrayList<>();
        brandsList.add(R.drawable.nikelogo);
        brandsList.add(R.drawable.toyotalogo);
        brandsList.add(R.drawable.catlogo);
        brandsList.add(R.drawable.audilogo);
        brandsList.add(R.drawable.kialogo);
        brandsList.add(R.drawable.adidas_logo);

        rvBrands.bindRecyclerView(new BrandsBinder(mProductItemClickListener), brandsList, new GridLayoutManager(getContext(), 3), new DefaultItemAnimator());
        rvBrands.setNestedScrollingEnabled(false);

        categoriesList = new ArrayList<>();
        homeProducts = new ArrayList<>();
    }

    private void setHomeCategorizedHome(int categoryTypeID) {
        this.currentCategoryTypeID = categoryTypeID;
        ArrayList<ProductModelAPI> homeList = new ArrayList<>();
        if (homeProducts != null && homeProducts.size() > 0) {
            for (int pos = 0; pos < homeProducts.size(); pos++) {
                if (homeProducts.get(pos).getProductOn() == categoryTypeID) {
                    homeList.add(homeProducts.get(pos));
                }
            }
            bindHomeItemLists(homeList);
        }
    }

    @Override
    public void onResturant_Click() {
        replaceFragment(new ResturantMenu(), true, true);
    }

    public void getParentCategory() {

        WebApiRequest.getInstance(mainActivity).genricObjectRequest(null, AppConstant.ALL_CATEGORYS, new WebApiRequest.APIRequestObjectCallBack() {
            @Override
            public void onSuccess(Api_Response response) {

                Category category = (Category) JsonHelpers.convertToModelClass(response.getResult(), Category.class);
                categoriesList.clear();
                categoriesList.addAll(category.getCategories());

                int spacingInPixels = mainActivity.getResources().getDimensionPixelSize(R.dimen.dp1);
                rvCatogories.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

                rvCatogories.bindRecyclerView(new CategoryBinder(mProductItemClickListener), categoriesList, new GridLayoutManager(getContext(), 4), new DefaultItemAnimator());
                rvCatogories.setHasFixedSize(true);
                rvCatogories.setNestedScrollingEnabled(false);
                mainActivity.hideLoader();
            }

            @Override
            public void onError() {
                mainActivity.hideLoader();
            }

            @Override
            public void onNoNetwork() {
                mainActivity.hideLoader();
            }
        });


    }

    @Override
    public void onItemViewClicked(Object ent, int position, int id) {


    }

    @OnClick({R.id.spCountry, R.id.spCity, R.id.btnApply, R.id.dismissView, R.id.ivSearch, R.id.tvViewForYou, R.id.tvViewJustIn, R.id.tvViewTopViewed, R.id.tvViewCatogories, R.id.tvViewBrands, R.id.etSearch, R.id.llSearch, R.id.etNewSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvViewForYou:
                mainActivity.replaceFragment(ViewAllProducts.newInstance("Just For You", currentCategoryTypeID, null, null, false), true, true);
                break;
            case R.id.tvViewJustIn:
                mainActivity.replaceFragment(ViewAllProducts.newInstance("Just In", currentCategoryTypeID, null, null, false), true, true);
                break;
            case R.id.tvViewTopViewed:
                mainActivity.replaceFragment(ViewAllProducts.newInstance("Top Viewed", currentCategoryTypeID, null, null, false), true, true);
                break;
            case R.id.tvViewCatogories:
                mainActivity.replaceFragment(new FragmentCategory(), true, true);
                break;
            case R.id.tvViewBrands:
                break;

            case R.id.ivSearch:
            case R.id.llSearch:
            case R.id.etSearch:
            case R.id.etNewSearch:
//                UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.implementdialog));
                mainActivity.replaceFragment(ViewAllProducts.newInstance(mainActivity.getResources().getString(R.string.searchHere), currentCategoryTypeID, null, null, false), true, false);
                break;


            case R.id.btnApply:

                if (validatefilter()) {

                  if( preferenceHelper.getUser()!=null) {
                      serviceHelper.enqueueCall(webService.getAllProducts(countryID, cityID, null, null, null, preferenceHelper.getUser().getId()), WebServiceConstants.GET_ALL_PRODUCTS);

                  }else
                  {
                      serviceHelper.enqueueCall(webService.getAllProducts(countryID, cityID, null, null, null, null), WebServiceConstants.GET_ALL_PRODUCTS);


                  }
                  UIHelper.animation(Techniques.FadeOut, 400, 0, llFilter);
                    binding.llFilter.setVisibility(View.GONE);
                }


                break;
            case R.id.dismissView:
                UIHelper.animation(Techniques.FadeOut, 400, 0, llFilter);
                llFilter.setVisibility(View.GONE);
                break;

            case R.id.spCountry:
                openCountries();
                break;

            case R.id.spCity:
                openCities();
                break;
        }
    }

    private boolean validatefilter() {

        if (countryID == null) {
            UIHelper.showToast(getActivity(), getString(R.string.select_country));
            return false;
        }
        if (cityID == 0) {
            UIHelper.showToast(getActivity(), getString(R.string.select_city));
            return false;
        }
        return true;


    }

    private void openCountries() {
        if (countries != null && countries.size() > 0) {
            ArrayList<String> onlynames = new ArrayList<>();
            for (int i = 0; i < countries.size(); i++) {
                onlynames.add(countries.get(i).getName());
            }
            DialogFactory.listDialog(mainActivity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    spCountry.setText(onlynames.get(i));
                    selectedCountry = countries.get(i);
                    country = onlynames.get(i);
                    countryID = countries.get(i).getCode();
                    city = "";
                    spCity.setText(mainActivity.getResources().getString(R.string.select_city));
                    getCities(selectedCountry.getCode());
                }
            }, mainActivity.getResources().getString(R.string.select_country), onlynames);
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_country_found));
        }
    }

    private void openCities() {
        if (cities != null && cities.size() > 0) {
            ArrayList<String> onlynames = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                onlynames.add(cities.get(i).getName());
            }
            DialogFactory.listDialog(mainActivity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    spCity.setText(onlynames.get(i));
                    selectedCity = cities.get(i);
                    city = onlynames.get(i);
                    cityID =cities.get(i).getiD();
                }
            }, mainActivity.getResources().getString(R.string.select_city), onlynames);
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_city_found));
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.top = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 1 && parent.getChildLayoutPosition(view) == 3) {
                outRect.right = space;
            } else {
                outRect.right = 0;
            }
        }
    }

    public static class SpacesItemDecorationAllSideEqual extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecorationAllSideEqual(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.top = space;
            outRect.bottom = space;
            outRect.right = space;
        }
    }

    public static class SpacesItemDecorationHome extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecorationHome(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.left = space;
//            outRect.top = space;
//            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.right = space;
            } else if (parent.getChildLayoutPosition(view) == 1) {
                outRect.left = space;
            }
        }
    }

    public void getCountries() {
        serviceHelper.enqueueCall(webService.getCountires(), AppConstant.GET_COUNTRIES);
    }

    public void getCities(String countryCode) {
        serviceHelper.enqueueCall(webService.getCities(countryCode), AppConstant.GET_CITIES);
    }

    private void getCart() {
    if(preferenceHelper.getLoginStatus())
        serviceHelper.enqueueCall(webService.getCart("" + preferenceHelper.getUser().getId()), AppConstant.GET_CART);

    }

}
