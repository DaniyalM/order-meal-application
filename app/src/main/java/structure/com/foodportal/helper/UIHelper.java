package structure.com.foodportal.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.BaseActivity;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.interfaces.SimpleDialogActionListener;

/**
 * Created by Addi.
 */
public  class UIHelper {

    public interface Utilinterface {
        public void dialogPositive_Click(DialogInterface dialog);
    }

    public static void alertDialog(String title, String msg, Boolean isSingleButton, final Utilinterface utilinterface, MainActivity mainActivity, String posBtnTxt, String ngtvBtnTxt) {

        final AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(mainActivity);

        builder
                .setMessage(msg)
                .setPositiveButton(posBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        utilinterface.dialogPositive_Click(dialog);
                    }
                })
                .setNegativeButton(ngtvBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                    }
                })
                .show();

    }

    @SuppressLint("CheckResult")
    public static void setImageWithGlide(Context context, ImageView view, String url) {
        Glide.with(context).clear(view);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(context.getResources().getColor(R.color.colorAccentPink));
        circularProgressDrawable.setColorFilter(context.getResources().getColor(R.color.colorAccentPink),android.graphics.PorterDuff.Mode.MULTIPLY);
        circularProgressDrawable.start();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate().placeholder(circularProgressDrawable);



        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(view);
    }

    public static void setImagewithGlide(Activity activity, ImageView imageView, String path) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate().placeholder(R.drawable.placeholder);
        Glide.with(activity)
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void showToast(Context activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void hideSoftKeyboards(BaseActivity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void openExitPopUp(final Activity activity) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
            builder1.setMessage(activity.getString(R.string.are_you_sure_exit));
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    activity.getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            activity.finish();
                        }
                    });

            builder1.setNegativeButton(
                    activity.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
    }

    public static void showSimpleDialog(final Context context, int icon, String title, String
            message, String positiveButton, String negativeButton, boolean cancelable,
                                        final boolean logout, final SimpleDialogActionListener simpleDialogActionListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (logout) {
                            simpleDialogActionListener.onDialogActionListener(dialog, which, true, true);
                        } else {
                            simpleDialogActionListener.onDialogActionListener(dialog, which, true, false);
                        }
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleDialogActionListener.onDialogActionListener(dialog, which, false, false);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String getMomentsAgo(String date) {
        String moments = null;
        String dateDate = null, dateTime = null, thatDay = null, thatMonth = null, thatYear = null, thatHour, thatMinute, thatSeconds;

        if(date != null){
            dateDate = date.split(" ")[0];
            thatDay = dateDate.split("-")[2];
            thatMonth = dateDate.split("-")[1];
            thatYear = dateDate.split("-")[0];

            dateTime = date.split(" ")[1];
        }

        moments = thatDay + " - " + thatMonth + " - " + thatYear;
        return moments;
    }

    public static String getFormattedDate(String date, String oldFormat, String newFormat){
        String formattedDate= "";
        SimpleDateFormat input = new SimpleDateFormat(oldFormat);
        SimpleDateFormat output = new SimpleDateFormat(newFormat);
        try {
            Date newDate = input.parse(date);
            formattedDate = output.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static void share(BaseActivity activityContext, final String title, final String fileUrl){
        if (!NetworkUtils.isNetworkAvailable(activityContext)) {
            UIHelper.showToast(activityContext, activityContext.getResources().getString(R.string.no_connection));
            return;
        }

        TedPermission.with(activityContext).setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                new ShareDetail(activityContext, title, "").execute(fileUrl);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                UIHelper.showToast(activityContext, activityContext.getResources().getString(R.string.permission_denied));

            }
        }).check();
    }

    public static void shareWithImage(BaseActivity activityContext, String title, String url, Bitmap bitmap) {
        if (!NetworkUtils.isNetworkAvailable(activityContext)) {
            UIHelper.showToast(activityContext, activityContext.getResources().getString(R.string.no_connection));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);
        String path = null;
        path = MediaStore.Images.Media.insertImage(activityContext.getContentResolver(), bitmap, title, null);
        Uri screenshotUri = Uri.parse(path);
        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        activityContext.startActivity(Intent.createChooser(intent, activityContext.getResources().getString(R.string.share_image_via)));
    }

    public static void openEmailComposer(BaseActivity activityContext, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        try {
            activityContext.startActivity(Intent.createChooser(intent, activityContext.getResources().getString(R.string.send_email)));
        } catch (ActivityNotFoundException activityNotFoundException) {
            UIHelper.showToast(activityContext, activityContext.getResources().getString(R.string.no_email_client));
        }
    }

    public static void animation(Techniques techniques, int duration, int repeat, View view) {
        //view.clearAnimation();
        // view.clearFocus();
        YoYo.with(techniques)
                .duration(duration)
                .repeat(repeat)
                .playOn(view);
        view.clearFocus();
        view.clearAnimation();
    }
}
