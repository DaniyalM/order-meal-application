package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.Titlebar;

public class OrderHistoryFragment extends BaseFragment  {


    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

       // titlebar.setTitle(getString(R.string.my_cart));
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cart_orderhistory, container, false);
        ButterKnife.bind(this, view);
        init();
        getHistory();
        return view;
    }

    private void getHistory() {

    }


    private void init() {


    }

}
