package com.kitku.kitku.Cart.Cart_OnProgress;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitku.kitku.R;

import java.util.ArrayList;

public class Cart_OnProgress_OrderProgressCardViewAdapter
        extends RecyclerView.Adapter<Cart_OnProgress_OrderProgressCardViewAdapter.ProgressListViewHolder> {

    private ArrayList<Cart_OnProgress_OrderProgressCardViewDataModel> progressListDataModel;

    public Cart_OnProgress_OrderProgressCardViewAdapter(ArrayList<Cart_OnProgress_OrderProgressCardViewDataModel> progressListDataModel) {
        this.progressListDataModel = progressListDataModel;
    }

    @NonNull
    @Override
    public Cart_OnProgress_OrderProgressCardViewAdapter.ProgressListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cartonprogress_orderlistitem_rv_layout, viewGroup, false);

        return new ProgressListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Cart_OnProgress_OrderProgressCardViewAdapter.ProgressListViewHolder progressListViewHolder, int position) {

        progressListViewHolder.itemImage.setImageResource(progressListDataModel.get(position).getImage_onprogresslist_item());
        progressListViewHolder.textItemName.setText(progressListDataModel.get(position).getText_name_onprogresslist_item());
        progressListViewHolder.textItemProgress.setText(progressListDataModel.get(position).getText_progress_onprogresslist_item());

    }

    @Override
    public int getItemCount() {
        return progressListDataModel.size();
    }

    public class ProgressListViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView textItemName, textItemProgress;

        public ProgressListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.imageOnProgressListItem);
            textItemName = itemView.findViewById(R.id.textnameOnProgressListItem);
            textItemProgress = itemView.findViewById(R.id.textprogressOnProgressListItem);

        }
    }
}
