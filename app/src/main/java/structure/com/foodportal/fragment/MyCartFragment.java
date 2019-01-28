package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.MyCartAdapter;
import structure.com.foodportal.helper.Titlebar;

public class MyCartFragment extends BaseFragment {


    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ViewPagerAdapter viewPagerAdapter;
    //    @BindView(R.id.activity_easy_tab_text)
//    LinearLayout activityEasyTabText;
    @BindView(R.id.tabs)
     TabLayout tabLayout;
    private MyCartAdapter pagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        mainActivity.hideBottombar();


        //pagerAdapter = new MyCartAdapter(mainActivity.getSupportFragmentManager());
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new CartFragment(),"My Bag");
        viewPagerAdapter.addFragment(new OrderHistoryFragment(),"Store");
        tabLayout.setupWithViewPager(viewpager);

      //  easytabs.setViewPager(viewpager, 0); // Set viewPager to EasyTabs with default index
        // Optional, add a listener
//        easytabs.setPagerListener(position -> {
//
//        });
        viewpager.setOffscreenPageLimit(0);

    }


    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showTitlebar();
        titlebar.setTitle(getString(R.string.cart));
        titlebar.showBackButton(mainActivity);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
