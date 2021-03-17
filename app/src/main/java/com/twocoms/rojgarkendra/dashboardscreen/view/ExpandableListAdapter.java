package com.twocoms.rojgarkendra.dashboardscreen.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.model.NavMenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
//    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
//    private HashMap<String, List<String>> _listDataChild;
    public ArrayList<NavMenuModel> allMenus;

    public ExpandableListAdapter(Context _context, ArrayList<NavMenuModel> allMenus) {
        this._context = _context;
        this.allMenus = allMenus;
//        this._listDataHeader = _listDataHeader;
//        this._listDataChild = _listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return allMenus.get(groupPosition).getAllSubMenu().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        NavMenuModel childData = (NavMenuModel) getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        ImageView imageView = convertView.findViewById(R.id.child_icon);

        imageView.setImageResource(childData.getIconImg());

        txtListChild.setText(childData.getIconName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        if (this._listDataChild.get(
//                this._listDataHeader.get(groupPosition)) == null) {
//            return 0;
//
//        } else {
//            return this._listDataChild.get(
//                    this._listDataHeader.get(groupPosition)).size();
//        }

        return allMenus.get(groupPosition).getAllSubMenu().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.allMenus.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.allMenus.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        NavMenuModel headerTitle = (NavMenuModel) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.grpItem);
        lblListHeader.setText(headerTitle.getIconName());

        ImageView imageView = convertView.findViewById(R.id.header_icon);

        //imageView.setImageResource(headerTitle.getIconImg());

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
