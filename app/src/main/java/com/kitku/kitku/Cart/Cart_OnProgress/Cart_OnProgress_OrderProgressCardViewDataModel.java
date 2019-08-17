package com.kitku.kitku.Cart.Cart_OnProgress;

public class Cart_OnProgress_OrderProgressCardViewDataModel {

    private int image_onprogresslist_item;
    private String text_name_onprogresslist_item;
    private String text_progress_onprogresslist_item;

    public Cart_OnProgress_OrderProgressCardViewDataModel(int image_onprogresslist_item, String text_name_onprogresslist_item, String text_progress_onprogresslist_item) {
        this.image_onprogresslist_item = image_onprogresslist_item;
        this.text_name_onprogresslist_item = text_name_onprogresslist_item;
        this.text_progress_onprogresslist_item = text_progress_onprogresslist_item;
    }

    public String getText_name_onprogresslist_item() {
        return text_name_onprogresslist_item;
    }

    public String getText_progress_onprogresslist_item() {
        return text_progress_onprogresslist_item;
    }

    public int getImage_onprogresslist_item() {
        return image_onprogresslist_item;
    }

    public void setText_name_onprogresslist_item(String text_name_onprogresslist_item) {
        this.text_name_onprogresslist_item = text_name_onprogresslist_item;
    }

    public void setText_progress_onprogresslist_item(String text_progress_onprogresslist_item) {
        this.text_progress_onprogresslist_item = text_progress_onprogresslist_item;
    }

    public void setImage_onprogresslist_item(int image_onprogresslist_item) {
        this.image_onprogresslist_item = image_onprogresslist_item;
    }
}
