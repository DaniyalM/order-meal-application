package structure.com.foodportal.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.adapter.SignupAdapter;
import structure.com.foodportal.databinding.FragmentSignupProfileBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.ActivityResultInterface;
import structure.com.foodportal.interfaces.MediaTypePicker;
import structure.com.foodportal.interfaces.OnActivityResultInterface;
import structure.com.foodportal.models.UserModel;
import structure.com.foodportal.models.UserSignUpModel;

@SuppressLint("ValidFragment")
public class SignupProfileFragment extends BaseFragment implements View.OnClickListener, ActivityResultInterface, MediaTypePicker, OnActivityResultInterface {

    FragmentSignupProfileBinding binding;
    private ArrayList<String> photoPaths = new ArrayList<>();
    File profileImageFile = null;
    private MultipartBody.Part body;
    SignupAdapter signupAdapter;

    public SignupProfileFragment(SignupAdapter signupAdapter) {
        this.signupAdapter = signupAdapter;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(getString(R.string.sign_up));
        titlebar.showTitlebar();
        titlebar.showCrossButton().setOnClickListener(v -> {

            registrationActivity.onBackPressed();

        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_profile, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {

        binding.btnPhoto.setOnClickListener(this);
        binding.tvRetake.setOnClickListener(this);
        binding.tvSkip.setOnClickListener(this);
        registrationActivity.setOnActivityResultInterface(this);
        binding.btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnPhoto:
                UIHelper.hideSoftKeyboards(registrationActivity);
                registrationActivity.openFilePicker(SignupProfileFragment.this, 1, false, AppConstant.ProfilePictureParameters.MIN_WINDOW_WIDTH, AppConstant.ProfilePictureParameters.MIN_WINDOW_HEIGHT, AppConstant.ProfilePictureParameters.ASPECT_RATIO_X, AppConstant.ProfilePictureParameters.ASPECT_RATIO_Y);
                // registrationActivity.cameraPermission();
                break;

            case R.id.tvRetake:
                UIHelper.hideSoftKeyboards(registrationActivity);
                registrationActivity.openFilePicker(SignupProfileFragment.this, 1, false, AppConstant.ProfilePictureParameters.MIN_WINDOW_WIDTH, AppConstant.ProfilePictureParameters.MIN_WINDOW_HEIGHT, AppConstant.ProfilePictureParameters.ASPECT_RATIO_X, AppConstant.ProfilePictureParameters.ASPECT_RATIO_Y);
                //  registrationActivity.cameraPermission();
                break;

            case R.id.tvSkip:
                UIHelper.hideSoftKeyboards(registrationActivity);
                registrationActivity.showMainActivity();
                break;

            case R.id.btnConfirm:
                validate();
                break;

        }
    }

    @Override
    public void onActivityResultI(int requestCode, int i, Intent data) {
        if (requestCode == AppConstant.CAMERA_PERMISSION) {
            FilePickerBuilder.getInstance().setMaxCount(1)
                    .setActivityTheme(R.style.LibAppTheme)
                    .pickPhoto(this);
        }
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case FilePickerConst.REQUEST_CODE_PHOTO:
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    photoPaths.clear();
//                    photoPaths.addAllToAdapter(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
//                    UIHelper.setImagewithGlide(registrationActivity, binding.ivPhoto, photoPaths.get(0));
//                    binding.tvText.setText(getResources().getString(R.string.looking_good));
//                    binding.tvRetake.setVisibility(View.VISIBLE);
//                    binding.btnPhoto.setVisibility(View.GONE);
//                    binding.btnConfirm.setVisibility(View.VISIBLE);
//                    binding.tvSkip.setVisibility(View.GONE);
//                }
//                break;
//        }
//
//    }
//
    private void validate() {
        UIHelper.hideSoftKeyboards(registrationActivity);

        if (profileImageFile != null) {

            UserSignUpModel userSignUpModel = UserSignUp.getInstance().userSignUpModel.withdeviceId("test")
                    .withdeviceType("android")
                    .withfacebookAuthToken("")
                    .withlang("EN_US")
                    .withzipCode("12345")
                    .withgoogleAuthToken("").build();
            //registrationActivity.getSignupPager().setCurrentItem(6);
            register(userSignUpModel);
            //  UserSignUp.getInstance().userSignUpModel.withprofilePicture("");
            //registrationActivity.getSignupPager().setCurrentItem(6);
        } else
            UIHelper.showToast(registrationActivity, registrationActivity.getResources().getString(R.string.image_required));

    }

    public RequestBody getImageRequestBody(File value) {

        return RequestBody.create(MediaType.parse("image/*"), value);
    }

    @Override
    public void onPhotoClicked(ArrayList<File> file) {
        // this.files.addAllToAdapter(file);
        binding.tvText.setText(getResources().getString(R.string.looking_good));
        binding.tvRetake.setVisibility(View.VISIBLE);
        binding.btnPhoto.setVisibility(View.GONE);
        binding.btnConfirm.setVisibility(View.VISIBLE);
        //  binding.tvSkip.setVisibility(View.GONE);

        profileImageFile = file.get(0);
        UIHelper.setImagewithGlide(registrationActivity, binding.ivPhoto, profileImageFile.getAbsolutePath());
        body = MultipartBody.Part.createFormData(
                "profile_picture", file.get(0).getName(), getImageRequestBody(file.get(0)));
    }

    @Override
    public void onDocClicked(ArrayList<String> files) {

    }

    @Override
    public void onActivityResultInterface(int requestCode, int resultCode, Intent data) throws IOException {

    }


    public void register(UserSignUpModel userSignUpModel) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("full_name", createPartFromString(userSignUpModel.getFullName()));
        map.put("country_code", createPartFromString(userSignUpModel.getCountryCode()));
        map.put("user_id", createPartFromString("" + UserSignUp.getInstance().getUser_id()));
        map.put("zip_code", createPartFromString(userSignUpModel.getZipCode()));
        map.put("full_address", createPartFromString(userSignUpModel.getFullAddress()));
        map.put("location", createPartFromString(userSignUpModel.getFullAddress()));
        map.put("latitude", createPartFromString("" + userSignUpModel.getLatitude()));
        map.put("longitude", createPartFromString("" + userSignUpModel.getLongitude()));
        map.put("email", createPartFromString("" + userSignUpModel.getEmail()));
//        map.put("profile_image", createPartFromString(""+userSignUpModel.getProfilePicture()));

        serviceHelper.enqueueCall(webService.updateUser(map, body), WebServiceConstants.USER_UPDATE);

    }


    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case WebServiceConstants.USER_UPDATE:
                // ((SignupFinalFragment)((SignupAdapter) registrationActivity.getSignupPager().getAdapter()).getItem(6)).setImage(profileImageFile.getAbsolutePath());
                //  UserSignUp.getInstance().setProfileImage(profileImageFile.getAbsolutePath());
                registrationActivity.getSignupPager().setCurrentItem(6);
                signupAdapter.setCircleIndicator(registrationActivity);
                preferenceHelper.putUser((UserModel) result);

                signupAdapter.getOnPictureUploadedListener().onPictureUploaded(preferenceHelper.getUser().getProfileImage());

                break;


        }
    }

    @NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, value);
    }

}
