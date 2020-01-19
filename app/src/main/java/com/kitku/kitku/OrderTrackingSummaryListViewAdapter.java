package com.kitku.kitku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class OrderTrackingSummaryListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> summaryGroupName;
    private HashMap<String, OrderTrackingSummaryChildDataModel> listViewDatas;

    public OrderTrackingSummaryListViewAdapter(Context context, List<String> summaryGroupName,
                                               HashMap<String, OrderTrackingSummaryChildDataModel> listViewDatas) {
        this.context = context;
        this.summaryGroupName = summaryGroupName;
        this.listViewDatas = listViewDatas;
    }

    @Override
    public int getGroupCount() {
        return this.summaryGroupName.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.summaryGroupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listViewDatas.get(this.summaryGroupName.get(groupPosition));
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
            view = layoutInflater.inflate(R.layout.ordertracking_ordersummary_group_layout, parent, false);
        }

        TextView summaryGroupName = view.findViewById(R.id.textNameOrderSummaryGroup);
        summaryGroupName.setText(groupName);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup parent) {

        OrderTrackingSummaryChildDataModel summaryChildDataModel = (OrderTrackingSummaryChildDataModel)
                getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.ordertracking_ordersummary_item_layout, parent, false);
        }

        TextView summaryChildName = view.findViewById(R.id.textOrderSummaryNameItem);
        TextView summaryChildAmount = view.findViewById(R.id.textOrderSummaryAmountItem);
        TextView summaryChildItemPrice = view.findViewById(R.id.textOrderSummaryItemPriceItem);
        TextView summaryChildDiscountPrice = view.findViewById(R.id.textOrderSummaryDiscountPriceItem);
        TextView summaryChildDeliveryPrice = view.findViewById(R.id.textOrderSummaryDeliveryPriceItem);
        TextView summaryChildUniqueCode = view.findViewById(R.id.textOrderSummaryUniqueCodeItem);
        TextView summaryChildTotalPrice = view.findViewById(R.id.textOrderSummaryTotalPriceItem);

        summaryChildName.setText(summaryChildDataModel.getSummaryChildName());
        summaryChildAmount.setText(summaryChildDataModel.getSummaryChildAmount());
        summaryChildItemPrice.setText(summaryChildDataModel.getSummaryChildItemPrice());
        summaryChildDiscountPrice.setText(summaryChildDataModel.getSummaryChildDiscountPrice());
        summaryChildDeliveryPrice.setText(summaryChildDataModel.getSummaryChildDeliveryPrice());
        summaryChildUniqueCode.setText(summaryChildDataModel.getSummaryChildUniqueCode());
        summaryChildTotalPrice.setText(summaryChildDataModel.getSummaryChildTotalPrice());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
