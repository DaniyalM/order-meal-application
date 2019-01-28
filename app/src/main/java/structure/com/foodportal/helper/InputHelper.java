package structure.com.foodportal.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import structure.com.foodportal.activity.MainActivity;

/**
 * Created by anumrukhsar on 10/24/2017.
 */

public class InputHelper {
    public static void call(String number, MainActivity context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static void email(String email, MainActivity context) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Renting App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Lorum ipsum dolor ...");
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

    }

    public static void sms(String number, MainActivity context) {
        TedPermission.with(context).setPermissions(Manifest.permission.SEND_SMS)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, "sms message", null, null);
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.putExtra("sms_body", "");
                        sendIntent.setType("vnd.android-dir/mms-sms");
                        context.startActivity(sendIntent);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        UIHelper.showToast(context, "Permission is required for this Feature");
                    }
                })
                .check();

    }

    public static File persistImage(Bitmap bitmap, String name, Context context) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("Error", "Error writing bitmap", e);
        }

        return imageFile;
    }

    public static File persistVideo(Bitmap newBitmap, String name, Context context) throws IOException {
        File filesDir = context.getFilesDir();
        File videoFile = new File(filesDir, name + ".mp4");

        OutputStream os;
        try {
            os = new FileOutputStream(videoFile);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("Error", "Error writing bitmap", e);
        }

        return videoFile;
    }

}
