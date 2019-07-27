package com.kitku.kitku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemCategoryCardViewAdapter extends RecyclerView.Adapter<ListItemCategoryCardViewAdapter.ListItemCategoryCardViewViewHolder> {

    private ArrayList<ListItemCategoryCardViewDataModel> listItemCategoryCardViewDataModels;

    /* Constructor untuk ArrayList */

    ListItemCategoryCardViewAdapter(ArrayList<ListItemCategoryCardViewDataModel> listItemCategoryCardViewDataModels) {
        this.listItemCategoryCardViewDataModels = listItemCategoryCardViewDataModels;
    }

    /* Mendefinisikan view yang akan berada dalam Layout ListItem Activity */

    @NonNull
    @Override
    public ListItemCategoryCardViewAdapter.ListItemCategoryCardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_category_item_layout, viewGroup, false);
        return new ListItemCategoryCardViewViewHolder(view);
    }

    /* Method untuk memberikan masukan kedalam View yang ada di Layout listitem_category_item */

    @Override
    public void onBindViewHolder(@NonNull ListItemCategoryCardViewAdapter.ListItemCategoryCardViewViewHolder listItemCategoryCardViewViewHolder, int position) {
        listItemCategoryCardViewViewHolder.imageCategoryItem.setImageBitmap(listItemCategoryCardViewDataModels.get(position).getImage_category_item());
        listItemCategoryCardViewViewHolder.imageCategoryItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        listItemCategoryCardViewViewHolder.textnameCategoryItem.setText(listItemCategoryCardViewDataModels.get(position).getText_name_category_item());
        listItemCategoryCardViewViewHolder.textpriceCategoryItem.setText(listItemCategoryCardViewDataModels.get(position).getText_price_category_item());
        listItemCategoryCardViewViewHolder.textpackCategoryItem.setText(listItemCategoryCardViewDataModels.get(position).getText_pack_category_item());
        listItemCategoryCardViewViewHolder.id_barang = listItemCategoryCardViewDataModels.get(position).getId_barang();
    }

    @Override
    public int getItemCount() {
        return listItemCategoryCardViewDataModels.size();
    }

    /* Class ViewHolder untuk menyimpan dan mendefinisikan View yang berada dalam RecyclerView nantinya */

    class ListItemCategoryCardViewViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCategoryItem;
        TextView textnameCategoryItem, textpriceCategoryItem, textpackCategoryItem;
        String id_barang;

        ListItemCategoryCardViewViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageCategoryItem = itemView.findViewById(R.id.imageCategoryItem);
            textnameCategoryItem = itemView.findViewById(R.id.textnameCategoryItem);
            textpriceCategoryItem = itemView.findViewById(R.id.textpriceCategoryItem);
            textpackCategoryItem = itemView.findViewById(R.id.textpackCategoryItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Detail_ItemActivity.class);
                    intent.putExtra("id", id_barang);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
