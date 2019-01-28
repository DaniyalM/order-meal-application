package structure.com.foodportal.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.FragmentUserAccountBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.ProductCreation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.models.UserModel;
import structure.com.foodportal.viewbinders.ProductItemBinder;

@SuppressWarnings({"SetTextI18n", "unchecked"})
public class UserAccountFragment extends BaseFragment implements View.OnClickListener {
    private FragmentUserAccountBinding binding;
    private RecyclerItemClickListener mProductItemClickListener = ((ent, position, id) -> {
        switch (id) {
            case R.id.itemProduct:
                ProductDetailFragment fragment = new ProductDetailFragment();
                fragment.setProductDetail((ProductModelAPI) ent, true);
                replaceFragment(fragment, true, true);


                break;
        }
    });

    public static UserAccountFragment newInstance() {
        Bundle args = new Bundle();

        UserAccountFragment fragment = new UserAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                titlebar.resetView();
                titlebar.showTitlebar();
                titlebar.setTitle(mainActivity.getResources().getString(R.string.my_account));
                titlebar.showMenuButton(mainActivity);
            }
        },1000);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_account, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity.showBottombar();

        setListners();
        init();
    }

    private void setListners() {
        binding.btnAddproduct.setOnClickListener(this);
        binding.btnEdit.setOnClickListener(this);

    }

    private void init() {
        UserModel model = preferenceHelper.getUser();
        ImageLoader.getInstance().displayImage(model.getProfileImage(), binding.imgItemPic);

        binding.mrOwnerRating.setRating(model.getRating());
        binding.txtName.setText(model.getFullName() + "");
        binding.txtEmail.setText(model.getEmail() + "");
        binding.txtMobileNumber.setText(model.getPhoneNo() + "");
        binding.txtAddress.setText(model.getFullAddress() + "");
        if (model.getCity() != null && model.getCountry() != null) {
            binding.txtCountry.setText(model.getCity() + ", " + model.getCountry());
        }




        serviceHelper.enqueueCall(webService.getUserProducts(model.getId()), AppConstant.GET_MY_PRODUCTS);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.GET_MY_PRODUCTS:
                bindRecyclerView((ArrayList<ProductModelAPI>) result);
                break;
        }
    }

    private void bindRecyclerView(ArrayList<ProductModelAPI> result) {
        if (result.size() < 1) {
            binding.containerNoData.setVisibility(View.VISIBLE);
            binding.rvMyProducts.setVisibility(View.GONE);
        } else {
            int spacingInPixels = mainActivity.getResources().getDimensionPixelSize(R.dimen.dp8);
            HomeFragment.SpacesItemDecorationAllSideEqual spacesItemDecorationHome = new HomeFragment.SpacesItemDecorationAllSideEqual(spacingInPixels);

            binding.rvMyProducts.addItemDecoration(spacesItemDecorationHome);
            binding.rvMyProducts.bindRecyclerView(new ProductItemBinder(mainActivity, mProductItemClickListener, true), result, new GridLayoutManager(getContext(), 2), new DefaultItemAnimator());
            binding.rvMyProducts.setNestedScrollingEnabled(false);
            binding.rvMyProducts.setHasFixedSize(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEdit:
                replaceFragment(new EditProfileFragment(), true, true);
                break;

            case R.id.btnAddproduct:
                AppConstant.selectedDays.clear();
                AppConstant.checkedDays = AppConstant.clearCheckedDays;
                ProductCreation.getInstance().reset();
                replaceFragment(new AddProductFragment(), true, true);
                break;


        }
    }
}