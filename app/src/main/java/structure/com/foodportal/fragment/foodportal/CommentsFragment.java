package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Comment;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCommentsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSubCategory;
import structure.com.foodportal.databinding.FragmenCommentsBinding;
import structure.com.foodportal.databinding.FragmentRecipeBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DataSyncEvent;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.CommentClickListner;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.CategorySliderWrapper;
import structure.com.foodportal.models.foodModels.Comments;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.RecipeWrapper;
import structure.com.foodportal.models.foodModels.SavedRecipe;

public class CommentsFragment extends BaseFragment implements CommentClickListner {


    FoodSubCategory foodCategoryAdapter;

    CategorySlider categorySlider;
    FoodDetailModel savedRecipe;
    ArrayList<CategorySlider> categorySliders;
    LinearLayoutManager linearLayoutManagerComment;
    FoodDetailModelWrapper foodDetailModel;
    ArrayList<Comments> comments;
    FragmenCommentsBinding binding;
    FoodCommentsAdapter foodCommentsAdapter;
    public void setArrayComments(FoodDetailModelWrapper foodDetailModel){
        this.foodDetailModel= foodDetailModel;


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragmen_comments, container, false);
        mainActivity.hideBottombar();
        setListners();




        return binding.getRoot();
    }



        void setListners(){



            final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
            defaultItemAnimator.setAddDuration(1000000);
            defaultItemAnimator.setRemoveDuration(1000000);
            binding.rvCommentsSection.setItemAnimator(defaultItemAnimator);
            linearLayoutManagerComment = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
            binding.rvCommentsSection.setLayoutManager(linearLayoutManagerComment);
            comments= new ArrayList<>();
            setdata();

        }

    private void setdata() {
        if (foodDetailModel.getAllReviews().size() > 0) {
            comments.addAll(foodDetailModel.getAllReviews());
            foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, false);
            binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
            foodCommentsAdapter.notifyDataSetChanged();

        } else {


        }
    }

    Titlebar titlebar;

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showBackButton(mainActivity);
        this.titlebar = titlebar;


    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().post(new DataSyncEvent(0));
        super.onDestroyView();

        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showBackButton(mainActivity);

    }





    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY_RECIPE:



                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:


                break;


        }

    }


    @Override
    public void onReplyClick(int positon) {

    }
}