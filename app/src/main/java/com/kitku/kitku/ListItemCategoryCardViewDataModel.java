package com.kitku.kitku;

import android.graphics.Bitmap;

public class ListItemCategoryCardViewDataModel {

    /* POJO dan Model Data untuk Banner Slider pada Main Activity  */

    private Bitmap image_category_item;
    private String text_name_category_item;
    private String text_price_category_item;
    private String text_pack_category_item;

    /* Constructor untuk Data Model yang akan dipakai pada ListItem */

    public ListItemCategoryCardViewDataModel(Bitmap image_category_item, String text_name_category_item, String text_price_category_item, String text_pack_category_item) {
        this.image_category_item = image_category_item;
        this.text_name_category_item = text_name_category_item;
        this.text_price_category_item = text_price_category_item;
        this.text_pack_category_item = text_pack_category_item;
    }

    public Bitmap getImage_category_item() {
        return image_category_item;
    }

    public String getText_name_category_item() {
        return text_name_category_item;
    }

    public String getText_price_category_item() {
        return text_price_category_item;
    }

    public String getText_pack_category_item() {
        return text_pack_category_item;
    }

    public void setImage_category_item(Bitmap image_category_item) {
        this.image_category_item = image_category_item;
    }

    public void setText_name_category_item(String text_name_category_item) {
        this.text_name_category_item = text_name_category_item;
    }

    public void setText_price_category_item(String text_price_category_item) {
        this.text_price_category_item = text_price_category_item;
    }

    public void setText_pack_category_item(String text_pack_category_item) {
        this.text_pack_category_item = text_pack_category_item;
    }
}
