package structure.com.foodportal.customViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import structure.com.foodportal.R;

public class DefaultSliderView extends BaseSliderView {

    public DefaultSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        TextView targettv = (TextView)v.findViewById(R.id.tvRecipename);
        bindEventAndShow(v, target,targettv);
        return v;
    }
}