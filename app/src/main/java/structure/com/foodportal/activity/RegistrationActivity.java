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
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import id.zelory.compressor.Compressor;
import structure.com.foodportal.R;
import structure.com.foodportal.fragment.GetStartedFragment;
import structure.com.foodportal.fragment.LoginFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.GooglePlaceHelper;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.MediaTypePicker;
import structure.com.foodportal.interfaces.OnActivityResultInterface;
import structure.com.foodportal.interfaces.foodInterfaces.DataListner;
import structure.com.foodportal.models.foodModels.User;

public class RegistrationActivity extends FacebookBaseFragment {
    private static final int RC_SIGN_IN = 1;
    AVLoadingIndicatorView imgLoader;
    FrameLayout mainFrame;
    Titlebar titlebar;
    private PermissionListener permissionListener;
    //  private ActivityResultInterface onActivityResultInterface;
    OnActivityResultInterface onActivityResultInterface;
    private ViewPager signupPager;
    private boolean isLoading;
    ArrayList<String> photoPaths;
    MediaTypePicker mediaPickerListener;
    String navigator;
    GoogleSignInApi mGoogleSignInClientApi;
    GoogleSignInClient mGoogleSignInClient;
    int minWindowWidth, minWindowHeight, aspectRatioX, aspectRatioY;

    public String getUserModel() {
        return userModel;
    }

    public void setUserModel(String userModel) {
        this.userModel = userModel;
    }

    String userModel;

    @Override
    public void onSocialInfoFetched(JSONObject data) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // navigator = getIntent().getStringExtra("login");

        init();
        google();

    }

    public void showLoader() {
        imgLoader.show();
        imgLoader.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    public void hideLoader() {
        imgLoader.hide();
        imgLoader.setVisibility(View.GONE);
        isLoading = false;
    }

    private void init() {
        imgLoader = findViewById(R.id.imgLoader);
        // showAnimation(imgLoader);
        mainFrame = findViewById(R.id.mainFrame);
        setFrame(R.id.mainFrame);
        titlebar = findViewById(R.id.title);
        fragmentsNavigator();
//
//        permissionListener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//               // onActivityResultInterface.onActivityResultI(AppConstant.CAMERA_PERMISSION, 1, null);
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(RegistrationActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//        };
    }

    private void fragmentsNavigator() {

        if (prefHelper != null && prefHelper.getLoginStatus()) {

            showMainActivity();
        } else {

            replaceFragment(new GetStartedFragment(), true, false);

        }
    }

    public Titlebar getTitleBar() {
        return titlebar;
    }

    public void getSetCode(OnCountryPickerListener listener) {
        CountryPicker.Builder builder =
                new CountryPicker.Builder().with(this)
                        .listener(listener);
        CountryPicker picker = builder.build();
        picker.showBottomSheet(this);
    }

    public void cameraPermission() {
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

//    public void setOnActivityResultInterface(ActivityResultInterface onActivityResultInterface) {
//        this.onActivityResultInterface = onActivityResultInterface;
//    }

    public ViewPager getSignupPager() {
        return signupPager;
    }

    public void setSignupPager(ViewPager signupPager) {
        this.signupPager = signupPager;
    }

    public void showMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {

        if (signupPager != null) {


            if (signupPager.getCurrentItem() == 0) {
                UIHelper.hideSoftKeyboards(this);

                if (getSupportFragmentManager().getBackStackEntryCount() > 1 && !isLoading) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    UIHelper.openExitPopUp(this);
                }

            } else if (signupPager.getCurrentItem() >= 4) {

                switch (signupPager.getCurrentItem()) {


                    case 4:

                        UIHelper.showSimpleDialog(
                                this,
                                0,
                                this.getResources().getString(R.string.verification),
                                this.getResources().getString(R.string.confirmation_witoutpin),
                                this.getResources().getString(R.string.skip),
                                this.getResources().getString(R.string.cancel),
                                false,
                                false,
                                (dialog, which, positive, logout) -> {
                                    if (positive) {
                                        // popBackStackTillEntry(0);
                                        setSignupPager(null);
                                        replaceFragmentWithClearBackStack(new GetStartedFragment(), true, false);
                                        replaceFragment(new LoginFragment(), true, false);
                                        dialog.dismiss();
                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                        );
                        break;

                    case 5:

                        UIHelper.showSimpleDialog(
                                this,
                                0,
                                this.getResources().getString(R.string.without_picture),
                                this.getResources().getString(R.string.confirmation_profile_picture),
                                this.getResources().getString(R.string.skip),
                                this.getResources().getString(R.string.cancel),
                                false,
                                false,
                                (dialog, which, positive, logout) -> {
                                    if (positive) {
                                        setSignupPager(null);
                                        this.showMainActivity();
                                        dialog.dismiss();
                                    } else {
                                        dialog.dismiss();
                                    }
                                }
                        );
                        break;


                    case 6:

                        UIHelper.openExitPopUp(this);
                        break;


                }

            } else {

                signupPager.setCurrentItem(signupPager.getCurrentItem() - 1);

            }


        } else {
            UIHelper.hideSoftKeyboards(this);

            if (getSupportFragmentManager().getBackStackEntryCount() > 1 && !isLoading) {
                getSupportFragmentManager().popBackStack();
            } else {
                UIHelper.openExitPopUp(this);
            }


        }


    }

    public void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void google(){
        String servertoken= this.getResources().getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
             //   .requestServerAuthCode(servertoken)
            //    .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestProfile()
                .requestId()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

//    @Override
//    protected void onStart() {
//        //super.onStart();
//       // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//       // updateUI(account);
//    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google SignIn Optons", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    DataListner dataListner;
    public  void setcontent(DataListner dataListner) {
        this.dataListner= dataListner;

    }
    private void updateUI(GoogleSignInAccount account) throws JSONException {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(account!=null){

        User user = new User();
        user.setAcct_type(2);
        user.setProfile_picture(String.valueOf(account.getPhotoUrl()));
        user.setName_en(account.getDisplayName());
        user.setId(account.getId());
        user.setEmail(account.getEmail());

      dataListner.getdataGOOGLE(user);

//        prefHelper.putUserFood(user);
//        prefHelper.setLoginStatus(true);
//        finish();
//        showMainActivity();
//          Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();

      }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        FacebookBaseFragment.callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;


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


                    new RegistrationActivity.AsyncTaskRunner().execute(cameraPic);

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

    public void doCrop(Uri uri, int minWindowWidth, int minWindowHeight, int aspectRatioX, int aspectRatioY) {
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
            Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    public void openFilePicker(final MediaTypePicker listener, final int count, final Boolean videoenabling) {

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
                                .pickPhoto(RegistrationActivity.this);

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        //Utils.showToast(this, getString(R.string.permission_denied));
                    }
                }).check();
    }

    public void openFilePicker(final MediaTypePicker listener, final int count, final Boolean videoenabling, int minWindowWidth, int minWindowHeight, int aspectRatioX, int aspectRatioY) {
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
                                .pickPhoto(RegistrationActivity.this);

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        //Utils.showToast(this, getString(R.string.permission_denied));
                    }
                }).check();
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
                        File compressedImageFile = new Compressor(RegistrationActivity.this).compressToFile(file, "compressed_" + file.getName());
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
            progressDialog = ProgressDialog.show(RegistrationActivity.this,
                    getApplicationContext().getString(R.string.app_name),
                    getApplicationContext().getString(R.string.compressing_image_please_wait));

            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
        }
    }


}
