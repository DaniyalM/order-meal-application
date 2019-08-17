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

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodMyReviewsAdapter;
import structure.com.foodportal.databinding.FragmentMyReviewsBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.FoodMyReviewsListener;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.Recipe;

public class FoodMyReviewsFragment extends BaseFragment implements FoodMyReviewsListener {

    FoodMyReviewsAdapter foodMyReviewsAdapter;
    FragmentMyReviewsBinding binding;
    ArrayList<Recipe> reviews;

    private String myReviews, reviewSingle, reviewsMultiple;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_reviews, container, false);
        setListners();
        mainActivity.hideBottombar();


        binding.tvReviewCount.setText(myReviews);
        getMyReviews();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        setValuesByLanguage(preferenceHelper.getSelectedLanguage());
    }

    private void setValuesByLanguage(int language) {
        switch (language) {
            case AppConstant.Language.ENGLISH:
            default:
                myReviews = getString(R.string.my_reviews_en);
                reviewSingle = getString(R.string.review_single_en);
                reviewsMultiple = getString(R.string.reviews_multi_en);
                break;

            case AppConstant.Language.URDU:
                myReviews = getString(R.string.my_reviews_ur);
                reviewSingle = getString(R.string.review_single_ur);
                reviewsMultiple = getString(R.string.reviews_multi_ur);
                break;
        }
    }

    private void getMyReviews() {
        serviceHelper.enqueueArrayCall(webService.getMyReviews(Integer.valueOf(preferenceHelper.getUserFood().getId().replace(".0", ""))),
                AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MY_REVIEWS);
    }

    private void setListners() {
        reviews = new ArrayList<>();
        foodMyReviewsAdapter = new FoodMyReviewsAdapter(reviews, getContext(), this);
        foodMyReviewsAdapter.setPreferenceHelper(preferenceHelper);

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
        binding.tvReviewCount.setVisibility(View.VISIBLE);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foodMyReviewsAdapter.addAllToAdapter(reviews);
            }
        });
        if (foodMyReviewsAdapter.getItemCount() == 1) {
            binding.tvReviewCount.setText(myReviews + " (" + foodMyReviewsAdapter.getItemCount() + " " + reviewSingle +  ")");
        } else {
            binding.tvReviewCount.setText(myReviews + " (" + foodMyReviewsAdapter.getItemCount() + " " + reviewsMultiple +  ")");
        }
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
                    binding.tvReviewCount.setVisibility(View.GONE);
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
