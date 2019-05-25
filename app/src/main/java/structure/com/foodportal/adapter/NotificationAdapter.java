package structure.com.foodportal.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.databinding.LayoutNotificationItemBinding;

import structure.com.foodportal.fragment.OrdersHistoryFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {
    LayoutNotificationItemBinding binding;
    MainActivity mainActivity;
    ArrayList<Notification> arrayList;

    public NotificationAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mainActivity), R.layout.layout_notification_item, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (arrayList.get(position).getUpdatedAt() != null) {
            holder.binding.tvNotificationDate.setText(UIHelper.getFormattedDate(arrayList.get(position).getUpdatedAt(), AppConstant.SERVER_DATE_FORMAT, "MMM d, yyyy"));
        }
        holder.binding.tvNotificationType.setText(arrayList.get(position).getActionType());
        holder.binding.tvNotificationDescription.setText(arrayList.get(position).getMessage());

        holder.binding.rlNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.get(position).getActionType().equals(AppConstant.FcmHelper.ACTION_TYPE_JOB) || arrayList.get(position).getActionType().equals(AppConstant.FcmHelper.COMPLETED)) {
                 
                } else if (arrayList.get(position).getActionType().equals(AppConstant.FcmHelper.ACCEPTED) || arrayList.get(position).getActionType().equals(AppConstant.FcmHelper.CANCELLED)) {
                    OrdersHistoryFragment ordersHistoryFragment = new OrdersHistoryFragment();
                    ordersHistoryFragment.setFromNotification(true);
                    ordersHistoryFragment.setActionId(arrayList.get(position).getActionId());
                    mainActivity.replaceFragment(ordersHistoryFragment, true, false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    public void addAll(ArrayList<Notification> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        LayoutNotificationItemBinding binding;

        Holder(LayoutNotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
