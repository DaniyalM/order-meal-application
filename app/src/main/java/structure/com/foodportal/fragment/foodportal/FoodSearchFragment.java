package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.internal.Util;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodMealTypeAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSearchAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSuggestionAdapter;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodChipsListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodSearchListner;
import structure.com.foodportal.models.foodModels.ChipsList;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.HeaderWrapper;

public class FoodSearchFragment extends BaseFragment implements View.OnClickListener, FoodSearchListner, FoodChipsListner {

    FoodSearchAdapter foodSearchAdapter;
    FoodSuggestionAdapter foodSuggestionAdapter;
    FoodMealTypeAdapter foodMealTypeAdapter;
    EditText searchAutoComplete;
    RecyclerView rvsearch;
    ArrayList<FoodDetailModel> steps;
    LinearLayoutManager linearLayoutManagerSearch;
    RecyclerView rvSuggestion, rvMealType;

    ArrayList<String> suggestionList;
    ArrayList<String> mealTypeList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_search, container, false);

        setData();
        initviews(rootView);
        setListners();
        return rootView;
    }

    private void setData() {
        suggestionList = new ArrayList<>();
        mealTypeList = new ArrayList<>();

        suggestionList.add("Biryani");
        suggestionList.add("Qeema");
        suggestionList.add("Sheer");
        suggestionList.add("Hara masala");
        suggestionList.add("Chicken");
        suggestionList.add("Paye");
        suggestionList.add("Pulao");
        suggestionList.add("Sagudana Kheer");

        mealTypeList.add("Dinner");
        mealTypeList.add("Lucnh");
        mealTypeList.add("Breakfast");
        mealTypeList.add("Brunch");
        mealTypeList.add("Tea");
        mealTypeList.add("Event");


    }

    void initviews(View view) {
        rvSuggestion = (RecyclerView) view.findViewById(R.id.rvSuggestions);
        rvMealType = (RecyclerView) view.findViewById(R.id.rvMealType);
        searchAutoComplete = (EditText) view.findViewById(R.id.etSearch);
        rvsearch = (RecyclerView) view.findViewById(R.id.rvSearch);
    }

    private void setListners() {
        linearLayoutManagerSearch = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        steps = new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager staggeredGridLayoutManagernext = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
        rvSuggestion.setLayoutManager(staggeredGridLayoutManager);
        rvMealType.setLayoutManager(staggeredGridLayoutManagernext);
        foodSuggestionAdapter = new FoodSuggestionAdapter(suggestionList, mainActivity, this);
        foodMealTypeAdapter = new FoodMealTypeAdapter(mealTypeList, mainActivity, this);
        rvMealType.setAdapter(foodMealTypeAdapter);
        rvSuggestion.setAdapter(foodSuggestionAdapter);
        foodSuggestionAdapter.notifyDataSetChanged();
        foodMealTypeAdapter.notifyDataSetChanged();

        foodSearchAdapter = new FoodSearchAdapter(steps, mainActivity, this);
        rvsearch.setLayoutManager(linearLayoutManagerSearch);
        rvsearch.setAdapter(foodSearchAdapter);
        searchAutoComplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

//                if(searchAutoComplete.getText().toString().length()>3){
//
//                    serviceHelper.enqueueArrayCall(webService.getSearchResult(searchAutoComplete.getText().toString().trim()),
//                            AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME_SEARCH);
//
//                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });
        searchAutoComplete.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (searchAutoComplete.getText().toString().length() > 1) {

                        serviceHelper.enqueueArrayCall(webService.getSearchResult(searchAutoComplete.getText().toString().trim()),
                                AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME_SEARCH);
                    }

                    return true;
                }
                return false;
            }
        });
        rvsearch.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        rvsearch.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < rvsearch.getChildCount(); i++) {
                            View v = rvsearch.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });


    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME_SEARCH:
                UIHelper.hideSoftKeyboards(mainActivity);
                foodSearchAdapter.addAll((ArrayList<FoodDetailModel>) result);

                break;

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS:
                FoodDetailModelWrapper foodDetailModel = (FoodDetailModelWrapper) JsonHelpers.convertToModelClass(result, FoodDetailModelWrapper.class);
                if (foodDetailModel != null) {
                    UIHelper.hideSoftKeyboards(mainActivity);
                  //  LocalDataHelper.writeToFile(result.toString(), mainActivity, "Detail");
                    FoodDetailFragment detailFragment = new FoodDetailFragment();
                    detailFragment.setFromSearch(true);
                    detailFragment.setFoodDetailModel(foodDetailModel);
                    mainActivity.addFragment(detailFragment, true, true);
                    //    setData(foodDetailModel.getData());

                }

                break;
        }


    }


    Titlebar titlebar;

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.showMenuButton(mainActivity);
        titlebar.hidesearch();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        titlebar.showsearch(mainActivity);
        titlebar.showMenuButton(mainActivity);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void clickSearch(FoodDetailModel foodDetailModel,int position) {

        serviceHelper.enqueueCall(webService.getfooddetail(foodDetailModel.getSlug(),String.valueOf(preferenceHelper.getUserFood().getId())), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS);

    }




    @Override
    public void onSuggestionsClick(int position) {
        searchAutoComplete.setText(suggestionList.get(position));
        serviceHelper.enqueueArrayCall(webService.getSearchResult(searchAutoComplete.getText().toString().trim()),
                AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME_SEARCH);

    }

    @Override
    public void onMealTypeClick(int position) {
        searchAutoComplete.setText(mealTypeList.get(position));
        serviceHelper.enqueueArrayCall(webService.getSearchResult(searchAutoComplete.getText().toString().trim()),
                AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME_SEARCH);
    }
}
