package com.kitku.kitku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class OrderTrackingTimeListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> timeGroupName;
    private HashMap<String, OrderTrackingTimeChildDataModel> listViewDatas;

    public OrderTrackingTimeListViewAdapter(Context context, List<String> timeGroupName,
                                            HashMap<String, OrderTrackingTimeChildDataModel> listViewDatas) {
        this.context = context;
        this.timeGroupName = timeGroupName;
        this.listViewDatas = listViewDatas;
    }

    @Override
    public int getGroupCount() {
        return timeGroupName.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.timeGroupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listViewDatas.get(this.timeGroupName.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup parent) {

        String groupName = (String) getGroup(groupPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.ordertracking_ordertime_group_layout, parent, false);
        }

        TextView timeGroupName = view.findViewById(R.id.textNameOrderTimeGroup);
        timeGroupName.setText(groupName);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup parent) {

        OrderTrackingTimeChildDataModel timeChildDataModel = (OrderTrackingTimeChildDataModel)
                getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.ordertracking_ordertime_item_layout, parent, false);
        }

        TextView timeChildDate = view.findViewById(R.id.textOrderTimeDatetimeItem);
        TextView timeChildTime = view.findViewById(R.id.textOrderTime_TimeItem);

        timeChildDate.setText(timeChildDataModel.getTimeChildDate());
        timeChildTime.setText(timeChildDataModel.getTimeChildTime());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
