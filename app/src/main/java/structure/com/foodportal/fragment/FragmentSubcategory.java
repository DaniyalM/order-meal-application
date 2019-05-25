package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.SubCategoryAdapter;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.CategoryInterface;
import structure.com.foodportal.models.AllCategory;
import structure.com.foodportal.webservice.Api_Array_Response;
import structure.com.foodportal.webservice.WebApiRequest;

public class FragmentSubcategory extends BaseFragment implements View.OnClickListener, CategoryInterface {


    @BindView(R.id.rvSubCategory)
    RecyclerView rvSubCategory;
    HashMap hashMap;
    SubCategoryAdapter subcategoryAdapter;
    ArrayList<AllCategory> categoriesList;
    LinearLayoutManager linearLayoutManager;
    AllCategory category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subcategory, container, false);
        ButterKnife.bind(this, rootView);

        init();
        if (category != null) {
            getSubCategory(category.getId());
        }

        return rootView;


    }

    protected void setCategory(AllCategory category) {
        this.category = category;

    }

    private void init() {
        mainActivity.hideBottombar();
        hashMap = new HashMap();
        categoriesList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);
        rvSubCategory.setLayoutManager(linearLayoutManager);
        rvSubCategory.setItemAnimator(defaultItemAnimator);
        subcategoryAdapter = new SubCategoryAdapter(mainActivity, categoriesList, this);
        subcategoryAdapter.setItemClickListener((ent, position, id) -> {
            AllCategory category = (AllCategory) ent;
            //mainActivity.replaceFragment(ViewAllProducts.newInstance(category.getTitle(), category.getParentId(), category.getId()), true, true);
        });
        rvSubCategory.setAdapter(subcategoryAdapter);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(category.getTitle());
        titlebar.showBackButton(mainActivity);
    }

    @SuppressWarnings("unchecked")
    private void getSubCategory(int category_id) {


        hashMap.put("category_id", category_id);

        WebApiRequest.getInstance(mainActivity).genricArrayRequest(hashMap, AppConstant.SUB_CATEGORYS, new WebApiRequest.APIRequestDataCallBack() {
            @Override
            public void onSuccess(Api_Array_Response response) {

                categoriesList.addAll((ArrayList<AllCategory>) JsonHelpers.convertToArrayModelClass(response.getResult(), AllCategory.class));
                subcategoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError() {

            }

            @Override
            public void onNoNetwork() {

            }
        });


    }

    @Override
    public void onCategoryClicked(ArrayList<AllCategory> category, int position) {
//            AllCategory ent = category.get(position);
//            ViewAllProducts viewAllProducts =new ViewAllProducts();
//            viewAllProducts.setCategoryTypeID(ent.getParentId());
//           viewAllProducts.setCategoryID(ent.getParentId());
//            viewAllProducts.setBrandID(ent.getId());
//          //  viewAllProducts.setSubCategory(ent.getId());
//            viewAllProducts.setTitle(ent.getTitle());
//            viewAllProducts.setFromCatogories(true);
//          //  mainActivity.replaceFragment(ViewAllProducts.newInstance(ent.getTitle(), ent.getParentId(), ent.getId()), true, true);
//            mainActivity.replaceFragment(viewAllProducts, true, true);
    }
}
