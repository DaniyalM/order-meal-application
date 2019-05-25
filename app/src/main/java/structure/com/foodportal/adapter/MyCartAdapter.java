package structure.com.foodportal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import structure.com.foodportal.fragment.OrderHistoryFragment;

public class MyCartAdapter extends FragmentPagerAdapter {

    public MyCartAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
               // return  new CartFragment();
            case 1:
                // Games fragment activity
                return new OrderHistoryFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}