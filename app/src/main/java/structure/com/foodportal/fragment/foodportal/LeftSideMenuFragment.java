package structure.com.foodportal.fragment.foodportal;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.adapter.foodPortalAdapters.NavItemBinderLeft;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftSideMenuFragment extends BaseFragment {

    public ArrayList<Integer> arrayNavOptions = new ArrayList<>();
    public ArrayList<Integer> arrImages = new ArrayList<>();
    //list variables
    private NavItemBinderLeft adapter;
    final int[] positionArray = {0, 1, 2, 3, 4, 5, 6, 7};
    ListView expandedListView;
    Unbinder unbinder;
    @BindView(R.id.sideoptions)
    ListView left_drawer;
    CircleImageView profileImage;
//    @BindView(R.id.txtName)
//    TextView txtName;
    String delUserType;


    public LeftSideMenuFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }

    public LeftSideMenuFragment newInstance(MainActivity activityReference_) {
        LeftSideMenuFragment leftSideMenuFragment = new LeftSideMenuFragment();
        mainActivity = activityReference_;
        return leftSideMenuFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);
        unbinder = ButterKnife.bind(this, view);
        profileImage = view.findViewById(R.id.img_background);
        expandedListView = view.findViewById(R.id.sideoptions);
        adapter = new NavItemBinderLeft(mainActivity, arrayNavOptions, this);
        expandedListView.setAdapter(adapter);
        setListner(preferenceHelper);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setByLoginStatus() {

        arrayNavOptions.clear();
        arrImages.clear();
      //  User user = preferenceHelper.getUser();
    //    txtName.setText(user.getName());
//        if (user.getUser_image() != null && user.getUser_image().size() > 0)
//            if (user.getUser_image().get(0).toString() != null)
//                Utils.setImageWithGlide(getActivity(), profileImage, user.getUser_image().get(0).getImage_url());
//            else {
//                Utils.setImageWithGlide(getActivity(), profileImage, "https://eadb.org/wp-content/uploads/2015/08/profile-placeholder.jpg");
//            }
//        switch (delUserType) {
//
//            case BasePreferenceHelper.USER_VENDER:

//                arrayNavOptions.add(R.string.home);
//                arrayNavOptions.add(R.string.brands);
//                arrayNavOptions.add(R.string.deals_in);
//                arrayNavOptions.add(R.string.swNotifications);
//                arrImages.add(R.drawable.home);
//                arrImages.add(R.drawable.brands);
//                arrImages.add(R.drawable.deals_in);
//                arrImages.add(R.drawable.swNotifications);
//                break;
//            default:

//                arrayNavOptions.add(R.string.home);
//                arrayNavOptions.add(R.string.swNotifications);
//                arrayNavOptions.add(R.string.message);
//                arrayNavOptions.add(R.string.my_orders);
//                arrayNavOptions.add(R.string.my_cars);
//                arrImages.add(R.drawable.directory);
//                arrImages.add(R.drawable.swNotifications);
//                arrImages.add(R.drawable.message);
//                arrImages.add(R.drawable.my_orders);
//                arrImages.add(R.drawable.my_car);
              //  break;


        arrayNavOptions.add(R.string.home_en);
        arrayNavOptions.add(R.string.recipes_en);
        arrayNavOptions.add(R.string.tutorials_en);
        arrayNavOptions.add(R.string.cleaning);
        arrayNavOptions.add(R.string.cooking_guides_en);
        arrayNavOptions.add(R.string.recently_viewed_en);
        arrayNavOptions.add(R.string.submit_your_recipe);
        arrayNavOptions.add(R.string.my_reviews_en);
        arrayNavOptions.add(R.string.my_saved_recipes_en);
        arrayNavOptions.add(R.string.log_out_en);



        arrImages.add(R.drawable.icon_menu_home);
        arrImages.add(R.drawable.icon_menu_my_saved_recipe);
        arrImages.add(R.drawable.icon_menu_tutorial);
        arrImages.add(R.drawable.icon_menu_cleaning);
        arrImages.add(R.drawable.icon_menu_cooking_guide);
        arrImages.add(R.drawable.icon_menu_recently_view);
        arrImages.add(R.drawable.icon_menu_submit_your_recipe);
        arrImages.add(R.drawable.icon_menu_my_reviews);
        arrImages.add(R.drawable.icon_menu_my_saved_recipe);
        arrImages.add(R.drawable.icon_menu_logout);

    }

//        arrayNavOptions.add(R.string.settings);
//        arrayNavOptions.add(R.string.logout);
//        arrImages.add(R.drawable.settings);
//        arrImages.add(R.drawable.logout);




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public void setCountinLeftMenu(ArrayList<Integer> badgecount) {
        if (adapter != null) {
            adapter.badgeCount(badgecount);
            adapter.notifyDataSetChanged();
        }
    }

    public void setListner(final BasePreferenceHelper preferenceHelper) {
        
        
        
        
//        if (preferenceHelper != null && preferenceHelper.getUser() != null) {
//            if (preferenceHelper.getUser().getUserType().equals(BasePreferenceHelper.USER_BUYER)) {
//                profileImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        closeDrawer();
//                        expandedListView.clearChoices();
//                        expandedListView.requestLayout();
//                        adapter.notifyDataSetChanged();
//
//                        activityReference.initFragments(new ProfileFragment());
//
//                    }
//                });
//            }
//            if (preferenceHelper.getUser().getUserType().equals(BasePreferenceHelper.USER_VENDER)) {
//                profileImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        closeDrawer();
//
//                        expandedListView.clearChoices();
//                        expandedListView.requestLayout();
//                        adapter.notifyDataSetChanged();
//                        expandedListView.setAdapter(adapter);
//                        VendorFragment vendorFragment = new VendorFragment();
//                        vendorFragment.setApperence(true);
//                        activityReference.initFragments(vendorFragment);
//                    }
//                });
//            }
        //    delUserType = preferenceHelper.getUser().getUserType();
            setByLoginStatus();
            delUserType ="vendor";
            if (delUserType.equals(BasePreferenceHelper.USER_VENDER)) {
                expandedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (position == positionArray[0]) {
//                            if (activityReference.currentFrag.equals(VendorHomeFragment.class.getName())) {
//                                closeDrawer();
//                                return;
//                            }

                            closeDrawer();

                        }

                        if (position == positionArray[1]) {


                            closeDrawer();

                        }


                        if (position == positionArray[2]) {

                            closeDrawer();
                        }

                        if (position == positionArray[3]) {

                            closeDrawer();

                        }

                        if (position == positionArray[4]) {


                            closeDrawer();

                        }


                        if (position == positionArray[5]) {
                            closeDrawer();
                            expandedListView.clearChoices();
                            expandedListView.requestLayout();
                            adapter.notifyDataSetChanged();
                            expandedListView.setAdapter(adapter);

                            DialogFactory.createQuitDialog(mainActivity, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                              //      logout();

                                }
                            }, R.string.message_logout).show();

                        }


                        if (position == positionArray[6]) {
                            closeDrawer();

                        }

                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
                expandedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == positionArray[0]) {
//                            if (activityReference.currentFrag.equals(HomeFragment.class.getName())) {
//                                closeDrawer();
//                                return;
//                            }

                            closeDrawer();
                        }

                        if (position == positionArray[1]) {

                            closeDrawer();

                        }


                        if (position == positionArray[2]) {


                            closeDrawer();
                        }

                        if (position == positionArray[3]) {
//                            if (activityReference.currentFrag.equals(OrderFragment.class.getName())) {
//                                closeDrawer();
//                                return;
//                            }
                          //  activityReference.clearStack();

                            closeDrawer();
                        }

                        if (position == positionArray[4]) {
//                            if (activityReference.currentFrag.equals(CarsListFragment.class.getName())) {
//                                closeDrawer();
//                                return;
//                            }
                         //   activityReference.clearStack();

                            closeDrawer();


                        }


                        if (position == positionArray[5]) {
//                            if (activityReference.currentFrag.equals(SettingsFragment.class.getName())) {
//                                closeDrawer();
//                                return;
//                            }
                            closeDrawer();
                           // activityReference.clearStack();


                        }


                        if (position == positionArray[6]) {
                            closeDrawer();
                            expandedListView.clearChoices();
                            expandedListView.requestLayout();
                            adapter.notifyDataSetChanged();
                            expandedListView.setAdapter(adapter);
                            DialogFactory.createQuitDialog(mainActivity, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                  //  logout();

                                }
                            }, R.string.message_logout).show();

                            ;

                            // activityReference.onBackPressed();

                            //activityReference.initFragments(new SparePartsFilter());
                            //closeDrawer();

                        }

                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }






    public void closeDrawer() {
        UIHelper.hideSoftKeyboards(mainActivity);
      //  mainActivity.closeLeftDrawer();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
//
//    public void logout() {
//        if (Utils.checknetworkwitherror(activityReference)) {
//            activityReference.showloader();
//
//            WebServiceFactory.getInstance(activityReference).logout(preferenceHelper.getDeviceToken()).enqueue(new Callback<Api_Response>() {
//                @Override
//                public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
//
//
//                    if (response != null && response.body() != null) {
//
//                        if (response.body().getUserBlocked() == 0) {
//
//
//                            if (response.body().checkSuccesscode()) {
//
//                                RealmController.with(activityReference).clearRealm();
//
//                                preferenceHelper.setStringPrefrence(BasePreferenceHelper.USER_TYPE, null);
//                                preferenceHelper.putUser(null);
//                                preferenceHelper.putUserToken(null);
//                                preferenceHelper.setLoginStatus(false);
//                                activityReference.clearStack();
//                                activityReference.initFragments(new LoginFragment());
//                                activityReference.hideloader();
//                            } else {
//                                Utils.showSnackBar(activityReference, activityReference.findViewById(R.id.mainFrameLayout),
//                                        response.body().getMessage(), ContextCompat.getColor(activityReference, R.color.appColor));
//                            }
//
//                        } else {
//
//                            activityReference.hideloader();
//                            Utils.blockuser(preferenceHelper, activityReference, response.body().getMessage());
//                            activityReference.clearStack();
//                            activityReference.initFragments(new LoginFragment());
//                        }
//
//
//                    } else {
//
//                        Utils.showSnackBar(activityReference, activityReference.findViewById(R.id.mainFrameLayout),
//                                response.body().getMessage(), ContextCompat.getColor(activityReference, R.color.appColor));
//
//                    }
//                    activityReference.hideloader();
//                }
//
//                @Override
//                public void onFailure(Call<Api_Response> call, Throwable t) {
//                    activityReference.hideloader();
//
//                }
//            });
//        }
//
//    }


}
