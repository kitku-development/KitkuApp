package com.kitku.kitku.Checkout.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitku.kitku.R;

public class Checkout_SetAdress_SpinnerAdapter extends BaseAdapter {

    private Context context;
    private int[] spinnerImages;
    private String[] spinnerTexts;
    private LayoutInflater layoutInflater;

    public Checkout_SetAdress_SpinnerAdapter(Context context, int[] spinnerImages, String[] spinnerTexts) {
        this.context = context;
        this.spinnerImages = spinnerImages;
        this.spinnerTexts = spinnerTexts;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return spinnerImages.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.checkout_setaddres_item_spn_layout, null);
        ImageView imageSetAddressSpinner = view.findViewById(R.id.imageSetAddressSpinner);
        TextView textSetAddressSpinner = view.findViewById(R.id.textSetAddressSpinner);
        imageSetAddressSpinner.setImageResource(spinnerImages[position]);
        textSetAddressSpinner.setText(spinnerTexts[position]);

        return view;
    }
}
