package com.kitku.kitku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.kitku.kitku.Login.LoginFragment;

import static androidx.core.view.ViewCompat.generateViewId;

class UserMenuBuilder {

    // untuk isi konten view
    private String message, menuText;

    // SharedPreference untuk logout
    private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit;
    private String tag;

    // nextID untuk referensi batas
    private int nextID;

    // resources untuk size SP
    private Resources resources;

    // userMenuLayout untuk parent dari view
    private RelativeLayout userMenuLayout;

    // untuk remove fragment : WIP
    private View pageview;
    private Fragment pagefragment;
    private FragmentManager childfragment;

    UserMenuBuilder(RelativeLayout userMenuLayout,
                    Resources resources,
                    String menuText,
                    String message,
                    int ID,
                    View view,
                    Fragment fragment,
                    FragmentManager child,
                    String tag) {
        this.userMenuLayout     = userMenuLayout;
        this.resources          = resources;
        this.menuText           = menuText;
        this.message            = message;
        this.nextID             = this.addMenu(ID);
        this.pageview           = view;
        this.pagefragment       = fragment;
        this.childfragment      = child;
        this.tag                = tag;
    }

    private int addMenu(int ID) {
        RelativeLayout.LayoutParams params;

        //////////////////////////
        // Garis pembatas atas
        View line = new View(userMenuLayout.getContext());
        line.setBackgroundColor(Color.BLACK);
        line.setId(View.generateViewId());
        if (ID != 0) {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP,
                            1,
                            resources.getDisplayMetrics()));
        } else params = additionalLine();
        params.addRule(RelativeLayout.BELOW, ID);
        line.setLayoutParams(params);
        userMenuLayout.addView(line);
        ////////////////////////////

        ////////////////////////////
        // Deklarasi max height dalam unit sp
        int spSize40 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                40,
                resources.getDisplayMetrics());
        int spSize10 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                10,
                resources.getDisplayMetrics());
        int spSize50 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                50,
                resources.getDisplayMetrics());
        ////////////////////////////

        ////////////////////////////
        // Teks menu
        TextView menuText = new TextView(userMenuLayout.getContext());
        menuText.setId(generateViewId());
        menuText.setText(this.menuText);
        menuText.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                8,
                resources.getDisplayMetrics()));
        menuText.setGravity(Gravity.CENTER_VERTICAL);
        //params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, spSize40);
        //params.addRule(RelativeLayout.START_OF, );
        //params.addRule(RelativeLayout.BELOW, line.getId());
        //menuText.setLayoutParams(params);
        menuText.setPadding(spSize10,0,spSize10,0);
        menuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuClick();
            }
        });
        /////////////////////////////

        /////////////////////////////
        // Button menu
        ImageButton menuButton = new ImageButton(userMenuLayout.getContext());
        menuButton.setBackgroundColor(Color.WHITE);
        menuButton.setImageDrawable(
                resources.getDrawable(
                        R.drawable.ic_button_back,
                        userMenuLayout.getContext().getTheme()));
        menuButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        menuButton.setRotation(180);
        params = new RelativeLayout.LayoutParams(spSize40, spSize50);
        params.addRule(RelativeLayout.BELOW, line.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        menuButton.setLayoutParams(params);
        menuButton.setPadding(spSize10,0,spSize10,0);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuClick();
            }
        });
        /////////////////////////////

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, spSize50);
        params.addRule(RelativeLayout.START_OF, menuButton.getId());
        params.addRule(RelativeLayout.BELOW, line.getId());
        menuText.setLayoutParams(params);
        menuText.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        /////////////////////////////
        // Garis pembatas bawah
        View lines = new View(userMenuLayout.getContext());
        lines.setBackgroundColor(Color.BLACK);
        lines.setId(View.generateViewId());
        if (this.menuText.equals("Logout")) params = additionalLine();
        else {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP,
                            1,
                            resources.getDisplayMetrics()));
        }
        params.addRule(RelativeLayout.BELOW, menuText.getId());
        lines.setLayoutParams(params);
        //////////////////////////////

        //////////////////////////////
        // Tambahkan text, button, dan pembatas ke menu
        userMenuLayout.addView(menuText);
        userMenuLayout.addView(menuButton);
        userMenuLayout.addView(lines);
        //////////////////////////////

        //////////////////////////////
        // Ganti id view ke pembatas yang baru
        return lines.getId();
        //////////////////////////////
    }

    // onClick menu
    private void menuClick(){
        new AlertDialog.Builder(pagefragment.getContext())
                .setTitle(menuText)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!menuText.equals("Logout")) dialogInterface.dismiss();
                else {
                    userData = PreferenceManager.getDefaultSharedPreferences(
                            userMenuLayout.getContext().getApplicationContext());
                    userDataEdit = userData.edit();
                    if (tag.equals("User"))
                        userDataEdit.remove("ID_User");
                    else userDataEdit.remove("ID_Mitra");
                    userDataEdit.apply();
                    pageview.setVisibility(View.GONE);
                    pageview.setId(View.generateViewId());
                    Fragment LoginFragment = new LoginFragment();
                    FragmentManager fragmentManager = childfragment;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(pageview.getId(), LoginFragment);
                    fragmentTransaction.commit();
                    //pageview = inflater.inflate(R.layout.fragment_login, container, false);
                }}})
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();}})
                .show();
    }

    int getNextID(){ return nextID; }

    //private ViewGroup container;
    //private LayoutInflater inflater;
    //void setAdditionalData(LayoutInflater inflater, ViewGroup container) {
    //    this.container  = container;
    //    this.inflater   = inflater;
    //}

    private RelativeLayout.LayoutParams additionalLine() {
         return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP,
                        2,
                        resources.getDisplayMetrics()));
    }
}
