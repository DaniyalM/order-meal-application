package structure.com.foodportal.adapter.foodPortalAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.fragment.foodportal.LeftSideMenuFragment;


public class NavItemBinderLeft extends BaseAdapter {
    MainActivity context;
    int flags[];
    ArrayList<Integer> optionNames, optionsImages;
    boolean[] selectedStatus;
    LayoutInflater inflter;
    int drawable;
    ArrayList<Integer> badgecount;
    LeftSideMenuFragment leftSideMenuFragment;

    public NavItemBinderLeft(MainActivity applicationContext, ArrayList<Integer> optionNames, LeftSideMenuFragment leftSideMenuFragment_) {
        this.context = applicationContext;
        this.optionNames = optionNames;
        this.badgecount =new ArrayList<>();
        inflter = (LayoutInflater.from(applicationContext));
        leftSideMenuFragment = leftSideMenuFragment_;
//        setDefaultBoolValues();

    }


//    private void setDefaultBoolValues() {
//        selectedStatus = new boolean[arrImages.size()];
//        for (int i = 0; i < arrImages.size(); i++) {
//            selectedStatus[i] = false;
//        }
//    }

    @Override
    public int getCount() {
        return optionNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflter.inflate(R.layout.row_item_nav, null);
        TextView mOptions = (TextView) view.findViewById(R.id.txt_home);
        //TextView badge = (TextView) view.findViewById(R.id.count);
     //   LinearLayout layout =(LinearLayout) view.findViewById(R.id.leftsideItemLayout);

//        if(badgecount!= null && badgecount.size()>0 &&badgecount.size()>position && badgecount.get(position)>0){
//              if  (badgecount.get(position) > 99) {
//
//                badge.setVisibility(View.VISIBLE);
//                badge.setText("+99");
//            } else {
//                badge.setVisibility(View.VISIBLE);
//                badge.setText("" + badgecount.get(position));
//            }
//        }  else{
//            badge.setVisibility(View.GONE);
//            }


        mOptions.setText(context.getString(optionNames.get(position)));

//        mOptions.setTypeface(FontUtils.setMontserratLight(context));

      //  mOptions.setCompoundDrawablesRelativeWithIntrinsicBounds(leftSideMenuFragment.arrImages.get(position), 0, 0, 0);
//        if (selectedStatus[position]) {
//            mOptions.setTextColor(context.getResources().getColor(R.color.lightGreen));
////           mOptions.setCompoundDrawablesWithIntrinsicBounds(arrImagesSelected[position], 0, 0, 0);
//        }
//        else {
//            mOptions.setTextColor(context.getResources().getColor(R.color.gray));
//            //set Left Drawable
//            mOptions.setCompoundDrawablesWithIntrinsicBounds(arrImages[position], 0, 0, 0);
//        }
        return view;
    }

//    public void setSelectedIndex(int i) {
//        for (int j = 0; j < selectedStatus.length; j++) {
//            if(j == i) {
//                selectedStatus[j] = true;
//
//            }
//            else {
//                selectedStatus[j] = false;
//            }
//        }
//
//    }

   public void badgeCount(ArrayList<Integer> badgecountArray){
       badgecount.clear();
        badgecount.addAll(badgecountArray);
        notifyDataSetChanged();
    }
}
