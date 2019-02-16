package structure.com.foodportal.adapter.foodPortalAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import structure.com.foodportal.R;
import structure.com.foodportal.customViews.data.BaseItem;
import structure.com.foodportal.customViews.data.CustomDataProvider;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.models.foodModels.DrawerItem;

public class DrawerAdapter extends MultiLevelListAdapter {
    private final Context context;
    String selectedItem = "";

    private class ViewHolder {
        TextView nameView;
        TextView infoView;
        ImageView arrowView;
        ImageView logoImage;
        View selector;
        View verticalView;
        View horizontalView;
        //  LevelBeamView levelBeamView;
    }

    public DrawerAdapter(Context context) {
        this.context = context;

    }

    @Override
    public List<?> getSubObjects(Object object) {
        return CustomDataProvider.getSubItems((BaseItem) object);
    }

    @Override
    public boolean isExpandable(Object object) {
        return CustomDataProvider.isExpandable((BaseItem) object);
    }

    @Override
    public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.data_item, null);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.dataItemName);
            viewHolder.arrowView = (ImageView) convertView.findViewById(R.id.dataItemArrow);
            viewHolder.verticalView = (View) convertView.findViewById(R.id.verticalLine);
            viewHolder.horizontalView = (View) convertView.findViewById(R.id.horizontalLine);
            viewHolder.selector = (View) convertView.findViewById(R.id.selector);
            viewHolder.logoImage = (ImageView) convertView.findViewById(R.id.logoImage);
            //   viewHolder.levelBeamView = (LevelBeamView) convertView.findViewById(R.id.dataItemLevelBeam);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameView.setText(((BaseItem) object).getName());
//        if((((BaseItem) object).getImage())!=null){
//         //   viewHolder.logoImage.setVisibility(View.VISIBLE);
//          //  UIHelper.setImageWithGlide(context,viewHolder.logoImage, ((BaseItem) object).getImage());
//
//        }

        if (!Utils.isEmptyOrNull(selectedItem)&&selectedItem.equals(((BaseItem) object).getName())) {
            viewHolder.selector.setVisibility(View.VISIBLE);
        } else {
            viewHolder.selector.setVisibility(View.GONE);
        }

        setVerticalAndHorizontalView(viewHolder,itemInfo);

        if (itemInfo.isExpandable()) {
            viewHolder.arrowView.setVisibility(View.VISIBLE);
            viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                    R.drawable.uparrow : R.drawable.downarrow);
        } else {
            viewHolder.arrowView.setVisibility(View.GONE);
        }

        //  viewHolder.levelBeamView.setLevel(itemInfo.getLevel());

        return convertView;
    }

    public void setVerticalAndHorizontalView(ViewHolder viewHolder,ItemInfo itemInfo){
        switch (itemInfo.getLevel()) {
            case 1:
                viewHolder.verticalView.setVisibility(View.VISIBLE);
                viewHolder.horizontalView.setVisibility(View.VISIBLE);
                viewHolder.nameView.setPadding(0, 0, 0, 0);
                break;
            case 2:
                viewHolder.verticalView.setVisibility(View.VISIBLE);
                viewHolder.horizontalView.setVisibility(View.GONE);
                viewHolder.nameView.setPadding(150, 0, 0, 0);
                break;
            default:
                viewHolder.verticalView.setVisibility(View.GONE);
                viewHolder.horizontalView.setVisibility(View.GONE);
                viewHolder.nameView.setPadding(0, 0, 0, 0);
                break;
        }
    }
    public void setSelectedItem(String itemName){
        this.selectedItem=itemName;
        notifyDataSetChanged();

    }

}
