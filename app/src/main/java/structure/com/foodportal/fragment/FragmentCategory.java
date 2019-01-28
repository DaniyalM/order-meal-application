package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.CategoryAdapter;
import structure.com.foodportal.databinding.FragmentCategoryBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.CategoryInterface;
import structure.com.foodportal.models.AllCategory;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.webservice.Api_Response;
import structure.com.foodportal.webservice.WebApiRequest;

public class FragmentCategory extends BaseFragment implements View.OnClickListener, CategoryInterface {


    ArrayList<AllCategory> categoriesList;
    FragmentCategoryBinding binding;
    @BindView(R.id.rvAllCategory)
    RecyclerView rvAllCategory;
    CategoryAdapter categoryAdapter;
    LinearLayoutManager linearLayoutManager;
    Unbinder unbinder;

    boolean fromBottomBar;

    public FragmentCategory() {
    }

    public FragmentCategory(boolean fromBottomBar) {
        this.fromBottomBar = fromBottomBar;
    }


    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.categories));
        if (fromBottomBar) {
            titlebar.showMenuButton(mainActivity);
        } else {
            titlebar.showBackButton(mainActivity);
        }
        titlebar.showTitlebar();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
        init();
        getParentCategory();

        if (fromBottomBar) {
            mainActivity.showBottombar();
        }

        return binding.getRoot();
    }

    private void init() {
        mainActivity.hideBottombar();
        categoriesList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);
        rvAllCategory.setLayoutManager(linearLayoutManager);
        rvAllCategory.setItemAnimator(defaultItemAnimator);
        categoryAdapter = new CategoryAdapter(mainActivity, categoriesList, this);
        rvAllCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void onClick(View view) {

    }


    public void getParentCategory() {

        WebApiRequest.getInstance(mainActivity).genricObjectRequest(null, AppConstant.ALL_CATEGORYS, new WebApiRequest.APIRequestObjectCallBack() {
            @Override
            public void onSuccess(Api_Response response) {


                Category category = (Category) JsonHelpers.convertToModelClass(response.getResult(), Category.class);
                categoriesList.addAll(category.getCategories());
                categoryAdapter.notifyDataSetChanged();


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

        FragmentSubcategory fragmentSubcategory = new FragmentSubcategory();
        fragmentSubcategory.setCategory(category.get(position));
        mainActivity.replaceFragment(fragmentSubcategory, true, true);

    }
}
