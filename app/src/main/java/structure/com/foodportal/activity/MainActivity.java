package structure.com.foodportal.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import id.zelory.compressor.Compressor;
import structure.com.foodportal.R;
import structure.com.foodportal.customViews.MultiLeftSideMenu;

import structure.com.foodportal.fragment.OrdersHistoryFragment;
import structure.com.foodportal.fragment.SideMenuFragment;
import structure.com.foodportal.fragment.UserAccountFragment;
import structure.com.foodportal.fragment.foodportal.FoodHomeFragment;
import structure.com.foodportal.global.SideMenuChooser;
import structure.com.foodportal.global.SideMenuDirection;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.GooglePlaceHelper;
import structure.com.foodportal.helper.ServiceHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.DataLoadedListener;
import structure.com.foodportal.interfaces.MediaTypePicker;
import structure.com.foodportal.interfaces.OnActivityResultInterface;
import structure.com.foodportal.interfaces.webServiceResponseLisener;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.FCMPayload;
import structure.com.foodportal.models.foodModels.HeaderWrapper;
import structure.com.foodportal.webservice.WebServiceFactory;
import structure.com.foodportal.webservice.webservice;


public class MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, DataLoadedListener, webServiceResponseLisener {
    public RadioButton rbHome, rbSearch, rbNotification, rbAccount;
    public boolean isNavigationGravityRight = false;
    MultiLeftSideMenu sideMneuFragmentContainer;
    FrameLayout framelayout;
    //  ImageView imgLoader;
    FrameLayout imgLoader;
    Titlebar titlebar;
    public FrameLayout mainFrame;

    RadioGroup rgTabStart, rgTabEnd;
    DrawerLayout drawerLayout;
    private boolean isLoading;
    private String sideMenuType;
    private String sideMenuDirection;
    private View contentView;
    public SideMenuFragment sideMenuFragment;
    ImageView btnAddProduct;
    ConstraintLayout bottombar;
    //public RadioButton rbHome, rbCart, rbOrder, rbAccount;
    // private boolean isLoading;
    OnActivityResultInterface onActivityResultInterface;
    ArrayList<String> photoPaths;
    MediaTypePicker mediaPickerListener;
    private Category categories;
    ViewPager stepPager;
    int minWindowWidth, minWindowHeight, aspectRatioX, aspectRatioY;
    protected ServiceHelper serviceHelper;
    protected webservice webService;
    Bundle bundle;
    protected PowerManager.WakeLock mWakeLock;

    public void keepScreenAwake() {
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

    }

    String action_type;
    int ref_id;
    String slug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keepScreenAwake();
        webService = WebServiceFactory.getInstance(AppConstant.BASE_URL, prefHelper);
        serviceHelper = new ServiceHelper(this, this);
        if (getIntent().getExtras() != null) {

            bundle = new Bundle();
            bundle = getIntent().getExtras();
          FCMPayload fcmPayload= (FCMPayload) bundle.getSerializable(AppConstant.FcmHelper.FCM_DATA_PAYLOAD);
          if (bundle != null) {
                if (fcmPayload != null) {

                    action_type = fcmPayload.getAction_type();
                    ref_id = fcmPayload.getRef_id();
                    slug = fcmPayload.getSlug();
                } else {


                    if(bundle.get("action_type")!=null){
                        action_type = bundle.get("action_type").toString();
                        ref_id = Integer.valueOf(bundle.get("ref_id").toString());
                        slug = bundle.get("slug").toString();
                    }



                }



            }



        }

        init();

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();


        serviceHelper.enqueueArrayCall(webService.getHeader(), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HEADER);

        titlebar.setMenuOnclickListener(view -> {
            if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                drawerLayout.openDrawer(Gravity.LEFT);
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    public MultiLeftSideMenu getLeftSideMenuFragment() {
        return sideMneuFragmentContainer;
    }


    public void showLoader() {
        //   imgLoader.show();
        imgLoader.setVisibility(View.VISIBLE);
        //   isLoading = true;
    }

    public void hideLoader() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgLoader.setVisibility(View.GONE);
            }
        }, 1500);

        // isLoading = false;
    }

    private void init() {
        //   imgLoader = findViewById(R.id.imgLoader);
        imgLoader = findViewById(R.id.avi);
        //showAnimation(imgLoader);
        mainFrame = findViewById(R.id.mainFrame);
        bottombar = findViewById(R.id.bottomBar);
        btnAddProduct = findViewById(R.id.btnAddproduct);

        setFrame(R.id.mainFrame);
        titlebar = findViewById(R.id.title);

        rbHome = findViewById(R.id.rbHome);
        rbSearch = findViewById(R.id.rbSearch);
        rbNotification = findViewById(R.id.rbNotification);
        rbAccount = findViewById(R.id.rbprofile);

        rbHome.setOnCheckedChangeListener(this);
        rbSearch.setOnCheckedChangeListener(this);
        rbNotification.setOnCheckedChangeListener(this);
        rbAccount.setOnCheckedChangeListener(this);


        rgTabStart = findViewById(R.id.rgTabsStart);
        rgTabEnd = findViewById(R.id.rgTabsEnd);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        framelayout = (FrameLayout) findViewById(R.id.sideMneuFragmentContainer);
        // sideMneuFragmentContainer = findViewById(R.id.sideMneuFragmentContainer);
        contentView = findViewById(R.id.contentView);
//        btnAddProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (prefHelper != null && prefHelper.getUser() != null) {
//                    ProductCreation.getInstance().reset();
//                    replaceFragment(new AddProductFragment(), true, false);
//                } else {
//
//                    UIHelper.showToast(MainActivity.this, getString(R.string.please_login_addprodcuts));
//                }
//            }
//        });


    }

    public void hideBottombar() {

        bottombar.setVisibility(View.GONE);
    }

    public void showBottombar() {

        bottombar.setVisibility(View.VISIBLE);
    }

    public RadioGroup getTab() {
        if (rbHome.isChecked() || rbSearch.isChecked())
            return rgTabStart;
        else return rgTabEnd;
    }

    @Override
    public void onDataLoaded() {
        if (bundle != null) {
            FCMPayload fcmPayload = (FCMPayload) bundle.getSerializable(AppConstant.FcmHelper.FCM_DATA_PAYLOAD);
            if (fcmPayload != null) {
                if (fcmPayload.getAction_type().equals(AppConstant.FcmHelper.ACTION_TYPE_JOB) || fcmPayload.getAction_type().equals(AppConstant.FcmHelper.COMPLETED)) {
                 //   CartFragment cartFragment = new CartFragment();
                  //  cartFragment.setFromNotification(true);
                 //   replaceFragment(cartFragment, true, true);
                    bundle = null;
                } else if (fcmPayload.getAction_type().equals(AppConstant.FcmHelper.ACCEPTED) || fcmPayload.getAction_type().equals(AppConstant.FcmHelper.CANCELLED)) {
                    OrdersHistoryFragment ordersHistoryFragment = new OrdersHistoryFragment();
                    ordersHistoryFragment.setFromNotification(true);
                    ordersHistoryFragment.setActionId(fcmPayload.getAction_id());
                    replaceFragment(ordersHistoryFragment, true, false);
                    bundle = null;
                }
            }
        }
    }

    private void settingSideMenu(String type, String direction, ArrayList<HeaderWrapper> headerWrapper) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {

            DisplayMetrics matrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(matrics);
            Long longwidth = Math.round(matrics.widthPixels * 0.75);
            int drawerwidth = longwidth.intValue();
            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(drawerwidth, (int) DrawerLayout.LayoutParams.MATCH_PARENT);


//            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
////                params.gravity = Gravity.LEFT;
////                drawerLayout.setLayoutParams(params);
////            } else {
////                params.gravity = Gravity.RIGHT;
////                drawerLayout.setLayoutParams(params);
////            }
            // sideMneuFragmentContainer = MultiLeftSideMenu.newInstance();

//            HomeFragment homeFragment = new HomeFragment();
//            homeFragment.setDataLoadedListener(this);
//            replaceFragment(homeFragment, true, false);
/*
            if (prefHelper != null && prefHelper.getLoginStatus()) {
//                sideMenuFragment.setListner(prefHelper);
                replaceFragment(new HomeFragment(), true, false);

            } else {

                replaceFragment(new HomeFragment(), true, false);
            }
*/
//            sideMneuFragmentContainer = new MultiLeftSideMenu(headerWrapper);
//            sideMneuFragmentContainer.MultiLeftSideMenu(headerWrapper);



            if (action_type != null){

                switch (action_type) {

                    case "recipe":

                        clearBackStack();
                        closeDrawer();
                        FoodHomeFragment foodHomeFragment =new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES);
                        foodHomeFragment.setTypeandSlug(action_type,slug);
                        addFragment(foodHomeFragment, true, false);

                        break;
                    case "cleaning":
                        clearBackStack();
                        closeDrawer();
                        foodHomeFragment =new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.CLEANING);
                        foodHomeFragment.setTypeandSlug(action_type,slug);
                        addFragment(foodHomeFragment, true, false);

                        //   addFragment(new FoodHomeFragment(action_type,slug), true, true);
                        break;
                    case "tutorial":
                        clearBackStack();
                        closeDrawer();
                        foodHomeFragment =new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.TUTORIALS);
                        foodHomeFragment.setTypeandSlug(action_type,slug);
                        addFragment(foodHomeFragment, true, false);

                        ///  addFragment(new FoodHomeFragment(action_type,slug), true, true);
                        break;
                    case "blog":
                        clearBackStack();
                        closeDrawer();
                        foodHomeFragment =new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG);
                        foodHomeFragment.setTypeandSlug(action_type,slug);
                        addFragment(foodHomeFragment, true, false);
                        break;
                    case "special_recipe":
                        clearBackStack();
                        closeDrawer();
                        foodHomeFragment =new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES);
                        foodHomeFragment.setTypeandSlug(action_type,slug);
                        addFragment(foodHomeFragment, true, false);
                        break;



                }


            }else{
                closeDrawer();
                addFragment(new FoodHomeFragment(AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES), true, true);

            }


            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(framelayout.getId(), new MultiLeftSideMenu(headerWrapper,action_type)).commit();
            setDrawerListeners();
            // drawerLayout.closeDrawers();










        }
    }

    public void lockDrawer() {
        try {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDrawerListeners() {
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (!isNavigationGravityRight) {
                    contentView.setTranslationX(slideOffset * drawerView.getWidth());
                    drawerLayout.bringChildToFront(drawerView);
                    drawerLayout.requestLayout();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isNavigationGravityRight = false;
                drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (!isNavigationGravityRight) {
                    if (newState == DrawerLayout.STATE_SETTLING) {
                        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            /*Blurry.with(MainActivity.this)
                                    .radius(15)
                                    .sampling(3)
                                    .async()
                                    .animate(500)
                                    .onto(contentView);*/
                            // setBlurBackground();
                        } else {
                            // Blurry.delete(contentView);
                            // removeBlurImage();
                        }
                    }
                }
            }
        });
    }

    public Titlebar getTitleBar() {
        return titlebar;
    }

    @Override
    protected void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        UIHelper.hideSoftKeyboards(this);

        if (getSupportFragmentManager().getBackStackEntryCount() > 1 && !isLoading) {
            getSupportFragmentManager().popBackStack();
        } else {
            UIHelper.openExitPopUp(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.rbHome:
                rgTabEnd.clearCheck();
                if (rbHome.isChecked())
                  //  replaceFragmentWithClearBackStack(new HomeFragment(), true, false);
                break;
            case R.id.rbSearch:
                rgTabEnd.clearCheck();
                if (rbSearch.isChecked()) {
//                    willbeimplementedinfuture();
                   // replaceFragmentWithClearBackStack(new FragmentCategory(true), true, false);
                }
                break;
            case R.id.rbNotification:
                rgTabStart.clearCheck();
                if (rbNotification.isChecked()) {

                    if (prefHelper.getLoginStatus()) {
                        //replaceFragmentWithClearBackStack(new NotificationsFragment(), true, false);
                    } else {

                        UIHelper.showToast(this, getString(R.string.please_login_viewnotifications));

                    }

                }
                break;
            case R.id.rbprofile:
                rgTabStart.clearCheck();
                if (rbAccount.isChecked()) {
                    if (prefHelper.getLoginStatus()) {
                        replaceFragmentWithClearBackStack(new UserAccountFragment(), true, false);
                    } else {

                        UIHelper.showToast(MainActivity.this, getString(R.string.please_login_notification));
                    }
                }


                break;
        }

    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case GooglePlaceHelper.REQUEST_CODE_AUTOCOMPLETE:
                try {
                    onActivityResultInterface.onActivityResultInterface(requestCode, resultCode, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Activity.RESULT_OK:
                //  getLocation();

                break;
            case Activity.RESULT_CANCELED:
                // The user was asked to change settings, but chose not to
                Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                break;
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
//                    data.getExtras().getStringArrayList(FilePickerConst.KEY_SELECTED_MEDIA).get(0);
                    File file = new File(data.getExtras().getStringArrayList(FilePickerConst.KEY_SELECTED_MEDIA).get(0));
                    doCrop(Uri.fromFile(file), minWindowWidth, minWindowHeight, aspectRatioX, aspectRatioY);
//                    photoPaths = new ArrayList<>();
//                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
//                    new AsyncTaskRunner().execute(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {


                    mediaPickerListener.onDocClicked(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));

                }
                break;
            case AppConstant.CAMERA_PIC_REQUEST:
                if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {

                    ArrayList<String> cameraPic = new ArrayList<>();
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(this, (Bitmap) data.getExtras().get("data"));
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    cameraPic.add(getRealPathFromURI(tempUri));


                    new AsyncTaskRunner().execute(cameraPic);

                }
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                cropImageResult(resultCode, data);
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void setOnActivityResultInterface(OnActivityResultInterface activityResultInterface) {
        this.onActivityResultInterface = activityResultInterface;
    }

    public void openFilePicker(final MediaTypePicker listener, final int count,
                               final Boolean videoenabling) {

        TedPermission.with(this)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        mediaPickerListener = listener;
                        FilePickerBuilder.getInstance()
                                .setMaxCount(count)
                                //.setSelectedFiles(photoPaths)
                                .setActivityTheme(R.style.FilePickeTheme)
                                .enableVideoPicker(videoenabling)
                                .enableCameraSupport(true)
                                .showGifs(true)
                                .enableSelectAll(false)
                                .enableImagePicker(true)
                                .showFolderView(false)
                                .withOrientation(Orientation.UNSPECIFIED)
                                .pickPhoto(MainActivity.this);

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        //Utils.showToast(this, getString(R.string.permission_denied));
                    }
                }).check();
    }

    public void openFilePicker(final MediaTypePicker listener, final int count,
                               final Boolean videoenabling, int minWindowWidth, int minWindowHeight, int aspectRatioX,
                               int aspectRatioY) {
        this.minWindowWidth = minWindowWidth;
        this.minWindowHeight = minWindowHeight;
        this.aspectRatioX = aspectRatioX;
        this.aspectRatioY = aspectRatioY;

        TedPermission.with(this)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        mediaPickerListener = listener;
                        FilePickerBuilder.getInstance()
                                .setMaxCount(count)
                                //.setSelectedFiles(photoPaths)
                                .setActivityTheme(R.style.FilePickeTheme)
                                .enableVideoPicker(videoenabling)
                                .enableCameraSupport(true)
                                .showGifs(true)
                                .enableSelectAll(false)
                                .enableImagePicker(true)
                                .showFolderView(false)
                                .withOrientation(Orientation.UNSPECIFIED)
                                .pickPhoto(MainActivity.this);

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        //Utils.showToast(this, getString(R.string.permission_denied));
                    }
                }).check();
    }

    public void doCrop(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setMinCropWindowSize(500, 350)
                .setAspectRatio(16, 9)
                .getIntent(this);

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void doCrop(Uri uri, int minWindowWidth, int minWindowHeight, int aspectRatioX,
                       int aspectRatioY) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setMinCropWindowSize(minWindowWidth, minWindowHeight)
                .setAspectRatio(aspectRatioX, aspectRatioY)
                .getIntent(this);

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void cropImageResult(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            ArrayList<String> cameraPic = new ArrayList<>();
            cameraPic.add(resultUri.getPath());
            new AsyncTaskRunner().execute(cameraPic);
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    public void openMap(double latitude, double longitude, String title, String label) {
        Intent mapActivity = new Intent(this, MapActivity.class);
        mapActivity.putExtra("latitude", latitude);
        mapActivity.putExtra("longitude", longitude);
        mapActivity.putExtra("title", title);
        mapActivity.putExtra("label", label);
        startActivity(mapActivity);
    }

    private class AsyncTaskRunner extends AsyncTask<ArrayList<String>, ArrayList<File>, ArrayList<File>> {

        ProgressDialog progressDialog;

        @SafeVarargs
        @Override
        protected final ArrayList<File> doInBackground(ArrayList<String>... params) {

            ArrayList<File> compressedAndVideoImageFileList = new ArrayList<>();

            for (int index = 0; index < params[0].size(); index++) {

                File file = new File(params[0].get(index));

                if (file.toString().endsWith(".jpg") ||
                        file.toString().endsWith(".jpeg") ||
                        file.toString().endsWith(".png") ||
                        file.toString().endsWith(".gif")) {
                    try {
                        File compressedImageFile = new Compressor(MainActivity.this).compressToFile(file, "compressed_" + file.getName());
                        compressedAndVideoImageFileList.add(compressedImageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    compressedAndVideoImageFileList.add(file);
                }
            }
            return compressedAndVideoImageFileList;
        }


        @Override
        protected void onPostExecute(ArrayList<File> result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();

            mediaPickerListener.onPhotoClicked(result);


        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    getApplicationContext().getString(R.string.app_name),
                    getApplicationContext().getString(R.string.compressing_image_please_wait));

            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
        }
    }

    public void showRegistrationActivity() {
        Intent i = new Intent(this, RegistrationActivity.class);
        i.putExtra("login", "login");
        startActivity(i);
        finish();
    }

    public void getSetCode(OnCountryPickerListener listener) {
        CountryPicker.Builder builder =
                new CountryPicker.Builder().with(this)
                        .listener(listener);
        CountryPicker picker = builder.build();
        picker.showBottomSheet(this);
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories = categories;
    }

    public ViewPager getStepPager() {
        return stepPager;
    }

    public void setSignupPager(ViewPager stepPager) {
        this.stepPager = stepPager;
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {

        ArrayList<HeaderWrapper> headerWrappers = new ArrayList<>();
        headerWrappers.addAll((ArrayList<HeaderWrapper>) result);
        if (headerWrappers != null) {
            settingSideMenu(sideMenuType, sideMenuDirection, headerWrappers);
            unlockDrawers();

        }
    }


    @Override
    public void ResponseFailure(String tag) {

    }


    public void lockDrawers() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawers() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();

    }


}
