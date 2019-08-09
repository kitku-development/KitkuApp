package com.kitku.kitku;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart_DoneFragment extends Fragment {

    private RecyclerView recyclerviewDoneList;
    private Cart_Done_OrderDoneCardViewAdapter cartDoneListAdapter;
    private ArrayList<Cart_Done_OrderDoneCardViewDataModel> doneListArrayList = new ArrayList<>();

    public Cart_DoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View doneView = inflater.inflate(R.layout.fragment_cart__done, container, false);

        recyclerviewDoneList = doneView.findViewById(R.id.recyclerviewCart_DoneOrderList);
        recyclerviewDoneList.setHasFixedSize(true);

        return doneView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addData();
        showData();

    }

    private void showData() {
        recyclerviewDoneList.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartDoneListAdapter = new Cart_Done_OrderDoneCardViewAdapter(doneListArrayList);
        recyclerviewDoneList.setAdapter(cartDoneListAdapter);
    }

    private void addData() {
        doneListArrayList = new ArrayList<>();

        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));
        doneListArrayList.add(new Cart_Done_OrderDoneCardViewDataModel
                (R.drawable.image_sayur_bawangbombay, "Bawang Bombay",
                        "Sudah diterima"));

    }

}
