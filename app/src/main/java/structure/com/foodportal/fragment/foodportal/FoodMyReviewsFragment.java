package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodMyReviewsAdapter;
import structure.com.foodportal.databinding.FragmentMyReviewsBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodMyReviewsListener;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Recipe;

public class FoodMyReviewsFragment extends BaseFragment implements FoodMyReviewsListener {

    FoodMyReviewsAdapter foodMyReviewsAdapter;
    FragmentMyReviewsBinding binding;
    ArrayList<Recipe> reviews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_reviews, container, false);
        setListners();
        mainActivity.hideBottombar();
        getMyReviews();
        return binding.getRoot();
    }

    private void getMyReviews() {
        serviceHelper.enqueueArrayCall(webService.getMyReviews(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", ""))),
                AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MY_REVIEWS);
    }

    private void setListners() {
        reviews = new ArrayList<>();
        foodMyReviewsAdapter = new FoodMyReviewsAdapter(reviews, getContext(), this);
        binding.rvMyReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMyReviews.setAdapter(foodMyReviewsAdapter);
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showMenuButton(mainActivity);
    }

    public void setData() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foodMyReviewsAdapter.addAllToAdapter(reviews);
            }
        });
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MY_REVIEWS:
                reviews.addAll((ArrayList<Recipe>) result);
                if (reviews != null && reviews.size() > 0) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                        }
                    });
                } else {
                    binding.rvMyReviews.setVisibility(View.GONE);
                    binding.tvEmptyView.setVisibility(View.VISIBLE);
                }
                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodDetailModel != null) {
                    LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
                    FoodDetailFragment detailFragment = new FoodDetailFragment();
                    detailFragment.setFoodDetailModel(foodDetailModel);
                    mainActivity.addFragment(detailFragment, true, true);
                }
                break;
        }
    }

    @Override
    public void ResponseFailure(String tag) {
        Log.e(FoodMyReviewsFragment.class.getSimpleName(), "ResponseFailure(): " + tag);
    }

    @Override
    public void onCategoryClick(int reviewPosition, int categoryPosition) {
        // TODO:
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setModel(reviews.get(reviewPosition).getCategory_slider().get(categoryPosition));
        mainActivity.addFragment(recipeFragment, true, true);
    }

    @Override
    public void onReviewClick(int position) {
        // TODO:
        serviceHelper.enqueueCall(webService.getfooddetail(reviews.get(position).getSlug(),
                String.valueOf(preferenceHelper.getUserFood().getId())),
                AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);
    }
}
