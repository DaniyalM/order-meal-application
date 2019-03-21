package structure.com.foodportal.helper;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.activity.RegistrationActivity;
import structure.com.foodportal.databinding.TitlebarBinding;
import structure.com.foodportal.fragment.LoginFragment;

public class Titlebar extends RelativeLayout {
    private TitlebarBinding binding;
    private OnClickListener menuOnclickListener;
    private OnClickListener notificationOnclickListener;
    RelativeLayout rlSearch;
    ImageView ivSearch;
    public Titlebar(Context context) {
        super(context);
        initLayout(context);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.titlebar, this, true);
        rlSearch = binding.getRoot().findViewById(R.id.rlSearch);
        ivSearch = binding.getRoot().findViewById(R.id.ivSearch);

        resetView();

    }

    public void hideTitlebar() {
        binding.rlTitlebarMainLayout.setVisibility(View.GONE);
    }

    public ImageView showFilter() {
        binding.ivFilter.setVisibility(View.VISIBLE);
        return binding.ivFilter;

    }

    public void showBackButton(final Activity activity) {

        binding.imgBack.setVisibility(VISIBLE);
        binding.imgBack.setImageResource(R.drawable.backbtn);
        binding.imgBack.setOnClickListener(view -> activity.onBackPressed());
    }
    public ImageView showsearch(Activity activity){
        rlSearch.setVisibility(VISIBLE);
        return  ivSearch;
    }

    public void hidesearch(){

        rlSearch.setVisibility(GONE);
    }

    public ImageView showBackButton(final Activity activity, boolean modified) {
        binding.imgBack.setVisibility(VISIBLE);
        binding.imgBack.setImageResource(R.drawable.backbtn);
        return binding.imgBack;
    }


    public void showCrossButton(final Activity activity) {


        binding.imgBack.setVisibility(VISIBLE);
        binding.imgBack.setImageResource((R.drawable.cross));
        binding.imgBack.setOnClickListener(view -> {
            if (activity instanceof MainActivity)
                ((MainActivity) activity).replaceFragmentWithClearBackStack(new LoginFragment(), true, true);
            else
                ((RegistrationActivity) activity).replaceFragmentWithClearBackStack(new LoginFragment(), true, true);
        });


    }

    public ImageView showCrossButton() {
        binding.imgBack.setVisibility(VISIBLE);
        binding.imgBack.setImageResource((R.drawable.cross));
        return binding.imgBack;
    }

    public void showTitlebar() {
        binding.rlTitlebarMainLayout.setVisibility(View.VISIBLE);

    }

    public void showMenuButton(final MainActivity mainActivity) {
        binding.imgBack.setVisibility(VISIBLE);
        binding.imgBack.setImageResource(R.drawable.nav);
       binding.imgBack.setOnClickListener(menuOnclickListener);


    }

    public void setMenuOnclickListener(OnClickListener menuOnclickListener) {
        this.menuOnclickListener = menuOnclickListener;
    }

    public void setNotificationOnclickListener(OnClickListener notificationOnclickListener) {
        this.notificationOnclickListener = notificationOnclickListener;
    }

    public void hideTitle() {
        binding.tvTitle.setVisibility(INVISIBLE);
    }

    public void setTitle(String title) {
        binding.tvTitle.setVisibility(VISIBLE);
        binding.tvTitle.setText(title);
        binding.tvTitle.setSelected(true);
        binding.rlTitlebarMainLayout.setVisibility(View.VISIBLE);
    }

    public void resetView() {
        binding.imgBack.setVisibility(INVISIBLE);
        binding.tvTitle.setVisibility(INVISIBLE);
        binding.imgBack.setVisibility(INVISIBLE);
        binding.rlNotification.setVisibility(INVISIBLE);
        binding.ivEdit.setVisibility(INVISIBLE);
        binding.ivShare.setVisibility(INVISIBLE);
        binding.ivDelete.setVisibility(INVISIBLE);
        binding.ivCart.setVisibility(INVISIBLE);
        binding.ivFilter.setVisibility(INVISIBLE);
        binding.tvCartCount.setVisibility(INVISIBLE);
        hidesearch();


    }

    public void showNotification(int count) {
        binding.rlNotification.setVisibility(VISIBLE);
        binding.rlNotification.setOnClickListener(notificationOnclickListener);
        if (count > 0) {
            binding.tvNotiCount.setVisibility(VISIBLE);
            binding.tvNotiCount.setText(count + "");
        } else
            binding.tvNotiCount.setVisibility(INVISIBLE);
    }

    public ImageView showDeleteButton(final Activity activity) {

        binding.ivDelete.setVisibility(VISIBLE);
        binding.ivDelete.setImageResource(R.drawable.delete);
        return binding.ivDelete;

    }


    public ImageView showEditButton(final Activity activity) {

        binding.ivEdit.setVisibility(VISIBLE);
        binding.ivEdit.setImageResource(R.drawable.edit);
        return binding.ivEdit;

    }

    public ImageView showShareButton(final Activity activity) {

        binding.ivShare.setVisibility(VISIBLE);
        binding.ivShare.setImageResource(R.drawable.share);
        return binding.ivShare;

    }

    public RelativeLayout showCart(int count) {
        binding.rlCart.setVisibility(View.VISIBLE);
        binding.ivCart.setVisibility(View.VISIBLE);


        if (count > 0) {
            if (count > 99) {
                binding.tvCartCount.setText(""+99);
            } else {
                binding.tvCartCount.setText("" + count);

            }

            binding.tvCartCount.setVisibility(View.VISIBLE);



        } else
            binding.tvCartCount.setVisibility(View.INVISIBLE);


        return binding.rlCart;
    }

}
