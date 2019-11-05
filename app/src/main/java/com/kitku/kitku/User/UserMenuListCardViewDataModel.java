package com.kitku.kitku.User;

import android.view.View;

public class UserMenuListCardViewDataModel {

    private String text_name_list_menu_item, tag;
    private View listView;

    public UserMenuListCardViewDataModel(String text_name_list_menu_item, View listView, String tag) {
        this.text_name_list_menu_item = text_name_list_menu_item;
        this.listView = listView;
        this.tag = tag;
    }

    public String getText_name_list_menu_item() {
        return text_name_list_menu_item;
    }

    /*public void setText_name_list_menu_item(String text_name_list_menu_item) {
        this.text_name_list_menu_item = text_name_list_menu_item;
    }*/

    View getListView() {
        return listView;
    }

    String getTag() {
        return tag;
    }
}
