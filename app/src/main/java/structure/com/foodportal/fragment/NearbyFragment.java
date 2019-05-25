package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.NearbyAdapter;
import structure.com.foodportal.databinding.FragmentNearbyBinding;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.ResturantClickInterface;


public class NearbyFragment extends BaseFragment implements ResturantClickInterface {

    FragmentNearbyBinding binding;
    NearbyAdapter nearbyAdapter;
    private ArrayList<String> imgRest;

    public NearbyFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setTitle(getString(R.string.nearby_rest));
        titlebar.showBackButton(mainActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nearby, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        imgRest = new ArrayList<>();
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        imgRest.add("https://vignette.wikia.nocookie.net/logopedia/images/1/1e/Plain-mcdonalds-logo.jpg/revision/latest?cb=20161208155014");
        nearbyAdapter = new NearbyAdapter(mainActivity, imgRest, this);
        binding.rvNearby.setLayoutManager(new LinearLayoutManager(mainActivity));
        binding.rvNearby.setAdapter(nearbyAdapter);
        mainActivity.getTab().setVisibility(View.GONE);
    }

    @Override
    public void onResturant_Click() {
       // replaceFragment(new ResturantMenu(), true, true);
    }
}
