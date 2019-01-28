package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.CartModule.CartProductMainClass;

public class ConfirmationFragment extends BaseFragment {
    @BindView(R.id.tvCreditCardNumber)
    TextView tvCreditCardNumber;
    @BindView(R.id.tcCvv)
    TextView tcCvv;
    @BindView(R.id.tvExpiryDate)
    TextView tvExpiryDate;
    @BindView(R.id.tvNameonCard)
    TextView tvNameonCard;
    @BindView(R.id.rvOrderSummary)
    RecyclerView rvOrderSummary;
    @BindView(R.id.tvSubtotal)
    TextView tvSubtotal;
    @BindView(R.id.tvBeforeVat)
    TextView tvBeforeVat;
    @BindView(R.id.tvVatCharges)
    TextView tvVatCharges;
    @BindView(R.id.tvGrandtotal)
    TextView tvGrandtotal;
    @BindView(R.id.btnCheckout)
    Button btnCheckout;
    //  OrderSummaryAdapter adapter;
    CartProductMainClass cartProductMainClass;
    @BindView(R.id.ivProductImage)
    ImageView ivProductImage;
    @BindView(R.id.llProductImage)
    LinearLayout llProductImage;
    @BindView(R.id.tvProductCatogory)
    TextView tvProductCatogory;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvPriceTag)
    TextView tvPriceTag;
    @BindView(R.id.tvPriceType)
    TextView tvPriceType;
    @BindView(R.id.tvProductLocation)
    TextView tvProductLocation;
    @BindView(R.id.ibRemove)
    ImageButton ibRemove;
    @BindView(R.id.tvpaymentMethodType)
    TextView tvpaymentMethodType;
    int pay_method_type = 0;
    @BindView(R.id.VATPercent)
    TextView VATPercent;

    private String number = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        switch (pay_method_type) {
            case 0:
                tvpaymentMethodType.setText(mainActivity.getString(R.string.credit_card));
                tvpaymentMethodType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.creditcard, 0, 0, 0);
                break;

            case 1:
                tvpaymentMethodType.setText(mainActivity.getString(R.string.cash_on_pickup));
                tvpaymentMethodType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0);
                break;

            case 2:
                tvpaymentMethodType.setText(mainActivity.getString(R.string.cash_on_delivery));
                tvpaymentMethodType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0);
                break;
        }

        tvBeforeVat.setText(cartProductMainClass.getCart_product().getAmount()+" AED");

    //    VATPercent.setText("VAT Charges("+cartProductMainClass.getCart_product().getVat()+"%)");
        VATPercent.setText("VAT Charges");

         //   Float vatcharges = ( Float.valueOf(cartProductMainClass.getCart_product().getAmount())*cartProductMainClass.getCart_product().getVat()/100);

      //  tvVatCharges.setText(""+vatcharges+" AED");
        tvVatCharges.setText(""+cartProductMainClass.getCart_product().getVat()+" AED");
        tvGrandtotal.setText(""+(Float.valueOf(cartProductMainClass.getCart_product().getAmount())-cartProductMainClass.getCart_product().getVat())+ " AED");
        tvProductCatogory.setText(cartProductMainClass.getCart_product().getTitle());
        tvProductName.setText(cartProductMainClass.getCart_product().getModelNumber());
        tvProductLocation.setText(cartProductMainClass.getCart_product().getLocation());
        tvPriceTag.setText("AED " +cartProductMainClass.getCart_product().getAmount());
        if (cartProductMainClass.getCart_product().getProductRentType() != null) {
            tvPriceType.setText(cartProductMainClass.getCart_product().getProductRentType().getName());
        } else {
        }
        UIHelper.setImagewithGlide(mainActivity, ivProductImage, cartProductMainClass.getCart_product().getProduct_images().get(0).getProductImage());
      //  tvGrandtotal.setText(cartProductMainClass.getCart_product().getAmount());
        ///  adapter = new OrderSummaryAdapter(mainActivity);
        //   rvOrderSummary.setAdapter(adapter);
        //   rvOrderSummary.setLayoutManager(new LinearLayoutManager(mainActivity));
        //   adapter.notifyDataSetChanged();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(getString(R.string.confirmation));
        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.CART_CHECKOUT:
                InvoiceFragment invoiceFragment = new InvoiceFragment();
                invoiceFragment.setModel(cartProductMainClass);
                //mainActivity.setCardFlipAnimationBool(true);
                mainActivity.replaceFragment(invoiceFragment, true, true);
                break;
        }
    }

    @OnClick({R.id.rvOrderSummary, R.id.btnCheckout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rvOrderSummary:
                break;
            case R.id.btnCheckout:
                if (number != null && !number.equals("0")) {

                    serviceHelper.enqueueCall(webService.checkout("" + cartProductMainClass.getProductId(),
                            "" + preferenceHelper.getUser().getId(),
                            number, cartProductMainClass.getCart_product().getAmount(), cartProductMainClass.getRent_type_id(), "" + pay_method_type), AppConstant.CART_CHECKOUT);
                } else {
                    serviceHelper.enqueueCall(webService.checkout("" + cartProductMainClass.getProductId(),
                            "" + preferenceHelper.getUser().getId(),
                            "1", cartProductMainClass.getCart_product().getAmount(), null, "" + pay_method_type), AppConstant.CART_CHECKOUT);
                }
                break;
        }
    }

    public void setModel(CartProductMainClass cartProductMainClass, int pay_method_type, String number) {
        this.cartProductMainClass = cartProductMainClass;
        this.pay_method_type = pay_method_type;
        this.number = number;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
