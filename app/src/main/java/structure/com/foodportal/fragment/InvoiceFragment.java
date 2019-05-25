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
import structure.com.foodportal.adapter.OrderSummaryAdapter;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.CartModule.CartProductMainClass;

public class InvoiceFragment extends BaseFragment {


    @BindView(R.id.rvOrderInvoice)
    RecyclerView rvOrderInvoice;
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
    OrderSummaryAdapter adapter;
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
    @BindView(R.id.VATPercent)
    TextView VATPercent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        tvBeforeVat.setText(cartProductMainClass.getCart_product().getAmount() + " AED");
        VATPercent.setText("VAT Charges(" + cartProductMainClass.getCart_product().getVat() + "%)");
        Float vatcharges = (Float.valueOf(cartProductMainClass.getCart_product().getAmount()) * cartProductMainClass.getCart_product().getVat() / 100);

        tvVatCharges.setText("" + vatcharges + " AED");
        tvGrandtotal.setText("" + (Float.valueOf(cartProductMainClass.getCart_product().getAmount()) - vatcharges) + " AED");
        tvProductCatogory.setText(cartProductMainClass.getCart_product().getTitle());
        tvProductName.setText(cartProductMainClass.getCart_product().getModelNumber());
        tvProductLocation.setText(cartProductMainClass.getCart_product().getLocation());
        tvPriceTag.setText("AED " + cartProductMainClass.getCart_product().getAmount());
//        tvProductCatogory.setText(cartProductMainClass.getCart_product().getTitle());
//        tvProductName.setText(cartProductMainClass.getCart_product().getModelNumber());
//        tvProductLocation.setText(cartProductMainClass.getCart_product().getLocation());
//        tvPriceTag.setText(cartProductMainClass.getCart_product().getAmount());
        if (cartProductMainClass.getCart_product().getProductRentType() != null) {

            tvPriceType.setText(cartProductMainClass.getCart_product().getProductRentType().getName());

        } else {


        }
        UIHelper.setImagewithGlide(mainActivity, ivProductImage, cartProductMainClass.getCart_product().getProduct_images().get(0).getProductImage());
        tvGrandtotal.setText(cartProductMainClass.getCart_product().getAmount());

//        adapter = new OrderSummaryAdapter(mainActivity);
//        rvOrderInvoice.setAdapter(adapter);
//        rvOrderInvoice.setLayoutManager(new LinearLayoutManager(mainActivity));
//        adapter.notifyDataSetChanged();

    }


    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(getString(R.string.invoice));
        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @OnClick(R.id.btnCheckout)
    public void onViewClicked() {

       // mainActivity.replaceFragmentWithClearBackStack(new HomeFragment(), true, false);

    }

    public void setModel(CartProductMainClass cartProductMainClass) {
        this.cartProductMainClass = cartProductMainClass;
    }
}
