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

public class ChangePasswordFragment extends BaseFragment {
    @BindView(R.id.etCurrPassword)
    EditText etCurrPassword;
    @BindView(R.id.ctCurerPassword)
    CustomTextInputLayout ctCurerPassword;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_paswwrod, container, false);
        ButterKnife.bind(this, view);
        mainActivity.hideBottombar();
        return view;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(getString(R.string.changePassword));
        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSubmit:
                changePassword();
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.USER_CHANGE_PASSWORD:
                UIHelper.hideSoftKeyboards(mainActivity);
               // mainActivity.replaceFragmentWithClearBackStack(new HomeFragment(), true, true);
                break;
        }
    }

    private void changePassword() {
        if (etCurrPassword.getText().toString().length() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.current_password_required));
            return;
        }

        if (etPassword.getText().toString().length() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.new_pass_error));
            return;
        }
        if (etConfirmPassword.getText().toString().length() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.confirmpass_error));
            return;
        }

        if (etPassword.getText().toString().length() < 6) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.password_characters_en));
            return;
        }

        if (etConfirmPassword.getText().toString().length() < 6) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.password_characters_en));
            return;
        }

        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.pass_do_not_match_en));
            return;
        }

        serviceHelper.enqueueCall(webService.changepassword(preferenceHelper.getUser().getId(), etPassword.getText().toString(), etCurrPassword.getText().toString()), AppConstant.USER_CHANGE_PASSWORD);
    }

}
