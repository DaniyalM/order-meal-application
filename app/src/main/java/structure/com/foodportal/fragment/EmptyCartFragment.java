package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentEmptyCartBinding;
import structure.com.foodportal.helper.Titlebar;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyCartFragment extends BaseFragment {

    FragmentEmptyCartBinding binding;

    public EmptyCartFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setTitle(getString(R.string.your_cart));
//        titlebar.showCrossButton(mainActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_empty_cart, container, false);
        init();
        return binding.getRoot();
    }


    private void init() {

        binding.btnBackto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.rbHome.setChecked(true);
            }
        });

    }

}
