package structure.com.foodportal.interfaces.foodInterfaces;

import com.bumptech.glide.load.engine.GlideException;

public interface FoodImageLoadListener {

    void onFailure(GlideException e);
    void onSuccess();
}
