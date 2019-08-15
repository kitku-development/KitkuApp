package com.kitku.kitku;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart_OrderFragment extends Fragment {

    private RecyclerView recyclerviewOrderList;
    private Cart_Order_OrderListCardViewAdapter cartOrderListAdapter;
    private ArrayList<Cart_Order_OrderListCardViewDataModel> orderListArrayList = new ArrayList<>();

    public Cart_OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View orderView = inflater.inflate(R.layout.fragment_cart__order, container, false);

        recyclerviewOrderList = orderView.findViewById(R.id.recyclerviewCart_OrderOrderList);
        recyclerviewOrderList.setHasFixedSize(true);

        return orderView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addData();
        showData();

    }

    private void showData() {
        recyclerviewOrderList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        cartOrderListAdapter = new Cart_Order_OrderListCardViewAdapter(orderListArrayList);
        recyclerviewOrderList.setAdapter(cartOrderListAdapter);
    }

    private void addData() {
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
    }

}
