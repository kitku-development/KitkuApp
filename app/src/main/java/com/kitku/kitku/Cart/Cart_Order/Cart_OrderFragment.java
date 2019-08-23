package com.kitku.kitku.Cart.Cart_Order;
import com.kitku.kitku.BackgroundProcess.ImageCaching;
import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.kitku.kitku.R;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart_OrderFragment extends Fragment {

    private RecyclerView recyclerviewOrderList;
    private Cart_Order_OrderListCardViewAdapter cartOrderListAdapter;
    private ArrayList<Cart_Order_OrderListCardViewDataModel> orderListArrayList = new ArrayList<>();
    private TextView cartTotalPrice, totalItem;

    private Button buttonToCheckout;

    public Cart_OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View orderView = inflater.inflate(R.layout.fragment_cart__order, container, false);

        recyclerviewOrderList = orderView.findViewById(R.id.recyclerviewCart_OrderOrderList);
        recyclerviewOrderList.setHasFixedSize(true);

        buttonToCheckout = orderView.findViewById(R.id.buttonCart_OrderGoToCheckout);
        cartTotalPrice = orderView.findViewById(R.id.cartTotalPrice);
        totalItem = orderView.findViewById(R.id.textCart_Order1);

        earlyProc();

        return orderView;
    }

    public void earlyProc() {
        addData();
        showData();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        earlyProc();
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        buttonToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jika User belum mengisi Alamat, maka akan diarahkan ke Halaman Pengisian Alamat
                Intent intent = new Intent(v.getContext(), Checkout_SetAddressActivity.class);
                v.getContext().startActivity(intent);

                /* Jika User sudah mengisi Alamat, maka akan langsung diarahkan ke Halaman Checkout
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                v.getContext().startActivity(intent);



            }
        });

    }*/

    private void showData() {
        recyclerviewOrderList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        cartOrderListAdapter = new Cart_Order_OrderListCardViewAdapter(orderListArrayList);
        recyclerviewOrderList.setAdapter(cartOrderListAdapter);
    }

    private List<String> id_barang, jumlah, harga, nama, satuan, pack;
    private void addData() {
        new loadData(this).execute();
        /*
        orderListArrayList = new ArrayList<>();

        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 15.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 16.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 17.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 18.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 19.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 20.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 21.000", "/ 1 pack"));
        orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Rp 22.000", "/ 1 pack"));
    */
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
                    mParentActivity.get().id_barang  = new z_BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("id_barang"));
                    mParentActivity.get().jumlah     = new z_BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("jumlah"));
                    mParentActivity.get().harga      = new z_BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("harga"));
                    mParentActivity.get().nama       = new z_BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("nama"));
                    mParentActivity.get().satuan     = new z_BackendPreProcessing()
                        .ItemListDeserialize(jsonObject.getJSONArray("satuan"));
                    mParentActivity.get().pack       = new z_BackendPreProcessing()
                            .ItemListDeserialize(jsonObject.getJSONArray("pack"));
                    List<Bitmap> gambar = new ArrayList<>();
                    mParentActivity.get().orderListArrayList = new ArrayList<>();
                    for (int index = 0; index < mParentActivity.get().id_barang.size(); index++) {
                        try {
                            gambar.add(index, new ImageCaching().getImage(
                                    Objects.requireNonNull(Objects.requireNonNull(mParentActivity.get().getContext())
                                            .getExternalFilesDir("Images"))
                                            .getCanonicalPath() +
                                            mParentActivity.get().id_barang.get(index)));
                            mParentActivity.get().orderListArrayList.add(new Cart_Order_OrderListCardViewDataModel(
                                    gambar.get(index),
                                    mParentActivity.get().nama.get(index),
                                    mParentActivity.get().harga.get(index),
                                    mParentActivity.get().pack.get(index),
                                    mParentActivity.get().cartTotalPrice,
                                    mParentActivity.get().id_barang.get(index),
                                    mParentActivity.get().getContext(),
                                    mParentActivity.get().satuan.get(index)
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
            mParentActivity.get().cartTotalPrice.setText(totalPriceString);
            mParentActivity.get().totalItem.setText(
                    "Total pesanan : " + totalItem + " item(s)");
        }
    }
}
