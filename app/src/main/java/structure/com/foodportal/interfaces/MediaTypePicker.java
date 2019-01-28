package structure.com.foodportal.interfaces;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ahsanali on 11/23/2017.
 */

public interface MediaTypePicker {
    void onPhotoClicked(ArrayList<File> file);
    void onDocClicked(ArrayList<String> files);
}
