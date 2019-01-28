package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.OrdersHistoryAdapter;
import structure.com.foodportal.databinding.FragmentOrdersHistoryBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.ArrayPositionListener;
import structure.com.foodportal.models.Order;

public class OrdersHistoryFragment extends BaseFragment implements ArrayPositionListener {
    FragmentOrdersHistoryBinding binding;
    ArrayList<Order> arrayList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    OrdersHistoryAdapter adapter;
    int activePosition = 0, actionId = 0;
    private boolean fromNotification;

    public OrdersHistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity.hideBottombar();

        adapter = new OrdersHistoryAdapter(mainActivity, this);
        linearLayoutManager = new LinearLayoutManager(mainActivity);
        binding.recyclerview.setLayoutManager(linearLayoutManager);
        binding.recyclerview.setAdapter(adapter);

        getOrdersHistory();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.orderHistory));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.GET_ORDERS:
                setOrdersListing((ArrayList<Order>) result);
                break;
            case AppConstant.MARK_ORDER:
                arrayList.get(activePosition).setStatus(AppConstant.OrderStatuses.COMPLETED);
                adapter.addAll(arrayList);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onPositionReceived(int position) {
        activePosition = position;
        serviceHelper.enqueueCall(webService.acceptOrder(arrayList.get(position).getId() + "", AppConstant.OrderStatuses.COMPLETED, arrayList.get(position).getPaymentDate()), AppConstant.MARK_ORDER);
    }

    private void getOrdersHistory() {
        serviceHelper.enqueueCall(webService.GetOrders(preferenceHelper.getUser().getId() + ""), AppConstant.GET_ORDERS);
    }

    private void setOrdersListing(ArrayList<Order> arrayList) {
        this.arrayList.clear();
        for (int position = 0; position < arrayList.size(); position++) {
            if (preferenceHelper.getUser().getId() != arrayList.get(position).getOrderProduct().getUserId()) {
                this.arrayList.add(arrayList.get(position));
            }
        }

        if (this.arrayList.size() == 0) {
            binding.recyclerview.setVisibility(View.GONE);
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
        } else {
            Collections.reverse(this.arrayList);
            adapter.addAll(this.arrayList);
            adapter.notifyDataSetChanged();
            binding.tvNoDataFound.setVisibility(View.GONE);
            binding.recyclerview.setVisibility(View.VISIBLE);
            if (fromNotification) {
                for (int position = 0; position < this.arrayList.size(); position++) {
                    if (actionId == this.arrayList.get(position).getId()) {
                        OrderHistoryDetailFragment detailsFragment = new OrderHistoryDetailFragment();
                        detailsFragment.setMainActivity(mainActivity);
                        detailsFragment.setOrder(this.arrayList.get(position));
                        mainActivity.replaceFragment(detailsFragment, true, true);
                        fromNotification = false;
                    }
                }
            }
        }
    }

    public void setFromNotification(boolean fromNotification) {
        this.fromNotification = fromNotification;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
}
