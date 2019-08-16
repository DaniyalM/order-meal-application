package structure.com.foodportal.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.activity.MapActivity;
import structure.com.foodportal.databinding.LayoutOrderHistoryBinding;
import structure.com.foodportal.fragment.OrderHistoryDetailFragment;
import structure.com.foodportal.fragment.OrderRatingFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.InputHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.ArrayPositionListener;
import structure.com.foodportal.interfaces.SimpleDialogActionListener;
import structure.com.foodportal.models.Order;

public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.Holder> {
    private MainActivity mainActivity;
    private LayoutOrderHistoryBinding binding;
    private ArrayList<Order> arrayList;
    private boolean locationClicked = false;
    private ArrayPositionListener arrayPositionListener;

    public OrdersHistoryAdapter(MainActivity mainActivity, ArrayPositionListener arrayPositionListener) {
        this.mainActivity = mainActivity;
        this.arrayList = new ArrayList<>();
        this.arrayPositionListener = arrayPositionListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mainActivity), R.layout.layout_order_history, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (arrayList.get(position).getOrderProduct() != null) {
            if (arrayList.get(position).getOrderProduct().getProductImages().size() > 0) {
                UIHelper.setImageWithGlide(mainActivity, binding.ivProductImage, arrayList.get(position).getOrderProduct().getProductImages().get(0).getProductImage());
            }
        }
        holder.binding.tvproducttitle.setText(arrayList.get(position).getOrderProduct().getBrand());
        holder.binding.tvproductdescription.setText(arrayList.get(position).getOrderProduct().getTitle());
        holder.binding.tvproductprice.setText(AppConstant.CURRENCY_UNIT + " " + arrayList.get(position).getOrderProduct().getAmount());
        holder.binding.tvOrderId.setText(arrayList.get(position).getId() + "");
        if (arrayList.get(position).getOrderProduct().getProductOn() == AppConstant.ProductOn.RENT) {
            holder.binding.tvOrderType.setText(mainActivity.getResources().getString(R.string.rent_only));
            if (arrayList.get(position).getQuantity() > 0) {
                if (arrayList.get(position).getRent_type_id() != null) {
                    switch (arrayList.get(position).getRent_type_id()) {
                        case AppConstant.RentTypes.PER_HOUR:
                            holder.binding.tvOrderDuration.setText(arrayList.get(position).getQuantity() == 1 ? arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.hour_singular) : arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.hour_plural));
                            break;

                        case AppConstant.RentTypes.PER_DAY:
                            holder.binding.tvOrderDuration.setText(arrayList.get(position).getQuantity() == 1 ? arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.day_singular) : arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.days_plural));
                            break;

                        case AppConstant.RentTypes.PER_WEEK:
                            holder.binding.tvOrderDuration.setText(arrayList.get(position).getQuantity() == 1 ? arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.week_singular) : arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.week_plural));
                            break;

                        case AppConstant.RentTypes.PER_MONTH:
                            holder.binding.tvOrderDuration.setText(arrayList.get(position).getQuantity() == 1 ? arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.month_singular) : arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.months_plural));
                            break;
                    }

                } else {
                    holder.binding.tvOrderDuration.setText(arrayList.get(position).getQuantity() == 1 ? arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.day_singular) : arrayList.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.days_plural));
                }
                holder.binding.llDuration.setVisibility(View.VISIBLE);
            }
        } else if (arrayList.get(position).getOrderProduct().getProductOn() == AppConstant.ProductOn.SALE) {
            holder.binding.tvOrderType.setText(mainActivity.getResources().getString(R.string.sale_only));
        }
        if (arrayList.get(position).getPickup_date() != null) {
            holder.binding.tvOrderPickupDateTime.setText(
                    UIHelper.getFormattedDate(arrayList.get(position).getPickup_date(), AppConstant.SERVER_DATE_FORMAT, "MMMM dd, yyyy")
                            + " at " +
                            UIHelper.getFormattedDate(arrayList.get(position).getPickup_date(), AppConstant.SERVER_DATE_FORMAT, "h:mm a")
            );
        } else {
            holder.binding.tvOrderPickupDateTime.setText(mainActivity.getResources().getString(R.string.not_available));
        }
        holder.binding.tvSellerName.setText(Html.fromHtml("<u>" + arrayList.get(position).getOrderProduct().getProduct_user().getFullName() + "</u>"));
        holder.binding.tvLocation.setText(arrayList.get(position).getOrderProduct().getLocation());

        switch (arrayList.get(position).getStatus()) {
            case AppConstant.OrderStatuses.CANCELLED:
                holder.binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorRed));
                holder.binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.order_rejected));
                holder.binding.tvOrderPickupDateTime.setText(mainActivity.getResources().getString(R.string.order_rejected));
                holder.binding.tvRentNow.setVisibility(View.GONE);
                break;

            case AppConstant.OrderStatuses.PENDING:
                holder.binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusSuccess));
                holder.binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.ready_to_be_approved));
                holder.binding.tvOrderPickupDateTime.setText(mainActivity.getResources().getString(R.string.ready_to_be_approved));
                holder.binding.tvRentNow.setVisibility(View.GONE);
                break;

            case AppConstant.OrderStatuses.ACCEPTED:
                holder.binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusSuccess));
                holder.binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.ready_to_be_picked));
                holder.binding.tvRentNow.setText(Html.fromHtml("<u><font color='#colorNewBlue'> " + mainActivity.getResources().getString(R.string.order_received) + "</u>"));
                holder.binding.tvRentNow.setVisibility(View.VISIBLE);
                break;

            case AppConstant.OrderStatuses.COMPLETED:
                holder.binding.tvOrderStatus.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusWarning));
                holder.binding.tvOrderStatus.setText(mainActivity.getResources().getString(R.string.ready_to_be_completed));
                if (arrayList.get(position).getIsRated() == 0) {
                    holder.binding.tvRentNow.setText(Html.fromHtml("<u><font color='#colorNewBlue'> " + mainActivity.getResources().getString(R.string.rate_now) + "</u>"));
                    holder.binding.tvRentNow.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.tvRentNow.setVisibility(View.GONE);
                }
                break;
        }

        holder.binding.tvOrderStatus.setEnabled(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHistoryDetailFragment detailsFragment = new OrderHistoryDetailFragment();
                detailsFragment.setMainActivity(mainActivity);
                detailsFragment.setOrder(arrayList.get(position));
                mainActivity.replaceFragment(detailsFragment, true, true);
            }
        });

        holder.binding.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationClicked) {
                    locationClicked = true;
                    holder.binding.tvLocation.setAlpha((float) 0.75);
                    openMap(
                            Double.parseDouble(arrayList.get(position).getOrderProduct().getLatitude()),
                            Double.parseDouble(arrayList.get(position).getOrderProduct().getLongitude()),
                            arrayList.get(position).getOrderProduct().getTitle(),
                            arrayList.get(position).getOrderProduct().getLocation()
                    );
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            locationClicked = false;
                            holder.binding.tvLocation.setAlpha((float) 1);
                        }
                    }, 5000);
                }
            }
        });

        holder.binding.tvSellerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showSimpleDialog(
                        mainActivity,
                        0,
                        arrayList.get(position).getOrderProduct().getProduct_user().getFullName(),
                        mainActivity.getResources().getString(R.string.do_you_want_to),
                        mainActivity.getResources().getString(R.string.call_seller),
                        mainActivity.getResources().getString(R.string.message_seller),
                        true,
                        false,
                        new SimpleDialogActionListener() {
                            @Override
                            public void onDialogActionListener(DialogInterface dialog, int which, boolean positive, boolean logout) {
//                                UIHelper.showToast(mainActivity, "testing");

                                if (positive) {
                                    InputHelper.call(arrayList.get(position).getOrderProduct().getProduct_user().getCountryCode() + arrayList.get(position).getOrderProduct().getProduct_user().getPhoneNo(), mainActivity);
                                } else {
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                    sendIntent.setData(Uri.parse("smsto:" + arrayList.get(position).getOrderProduct().getProduct_user().getCountryCode() + arrayList.get(position).getOrderProduct().getProduct_user().getPhoneNo()));
                                    sendIntent.putExtra("sms_body", "");
                                    mainActivity.startActivity(sendIntent);
                                }

                            }
                        }

                );
            }
        });

        holder.binding.tvRentNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.get(position).getStatus() == AppConstant.OrderStatuses.ACCEPTED) {
                    UIHelper.showSimpleDialog(
                            mainActivity,
                            0,
                            mainActivity.getResources().getString(R.string.receive_confirmation),
                            mainActivity.getResources().getString(R.string.receive_confirmation_message),
                            mainActivity.getResources().getString(R.string.yes_en),
                            mainActivity.getResources().getString(R.string.no),
                            false,
                            false,
                            new SimpleDialogActionListener() {
                                @Override
                                public void onDialogActionListener(DialogInterface dialog, int which, boolean positive, boolean logout) {
                                    if (positive) {
                                        receivedConfirmation(position);
                                        dialog.dismiss();
                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                            }
                    );
                } else if (arrayList.get(position).getStatus() == AppConstant.OrderStatuses.COMPLETED) {
                    mainActivity.replaceFragment(new OrderRatingFragment(arrayList.get(position)), true, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addAll(ArrayList<Order> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
    }

    private void openMap(double latitude, double longitude, String title, String label) {
        Intent mapActivity = new Intent(mainActivity, MapActivity.class);
        mapActivity.putExtra("latitude", latitude);
        mapActivity.putExtra("longitude", longitude);
        mapActivity.putExtra("title", title);
        mapActivity.putExtra("label", label);
        mainActivity.startActivity(mapActivity);
    }

    private void receivedConfirmation(int position) {
        this.arrayPositionListener.onPositionReceived(position);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        LayoutOrderHistoryBinding binding;

        Holder(LayoutOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
