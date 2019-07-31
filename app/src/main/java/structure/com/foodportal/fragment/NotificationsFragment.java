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

import structure.com.foodportal.databinding.FragmentNotificationsBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.models.Notification;

public class NotificationsFragment extends BaseFragment {
    FragmentNotificationsBinding binding;
    ArrayList<Notification> arrayList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
  //  NotificationAdapter adapter;

    public NotificationsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mainActivity.hideBottombar();

      //  adapter = new NotificationAdapter(mainActivity);
        linearLayoutManager = new LinearLayoutManager(mainActivity);
        binding.recyclerview.setLayoutManager(linearLayoutManager);
       // binding.recyclerview.setAdapter(adapter);

        getNotifications();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.showTitlebar();
        titlebar.showMenuButton(mainActivity);
        titlebar.setTitle(mainActivity.getResources().getString(R.string.notifications));
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.GET_NOTIFICATIONS:
                setNotificationsListing((ArrayList<Notification>) result);
                break;
        }
    }

    private void getNotifications() {
        serviceHelper.enqueueCall(webService.GetNotifications(preferenceHelper.getUser().getId() + ""), AppConstant.GET_NOTIFICATIONS);
    }

    private void setNotificationsListing(ArrayList<Notification> arrayList) {
        this.arrayList.clear();
        if (arrayList.size() == 0) {
            binding.recyclerview.setVisibility(View.GONE);
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
        } else {
            this.arrayList.addAll(arrayList);
            Collections.reverse(this.arrayList);
          //  adapter.addAllToAdapter(this.arrayList);
          //  adapter.notifyDataSetChanged();
            binding.tvNoDataFound.setVisibility(View.GONE);
            binding.recyclerview.setVisibility(View.VISIBLE);
        }
    }
}
