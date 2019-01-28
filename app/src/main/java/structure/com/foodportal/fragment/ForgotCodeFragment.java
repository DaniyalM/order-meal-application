package structure.com.foodportal.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentForgotCodeBinding;
import structure.com.foodportal.helper.CountDownInterval;
import structure.com.foodportal.helper.Titlebar;


public class ForgotCodeFragment extends BaseFragment implements View.OnClickListener {

    FragmentForgotCodeBinding binding;
    private CountDownTimer counttimer;

    public ForgotCodeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_code, container, false);
        init();
        setTimer();
        return binding.getRoot();
    }

    private void setTimer() {
        if (counttimer != null)
            counttimer.cancel();
        counttimer = new CountDownInterval(binding.tvTimer, 2 * 60 * 1000, 1000) {
            @Override
            public void onFinish() {
                if (binding.tvTimer != null) {
                    binding.tvTimer.setText("00:00");
                    binding.tvResend.setEnabled(true);
                }
            }
        }.start();
    }

    private void init() {

        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
              //  replaceFragment(new ForgotPasswordFragment(), true, true);
                break;
        }
    }
}
