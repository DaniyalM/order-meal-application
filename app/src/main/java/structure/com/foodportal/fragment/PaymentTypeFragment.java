package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.models.CartModule.CartProductMainClass;

public class PaymentTypeFragment extends BaseFragment {


    @BindView(R.id.tvCashonReturn)
    TextView tvCashonReturn;
    @BindView(R.id.tvCashOnPickup)
    TextView tvCashOnPickup;
    CartProductMainClass cartProductMainClass;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    private String numbers ="0";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        tvAmount.setText("AED "+cartProductMainClass.getCart_product().getAmount()+".0");
    }


    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setTitle(getString(R.string.payment_method));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.tvCashonReturn, R.id.tvCashOnPickup})
    public void onViewClicked(View view) {

        ConfirmationFragment confirmationFragment;
        switch (view.getId()) {
            case R.id.tvCashonReturn:
             //   UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.implementdialog));

                 confirmationFragment = new ConfirmationFragment();
                if(numbers!=null && !numbers.equals("0")){
                    confirmationFragment.setModel(cartProductMainClass,2,numbers);

                }else{

                    confirmationFragment.setModel(cartProductMainClass,2,null);

                }
                mainActivity.replaceFragment(confirmationFragment, true, true);
                break;
            case R.id.tvCashOnPickup:

                 confirmationFragment = new ConfirmationFragment();
                if(numbers!=null && !numbers.equals("0")){
                confirmationFragment.setModel(cartProductMainClass,1,numbers);

                }else{

                    confirmationFragment.setModel(cartProductMainClass,1,null);

                }
                mainActivity.replaceFragment(confirmationFragment, true, true);

                break;
        }
    }

    public void setCartProductMainClass(CartProductMainClass cartProductMainClass,String numbers) {
        this.cartProductMainClass = cartProductMainClass;
        this.numbers = numbers;
    }
}
