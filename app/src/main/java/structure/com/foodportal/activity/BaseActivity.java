package structure.com.foodportal.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerConst;
import id.zelory.compressor.Compressor;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import structure.com.foodportal.R;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.GooglePlaceHelper;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.OnActivityResultInterface;

public class BaseActivity extends AppCompatActivity {

    private BaseFragment baseFragment;
    public int mainFrame;
    public BasePreferenceHelper prefHelper;
    OnActivityResultInterface onActivityResultInterface;
     boolean cardFlipAnimationBool=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefHelper = new BasePreferenceHelper(this);
    }

    protected void showAnimation(final ImageView imgAnimation) {

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getAssets(), "drawable-hdpi/foodtasty.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gifDrawable != null) {

            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
//                    imgAnimation.setVisibility(View.GONE);
                }
            });
        }
        imgAnimation.setImageDrawable(gifDrawable);
    }

    public void replaceFragmentWithClearBackStack(BaseFragment frag, boolean isAddToBackStack, boolean animate) {
        clearBackStack();
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.replace(mainFrame, frag, frag.getClass().getSimpleName());

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }


    protected void setFrame(int mainFrame) {
        this.mainFrame = mainFrame;

    }

    public void addFragment(BaseFragment frag, String tag, boolean isAddToBackStack, boolean animate) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.add(mainFrame, frag, tag);

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }
    public void popBackStackTillEntry(int entryIndex) {
        if (getSupportFragmentManager() == null)
            return;
        if (getSupportFragmentManager().getBackStackEntryCount() <= entryIndex)
            return;
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                entryIndex);
        if (entry != null) {
            getSupportFragmentManager().popBackStack(entry.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
    protected void willbeimplementedinfuture() {
        UIHelper.showToast(this, "Will be implemented in Next Module");
    }
    public void popFragment() {
        if (getSupportFragmentManager() == null)
            return;
        getSupportFragmentManager().popBackStack();
    }

    public void setCardFlipAnimationBool(boolean cardFlipAnimationBool){

        this.cardFlipAnimationBool=cardFlipAnimationBool;

    }
    public void replaceFragment(BaseFragment frag, boolean isAddToBackStack, boolean animate) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {

            if(cardFlipAnimationBool){
                transaction.setCustomAnimations(R.anim.animation_item,0,0,0);
            }else {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            }

        }
        transaction.replace(mainFrame, frag, frag.getClass().getSimpleName());

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }
    public void replaceFragment(BaseFragment frag, boolean isAddToBackStack, boolean animate,boolean isBottom) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {

            if(cardFlipAnimationBool){
                transaction.setCustomAnimations(R.anim.animation_item,0,0,0);
            }else {
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, 0,0);
            }

        }
        transaction.replace(mainFrame, frag, frag.getClass().getSimpleName());

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }

    public void addFragmentwithsharedelement(BaseFragment frag, boolean isAddToBackStack, boolean animate, View id, String transitioname) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addSharedElement(id,transitioname);
        if (animate) {

            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.add(mainFrame, frag, frag.getClass().getSimpleName());

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }
    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }
    public void addFragment(BaseFragment frag, boolean isAddToBackStack, boolean animate) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {

            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
           // transaction.setCustomAnimations(R.anim.fadein,R.anim.fadein);
        }
        transaction.add(mainFrame, frag, frag.getClass().getSimpleName());

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }
   public void addFragment(BaseFragment frag, boolean isAddToBackStack, boolean animate,boolean state) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animate) {
            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, 0,0);
            //transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.add(mainFrame, frag, frag.getClass().getSimpleName());

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }

    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
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
                 //   photoPaths = new ArrayList<>();
                 //   photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                  //  new BaseActivity().AsyncTaskRunner().execute(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {


                //    mediaPickerListener.onDocClicked(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));

                }
                break;
            case AppConstant.CAMERA_PIC_REQUEST:
                if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {

                    ArrayList<String> cameraPic = new ArrayList<>();
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                  //  Uri tempUri = getImageUri(this, (Bitmap) data.getExtras().get("data"));
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
////

                 //  new BaseActivity().AsyncTaskRunner().execute(cameraPic);

                }
                break;
        }
    }

    public void setOnActivityResultInterface(OnActivityResultInterface activityResultInterface) {
        this.onActivityResultInterface = activityResultInterface;
    }


    private  class AsyncTaskRunner extends AsyncTask<ArrayList<String>, ArrayList<File>, ArrayList<File>> {

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
                        File compressedImageFile = new Compressor(BaseActivity.this).compressToFile(file, "compressed_" + file.getName());
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

         //   mediaPickerListener.onPhotoClicked(result);


        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(BaseActivity.this,
                    getApplicationContext().getString(R.string.app_name),
                    getApplicationContext().getString(R.string.compressing_image_please_wait));

            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
        }
    }


}
