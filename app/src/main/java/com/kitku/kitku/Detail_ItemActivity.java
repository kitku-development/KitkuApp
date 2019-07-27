package com.kitku.kitku;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

public class Detail_ItemActivity extends AppCompatActivity {

    Button buttonDetail_ItemBuyItem;
    ImageView kolomGambar;
    TextView teksNama, teksPack, teksHarga, teksDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__item);

        buttonDetail_ItemBuyItem = findViewById(R.id.buttonDetail_Item_BuyItem);

        kolomGambar         = findViewById(R.id.imageDetail_Item);
        teksNama            = findViewById(R.id.textitemnameDetail_Item);
        teksHarga           = findViewById(R.id.textitempriceDetail_Item);
        teksPack            = findViewById(R.id.textpackDetail_Item);
        teksDetail          = findViewById(R.id.textitemdescriptionDetail_Item);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.txtDetail_Item1).setVisibility(View.INVISIBLE);
        findViewById(R.id.lineDetail_Item3).setVisibility(View.INVISIBLE);
        buttonDetail_ItemBuyItem.setVisibility(View.INVISIBLE);

        String id_barang = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_barang = bundle.getString("id");
            //Log.d("id",id_barang);
        }
        runAsync();
        sendData.execute(z_BackendPreProcessing.URL_ProdukDetail + id_barang, null);
    }

    public void buttonBuyItem(View view) {
        final Dialog dialog_detail_item_buyitem = new Dialog(this);
        dialog_detail_item_buyitem.setContentView(R.layout.dialog_detail_item_buyitem);

        Button buttonDialoDetail_Item_Back = dialog_detail_item_buyitem.findViewById(R.id.buttonDialogDetail_Item_Back);
        buttonDialoDetail_Item_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_detail_item_buyitem.dismiss();
            }
        });

        dialog_detail_item_buyitem.setCancelable(false);
        dialog_detail_item_buyitem.setCanceledOnTouchOutside(false);
        dialog_detail_item_buyitem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog_detail_item_buyitem.show();
    }

    public void setBackButton(View v) {
        this.finish();
    }

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    String nama_barang, desc, satuan, harga, jumlah, review, url;
    backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    protected void runAsync() {
        // Memulai Efek Shimmer
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                //Log.d("out",output);
                String[] data;
                try {
                    // Parsing data dari JSON ke dalam array
                    data = new z_BackendPreProcessing().bacaDetailProduk(output);

                    // Data dalam array dikelompokkan dalam array baru
                    nama_barang = data[0];
                    desc        = data[1];
                    satuan      = data[2];
                    harga       = data[3];
                    jumlah      = data[4];
                    review      = data[5];
                    url         = data[6];
                    try {
                        new downloadImage(Detail_ItemActivity.this).execute(
                                url);
                    } catch (Exception e) { e.printStackTrace(); }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // AsyncTask untuk download gambar dan simpan pada array
    Bitmap gambar;
    static class downloadImage extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<Detail_ItemActivity> mParentActivity;

        downloadImage(Detail_ItemActivity parentActivity) {
            mParentActivity = new WeakReference<>(parentActivity);
        }

        @Override
        protected Bitmap doInBackground(String...url) {
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url[0]).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) { e.printStackTrace(); }

            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            final Detail_ItemActivity parentActivity = mParentActivity.get();
            parentActivity.gambar = result;
            parentActivity.loadFinish();
        }
    }

    protected void loadFinish() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        findViewById(R.id.txtDetail_Item1).setVisibility(View.VISIBLE);
        findViewById(R.id.lineDetail_Item3).setVisibility(View.VISIBLE);
        buttonDetail_ItemBuyItem.setVisibility(View.VISIBLE);

        teksNama.setText(nama_barang);
        String temp = "Rp. " + harga;
        teksHarga.setText(temp);
        if (Integer.parseInt(satuan) > 1)
            temp = "/ " + satuan + " pack";
        else
            temp = "/ pack";
        teksPack.setText(temp);
        teksDetail.setText(desc);
        kolomGambar.setImageBitmap(gambar);
    }
}
