package com.kitku.kitku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private ExpandableListView listViewMethod, listViewStep;
    private Button buttonToTracking;

    private PaymentMethodListViewAdapter paymentMethodAdapter;
    private PaymentStepListViewAdapter paymentStepAdapter;

    private List<String> methodGroupNameList;
    private List<String> stepGroupNameList;

    private HashMap<String, PaymentMethodChildDataModel> methodListDatas;
    private HashMap<String, PaymentStepChildDataModel> stepListDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        listViewMethod = findViewById(R.id.listviewPayment_PaymentMethod);
        listViewStep = findViewById(R.id.listviewPayment_PaymentSteps);
        buttonToTracking = findViewById(R.id.buttonPaymentGoToTracking);

        this.methodListDatas = getMethodData();
        this.stepListDatas = getStepData();

        this.methodGroupNameList = new ArrayList<>(methodListDatas.keySet());
        this.stepGroupNameList = new ArrayList<>(stepListDatas.keySet());

        this.paymentMethodAdapter = new PaymentMethodListViewAdapter(this, methodGroupNameList, methodListDatas);
        this.paymentStepAdapter = new PaymentStepListViewAdapter(this, stepGroupNameList, stepListDatas);

        listViewMethod.setAdapter(paymentMethodAdapter);
        listViewStep.setAdapter(paymentStepAdapter);

        listViewMethod.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

        listViewStep.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });
    }

    public void setCancelButton(View view) {
    }

    private HashMap<String, PaymentMethodChildDataModel> getMethodData() {

        HashMap<String, PaymentMethodChildDataModel> methodListData = new HashMap<>();

        methodListData.put("Bank Mandiri", new PaymentMethodChildDataModel
                (R.drawable.payment_mandiri_image, "123456789012345",
                        "123-4567-890", "Rp. 30.000"));
        methodListData.put("Bank Nagari", new PaymentMethodChildDataModel
                (R.drawable.payment_nagari_image, "234567890123456",
                        "234-5678-901", "Rp.30.000"));
        methodListData.put("Bank BCA", new PaymentMethodChildDataModel
                (R.drawable.payment_bca_image, "345678901234567",
                        "345-6789-012", "Rp.30.000"));

        return methodListData;
    }

    private HashMap<String, PaymentStepChildDataModel> getStepData() {

        HashMap<String, PaymentStepChildDataModel> stepListData = new HashMap<>();

        stepListData.put("ATM", new PaymentStepChildDataModel
                        (R.drawable.payment_atm_icon,
                        "1.  Catat kode pembayaran anda",
                        "2.  Gunakan ATM Mandiri untuk menyelesaikan pembayaran",
                        "3.  Masukkan PIN kartu ATM",
                        "4.  Pilih \''Bayar/Beli\''",
                        "5.  Cari pilihan \''Multi Payment\''",
                        "6.  Masukkan kode perusahaan 12345",
                        "7.  Masukkan kode pembayaran",
                        "8.  Masukkan jumlah pembayaran sesuai dengan jumlah tagihan anda kemudian tekan \''Benar\''",
                        "9.  Pilih tagihan anda jika sudah sesuai dan tekan \''Ya\''",
                        "10.  Konfirmasukan tagihan anda apakah sudah sesuai lalu tekan \''Ya\''",
                        "11.  Harap simpan struk transaksi setelah pembayaran"));

        stepListData.put("Internet Banking", new PaymentStepChildDataModel
                (R.drawable.payment_mobile_bank_icon,
                        "1.  Catat kode pembayaran anda",
                        "2.  Gunakan ATM Mandiri untuk menyelesaikan pembayaran",
                        "3.  Masukkan PIN kartu ATM",
                        "4.  Pilih \''Bayar/Beli\''",
                        "5.  Cari pilihan \''Multi Payment\''",
                        "6.  Masukkan kode perusahaan 12345",
                        "7.  Masukkan kode pembayaran",
                        "8.  Masukkan jumlah pembayaran sesuai dengan jumlah tagihan anda kemudian tekan \''Benar\''",
                        "9.  Pilih tagihan anda jika sudah sesuai dan tekan \''Ya\''",
                        "10.  Konfirmasukan tagihan anda apakah sudah sesuai lalu tekan \''Ya\''",
                        "11.  Harap simpan struk transaksi setelah pembayaran"));

        return stepListData;
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

    public void goToTrackingClick(View view) {
        Intent intent = new Intent(PaymentActivity.this, OrderTrackingActivity.class);
        startActivity(intent);
    }
}
