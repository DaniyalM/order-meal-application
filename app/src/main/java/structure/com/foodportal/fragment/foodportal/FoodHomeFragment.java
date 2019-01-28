package structure.com.foodportal.fragment.foodportal;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentHomefoodBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.Titlebar;

public class FoodHomeFragment extends BaseFragment implements View.OnClickListener {


    FragmentHomefoodBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homefood, container, false);
       setListners();

        return binding.getRoot();
    }

    private void setListners() {
     //  mainActivity.getleftSidemmenu().setListner(preferenceHelper);
        //binding.btnView.setonClickListner(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        titlebar.setTitle(getString(R.string.cooking_food));
        titlebar.showMenuButton(mainActivity);



    }


}
