package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodPreparationAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSearchAdapter;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.foodInterfaces.FoodSearchListner;
import structure.com.foodportal.models.foodModels.ChipsList;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.HeaderWrapper;

public class FoodSearchFragment extends BaseFragment implements View.OnClickListener, FoodSearchListner, ChipsInput.ChipsListener {

    FoodSearchAdapter foodSearchAdapter;
    EditText searchAutoComplete;
    RecyclerView rvsearch;
    ArrayList<FoodDetailModel> steps;
    LinearLayoutManager linearLayoutManagerSearch;
    private static String[] SUGGESTIONS = new String[]{"Nachos", "Chip", "Tortilla Chips", "Melted Cheese", "Salsa", "Guacamole", "Cheddar", "Mozzarella", "Mexico", "Jalapeno"};
    EditText editText,editTextExtra;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_search, container, false);
        editText = (EditText) rootView.findViewById(R.id.chips) ;
        editTextExtra = (EditText) rootView.findViewById(R.id.chipsextra) ;
// build the ContactChip list
        List<ChipsList> contactList = new ArrayList<ChipsList>();
        createChip("Chesse");
        createChip("Tortilla Chips");
        createChip("Biryani");
        createChip("Sheer");
        createChip("Masala");
        createChip("Tikka");
        createChip("Nihari");


        setListners(rootView);
        return rootView;
    }

    private void setListners(View view) {
        linearLayoutManagerSearch = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        steps = new ArrayList<>();

        searchAutoComplete = (EditText) view.findViewById(R.id.etSearch);
        rvsearch = (RecyclerView) view.findViewById(R.id.rvSearch);
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

                foodSearchAdapter.addAll((ArrayList<FoodDetailModel>) result);

                break;


        }


    }


    Titlebar titlebar;

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.showBackButton(mainActivity);
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
    public void clickSearch(int position) {

    }

    public void createChip(String contactName) {

        final SpannableStringBuilder sb = new SpannableStringBuilder();
        TextView tv = createContactTextView(contactName);
        BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv);
        bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());

        sb.append(contactName + ",");
        sb.setSpan(new ImageSpan(bd), sb.length() - (contactName.length() + 1), sb.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(sb);
    }


    public TextView createContactTextView(String text) {
        //creating textview dynamically
        TextView tv = new TextView(mainActivity);
        tv.setText(text);
        tv.setTextSize(50);
        tv.setBackgroundResource(R.drawable.border_item_rounded);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_menu_recipe, 0);
        return tv;
    }

    public static Object convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(20, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        return new BitmapDrawable(viewBmp);

    }

    @Override
    public void onChipAdded(ChipInterface chip, int newSize) {

    }

    @Override
    public void onChipRemoved(ChipInterface chip, int newSize) {

    }

    @Override
    public void onTextChanged(CharSequence text) {

    }
}
