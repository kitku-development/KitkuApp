package com.kitku.kitku;

import android.view.View;

import androidx.fragment.app.Fragment;

class UserMenuModel {

    private String menuText, menuMessage, tag;
    private Fragment fragmentView;
    private View upperBorder, lowerBorder;

    UserMenuModel(String menuText, String menuMessage, Fragment fragmentView, String tag) {
        this.menuText       = menuText;
        this.menuMessage    = menuMessage;
        this.fragmentView   = fragmentView;
        this.tag            = tag;
    }

    String menuText_getText() { return menuText; }

    String menuMessage_getText() { return menuMessage; }

    Fragment fragment_getView() { return fragmentView; }

    String tag_whichUser() { return tag; }

    void set_upperBorderID(View v) { v.setId(View.generateViewId()); }

    int get_upperBorderID() { return upperBorder.getId();}

    void set_lowerBorderID(View v) { v.setId(View.generateViewId()); }

    int get_lowerBorderID() { return lowerBorder.getId(); }
}
