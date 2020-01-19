package com.kitku.kitku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class OrderTrackingAddressListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> addressGroupName;
    private HashMap<String, OrderTrackingAddressChildDataModel> listViewDatas;

    public OrderTrackingAddressListViewAdapter(Context context, List<String> addressGroupName,
                                               HashMap<String, OrderTrackingAddressChildDataModel> listViewDatas) {
        this.context = context;
        this.addressGroupName = addressGroupName;
        this.listViewDatas = listViewDatas;
    }

    @Override
    public int getGroupCount() {
        return addressGroupName.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.addressGroupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listViewDatas.get(this.addressGroupName.get(groupPosition));
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
            view = layoutInflater.inflate(R.layout.ordertracking_orderaddress_group_layout, parent, false);
        }

        TextView addressGroupName = view.findViewById(R.id.textNameOrderAddressGroup);
        addressGroupName.setText(groupName);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup parent) {

        OrderTrackingAddressChildDataModel addressChildDataModel = (OrderTrackingAddressChildDataModel)
                getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.ordertracking_orderaddress_item_layout, parent, false);
        }

        TextView addressChildName = view.findViewById(R.id.textOrderAddressNameItem);
        TextView addressChildPhone = view.findViewById(R.id.textOrderAddressPhoneItem);
        TextView addressChildAddressHouse = view.findViewById(R.id.textOrderAddress_AddressItem1);
        TextView addressChildAddressCity = view.findViewById(R.id.textOrderAddress_AddressItem2);
        TextView addressChildAddressProvince = view.findViewById(R.id.textOrderAddress_AddressItem3);

        addressChildName.setText(addressChildDataModel.getAddressChildName());
        addressChildPhone.setText(addressChildDataModel.getAddressChildPhone());
        addressChildAddressHouse.setText(addressChildDataModel.getAddressChildAddressHouse());
        addressChildAddressCity.setText(addressChildDataModel.getAddressChildAddressCity());
        addressChildAddressProvince.setText(addressChildDataModel.getAddressChildAddressProvince());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
