package structure.com.foodportal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.relex.circleindicator.CircleIndicator;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.RegistrationActivity;
import structure.com.foodportal.fragment.Locationfragment;
import structure.com.foodportal.fragment.PinFragment;
import structure.com.foodportal.fragment.SignupFinalFragment;
import structure.com.foodportal.fragment.SignupOneFragment;
import structure.com.foodportal.fragment.SignupProfileFragment;
import structure.com.foodportal.fragment.SignupThreeFragment;
import structure.com.foodportal.fragment.SignupTwoFragment;
import structure.com.foodportal.interfaces.OnPictureUploadedListener;

/**
 * Created by Addi.
 */
public class SignupAdapter extends FragmentStatePagerAdapter {
    CircleIndicator circleIndicator;
    OnPictureUploadedListener onPictureUploadedListener;

    public SignupAdapter(FragmentManager fm) {
        super(fm);
    }

    public SignupAdapter(FragmentManager fm, CircleIndicator circleIndicator,String path) {
        super(fm);
        this.circleIndicator = circleIndicator;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SignupOneFragment();

            case 1:
                return new SignupTwoFragment();

            case 2:
                return new Locationfragment();

            case 3:
                return new SignupThreeFragment();

            case 4:
                return new PinFragment();
            //return new SignupProfileFragment();
            case 5:
                return new SignupProfileFragment(this);

            case 6:
                return new SignupFinalFragment(this, circleIndicator);
                // return new SignupProfileFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 7;
    }

    public void setCircleIndicator(RegistrationActivity registrationActivity) {


        circleIndicator.setBackgroundColor(registrationActivity.getResources().getColor(R.color.colorBlue));


    }

    public OnPictureUploadedListener getOnPictureUploadedListener() {
        return onPictureUploadedListener;
    }

    public void setOnPictureUploadedListener(OnPictureUploadedListener onPictureUploadedListener) {
        this.onPictureUploadedListener = onPictureUploadedListener;

    }
}
