package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentCreditCardBinding;
import structure.com.foodportal.helper.Titlebar;

public class CreditCardFragment extends BaseFragment {
    FragmentCreditCardBinding binding;

    public CreditCardFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_credit_card, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.credit_card));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();
    }
}
