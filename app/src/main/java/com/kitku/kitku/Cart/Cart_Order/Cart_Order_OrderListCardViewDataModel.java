package com.kitku.kitku.Cart.Cart_Order;

public class Cart_Order_OrderListCardViewDataModel {

    private int image_orderlist_item;
    private String text_name_orderlist_item;
    private String text_price_orderlist_item;
    private String text_pack_orderlist_item;
    private int orderAmount = 1;


    public Cart_Order_OrderListCardViewDataModel(int image_orderlist_item, String text_name_orderlist_item, String text_price_orderlist_item, String text_pack_orderlist_item) {
        this.image_orderlist_item = image_orderlist_item;
        this.text_name_orderlist_item = text_name_orderlist_item;
        this.text_price_orderlist_item = text_price_orderlist_item;
        this.text_pack_orderlist_item = text_pack_orderlist_item;
    }

    public int getImage_orderlist_item() {
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

    public void setImage_orderlist_item(int image_orderlist_item) {
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
