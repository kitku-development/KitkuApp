package com.kitku.kitku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity {

    private TextView textviewListItemCategoryName;
    private RecyclerView recyclerViewListItemCategoryItem;
    private ArrayList<ListItemCategoryCardViewDataModel> listItemCategoryCardViewDataModelArrayList = new ArrayList<>();
    private ListItemCategoryCardViewAdapter listItemCategoryCardViewAdapter;
    String txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        addCategoryItemData();

        textviewListItemCategoryName = findViewById(R.id.textviewListItemCategoryName);
        recyclerViewListItemCategoryItem = findViewById(R.id.recyclerviewListItemCategoryItem);
        recyclerViewListItemCategoryItem.setHasFixedSize(true);

        /* Fungsi untuk mendapatkan nilai Intent dari Home, untuk mengubah text pada Title Textview */

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("titletextVegetable")) {
            txtTitle = bundle.getString("titletextVegetable");
            textviewListItemCategoryName.setText(txtTitle);
        }
        if (bundle != null && bundle.containsKey("titletextMeat")) {
            txtTitle = bundle.getString("titletextMeat");
            textviewListItemCategoryName.setText(txtTitle);
        }
        if (bundle != null && bundle.containsKey("titletextFish")) {
            txtTitle = bundle.getString("titletextFish");
            textviewListItemCategoryName.setText(txtTitle);
        }
        if (bundle != null && bundle.containsKey("titletextWheat")) {
            txtTitle = bundle.getString("titletextWheat");
            textviewListItemCategoryName.setText(txtTitle);
        }

        showCategoryItemData();

    }

    /* Method yang akan menampilkan RecyclerView beserta isinya */

    private void showCategoryItemData() {
        recyclerViewListItemCategoryItem.setLayoutManager(new GridLayoutManager(this,2));
        listItemCategoryCardViewAdapter = new ListItemCategoryCardViewAdapter(listItemCategoryCardViewDataModelArrayList);
        recyclerViewListItemCategoryItem.setAdapter(listItemCategoryCardViewAdapter);
    }

    /* Method untuk menambahkan data yang akan disimpan dalam ArrayList berdasarkan Data Modelnya */

    private void addCategoryItemData() {
        listItemCategoryCardViewDataModelArrayList = new ArrayList<>();

        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_sawiputih,"Sawi Putih",
                        "Rp 15.000", "/ 1 pcs"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_sawihijau, "Sawi Hijau",
                        "Rp 15.500", "/ 1 pcs"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_kangkung, "Kangkung",
                        "Rp 4.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_bayammerah, "Bayam Merah",
                        "Rp 13.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_bayamhijau, "Bayam Hijau",
                        "Rp 4.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_mentimun, "Mentimun",
                        "Rp 6.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_wortel, "Wortel",
                        "Rp 9.500", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_pare, "Pare",
                        "Rp 6.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_labusiam, "Labu Siam",
                        "Rp 9.500", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_pucukubi, "Pucuk Ubi",
                        "Rp 10.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_kol, "Kol",
                        "Rp 8.000", "/ 1 pcs"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_kembangkol, "Kembang Kol",
                        "Rp 30.000", "/ 1 pcs"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_brokoli, "Brokoli",
                        "Rp 24.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_buncis, "Buncis",
                        "Rp 4.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_kacangpanjang, "Kacang Panjang",
                        "Rp 5.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_kentang, "Kentang",
                        "Rp 18.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_tombakbawang, "Tombak Bawang",
                        "Rp 8.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_bawangmerah, "Bawang Merah",
                        "Rp 12.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_bawangputih, "Bawang Putih",
                        "Rp 10.000", "/ 1 pack"));
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 20.000", "/ 1 pack"));
    }
}
