package com.kitku.kitku;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cart_Done_OrderDoneCardViewAdapter extends RecyclerView.Adapter<Cart_Done_OrderDoneCardViewAdapter.DoneListViewHolder> {

    private ArrayList<Cart_Done_OrderDoneCardViewDataModel> doneListDataModel;

    public Cart_Done_OrderDoneCardViewAdapter(ArrayList<Cart_Done_OrderDoneCardViewDataModel> doneListDataModel) {
        this.doneListDataModel = doneListDataModel;
    }

    @NonNull
    @Override
    public Cart_Done_OrderDoneCardViewAdapter.DoneListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cartdone_orderlistitem_reyclerview_layout, viewGroup, false);

        return new DoneListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Done_OrderDoneCardViewAdapter.DoneListViewHolder doneListViewHolder, int position) {

        doneListViewHolder.itemImage.setImageResource(doneListDataModel.get(position).getImage_donelist_item());
        doneListViewHolder.textItemName.setText(doneListDataModel.get(position).getText_name_donelist_item());
        doneListViewHolder.textItemInfo.setText(doneListDataModel.get(position).getText_info_donelist_item());

    }

    @Override
    public int getItemCount() {
        return doneListDataModel.size();
    }

    public class DoneListViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView textItemName, textItemInfo;


        public DoneListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.imageDoneListItem);
            textItemName = itemView.findViewById(R.id.textnameDoneListItem);
            textItemInfo = itemView.findViewById(R.id.textdoneinfoDoneListItem);

        }
    }
}
