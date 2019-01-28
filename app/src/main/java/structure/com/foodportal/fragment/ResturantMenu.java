package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.ArrayListSingleChildExpandableAdapter;
import structure.com.foodportal.adapter.HelpListItemBinder;
import structure.com.foodportal.adapter.menuAdapter;
import structure.com.foodportal.databinding.FragmentRestaurantMenuBinding;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

/**
 * Created by Addi.
 */
public class ResturantMenu extends BaseFragment implements HelpListItemBinder.FoodCount {

    FragmentRestaurantMenuBinding binding;
    menuAdapter adapter;

    private ArrayList<String> questionCollection;
    private ArrayList<String> answerCollection;
    private HashMap<String, String> listDataChild;
    private ArrayListSingleChildExpandableAdapter<String, String> adapterExpandable;
    private int count = 0;

    public ResturantMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_menu, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.hideTitlebar();

    }

    private void init() {

        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("https://amp.businessinsider.com/images/5228d9db6bb3f75f278b4567-750-563.jpg");
        imgList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8vprjcG-cnpfF3m0iM7-OtP31ihqORS4i2fkXXZR4Dl7wSUf1");
        adapter = new menuAdapter(mainActivity, imgList);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

        questionCollection = new ArrayList<>();
        listDataChild = new HashMap<>();
        answerCollection = new ArrayList<>();

        questionCollection.add("Starters");
        questionCollection.add("Burger");
        questionCollection.add("Soup");
        questionCollection.add("Salads");

        answerCollection.add("Clusket's Wings");
        answerCollection.add("Hummus with wood Roasted Chicken");
        answerCollection.add("Clusket's Wings");
        answerCollection.add("Hummus with wood Roasted Chicken");

        for (int i = 0; i < questionCollection.size(); i++) {
            listDataChild.put(questionCollection.get(i), answerCollection.get(i));
        }

        adapterExpandable = new ArrayListSingleChildExpandableAdapter<>(mainActivity, questionCollection, listDataChild, new HelpListItemBinder(this));
        binding.expandedMenu.setAdapter(adapterExpandable);
        adapter.notifyDataSetChanged();

        mainActivity.getTab().setVisibility(View.GONE);

        binding.tvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MyCartFragment(), true, true);
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onBackPressed();
            }
        });

        UIHelper.setImagewithGlide(mainActivity,binding.ivHeader,"https://previews.123rf.com/images/cookelma/cookelma1501/cookelma150100168/35584272-moscow-russia-october-6-2014-mcdonald-s-food-mcdonald-s-corporation-is-the-world-s-largest-chain-of-.jpg");

    }

    @Override
    public void noFoodCount(int count) {
        this.count += count;
        binding.tvCount.setText(this.count + "");

        binding.tvFPrice.setText(getString(R.string.aed) + " " + (this.count * 100) + "");
    }
}
