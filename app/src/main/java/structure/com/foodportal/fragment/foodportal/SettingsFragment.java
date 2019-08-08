package structure.com.foodportal.fragment.foodportal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.SwitchCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;

public class SettingsFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.swAutoPlay)
    SwitchCompat autoplay;

    @BindView(R.id.llLanguage)
    LinearLayout linearLayoutLanguage;

    @BindView(R.id.tvSelectedLanguage)
    TextView textViewSelectedLanguage;

    @BindView(R.id.swNotifications)
    SwitchCompat notification;

    @BindView(R.id.llEmailSupport)
    LinearLayout llEmailSupport;

    @BindView(R.id.mainNotificationlayout)
    LinearLayout mainNotificationlayout;

    @BindView(R.id.tvLegalPrivacyPolicy)
    TextView tvLegalPrivacyPolicy;

    @BindView(R.id.tvAboutUs)
    TextView tvAboutUs;

    @BindView(R.id.tvUserAgreements)
    TextView tvUserAgreements;

    @BindView(R.id.tvAccountLogin)
    TextView tvAccountLogin;

    @BindView(R.id.tvVersion)
    TextView tvVersion;


    @BindView(R.id.tvWithEmail)
    TextView tvWithEmail;

    @BindView(R.id.tvWithFacebok)
    TextView tvWithFacebok;

    String[] mLanguages;

    Unbinder unbinder;
    Titlebar titlebar;

    private int mSelectedItemIndex;
    private int mPreferenceSelectedItemIndex;
    private int mLanguagePreviousSelectedItemIndex = 0;
    private boolean mIsChangeApplied = false;

//    @BindView(R.id.bottom_sheet)
//    LinearLayout layoutBottomSheet;
//
//    BottomSheetBehavior sheetBehavior;
//    BottomSheetBehavior sheetBehaviorAfterLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);

        mLanguages = mainActivity.getResources().getStringArray(R.array.languages_array);
        initLanguageVariables();

        // sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        //   sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        setlistner();
        checkAutoPlayandNotification();
        getVersionInfo();
//        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        // btnBottomSheet.setText("Close Sheet");
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        //btnBottomSheet.setText("Expand Sheet");
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        break;
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
        return view;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.showBackButton(mainActivity);
        this.titlebar = titlebar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        titlebar.showMenuButton(mainActivity);
        // unbind the view to free some memory
        unbinder.unbind();
    }

    public void checkAutoPlayandNotification() {


        if (preferenceHelper.getAutoPlay()) {
            autoplay.setChecked(true);

        } else {

            autoplay.setChecked(false);
        }


        if (preferenceHelper.getUserFood().getId().equals("293")) {
            mainNotificationlayout.setClickable(false);
            notification.setEnabled(false);
            mainNotificationlayout.setAlpha(0.5f);

        } else {
            notification.setEnabled(true);
            mainNotificationlayout.setClickable(true);
            mainNotificationlayout.setAlpha(1f);

        }

        if (preferenceHelper.getNotification()) {
            notification.setChecked(true);

        } else {
            notification.setChecked(false);

        }

    }

    String versionName;

    private void getVersionInfo() {
        versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = mainActivity.getPackageManager().getPackageInfo(mainActivity.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // TextView textViewVersionInfo = (TextView) findViewById(R.id.textview_version_info);
        tvVersion.setText(String.format("App Version V" + versionName));
    }

    void setlistner() {

        autoplay.setOnCheckedChangeListener(this);
        linearLayoutLanguage.setOnClickListener(this);
        notification.setOnClickListener(this);
        llEmailSupport.setOnClickListener(this);
        tvLegalPrivacyPolicy.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvUserAgreements.setOnClickListener(this);
        tvAccountLogin.setOnClickListener(this);

        tvWithEmail.setOnClickListener(this);
        tvWithFacebok.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {

            case R.id.swAutoPlay:

                if (b) {

                    autoplay.setChecked(true);
                    preferenceHelper.setAutoPlay(true);
                } else {

                    autoplay.setChecked(false);
                    preferenceHelper.setAutoPlay(false);
                }

                break;


        }


    }

    @SuppressLint("IntentReset")
    void sendEmail() {
        TelephonyManager manager = (TelephonyManager) mainActivity.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        String locale = mainActivity.getResources().getConfiguration().locale.getCountry();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SUPPORT_EMAIL});
        // emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Food Tribune Android " + versionName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "-How can we make Food Tribune Better?-" + "\n" +
                "Device " + android.os.Build.MANUFACTURER + "\n" +
                "Android Version: Android" + Build.VERSION.SDK_INT + " (" + Build.VERSION.CODENAME + ")" + "\n" +
                "Carrier" + carrierName + "\n" +
                "Network Status : Wifi" + "\n" +
                "Language : en" + "\n" +
                "Country " + locale + "\n" +
                "Device token" + preferenceHelper.getDeviceToken()
        );


        //   startActivity(Intent.createChooser(emailIntent, "Email "));


//       // Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SUPPORT_EMAIL);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//        emailIntent.setData(Uri.parse("mailto:"));
        try {
            startActivity(Intent.createChooser(emailIntent, "Email "));
            //    startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
            Toast.makeText(mainActivity, "No email client found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogout:
                mBottomSheetDialog.dismiss();
                mainActivity.prefHelper.putUserFood(null);
                mainActivity.prefHelper.setLoginStatus(false);
                mainActivity.finish();
                mainActivity.showRegistrationActivity();


                break;
            case R.id.tvWithFacebok:
                mBottomSheetDialog.dismiss();
                mainActivity.prefHelper.putUserFood(null);
                mainActivity.prefHelper.setLoginStatus(false);
                mainActivity.finish();
                mainActivity.showRegistrationActivity();


                break;

            case R.id.tvWithEmail:
                mBottomSheetDialog.dismiss();
                mainActivity.prefHelper.putUserFood(null);
                mainActivity.prefHelper.setLoginStatus(false);
                mainActivity.finish();
                mainActivity.showRegistrationActivity();
                break;
            case R.id.llLanguage:
                onClickLanguage();
                break;
            case R.id.llEmailSupport:
                sendEmail();
                break;
            case R.id.tvLegalPrivacyPolicy:
                openPrivacyPolicy();
                break;
            case R.id.tvAboutUs:
                openPrivacyPolicy();
                break;
            case R.id.tvUserAgreements:
                openPrivacyPolicy();
                break;
            case R.id.tvAccountLogin:

                toggleBottomSheet();
                break;
            case R.id.swNotifications:
                if (notification.isChecked()) {
                    notificationturnonoff(1);
                    notification.setChecked(true);
                    preferenceHelper.setNotification(true);
                } else {
                    notificationturnonoff(0);
                    notification.setChecked(false);
                    preferenceHelper.setNotification(false);
                }
                break;
        }
    }

    private void initLanguageVariables() {
        mPreferenceSelectedItemIndex = mainActivity.prefHelper.getSelectedLanguageIndex();
        mSelectedItemIndex = mPreferenceSelectedItemIndex;
        mLanguagePreviousSelectedItemIndex = mPreferenceSelectedItemIndex;

        textViewSelectedLanguage.setText(mLanguages[mPreferenceSelectedItemIndex]);
    }

    private void onClickLanguage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", null);

        initLanguageVariables();

        builder.setSingleChoiceItems(mLanguages, mSelectedItemIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                mIsChangeApplied = false;
                mSelectedItemIndex = position;
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(mainActivity.getString(R.string.language));
        alertDialog.setCancelable(true);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsChangeApplied = false;
                alertDialog.dismiss();
            }
        });

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsChangeApplied = true;
                mLanguagePreviousSelectedItemIndex = mSelectedItemIndex;
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsChangeApplied == false) {
                    mLanguagePreviousSelectedItemIndex = mPreferenceSelectedItemIndex;
                    mainActivity.prefHelper.putSelectedLanguageIndex(mLanguagePreviousSelectedItemIndex);
                } else {
                    mainActivity.prefHelper.putSelectedLanguageIndex(mSelectedItemIndex);
                }
                initLanguageVariables();
            }
        });
    }

    private void openPrivacyPolicy() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://food.tribune.com.pk/en/static/privacy-policy"));
        startActivity(browserIntent);
    }


    void notificationturnonoff(int i) {


        serviceHelper.enqueueCall(webService.notificationSwitch(i), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_NOTIFICATION);

    }

    BottomSheetDialog mBottomSheetDialog;

    @OnClick(R.id.tvAccountLogin)
    public void toggleBottomSheet() {

        if (preferenceHelper.getUserFood().getId().equals("293")) {
//            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                // btnBottomSheet.setText("Close sheet");
//            } else {
//                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                // btnBottomSheet.setText("Expand sheet");
//            }


            mBottomSheetDialog = new BottomSheetDialog(mainActivity);
            View sheetView = mainActivity.getLayoutInflater().inflate(R.layout.bottom_sheet, null);
            mBottomSheetDialog.setContentView(sheetView);
            TextView btnFacebook = (TextView) sheetView.findViewById(R.id.tvWithFacebok);
            TextView btnEmail = (TextView) sheetView.findViewById(R.id.tvWithEmail);
            btnEmail.setOnClickListener(this);
            btnFacebook.setOnClickListener(this);
            mBottomSheetDialog.show();
        } else {


            mBottomSheetDialog = new BottomSheetDialog(mainActivity);
            View sheetView = mainActivity.getLayoutInflater().inflate(R.layout.dialog_layout, null);
            mBottomSheetDialog.setContentView(sheetView);
            //mBottomSheetDialog.show();

//            final Dialog dialog = new Dialog(mainActivity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCancelable(false);
//            dialog.setContentView(R.layout.dialog_layout);
//            Window window = dialog.getWindow();
//            WindowManager.LayoutParams wlp = window.getAttributes();
//            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            wlp.gravity = Gravity.BOTTOM;
//            //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//            window.setAttributes(wlp);


            Button btnCancel = (Button) sheetView.findViewById(R.id.btnCancel);
            CircleImageView userImage = (CircleImageView) sheetView.findViewById(R.id.userImage);
            TextView userName = (TextView) sheetView.findViewById(R.id.userName);
            userName.setText(preferenceHelper.getUserFood().getName_en());
            switch (preferenceHelper.getUserFood().getAcct_type()) {
                case 1:
                    UIHelper.setImageWithGlide(mainActivity, userImage, AppConstant.BASE_URL_IMAGE + preferenceHelper.getUserFood().getProfile_picture());

                    break;
                case 2:
                    UIHelper.setImageWithGlide(mainActivity, userImage, preferenceHelper.getUserFood().getProfile_picture());

                    break;
                case 3:
                    UIHelper.setImageWithGlide(mainActivity, userImage,
                            "https://graph.facebook.com/" + preferenceHelper.getUserFood().getProfile_picture() + "/picture?type=large");
                    break;

                case 4:
                    UIHelper.setImageWithGlide(mainActivity, userImage, preferenceHelper.getUserFood().getProfile_picture());
                    break;

            }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });

            Button btnlogout = (Button) sheetView.findViewById(R.id.btnlogout);
            btnlogout.setOnClickListener(this);

            mBottomSheetDialog.show();
        }


    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {


        switch (Tag) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_NOTIFICATION:
                if (notification.isChecked()) {
                    notification.setChecked(false);

                } else {

                    notification.setChecked(true);
                }

                break;


        }


    }

}
