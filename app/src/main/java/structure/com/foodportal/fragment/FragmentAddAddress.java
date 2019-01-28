package structure.com.foodportal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.AddressAdapter;
import structure.com.foodportal.helper.Titlebar;

public class FragmentAddAddress extends BaseFragment implements AddressAdapter.Addaddresslistner {


    @BindView(R.id.rvAddress)
    RecyclerView rvAddress;
    @BindView(R.id.tvAddAddress)
    TextView tvAddAddress;
    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;
    AddressAdapter addressAdapter;
    ArrayList<String> list;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);
        ButterKnife.bind(this, view);
        mainActivity.hideBottombar();
        init();
        return view;
    }

    private void init() {
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        addressAdapter = new AddressAdapter(mainActivity, list, this);
        rvAddress.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();

    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        titlebar.resetView();
        titlebar.setVisibility(View.VISIBLE);
        titlebar.setTitle(getString(R.string.delivery_address));
        titlebar.showTitlebar();
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onClickDelete(int position) {

    }

    @OnClick(R.id.tvAddAddress)
    public void onViewClicked() {

    }
}
