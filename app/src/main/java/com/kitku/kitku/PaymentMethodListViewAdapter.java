package com.kitku.kitku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class PaymentMethodListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> methodGroupName;
    private HashMap<String, PaymentMethodChildDataModel> listViewDatas;

    public PaymentMethodListViewAdapter(Context context, List<String> methodGroupName,
                                        HashMap<String, PaymentMethodChildDataModel> listViewDatas) {
        this.context = context;
        this.methodGroupName = methodGroupName;
        this.listViewDatas = listViewDatas;
    }

    @Override
    public int getGroupCount() {
        return this.methodGroupName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.methodGroupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listViewDatas.get(this.methodGroupName.get(groupPosition));
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
            view = layoutInflater.inflate(R.layout.payment_method_group_layout, parent, false);
        }

        TextView methodGroupName = view.findViewById(R.id.textNamePaymentGroup);
        methodGroupName.setText(groupName);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup parent) {

        PaymentMethodChildDataModel methodChildDataModel = (PaymentMethodChildDataModel) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.payment_method_item_layout, parent, false);
        }

        ImageView methodChildImage = view.findViewById(R.id.imageLogoPaymentItem);
        TextView methodChildCode = view.findViewById(R.id.textPaymentCodeItem);
        TextView methodChildAccount = view.findViewById(R.id.textPaymentAccountNumberItem);
        TextView methodChildPrice = view.findViewById(R.id.textPaymentTotalPriceItem);

        methodChildImage.setImageResource(methodChildDataModel.getMethodChildImage());
        methodChildCode.setText(methodChildDataModel.getMethodChildCode());
        methodChildAccount.setText(methodChildDataModel.getMethodChildAccount());
        methodChildPrice.setText(methodChildDataModel.getMethodChildPrice());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
