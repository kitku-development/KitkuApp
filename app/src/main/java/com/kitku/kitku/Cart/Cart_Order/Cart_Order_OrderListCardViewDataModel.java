package com.kitku.kitku.Cart.Cart_Order;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

public class Cart_Order_OrderListCardViewDataModel {

    //private int image_orderlist_item;
    private Bitmap image_orderlist_item;
    private String text_name_orderlist_item;
    private String text_price_orderlist_item;
    private String text_pack_orderlist_item;
    private int orderAmount = 1, productPrice;
    private TextView totalPriceText;
    private String id_barang, q;
    private Context context;

    public Cart_Order_OrderListCardViewDataModel(Bitmap image_orderlist_item, String text_name_orderlist_item, String text_price_orderlist_item, String text_pack_orderlist_item, TextView view, String id, Context c, String q) {
    //public Cart_Order_OrderListCardViewDataModel(int image_orderlist_item, String text_name_orderlist_item, String text_price_orderlist_item, String text_pack_orderlist_item) {
        this.image_orderlist_item = image_orderlist_item;
        this.text_name_orderlist_item = text_name_orderlist_item;
        this.text_price_orderlist_item = text_price_orderlist_item;
        this.text_pack_orderlist_item = text_pack_orderlist_item;
        this.totalPriceText = view;
        this.productPrice = Integer.valueOf(text_price_orderlist_item);
        this.id_barang = id;
        this.context = c;
        this.q = q;
    }

    /*public int getImage_orderlist_item() {
        return image_orderlist_item;
    }*/

    public Bitmap getImage_orderlist_item() {
        return image_orderlist_item;
    }

    public String getText_name_orderlist_item() {
        return text_name_orderlist_item;
    }

    public String getText_price_orderlist_item() {
        return text_price_orderlist_item;
    }

    public String getText_pack_orderlist_item() {
        return text_pack_orderlist_item;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public TextView getTotalPriceText() { return totalPriceText; }

    public int getProductPrice() { return productPrice; }

    public String getId_barang() { return id_barang; }

    public Context getContext() { return context; }

    public String getQuantity() { return q; }

    public void setImage_orderlist_item(Bitmap image_orderlist_item) {
        this.image_orderlist_item = image_orderlist_item;
    }

    public void setText_name_orderlist_item(String text_name_orderlist_item) {
        this.text_name_orderlist_item = text_name_orderlist_item;
    }

    public void setText_price_orderlist_item(String text_price_orderlist_item) {
        this.text_price_orderlist_item = text_price_orderlist_item;
    }

    public void setText_pack_orderlist_item(String text_pack_orderlist_item) {
        this.text_pack_orderlist_item = text_pack_orderlist_item;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
