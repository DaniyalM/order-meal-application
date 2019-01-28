package structure.com.foodportal.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.OnCountryPickerListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentEditProfileBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.CustomValidation;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.GooglePlaceDataInterface;
import structure.com.foodportal.helper.GooglePlaceHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.ActivityResultInterface;
import structure.com.foodportal.interfaces.MediaTypePicker;
import structure.com.foodportal.interfaces.OnActivityResultInterface;
import structure.com.foodportal.models.City;
import structure.com.foodportal.models.UserModel;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener, MediaTypePicker, ActivityResultInterface, OnActivityResultInterface, GooglePlaceDataInterface {
    private FragmentEditProfileBinding binding;
    private File profileImageFile = null;
    private MultipartBody.Part body;
    private ArrayList<structure.com.foodportal.models.Country> countries;
    private ArrayList<City> cities;
    private structure.com.foodportal.models.Country selectedCountry;
    private City selectedCity;
    private GooglePlaceHelper googlePlaceHelper;

    private double longitude, latitude;
    private String address, country, city, profileImage;
    UserModel user;

    private boolean isClicked = true;


    public EditProfileFragment() {
    }

    public static EditProfileFragment Instance() {
        return new EditProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity.hideBottombar();
        googlePlaceHelper = new GooglePlaceHelper(mainActivity, GooglePlaceHelper.PLACE_PICKER, this, this);
        mainActivity.setOnActivityResultInterface(this);
        getCountries();
        setListeners();
        setProfile();
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.edit_account));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivProfilePic:
                addProfilePicture();
                break;

            case R.id.btnUpdate:
                update();
                break;

            case R.id.tvCodePicker:
                getSetCode();
                break;

            case R.id.tvCountry:
                openCountries();
                break;

            case R.id.tvCity:
                openCities();
                break;

            case R.id.tvAddress:
                setAddress();
                break;
        }
    }

    @Override
    public void onPhotoClicked(ArrayList<File> file) {
        if (file != null && file.size() > 0) {
            profileImageFile = file.get(0);
            UIHelper.setImagewithGlide(mainActivity, binding.ivProfilePic, profileImageFile.getAbsolutePath());
            body = MultipartBody.Part.createFormData("profile_picture", file.get(0).getName(), getImageRequestBody(file.get(0)));
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.image_finding_error));
        }
    }

    @Override
    public void onDocClicked(ArrayList<String> files) {

    }

    @Override
    public void onActivityResultI(int cameraPermission, int i, Intent data) {

    }

    @Override
    public void onActivityResultInterface(int requestCode, int resultCode, Intent data) throws IOException {
        if (requestCode == AppConstant.CAMERA_PERMISSION) {
            FilePickerBuilder.getInstance().setMaxCount(1)
                    .setActivityTheme(R.style.LibAppTheme)
                    .pickPhoto(this);
        } else {
            googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPlaceActivityResult(double longitude, double latitude, String address, String country, String city) throws IOException {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        binding.tvAddress.setText(address);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.GET_COUNTRIES:
                countries = (ArrayList<structure.com.foodportal.models.Country>) result;
                break;

            case AppConstant.GET_CITIES:
                cities = (ArrayList<City>) result;
                break;

            case WebServiceConstants.USER_UPDATE:
                preferenceHelper.putUser((UserModel) result);
              //  mainActivity.sideMenuFragment.setListner(preferenceHelper);
                mainActivity.onBackPressed();
                break;
        }
    }

    public RequestBody getImageRequestBody(File value) {
        return RequestBody.create(MediaType.parse("image/*"), value);
    }

    private void setListeners() {
        binding.tvCodePicker.setOnClickListener(this);
        binding.tvCountry.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.ivProfilePic.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.tvAddress.setOnClickListener(this);

        mainActivity.setOnActivityResultInterface(this);

        binding.ctName.setErrorEnabled();
        binding.ctEmailAddress.setErrorEnabled();
        binding.ctPhoneNumber.setErrorEnabled();
        binding.ctZipCode.setErrorEnabled();

        binding.ctAddress.setErrorEnabled();
        binding.ctCountry.setErrorEnabled();
        binding.ctCity.setErrorEnabled();
    }

    private void setProfile() {
        user = preferenceHelper.getUser();
        if (user != null) {
            if (user.getProfileImage() != null) {
                profileImage = user.getProfileImage();
                ImageLoader.getInstance().displayImage(user.getProfileImage(), binding.ivProfilePic);
            }

            if (user.getFullName() != null) {
                binding.etName.setText(user.getFullName());
            }

            if (user.getEmail() != null) {
                binding.etEmailAddress.setText(user.getEmail());
            }

            if (user.getPhoneNo() != null) {
                binding.etPhoneNumber.setText(user.getPhoneNo());
            }

            if (user.getFullAddress() != null) {
                binding.tvAddress.setText(user.getFullAddress());
                address = user.getFullAddress();
            }

            if (user.getCountry() != null && (!user.getCountry().equals(""))) {
                binding.tvCountry.setText(user.getCountry());
                country = user.getCountry();
            }

            if (user.getCity() != null && (!user.getCity().equals(""))) {
                binding.tvCity.setText(user.getCity());
                city = user.getCity();
            }

            if (user.getLatitude() != null) {
                latitude = Double.parseDouble(user.getLatitude());
            }

            if (user.getLongitude() != null) {
                longitude = Double.parseDouble(user.getLongitude());
            }

            if (user.getZipCode() != null) {
                binding.etZipCode.setText(user.getZipCode());
            }
            if (user.getAbout() != null) {
                binding.etBio.setText(user.getAbout());
            }
        }
    }

    private void addProfilePicture() {
        mainActivity.openFilePicker(this, 1, false, AppConstant.ProfilePictureParameters.MIN_WINDOW_WIDTH, AppConstant.ProfilePictureParameters.MIN_WINDOW_HEIGHT, AppConstant.ProfilePictureParameters.ASPECT_RATIO_X, AppConstant.ProfilePictureParameters.ASPECT_RATIO_Y);
    }

    private void getSetCode() {
        mainActivity.getSetCode(new OnCountryPickerListener() {
            @Override
            public void onSelectCountry(Country country) {
                binding.tvCodePicker.setText(country.getDialCode());
                binding.tvCodePicker.setCompoundDrawablesWithIntrinsicBounds(country.getFlag(), 0, 0, 0);
            }
        });
    }

    private void openCountries() {
        if (countries != null && countries.size() > 0) {
            ArrayList<String> onlynames = new ArrayList<>();
            for (int i = 0; i < countries.size(); i++) {
                onlynames.add(countries.get(i).getName());
            }
            DialogFactory.listDialog(mainActivity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    binding.tvCountry.setText(onlynames.get(i));
                    selectedCountry = countries.get(i);
                    country = onlynames.get(i);
                    city = "";
                    binding.tvCity.setText(mainActivity.getResources().getString(R.string.select_city));
                    getCities(selectedCountry.getCode());
                }
            }, mainActivity.getResources().getString(R.string.select_country), onlynames);
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_country_found));
        }
    }

    private void openCities() {
        if (cities != null && cities.size() > 0) {
            ArrayList<String> onlynames = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                onlynames.add(cities.get(i).getName());
            }
            DialogFactory.listDialog(mainActivity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    binding.tvCity.setText(onlynames.get(i));
                    selectedCity = cities.get(i);
                    city = onlynames.get(i);

                }
            }, mainActivity.getResources().getString(R.string.select_city), onlynames);
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_city_found));
        }
    }

    private void setAddress() {
        if (isClicked) {
            googlePlaceHelper.openAutocompleteActivity();
            isClicked = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    isClicked = true;
                }
            }, 3000);

        }
    }

    private void update() {
        if (user.getProfileImage() == null) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.upload_profile_picture));
            return;
        }

        if (!CustomValidation.validateLength(binding.etName, binding.ctName, mainActivity.getResources().getString(R.string.name_error), "3", "100")) {
            return;
        }

        if (!CustomValidation.validateEmail(binding.etEmailAddress, binding.ctEmailAddress, mainActivity.getResources().getString(R.string.err_email))) {
            return;
        }

        if (!CustomValidation.validateLength(binding.etPhoneNumber, binding.ctPhoneNumber, mainActivity.getResources().getString(R.string.phone_number_error), "7", "12")) {
            return;
        }

        if (binding.tvAddress.getText().toString().length() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.address_error));
            return;
        }

        if(country == null || country.equals("")){
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.select_country));
            return;
        }

        if(city == null || city.equals("")){
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.select_city));
            return;
        }

        if (!CustomValidation.validateLength(binding.etZipCode, binding.ctZipCode, mainActivity.getResources().getString(R.string.zipcode_error), "1", "16")) {
            return;
        }

        updateCall();
    }

    public void getCountries() {
        serviceHelper.enqueueCall(webService.getCountires(), AppConstant.GET_COUNTRIES);
    }

    public void getCities(String countryCode) {
        serviceHelper.enqueueCall(webService.getCities(countryCode), AppConstant.GET_CITIES);
    }

    public void updateCall() {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("full_name", createPartFromString(binding.etName.getText().toString()));
        map.put("country_code", createPartFromString(binding.tvCodePicker.getText().toString()));
        map.put("user_id", createPartFromString("" + preferenceHelper.getUser().getId()));
        map.put("zip_code", createPartFromString(binding.etZipCode.getText().toString()));
        map.put("full_address", createPartFromString(binding.tvAddress.getText().toString()));
        map.put("location", createPartFromString("" + address));
        map.put("latitude", createPartFromString("" + latitude));
        map.put("longitude", createPartFromString("" + longitude));
        map.put("email", createPartFromString(binding.etEmailAddress.getText().toString()));
        map.put("country", createPartFromString(binding.tvCountry.getText().toString()));
        map.put("city", createPartFromString(city));
        map.put("about", createPartFromString(binding.etBio.getText().toString()));

        serviceHelper.enqueueCall(webService.updateUser(map, body), WebServiceConstants.USER_UPDATE);
    }

    @io.reactivex.annotations.NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, value);
    }
}
