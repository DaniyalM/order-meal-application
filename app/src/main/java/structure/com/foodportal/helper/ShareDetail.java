package structure.com.foodportal.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

import structure.com.foodportal.activity.BaseActivity;

public class ShareDetail extends AsyncTask<String, Void, Bitmap> {
    Context _context;
    String _url;
    String _title;
    ProgressDialog mProgressDialog;

    public ShareDetail(Context context,String title, String url) {
        this._context = context;
        this._url = url;
        this._title = title;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String imageURL = strings[0];

        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//        super.onPostExecute(bitmap);
        UIHelper.shareWithImage((BaseActivity) _context,_title,_url, bitmap);
    }
}
