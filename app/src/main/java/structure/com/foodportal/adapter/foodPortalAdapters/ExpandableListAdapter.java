package structure.com.foodportal.adapter.foodPortalAdapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.HeaderWrapper;

import static structure.com.foodportal.helper.AppConstant.FOODPORTAL_FOOD_DETAILS.BLOG;
import static structure.com.foodportal.helper.AppConstant.FOODPORTAL_FOOD_DETAILS.RECIPES;
import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private BasePreferenceHelper preferenceHelper;
    private ArrayList<HeaderWrapper> headerWrapper;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<CategorySlider>> _listDataChild;
    ExpandableListView expandableListView;

    public ExpandableListAdapter(Context context, BasePreferenceHelper preferenceHelper,
                                 ArrayList<HeaderWrapper> headerWrapper, List<String> listDataHeader,
                                 HashMap<String, List<CategorySlider>> listChildData,
                                 ExpandableListView expandableListView) {
        this._context = context;
        this.preferenceHelper = preferenceHelper;
        this.headerWrapper = headerWrapper;
        this.expandableListView = expandableListView;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final CategorySlider childText = (CategorySlider) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        int[] attrs = new int[]{android.R.attr.expandableListPreferredChildPaddingLeft};
        TypedArray ta = _context.obtainStyledAttributes(attrs);
        int leftPadding = (int) ta.getDimension(0, 0);
        ta.recycle();

        switch (preferenceHelper.getSelectedLanguage()) {
            case ENGLISH:
            default:
                txtListChild.setText(childText.getCategory_title_en());
                break;

            case URDU:
                txtListChild.setPadding(0, 5, leftPadding, 5);
                txtListChild.setText(childText.getCategory_title_ur());
                break;
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        LinearLayout linearLayoutGroup = (LinearLayout) convertView.findViewById(R.id.linearLayoutGroup);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        ImageView etvexpand = (ImageView) convertView.findViewById(R.id.etvexpand);

        linearLayoutGroup.setLayoutDirection(preferenceHelper.getSelectedLanguage() == ENGLISH ? View.LAYOUT_DIRECTION_LTR : View.LAYOUT_DIRECTION_RTL);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.replace("سبق", _context.getString(R.string.tutorials_ur)));

        if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(RECIPES)) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH)
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_menu_recipe, 0, 0, 0);
            else if (preferenceHelper.getSelectedLanguage() == URDU)
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_menu_recipe, 0);
        }
        if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase("Tutorial")) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH)
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_menu_tutorial, 0, 0, 0);
            else if (preferenceHelper.getSelectedLanguage() == URDU)
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_menu_tutorial, 0);
        }
        if (headerWrapper.get(groupPosition).getSlug().equalsIgnoreCase(BLOG)) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH)
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_menu_cleaning, 0, 0, 0);
            else if (preferenceHelper.getSelectedLanguage() == URDU)
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_menu_cleaning, 0);
        }

        if (_listDataChild.get(getGroup(groupPosition)).size() == 0) {


            etvexpand.setVisibility(View.GONE);

        } else {

            etvexpand.setVisibility(View.VISIBLE);
        }

        etvexpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isExpanded) {
                    expandableListView.expandGroup(groupPosition);
                } else {
                    expandableListView.collapseGroup(groupPosition);
                }

            }
        });

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