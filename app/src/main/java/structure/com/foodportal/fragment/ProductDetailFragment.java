package structure.com.foodportal.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MapActivity;
import structure.com.foodportal.adapter.ProductDescriptionAdapter;
import structure.com.foodportal.databinding.FragmentProductDetailBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.InputHelper;
import structure.com.foodportal.helper.ProductCreation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.ProductDescriptionModel;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.viewbinders.ProductDescriptionBinder;

public class ProductDetailFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {
    ArrayList<ProductDescriptionModel> productDescriptionModels;
    ArrayList<String> productImages;
    FragmentProductDetailBinding binding;
    ProductDescriptionAdapter productDescriptionAdapter;
    String latEiffelTower = "48.858235";
    String lngEiffelTower = "2.294571";
    String url = "http://maps.google.com/maps/api/staticmap?center=" + latEiffelTower + "," + lngEiffelTower + "&zoom=15&size=200x200&sensor=false";
    SliderLayout sliderLayout;
    CircleImageView imgPerson;
    @BindView(R.id.llPurchaseMode)
    LinearLayout llPurchaseMode;
    Unbinder unbinder;
    @BindView(R.id.productScroller)
    ScrollView productScroller;
    private ProductModelAPI productDetail;
    private boolean isEdit = false;
    private Titlebar titlebar;
    int rentTypeID = 0;
    ArrayList<Renttype> renttypes;

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private boolean locationClicked = false;

    public ProductDetailFragment() {
    }

    public void setProductDetail(ProductModelAPI productDetail, boolean isEdit) {
        this.isEdit = isEdit;
        this.productDetail = productDetail;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.resetView();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.detail));
        titlebar.showBackButton(mainActivity);
        titlebar.showTitlebar();

        if (isEdit) {

            titlebar.showDeleteButton(mainActivity).setOnClickListener(this);
            titlebar.showEditButton(mainActivity).setOnClickListener(this);
            titlebar.showShareButton(mainActivity).setOnClickListener(this);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
        renttypes = new ArrayList<>();
        init();
        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (productDetail != null) {
            if (productDetail.getLatitude() != null && productDetail.getLongitude() != null) {
                Double lat = Double.parseDouble(productDetail.getLatitude());
                Double lng = Double.parseDouble(productDetail.getLongitude());
                LatLng latLng = new LatLng(lat, lng);
//        googleMap.addMarker(new MarkerOptions().position(latLng).title(label));
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(productDetail.getLocation())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.setMinZoomPreference(13);
                mMap.getUiSettings().setAllGesturesEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if (!locationClicked) {
                            locationClicked = true;
                            binding.llMap.setAlpha((float) 0.50);
                            openMap(Double.parseDouble(productDetail.getLatitude()), Double.parseDouble(productDetail.getLongitude()), productDetail.getTitle(), productDetail.getLocation());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    locationClicked = false;
                                    binding.llMap.setAlpha((float) 1);
                                }
                            }, 5000);
                        }
                    }
                });
            }
        }
    }

    private void init() {

        binding.btnCall.setOnClickListener(this);
        binding.btnSms.setOnClickListener(this);
        binding.btnEmail.setOnClickListener(this);
        binding.btnrentNow.setOnClickListener(this);
        binding.btnAddtoCart.setOnClickListener(this);
        binding.btnTermsAndCondition.setOnClickListener(this);
        binding.txtPaymentCalculation.setOnClickListener(this);
        binding.mapImage.setOnClickListener(this);
        binding.txtBrandName.setOnClickListener(this);
        binding.llMap.setOnClickListener(this);
        mainActivity.hideBottombar();
        sliderLayout = (SliderLayout) binding.getRoot().findViewById(R.id.slider);
        imgPerson = binding.getRoot().findViewById(R.id.imgPerson);
        productImages = new ArrayList<>();
        for (ProductModelAPI.ProductImages image :
                productDetail.getProductImages()) {
            productImages.add(image.getProductImage());
        }
        setImageSlider(productImages);

        if (productDetail.getUser_name() != null) {
            binding.txtOwnerName.setText(productDetail.getUser_name());
        }     if (productDetail.getProduct_user() != null) {
            if (productDetail.getProduct_user().getProfileImage() != null) {
                UIHelper.setImagewithGlide(mainActivity, imgPerson, productDetail.getProduct_user().getProfileImage());
            }
            binding.mrOwnerRating.setRating(productDetail.getProduct_user().getRating());
        }


//        UIHelper.setImagewithGlide(mainActivity, binding.mapImage, "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&key=AIzaSyDIJ9XX2ZvRKCJcFRrl-lRanEtFUow4piM&signature=pgcz8_2FdcdBqftd6BrlxesXTjA=");
//        UIHelper.setImagewithGlide(mainActivity, binding.mapImage, "http://maps.google.com/maps/api/staticmap?center=" + productDetail.getLatitude() + "," + productDetail.getLongitude() + "&zoom=13&size=600x300&sensor=false&key=AIzaSyAjjC2KOZSz_SfFhPFOFW6h1kywNvE2eIU");

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        productDescriptionModels = new ArrayList<>();

//
//        if (productDetail.getModelNumber() != null && !productDetail.getModelNumber().equals("") && !productDetail.getModelNumber().equals("N/A")) {
//
//            productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
//        }
//        if (productDetail.getProductCondition() != null && !productDetail.getProductCondition().equals("") && !productDetail.getProductCondition().equals("N/A")) {
//
//            productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getProductCondition()));
//        }
//        if (productDetail.getBuiltYear() != null && !productDetail.getBuiltYear().equals("") && !productDetail.getBuiltYear().equals("N/A")) {
//
//            productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
//        }
//        if (productDetail.getAccessoryType() != null && !productDetail.getAccessoryType().equals("") && !productDetail.getAccessoryType().equals("N/A")) {
//
//            productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));
//        }

        settingpList(productDetail, productDescriptionModels);


//        productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
//        productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getProductCondition()));
//        productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
//        productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));
//        productDescriptionModels.add(new ProductDescriptionModel(5, "" + productDetail.getKilometers()));
//        productDescriptionModels.add(new ProductDescriptionModel(6, "" + productDetail.getMega_pixels()));
//        productDescriptionModels.add(new ProductDescriptionModel(7, "" + productDetail.getScreen_size()));
//        productDescriptionModels.add(new ProductDescriptionModel(8, "" + productDetail.getColor()));
//        productDescriptionModels.add(new ProductDescriptionModel(9, "" + productDetail.getSmart_features()));
//        productDescriptionModels.add(new ProductDescriptionModel(10, "" + productDetail.getFuel_type()));
//        productDescriptionModels.add(new ProductDescriptionModel(11, "" + productDetail.getType()));
//        productDescriptionModels.add(new ProductDescriptionModel(12, "" + productDetail.getBedrooms()));
//        productDescriptionModels.add(new ProductDescriptionModel(13, "" + productDetail.getBathrooms()));
//        productDescriptionModels.add(new ProductDescriptionModel(14, "" + productDetail.getWarrenty()));
//        productDescriptionModels.add(new ProductDescriptionModel(15, "" + productDetail.getBalconies()));
//        productDescriptionModels.add(new ProductDescriptionModel(16, "" + productDetail.getSeller_type()));

        double lastRowIndex = Math.ceil(productDescriptionModels.size()+1 / 2);


        GridLayoutManager manager = new GridLayoutManager(mainActivity, 2); // MAX NUMBER OF SPACES
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == lastRowIndex)
                    return 2;
                else
                    return 1;
            }
        });


        binding.recylerView.bindRecyclerView(new ProductDescriptionBinder(), productDescriptionModels, /*new GridLayoutManager(getContext(), 2)*/manager, new DefaultItemAnimator());
        binding.recylerView.setHasFixedSize(true);

        //  binding.recylerView.addItemDecoration(new GridSpacingItemDecoration(2,8,true));

        binding.recylerView.setNestedScrollingEnabled(false);
        // binding.re
        for (int i = 0; i < productDetail.getProductPaymentTypes().size(); i++) {
            if (productDetail.getProductPaymentTypes().get(i).getPaymentTypeId() == 1) {
//                binding.txtCashColection.setVisibility(View.VISIBLE);
                binding.txtCashColection.setText(mainActivity.getResources().getString(R.string.cash_on_collection));
                binding.txtCashColection.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tickcircle, 0, 0, 0);
            }
            if (productDetail.getProductPaymentTypes().get(i).getPaymentTypeId() == 3) {
//                binding.txtPayOnline.setVisibility(View.VISIBLE);
                binding.txtPayOnline.setText(mainActivity.getResources().getString(R.string.payonline));
                binding.txtPayOnline.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tickcircle, 0, 0, 0);
            }
            if (productDetail.getProductPaymentTypes().get(i).getPaymentTypeId() == 2) {
//                binding.txtPayOnReturn.setVisibility(View.VISIBLE);
                binding.txtPayOnReturn.setText(mainActivity.getResources().getString(R.string.yes_return));
                binding.txtPayOnReturn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tickcircle, 0, 0, 0);
            }
        }


        if (productDetail.getProductBrand() != null) {

            binding.txtBrandName.setText(productDetail.getProductBrand().getName());

        } else {

            switch (productDetail.getSub_category_id()) {

                case AppConstant.CategoriesIds.AUTO_PARTS:
                    binding.txtBrandName.setText(mainActivity.getResources().getString(R.string.autopart));

                    break;

                case AppConstant.CategoriesIds.APARTMENT:
                case AppConstant.CategoriesIds.OFFICE:
                case AppConstant.CategoriesIds.VILLA:
                case AppConstant.CategoriesIds.HOUSE:
                case AppConstant.CategoriesIds.PLOT:
                case AppConstant.CategoriesIds.WAREHOUSE:

                    binding.txtBrandName.setText(mainActivity.getResources().getString(R.string.property));


                    break;

                case AppConstant.CategoriesIds.FURNITURE:
                case AppConstant.CategoriesIds.CLOTHING:
                case AppConstant.CategoriesIds.BABY_ITEMS:
                case AppConstant.CategoriesIds.BOOKS:
                case AppConstant.CategoriesIds.GAMING:
                case AppConstant.CategoriesIds.PETS:
                case AppConstant.CategoriesIds.OTHER:

                    binding.txtBrandName.setText(mainActivity.getResources().getString(R.string.classified));

                    break;


            }


        }


        binding.txtFeature.setText(productDetail.getTitle());
        binding.mrRating.setRating(productDetail.getRating());
        if (productDetail.getReviews() != null) {
            binding.txtReviewCount.setText("( " + productDetail.getReviews().size() + " )");
        } else {
            binding.txtReviewCount.setText("( 0 )");
        }
        binding.txtProductPrice.setText("AED " + productDetail.getAmount());
//        binding.txtPaymentType.setText(productDetail.getRentType());
        binding.txtPostedTime.setText(UIHelper.getFormattedDate(productDetail.getUpdatedAt(), AppConstant.SERVER_DATE_FORMAT, "d MMM yyyy"));
//        binding.txtPaymentCalculation.setText("AED " + (Double.parseDouble(productDetail.getAmount()) * 7) + " Per Week");

        if (productDetail.getProductRentType() != null) {
            if (productDetail.getProductRentType().size() > 0) {
                for (int pos = 0; pos < productDetail.getProductRentType().size(); pos++) {
                    if (productDetail.getProductRentType().get(pos).getRenttype() != null) {
                        renttypes.add(productDetail.getProductRentType().get(pos).getRenttype());
                    }
                }
            }
        }

        if (productDetail.getProductOn() == AppConstant.ProductOn.SALE) {
            binding.txtPaymentType.setVisibility(View.GONE);
            binding.txtPaymentCalculation.setVisibility(View.GONE);
            binding.btnrentNow.setVisibility(View.GONE);
        } else {
            binding.btnAddtoCart.setVisibility(View.GONE);
        }

        binding.txtProductDescription.setText(productDetail.getDescription());
        binding.txtProductFeature.setText(productDetail.getProduct_user().getAbout());


        //    mainActivity.getTab().setVisibility(View.VISIBLE);
        if (isEdit) {
            llPurchaseMode.setVisibility(View.GONE);
        } else {
            if (productDetail.getIs_view() == 0) {
                if (preferenceHelper.getLoginStatus()) {
                    if (preferenceHelper.getUser() != null) {
                        serviceHelper.enqueueCall(webService.MarkViewed(productDetail.getId() + "", preferenceHelper.getUser().getId() + "", ""), AppConstant.MARKVIEWED);
                    }
                } else {
                    serviceHelper.enqueueCall(webService.MarkViewed(productDetail.getId() + "", "", preferenceHelper.getDeviceToken()), AppConstant.MARKVIEWED);
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEmail:
                if (productDetail.getProduct_user() != null) {
                    if (productDetail.getProduct_user().getEmail() != null) {
                        UIHelper.showSimpleDialog(
                                mainActivity,
                                0,
                                mainActivity.getResources().getString(R.string.send_email),
                                mainActivity.getResources().getString(R.string.email_surety) + " to " + productDetail.getUser_name() + " ?",
                                mainActivity.getResources().getString(R.string.send_email),
                                mainActivity.getResources().getString(R.string.cancel_en),
                                false,
                                false,
                                (dialog, which, positive, logout) -> {
                                    if (positive) {
                                        dialog.dismiss();
                                        UIHelper.openEmailComposer(mainActivity, productDetail.getProduct_user().getEmail().trim());
                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                        );
                    } else {
                        UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.seller_email_unavailable));
                    }
                } else {
                    UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.seller_details_unavailable));
                }
                break;

            case R.id.btnSms:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {

                        UIHelper.showSimpleDialog(
                                mainActivity,
                                0,
                                mainActivity.getResources().getString(R.string.send_sms),
                                mainActivity.getResources().getString(R.string.sms_surety) + " to " + productDetail.getUser_name() + " ?",
                                mainActivity.getResources().getString(R.string.send_sms),
                                mainActivity.getResources().getString(R.string.cancel_en),
                                false,
                                false,
                                (dialog, which, positive, logout) -> {
                                    if (positive) {
                                        dialog.dismiss();
                                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                        sendIntent.setData(Uri.parse("smsto:" + productDetail.getProduct_user().getCountryCode() + productDetail.getProduct_user().getPhoneNo()));
                                        sendIntent.putExtra("sms_body", "");
                                        startActivity(sendIntent);
                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                        );

                    } else {
                        requestPermission();
                    }
                }
                break;

            case R.id.btnCall:
                UIHelper.showSimpleDialog(
                        mainActivity,
                        0,
                        mainActivity.getResources().getString(R.string.call_phone),
                        mainActivity.getResources().getString(R.string.call_phone_surety) + " to " + productDetail.getUser_name() + " ?",
                        mainActivity.getResources().getString(R.string.call),
                        mainActivity.getResources().getString(R.string.cancel_en),
                        false,
                        false,
                        (dialog, which, positive, logout) -> {
                            if (positive) {
                                dialog.dismiss();
                                InputHelper.call(productDetail.getProduct_user().getCountryCode() + productDetail.getProduct_user().getPhoneNo(), mainActivity);
                            } else {
                                dialog.dismiss();
                            }
                        }
                );
                break;

            case R.id.btnrentNow:
                if (mainActivity.prefHelper.getLoginStatus()) {


                    DialogFactory.createMessageDialog2(mainActivity, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            rentNow();

                        }
                    }, null, getString(R.string.rent_now_message), getString(R.string.alert)).show();


                }
                //  mainActivity.replaceFragment(new CartFragment(), true, true);}
                else {

                    UIHelper.showToast(mainActivity, mainActivity.getString(R.string.please_login_rentitem));

                }

                break;

            case R.id.btnAddtoCart:
                if (!preferenceHelper.getLoginStatus()) {
                    UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.please_login_notification));
                    return;
                }


                DialogFactory.createMessageDialog2(mainActivity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        serviceHelper.enqueueCall(webService.addProductToCart("" + productDetail.getId(), "" + preferenceHelper.getUser().getId(), preferenceHelper.getDeviceToken(), null), AppConstant.ADD_TO_CART);


                    }
                }, null, getString(R.string.add_to_cart_message), getString(R.string.alert)).show();


                //
                // mainActivity.replaceFragment(new ConfirmationFragment(),true,true);

                //  UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.implementdialog));
                break;

            case R.id.ivShare:
                share();
                break;

            case R.id.ivDelete:
                DialogFactory.createQuitDialog(mainActivity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        serviceHelper.enqueueCall(
                                webService.deleteProduct(productDetail.getId(), preferenceHelper.getUser().getId()),
                                AppConstant.DELETE_PRODUCT);
                    }
                }, R.string.product_delete_message).show();
                break;

            case R.id.ivEdit:
                editProduct();
                break;

            case R.id.txtPaymentCalculation:
//                UIHelper.showToast(mainActivity, mainActivity.getString(R.string.implementdialog));
                selectRentType();
                break;

            case R.id.btnTermsAndCondition:
                TermsAndConditon termsAndConditon = new TermsAndConditon();
                termsAndConditon.setkey("Terms and Condition");
                if (productDetail.getGuidelines() != null) {
                    termsAndConditon.setData(productDetail.getGuidelines());
                }
                mainActivity.replaceFragment(termsAndConditon, true, true);

                //   UIHelper.showToast(mainActivity, mainActivity.getString(R.string.implementdialog));
                break;

//            case R.id.mapImage:
            case R.id.llMap:
                if (!locationClicked) {
                    locationClicked = true;
                    binding.llMap.setAlpha((float) 0.75);
                    openMap(Double.parseDouble(productDetail.getLatitude()), Double.parseDouble(productDetail.getLongitude()), productDetail.getTitle(), productDetail.getLocation());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            locationClicked = false;
                            binding.llMap.setAlpha((float) 1);
                        }
                    }, 5000);
                }
                break;
            case R.id.txtBrandName:
//                mainActivity.replaceFragment(new OrderRatingFragment(productDetail), true, true);
                break;
        }
    }

    private void selectRentType() {
        if (!preferenceHelper.getLoginStatus()) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.please_login_notification));
            return;
        }

//        if (isEdit) {
//            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.cannot_add_own_product_to_cart));
//            return;
//        }

        ArrayList<String> types = new ArrayList<>();
        double price = 0;
        if (productDetail.getAmount() != null) {
            price = Double.parseDouble(productDetail.getAmount());
        }
        if (renttypes.size() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.no_rent_type_found));
        }

        if (renttypes.size() > 0) {

            for (int pos = 0; pos < renttypes.size(); pos++) {
                types.add(renttypes.get(pos).getName());
            }

            double finalPrice = price;
            DialogFactory.listDialog(mainActivity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rentTypeID = renttypes.get(which).getId();
                    if (renttypes.get(which).getName().equals(mainActivity.getResources().getString(R.string.per_hour))) {
                        binding.txtPaymentCalculation.setText((finalPrice / 24) + " " + mainActivity.getResources().getString(R.string.per_hour));
                    } else if (renttypes.get(which).getName().equals(mainActivity.getResources().getString(R.string.per_day))) {
                        binding.txtPaymentCalculation.setText(finalPrice + " " + mainActivity.getResources().getString(R.string.per_day));
                    } else if (renttypes.get(which).getName().equals(mainActivity.getResources().getString(R.string.per_week))) {
                        binding.txtPaymentCalculation.setText(String.format("%.2f", (finalPrice * 7)) + " " + mainActivity.getResources().getString(R.string.per_week));
                    } else if (renttypes.get(which).getName().equals(mainActivity.getResources().getString(R.string.per_month))) {
                        binding.txtPaymentCalculation.setText(String.format("%.2f", (finalPrice * 30)) + " " + mainActivity.getResources().getString(R.string.per_month));
                    }

                }
            }, mainActivity.getResources().getString(R.string.select_rent_type), types);
        }
    }

    private void rentNow() {
        if (!preferenceHelper.getLoginStatus()) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.please_login_notification));
            return;
        }

        if (rentTypeID == 0) {
            productScroller.fullScroll(ScrollView.FOCUS_UP);
            binding.txtPaymentCalculation.requestFocus();
            UIHelper.animation(Techniques.Shake, 1000, 0, binding.txtPaymentCalculation);
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.select_rent_type));
            return;
        }
        serviceHelper.enqueueCall(webService.addProductToCart("" + productDetail.getId(), "" + preferenceHelper.getUser().getId(), preferenceHelper.getDeviceToken(), "" + rentTypeID), AppConstant.ADD_TO_CART);


    }

    private void share() {
        if (productDetail != null) {
            if (productDetail.getProductImages().size() > 0) {
                UIHelper.share(mainActivity, productDetail.getTitle(), productDetail.getProductImages().get(0).getProductImage());
            }
        }
    }

    private void setImageSlider(ArrayList<String> user_image) {
        DefaultSliderView defaultSliderView;

        if (user_image.size() > 0) {
            for (int i = 0; i < user_image.size(); i++) {
                defaultSliderView = new DefaultSliderView(mainActivity);
                defaultSliderView
                        .image(user_image.get(i)).setScaleType(BaseSliderView.ScaleType.CenterCrop);
                sliderLayout.addSlider(defaultSliderView);
            }
        } else {

            defaultSliderView = new DefaultSliderView(mainActivity);
            defaultSliderView
                    .image(R.drawable.image_placeholder);
            sliderLayout.addSlider(defaultSliderView);
        }


//        binding.slider.addSlider(defaultSliderView);
    }

    @Override
    public void onDestroyView() {
        titlebar.resetView();
        titlebar.showTitlebar();
        titlebar.setTitle(getString(R.string.homeTitle));
        titlebar.showCart(0).setOnClickListener(v -> UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.implementdialog)));
        titlebar.showMenuButton(mainActivity);
        mainActivity.showBottombar();
        super.onDestroyView();

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.DELETE_PRODUCT:
                mainActivity.onBackPressed();
                break;
            case AppConstant.ADD_TO_CART:


              //  mainActivity.replaceFragment(new CartFragment(), true, true);


                break;
        }
    }

    private void editProduct() {
        ProductCreation.getInstance().reset();
        ProductCreation.getInstance().setEditing(true);
        ProductCreation.getInstance().setCurrentProduct(productDetail);
      //  replaceFragment(new AddProductFragment(), true, true);
    }

    public void openMap(double latitude, double longitude, String title, String label) {
        Intent mapActivity = new Intent(mainActivity, MapActivity.class);
        mapActivity.putExtra("latitude", latitude);
        mapActivity.putExtra("longitude", longitude);
        mapActivity.putExtra("title", title);
        mapActivity.putExtra("label", label);
        mainActivity.startActivity(mapActivity);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(mainActivity,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(mainActivity,
                            "Permission denied", Toast.LENGTH_LONG).show();
                    // Button sendSMS = (Button) findViewById(R.id.sendSMS);
                    //  sendSMS.setEnabled(false);

                }
                break;
        }
    }

    public class Renttype {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        private boolean selected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    private void settingpList(ProductModelAPI productDetail, ArrayList<ProductDescriptionModel> productDescriptionModels) {




            switch (productDetail.getSub_category_id()) {

                case AppConstant.CategoriesIds.CAR:
                case AppConstant.CategoriesIds.HEAVY_VEHICLES:

                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getProductBrand().getName()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    productDescriptionModels.add(new ProductDescriptionModel(5, "" + productDetail.getKilometers()));
                    productDescriptionModels.add(new ProductDescriptionModel(8, "" + productDetail.getColor()));
                    productDescriptionModels.add(new ProductDescriptionModel(10, "" + productDetail.getFuel_type()));
                    productDescriptionModels.add(new ProductDescriptionModel(14, "" + productDetail.getWarrenty()));
                    productDescriptionModels.add(new ProductDescriptionModel(17, "" + productDetail.getDoors()));


                    break;
                case AppConstant.CategoriesIds.MOTORBIKE:
                case AppConstant.CategoriesIds.BOATS:
                    productDescriptionModels.add(new ProductDescriptionModel(1,productDetail.getProductBrand().getName()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    productDescriptionModels.add(new ProductDescriptionModel(5, "" + productDetail.getKilometers()));
                    productDescriptionModels.add(new ProductDescriptionModel(8, "" + productDetail.getColor()));
                    productDescriptionModels.add(new ProductDescriptionModel(10, "" + productDetail.getFuel_type()));
                    productDescriptionModels.add(new ProductDescriptionModel(14, "" + productDetail.getWarrenty()));
                    break;
                case AppConstant.CategoriesIds.AUTO_PARTS:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));


                    break;
                case AppConstant.CategoriesIds.PLATES:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
//                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getProductCondition()));
//                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
//                    productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));
//                    productDescriptionModels.add(new ProductDescriptionModel(5, "" + productDetail.getKilometers()));
//                    productDescriptionModels.add(new ProductDescriptionModel(6, "" + productDetail.getMega_pixels()));
//                    productDescriptionModels.add(new ProductDescriptionModel(7, "" + productDetail.getScreen_size()));
//                    productDescriptionModels.add(new ProductDescriptionModel(8, "" + productDetail.getColor()));
//                    productDescriptionModels.add(new ProductDescriptionModel(9, "" + productDetail.getSmart_features()));
//                    productDescriptionModels.add(new ProductDescriptionModel(10, "" + productDetail.getFuel_type()));
//                    productDescriptionModels.add(new ProductDescriptionModel(11, "" + productDetail.getType()));
//                    productDescriptionModels.add(new ProductDescriptionModel(12, "" + productDetail.getBedrooms()));
//                    productDescriptionModels.add(new ProductDescriptionModel(13, "" + productDetail.getBathrooms()));
//                    productDescriptionModels.add(new ProductDescriptionModel(14, "" + productDetail.getWarrenty()));
//                    productDescriptionModels.add(new ProductDescriptionModel(15, "" + productDetail.getBalconies()));
//                    productDescriptionModels.add(new ProductDescriptionModel(16, "" + productDetail.getSeller_type()));

                    break;

                case AppConstant.CategoriesIds.HOME_APPLIANCES:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    if(productDetail.getAccessoryType()!=null){

                        productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));

                    }
                    productDescriptionModels.add(new ProductDescriptionModel(11, "" + productDetail.getType_id()));

                    break;
                case AppConstant.CategoriesIds.CAMERAS:

                    productDescriptionModels.add(new ProductDescriptionModel(1,productDetail.getMake_model()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                //    productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));
                    productDescriptionModels.add(new ProductDescriptionModel(6, "" + productDetail.getMega_pixels()));
                    productDescriptionModels.add(new ProductDescriptionModel(11, "" + productDetail.getType_id()));




                    break;
                case AppConstant.CategoriesIds.MOBILE_PHONES:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getMake_model()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                  //  productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));
                  //  productDescriptionModels.add(new ProductDescriptionModel(6, "" + productDetail.getMega_pixels()));
                    productDescriptionModels.add(new ProductDescriptionModel(7, "" + productDetail.getScreen_size()));
                    productDescriptionModels.add(new ProductDescriptionModel(18, "" + productDetail.getExpandable_memory()));
                    productDescriptionModels.add(new ProductDescriptionModel(19, "" + productDetail.getOperating_system()));
                    productDescriptionModels.add(new ProductDescriptionModel(20, "" + productDetail.getProcessor()));
                    productDescriptionModels.add(new ProductDescriptionModel(21, "" + productDetail.getProduct_cellular_type()));

                    break;
                case AppConstant.CategoriesIds.TELEVISIONS_SCREENS:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    productDescriptionModels.add(new ProductDescriptionModel(7, "" + productDetail.getScreen_size()));
                    productDescriptionModels.add(new ProductDescriptionModel(9, "" + productDetail.getSmart_features()));


                    break;
                case AppConstant.CategoriesIds.TABLETS_LAPTOPS:
                case AppConstant.CategoriesIds.DIGITAL:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getMake_model()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    productDescriptionModels.add(new ProductDescriptionModel(7, "" + productDetail.getScreen_size()));
                    productDescriptionModels.add(new ProductDescriptionModel(19, "" + productDetail.getOperating_system()));


                    break;


                case AppConstant.CategoriesIds.APARTMENT:
                case AppConstant.CategoriesIds.VILLA:
                case AppConstant.CategoriesIds.HOUSE:
                case AppConstant.CategoriesIds.PLOT:
                case AppConstant.CategoriesIds.OFFICE:
                case AppConstant.CategoriesIds.WAREHOUSE:

                    if(productDetail.getModelNumber()!= null && !productDetail.getModelNumber().equals("null")){

                        productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
                    }
                    productDescriptionModels.add(new ProductDescriptionModel(12, "" + productDetail.getBedrooms()));
                    productDescriptionModels.add(new ProductDescriptionModel(13, "" + productDetail.getBathrooms()));
                    productDescriptionModels.add(new ProductDescriptionModel(15, "" + productDetail.getBalconies()));
                    productDescriptionModels.add(new ProductDescriptionModel(16, "" + productDetail.getSeller_type()));
                    productDescriptionModels.add(new ProductDescriptionModel(22, "" + (productDetail.getPets_allowed()==1 ? "Yes": "No")));
                    productDescriptionModels.add(new ProductDescriptionModel(23, "" + productDetail.getTotal_cheques()));
                    productDescriptionModels.add(new ProductDescriptionModel(24, "" + productDetail.getTotal_area()));

                    break;

                case AppConstant.CategoriesIds.INDUSTRIAL_EQUIPMENT:
                case AppConstant.CategoriesIds.OFFICE_EQUIPMENT:
                case AppConstant.CategoriesIds.SPORTS_EQUIPMENT:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getMake_model()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));

                    break;


                case AppConstant.CategoriesIds.MACHINERY:
                    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getMake_model()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));

                    break;

                case AppConstant.CategoriesIds.FURNITURE:
                case AppConstant.CategoriesIds.CLOTHING:
                case AppConstant.CategoriesIds.BABY_ITEMS:
                case AppConstant.CategoriesIds.BOOKS:
                case AppConstant.CategoriesIds.GAMING:
                case AppConstant.CategoriesIds.PETS:
                case AppConstant.CategoriesIds.OTHER:
                //    productDescriptionModels.add(new ProductDescriptionModel(1, productDetail.getModelNumber()));
                    productDescriptionModels.add(new ProductDescriptionModel(2, productDetail.getCondition()));
                    productDescriptionModels.add(new ProductDescriptionModel(3, productDetail.getBuiltYear()));
                    productDescriptionModels.add(new ProductDescriptionModel(11, productDetail.getType_id()));
                    productDescriptionModels.add(new ProductDescriptionModel(26, productDetail.getTitle()));
                   // productDescriptionModels.add(new ProductDescriptionModel(4, productDetail.getAccessoryType()));






                    break;

            }

        }






}
