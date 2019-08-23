package com.kitku.kitku.Cart.Cart_Order;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitku.kitku.R;
import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;

import java.util.ArrayList;

public class Cart_Order_OrderListCardViewAdapter
        extends RecyclerView.Adapter<Cart_Order_OrderListCardViewAdapter.OrderListViewHolder> {

    private ArrayList<Cart_Order_OrderListCardViewDataModel> orderListDataModel;
    private int defaultAmount = 1;

    public void removeItem (int position) {
        orderListDataModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderListDataModel.size());

    }

    public Cart_Order_OrderListCardViewAdapter(ArrayList<Cart_Order_OrderListCardViewDataModel> orderListDataModel) {
        this.orderListDataModel = orderListDataModel;
    }

    @NonNull
    @Override
    public Cart_Order_OrderListCardViewAdapter.OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cartorder_orderlistitem_rv_layout, viewGroup, false);
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Cart_Order_OrderListCardViewAdapter.OrderListViewHolder orderListViewHolder, final int position) {
        String priceString = "Rp. " + orderListDataModel.get(position).getText_price_orderlist_item();
        //String priceString = orderListDataModel.get(position).getText_price_orderlist_item();
        //orderListViewHolder.itemImage.setImageResource(orderListDataModel.get(position).getImage_orderlist_item());
        orderListViewHolder.itemImage.setImageBitmap(orderListDataModel.get(position).getImage_orderlist_item());
        orderListViewHolder.textItemName.setText(orderListDataModel.get(position).getText_name_orderlist_item());
        orderListViewHolder.textItemPrice.setText(priceString);
        orderListViewHolder.textItemPack.setText(orderListDataModel.get(position).getText_pack_orderlist_item());
        orderListViewHolder.textTotalPrice = orderListDataModel.get(position).getTotalPriceText();
        orderListViewHolder.productPrice = orderListDataModel.get(position).getProductPrice();
        orderListViewHolder.id_barang = orderListDataModel.get(position).getId_barang();
        orderListViewHolder.c = orderListDataModel.get(position).getContext();
        orderListViewHolder.textItemAmount.setText(orderListDataModel.get(position).getQuantity());

        orderListViewHolder.textItemAmount.setText(String.valueOf(defaultAmount));

        orderListViewHolder.imageButtonAddAmountItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                orderListDataModel.get(position).setOrderAmount(orderListDataModel.get(position).getOrderAmount()+1);
                orderListViewHolder.textItemAmount.setText(String.valueOf(orderListDataModel.get(position).getOrderAmount()));

                if (orderListDataModel.get(position).getOrderAmount() == 0) {
                    removeItem(position);
                }
            }
        });

        orderListViewHolder.imageButtonRemoveAmountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListDataModel.get(position).setOrderAmount(orderListDataModel.get(position).getOrderAmount()-1);
                orderListViewHolder.textItemAmount.setText(String.valueOf(orderListDataModel.get(position).getOrderAmount()));

                if (orderListDataModel.get(position).getOrderAmount() <= 0) {
                    removeItem(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderListDataModel.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        ImageButton imageButtonAddAmountItem, imageButtonRemoveAmountItem;
        ImageView itemImage;
        TextView textItemName, textItemPrice, textItemPack, textItemAmount, textTotalPrice;
        int itemAmount;
        int productPrice;
        String id_barang;
        Context c;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButtonAddAmountItem = itemView.findViewById(R.id.imagebtnaddorderOrderListItem);
            imageButtonRemoveAmountItem = itemView.findViewById(R.id.imagebtnremoveorderOrderListItem);
            itemImage = itemView.findViewById(R.id.imageOrderListItem);
            textItemName = itemView.findViewById(R.id.textnameOrderListItem);
            textItemPrice = itemView.findViewById(R.id.textpriceOrderListItem);
            textItemPack = itemView.findViewById(R.id.textpackOrderListItem);
            textItemAmount = itemView.findViewById(R.id.textorderamountOrderListItem);

            // ubah teks toal harga ketika jumlah item berubah

            textItemAmount.addTextChangedListener(new TextWatcher() {
                int oldQuantity, newQuantity, oldPrice;
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    oldQuantity = Integer.valueOf(textItemAmount.getText().toString());
                    //Log.d("oldq",String.valueOf(oldQuantity));
                    if (!textTotalPrice.getText().toString().equals("Rp. 0"))
                        oldPrice = Integer.valueOf(textTotalPrice.getText().toString().replace("Rp. ", ""));
                    else
                        oldPrice = 0;
                    //Log.d("oldp",String.valueOf(oldPrice));
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    newQuantity  = Integer.valueOf(textItemAmount.getText().toString());
                    itemAmount   = newQuantity;
                    int oldTotalProductPrice = oldQuantity * productPrice;
                    int newTotalProductPrice = newQuantity * productPrice;
                    int newPrice = oldPrice + newTotalProductPrice - oldTotalProductPrice;
                    String newPriceString = "Rp. " + newPrice;
                    textTotalPrice.setText(newPriceString);

                    // update data cart sharedpreference
                    try {
                        new z_BackendPreProcessing().addItemToCart(
                                id_barang,
                                String.valueOf(itemAmount),
                                String.valueOf(productPrice),
                                c,
                                textItemName.getText().toString(),
                                "1",
                                textItemPack.getText().toString());
                    } catch (Exception e) { e.printStackTrace(); }
                }
            });
        }
    }
}
