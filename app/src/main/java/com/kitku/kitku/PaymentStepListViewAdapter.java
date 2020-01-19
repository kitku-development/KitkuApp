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

public class PaymentStepListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> stepGroupName;
    private HashMap<String, PaymentStepChildDataModel> listViewDatas;

    public PaymentStepListViewAdapter(Context context, List<String> stepGroupName, HashMap<String,
            PaymentStepChildDataModel> listViewDatas) {
        this.context = context;
        this.stepGroupName = stepGroupName;
        this.listViewDatas = listViewDatas;
    }

    @Override
    public int getGroupCount() {
        return this.stepGroupName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.stepGroupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listViewDatas.get(this.stepGroupName.get(groupPosition));
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
        PaymentStepChildDataModel stepChildDataModel = (PaymentStepChildDataModel) getChild(groupPosition,0);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.payment_step_group_layout, parent, false);
        }

        ImageView stepGroupImage = view.findViewById(R.id.imagePaymentStepGroup);
        TextView stepGroupName = view.findViewById(R.id.textNamePaymentStepGroup);

        stepGroupImage.setImageResource(stepChildDataModel.getStepGroupImage());
        stepGroupName.setText(groupName);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup parent) {

        PaymentStepChildDataModel stepChildDataModel = (PaymentStepChildDataModel) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.payment_step_item_layout, parent, false);
        }

        TextView stepChildText1 = view.findViewById(R.id.textPaymentStepItem1);
        TextView stepChildText2 = view.findViewById(R.id.textPaymentStepItem2);
        TextView stepChildText3 = view.findViewById(R.id.textPaymentStepItem3);
        TextView stepChildText4 = view.findViewById(R.id.textPaymentStepItem4);
        TextView stepChildText5 = view.findViewById(R.id.textPaymentStepItem5);
        TextView stepChildText6 = view.findViewById(R.id.textPaymentStepItem6);
        TextView stepChildText7 = view.findViewById(R.id.textPaymentStepItem7);
        TextView stepChildText8 = view.findViewById(R.id.textPaymentStepItem8);
        TextView stepChildText9 = view.findViewById(R.id.textPaymentStepItem9);
        TextView stepChildText10 = view.findViewById(R.id.textPaymentStepItem10);
        TextView stepChildText11 = view.findViewById(R.id.textPaymentStepItem11);

        stepChildText1.setText(stepChildDataModel.getStepChildText1());
        stepChildText2.setText(stepChildDataModel.getStepChildText2());
        stepChildText3.setText(stepChildDataModel.getStepChildText3());
        stepChildText4.setText(stepChildDataModel.getStepChildText4());
        stepChildText5.setText(stepChildDataModel.getStepChildText5());
        stepChildText6.setText(stepChildDataModel.getStepChildText6());
        stepChildText7.setText(stepChildDataModel.getStepChildText7());
        stepChildText8.setText(stepChildDataModel.getStepChildText8());
        stepChildText9.setText(stepChildDataModel.getStepChildText9());
        stepChildText10.setText(stepChildDataModel.getStepChildText10());
        stepChildText11.setText(stepChildDataModel.getStepChildText11());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
