package structure.com.foodportal.customViews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.DrawerAdapter;
import structure.com.foodportal.customViews.data.BaseItem;
import structure.com.foodportal.customViews.data.CustomDataProvider;
import structure.com.foodportal.databinding.FragmentSidemenuBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.Spanny;
import structure.com.foodportal.helper.Titlebar;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import structure.com.foodportal.models.foodModels.DrawerItem;
import structure.com.foodportal.models.foodModels.HeaderWrapper;


public class MultiLeftSideMenu extends BaseFragment {
    private Context context;
    private FragmentSidemenuBinding binder;
    private DrawerAdapter drawerAdapter;
    ArrayList<DrawerItem> drawerItems;
    ArrayList<HeaderWrapper> headerWrapper;

    public static MultiLeftSideMenu newInstance() {

        return new MultiLeftSideMenu();
    }

    public  MultiLeftSideMenu(ArrayList<HeaderWrapper> headerWrapper) {
        this.headerWrapper = headerWrapper;


    }

    public MultiLeftSideMenu() {


    }


    public void MultiLeftSideMenu(ArrayList<HeaderWrapper> headerWrapper) {
        this.headerWrapper = headerWrapper;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_sidemenu, container, false);
        // if(headerWrapper!=null && headerWrapper.size()>0){

        try {
            confMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  }

        return binder.getRoot();
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    private void confMenu() throws IOException {


        // custom ListAdapter
        drawerAdapter = new DrawerAdapter(mainActivity);

        binder.sideoptions.setAdapter(drawerAdapter);
        binder.sideoptions.setOnItemClickListener(mOnItemClickListener);
        drawerItems = new ArrayList<>();
        ArrayList<DrawerItem> chilItems = new ArrayList<>();
        ArrayList<DrawerItem> beautyChildItems = new ArrayList<>();
        ArrayList<DrawerItem> womenApparelChildItems = new ArrayList<>();
        ArrayList<DrawerItem> healthAndLifeChildItems = new ArrayList<>();
        ArrayList<DrawerItem> saleChildItems = new ArrayList<>();


        if (headerWrapper != null && headerWrapper.size() > 0) {
            String sourceString = "<b>" + "Home"+ "</b> ";

            drawerItems.add((new DrawerItem(new BaseItem(Html.fromHtml(sourceString).toString(),null),  AppConstant.DrawerItemType.ITEM, null,null)));
            for (int i = 0; i < headerWrapper.size(); i++) {

             if(headerWrapper.get(i).getCategories().size()>0){

                 for(int k=0; k<headerWrapper.get(i).getCategories().size();k++){





                     chilItems.add(new DrawerItem(new BaseItem(headerWrapper.get(i).getCategories().get(k).getCategory_title_en(),
                             headerWrapper.get(i).getCategories().get(k).getSlider_thumb_path()
                             ), AppConstant.DrawerItemType.ITEM, null,headerWrapper.get(i).getCategories().get(k).getSlider_thumb_path()));

                 }
                 sourceString = "<b>"+headerWrapper.get(i).getTitle_en() +"</b> ";
                 drawerItems.add((new DrawerItem(new BaseItem(Html.fromHtml(sourceString).toString(),null), headerWrapper.get(i).getCategories().size() > 0 ? AppConstant.DrawerItemType.GROUP : AppConstant.DrawerItemType.ITEM, chilItems,null)));

             }



            }
             sourceString = "<b>" + "Logout"+ "</b> ";
            drawerItems.add((new DrawerItem(new BaseItem(Html.fromHtml(sourceString).toString(),null),  AppConstant.DrawerItemType.ITEM, null,null)));

            drawerAdapter.setDataItems(CustomDataProvider.getInitialItems(drawerItems));
            drawerAdapter.setSelectedItem(mainActivity.getResources().getString(R.string.makeup));
        }

//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        beautyChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        womenApparelChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        healthAndLifeChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        healthAndLifeChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        saleChildItems.add((new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null)));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, beautyChildItems));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, womenApparelChildItems));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, healthAndLifeChildItems));
//        chilItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.GROUP, saleChildItems));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.categories)), AppConstant.DrawerItemType.GROUP, chilItems));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.my_account)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));
//        drawerItems.add(new DrawerItem(new BaseItem(getString(R.string.makeup)), AppConstant.DrawerItemType.ITEM, null));

    }


    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        private void showItemDescription(Object object, ItemInfo itemInfo) {
            StringBuilder builder = new StringBuilder("\"");
            builder.append(((BaseItem) object).getName());
            builder.append("\" clicked!\n");
            builder.append(getItemInfoDsc(itemInfo));

            Toast.makeText(mainActivity, builder.toString(), Toast.LENGTH_SHORT).show();

            switch (((BaseItem) object).getName()) {






            }
        }

        @Override
        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            //showItemDescription(item, itemInfo);
            mainActivity.closeDrawer();
            if (itemInfo.getLevel() == 0) {
                drawerAdapter.setSelectedItem(((BaseItem) item).getName());
            }

            switch (((BaseItem) item).getName()) {

//                case "Shop":activityContext
//                    .emptyBackStack();
//                    activityContext.initFragmentsWithData(new HomeFragment(), null);
//                    break;
//                case "Brands":
//
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(new BrandsFragment(), null);
//                    break;
//                case "Celebrities":
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(new CelebritiesFragment(), null);
//                    break;
//                case "Wishlist":
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(WishListFragment.newInstance(), null);
//                    break;
//                case "My Account":
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(new MyAccountFragment(), null);
//
//                    break;
//                case "Settings":
//
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(new SettingsFragment(), null);
//                    break;
//                case "Contact Us":
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(new ContactUsFragment(), null);
//                    break;
//                case "FAQ's":
//                    activityContext.emptyBackStack();
//                    activityContext.initFragmentsWithData(new FaqsFragment(), null);
//                    break;
//                case "Fragranance":
//
//                    break;
//                case "Bath and Body":
//
//                    break;
//                case "Skincare":
//
//                    break;
//                case "Haircare":
//
//                    break;
//                case "Nailcare":
//
//                    break;
                default:

                    //  activityContext.initFragmentsWithData(ProductsFragment.newInstance(((BaseItem) item).getName()), null);
                    break;


            }


        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            // showItemDescription(item, itemInfo);
            if (itemInfo.getLevel() == 0) {
                drawerAdapter.setSelectedItem(((BaseItem) item).getName());


            }
        }
    };


    private String getItemInfoDsc(ItemInfo itemInfo) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("level[%d], idx in level[%d/%d]",
                itemInfo.getLevel() + 1, /*Indexing starts from 0*/
                itemInfo.getIdxInLevel() + 1 /*Indexing starts from 0*/,
                itemInfo.getLevelSize()));

        if (itemInfo.isExpandable()) {
            builder.append(String.format(", expanded[%b]", itemInfo.isExpanded()));
        }
        return builder.toString();
    }

    public void updateLeftDrawer(String name) {

        drawerAdapter.setSelectedItem(name);

    }

    @Override
    protected void setTitle(Titlebar titlebar) {

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void create() throws IOException {

        confMenu();
        drawerAdapter.notifyDataSetChanged();
    }
}

