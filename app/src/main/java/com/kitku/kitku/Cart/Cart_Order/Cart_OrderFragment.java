package com.kitku.kitku.Cart.Cart_Order;
import com.kitku.kitku.BackgroundProcess.BackendPreProcessing;
import com.kitku.kitku.BackgroundProcess.ImageCaching;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kitku.kitku.Checkout.Checkout_SetAddressActivity;
import com.kitku.kitku.MainActivity;
import com.kitku.kitku.R;

import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart_OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrderList;
    private Cart_Order_OrderListCardViewAdapter cartOrderListAdapter;
    private ArrayList<Cart_Order_OrderListCardViewDataModel> orderListArrayList = new ArrayList<>();
    private TextView cartTotalPrice, totalItem;

    private Button buttonToCheckout, buttonToHome;

    public Cart_OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View orderView = inflater.inflate(R.layout.fragment_cart__order, container, false);
        //orderViewPub = orderView;
        recyclerViewOrderList = orderView.findViewById(R.id.recyclerviewCart_OrderOrderList);
        recyclerViewOrderList.setHasFixedSize(true);

        buttonToCheckout = orderView.findViewById(R.id.buttonCart_OrderGoToCheckout);
        buttonToHome = orderView.findViewById(R.id.buttonCart_OrderGoToHome);
        cartTotalPrice = orderView.findViewById(R.id.cartTotalPrice);
        totalItem = orderView.findViewById(R.id.textCart_Order1);

        earlyProcedure();

        return orderView;
    }

    private void earlyProcedure() {
        buttonToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.default_home_button();
            }
        });
        buttonToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jika User belum mengisi Alamat, maka akan diarahkan ke Halaman Pengisian Alamat
                Intent intent = new Intent(v.getContext(), Checkout_SetAddressActivity.class);
                v.getContext().startActivity(intent);

                /* Jika User sudah mengisi Alamat, maka akan langsung diarahkan ke Halaman Checkout
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                v.getContext().startActivity(intent);*/
            }
        });
        addData();
        showData();
    }

    @Override
    public void onResume() {
        super.onResume();
        earlyProcedure();
    }

    private void showData() {
        recyclerViewOrderList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        cartOrderListAdapter = new Cart_Order_OrderListCardViewAdapter(orderListArrayList);
        recyclerViewOrderList.setAdapter(cartOrderListAdapter);
        recyclerViewOrderList.invalidate();
        recyclerViewOrderList.refreshDrawableState();
    }

    private List<String> id_barang, jumlah, harga, nama, pack;//satuan, pack;
    private void addData() {
        new loadData(this).execute();
    }

    static class loadData extends AsyncTask<String, Void, String> {
        private WeakReference<Cart_OrderFragment> mParentActivity;
        int totalPrice, totalItem;

        loadData(Cart_OrderFragment parentActivity) {
            mParentActivity = new WeakReference<>(parentActivity);
        }

        @Override
        protected String doInBackground(String... strings) {
            // get cart data from sharedpreference
            SharedPreferences cartData = PreferenceManager.getDefaultSharedPreferences(
                    Objects.requireNonNull(mParentActivity.get().getContext()));
            String rawData;
            if (cartData.contains("Cart")) {
                rawData = cartData.getString("Cart", null);
                JSONObject jsonObject;
                try {
                    totalPrice = 0;
                    assert rawData != null;
                    jsonObject = new JSONObject(rawData);
                    mParentActivity.get().id_barang  = new BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("id_barang"));
                    mParentActivity.get().jumlah     = new BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("jumlah"));
                    mParentActivity.get().harga      = new BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("harga"));
                    mParentActivity.get().nama       = new BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("nama"));
                    //mParentActivity.get().satuan     = new BackendPreProcessing()
                    //    .ItemListDeserialize(jsonObject.getJSONArray("satuan"));
                    mParentActivity.get().pack       = new BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("pack"));
                    List<Bitmap> gambar = new ArrayList<>();
                    mParentActivity.get().orderListArrayList = new ArrayList<>();
                    String productPicLocation = mParentActivity.get().getStringOfProductPicLocation();
                    for (int index = 0; index < mParentActivity.get().id_barang.size(); index++) {
                        try {
                            Bitmap image = ImageCaching.getImageDirectly(
                                    productPicLocation.concat("/")
                                            .concat(mParentActivity.get().id_barang.get(index)));
                            gambar.add(index, image);
                            mParentActivity.get().orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel(
                                    gambar.get(index),
                                    mParentActivity.get().nama.get(index),
                                    mParentActivity.get().harga.get(index),
                                    mParentActivity.get().pack.get(index).concat(" / pack"),
                                    mParentActivity.get().cartTotalPrice,
                                    mParentActivity.get().id_barang.get(index),
                                    mParentActivity.get().getContext(),
                                    mParentActivity.get().jumlah.get(index)
                            ));
                        } catch (Exception e) { e.printStackTrace(); }
                        totalPrice += Integer.valueOf(mParentActivity.get().harga.get(index));
                        totalItem = index + 1;
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
            return null;
        }

        protected void onPostExecute(String result) {
            String totalPriceString = "Rp. " + totalPrice;
            String totalPesanan = "Total pesanan : ".concat(String.valueOf(totalItem)).concat(" item(s)");
            mParentActivity.get().cartTotalPrice.setText(totalPriceString);
            mParentActivity.get().totalItem.setText(totalPesanan);
        }
    }

    public String getStringOfProductPicLocation() {
        File folder = null;
        String location = "";
        try {
            folder = this.getContext().getExternalFilesDir("Images");
            if (folder != null)
                location = folder.getAbsolutePath().concat("/");
        } catch (Exception e) {
            ImageCaching.createDir(folder);
            location = getStringOfProductPicLocation();
        }
        return location;
    }
}
