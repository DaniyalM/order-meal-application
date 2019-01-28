package structure.com.foodportal.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.databinding.LayoutOrderHistoryBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.InputHelper;
import structure.com.foodportal.helper.ServiceHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.SimpleDialogActionListener;
import structure.com.foodportal.interfaces.TaskDoneListener;
import structure.com.foodportal.models.Order;

public class OrderHistoryDetailFragment extends BaseFragment implements View.OnClickListener, TaskDoneListener {
    private MainActivity mainActivity;
    private LayoutOrderHistoryBinding binding;
    private Order order;
    private boolean locationClicked = false;

    public OrderHistoryDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_order_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setOrderDetails();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.orderDetail));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLocation:
                openLocation();
                break;

            case R.id.tvSellerName:
                openSellerDetails();
                break;

            case R.id.tvRentNow:
                openRating();
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag){
            case AppConstant.MARK_ORDER:
                order.setStatus(AppConstant.OrderStatuses.COMPLETED);
                setOrderDetails();
                break;
        }
    }

    @Override
    public void onTaskDone() {
        order.setIsRated(1);
        setOrderDetails();
    }

    private void setListeners() {
        binding.tvLocation.setOnClickListener(this);
        binding.tvSellerName.setOnClickListener(this);
        binding.tvRentNow.setOnClickListener(this);
    }

    private void setOrderDetails() {
        if (order != null) {
            if (order.getOrderProduct() != null) {
                if (order.getOrderProduct().getProductImages().size() > 0) {
                    UIHelper.setImageWithGlide(mainActivity, binding.ivProductImage, order.getOrderProduct().getProductImages().get(0).getProductImage());
                }
            }
            binding.tvproducttitle.setText(order.getOrderProduct().getBrand());
            binding.tvproductdescription.setText(order.getOrderProduct().getTitle());
            binding.tvproductprice.setText(AppConstant.CURRENCY_UNIT + " " + order.getOrderProduct().getAmount());
            binding.tvOrderId.setText(order.getId() + "");
            if (order.getOrderProduct().getProductOn() == AppConstant.ProductOn.RENT) {
                binding.tvOrderType.setText(mainActivity.getResources().getString(R.string.rent_only));
                if (order.getQuantity() > 0) {
                    if (order.getRent_type_id() != null) {
                        switch (order.getRent_type_id()) {
                            case AppConstant.RentTypes.PER_HOUR:
                                binding.tvOrderDuration.setText(order.getQuantity() == 1 ? order.getQuantity() + " " + mainActivity.getResources().getString(R.string.hour_singular) : order.getQuantity() + " " + mainActivity.getResources().getString(R.string.hour_plural));
                                break;

                            case AppConstant.RentTypes.PER_DAY:
                                binding.tvOrderDuration.setText(order.getQuantity() == 1 ? order.getQuantity() + " " + mainActivity.getResources().getString(R.string.day_singular) : order.getQuantity() + " " + mainActivity.getResources().getString(R.string.days_plural));
                                break;

                            case AppConstant.RentTypes.PER_WEEK:
                                binding.tvOrderDuration.setText(order.getQuantity() == 1 ? order.getQuantity() + " " + mainActivity.getResources().getString(R.string.week_singular) : order.getQuantity() + " " + mainActivity.getResources().getString(R.string.week_plural));
                                break;

                            case AppConstant.RentTypes.PER_MONTH:
                                binding.tvOrderDuration.setText(order.getQuantity() == 1 ? order.getQuantity() + " " + mainActivity.getResources().getString(R.string.month_singular) : order.getQuantity() + " " + mainActivity.getResources().getString(R.string.months_plural));
                                break;
                        }

                    } else {
                        binding.tvOrderDuration.setText(order.getQuantity() == 1 ? order.getQuantity() + " " + mainActivity.getResources().getString(R.string.day_singular) : order.getQuantity() + " " + mainActivity.getResources().getString(R.string.days_plural));
                    }
                    binding.llDuration.setVisibility(View.VISIBLE);
                }
            } else if (order.getOrderProduct().getProductOn() == AppConstant.ProductOn.SALE) {
                binding.tvOrderType.setText(mainActivity.getResources().getString(R.string.sale_only));
            }
            if (order.getPickup_date() != null) {
                binding.tvOrderPickupDateTime.setText(
                        UIHelper.getFormattedDate(order.getPickup_date(), AppConstant.SERVER_DATE_FORMAT, "MMMM dd, yyyy")
                                + " at " +
                                UIHelper.getFormattedDate(order.getPickup_date(), AppConstant.SERVER_DATE_FORMAT, "h:mm a")
                );
            } else {
                binding.tvOrderPickupDateTime.setText(mainActivity.getResources().getString(R.string.not_available));
            }
            binding.tvSellerName.setText(Html.fromHtml("<u>" + order.getOrderProduct().getProduct_user().getFullName() + "</u>"));
            binding.tvLocation.setText(order.getOrderProduct().getLocation());

            switch (order.getStatus()) {
                case AppConstant.OrderStatuses.CANCELLED:
                    binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorRed));
                    binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.order_rejected));
                    binding.tvOrderPickupDateTime.setText(mainActivity.getResources().getString(R.string.order_rejected));
                    binding.tvRentNow.setVisibility(View.GONE);
                    break;

                case AppConstant.OrderStatuses.PENDING:
                    binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusSuccess));
                    binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.ready_to_be_approved));
                    binding.tvOrderPickupDateTime.setText(mainActivity.getResources().getString(R.string.ready_to_be_approved));
                    binding.tvRentNow.setVisibility(View.GONE);
                    break;

                case AppConstant.OrderStatuses.ACCEPTED:
                    binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusSuccess));
                    binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.ready_to_be_picked));
                    binding.tvRentNow.setText(Html.fromHtml("<u><font color='#colorNewBlue'> " + mainActivity.getResources().getString(R.string.order_received) + "</u>"));
                    binding.tvRentNow.setVisibility(View.VISIBLE);
                    break;

                case AppConstant.OrderStatuses.COMPLETED:
                    binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusWarning));
                    binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.ready_to_be_completed));
                    if (order.getIsRated() == 0) {
                        binding.tvRentNow.setText(Html.fromHtml("<u><font color='#colorNewBlue'> " + mainActivity.getResources().getString(R.string.rate_now) + "</u>"));
                        binding.tvRentNow.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvRentNow.setVisibility(View.GONE);
                    }
                    break;
            }

            binding.tvOrderStatus.setEnabled(true);
        }
    }

    private void openLocation() {
        if (order != null) {
            if (!locationClicked) {
                locationClicked = true;
                binding.tvLocation.setAlpha((float) 0.75);
                mainActivity.openMap(
                        Double.parseDouble(order.getOrderProduct().getLatitude()),
                        Double.parseDouble(order.getOrderProduct().getLongitude()),
                        order.getOrderProduct().getTitle(),
                        order.getOrderProduct().getLocation()
                );
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        locationClicked = false;
                        binding.tvLocation.setAlpha((float) 1);
                    }
                }, 5000);
            }
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.order_details_not_found));
        }
    }

    private void openSellerDetails() {
        if (order != null) {
            UIHelper.showSimpleDialog(
                    mainActivity,
                    0,
                    order.getOrderProduct().getProduct_user().getFullName(),
                    mainActivity.getResources().getString(R.string.do_you_want_to),
                    mainActivity.getResources().getString(R.string.call_seller),
                    mainActivity.getResources().getString(R.string.message_seller),
                    true,
                    false,
                    new SimpleDialogActionListener() {
                        @Override
                        public void onDialogActionListener(DialogInterface dialog, int which, boolean positive, boolean logout) {
                            if (positive) {
                                InputHelper.call(order.getOrderProduct().getProduct_user().getCountryCode() + order.getOrderProduct().getProduct_user().getPhoneNo(), mainActivity);
                            } else {
                                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                sendIntent.setData(Uri.parse("smsto:" + order.getOrderProduct().getProduct_user().getCountryCode() + order.getOrderProduct().getProduct_user().getPhoneNo()));
                                sendIntent.putExtra("sms_body", "");
                                mainActivity.startActivity(sendIntent);
                            }

                        }
                    }

            );
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.order_details_not_found));
        }
    }

    private void openRating() {
        if (order.getStatus() == AppConstant.OrderStatuses.ACCEPTED) {
            UIHelper.showSimpleDialog(
                    mainActivity,
                    0,
                    mainActivity.getResources().getString(R.string.receive_confirmation),
                    mainActivity.getResources().getString(R.string.receive_confirmation_message),
                    mainActivity.getResources().getString(R.string.yes),
                    mainActivity.getResources().getString(R.string.no),
                    false,
                    false,
                    new SimpleDialogActionListener() {
                        @Override
                        public void onDialogActionListener(DialogInterface dialog, int which, boolean positive, boolean logout) {
                            if (positive) {
                                changeOrderStatus(order.getId(), AppConstant.OrderStatuses.COMPLETED);
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }
            );
        } else if (order.getStatus() == AppConstant.OrderStatuses.COMPLETED) {
            OrderRatingFragment orderRatingFragment = new OrderRatingFragment(order);
            orderRatingFragment.setTaskDoneListener(this);
            mainActivity.replaceFragment(orderRatingFragment, true, true);
        }
    }

    public void changeOrderStatus(int id, int statusId) {
        serviceHelper.enqueueCall(
                webService.acceptOrder(
                        id + "",
                        statusId,
                        order.getPaymentDate()
                ),
                AppConstant.MARK_ORDER);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setBinding(LayoutOrderHistoryBinding binding) {
        this.binding = binding;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
