package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.SeeAllRecipesAdapter;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.Sections;

public  class SeeAllRecipersFragment extends BaseFragment implements View.OnClickListener,SubCategoryListner {



    Unbinder unbinder;
    int total_pages;
    int page_my=1;
    @BindView(R.id.rvAllRecipes)
    ShimmerRecyclerView rvSeeAll;
    LinearLayoutManager mlayoutManager;
    SeeAllRecipesAdapter seeAllRecipesAdapter;
    ArrayList<Sections> sections =new ArrayList<>();
    Titlebar titlebar;
    boolean popular =true;

    public void setbool(Boolean popular){

        this.popular= popular;

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_seerecipes, container, false);

        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);
        setListner();


        return view;
    }

    private void setListner() {


        seeAllRecipesAdapter= new SeeAllRecipesAdapter(sections,mainActivity,this);
        mlayoutManager = new LinearLayoutManager(mainActivity);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mainActivity, 2);
        rvSeeAll.setLayoutManager(mLayoutManager);
        rvSeeAll.setAdapter(seeAllRecipesAdapter);
        rvSeeAll.setHasFixedSize(true);
        rvSeeAll.showShimmerAdapter();
       // setPagination(mlayoutManager, seeAllRecipesAdapter.getItemCount() - 1);
        rvSeeAll.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        rvSeeAll.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i <rvSeeAll.getChildCount(); i++) {
                            View v =rvSeeAll.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });


        if(popular){
            getAllRecipes(0,null);
        }else{
            getAllFeatured(0,null);
        }
    }

    @Override
    public void onClick(View view) {



    }



    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showBackButton(mainActivity);
        this.titlebar=titlebar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        titlebar.showMenuButton(mainActivity);
        // unbind the view to free some memory
        unbinder.unbind();
    }



    private void setPagination(final LinearLayoutManager layoutmanager, final int i) {
        rvSeeAll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (layoutmanager != null && layoutmanager.findLastCompletelyVisibleItemPosition() == seeAllRecipesAdapter.getItemCount() - 1) {

                    if (total_pages > page_my) {
                        page_my++;
                      //  getAllRecipes(layoutmanager.findLastCompletelyVisibleItemPosition() - 4, layoutmanager);
                    }
                }


            }


        });
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_POPULAR:



//                if (mlayoutManager != null)
//                    mlayoutManager.scrollToPositionWithOffset(mlayoutManager.findLastCompletelyVisibleItemPosition() - 4, 0);
                sections.addAll(((Section) result).getSection_list());
                rvSeeAll.hideShimmerAdapter();
                seeAllRecipesAdapter.notifyDataSetChanged();
                break;

                case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_FEATURED:
                    sections.addAll(((Section) result).getSection_list());
                    rvSeeAll.hideShimmerAdapter();
                    seeAllRecipesAdapter.notifyDataSetChanged();

                break;
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                FoodDetailFragment recipeFragment = new FoodDetailFragment();
                recipeFragment.setFromSearch(true);
                recipeFragment.setFoodDetailModel(foodDetailModel);
                mainActivity.addFragment(recipeFragment, true, true);

        }

    }



    private void getAllRecipes(int i, LinearLayoutManager layoutmanager) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getPopularRecipes(page_my,25), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_POPULAR);


    }
 private void getAllFeatured(int i, LinearLayoutManager layoutmanager) {

        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getPopularRecipes(page_my,25), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_POPULAR);


    }


    @Override
    public void onSubCategoryClick(int position) {
        if (NetworkUtils.isNetworkAvailable(mainActivity))
            serviceHelper.enqueueCall(webService.getfooddetail(sections.get(position).getSlug(),String.valueOf(preferenceHelper.getUserFood().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);

    }
}