package structure.com.foodportal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.activity.RegistrationActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.ServiceHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.webServiceResponseLisener;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.webservice.WebServiceFactory;
import structure.com.foodportal.webservice.webservice;


abstract public class BaseFragment extends Fragment implements webServiceResponseLisener {
    protected ServiceHelper serviceHelper;
    protected webservice webService;
    RegistrationActivity registrationActivity;
    public MainActivity mainActivity;
    public BasePreferenceHelper preferenceHelper;

    public BaseFragment() {
    }

    protected abstract void setTitle(Titlebar titlebar);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webService = WebServiceFactory.getInstance(AppConstant.BASE_URL);
        serviceHelper = new ServiceHelper(this, getActivity());

        setPreferenceHelper();
    }

    public void setPreferenceHelper() {
        if (mainActivity != null) {

            preferenceHelper = getMainActivity().prefHelper;
        } else if (registrationActivity != null) {

            preferenceHelper = getRegistrationActivity().prefHelper;
        }


        if (preferenceHelper == null && registrationActivity != null) {
            preferenceHelper = new BasePreferenceHelper(registrationActivity);
        } else if (preferenceHelper == null && mainActivity != null) {
            preferenceHelper = new BasePreferenceHelper(mainActivity);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegistrationActivity)
            registrationActivity = (RegistrationActivity) context;
        else
            mainActivity = (MainActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getMainActivity() != null && getMainActivity().getDrawerLayout() != null) {
            getMainActivity().lockDrawer();
        }
        if (getActivity() instanceof RegistrationActivity) {
            setTitle(((RegistrationActivity) getActivity()).getTitleBar());
        } else if (getActivity() instanceof MainActivity) {
            setTitle(((MainActivity) getActivity()).getTitleBar());
        }
    }

    private MainActivity getMainActivity() {
        if (getActivity() instanceof MainActivity)
            return (MainActivity) getActivity();
        else return null;
    }

    private RegistrationActivity getRegistrationActivity() {
        if (getActivity() instanceof RegistrationActivity)
            return (RegistrationActivity) getActivity();
        else return null;
    }

    public void closeDrawer() {
        if (mainActivity != null) {
            mainActivity.closeDrawer();
        }

    }

    protected void willbeimplementedinfuture() {
        UIHelper.showToast(getActivity(), "Will be implemented in Next Module");
    }

    protected void replaceFragment(BaseFragment frag, boolean isAddToBackStack, boolean animate) {
        if (registrationActivity != null) {
            UIHelper.hideSoftKeyboards(registrationActivity);
            registrationActivity.replaceFragment(frag, isAddToBackStack, animate);
        } else {
            mainActivity.replaceFragment(frag, isAddToBackStack, animate);
            UIHelper.hideSoftKeyboards(mainActivity);
        }
    }

    public void popBackStackTillEntry(int entryIndex) {
        if (mainActivity != null) {
            mainActivity.popBackStackTillEntry(entryIndex);
        }
    }

    public void popFragment() {
        if (mainActivity != null) {
            mainActivity.popFragment();
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {

    }

    @Override
    public void ResponseFailure(String tag) {

    }
}
