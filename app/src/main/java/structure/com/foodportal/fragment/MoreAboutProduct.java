package structure.com.foodportal.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.databinding.FragmentMoreaboutproductBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.GooglePlaceDataInterface;
import structure.com.foodportal.helper.GooglePlaceHelper;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.ProductCreation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.OnActivityResultInterface;
import structure.com.foodportal.models.AddProductModel;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.City;
import structure.com.foodportal.models.ConditionType;
import structure.com.foodportal.models.Country;
import structure.com.foodportal.models.Itemlistobject;
import structure.com.foodportal.webservice.Api_Response;
import structure.com.foodportal.webservice.WebApiRequest;

import static io.fabric.sdk.android.Fabric.TAG;

public class MoreAboutProduct extends BaseFragment implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener, GooglePlaceDataInterface, OnActivityResultInterface {
    @BindView(R.id.rdOrignal)
    RadioButton rdOrignal;
    @BindView(R.id.rdReplace)
    RadioButton rdReplace;
    @BindView(R.id.rdNone)
    RadioButton rdNone;
    Unbinder unbinder;
    @BindView(R.id.rdExpandableMemoryYes)
    RadioButton rdExpandableMemoryYes;
    @BindView(R.id.rdExpandableMemoryNo)
    RadioButton rdExpandableMemoryNo;
    @BindView(R.id.radioGroupExpandableMemory)
    RadioGroup radioGroupExpandableMemory;
    @BindView(R.id.rdSmartFeaturesYes)
    RadioButton rdSmartFeaturesYes;
    @BindView(R.id.rdSmartFeaturesNo)
    RadioButton rdSmartFeaturesNo;
    @BindView(R.id.radioGroupSmartFeatures)
    RadioGroup radioGroupSmartFeatures;
    @BindView(R.id.rdPetsAllowedYes)
    RadioButton rdPetsAllowedYes;
    @BindView(R.id.rdPetsAllowedNo)
    RadioButton rdPetsAllowedNo;
    @BindView(R.id.radioGroupPetsAllowed)
    RadioGroup radioGroupPetsAllowed;
    @BindView(R.id.tvproductLocation)
    EditText tvproductLocation;
    @BindView(R.id.etProductModelNumber)
    EditText etProductModelNumber;
    @BindView(R.id.etKilometers)
    EditText etKilometers;
    @BindView(R.id.etMegaPixels)
    EditText etMegaPixels;
    @BindView(R.id.etOperatingSystems)
    EditText etOperatingSystems;
    @BindView(R.id.etProcessor)
    EditText etProcessor;
    @BindView(R.id.etTotalArea)
    EditText etTotalArea;
    private GooglePlaceHelper googlePlaceHelper;
    private boolean isClicked = true;
    FragmentMoreaboutproductBinding binding;
    ArrayList<String> year;
    ArrayList<String> condition, doors, colors, data;
    private AddProductModel.Builder productDetailsBuilder;
    String productConditionId = "0";
    int productAccesseroy_Id = 0;
    double longitude;
    double latitude;
    private Titlebar titlebar;
    private boolean isService;
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private Country selectedCountry;
    private City selectedCity;

    //Ids and values of product to be added and modified
    static int classifiedAge;
    String warrenty_id, fuel_type_id, doorId, colorId, mega_pixels, expandable_memory, cellular_type_id, smart_features, bedrooms, bathrooms, pets_allowed, total_cheques, balconies, seller_type_id, type_id, city_id = "0";
    String built_year, screen_size, country, city, country_id;

    public void setProductDetailsBuilder(AddProductModel.Builder productDetailsBuilder) {
        this.productDetailsBuilder = productDetailsBuilder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_moreaboutproduct, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
//        hideAllOptions();
        getCountries();
        setListners();
        init();
        //  getConditionCategory();
        setData();
        return binding.getRoot();
    }

    AddProductModel model;

    @Override
    public void onViewCreated(@android.support.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = productDetailsBuilder.build();
        setViews(model.getCategory_id(), model.getSubCategories());


    }

    private void setListners() {
        googlePlaceHelper = new GooglePlaceHelper(mainActivity, GooglePlaceHelper.PLACE_PICKER, this, this);
        (mainActivity).setOnActivityResultInterface(this);
        binding.spProductYaer.setOnItemSelectedListener(this);
//      binding.spProductCondition.setOnItemSelectedListener(this);
//      binding.tvproductLocation.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        rdNone.setOnClickListener(this);
        rdOrignal.setOnClickListener(this);
        rdReplace.setOnClickListener(this);
        binding.tvAgeChanger.setOnClickListener(this);
        binding.tvCountry.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.spinnerDoors.setOnItemSelectedListener(this);
        binding.spinnerColors.setOnItemSelectedListener(this);
        binding.spinnerFuel.setOnItemSelectedListener(this);
        binding.spinnerWarranty.setOnItemSelectedListener(this);
        binding.spinnerScreensize.setOnItemSelectedListener(this);
        binding.spProductCondition.setOnItemSelectedListener(this);
        binding.spinnerCellularType.setOnItemSelectedListener(this);
        binding.spinnerBedrooms.setOnItemSelectedListener(this);
        binding.spinnerBathrooms.setOnItemSelectedListener(this);
        binding.spinnerCheques.setOnItemSelectedListener(this);
        binding.spinnerBalconies.setOnItemSelectedListener(this);
        binding.spinnerSellerType.setOnItemSelectedListener(this);

        radioGroupExpandableMemory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rdExpandableMemoryYes:
                        if (checkedRadioButton.isChecked()) {
                            expandable_memory = String.valueOf(AppConstant.YES);
                        }
                        break;

                    case R.id.rdExpandableMemoryNo:
                        if (checkedRadioButton.isChecked()) {
                            expandable_memory = String.valueOf(AppConstant.NO);
                        }
                        break;
                }
            }
        });

        radioGroupSmartFeatures.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rdSmartFeaturesYes:
                        if (checkedRadioButton.isChecked()) {
                            smart_features = String.valueOf(AppConstant.YES);
                        }
                        break;

                    case R.id.rdSmartFeaturesNo:
                        if (checkedRadioButton.isChecked()) {
                            smart_features = String.valueOf(AppConstant.NO);
                        }
                        break;
                }
            }
        });

        radioGroupPetsAllowed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rdPetsAllowedYes:
                        if (checkedRadioButton.isChecked()) {
                            pets_allowed = String.valueOf(AppConstant.YES);
                        }
                        break;

                    case R.id.rdPetsAllowedNo:
                        if (checkedRadioButton.isChecked()) {
                            pets_allowed = String.valueOf(AppConstant.NO);
                        }
                        break;
                }
            }
        });


        binding.spType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (position == 0) {
                    type_id = "0";
                } else {
                    type_id = mainActivity.getCategories().getType().get(position - 1).getName();
                }

            }
        });

    }

    private void init() {


        if (mainActivity.getCategories() != null) {

            int dataposition = 0;
            year = new ArrayList<>();
            condition = new ArrayList<>();
            int currentyear = Calendar.getInstance().get(Calendar.YEAR);
            year.add(mainActivity.getResources().getString(R.string.how_old_is_the_product));

            for (int i = 0; i < 10; i++) {
                year.add(String.valueOf(currentyear - i));
            }

//        year.add("1991");
//        year.add("1992");
//        year.add("1993");
//        year.add("1994");
//        year.add("1995");
//        year.add("1996");

            binding.spProductYaer.setItems(year);
            if (ProductCreation.getInstance().getCurrentProduct() !=null && ProductCreation.getInstance().getCurrentProduct().getBuiltYear() != null) {
                for (int pos = 0; pos < year.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getBuiltYear().equals(year.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                binding.spProductYaer.setSelectedIndex(dataposition);
                built_year = year.get(dataposition);

            }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getDoors() != null) {
                if (mainActivity.getCategories().getDoors().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_number_of_doors));
                    for (int pos = 0; pos < mainActivity.getCategories().getDoors().size(); pos++) {
                        data.add(mainActivity.getCategories().getDoors().get(pos).getName());
                    }
                    binding.spinnerDoors.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getDoors() != null) {
                dataposition =0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getDoors().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }


                if (dataposition == 0) {


                } else {
                    binding.spinnerDoors.setSelectedIndex(dataposition);

                    doorId = String.valueOf(data.get(dataposition));
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getColors() != null) {
                if (mainActivity.getCategories().getColors().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_colors));
                    for (int pos = 0; pos < mainActivity.getCategories().getColors().size(); pos++) {
                        data.add(mainActivity.getCategories().getColors().get(pos).getName());
                    }
                    binding.spinnerColors.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getColor() != null) {
                dataposition =0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getColor().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerColors.setSelectedIndex(dataposition);

                    colorId = data.get(dataposition);


                }

            }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getFuel_type() != null) {
                if (mainActivity.getCategories().getFuel_type().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_fuel_type));
                    for (int pos = 0; pos < mainActivity.getCategories().getFuel_type().size(); pos++) {
                        data.add(mainActivity.getCategories().getFuel_type().get(pos).getName());
                    }
                    binding.spinnerFuel.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getFuel_type() != null) {
                dataposition =0;

                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getFuel_type().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerFuel.setSelectedIndex(dataposition);
                    fuel_type_id = String.valueOf(data.get(dataposition));
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getType() != null) {
                if (mainActivity.getCategories().getType().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_type));
                    for (int pos = 0; pos < mainActivity.getCategories().getType().size(); pos++) {
                        data.add(mainActivity.getCategories().getType().get(pos).getName());
                    }
                    binding.spType.setItems(data);
                }
            }
//            if (ProductCreation.getInstance().getCurrentProduct().getType() != null) {
//                dataposition=0;
//                for (int pos = 0; pos < data.size(); pos++) {
//                    if (ProductCreation.getInstance().getCurrentProduct().getType().equals(data.get(pos))) {
//                        dataposition = pos;
//                        break;
//                    }
//                }
//                if (dataposition == 0) {
//
//
//                } else {
//                    binding.spType.setSelectedIndex(dataposition);
//                    type_id = String.valueOf(data.get(dataposition));
//                }
//
//            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            data = new ArrayList<>();
            data.addAll(Arrays.asList(AppConstant.SCREEN_SIZES));
            data.add(0, mainActivity.getResources().getString(R.string.select_screensize));
            binding.spinnerScreensize.setItems(data);

            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getScreen_size() != null) {
               dataposition=0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getScreen_size().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerScreensize.setSelectedIndex(dataposition);
                    screen_size = data.get(dataposition);
                }

            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getWarrenty() != null) {
                if (mainActivity.getCategories().getWarrenty().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_warranty));
                    for (int pos = 0; pos < mainActivity.getCategories().getWarrenty().size(); pos++) {
                        data.add(mainActivity.getCategories().getWarrenty().get(pos).getName());
                    }
                    binding.spinnerWarranty.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getWarrenty() != null) {
                dataposition = 0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getWarrenty().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerWarranty.setSelectedIndex(dataposition);
                    warrenty_id = String.valueOf(data.get(dataposition));
                }

            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getCondition() != null) {
                if (mainActivity.getCategories().getCondition().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_product_condition));
                    for (int pos = 0; pos < mainActivity.getCategories().getCondition().size(); pos++) {
                        data.add(mainActivity.getCategories().getCondition().get(pos).getName());
                    }
                    binding.spProductCondition.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getCondition() != null) {
                dataposition = 0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getCondition().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spProductCondition.setSelectedIndex(dataposition);
                    productConditionId = mainActivity.getCategories().getCondition().get(dataposition - 1).getId();
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (mainActivity.getCategories().getCellular_type() != null) {
                if (mainActivity.getCategories().getCellular_type().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_cellular_type));
                    for (int pos = 0; pos < mainActivity.getCategories().getCellular_type().size(); pos++) {
                        data.add(mainActivity.getCategories().getCellular_type().get(pos).getName());
                    }
                    binding.spinnerCellularType.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getProduct_cellular_type() != null) {
                dataposition=0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getProduct_cellular_type().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerCellularType.setSelectedIndex(dataposition);
                    cellular_type_id = String.valueOf(mainActivity.getCategories().getCellular_type().get(dataposition - 1).getId());
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            data = new ArrayList<>();
            data.add(mainActivity.getResources().getString(R.string.select_bedrooms));
            for (int pos = 1; pos <= 10; pos++) {
                data.add(pos + "");
            }
            binding.spinnerBedrooms.setItems(data);

            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getBedrooms() != 0) {
                dataposition=0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (String.valueOf(ProductCreation.getInstance().getCurrentProduct().getBedrooms()).equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }

                if (dataposition == 0) {


                } else {
                    binding.spinnerBedrooms.setSelectedIndex(dataposition);
                    bedrooms = String.valueOf(data.get(dataposition));
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            data = new ArrayList<>();
            data.add(mainActivity.getResources().getString(R.string.select_bathrooms));
            for (int pos = 1; pos <= 10; pos++) {
                data.add(pos + "");
            }
            binding.spinnerBathrooms.setItems(data);
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getBathrooms() != 0) {
                dataposition=0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (String.valueOf(ProductCreation.getInstance().getCurrentProduct().getBathrooms()).equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerBathrooms.setSelectedIndex(dataposition);

                    bathrooms = String.valueOf(data.get(dataposition));
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            data = new ArrayList<>();
            data.add(mainActivity.getResources().getString(R.string.select_cheques));
            data.add("1");
            data.add("2");
            data.add("4");
            data.add("6");
            data.add("12");
            binding.spinnerCheques.setItems(data);
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getTotal_cheques() != 0) {
                dataposition = 0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (String.valueOf(ProductCreation.getInstance().getCurrentProduct().getTotal_cheques()).equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerCheques.setSelectedIndex(dataposition);

                    total_cheques = String.valueOf(data.get(dataposition));
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            data = new ArrayList<>();
            data.add(mainActivity.getResources().getString(R.string.select_balconies));
            for (int pos = 1; pos <= 6; pos++) {
                data.add(pos + "");
            }
            binding.spinnerBalconies.setItems(data);


            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getBalconies() != 0) {
                dataposition = 0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (String.valueOf(ProductCreation.getInstance().getCurrentProduct().getBalconies()).equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerBalconies.setSelectedIndex(dataposition);

                    balconies = String.valueOf(data.get(dataposition));
                }
            }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (mainActivity.getCategories().getSeller_type() != null) {
                if (mainActivity.getCategories().getSeller_type().size() > 0) {
                    data = new ArrayList<>();
                    data.add(mainActivity.getResources().getString(R.string.select_seller_type));
                    for (int pos = 0; pos < mainActivity.getCategories().getSeller_type().size(); pos++) {
                        data.add(mainActivity.getCategories().getSeller_type().get(pos).getName());
                    }
                    binding.spinnerSellerType.setItems(data);
                }
            }
            if (ProductCreation.getInstance().getCurrentProduct()!=null &&ProductCreation.getInstance().getCurrentProduct().getSeller_type() != null) {
                dataposition = 0;
                for (int pos = 0; pos < data.size(); pos++) {
                    if (ProductCreation.getInstance().getCurrentProduct().getSeller_type().equals(data.get(pos))) {
                        dataposition = pos;
                        break;
                    }
                }
                if (dataposition == 0) {


                } else {
                    binding.spinnerSellerType.setSelectedIndex(dataposition);
                    seller_type_id = String.valueOf(data.get(dataposition));
                }
            }


        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvCountry:
                openCountries();
                break;

            case R.id.tvCity:
                openCities();
                break;

            case R.id.btnNext:

                if (validated()) {


                    switch (model.getCategory_id()) {
                        case AppConstant.CategoriesIds.PROPERTIES:

                            model = productDetailsBuilder.withUserId(preferenceHelper.getUser().getId()).
                                    withOpeningTime("10").withClosingTime("12").build();
                            break;

                        default:

                            model = productDetailsBuilder.withLocation(binding.tvproductLocation.getText().toString())
                                    .withModelNumber("" + binding.etProductModelNumber.getText().toString())
                                    .withBuiltYear(0 + Integer.valueOf(binding.spProductYaer.getText().toString()))
                                    .withProductConditionId(productConditionId)
                                    .withAccessoryTypeId(0 + String.valueOf(productAccesseroy_Id))
                                    .withDescription(binding.inputProductDetail.getText().toString())
                                    .withGuidelines(binding.inputGuidelines.getText().toString())
                                    .withLongitude(String.valueOf(longitude))
                                    .withLatitude(String.valueOf(latitude))
                                    .withUserId(preferenceHelper.getUser().getId()).withOpeningTime("10").withClosingTime("12")
                                    .build();

                            break;


                    }


                    addProduct(model);
                }

                break;
            case R.id.tvproductLocation:
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

            case R.id.rdNone:
                none();
                break;


            case R.id.rdOrignal:
                original();
                break;


            case R.id.rdReplace:
                replace();
                break;

            case R.id.tvAgeChanger:
                SelectBirthDate selectBirthDate = new SelectBirthDate(mainActivity, (TextView) view);
                selectBirthDate.show(mainActivity.getSupportFragmentManager(), "BirthDatePicker");
                break;

        }

    }

    private boolean validated() {

        return validator(model.getSubCategories());

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.setTitle(mainActivity.getResources().getString(R.string.about_product));
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (view.getId()) {
            case R.id.spProductYaer:
                if (position == 0) {
                    built_year = null;
                } else {
                    built_year = year.get(position);
                }
                binding.spProductYaer.setText(year.get(position));
                break;

            case R.id.spinnerDoors:
                if (position == 0) {
                    doorId = "0";
                } else {
                    doorId = mainActivity.getCategories().getDoors().get(position - 1).getId();
                }
                break;

            case R.id.spinnerColors:
                if (position == 0) {
                    colorId = "0";
                } else {
                    colorId = mainActivity.getCategories().getColors().get(position - 1).getId();
                }
                break;

            case R.id.spinnerFuel:
                if (position == 0) {
                    fuel_type_id = "0";
                } else {
                    fuel_type_id = mainActivity.getCategories().getFuel_type().get(position - 1).getId();
                }
                break;

            case R.id.spinnerWarranty:
                if (position == 0) {
                    warrenty_id = "0";
                } else {
                    warrenty_id = mainActivity.getCategories().getWarrenty().get(position - 1).getId();
                }
                break;

            case R.id.spinnerScreensize:
                if (position == 0) {
                    screen_size = null;
                } else {
                    screen_size = item.toString();
                }
                break;

            case R.id.spProductCondition:
                if (position == 0) {
                    productConditionId = "0";
                } else {
                    productConditionId = mainActivity.getCategories().getCondition().get(position - 1).getId();
                }
                break;

            case R.id.spinnerCellularType:
                if (position == 0) {
                    cellular_type_id = "0";
                } else {
                    cellular_type_id = mainActivity.getCategories().getCellular_type().get(position - 1).getId();
                }
                break;

            case R.id.spinnerBedrooms:
                if (position == 0) {
                    bedrooms = "0";
                } else {
                    bedrooms = String.valueOf(item.toString());
                }
                break;

            case R.id.spinnerBathrooms:
                if (position == 0) {
                    bathrooms = "0";
                } else {
                    bathrooms = String.valueOf(item.toString());
                }
                break;

            case R.id.spinnerCheques:
                if (position == 0) {
                    total_cheques = "0";
                } else {
                    total_cheques = String.valueOf(item.toString());
                }
                break;

            case R.id.spinnerBalconies:
                if (position == 0) {
                    balconies = "0";
                } else {
                    balconies = String.valueOf(item.toString());
                }
                break;

            case R.id.spinnerSellerType:
                if (position == 0) {
                    seller_type_id = "0";
                } else {
                    seller_type_id = mainActivity.getCategories().getSeller_type().get(position - 1).getId();
                }
                break;
//                case R.id.spType:
//                if (position == 0) {
//                    type_id = "0";
//                } else {
//                    type_id = mainActivity.getCategories().getType().get(position - 1).getName();
//                }
//                break;
        }
    }

    @Override
    public void onPlaceActivityResult(double longitude, double latitude, String address, String country, String city) throws IOException {
        binding.tvproductLocation.setText(address);
        this.longitude = longitude;
        this.latitude = latitude;

    }

    @Override
    public void onError(String error) {


    }

    @Override
    public void onActivityResultInterface(int requestCode, int resultCode, Intent data) throws IOException {
        googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
    }

    Category category;

    public void getConditionCategory() {

        WebApiRequest.getInstance(mainActivity).genricObjectRequest(null, AppConstant.ALL_CATEGORYS, new WebApiRequest.APIRequestObjectCallBack() {
            @Override
            public void onSuccess(Api_Response response) {

                category = (Category) JsonHelpers.convertToModelClass(response.getResult(), Category.class);


                for (ConditionType condtype : category.getCondition_types()) {
                    condition.add(condtype.getName());
                }
                binding.spProductCondition.setItems(condition);

                if (ProductCreation.getInstance().isEditing()) {
                    int conditionPosition = 0;
                    for (int pos = 0; pos < condition.size(); pos++) {
                        if (ProductCreation.getInstance().getCurrentProduct().getProductCondition().equals(condition.get(pos))) {
                            conditionPosition = pos;
                            break;
                        }
                    }
                    if (conditionPosition == 0) {

                    } else {

                        binding.spProductCondition.setSelectedIndex(conditionPosition);
                    }
                }

            }

            @Override
            public void onError() {

            }

            @Override
            public void onNoNetwork() {

            }
        });


    }

    public void addProduct(AddProductModel model) {
        String paymentTypes = "", selectedDays = "", rentTypes = "";
        for (int pos = 0; pos < ProductCreation.getInstance().getPaymentTypes().size(); pos++) {
            paymentTypes += ProductCreation.getInstance().getPaymentTypes().get(pos).getId() + "";
            if (pos != ProductCreation.getInstance().getPaymentTypes().size() - 1) {
                paymentTypes += ", ";
            }
        }

        for (int pos = 0; pos < ProductCreation.getInstance().getRentTypes().size(); pos++) {
            rentTypes += ProductCreation.getInstance().getRentTypes().get(pos).getId() + "";
            if (pos != ProductCreation.getInstance().getRentTypes().size() - 1) {
                rentTypes += ", ";
            }
        }

        for (int pos = 0; pos < ProductCreation.getInstance().getDays().size(); pos++) {
            if (ProductCreation.getInstance().getDays().get(pos).isChecked()) {
                selectedDays += ProductCreation.getInstance().getDays().get(pos).getName();
                selectedDays += ", ";
            }
        }
        if (selectedDays.length() > 1) {
            selectedDays = selectedDays.trim();
            if (selectedDays.charAt(selectedDays.length() - 1) == ',') {
                selectedDays = selectedDays.substring(0, selectedDays.length() - 1);
            }
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("user_id", createPartFromString("" + String.valueOf(model.getUser_id())));
        map.put("accessory_type_id", createPartFromString("" + model.getAccessory_type_id()));
        map.put("amount", createPartFromString("" + model.getAmount()));
        map.put("brand_id", createPartFromString("" + String.valueOf(model.getBrand_id())));
        map.put("built_year", createPartFromString("" + String.valueOf(model.getBuilt_year())));
        map.put("category_id", createPartFromString("" + String.valueOf(model.getCategory_id())));
        map.put("sub_category_id", createPartFromString("" + String.valueOf(model.getSubCategories())));
        map.put("closing_time", createPartFromString("" + model.getClosing_time()));
        map.put("currency_code", createPartFromString("" + model.getCurrency_code()));
        map.put("description", createPartFromString("" + model.getDescription()));
        map.put("guidelines", createPartFromString("" + model.getGuidelines()));
        map.put("latitude", createPartFromString("" + model.getLatitude()));
        map.put("location", createPartFromString("" + binding.tvproductLocation.getText().toString()));
        map.put("longitude", createPartFromString("" + model.getLongitude()));
        map.put("model_number", createPartFromString("" + model.getModel_number()));
        map.put("opening_time", createPartFromString("" + model.getOpening_time()));
        map.put("payment_type_id", createPartFromString("" + paymentTypes));
        map.put("rent_type_ids", createPartFromString("" + rentTypes));
        map.put("product_on", createPartFromString("" + String.valueOf(ProductCreation.getInstance().getProductOn())));
        map.put("prod_avail_days", createPartFromString("" + selectedDays));
        map.put("product_condition_id", createPartFromString("" + String.valueOf(model.getProduct_condition_id())));
        map.put("rent_type_id", createPartFromString("" + String.valueOf(model.getRent_type_id())));
        map.put("title", createPartFromString("" + model.getTitle()));

        map.put("warrenty_id", createPartFromString("" + warrenty_id));
        map.put("fuel_type_id", createPartFromString("" + fuel_type_id));
        map.put("color_id", createPartFromString("" + colorId));
        map.put("doors_id", createPartFromString("" + doorId));
        map.put("condition_id", createPartFromString("" + productConditionId));
        map.put("kilometers", createPartFromString("" + etKilometers.getText().toString()));
        map.put("year", createPartFromString("" + binding.spProductYaer.getText().toString()));
        map.put("make_model_id", createPartFromString("" + model.getModels()));
        map.put("mega_pixels", createPartFromString("" + etMegaPixels.getText().toString()));
        map.put("screen_size", createPartFromString("" + screen_size));
        map.put("expandable_memory", createPartFromString("" + expandable_memory));
        map.put("operating_system", createPartFromString("" + etOperatingSystems.getText().toString()));
        map.put("processor", createPartFromString("" + etProcessor.getText().toString()));
        map.put("cellular_type_id", createPartFromString("" + cellular_type_id));
        map.put("cellular_type", createPartFromString("" + binding.spinnerCellularType.getText().toString()));
        map.put("smart_features", createPartFromString("" + smart_features));
        map.put("total_area", createPartFromString("" + etTotalArea.getText().toString()));
        map.put("bedrooms", createPartFromString("" + bedrooms));
        map.put("bathrooms", createPartFromString("" + bathrooms));
        map.put("pets_allowed", createPartFromString("" + pets_allowed));
        map.put("total_cheques", createPartFromString("" + total_cheques));
        map.put("balconies", createPartFromString("" + balconies));
        map.put("seller_type_id", createPartFromString("" + seller_type_id));
        map.put("accessories", createPartFromString("" + productAccesseroy_Id));
        map.put("type_id", createPartFromString("" + type_id));
        map.put("type", createPartFromString("" + binding.spType.getText().toString()));
        map.put("country_id", createPartFromString("" + country_id));
        map.put("city_id", createPartFromString("" + city_id));
        map.put("country", createPartFromString("" + binding.tvCountry.getText().toString()));
        map.put("city", createPartFromString("" + binding.tvCity.getText().toString()));
        map.put("compatibility", createPartFromString("" + binding.etCompatibility.getText().toString()));

        ArrayList<MultipartBody.Part> mediaFiles = new ArrayList<>();
        for (Itemlistobject item : model.getImagesList()) {
            if (item.isNewImage()) {
                File file = new File(item.getPhoto());
                mediaFiles.add(MultipartBody.Part.createFormData("images[]", file.getName(), RequestBody.create(MediaType.parse("image/"), file)));
            }
        }

        //Edit Product
        if (ProductCreation.getInstance().isEditing()) {
            map.put("product_id", createPartFromString(String.valueOf(ProductCreation.getInstance().getCurrentProduct().getId())));

            if (ProductCreation.getInstance().getDeletedImagesIds() != null) {
                String ids = "";
                for (int pos = 0; pos < ProductCreation.getInstance().getDeletedImagesIds().size(); pos++) {
                    ids += ProductCreation.getInstance().getDeletedImagesIds().get(pos);
                    if (pos != ProductCreation.getInstance().getDeletedImagesIds().size() - 1) {
                        ids += ", ";
                    }
                }
                map.put("deleted_product_images", createPartFromString(String.valueOf(ids)));
            }

            serviceHelper.enqueueCall(webService.editProduct(map, mediaFiles), WebServiceConstants.EDIT_PRODUCTS);
        } else {
            serviceHelper.enqueueCall(webService.addProducts2(map, mediaFiles), WebServiceConstants.ADD_PRODUCTS);
        }

    }

    @Override
    public void ResponseSuccess(Object result, String tag) {
        isService = true;

        switch (tag) {
            case AppConstant.GET_COUNTRIES:
                countries = (ArrayList<Country>) result;
                break;

            case AppConstant.GET_CITIES:
                cities = (ArrayList<City>) result;
                break;

            case WebServiceConstants.ADD_PRODUCTS:
                UIHelper.showToast(mainActivity, getString(R.string.product_added));
                // mainActivity.replaceFragmentWithClearBackStack(new HomeFragment(),true,true);
                ///   mainActivity.rbAccount.setChecked(true);
                mainActivity.setCategories(null);
                ProductCreation.getInstance().reset();
                mainActivity.replaceFragmentWithClearBackStack(new UserAccountFragment(), true, true);
                break;

            case WebServiceConstants.EDIT_PRODUCTS:
                UIHelper.showToast(mainActivity, getString(R.string.product_updated));
                // mainActivity.replaceFragmentWithClearBackStack(new HomeFragment(),true,true);
                ///   mainActivity.rbAccount.setChecked(true);
                mainActivity.setCategories(null);
                ProductCreation.getInstance().reset();
                mainActivity.replaceFragmentWithClearBackStack(new UserAccountFragment(), true, true);
                break;
        }


    }

    @NonNull
    private RequestBody createPartFromString(String value) {
        return RequestBody.create(
                MultipartBody.FORM, value);
    }

    private void none() {
        rdNone.setChecked(true);
        rdOrignal.setChecked(false);
        rdReplace.setChecked(false);
        productAccesseroy_Id = AppConstant.AccessoriesTypes.NONE;
    }

    private void original() {
        rdOrignal.setChecked(true);
        rdNone.setChecked(false);
        rdReplace.setChecked(false);
        productAccesseroy_Id = AppConstant.AccessoriesTypes.ORIGINAL;
    }

    private void replace() {
        rdReplace.setChecked(true);
        rdOrignal.setChecked(false);
        rdNone.setChecked(false);
        productAccesseroy_Id = AppConstant.AccessoriesTypes.REPLACEMENT;
    }

    private void setData() {
        int yearPosition = 0;
        if (ProductCreation.getInstance().isEditing()) {

            if (ProductCreation.getInstance().getCurrentProduct().getLatitude() != null) {
                this.latitude = Double.parseDouble(ProductCreation.getInstance().getCurrentProduct().getLatitude());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getLongitude() != null) {
                this.longitude = Double.parseDouble(ProductCreation.getInstance().getCurrentProduct().getLongitude());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getLocation() != null) {
                binding.tvproductLocation.setText(ProductCreation.getInstance().getCurrentProduct().getLocation());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getModelNumber() != null) {
                binding.etProductModelNumber.setText(ProductCreation.getInstance().getCurrentProduct().getModelNumber());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getAccessoryTypeId() == AppConstant.AccessoriesTypes.ORIGINAL) {
                original();
            }

            if (ProductCreation.getInstance().getCurrentProduct().getAccessoryTypeId() == AppConstant.AccessoriesTypes.REPLACEMENT) {
                replace();
            }

            if (ProductCreation.getInstance().getCurrentProduct().getAccessoryTypeId() == AppConstant.AccessoriesTypes.NONE) {
                none();
            }

            if (ProductCreation.getInstance().getCurrentProduct().getDescription() != null) {
                binding.inputProductDetail.setText(ProductCreation.getInstance().getCurrentProduct().getDescription());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getGuidelines() != null) {
                binding.inputGuidelines.setText(ProductCreation.getInstance().getCurrentProduct().getGuidelines());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getCountry() != null) {
                binding.tvCountry.setText(ProductCreation.getInstance().getCurrentProduct().getCountry());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getCity() != null) {
                binding.tvCity.setText(ProductCreation.getInstance().getCurrentProduct().getCity());
            }
            if (ProductCreation.getInstance().getCurrentProduct().getCity_id() != null) {
                city_id = ProductCreation.getInstance().getCurrentProduct().getCity_id();
            }
            if (ProductCreation.getInstance().getCurrentProduct().getCountry_id() != null) {
                country_id = ProductCreation.getInstance().getCurrentProduct().getCountry_id();
            }

//            if (ProductCreation.getInstance().getCurrentProduct().getType_id() != null) {
//                type_id = ProductCreation.getInstance().getCurrentProduct().getType_id();
//            }

            if (ProductCreation.getInstance().getCurrentProduct().getType_id() != null) {
                binding.spType.setText(ProductCreation.getInstance().getCurrentProduct().getType_id());
                type_id = ProductCreation.getInstance().getCurrentProduct().getType_id();
            }


//
//            if (ProductCreation.getInstance().getCurrentProduct().getBuiltYear() != null) {
//                for (int pos = 0; pos < condition.size(); pos++) {
//                    if (ProductCreation.getInstance().getCurrentProduct().getBuiltYear().equals(year.get(pos))) {
//                        yearPosition = pos;
//                        break;
//                    }
//                }
//                binding.spProductYaer.setSelectedIndex(yearPosition);
//                built_year =year.get(yearPosition);
//            }


            if (ProductCreation.getInstance().getCurrentProduct().getTitle() != null) {
                binding.etTitle.setText(ProductCreation.getInstance().getCurrentProduct().getTitle());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getMega_pixels() != null) {
                binding.etMegaPixels.setText(ProductCreation.getInstance().getCurrentProduct().getMega_pixels());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getOperating_system() != null) {
                binding.etOperatingSystems.setText(ProductCreation.getInstance().getCurrentProduct().getOperating_system());
            }
            if (ProductCreation.getInstance().getCurrentProduct().getProcessor() != null) {
                binding.etProcessor.setText(ProductCreation.getInstance().getCurrentProduct().getProcessor());
            }
            if (ProductCreation.getInstance().getCurrentProduct().getKilometers() != null) {
                binding.etKilometers.setText(ProductCreation.getInstance().getCurrentProduct().getKilometers());
            }
            if (ProductCreation.getInstance().getCurrentProduct().getTotal_area() != null) {
                binding.etTotalArea.setText(ProductCreation.getInstance().getCurrentProduct().getTotal_area());
            }


        }
    }

    @Override
    public void onDestroyView() {
        if (!isService) {
            titlebar.setTitle(getString(R.string.product_plan));
            titlebar.showBackButton(mainActivity);
        }
        super.onDestroyView();

    }

    private void hideAllOptions() {
        binding.llProductYear.setVisibility(View.GONE);
        binding.llKilometers.setVisibility(View.GONE);
        binding.llDoors.setVisibility(View.GONE);
        binding.llColor.setVisibility(View.GONE);
        binding.llFuel.setVisibility(View.GONE);
        binding.llWarranty.setVisibility(View.GONE);
        binding.llMegaPixels.setVisibility(View.GONE);
        binding.llScreensize.setVisibility(View.GONE);
        binding.llExpandableMemory.setVisibility(View.GONE);
        binding.llConditionType.setVisibility(View.GONE);
        binding.llAccessories.setVisibility(View.GONE);
        binding.llOperatingSystems.setVisibility(View.GONE);
        binding.llProcessor.setVisibility(View.GONE);
        binding.llCellularType.setVisibility(View.GONE);
        binding.llSmartFeatures.setVisibility(View.GONE);
        binding.llPetsAllowed.setVisibility(View.GONE);
        binding.llTotalArea.setVisibility(View.GONE);
        binding.llBedrooms.setVisibility(View.GONE);
        binding.llBathrooms.setVisibility(View.GONE);
        binding.llCheques.setVisibility(View.GONE);
        binding.llBalconies.setVisibility(View.GONE);
        binding.llSellerType.setVisibility(View.GONE);
        binding.llCompatibility.setVisibility(View.GONE);
        binding.llProductNumber.setVisibility(View.GONE);
        binding.llType.setVisibility(View.GONE);
    }


    private void setViews(int mainCategoryId, int subCategoryId) {
        hideAllOptions();


        switch (mainCategoryId) {
            case AppConstant.CategoriesIds.AUTOMOBILES:
                binding.llProductYear.setVisibility(View.VISIBLE);
                binding.llKilometers.setVisibility(View.VISIBLE);
                binding.llDoors.setVisibility(View.VISIBLE);
                binding.llColor.setVisibility(View.VISIBLE);
                binding.llFuel.setVisibility(View.VISIBLE);
                binding.llWarranty.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.ELECTRONICS:
                binding.llType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                binding.llMegaPixels.setVisibility(View.VISIBLE);
                binding.llScreensize.setVisibility(View.VISIBLE);
                binding.llExpandableMemory.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProcessor.setVisibility(View.VISIBLE);
                binding.llCellularType.setVisibility(View.VISIBLE);
                binding.llSmartFeatures.setVisibility(View.VISIBLE);
                binding.llOperatingSystems.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.PROPERTIES:
                binding.llTotalArea.setVisibility(View.VISIBLE);
                binding.llBedrooms.setVisibility(View.VISIBLE);
                binding.llBathrooms.setVisibility(View.VISIBLE);
                binding.llPetsAllowed.setVisibility(View.VISIBLE);
                binding.llCheques.setVisibility(View.VISIBLE);
                binding.llBalconies.setVisibility(View.VISIBLE);
                binding.llSellerType.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.MACHINERYEQUIPMENT:
                binding.llProductYear.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.CLASSIFIED:
                binding.llProductYear.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llType.setVisibility(View.VISIBLE);
                break;
        }

        switch (subCategoryId) {
            case AppConstant.CategoriesIds.CAR:
                break;
            case AppConstant.CategoriesIds.MOTORBIKE:
                binding.llDoors.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.AC_WATER_LEAK:
                break;
            case AppConstant.CategoriesIds.HEAVY_VEHICLES:
                break;
            case AppConstant.CategoriesIds.BOATS:
                binding.llDoors.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.AUTO_PARTS:
                binding.llKilometers.setVisibility(View.GONE);
                binding.llDoors.setVisibility(View.GONE);
                binding.llColor.setVisibility(View.GONE);
                binding.llFuel.setVisibility(View.GONE);
                binding.llWarranty.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.PLATES:
                break;
            case AppConstant.CategoriesIds.HOME_APPLIANCES:
                binding.llMegaPixels.setVisibility(View.GONE);
                binding.llScreensize.setVisibility(View.GONE);
                binding.llExpandableMemory.setVisibility(View.GONE);
                binding.llProcessor.setVisibility(View.GONE);
                binding.llCellularType.setVisibility(View.GONE);
                binding.llSmartFeatures.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.CAMERAS:
                binding.llScreensize.setVisibility(View.GONE);
                binding.llExpandableMemory.setVisibility(View.GONE);
                binding.llProcessor.setVisibility(View.GONE);
                binding.llCellularType.setVisibility(View.GONE);
                binding.llSmartFeatures.setVisibility(View.GONE);
                binding.llTitle.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.MOBILE_PHONES:
                binding.llMegaPixels.setVisibility(View.GONE);
                binding.llSmartFeatures.setVisibility(View.GONE);
                binding.llTitle.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.TELEVISIONS_SCREENS:
                binding.llType.setVisibility(View.GONE);
                binding.llMegaPixels.setVisibility(View.GONE);
                binding.llExpandableMemory.setVisibility(View.GONE);
                binding.llProcessor.setVisibility(View.GONE);
                binding.llOperatingSystems.setVisibility(View.GONE);
                binding.llCellularType.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.TABLETS_LAPTOPS:
                binding.llMegaPixels.setVisibility(View.GONE);
                binding.llTitle.setVisibility(View.GONE);
                // binding.llScreensize.setVisibility(View.GONE);
                binding.llExpandableMemory.setVisibility(View.GONE);
                binding.llProcessor.setVisibility(View.GONE);
                binding.llCellularType.setVisibility(View.GONE);
                binding.llSmartFeatures.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.DIGITAL:
                binding.llMegaPixels.setVisibility(View.GONE);
                binding.llScreensize.setVisibility(View.GONE);
                binding.llExpandableMemory.setVisibility(View.GONE);
                binding.llProcessor.setVisibility(View.GONE);
                binding.llCellularType.setVisibility(View.GONE);
                binding.llSmartFeatures.setVisibility(View.GONE);
                break;
            case AppConstant.CategoriesIds.APARTMENT:
                break;
            case AppConstant.CategoriesIds.VILLA:
                break;
            case AppConstant.CategoriesIds.HOUSE:
                break;
            case AppConstant.CategoriesIds.PLOT:
                break;
            case AppConstant.CategoriesIds.OFFICE:
                break;
            case AppConstant.CategoriesIds.WAREHOUSE:
                break;
            case AppConstant.CategoriesIds.INDUSTRIAL_EQUIPMENT:
                break;
            case AppConstant.CategoriesIds.OFFICE_EQUIPMENT:
                break;
            case AppConstant.CategoriesIds.SPORTS_EQUIPMENT:
                break;
            case AppConstant.CategoriesIds.MACHINERY:
                binding.llAccessories.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.FURNITURE:
                binding.llTitle.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.CLOTHING:
                binding.llTitle.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.BABY_ITEMS:
                binding.llTitle.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.BOOKS:
                break;
            case AppConstant.CategoriesIds.GAMING:
                binding.llTitle.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.PETS:
                binding.llAge.setVisibility(View.VISIBLE);
                binding.llTitle.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                break;
            case AppConstant.CategoriesIds.OTHER:
                binding.llTitle.setVisibility(View.VISIBLE);
                binding.llConditionType.setVisibility(View.VISIBLE);
                binding.llProductYear.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void diffView(String key) {
        switch (key) {

            case "Car":

                break;

        }
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
                    country_id = selectedCountry.getCode();
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
                    city_id = String.valueOf(selectedCity.getiD());
                    city = onlynames.get(i);
                    getLatitudeAndLongitudeFromGoogleMapForAddress(onlynames.get(i));
                }
            }, mainActivity.getResources().getString(R.string.select_city), onlynames);
        } else {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_city_found));
        }
    }

    public void getCountries() {
        serviceHelper.enqueueCall(webService.getCountires(), AppConstant.GET_COUNTRIES);
    }

    public void getCities(String countryCode) {
        serviceHelper.enqueueCall(webService.getCities(countryCode), AppConstant.GET_CITIES);
    }

    @SuppressLint("ValidFragment")
    public static class SelectBirthDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        MainActivity mainActivity;
        TextView textView;
        int yy;
        int mm;
        int dd;

        SelectBirthDate(MainActivity mainActivity, TextView textView) {
            this.mainActivity = mainActivity;
            this.textView = textView;
        }

        @android.support.annotation.NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);
            yy = calendar.get(Calendar.YEAR);
            mm = calendar.get(Calendar.MONTH);
            dd = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            return datePickerDialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            int age = yy - year;
            if (age >= AppConstant.MINIMUM_AGE) {
                classifiedAge = age;
                if (classifiedAge == 1) {
                    this.textView.setText(classifiedAge + " " + mainActivity.getResources().getString(R.string.year));
                } else if (classifiedAge > 1) {
                    this.textView.setText(classifiedAge + " " + mainActivity.getResources().getString(R.string.years));
                }
            } else {
                UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.minimum_age_error));
            }
        }
    }

    //////////////////////Automobiles//////////////////////////
    boolean validateCar() {


//        if (binding.llKilometers.getVisibility() == View.GONE) {
//
//            return false;
//
//        }
//
//        if (binding.llConditionType.getVisibility() == View.GONE) {
//            return false;
//
//        }

//        if (binding.llDoors.getVisibility() == View.GONE) {
//
//            return false;
//
//        }

//        if (binding.llColor.getVisibility() == View.GONE) {
//
//            return false;
//
//        }
        //        if (binding.llFuel.getVisibility() == View.GONE) {
//
//            return false;
//
//        }
        //        if (binding.llWarranty.getVisibility() == View.GONE) {
//
//            return false;
//        }
//
        if (!genericFields())
            return false;

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (binding.etKilometers.getText().toString().equals("")) {
            UIHelper.showToast(getActivity(), getString(R.string.yearkilometer));
            return false;

        }

        if (productConditionId == "0") {

            if (binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError)))
                UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }

        if (doorId == "0" || binding.spinnerDoors.getText().toString().equals(getString(R.string.select_number_of_doors))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_number_of_doors));
            return false;
        }

        if (colorId == "0" || binding.spinnerColors.getText().toString().equals(getString(R.string.select_colors))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_colors));
            return false;
        }

        if (fuel_type_id == "0" || binding.spinnerFuel.getText().toString().equals(getString(R.string.select_fuel_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_fuel_type));
            return false;
        }


        if (warrenty_id == "0"  ) {
            UIHelper.showToast(getActivity(), getString(R.string.select_warranty));
            return false;
        }


        return true;


    }

    boolean validateMotorBike() {


        if (!genericFields())
            return false;


        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (binding.etKilometers.getText().length() == 0) {
            UIHelper.showToast(getActivity(), getString(R.string.yearkilometer));
            return false;

        }

        if (productConditionId == "0") {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }


        if (colorId == "0" || binding.spinnerColors.getText().toString().equals(getString(R.string.select_colors))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_colors));
            return false;
        }

        if (fuel_type_id == "0"|| binding.spinnerFuel.getText().toString().equals(getString(R.string.select_fuel_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_fuel_type));
            return false;
        }


        if (warrenty_id == "0") {
            UIHelper.showToast(getActivity(), getString(R.string.select_warranty));
            return false;
        }


        return true;
    }

    boolean validateHeavyVehicle() {

        if (!genericFields())
            return false;

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (binding.etKilometers.getText().toString().equals("")) {
            UIHelper.showToast(getActivity(), getString(R.string.yearkilometer));
            return false;

        }

        if (productConditionId == "0") {

            if (binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError)))
                UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }

        if (doorId == "0" || binding.spinnerDoors.getText().toString().equals(getString(R.string.select_number_of_doors))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_number_of_doors));
            return false;
        }

        if (colorId == "0" || binding.spinnerColors.getText().toString().equals(getString(R.string.select_colors))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_colors));
            return false;
        }

        if (fuel_type_id == "0" || binding.spinnerFuel.getText().toString().equals(getString(R.string.select_fuel_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_fuel_type));
            return false;
        }


        if (warrenty_id == "0"  ) {
            UIHelper.showToast(getActivity(), getString(R.string.select_warranty));
            return false;
        }

        return true;

    }

    boolean validateBoats() {

        if (!genericFields())
            return false;


        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (binding.etKilometers.getText().length() == 0) {
            UIHelper.showToast(getActivity(), getString(R.string.yearkilometer));
            return false;

        }

        if (productConditionId == "0") {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }


        if (colorId == "0" || binding.spinnerColors.getText().toString().equals(getString(R.string.select_colors))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_colors));
            return false;
        }

        if (fuel_type_id == "0"|| binding.spinnerFuel.getText().toString().equals(getString(R.string.select_fuel_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_fuel_type));
            return false;
        }


        if (warrenty_id == "0") {
            UIHelper.showToast(getActivity(), getString(R.string.select_warranty));
            return false;
        }


        return true;
    }

    boolean validateAutoParts() {

        if (!genericFields())
            return false;

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (productConditionId == "0") {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        return true;
    }


    //////////////////////Electronics//////////////////////////
    boolean validateHomeApplainces() {
        if (!genericFields())
            return false;
        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (binding.spType.getText().toString().equals(getString(R.string.select_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_type));
            return false;
        }
        if (productConditionId == "0") {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        return true;
    }

    boolean validateCameras() {
        if (genericFields())
            //return false;

            if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                UIHelper.showToast(getActivity(), getString(R.string.yearError));
                return false;
            }
        if (binding.spType.getText().toString().equals(getString(R.string.select_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_type));
            return false;
        }
        if (productConditionId == "0" || binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        if (binding.etMegaPixels.getText().toString().equals("")) {
            UIHelper.showToast(getActivity(), getString(R.string.enter_megapixels));
            return false;
        }


        return true;
    }

    boolean validateMobilePhones() {
        if (!genericFields()) {
            return false;
        }

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (binding.spType.getText().toString().equals(getString(R.string.select_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_type));
            return false;
        }
        if (binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }


        if (binding.spinnerScreensize.getText().toString().trim().equals(getString(R.string.select_screensize))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_screensize));
            return false;
        }


        if (binding.etOperatingSystems.getText().toString().trim().equals("")) {
            UIHelper.showToast(getActivity(), getString(R.string.operating_system_required));
            return false;
        }
        if (binding.etProcessor.getText().toString().trim().equals("")) {
            UIHelper.showToast(getActivity(), getString(R.string.processor_required));
            return false;
        }
        if (binding.spinnerCellularType.getText().toString().equals(getString(R.string.select_cellular_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_cellular_type));
            return false;
        }



        return true;
    }

    boolean validateTelevisionScreens() {
        if (!genericFields()) {
            return false;
        }
        if (productConditionId == "0"|| binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        if (binding.spinnerScreensize.getText().toString().trim().equals(getString(R.string.select_screensize))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_screensize));
            return false;
        }

        return true;


    }

    boolean validateTabletsLaptopsComputers() {

        if (!genericFields()) {
            return false;
        }

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (productConditionId == "0" ||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        if (binding.spinnerScreensize.getText().toString().trim().equals(getString(R.string.select_screensize))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_screensize));
            return false;
        }

        return true;

    }

    boolean validateDigital() {
        if (!genericFields())
            return false;
        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (productConditionId == "0" ||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        if (binding.etMegaPixels.getText().toString().equals("")) {
            UIHelper.showToast(getActivity(), getString(R.string.enter_megapixels));
            return false;
        }
        return true;

    }


    //////////////////////Properties//////////////////////////

    boolean validatePropertires() {
        if (country_id == null) {

            UIHelper.showToast(getActivity(), getString(R.string.select_country));
            return false;
        }
        if (city_id == "0") {

            UIHelper.showToast(getActivity(), getString(R.string.select_city));
            return false;
        }

        if (etTotalArea.getText().length() == 0) {
            UIHelper.showToast(getActivity(), getString(R.string.areaerror));
            return false;
        }


        if (/*bedrooms == 0*/  binding.spinnerBedrooms.getText().toString().equals(getString(R.string.select_bedrooms))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_bedrooms));
            return false;
        }


        if (/*bathrooms == 0*/ binding.spinnerBathrooms.getText().toString().equals(getString(R.string.select_bathrooms))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_bathrooms));
            return false;
        }

        if (/*total_cheques == 0*/ binding.spinnerCheques.getText().toString().equals(getString(R.string.select_cheques))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_cheques));
            return false;
        }


        if (/*balconies == 0*/ binding.spinnerBalconies.getText().toString().equals(getString(R.string.select_balconies))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_balconies));
            return false;
        }


        if (/*seller_type_id == 0*/ binding.spinnerSellerType.getText().toString().equals(getString(R.string.select_seller_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_seller_type));
            return false;
        }

        return true;

    }

    //////////////////////Machinery & Equipment//////////////////////////

    boolean validateIndustrialOfficeSports() {
        if (!genericFields())
            return false;

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }

        return true;

    }

    boolean validateMachinary() {
        if (!genericFields())
            return false;

        if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
            UIHelper.showToast(getActivity(), getString(R.string.yearError));
            return false;
        }
        if (productConditionId == "0" ||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        if (productAccesseroy_Id == 0) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        return true;

    }

    /////////////////////Classified/////////////////////////////////////

    boolean validateClassified(int i) {
        if (!genericFields())
            return false;

        switch (i) {

            case AppConstant.CategoriesIds.FURNITURE:
                if (genericClassified()) {

                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }


                    return true;
                }

                break;
            case AppConstant.CategoriesIds.CLOTHING:
                if (genericClassified()) {
                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }

                    if (binding.spType.getText().toString().equals(getString(R.string.select_type))) {
                        UIHelper.showToast(getActivity(), getString(R.string.select_type));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }


                    return true;
                }

                break;
            case AppConstant.CategoriesIds.BABY_ITEMS:
                if (genericClassified()) {
                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }
                    return true;
                }

                break;
            case AppConstant.CategoriesIds.PETS:
                if (genericClassified()) {
                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }
                    if (binding.tvAgeChanger.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.agerequired));
                        return false;
                    }
                    return true;
                }

                break;
            case AppConstant.CategoriesIds.BOOKS:
                if (genericClassified()) {
                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }
                    return true;
                }

                break;
            case AppConstant.CategoriesIds.GAMING:
                if (genericClassified())
                {
                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }
                    return true;
                }

                break;
            case AppConstant.CategoriesIds.OTHER:
                if (genericClassified()) {
                    if (binding.spProductYaer.getText().toString().equals(getString(R.string.how_old_is_the_product))) {
                        UIHelper.showToast(getActivity(), getString(R.string.yearError));
                        return false;
                    }
                    if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
                        UIHelper.showToast(getActivity(), getString(R.string.conditionError));
                        return false;
                    }

                    if (binding.etTitle.getText().toString().isEmpty() || binding.etTitle.getText().toString().trim().equals("")) {
                        UIHelper.showToast(getActivity(), getString(R.string.titlerequired));
                        return false;
                    }
                    return true;
                }
                break;


        }


//        if (binding.llType.getVisibility() == View.GONE) {
//
//            return false;
//        }
//        if (binding.llConditionType.getVisibility() == View.GONE) {
//
//            return false;
//        }
        return true;

    }

    public boolean genericClassified() {
        if (productConditionId == "0"||binding.spProductCondition.getText().toString().equals(getString(R.string.conditionError))) {
            UIHelper.showToast(getActivity(), getString(R.string.conditionError));
            return false;
        }
        if (binding.spType.getText().toString().equals(getString(R.string.select_type))) {
            UIHelper.showToast(getActivity(), getString(R.string.select_type));
            return false;
        }

        return true;

    }


    boolean validator(int subCategoryId) {

        boolean returnType = false;

        switch (subCategoryId) {
            case AppConstant.CategoriesIds.CAR:
                returnType = validateCar();
                break;
            case AppConstant.CategoriesIds.MOTORBIKE:
                returnType = validateMotorBike();
                break;
            case AppConstant.CategoriesIds.AC_WATER_LEAK:
                break;
            case AppConstant.CategoriesIds.HEAVY_VEHICLES:
                returnType = validateHeavyVehicle();
                break;
            case AppConstant.CategoriesIds.BOATS:
                returnType = validateBoats();
                break;
            case AppConstant.CategoriesIds.AUTO_PARTS:
                returnType = validateAutoParts();
                break;
            case AppConstant.CategoriesIds.PLATES:
                break;
            case AppConstant.CategoriesIds.HOME_APPLIANCES:
                returnType = validateHomeApplainces();
                break;
            case AppConstant.CategoriesIds.CAMERAS:
                returnType = validateCameras();
                break;
            case AppConstant.CategoriesIds.MOBILE_PHONES:
                returnType = validateMobilePhones();
                break;
            case AppConstant.CategoriesIds.TELEVISIONS_SCREENS:
                returnType = validateTelevisionScreens();
                break;
            case AppConstant.CategoriesIds.TABLETS_LAPTOPS:
                returnType = validateTabletsLaptopsComputers();
                break;
            case AppConstant.CategoriesIds.DIGITAL:
                returnType = validateDigital();
                break;
            case AppConstant.CategoriesIds.APARTMENT:
                returnType = validatePropertires();
                break;
            case AppConstant.CategoriesIds.VILLA:
                returnType = validatePropertires();
                break;
            case AppConstant.CategoriesIds.HOUSE:
                returnType = validatePropertires();
                break;
            case AppConstant.CategoriesIds.PLOT:
                returnType = validatePropertires();
                break;
            case AppConstant.CategoriesIds.OFFICE:
                returnType = validatePropertires();
                break;
            case AppConstant.CategoriesIds.WAREHOUSE:
                returnType = validatePropertires();
                break;
            case AppConstant.CategoriesIds.INDUSTRIAL_EQUIPMENT:
                returnType = validateIndustrialOfficeSports();
                break;
            case AppConstant.CategoriesIds.OFFICE_EQUIPMENT:
                returnType = validateIndustrialOfficeSports();
                break;
            case AppConstant.CategoriesIds.SPORTS_EQUIPMENT:
                returnType = validateIndustrialOfficeSports();
                break;
            case AppConstant.CategoriesIds.MACHINERY:
                returnType = validateMachinary();
                break;
            case AppConstant.CategoriesIds.FURNITURE:
                returnType = validateClassified(AppConstant.CategoriesIds.FURNITURE);
                break;
            case AppConstant.CategoriesIds.CLOTHING:
                returnType = validateClassified(AppConstant.CategoriesIds.CLOTHING);
                break;
            case AppConstant.CategoriesIds.BABY_ITEMS:
                returnType = validateClassified(AppConstant.CategoriesIds.BABY_ITEMS);
                break;
            case AppConstant.CategoriesIds.BOOKS:
                returnType = validateClassified(AppConstant.CategoriesIds.BOOKS);
                break;
            case AppConstant.CategoriesIds.GAMING:
                returnType = validateClassified(AppConstant.CategoriesIds.GAMING);
                break;
            case AppConstant.CategoriesIds.PETS:
                returnType = validateClassified(AppConstant.CategoriesIds.PETS);
                break;
            case AppConstant.CategoriesIds.OTHER:
                returnType = validateClassified(AppConstant.CategoriesIds.OTHER);
                break;
        }
        return returnType;

    }

    boolean genericFields() {

        if (country_id == null) {

            UIHelper.showToast(getActivity(), getString(R.string.select_country));
            return false;
        }
        if (city_id == null) {

            UIHelper.showToast(getActivity(), getString(R.string.select_city));
            return false;
        }
        if (etProductModelNumber.getText().toString().equals("")) {
            etProductModelNumber.setText("N/A");
            // UIHelper.showToast(getActivity(), getString(R.string.modelnumError));
            //  return false;
        } else {


        }
        return true;

    }
    public boolean getLatitudeAndLongitudeFromGoogleMapForAddress(String searchedAddress){

        Geocoder coder = new Geocoder(mainActivity);
        List<Address> address;
        try
        {
            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
                Log.d(TAG, "############Address not correct #########");
            }
            Address location = address.get(0);
            latitude= location.getLatitude();
            longitude= location.getLongitude();
            Log.d(TAG, "Address Latitude : "+ location.getLatitude()+ "Address Longitude : "+ location.getLongitude());
            return true;

        }
        catch(Exception e)
        {
            Log.d(TAG, "MY_ERROR : ############Address Not Found");
            return false;
        }
    }
}
