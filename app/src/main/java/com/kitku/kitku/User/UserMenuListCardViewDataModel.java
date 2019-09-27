package com.kitku.kitku.User;

import android.view.View;

public class UserMenuListCardViewDataModel {

    private String text_name_list_menu_item;
    private View listView;

    public UserMenuListCardViewDataModel(String text_name_list_menu_item, View listView) {
        this.text_name_list_menu_item = text_name_list_menu_item;
        this.listView = listView;
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
}
