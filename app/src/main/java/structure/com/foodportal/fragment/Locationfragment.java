package structure.com.foodportal.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.UserSignUp;
import structure.com.foodportal.databinding.FragmentLocationBinding;
import structure.com.foodportal.helper.GooglePlaceDataInterface;
import structure.com.foodportal.helper.GooglePlaceHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.OnActivityResultInterface;

public class Locationfragment extends BaseFragment implements View.OnClickListener, GooglePlaceDataInterface, OnActivityResultInterface {

    double latitude, longitude;
    FragmentLocationBinding binding;
    private GooglePlaceHelper googlePlaceHelper;
    private boolean isClicked = true, locationSelected;
    String zipCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        googlePlaceHelper = new GooglePlaceHelper(registrationActivity, GooglePlaceHelper.PLACE_PICKER, this, this);
        (registrationActivity).setOnActivityResultInterface(Locationfragment.this);
        binding.btnNext.setOnClickListener(this);
        binding.btnPickLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnNext:

                if (binding.tvLocation.getText().equals("")) {
                    UIHelper.showToast(registrationActivity, "Add your Location");
                    return;
                } else

                    UserSignUp.getInstance()
                            .userSignUpModel.withfullAddress(binding.tvLocation.getText().toString())
                            .withzipCode(zipCode)
                            .withlatitude(latitude)
                            .withlongitude(longitude);
                registrationActivity.getSignupPager().setCurrentItem(3);


                break;

            case R.id.btnPickLocation:

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
                break;


        }
    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }


    @Override
    public void onPlaceActivityResult(double longitude, double latitude, String address, String country, String city) throws IOException {
        setLocationText(address);
        Geocoder geocoder = new Geocoder(registrationActivity, Locale.getDefault());
// lat,lng, your current location
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        LatLng latLng = new LatLng(latitude, longitude);
        if(addresses.size() > 0){
            zipCode = addresses.get(0).getPostalCode();
        }
        this.latitude = latitude;
        this.longitude = longitude;


    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onActivityResultInterface(int requestCode, int resultCode, Intent data) throws IOException {
        googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void setLocationText(String address) {
        registrationActivity.showLoader();
        this.locationSelected = true;
        binding.btnPickLocation.setText(registrationActivity.getResources().getString(R.string.change_location));
        binding.tvLocation.setText(address);
        binding.tvLocation.setSelected(true);
        registrationActivity.hideLoader();
    }
}
