package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.RegistrationActivity;
import structure.com.foodportal.fragment.BaseFragment;
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
    FragmentManager fragmentManager;

    public static int pos = 0;
    private List<Fragment> myFragments;
    private ArrayList<String> categories;
    Context c;


    public StepbyStepAdapter(Context c, FragmentManager fragmentManager, List<Fragment> myFrags, ArrayList<String> cats, FoodDetailModel foodDetailModel) {
        super(fragmentManager);
        myFragments = myFrags;
        this.foodDetailModel = foodDetailModel;
        this.categories = cats;
        this.c = c;
    }


    @Override
    public Fragment getItem(int position) {

        return myFragments.get(position);
    }

    @Override
    public int getCount() {

        return myFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);
        return categories.get(position);
    }




    /*  public OnPictureUploadedListener getOnPictureUploadedListener() {
        return onPictureUploadedListener;
    }

    public void setOnPictureUploadedListener(OnPictureUploadedListener onPictureUploadedListener) {
        this.onPictureUploadedListener = onPictureUploadedListener;

    }
*/

    public static int getPos() {
        return pos;
    }

    public void add(Class<StepFragment> a, String title, Bundle b) {
        myFragments.add(Fragment.instantiate(c, a.getName(), b));
        categories.add(title);
    }

    public static void setPos(int pos) {
        StepbyStepAdapter.pos = pos;
    }

}
