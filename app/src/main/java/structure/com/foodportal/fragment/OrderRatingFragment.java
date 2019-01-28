package structure.com.foodportal.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentOrderRatingBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.TaskDoneListener;
import structure.com.foodportal.models.Order;

public class OrderRatingFragment extends BaseFragment implements View.OnClickListener {
    FragmentOrderRatingBinding binding;
    private Order order;
    private TaskDoneListener taskDoneListener;

    public OrderRatingFragment() {
    }

    @SuppressLint("ValidFragment")
    public OrderRatingFragment(Order order) {
        this.order = order;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_rating, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setData();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.rate_review));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRate:
                rate();
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag){
            case AppConstant.RATE_ORDER:
                UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.product_seller_rated));
                if(taskDoneListener != null){
                    taskDoneListener.onTaskDone();
                }
                mainActivity.onBackPressed();
                break;
        }
    }

    private void setListeners(){
        binding.btnRate.setOnClickListener(this);
    }

    private void rate() {
        serviceHelper.enqueueCall(
                webService.RateOrder(
                        order.getProductId() + "",
                        binding.ratingProduct.getRating()  + "",
                        preferenceHelper.getDeviceToken(),
                        preferenceHelper.getUser().getId() + "",
                        order.getOrderProduct().getUserId() + "",
                        binding.ratingSeller.getRating() + "",
                        order.getId() + ""

                ),
                AppConstant.RATE_ORDER
        );
    }

    private void setData() {
        if (order.getOrderProduct() != null) {
            if (order.getOrderProduct().getProductImages() != null) {
                if (order.getOrderProduct().getProductImages().size() > 0) {
                    UIHelper.setImageWithGlide(mainActivity, binding.ivProductImage, order.getOrderProduct().getProductImages().get(0).getProductImage());
                }
            }

            if (order.getOrderProduct().getBrand() != null) {
                binding.tvBrand.setText(order.getOrderProduct().getBrand());
            }
            if (order.getOrderProduct().getTitle() != null) {
                binding.tvProductName.setText(order.getOrderProduct().getTitle());
            }
        }
    }

    public void setTaskDoneListener(TaskDoneListener taskDoneListener) {
        this.taskDoneListener = taskDoneListener;
    }
}
