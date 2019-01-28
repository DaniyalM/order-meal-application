package structure.com.foodportal.interfaces;

import android.content.Intent;

import java.io.IOException;

/**
 * Created by ahsanali on 6/17/2017.
 */

public interface OnActivityResultInterface {
    void onActivityResultInterface(int requestCode, int resultCode, Intent data) throws IOException;
}
