package structure.com.foodportal.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import structure.com.foodportal.R;
import structure.com.foodportal.customViews.CustomRecyclerView;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.viewbinders.ProductItemBinder;

@SuppressWarnings("unchecked")
public class ViewAllProducts extends BaseFragment implements TextWatcher {
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.rvProducts)
    CustomRecyclerView rvProducts;
    Unbinder unbinder;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    private String title;
    private ArrayList<String> imgRest;
    private Integer categoryID, brandID, categoryTypeID;
    ArrayList<ProductModelAPI> allProducts;
    Titlebar titlebar;


    boolean fromCatogories =false;
    private RecyclerItemClickListener mProductItemClickListener = ((ent, position, id) -> {
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setProductDetail((ProductModelAPI) ent, false);
        if (preferenceHelper.getLoginStatus()) {
            if (((ProductModelAPI) ent).getUserId() == preferenceHelper.getUser().getId()) {
                fragment.setProductDetail((ProductModelAPI) ent, true);
            }
        }
        if(((ProductModelAPI) ent).getIs_view() == 0){
            ((ProductModelAPI) ent).setIs_view(1);
            rvProducts.notifyItemChanged(position);
        }
        UIHelper.hideSoftKeyboards(mainActivity);
        etSearch.setText("");
        mainActivity.replaceFragment(fragment, true, true);
    });
    private Integer subCategoryID;

    public static ViewAllProducts newInstance(String title, Integer categoryID, Integer brandID) {
        Bundle args = new Bundle();

        ViewAllProducts fragment = new ViewAllProducts();
        fragment.setArguments(args);
        fragment.setTitle(title);
        fragment.setBrandID(brandID);
        fragment.setCategoryID(categoryID);

        return fragment;
    }

    public static ViewAllProducts newInstance(String title, Integer categoryTypeID, Integer categoryID, Integer brandID,boolean fromCatogories) {
        Bundle args = new Bundle();

        ViewAllProducts fragment = new ViewAllProducts();
        fragment.setArguments(args);
        fragment.setTitle(title);
        fragment.setBrandID(brandID);
        fragment.setCategoryID(categoryID);
        fragment.setCategoryTypeID(categoryTypeID);
        fragment.setFromCatogories(fromCatogories);
        return fragment;
    }

    public boolean isFromCatogories() {
        return fromCatogories;
    }

    public void setFromCatogories(boolean fromCatogories) {
        this.fromCatogories = fromCatogories;
    }
    public Integer getCategoryTypeID() {
        return categoryTypeID;
    }

    public void setCategoryTypeID(Integer categoryTypeID) {
        this.categoryTypeID = categoryTypeID;
    }

    public void setSubCategory(Integer subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setBrandID(Integer brandID) {
        this.brandID = brandID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_all_products_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSearch.addTextChangedListener(this);
        allProducts = new ArrayList<>();
        if (preferenceHelper.getLoginStatus()) {
            if (preferenceHelper.getUser() != null) {
                serviceHelper.enqueueCall(webService.getAllProducts(null,null,brandID,categoryID, null, preferenceHelper.getUser().getId()), WebServiceConstants.GET_ALL_PRODUCTS);
            } else {
                serviceHelper.enqueueCall(webService.getAllProducts(null,null,brandID, categoryID,null, 0), WebServiceConstants.GET_ALL_PRODUCTS);
            }
        } else {
            serviceHelper.enqueueCall(webService.getAllProducts(null,null,brandID, categoryID,null, 0), WebServiceConstants.GET_ALL_PRODUCTS);
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.GET_ALL_PRODUCTS:
//                bindProductItemLists((ArrayList<ProductModelAPI>) result);
                allProducts.clear();
                allProducts.addAll((ArrayList<ProductModelAPI>) result);
                setHomeCategorizedHome((ArrayList<ProductModelAPI>) result);
                break;
        }
    }

    private void setHomeCategorizedHome(ArrayList<ProductModelAPI> homeProducts) {
            if(homeProducts.size()>0){
                rvProducts.setVisibility(View.VISIBLE);
                txtNoData.setVisibility(View.GONE);
            }else{
                rvProducts.setVisibility(View.GONE);
                txtNoData.setVisibility(View.VISIBLE);

            }
        ArrayList<ProductModelAPI> homeList = new ArrayList<>();
        if (homeProducts != null && homeProducts.size() > 0) {
            for (int pos = 0; pos < homeProducts.size(); pos++) {


                if(fromCatogories){
                            if(categoryTypeID != null || homeProducts.get(pos).getBrandId() != 0){
                                // if( homeProducts.get(pos).getCategoryId() != 0){
                                if (homeProducts.get(pos).getProductOn() == categoryTypeID ||
                                        brandID== AppConstant.CategoriesIds.CAR ||
                                        brandID == AppConstant.CategoriesIds.MOTORBIKE ||
                                        brandID == AppConstant.CategoriesIds.HEAVY_VEHICLES ||
                                        brandID== AppConstant.CategoriesIds.BOATS ||
                                        brandID == AppConstant.CategoriesIds.AUTO_PARTS ||
                                        brandID == AppConstant.CategoriesIds.PLATES ||

                                        brandID== AppConstant.CategoriesIds.HOME_APPLIANCES ||
                                        brandID== AppConstant.CategoriesIds.CAMERAS ||
                                        brandID == AppConstant.CategoriesIds.MOBILE_PHONES ||
                                        brandID == AppConstant.CategoriesIds.TELEVISIONS_SCREENS ||
                                        brandID == AppConstant.CategoriesIds.TABLETS_LAPTOPS ||
                                        brandID== AppConstant.CategoriesIds.DIGITAL ||

                                        brandID == AppConstant.CategoriesIds.APARTMENT ||
                                        brandID == AppConstant.CategoriesIds.VILLA ||
                                        brandID == AppConstant.CategoriesIds.HOUSE ||
                                        brandID == AppConstant.CategoriesIds.PLOT ||
                                        brandID == AppConstant.CategoriesIds.OFFICE ||
                                        brandID == AppConstant.CategoriesIds.WAREHOUSE ||


                                        brandID == AppConstant.CategoriesIds.INDUSTRIAL_EQUIPMENT ||
                                        brandID == AppConstant.CategoriesIds.OFFICE_EQUIPMENT ||
                                        brandID == AppConstant.CategoriesIds.SPORTS_EQUIPMENT ||
                                        brandID == AppConstant.CategoriesIds.MACHINERY ||


                                        brandID == AppConstant.CategoriesIds.FURNITURE ||
                                        brandID == AppConstant.CategoriesIds.CLOTHING ||
                                        brandID == AppConstant.CategoriesIds.BABY_ITEMS ||
                                        brandID == AppConstant.CategoriesIds.BOOKS ||
                                        brandID == AppConstant.CategoriesIds.GAMING ||
                                        brandID == AppConstant.CategoriesIds.PETS ||
                                        brandID == AppConstant.CategoriesIds.OTHER

                                        ) {
                                    homeList.add(homeProducts.get(pos));
                                }
                            }
                        }else{

                            if(categoryTypeID != null){
                                if (homeProducts.get(pos).getProductOn() == categoryTypeID) {
                                    homeList.add(homeProducts.get(pos));
                                }
                            }

                        }



            }
            bindProductItemLists(homeList);
        }
    }

    private void bindProductItemLists(ArrayList<ProductModelAPI> result) {
        if (result.size() > 0) {
            rvProducts.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);

            int spacingInPixels = mainActivity.getResources().getDimensionPixelSize(R.dimen.dp8);
            HomeFragment.SpacesItemDecorationAllSideEqual spacesItemDecorationHome = new HomeFragment.SpacesItemDecorationAllSideEqual(spacingInPixels);

            rvProducts.addItemDecoration(spacesItemDecorationHome);

            rvProducts.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener), result, new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
            rvProducts.setNestedScrollingEnabled(false);
            rvProducts.setHasFixedSize(true);
        } else {
            rvProducts.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.resetView();
        titlebar.showBackButton(getActivity());
        titlebar.setTitle(title + "");
        mainActivity.hideBottombar();
    }

    @Override
    public void onDestroyView() {
    //    titlebar.showTitlebar();
      ///  titlebar.setTitle(mainActivity.getResources().getString(R.string.homeTitle));
//        titlebar.showNotification(0);
//        titlebar.showCart(0).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mainActivity.prefHelper.getLoginStatus()) {
//                    mainActivity.addFragment(new CartFragment(), true, true);
//                } else {
//
//                    UIHelper.showToast(mainActivity, mainActivity.getString(R.string.please_login_viewcart));
//
//                }
//            }
//        });
//        titlebar.showMenuButton();
        super.onDestroyView();
    }

    @OnClick({R.id.llSearch, R.id.etSearch, R.id.ivSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
               // UIHelper.showToast(mainActivity, mainActivity.getString(R.string.implementdialog));
                break;
            case R.id.etSearch:
//                UIHelper.showToast(mainActivity, mainActivity.getString(R.string.implementdialog));
                break;
            case R.id.ivSearch:

                UIHelper.hideSoftKeyboards(mainActivity);
                filter(etSearch.getText().toString());
               // UIHelper.showToast(mainActivity, mainActivity.getString(R.string.implementdialog));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      //  filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    //    filter(s.toString());
    }

    private void filter(String text) {
        if(allProducts.size() == 0){
         txtNoData.setVisibility(View.VISIBLE);
         rvProducts.setVisibility(View.GONE);
            //   UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_products_found));
            return;
        }

        if (text.length() == 0) {
            rvProducts.clearList();
            rvProducts.addAll(allProducts);
            rvProducts.notifyDataSetChanged();
//            adapter.addAll(arrayList);
//            adapter.notifyDataSetChanged();
        } else {
            ArrayList<ProductModelAPI> filteredList = new ArrayList<>();

            for (ProductModelAPI filterBrand : allProducts) {
                if (filterBrand.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(filterBrand);
                }
            }

            rvProducts.clearList();
            rvProducts.addAll(filteredList);
            rvProducts.notifyDataSetChanged();
        }
    }
}