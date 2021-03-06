package com.kitku.kitku.Cart.Cart_OnProgress;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kitku.kitku.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart_OnProgressFragment extends Fragment {

    private RecyclerView recyclerviewProgressList;
    private Cart_OnProgress_OrderProgressCardViewAdapter cartProgressListAdapter;
    private ArrayList<Cart_OnProgress_OrderProgressCardViewDataModel> progressListArrayList = new ArrayList<>();


    public Cart_OnProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View progressView = inflater.inflate(R.layout.fragment_cart__on_progress, container, false);

        recyclerviewProgressList = progressView.findViewById(R.id.recyclerviewCart_OnProgressOrderList);
        recyclerviewProgressList.setHasFixedSize(true);

        return progressView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addData();
        showData();

    }

    private void showData() {
        recyclerviewProgressList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        cartProgressListAdapter =  new Cart_OnProgress_OrderProgressCardViewAdapter(progressListArrayList);
        recyclerviewProgressList.setAdapter(cartProgressListAdapter);
    }

    private void addData() {

        progressListArrayList = new ArrayList<>();

        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengemasan"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengiriman"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengemasan"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengiriman"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengemasan"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengiriman"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengemasan"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengiriman"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengemasan"));
        progressListArrayList.add(new Cart_OnProgress_OrderProgressCardViewDataModel
                (R.drawable.image_sayur_bawangbombay_compressed, "Bawang Bombay",
                        "Sedang dalam proses pengiriman"));

    }


}
