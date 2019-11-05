package com.kitku.kitku.List_Item;

import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.kitku.kitku.BackgroundProcess.BackendPreProcessing;
import com.kitku.kitku.BackgroundProcess.ImageCaching;
import com.kitku.kitku.BackgroundProcess.ImageDownloader;
import com.kitku.kitku.R;
import com.kitku.kitku.BackgroundProcess.AsyncServerAccess;

import java.io.File;
import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListItemCategoryItem;
    private ArrayList<ListItemCategoryCardViewDataModel> listItemCategoryCardViewDataModelArrayList = new ArrayList<>();
    String txtTitle;
    private ShimmerFrameLayout shimmerframeListItemSkeletonView;
    //private static Context ListItemActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        //addCategoryItemData();
        TextView textviewListItemCategoryName = findViewById(R.id.textviewListItemCategoryName);
        recyclerViewListItemCategoryItem = findViewById(R.id.recyclerviewListItemCategoryItem);
        recyclerViewListItemCategoryItem.setHasFixedSize(true);
        //ListItemActivityContext = getBaseContext();

        // Variabel untuk Skeleton View
        shimmerframeListItemSkeletonView = findViewById(R.id.shimmerlayoutListItemSkeletonView);

        /* Fungsi untuk mendapatkan nilai Intent dari Home, untuk mengubah text pada Title Textview */

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("titletextVegetable")) {
            txtTitle = bundle.getString("titletextVegetable");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(BackendPreProcessing.URL_ProductCategory + "SAYUR", null);
        }
        if (bundle != null && bundle.containsKey("titletextMeat")) {
            txtTitle = bundle.getString("titletextMeat");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(BackendPreProcessing.URL_ProductCategory + "DAGING", null);
        }
        if (bundle != null && bundle.containsKey("titletextFish")) {
            txtTitle = bundle.getString("titletextFish");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(BackendPreProcessing.URL_ProductCategory + "IKAN", null);
        }
        if (bundle != null && bundle.containsKey("titletextWheat")) {
            txtTitle = bundle.getString("titletextWheat");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(BackendPreProcessing.URL_ProductCategory + "BIJI", null);
        }
        if (bundle != null && bundle.containsKey("titletextFastFood")) {
            txtTitle = bundle.getString("titletextFastFood");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(BackendPreProcessing.URL_ProductCategory + "BIJI", null);
        }
        if (bundle != null && bundle.containsKey("titletextOthers")) {
            txtTitle = bundle.getString("titletextOthers");
            textviewListItemCategoryName.setText(txtTitle);
            newCategoryClick();
            sendData.execute(BackendPreProcessing.URL_ProductCategory + "BIJI", null);
        }

        //showCategoryItemData();
    }

    /* Method yang akan menampilkan RecyclerView beserta isinya */

    private void showCategoryItemData() {
        recyclerViewListItemCategoryItem.setLayoutManager(new GridLayoutManager(this,2));
        ListItemCategoryCardViewAdapter listItemCategoryCardViewAdapter = new ListItemCategoryCardViewAdapter(listItemCategoryCardViewDataModelArrayList);
        /* Function ini akan mematikan efek shimmer dan menghilangkan layout SkeletonView
        dan menampilkan layout RecyclerView List Item  */
        shimmerframeListItemSkeletonView.stopShimmer();
        shimmerframeListItemSkeletonView.setVisibility(View.GONE);
        recyclerViewListItemCategoryItem.setVisibility(View.VISIBLE);
        recyclerViewListItemCategoryItem.setAdapter(listItemCategoryCardViewAdapter);

    }

    /* Method untuk menambahkan data yang akan disimpan dalam ArrayList berdasarkan Data Modelnya */

    private void addCategoryItemData(int index) {

        /*String packed = "/";
        if (Float.valueOf(satuan[index]) > 1.0)
            packed += satuan[index];
        packed += " pack";*/
        listItemCategoryCardViewDataModelArrayList.add(new ListItemCategoryCardViewDataModel(
                listGambar[index],
                String.valueOf(nama_barang[index]),
                String.valueOf(harga[index]),
                "/ pack",
                id_barang[index],
                Integer.valueOf(jumlah[index])
        ));

        counterselesai++;
        if (counterselesai == listGambar.length)
            showCategoryItemData();
    }

    void newCategoryClick(){
        listItemCategoryCardViewDataModelArrayList = new ArrayList<>();
        runAsync();
    }

    // Inisiasi AsyncTask dari AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends AsyncServerAccess {
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
                    data = BackendPreProcessing.readProductList(output);

                    // Data dalam array dikelompokkan dalam array baru
                    id_barang   = data[0];
                    nama_barang = data[1];
                    satuan      = data[2];
                    harga       = data[3];
                    jumlah      = data[4];
                    url         = data[5];
                    listGambar = new Bitmap[url.length];
                    counterselesai = 0;
                    String productPicLocation = getStringOfProductPicLocation();
                    for (int index = 0; index < url.length; index++) {
                        loadImage();
                        downloadImage.execute(
                                productPicLocation,
                                id_barang[index],
                                url[index],
                                String.valueOf(index));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(
                            ListItemActivity.this,
                            "Gagal terhubung ke server. Periksa kembali koneksi anda.",
                            Toast.LENGTH_LONG).
                            show();
                }
            }
        });
    }

    // AsyncTask untuk download gambar dan simpan pada array
    Bitmap[] listGambar;
    int counterselesai;

    static class backgroundImageDownloader extends ImageDownloader {
        backgroundImageDownloader(AsyncResponse delegate) { this.delegate = delegate; }
    }
    private backgroundImageDownloader downloadImage;

    private void loadImage() {
        downloadImage = new backgroundImageDownloader(new backgroundImageDownloader.AsyncResponse() {
            @Override
            public void processFinish(Bitmap output, Integer index) {
                listGambar[index] = output;
                addCategoryItemData(index);
            }
        });
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

    public void setbackButton(View v) {
        this.finish();
    }

    public String getStringOfProductPicLocation() {
        File folder = null;
        String location = "";
        try {
            folder = this.getExternalFilesDir("Images");
            if (folder != null)
                location = folder.getAbsolutePath().concat("/");
        } catch (Exception e) {
            ImageCaching.createDir(folder);
            location = getStringOfProductPicLocation();
        }
        return location;
    }

    /*static Context getContext() {
        return ListItemActivityContext;
    }*/
}