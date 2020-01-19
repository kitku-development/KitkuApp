package com.kitku.kitku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity {

    private ExpandableListView listViewSummary, listViewAddress, listViewTime;

    private OrderTrackingSummaryListViewAdapter orderSummaryAdapter;
    private OrderTrackingAddressListViewAdapter orderAddressAdapter;
    private OrderTrackingTimeListViewAdapter orderTimeAdapter;

    private List<String> summaryGroupNameList;
    private List<String> addressGroupNameList;
    private List<String> timeGroupNameList;

    private HashMap<String, OrderTrackingSummaryChildDataModel> summaryListDatas;
    private HashMap<String, OrderTrackingAddressChildDataModel> addressListDatas;
    private HashMap<String , OrderTrackingTimeChildDataModel> timeListDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        listViewSummary = findViewById(R.id.listviewOrderTracking_OrderSummary);
        listViewAddress = findViewById(R.id.listviewOrderTracking_OrderAddress);
        listViewTime = findViewById(R.id.listviewOrderTracking_OrderTime);

        this.summaryListDatas = getSummaryData();
        this.addressListDatas = getAddressData();
        this.timeListDatas = getTimeData();

        this.summaryGroupNameList = new ArrayList<>(summaryListDatas.keySet());
        this.addressGroupNameList = new ArrayList<>(addressListDatas.keySet());
        this.timeGroupNameList = new ArrayList<>(timeListDatas.keySet());

        this.orderSummaryAdapter = new OrderTrackingSummaryListViewAdapter(this, summaryGroupNameList, summaryListDatas);
        this.orderAddressAdapter = new OrderTrackingAddressListViewAdapter(this, addressGroupNameList, addressListDatas);
        this.orderTimeAdapter = new OrderTrackingTimeListViewAdapter(this, timeGroupNameList, timeListDatas);

        listViewSummary.setAdapter(orderSummaryAdapter);
        listViewAddress.setAdapter(orderAddressAdapter);
        listViewTime.setAdapter(orderTimeAdapter);

        listViewSummary.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });
        listViewAddress.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });
        listViewTime.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

    }

    private HashMap<String, OrderTrackingSummaryChildDataModel> getSummaryData() {

        HashMap<String, OrderTrackingSummaryChildDataModel> summaryListData = new HashMap<>();

        summaryListData.put("Ringkasan Pesanan", new OrderTrackingSummaryChildDataModel
                ("Salak",
                 "2 x",
                 "Rp 30.000",
                 "Rp 10.000",
                 "Rp 20.000",
                 "432",
                 "Rp 40.432"));

        return summaryListData;
    }

    private HashMap<String, OrderTrackingAddressChildDataModel> getAddressData() {

        HashMap<String, OrderTrackingAddressChildDataModel> addressListData = new HashMap<>();

        addressListData.put("Alamat Pengiriman", new OrderTrackingAddressChildDataModel
                ("Evan Edsa Azola",
                 "082175719430",
                 "Komplek Filano Jaya I, Kubu Parak Karakah",
                 "Padang Timur, Padang",
                 "Sumatera Barat"));

        return addressListData;
    }

    private HashMap<String, OrderTrackingTimeChildDataModel> getTimeData() {

        HashMap<String, OrderTrackingTimeChildDataModel> timeListData = new HashMap<>();

        timeListData.put("Tanggal dan Waktu Pengiriman", new OrderTrackingTimeChildDataModel
                ("Sabtu 14 Desember 2019",
                 "Sore (ETA. 17:00 - 20:00 WIB)"));

        return timeListData;
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter adapter = (ExpandableListAdapter) listView.getExpandableListAdapter();

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);

        for (int i=0; i < adapter.getGroupCount(); i++) {
            View groupItem = adapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i))&& (i != group)) || ((!listView.isGroupExpanded(i))&& (i == group))) {
                for (int j=0; j < adapter.getChildrenCount(i); j++) {
                    View listItem = adapter.getChildView(i, j, false, null, listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (adapter.getGroupCount() -1));

        if (height < 10) {
            height = 200;
        }

        layoutParams.height = height;
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();

    }


}
