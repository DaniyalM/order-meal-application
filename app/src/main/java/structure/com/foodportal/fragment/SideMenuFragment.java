package structure.com.foodportal.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.customViews.CustomRecyclerView;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.viewbinders.SideMenuBinder;

public class SideMenuFragment extends BaseFragment {
    @BindView(R.id.img_background)
    CircleImageView imgBackground;
    @BindView(R.id.txt_username)
    TextView txtUsername;
    @BindView(R.id.txt_useraddress)
    TextView txtUseraddress;
    @BindView(R.id.sideoptions)
    CustomRecyclerView rvSideMenuOptions;
    Unbinder unbinder;
    private ArrayList<String> sideMenuOptions;
    private ArrayList<Integer> sideMenuOptionsDrawable;
    TermsAndConditon termsAndConditon;
    private RecyclerItemClickListener menuItemClickListener = ((ent, position, id) -> {
        if ((ent).equals(getString(R.string.home))) {
            // closeDrawer();
          //  popBackStackTillEntry(0);
          //  replaceFragment(new HomeFragment(), true, true);
            UIHelper.showToast(getActivity(), getActivity().getResources().getString(R.string.please_login_notification));

        } else if ((ent).equals(getString(R.string.orderHistory))) {
            if(!preferenceHelper.getLoginStatus()){
                UIHelper.showToast(getActivity(), getActivity().getResources().getString(R.string.please_login_notification));
            }else{
                if(preferenceHelper.getUser() != null){
                    replaceFragment(new OrdersHistoryFragment(), true, false);
                }
            }

        } else if ((ent).equals(getString(R.string.changePassword))) {
            if (preferenceHelper != null && preferenceHelper.getUser() != null) {
                replaceFragment(new ChangePasswordFragment(), true, true);
            } else {
                UIHelper.showToast(getActivity(), getActivity().getResources().getString(R.string.please_login_notification));
            }


        } else if ((ent).equals(getString(R.string.aboutUs))) {

            termsAndConditon = new TermsAndConditon();
            termsAndConditon.setkey("About Us");
            replaceFragment(termsAndConditon, true, true);

        } else if ((ent).equals(getString(R.string.rateApp))) {
            willbeimplementedinfuture();

        } else if ((ent).equals(getString(R.string.termCondition))) {
            termsAndConditon = new TermsAndConditon();
            termsAndConditon.setkey("Terms and Condition");
            replaceFragment(termsAndConditon, true, true);


        } else if ((ent).equals(getString(R.string.privacyPolicy))) {
            termsAndConditon = new TermsAndConditon();
            termsAndConditon.setkey("Privacy Policy");
            replaceFragment(termsAndConditon, true, true);

        } else if ((ent).equals(getString(R.string.login)) || (ent).equals(getString(R.string.logout))) {

            if ((ent).equals(getString(R.string.login))) {


                logout();
            } else {

                DialogFactory.createQuitDialog(mainActivity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutService();
                                    /* activityReference.clearStack();
                                    activityReference.initFragments(new LoginFragment());
                                    preferenceHelper.putUser(null);
                                    preferenceHelper.putUserToken(null);*/
                    }
                }, R.string.message_logout).show();


            }
            //   mainActivity.showRegistrationActivity();
            // willbeimplementedinfuture();
        }
    });

    private void logout() {
        preferenceHelper.putUser(null);
        preferenceHelper.setLoginStatus(false);
//        preferenceHelper.putDeviceToken(null);
        mainActivity.showRegistrationActivity();
    }


    public static SideMenuFragment newInstance() {
        Bundle args = new Bundle();

        SideMenuFragment fragment = new SideMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindSideMenuOptions();

        setListner(preferenceHelper);

    }

    private void bindSideMenuOptions() {
        sideMenuOptions = new ArrayList<>();
        sideMenuOptionsDrawable = new ArrayList<>();

            sideMenuOptions.add(getString(R.string.home));
        sideMenuOptions.add(getString(R.string.recipes));
        sideMenuOptions.add(getString(R.string.tutorials));
        sideMenuOptions.add(getString(R.string.cleaning));
        sideMenuOptions.add(getString(R.string.cooking_guides));
        sideMenuOptions.add(getString(R.string.recently_viewed));
        sideMenuOptions.add(getString(R.string.submit_your_recipe));
        sideMenuOptions.add(getString(R.string.my_reviews));
        sideMenuOptions.add(getString(R.string.my_saved_recipes));
        sideMenuOptions.add(getString(R.string.logout));



        sideMenuOptionsDrawable.add(R.drawable.icon_menu_home);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_my_saved_recipe);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_tutorial);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_cleaning);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_cooking_guide);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_recently_view);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_submit_your_recipe);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_my_reviews);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_my_saved_recipe);
        sideMenuOptionsDrawable.add(R.drawable.icon_menu_logout);


        if (preferenceHelper.getLoginStatus()) {

            sideMenuOptions.add(getString(R.string.logout));
        } else {

            sideMenuOptions.add(getString(R.string.login));

        }

        rvSideMenuOptions.bindRecyclerView(new SideMenuBinder(menuItemClickListener), sideMenuOptions, new LinearLayoutManager(getActivity()), new DefaultItemAnimator());


    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.hideTitle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setListner(final BasePreferenceHelper preferenceHelper) {


        if (preferenceHelper != null && preferenceHelper.getUser() != null) {

            txtUsername.setText(preferenceHelper.getUser().getFullName());
            txtUseraddress.setText(preferenceHelper.getUser().getEmail());
            UIHelper.setImagewithGlide(mainActivity, imgBackground, preferenceHelper.getUser().getProfileImage());

        }


    }


    public void logoutService() {
        serviceHelper.enqueueCall(webService.logout(preferenceHelper.getUser().getId(), preferenceHelper.getDeviceToken()), AppConstant.USER_LOGOUT);

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.USER_LOGOUT:

                UIHelper.showToast(mainActivity, getString(R.string.logout_success));
                logout();
                break;
        }
    }
}