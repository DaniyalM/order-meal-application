package structure.com.foodportal.fragment;


import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.SignupAdapter;
import structure.com.foodportal.databinding.FragmentSignupFinalBinding;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.OnPictureUploadedListener;
import structure.com.foodportal.models.UserModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFinalFragment extends BaseFragment implements View.OnClickListener, OnPictureUploadedListener {

    FragmentSignupFinalBinding binding;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.ivPhoto)
    CircleImageView ivPhoto;
    Unbinder unbinder;
    private String path;
    private CircleIndicator circleIndicator;
    SignupAdapter signupAdapter;

    public SignupFinalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SignupFinalFragment(SignupAdapter signupAdapter, CircleIndicator circleIndicator) {
        this.signupAdapter = signupAdapter;
        this.circleIndicator = circleIndicator;
    }

    public void setImage(String path) {

        this.path = path;

    }


    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(getString(R.string.sign_up));
        titlebar.showTitlebar();
        titlebar.showCrossButton().setOnClickListener(v -> {

            registrationActivity.onBackPressed();

        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.signupAdapter.setOnPictureUploadedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_final, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
      /*  if(circleIndicator != null && registrationActivity.getSignupPager().getCurrentItem() == 6){
            circleIndicator.setBackgroundColor(registrationActivity.getResources().getColor(R.color.colorBlue));
        }*/
        setlistners();
        return binding.getRoot();
    }

    private void setlistners() {
        UserModel user = preferenceHelper.getUser();
        tvName.setText(user.getFullName());
        /// UIHelper.showToast(registrationActivity,user.getProfileImage());
        UIHelper.setImagewithGlide(registrationActivity, ivPhoto, user.getProfileImage());
        binding.btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnConfirm:
                UIHelper.hideSoftKeyboards(registrationActivity);
                registrationActivity.showMainActivity();
                break;

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onPictureUploaded(String url) {
        UIHelper.setImagewithGlide(registrationActivity, ivPhoto, url);
    }
}
