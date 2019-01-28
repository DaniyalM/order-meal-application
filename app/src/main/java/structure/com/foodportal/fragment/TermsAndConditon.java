package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.Titlebar;

public class TermsAndConditon extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tvTermsDemoFirst)
    TextView tvTermsDemoFirst;
    @BindView(R.id.tvTermsDemoSecond)
    TextView tvTermsDemoSecond;

    String key;
    String data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_terms, container, false);
        ButterKnife.bind(this, view);

        if (mainActivity != null) {
            mainActivity.hideBottombar();

        } else {

        }
        // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terms, container, false);
        if(data != null){
            tvTermsDemoFirst.setText(data);
            tvTermsDemoSecond.setVisibility(View.GONE);
        }

        return view;
    }

    public void setkey(String key) {

        this.key = key;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setVisibility(View.VISIBLE);
        switch (key) {

            case "Terms and Condition":
                titlebar.setTitle(getString(R.string.terms_condition));
                break;


            case "Privacy Policy":
                titlebar.setTitle(getString(R.string.privacy_policy));
                break;


            case "About Us":
                titlebar.setTitle(getString(R.string.aboutUs));
                break;

        }
        titlebar.showTitlebar();
        if (registrationActivity != null)
            titlebar.showBackButton(registrationActivity);
        else
            titlebar.showBackButton(mainActivity);


        //   titlebar.showBackButton(registrationActivity);
        titlebar.showTitlebar();
    }
}
