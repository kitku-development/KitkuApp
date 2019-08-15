package com.kitku.kitku;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
                .inflate(R.layout.cartorder_orderlistitem_recyclerview_layout, viewGroup, false);
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Cart_Order_OrderListCardViewAdapter.OrderListViewHolder orderListViewHolder, final int position) {

        orderListViewHolder.itemImage.setImageResource(orderListDataModel.get(position).getImage_orderlist_item());
        orderListViewHolder.textItemName.setText(orderListDataModel.get(position).getText_name_orderlist_item());
        orderListViewHolder.textItemPrice.setText(orderListDataModel.get(position).getText_price_orderlist_item());
        orderListViewHolder.textItemPack.setText(orderListDataModel.get(position).getText_pack_orderlist_item());

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
        TextView textItemName, textItemPrice, textItemPack, textItemAmount;


        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButtonAddAmountItem = itemView.findViewById(R.id.imagebtnaddorderOrderListItem);
            imageButtonRemoveAmountItem = itemView.findViewById(R.id.imagebtnremoveorderOrderListItem);
            itemImage = itemView.findViewById(R.id.imageOrderListItem);
            textItemName = itemView.findViewById(R.id.textnameOrderListItem);
            textItemPrice = itemView.findViewById(R.id.textpriceOrderListItem);
            textItemPack = itemView.findViewById(R.id.textpackOrderListItem);
            textItemAmount = itemView.findViewById(R.id.textorderamountOrderListItem);
        }
    }
}
