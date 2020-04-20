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
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SwitchCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import structure.com.foodportal.activity.SplashActivity;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.SimpleDialogActionListener;

import static android.view.View.LAYOUT_DIRECTION_LTR;
import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class SettingsFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.tvSettings)
    TextView tvSettings;

    @BindView(R.id.tvGeneral)
    TextView tvGeneral;

    @BindView(R.id.linearLayoutAutoPlay)
    LinearLayout linearLayoutAutoPlay;

    @BindView(R.id.tvAutoPlay)
    TextView tvAutoPlay;

    @BindView(R.id.swAutoPlay)
    SwitchCompat swAutoPlay;

    @BindView(R.id.linearLayoutNotification)
    LinearLayout linearLayoutNotification;

    @BindView(R.id.tvNotifications)
    TextView tvNotifications;

    @BindView(R.id.swNotifications)
    SwitchCompat swNotifications;

    @BindView(R.id.linearLayoutLanguage)
    LinearLayout linearLayoutLanguage;

    @BindView(R.id.tvLanguage)
    TextView tvLanguage;

    @BindView(R.id.tvSelectedLanguage)
    TextView tvSelectedLanguage;

    @BindView(R.id.tvFeedback)
    TextView tvFeedback;

    @BindView(R.id.linearLayoutEmailSupport)
    LinearLayout llEmailSupport;

    @BindView(R.id.tvEmailSupport)
    TextView tvEmailSupport;

    @BindView(R.id.tvEmailSupportDesc)
    TextView tvEmailSupportDesc;

    @BindView(R.id.tvLegal)
    TextView tvLegal;

    @BindView(R.id.tvLegalPrivacyPolicy)
    TextView tvLegalPrivacyPolicy;

    @BindView(R.id.tvLegalDesc)
    TextView tvLegalDesc;

    @BindView(R.id.tvAboutUs)
    TextView tvAboutUs;

    @BindView(R.id.tvAboutUsDesc)
    TextView tvAboutUsDesc;

    @BindView(R.id.tvUserAgreements)
    TextView tvUserAgreements;

    @BindView(R.id.tvUserAgreementsDesc)
    TextView tvUserAgreementsDesc;

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

    private String mTitle, mMessage, mAppVersionStr;
    private String mPositiveButtonText, mNegativeButtonText, mOK;
    private String mLogout;

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
            swAutoPlay.setChecked(true);

        } else {

            swAutoPlay.setChecked(false);
        }


        if (preferenceHelper.getUserFood().getId().equals("293")) {
            linearLayoutNotification.setClickable(false);
            swNotifications.setEnabled(false);
            linearLayoutNotification.setAlpha(0.5f);

        } else {
            swNotifications.setEnabled(true);
            linearLayoutNotification.setClickable(true);
            linearLayoutNotification.setAlpha(1f);

        }
        Log.e("Preference",""+preferenceHelper.getNotification());
        if (preferenceHelper.getNotification()) {

            swNotifications.setChecked(true);

        } else {
            swNotifications.setChecked(false);

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
        tvVersion.setText(String.format(mAppVersionStr + versionName));
    }

    void setlistner() {

        swAutoPlay.setOnCheckedChangeListener(this);
        linearLayoutLanguage.setOnClickListener(this);
        swNotifications.setOnClickListener(this);
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

                    swAutoPlay.setChecked(true);
                    preferenceHelper.setAutoPlay(true);
                } else {

                    swAutoPlay.setChecked(false);
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
            case R.id.linearLayoutLanguage:
                onClickLanguage();
                break;
            case R.id.linearLayoutEmailSupport:
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
                if (swNotifications.isChecked()) {
                    notificationturnonoff(1);
                    swNotifications.setChecked(true);
                    preferenceHelper.setNotification(true);
                } else {
                    notificationturnonoff(0);
                    swNotifications.setChecked(false);
                    preferenceHelper.setNotification(false);
                }
                break;
        }
    }

    private void initLanguageVariables() {
        mPreferenceSelectedItemIndex = mainActivity.prefHelper.getSelectedLanguage();
        mSelectedItemIndex = mPreferenceSelectedItemIndex;
        mLanguagePreviousSelectedItemIndex = mPreferenceSelectedItemIndex;

        tvSelectedLanguage.setText(mLanguages[mPreferenceSelectedItemIndex]);

        switch (mPreferenceSelectedItemIndex) {
            case AppConstant.Language.ENGLISH:
            default:
                mTitle = getString(R.string.restart_required_en);
                mMessage = getString(R.string.restart_required_desc_en);
                mAppVersionStr = getString(R.string.app_version_en);
                mPositiveButtonText = getString(R.string.yes_en);
                mNegativeButtonText = getString(R.string.cancel_en);
                mOK = getString(R.string.ok_en);
                mLogout = getString(R.string.log_out_en);

                tvSettings.setText(getString(R.string.settings_en));
                tvGeneral.setText(getString(R.string.general_en));
                linearLayoutAutoPlay.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                tvAutoPlay.setText(getString(R.string.auto_play_en));
                swAutoPlay.setText(getString(R.string.auto_play_desc_en));
                linearLayoutNotification.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                tvNotifications.setText(getString(R.string.notifications_en));
                swNotifications.setText(getString(R.string.notifications_desc_en));
                tvLanguage.setText(getString(R.string.language_en));
                tvFeedback.setText(getString(R.string.feedback_en));
                tvEmailSupport.setText(Utils.loadUnderlineHtmlText(getString(R.string.email_support_en)));
                tvEmailSupportDesc.setText(getString(R.string.email_support_desc_en));
                tvLegal.setText(getString(R.string.legal_en));
                tvLegalPrivacyPolicy.setText(Utils.loadUnderlineHtmlText(getString(R.string.legal_privacy_policy_en)));
                tvLegalDesc.setText(getString(R.string.legal_desc_en));
                tvAboutUs.setText(Utils.loadUnderlineHtmlText(getString(R.string.about_us_en)));
                tvAboutUsDesc.setText(getString(R.string.about_us_desc_en));
                tvUserAgreements.setText(Utils.loadUnderlineHtmlText(getString(R.string.user_agreements_en)));
                tvUserAgreementsDesc.setText(getString(R.string.user_agreements_desc_en));
                tvAccountLogin.setText(Utils.loadUnderlineHtmlText(getString(R.string.account_log_in_en)));
                break;

            case AppConstant.Language.URDU:
                mTitle = getString(R.string.restart_required_ur);
                mMessage = getString(R.string.restart_required_desc_ur);
                mAppVersionStr = getString(R.string.app_version_ur);
                mPositiveButtonText = getString(R.string.yes_ur);
                mNegativeButtonText = getString(R.string.cancel_ur);
                mOK = getString(R.string.ok_ur);
                mLogout = getString(R.string.log_out_ur);

                tvSettings.setText(getString(R.string.settings_ur));
                tvGeneral.setText(getString(R.string.general_ur));
                linearLayoutAutoPlay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvAutoPlay.setText(getString(R.string.auto_play_ur));
                swAutoPlay.setText(getString(R.string.auto_play_desc_ur));
                linearLayoutNotification.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                tvNotifications.setText(getString(R.string.notifications_ur));
                swNotifications.setText(getString(R.string.notifications_desc_ur));
                tvLanguage.setText(getString(R.string.language_ur));
                tvFeedback.setText(getString(R.string.feedback_ur));
                tvEmailSupport.setText(Utils.loadUnderlineHtmlText(getString(R.string.email_support_ur)));
                tvEmailSupportDesc.setText(getString(R.string.email_support_desc_ur));
                tvLegal.setText(getString(R.string.legal_ur));
                tvLegalPrivacyPolicy.setText(Utils.loadUnderlineHtmlText(getString(R.string.legal_privacy_policy_ur)));
                tvLegalDesc.setText(getString(R.string.legal_desc_ur));
                tvAboutUs.setText(Utils.loadUnderlineHtmlText(getString(R.string.about_us_ur)));
                tvAboutUsDesc.setText(getString(R.string.about_us_desc_ur));
                tvUserAgreements.setText(Utils.loadUnderlineHtmlText(getString(R.string.user_agreements_ur)));
                tvUserAgreementsDesc.setText(getString(R.string.user_agreements_desc_ur));
                tvAccountLogin.setText(Utils.loadUnderlineHtmlText(getString(R.string.account_log_in_ur)));
                break;
        }
    }

    private void onClickLanguage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(tvLanguage.getText().toString());
        builder.setCancelable(true);
        builder.setNegativeButton(mNegativeButtonText, null);
        builder.setPositiveButton(mOK, null);

        initLanguageVariables();

        builder.setSingleChoiceItems(mLanguages, mSelectedItemIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                mIsChangeApplied = false;
                mSelectedItemIndex = position;
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        if (preferenceHelper.getSelectedLanguage() == URDU) {
            TextView textViewTitle = (TextView) dialog.findViewById(getResources().getIdentifier("alertTitle", "id", "android"));
            UIHelper.makeAlertDialogTitleRightAligned(textViewTitle);
        }

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsChangeApplied = false;
                dialog.dismiss();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsChangeApplied = true;
                mLanguagePreviousSelectedItemIndex = mSelectedItemIndex;
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsChangeApplied == false) {
                    mLanguagePreviousSelectedItemIndex = mPreferenceSelectedItemIndex;
                    mainActivity.prefHelper.putSelectedLanguage(mLanguagePreviousSelectedItemIndex);
                    initLanguageVariables();
                } else {
                    // Here show restart app dialog
                    UIHelper.showSimpleDialog(
                            mainActivity,
                            mainActivity.prefHelper,
                            mTitle,
                            mMessage,
                            mPositiveButtonText,
                            mNegativeButtonText,
                            true,
                            new SimpleDialogActionListener() {
                                @Override
                                public void onDialogActionListener(DialogInterface dialog, int which, boolean positive, boolean logout) {
                                    if (positive) {
                                        mainActivity.prefHelper.putSelectedLanguage(mSelectedItemIndex);
//                                        initLanguageVariables();
                                        dialog.dismiss();
                                        ActivityCompat.finishAffinity(mainActivity);
                                        startActivity(new Intent(mainActivity, SplashActivity.class));
                                    } else {
                                        mIsChangeApplied = false;
                                        dialog.dismiss();
                                    }
                                }
                            }
                    );
                }
            }
        });
    }

    private void openPrivacyPolicy() {
        int lang = mainActivity.prefHelper.getSelectedLanguage();
        String locale = lang == ENGLISH ? "en" : "ur";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://food.tribune.com.pk/" + locale + "/static/privacy-policy"));
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

            LinearLayout linearLayoutUnlockCookingFood = (LinearLayout) sheetView.findViewById(R.id.linearLayoutUnlockCookingFood);
            TextView tvUnlockCookingFood = (TextView) sheetView.findViewById(R.id.tvUnlockCookingFood);
            TextView btnFacebook = (TextView) sheetView.findViewById(R.id.tvWithFacebok);
            TextView btnEmail = (TextView) sheetView.findViewById(R.id.tvWithEmail);

            switch (preferenceHelper.getSelectedLanguage()) {
                case ENGLISH:
                default:
                    linearLayoutUnlockCookingFood.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                    tvUnlockCookingFood.setText(getString(R.string.unlock_cooking_food_recipes_en));
                    btnFacebook.setText(getString(R.string.facebook_en));
                    btnEmail.setText(getString(R.string.email_en));
                    break;

                case URDU:
                    linearLayoutUnlockCookingFood.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    tvUnlockCookingFood.setText(getString(R.string.unlock_cooking_food_recipes_ur));
                    btnFacebook.setText(getString(R.string.facebook_ur));
                    btnEmail.setText(getString(R.string.email_ur));
                    break;
            }

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

            LinearLayout linearLayoutUser = (LinearLayout) sheetView.findViewById(R.id.linearLayoutUser);
            Button btnCancel = (Button) sheetView.findViewById(R.id.btnCancel);
            CircleImageView userImage = (CircleImageView) sheetView.findViewById(R.id.userImage);
            TextView userName = (TextView) sheetView.findViewById(R.id.userName);
            Button btnlogout = (Button) sheetView.findViewById(R.id.btnlogout);

            btnCancel.setText(mNegativeButtonText);
            btnlogout.setText(mLogout);

            switch (preferenceHelper.getSelectedLanguage()) {
                case ENGLISH:
                default:
                    linearLayoutUser.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                    userName.setText(preferenceHelper.getUserFood().getName_en());
                    break;

                case URDU:
                    linearLayoutUser.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    userName.setText(preferenceHelper.getUserFood().getName_ur());
                    break;
            }

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

            btnlogout.setOnClickListener(this);

            mBottomSheetDialog.show();
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {


        switch (Tag) {

            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_NOTIFICATION:
                if (swNotifications.isChecked()) {
                    swNotifications.setChecked(false);

                } else {

                    swNotifications.setChecked(true);
                }

                break;


        }


    }

}
