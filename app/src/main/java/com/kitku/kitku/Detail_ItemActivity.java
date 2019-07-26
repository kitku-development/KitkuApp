package com.kitku.kitku;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Detail_ItemActivity extends AppCompatActivity {

    Button buttonDetail_ItemBuyItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__item);

        buttonDetail_ItemBuyItem = findViewById(R.id.buttonDetail_Item_BuyItem);

    }

    public void buttonBuyItem(View view) {
        final Dialog dialog_detail_item_buyitem = new Dialog(this);
        dialog_detail_item_buyitem.setContentView(R.layout.dialog_detail_item_buyitem);

        Button buttonDialoDetail_Item_Back = dialog_detail_item_buyitem.findViewById(R.id.buttonDialogDetail_Item_Back);
        buttonDialoDetail_Item_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_detail_item_buyitem.dismiss();
            }
        });

        dialog_detail_item_buyitem.setCancelable(false);
        dialog_detail_item_buyitem.setCanceledOnTouchOutside(false);
        dialog_detail_item_buyitem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog_detail_item_buyitem.show();
    }
}
