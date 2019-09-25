package com.kitku.kitku;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kitku.kitku.BackgroundProcess.ImageCaching;
import com.kitku.kitku.BackgroundProcess.z_AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class Detail_ItemActivity extends AppCompatActivity {

    Button buttonDetail_ItemBuyItem, buttonGoToHome;
    ImageView kolomGambar;
    TextView teksNama, teksPack, teksHarga, teksDetail, teksToolbar;
    String id_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__item);

        buttonDetail_ItemBuyItem = findViewById(R.id.buttonDetail_Item_BuyItem);
        buttonGoToHome = findViewById(R.id.buttonDetail_Item_GoToHome);

        kolomGambar         = findViewById(R.id.imageDetail_Item);
        teksNama            = findViewById(R.id.textitemnameDetail_Item);
        teksHarga           = findViewById(R.id.textitempriceDetail_Item);
        teksPack            = findViewById(R.id.textitempackDetail_Item);
        teksDetail          = findViewById(R.id.textitemdescriptionDetail_Item);
        teksToolbar         = findViewById(R.id.textitemnameToolbarDetail_Item);
        findViewById(R.id.txtDetail_Item1).setVisibility(View.INVISIBLE);
        findViewById(R.id.lineDetail_Item3).setVisibility(View.INVISIBLE);
        buttonDetail_ItemBuyItem.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_barang = bundle.getString("id");
            //Log.d("id",id_barang);
        }
        runAsync();
        sendData.execute(z_BackendPreProcessing.URL_ProductDetail + id_barang, null);
        findViewById(R.id.button).setVisibility(View.GONE);
    }

    public void buttonBuyItem(View view) {
        // tambah data ke dalam sharedpreference
        try {
            new z_BackendPreProcessing().addItemToCart(
                    id_barang,
                    "1",
                    teksHarga.getText().toString(),
                    this,
                    teksNama.getText().toString(),
                    satuan,
                    teksPack.getText().toString());
        } catch (Exception e) { e.printStackTrace(); }

        final Dialog dialog_detail_item_buyitem = new Dialog(this);
        dialog_detail_item_buyitem.setContentView(R.layout.dialog_detail_item_buyitem);

        Button buttonDialogDetail_Item_Back = dialog_detail_item_buyitem.findViewById(R.id.buttonDialogDetail_Item_Back);
        buttonDialogDetail_Item_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_detail_item_buyitem.dismiss();
            }
        });
        Button buttonDialogDetail_Item_ToOrder = dialog_detail_item_buyitem.findViewById(R.id.buttonDialogDetail_Item_ToOrder);
        buttonDialogDetail_Item_ToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_ItemActivity.this, MainActivity.class);
                intent.putExtra("goToCart",true);
                startActivity(intent);
                Detail_ItemActivity.this.finish();
            }
        });

        dialog_detail_item_buyitem.setCancelable(false);
        dialog_detail_item_buyitem.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(
                dialog_detail_item_buyitem.getWindow()).setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));

        dialog_detail_item_buyitem.show();
    }

    public void setBackButton(View v) {
        this.finish();
    }

    public void buttonToHome(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
                    data = new z_BackendPreProcessing().readProductDetail(output);

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
                                url, id_barang);
                    } catch (Exception e) { e.printStackTrace(); }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(
                            Detail_ItemActivity.this,
                            "Gagal terhubung ke server. Periksa kembali koneksi anda.",
                            Toast.LENGTH_LONG).
                            show();
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
                File folder = mParentActivity.get().getExternalFilesDir("Images");
                assert folder != null;
                String dirLocation = folder.getAbsolutePath().concat("/");
                if (new ImageCaching().isExist(
                        dirLocation.concat(url[1])))
                    mIcon11 = new ImageCaching().getImage(dirLocation.concat(url[1]));
                else {
                    InputStream in = new java.net.URL(url[0]).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                    /*String imgLocation = Objects.requireNonNull(mParentActivity
                            .get()
                            .getExternalFilesDir("Images"))
                            .getCanonicalPath() + url[1];*/
                    new ImageCaching().putImageWithFullPath(url[1], mIcon11, mParentActivity.get().getBaseContext(), "item");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        findViewById(R.id.txtDetail_Item1).setVisibility(View.VISIBLE);
        findViewById(R.id.lineDetail_Item3).setVisibility(View.VISIBLE);
        buttonDetail_ItemBuyItem.setVisibility(View.VISIBLE);

        teksNama.setText(nama_barang);
        String temp = "Rp. " + harga;
        teksHarga.setText(temp);
        /*if (Integer.parseInt(satuan) > 1)
            temp = "/ " + satuan + " pack";
        else
            temp = "/ pack";*/
        teksPack.setText(satuan);
        teksDetail.setText(desc);
        kolomGambar.setImageBitmap(gambar);
        teksToolbar.setText(nama_barang);
    }

    public void secretButton(View v) {
        SharedPreferences.Editor edit = PreferenceManager
                .getDefaultSharedPreferences(this).edit();
        edit.remove("Cart");
        edit.apply();
    }
}
