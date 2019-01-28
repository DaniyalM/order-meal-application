package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentPaymentBinding;
import structure.com.foodportal.helper.Titlebar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends BaseFragment implements View.OnClickListener{

    FragmentPaymentBinding binding;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setTitle(getString(R.string.payment));
        titlebar.showBackButton(mainActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        return binding.getRoot();
    }

    private void init() {


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivCreditCard:

                break;
            case R.id.tvCreditCard:

                break;
            case R.id.ivCash:

                break;
            case R.id.tvCash:

                break;
            case R.id.ivApple:

                break;
            case R.id.tvApple:

                break;
        }
    }
}
