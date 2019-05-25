package structure.com.foodportal.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.helper.CustomTextInputLayout;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;


public class SignupOneFragment extends BaseFragment {

    //    FragmentSignupOneBinding binding;
    Button btnNext;
    EditText etLName, etFirstName;
    CustomTextInputLayout ctFName, ctLName;
    private View view;
    private TextView tvSignin;

    public SignupOneFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_one, container, false);
        view = inflater.inflate(R.layout.fragment_signup_one, container, false);
        init();
        return view;
    }

    private void init() {
        btnNext = view.findViewById(R.id.btnNext);
        etLName = view.findViewById(R.id.etLName);
        etFirstName = view.findViewById(R.id.etFirstName);
        tvSignin = view.findViewById(R.id.tvSignin);
        ctFName = view.findViewById(R.id.ctFName);
        ctLName = view.findViewById(R.id.ctLName);
        ctLName.setErrorEnabled();
        ctFName.setErrorEnabled();

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationActivity.onBackPressed();
               // registrationActivity.replaceFragment(new LoginFragment(),true,false);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        UIHelper.hideSoftKeyboards(registrationActivity);
//        if (CustomValidation.validateLength(etFirstName, ctFName, "Length must be greater than 4", "5", "15"))
//            if (CustomValidation.validateLength(etLName, ctLName, "Length must be greater than 4", "5", "15"))

        if(etFirstName.getText().toString().equals("") || etFirstName.getText().toString().trim().equals("")){
            UIHelper.showToast(registrationActivity,getString(R.string.fname_error));
            return;
        }
        if(etLName.getText().toString().equals("")|| etLName.getText().toString().trim().equals("")){
            UIHelper.showToast(registrationActivity,getString(R.string.lname_error));
            return;
        }
                UserSignUp.getInstance().userSignUpModel.withfullName(etFirstName.getText().toString() + " " + etLName.getText().toString());
        registrationActivity.getSignupPager().setCurrentItem(1);

    }

}
