package com.kitku.kitku;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity {

    private TextView textviewListItemCategoryName;
    private RecyclerView recyclerViewListItemCategoryItem;
    private ArrayList<ListItemCategoryCardViewDataModel> listItemCategoryCardViewDataModelArrayList = new ArrayList<>();
    private ListItemCategoryCardViewAdapter listItemCategoryCardViewAdapter;
    String txtTitle;
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        loadingIndicator = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.addView(loadingIndicator);

        //addCategoryItemData();
        textviewListItemCategoryName = findViewById(R.id.textviewListItemCategoryName);
        recyclerViewListItemCategoryItem = findViewById(R.id.recyclerviewListItemCategoryItem);
        recyclerViewListItemCategoryItem.setHasFixedSize(true);

        /* Fungsi untuk mendapatkan nilai Intent dari Home, untuk mengubah text pada Title Textview */

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("titletextVegetable")) {
            txtTitle = bundle.getString("titletextVegetable");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(z_BackendPreProcessing.URL_ProdukPerKategori + "SAYUR", null);
        }
        if (bundle != null && bundle.containsKey("titletextMeat")) {
            txtTitle = bundle.getString("titletextMeat");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(z_BackendPreProcessing.URL_ProdukPerKategori + "DAGING", null);
        }
        if (bundle != null && bundle.containsKey("titletextFish")) {
            txtTitle = bundle.getString("titletextFish");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(z_BackendPreProcessing.URL_ProdukPerKategori + "IKAN", null);
        }
        if (bundle != null && bundle.containsKey("titletextWheat")) {
            txtTitle = bundle.getString("titletextWheat");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(z_BackendPreProcessing.URL_ProdukPerKategori + "BIJI", null);
        }

        //showCategoryItemData();
    }

    /* Method yang akan menampilkan RecyclerView beserta isinya */

    private void showCategoryItemData() {
        recyclerViewListItemCategoryItem.setLayoutManager(new GridLayoutManager(this,2));
        listItemCategoryCardViewAdapter = new ListItemCategoryCardViewAdapter(listItemCategoryCardViewDataModelArrayList);
        recyclerViewListItemCategoryItem.setAdapter(listItemCategoryCardViewAdapter);
        loadingIndicator.setVisibility(View.GONE);
    }

    /* Method untuk menambahkan data yang akan disimpan dalam ArrayList berdasarkan Data Modelnya */

    private void addCategoryItemData(int index) {

        /*listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel
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
        */
        String packed = "/";
        if (Float.valueOf(satuan[index]) > 1.0)
            packed += satuan[index];
        packed += " pack";
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel(
                listGambar[index],
                String.valueOf(nama_barang[index]),
                String.valueOf(harga[index]),
                packed
        ));

        counterselesai++;
        if (counterselesai == listGambar.length)
            showCategoryItemData();
    }

    void newCategoryClick(){
        listItemCategoryCardViewDataModelArrayList = new ArrayList<>();
        runAsync();
    }

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    String[] id_barang, nama_barang, satuan, harga, jumlah, url;
    backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    protected void runAsync() {
        loadingIndicator.setVisibility(View.VISIBLE);
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                String[][] data;
                try {
                    // Parsing data dari JSON ke dalam array
                    data = new z_BackendPreProcessing().bacaListProduk(output);

                    // Data dalam array dikelompokkan dalam array baru
                    id_barang   = data[0];
                    nama_barang = data[1];
                    satuan      = data[2];
                    harga       = data[3];
                    jumlah      = data[4];
                    url         = data[5];
                    listGambar = new Bitmap[url.length];
                    counterselesai = 0;
                    for (int index = 0; index < url.length; index++) {
                        try {
                            new downloadImage(ListItemActivity.this).execute(
                                    url[index], String.valueOf(index));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    /*for (int index = 0; index < id_barang.length; index++) {
                        String print =
                                id_barang[index]    + " , " +
                                nama_barang[index]  + " , " +
                                satuan[index]       + " , " +
                                harga[index]        + " , " +
                                jumlah[index]       + " , " +
                                url[index];
                        Log.d("Barang : ", print);
                    }*/
                    //
                    // Proses untuk menampilkan barang dimulai dari sini (masih dalam kurung try catch)
                    //
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // AsyncTask untuk download gambar dan simpan pada array
    Bitmap[] listGambar;
    int counterselesai;
    static class downloadImage extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ListItemActivity> mParentActivity = null;
        int indexnum;

        downloadImage(ListItemActivity parentActivity) {
            mParentActivity = new WeakReference<>(parentActivity);
        }

        @Override
        protected Bitmap doInBackground(String...url) {
            indexnum = Integer.parseInt(url[1]);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url[0]).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) { e.printStackTrace(); }

            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            final ListItemActivity parentActivity = mParentActivity.get();
            parentActivity.listGambar[indexnum] = result;
            parentActivity.addCategoryItemData(indexnum);
        }
    }
}
