package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.models.foodModels.Ingredient;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<Ingredient> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<Ingredient>> _listDataChild;

    MainActivity mainActivity;
    public ExpandableListAdapter(Context context, ArrayList<Ingredient> listDataHeader,
                                 HashMap<String, ArrayList<Ingredient>> listChildData, MainActivity mainActivity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.mainActivity = mainActivity;
    }

    //    @Override
//    public Object getChild(int groupPosition, int childPosititon) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .get(childPosititon);
//    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
// return this._listDataChild.get().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //Child/////
    TextView tvCardetail,
            tvChasis,
            tvYear,
            tvMileage,
            tvBidPrice,
            tvFOB,
            tvShippingCountry,
            tvCNF,
            tvShipmentType,
            tvFrieghtCharges, tvOrderStatus,
            tvPort;
    ImageView ivCar;
    ProgressBar pbLoader;
    LinearLayout statusColor;

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        final String childText = (String) getChild(groupPosition, childPosition);
        final ArrayList<Ingredient> orders = _listDataChild.get(_listDataHeader.get(groupPosition).getTag_en());
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_ingredients, null);
        }

        tvCardetail = (TextView) convertView.findViewById(R.id.tvingredients);
        tvCardetail.setText(orders.get(childPosition).getSub_ingredients().get(childPosition).getTag_en());
        return convertView;
    }

    //    @Override
//    public int getChildrenCount(int groupPosition) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getOrderItems().get(groupPosition).getOrderDetails().get(groupPosition)).size();
//    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(_listDataHeader.get(groupPosition).getSub_ingredients()).size();
// return this._listDataChild.get(this._listDataHeader.get(groupPosition).getOrderItems().get(groupPosition).getOrderDetails().get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }



    ///Main ////
    int j,i=0;
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_ingredients, null);
        }
        TextView orderid;
        orderid = (TextView) convertView.findViewById(R.id.tvingredients);

        orderid.setText(_listDataHeader.get(groupPosition).getTag_en());


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
