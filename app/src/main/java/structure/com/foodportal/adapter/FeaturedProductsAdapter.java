package structure.com.foodportal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.databinding.LayoutFeaturedProductBinding;
import structure.com.foodportal.fragment.ProductDetailFragment;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.ProductModelAPI;

public class FeaturedProductsAdapter extends PagerAdapter {
    MainActivity mainActivity;
    LayoutFeaturedProductBinding binding;
    LayoutInflater layoutInflater;
    ArrayList<ProductModelAPI> arrayList;

    public FeaturedProductsAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.layoutInflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = new ArrayList<>();
    }

    public void addAll(ArrayList<ProductModelAPI> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView = layoutInflater.inflate(R.layout.layout_featured_product, container, false);
        ImageView ivSliderImage = rootView.findViewById(R.id.ivSliderImage);
        TextView tvProductName = rootView.findViewById(R.id.tvProductName);
        TextView btnInquireNow = rootView.findViewById(R.id.btnInquireNow);

        if (arrayList.get(position).getProductImages().size() > 0) {
            UIHelper.setImageWithGlide(mainActivity, ivSliderImage, arrayList.get(position).getProductImages().get(0).getProductImage());
        }
        tvProductName.setText(arrayList.get(position).getTitle());
        btnInquireNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailFragment fragment = new ProductDetailFragment();
                fragment.setProductDetail(arrayList.get(position), false);
                if (mainActivity.prefHelper.getLoginStatus()) {
                    if (arrayList.get(position).getUserId() == mainActivity.prefHelper.getUser().getId()) {
                        fragment.setProductDetail(arrayList.get(position), true);
                    }
                }
                mainActivity.replaceFragment(fragment, true, true);
            }
        });




        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
