package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import structure.com.foodportal.R;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomTextInputLayout;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

public class ForgotChangePassword extends BaseFragment {


    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ctPassword)
    CustomTextInputLayout ctPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.ctConfirmPassword)
    CustomTextInputLayout ctConfirmPassword;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private String email;
    private String code;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_changepassword, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(registrationActivity.getResources().getString(R.string.forgot_password));
        titlebar.showTitlebar();
        titlebar.showBackButton(registrationActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSubmit:
                UIHelper.hideSoftKeyboards(registrationActivity);

                if (etPassword.getText().toString().length() == 0) {
                    UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_required));
                    return;
                }

                if (etPassword.getText().toString().length() < 6) {
                    UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters));
                    return;
                }

                if (etConfirmPassword.getText().toString().length() == 0) {
                    UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.confirm_password_required));
                    return;
                }

                if (etConfirmPassword.getText().toString().length() < 6) {
                    UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.password_characters));
                    return;
                }

                if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.samepass_error));
                    return;
                }

                serviceHelper.enqueueCall(webService.forgotChangePassword(email, code, etPassword.getText().toString()), AppConstant.UPDATE_PASSWORD);

                break;
        }
    }

    public void setEmailandCode(String email, String code) {

        this.email = email;
        this.code = code;
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.UPDATE_PASSWORD:
               // registrationActivity.replaceFragmentWithClearBackStack(new LoginFragment(), true, true);
                break;
        }
    }

}
