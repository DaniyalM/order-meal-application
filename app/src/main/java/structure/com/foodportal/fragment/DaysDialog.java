package structure.com.foodportal.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.adapter.DaysAdapter;
import structure.com.foodportal.databinding.FragmentDialogDaysBinding;
import structure.com.foodportal.helper.ProductCreation;
import structure.com.foodportal.models.WeekDay;


@SuppressLint("ValidFragment")
public class DaysDialog extends DialogFragment implements View.OnClickListener{
    MainActivity mainActivity;
    FragmentDialogDaysBinding binding;
    DaysAdapter adapter;
    LinearLayoutManager linearLayout;
    ArrayList<WeekDay> days;
    OnDaysSelectedListener listener;
    Dialog dialog;

    public DaysDialog(MainActivity mainActivity, ArrayList<WeekDay> days, OnDaysSelectedListener listener) {
        this.mainActivity = mainActivity;
        this.days = days;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_days, container, false);

        adapter = new DaysAdapter(mainActivity);
        linearLayout = new LinearLayoutManager(mainActivity);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(mainActivity, 2));
        binding.recyclerview.setAdapter(adapter);

        adapter.addAll(days);
        adapter.notifyDataSetChanged();

        binding.btnCancel.setOnClickListener(this);
        binding.btnSelect.setOnClickListener(this);

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel:
                cancel();
                break;

            case R.id.btnSelect:
                select();
                break;
        }
    }

    private void cancel(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    private void select(){
        if(dialog != null){
            dialog.dismiss();
            ProductCreation.getInstance().setDays(adapter.getDays());
            listener.onDaysSelected();
        }
    }

    public interface OnDaysSelectedListener {
        void onDaysSelected();
    }
}
