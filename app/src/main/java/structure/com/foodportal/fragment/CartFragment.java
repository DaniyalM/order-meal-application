package structure.com.foodportal.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.CartAdapter;
import structure.com.foodportal.adapter.OrderAdapter;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.CartListner;
import structure.com.foodportal.interfaces.OrderListner;
import structure.com.foodportal.models.CartModule.CartProduct;
import structure.com.foodportal.models.CartModule.CartProductMainClass;
import structure.com.foodportal.models.Order;


public class CartFragment extends BaseFragment implements CartListner, OrderListner {


    CartAdapter adapter;
    OrderAdapter orderAdapter;
    ArrayList<CartProductMainClass> cartProductMainClasses;
    ArrayList<Order> orders;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManagerHistory;
    @BindView(R.id.rdBag)
    RadioButton rdBag;
    @BindView(R.id.rdStore)
    RadioButton rdStore;
    @BindView(R.id.rvOrderHistory)
    RecyclerView rvOrderHistory;
    @BindView(R.id.swapOrder)
    SwipeRefreshLayout swapOrder;
    @BindView(R.id.rdpending)
    RadioButton rdpending;
    @BindView(R.id.rdcompleted)
    RadioButton rdcompleted;
    @BindView(R.id.llcartEmpty)
    LinearLayout llcartEmpty;
    @BindView(R.id.llradioOrders)
    LinearLayout llradioOrders;
    @BindView(R.id.rGbagAndStore)
    RadioGroup rGbagAndStore;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.llStore)
    LinearLayout llStore;
    private Titlebar titlebar;

    boolean fromNotification = false;
    int pendingCounts, completedCounts;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.showTitlebar();
        titlebar.setTitle(getString(R.string.my_bag));
        titlebar.showBackButton(mainActivity);


    }

    ViewAnimator llCartAnimator, llstoreAnimator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cart_one, container, false);
        ButterKnife.bind(this, view);


        init();
        getCart();
        return view;
    }

    private void getCart() {
        serviceHelper.enqueueCall(webService.getCart("" + preferenceHelper.getUser().getId()), AppConstant.GET_CART);
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.GET_CART:
                if (((ArrayList<CartProductMainClass>) result).size() == 0) {
                    rvOrderHistory.setVisibility(View.GONE);
                    llcartEmpty.setVisibility(View.VISIBLE);

                } else {
                    cartProductMainClasses.clear();
                    cartProductMainClasses.addAll((ArrayList<CartProductMainClass>) result);
                    adapter.notifyDataSetChanged();
                    rvOrderHistory.setVisibility(View.VISIBLE);
                    llcartEmpty.setVisibility(View.GONE);
                    if (isFromNotification()) {
                        rdStore.setChecked(true);

                        getOrders();
                    }
                }


                break;

            case AppConstant.REMOVE_FROM_CART:

                getCart();

                break;


            case AppConstant.GET_ORDERS:


                orders.clear();
                orders.addAll((ArrayList<Order>) result);
                pendingCounts = getCount(orders, AppConstant.OrderStatuses.PENDING);
                completedCounts = getCount(orders, AppConstant.OrderStatuses.COMPLETED);
                setOrders(orders, AppConstant.OrderStatuses.PENDING);
                break;
            case AppConstant.MARK_ORDER:
                getOrders();
                break;
        }
    }

    private void init() {

        rdBag.setChecked(true);
        mainActivity.hideBottombar();
        rdpending.setTextColor(mainActivity.getResources().getColor(R.color.white));
        rdcompleted.setTextColor(mainActivity.getResources().getColor(R.color.colorBlack));

        cartProductMainClasses = new ArrayList<>();
        orders = new ArrayList<>();


        linearLayoutManager = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        linearLayoutManagerHistory = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);

        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);


        rvOrderHistory.setLayoutManager(linearLayoutManager);
        rvOrderHistory.setItemAnimator(defaultItemAnimator);

        adapter = new CartAdapter(mainActivity, cartProductMainClasses, this);
        orderAdapter = new OrderAdapter(mainActivity, orders, this);
        rvOrderHistory.setAdapter(adapter);


        swapOrder.setOnRefreshListener(() -> swapOrder.setRefreshing(false));

        rGbagAndStore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.rdBag:
                        initCartHistory();
                        llradioOrders.setVisibility(View.GONE);
                     //   UIHelper.animation(Techniques.SlideOutDown,1000,0,llradioOrders);
                        getCart();
                        break;

                    case R.id.rdStore:

                        llradioOrders.setVisibility(View.VISIBLE);
                        UIHelper.animation(Techniques.SlideInDown,450,0,llradioOrders);
                        initOrderHistory();
                        rdcompleted.setChecked(false);
                        rdpending.setChecked(true);
                        rdpending.setTextColor(mainActivity.getResources().getColor(R.color.white));
                        rdcompleted.setTextColor(mainActivity.getResources().getColor(R.color.colorBlack));
                        getOrders();
                        setOrders(orders, AppConstant.OrderStatuses.PENDING);
                        break;


                }

            }
        });
    }

    public void initOrderHistory() {

        rvOrderHistory.setAdapter(orderAdapter);
    }

    public void initCartHistory() {


        rvOrderHistory.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    public void removeItemFromCart(int pos, CartProduct cartProduct) {

        serviceHelper.enqueueCall(webService.removeProductfromCart("" + cartProduct.getId(), "" + preferenceHelper.getUser().getId(), preferenceHelper.getDeviceToken()), AppConstant.REMOVE_FROM_CART);

    }


    @Override
    public void removefromcart(CartProductMainClass cartProductMainClass, int pos) {

        DialogFactory.createMessageDialog2(mainActivity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                removeItemFromCart(pos, cartProductMainClass.getCart_product());

            }
        },null, getString(R.string.remove_from_cart_message), getString(R.string.alert)).show();


    }

    @Override
    public void onClickItem(CartProductMainClass cartProductMainClass, int position) {


        PaymentTypeFragment paymentTypeFragment = new PaymentTypeFragment();
        if (selectnumber != null && !selectnumber.equals("0")) {

            paymentTypeFragment.setCartProductMainClass(cartProductMainClass, selectnumber);

        } else {
            paymentTypeFragment.setCartProductMainClass(cartProductMainClass, null);

        }

        mainActivity.replaceFragment(paymentTypeFragment, true, true);

    }

    @Override
    public void dropDownDaysSelecotr(CartProductMainClass cartProductMainClass, int position) {


    }

    String selectnumber = "1";

    @Override
    public void onDialogClick(String number) {

        selectnumber = number;


    }

    @OnClick({R.id.rdBag, R.id.rdStore, R.id.rdpending, R.id.rdcompleted})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.rdpending:
                UIHelper.animation(Techniques.Pulse,600,0,rdpending);

                rdpending.setTextColor(mainActivity.getResources().getColor(R.color.white));
                rdcompleted.setTextColor(mainActivity.getResources().getColor(R.color.colorBlack));
                rdpending.setChecked(true);
                rdcompleted.setChecked(false);
                setOrders(orders, AppConstant.OrderStatuses.PENDING);
                break;
            case R.id.rdcompleted:

                UIHelper.animation(Techniques.Pulse,600,0,rdcompleted);

                rdcompleted.setTextColor(mainActivity.getResources().getColor(R.color.white));
                rdpending.setTextColor(mainActivity.getResources().getColor(R.color.colorBlack));
                rdcompleted.setChecked(true);
                rdpending.setChecked(false);
                setOrders(orders, AppConstant.OrderStatuses.COMPLETED);
                break;
        }
    }

    private void setOrders(ArrayList<Order> orders, int type) {
        ArrayList<Order> sortedOrders = new ArrayList<>();
        for (int pos = 0; pos < orders.size(); pos++) {
            if (orders.get(pos).getStatus() == type) {
                sortedOrders.add(orders.get(pos));
            }
        }
        rvOrderHistory.removeAllViews();
        orderAdapter.addAll(sortedOrders);
        rdpending.setText(pendingCounts+"\n"+mainActivity.getString(R.string.pending_orders));
        rdcompleted.setText(completedCounts+"\n"+mainActivity.getString(R.string.completed_order));
        if (sortedOrders.size() > 0) {
            rvOrderHistory.setVisibility(View.VISIBLE);
            llcartEmpty.setVisibility(View.GONE);
        } else {
            rvOrderHistory.setVisibility(View.GONE);
            llcartEmpty.setVisibility(View.VISIBLE);

        }

        orderAdapter.notifyDataSetChanged();
    }


    private int getCount(ArrayList<Order> orders, int type) {
        int count = 0;
        for (int pos = 0; pos < orders.size(); pos++) {
            if (orders.get(pos).getStatus() == type) {
                count++;
            }
        }
        return count;
    }

    public void getOrders() {

        serviceHelper.enqueueCall(webService.GetOrders("" + preferenceHelper.getUser().getId()), AppConstant.GET_ORDERS);


    }

    @Override
    public void Click(int pos) {

    }

    @Override
    public void Accept(Order order, int pos, String dateandtime) {
        serviceHelper.enqueueCall(webService.acceptOrder("" + order.getId(), AppConstant.OrderStatuses.ACCEPTED, dateandtime), AppConstant.MARK_ORDER);
    }

    @Override
    public void Reject(Order order, int pos) {
        serviceHelper.enqueueCall(webService.acceptOrder("" + order.getId(), AppConstant.OrderStatuses.CANCELLED, null), AppConstant.MARK_ORDER);

    }

    public boolean isFromNotification() {
        return fromNotification;
    }

    public void setFromNotification(boolean fromNotification) {
        this.fromNotification = fromNotification;
    }
}
