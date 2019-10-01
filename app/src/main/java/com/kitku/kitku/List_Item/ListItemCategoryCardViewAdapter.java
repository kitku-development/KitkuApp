package com.kitku.kitku.List_Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitku.kitku.Detail_ItemActivity;
import com.kitku.kitku.R;

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
        listItemCategoryCardViewViewHolder.jumlah = listItemCategoryCardViewDataModels.get(position).getJumlah();
        float transparency = (float) 0.5;
        if (listItemCategoryCardViewViewHolder.jumlah < 1) {
            listItemCategoryCardViewViewHolder.imageCategoryItem.setAlpha(transparency);
            listItemCategoryCardViewViewHolder.itemView.setOnClickListener(listItemCategoryCardViewViewHolder.StockEmpty_OnClick());
        }
        else listItemCategoryCardViewViewHolder.itemView.setOnClickListener(listItemCategoryCardViewViewHolder.StockExist_OnClick());
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
        int jumlah;
        View itemView;

        ListItemCategoryCardViewViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageCategoryItem = itemView.findViewById(R.id.imageCategoryItem);
            textnameCategoryItem = itemView.findViewById(R.id.textnameCategoryItem);
            textpriceCategoryItem = itemView.findViewById(R.id.textpriceCategoryItem);
            textpackCategoryItem = itemView.findViewById(R.id.textpackCategoryItem);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), Detail_ItemActivity.class);
                        intent.putExtra("id", id_barang);
                        itemView.getContext().startActivity(intent);
                    }
                });*/
        }

        View.OnClickListener StockExist_OnClick() {
            //itemView.setOnClickListener(
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(), Detail_ItemActivity.class);
                        intent.putExtra("id", id_barang);
                        itemView.getContext().startActivity(intent);

                    }
                };
            //});
        }

        View.OnClickListener StockEmpty_OnClick() {
            //itemView.setOnClickListener(
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(imageCategoryItem.getContext())
                            .setTitle("Informasi")
                            .setMessage("Stok barang kosong! \nSilakan periksa kembali nanti.")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }

            };
        }
    }
}
