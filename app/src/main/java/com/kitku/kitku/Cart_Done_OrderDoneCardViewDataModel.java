package com.kitku.kitku;

public class Cart_Done_OrderDoneCardViewDataModel {

    private int image_donelist_item;
    private String text_name_donelist_item;
    private String text_info_donelist_item;

    public Cart_Done_OrderDoneCardViewDataModel(int image_donelist_item, String text_name_donelist_item, String text_info_donelist_item) {
        this.image_donelist_item = image_donelist_item;
        this.text_name_donelist_item = text_name_donelist_item;
        this.text_info_donelist_item = text_info_donelist_item;
    }

    public int getImage_donelist_item() {
        return image_donelist_item;
    }

    public String getText_name_donelist_item() {
        return text_name_donelist_item;
    }

    public String getText_info_donelist_item() {
        return text_info_donelist_item;
    }

    public void setImage_donelist_item(int image_donelist_item) {
        this.image_donelist_item = image_donelist_item;
    }

    public void setText_name_donelist_item(String text_name_donelist_item) {
        this.text_name_donelist_item = text_name_donelist_item;
    }

    public void setText_info_donelist_item(String text_info_donelist_item) {
        this.text_info_donelist_item = text_info_donelist_item;
    }
}
