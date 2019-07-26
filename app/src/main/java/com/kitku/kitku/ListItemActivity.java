package com.kitku.kitku;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity {

    private TextView textviewListItemCategoryName;
    private RecyclerView recyclerViewListItemCategoryItem;
    private ArrayList<ListItemCategoryCardViewDataModel> listItemCategoryCardViewDataModelArrayList = new ArrayList<>();
    private ListItemCategoryCardViewAdapter listItemCategoryCardViewAdapter;
    String txtTitle;
    private ShimmerFrameLayout shimmerframeListItemSkeletonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        //addCategoryItemData();
        textviewListItemCategoryName = findViewById(R.id.textviewListItemCategoryName);
        recyclerViewListItemCategoryItem = findViewById(R.id.recyclerviewListItemCategoryItem);
        recyclerViewListItemCategoryItem.setHasFixedSize(true);

        // Variabel untuk Skeleton View
        shimmerframeListItemSkeletonView = findViewById(R.id.shimmerlayoutListItemSkeletonView);

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
        /* Function ini akan mematikan efek shimmer dan menghilangkan layout SkeletonView
        dan menampilkan layout RecyclerView List Item  */
        shimmerframeListItemSkeletonView.stopShimmer();
        shimmerframeListItemSkeletonView.setVisibility(View.GONE);
        recyclerViewListItemCategoryItem.setVisibility(View.VISIBLE);
        recyclerViewListItemCategoryItem.setAdapter(listItemCategoryCardViewAdapter);

    }

    /* Method untuk menambahkan data yang akan disimpan dalam ArrayList berdasarkan Data Modelnya */

    private void addCategoryItemData(int index) {

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
        // Memulai Efek Shimmer
        shimmerframeListItemSkeletonView.startShimmer();
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

    // Memulai efek shimmer jika pengguna kembali menggunakan aplikasi
    @Override
    protected void onResume() {
        super.onResume();
        shimmerframeListItemSkeletonView.startShimmer();
    }

    // Mematikan efek shimmer jika pengguna sudah berada di dalam aplikasi
    @Override
    protected void onPause() {
        super.onPause();
        shimmerframeListItemSkeletonView.stopShimmer();
    }
}
