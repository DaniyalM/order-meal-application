package structure.com.foodportal.adapter.foodPortalAdapters;

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
import structure.com.foodportal.fragment.foodportal.StepFragment;
import structure.com.foodportal.interfaces.OnPictureUploadedListener;
import structure.com.foodportal.models.foodModels.FoodDetailModel;

/**
 * Created by Addi.
 */
public class StepbyStepAdapter extends FragmentStatePagerAdapter {
    OnPictureUploadedListener onPictureUploadedListener;
    FoodDetailModel foodDetailModel;
    public StepbyStepAdapter(FragmentManager fm) {
        super(fm);
    }

    public StepbyStepAdapter(FragmentManager fm,FoodDetailModel foodDetailModel) {
        super(fm);
        this.foodDetailModel= foodDetailModel;

    }

    public void StepbyStepAdapter() {


    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StepFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return foodDetailModel.getSteps().size();
    }

    public void setCircleIndicator(RegistrationActivity registrationActivity) {


        //circleIndicator.setBackgroundColor(registrationActivity.getResources().getColor(R.color.colorBlue));


    }

    public OnPictureUploadedListener getOnPictureUploadedListener() {
        return onPictureUploadedListener;
    }

    public void setOnPictureUploadedListener(OnPictureUploadedListener onPictureUploadedListener) {
        this.onPictureUploadedListener = onPictureUploadedListener;

    }
}
