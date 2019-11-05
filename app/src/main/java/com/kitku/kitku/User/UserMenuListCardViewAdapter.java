package com.kitku.kitku.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kitku.kitku.PartnerFragment;
import com.kitku.kitku.User.UserMenu.BlogActivity;
import com.kitku.kitku.User.UserMenu.FAQActivity;
import com.kitku.kitku.R;
import com.kitku.kitku.User.UserMenu.TermsAndConditionsActivity;

import java.util.ArrayList;

public class UserMenuListCardViewAdapter extends RecyclerView.Adapter<UserMenuListCardViewAdapter.MenuListViewHolder> {

    private ArrayList<UserMenuListCardViewDataModel> menuListDataModel;

    public UserMenuListCardViewAdapter(ArrayList<UserMenuListCardViewDataModel> menuListDataModel) {
        this.menuListDataModel = menuListDataModel;
    }

    @NonNull
    @Override
    public MenuListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_menu_item_rv_layout, viewGroup, false);
        return new MenuListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuListViewHolder menuListViewHolder, final int position) {

        menuListViewHolder.textMenuName.setText(menuListDataModel.get(menuListViewHolder.getAdapterPosition()).getText_name_list_menu_item());
        menuListViewHolder.listView = menuListDataModel.get(menuListViewHolder.getAdapterPosition()).getListView();
        final String tag = menuListDataModel.get(menuListViewHolder.getAdapterPosition()).getTag();

        menuListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListViewHolder.getAdapterPosition() == 0) {
                    // Event yang akan terjadi jika User menekan tombol Blog berada di sini
                    Intent intent = new Intent(v.getContext(), BlogActivity.class);
                    v.getContext().startActivity(intent);
                } else if (menuListViewHolder.getAdapterPosition() == 1) {
                    // Event yang akan terjadi jika User menekan tombol Syarat & Ketentuan berada di sini
                    Intent intent = new Intent(v.getContext(), TermsAndConditionsActivity.class);
                    v.getContext().startActivity(intent);
                } else if (menuListViewHolder.getAdapterPosition() == 2) {
                    // Event yang akan terjadi jika User menekan tombol FAQ berada di sini
                    Intent intent = new Intent(v.getContext(), FAQActivity.class);
                    v.getContext().startActivity(intent);
                } else if (menuListViewHolder.getAdapterPosition() == 3) {
                    // Event yang akan terjadi jika User menekan tombol Logout berada di sini
                    loggingOut(v.getContext(), menuListViewHolder.listView, tag);
                }
            }
        });

        menuListViewHolder.btnMenuAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (menuListViewHolder.getAdapterPosition() == 0) {
                    // Event yang akan terjadi jika User menekan tombol Blog berada di sini
                    Intent intent = new Intent(v.getContext(), BlogActivity.class);
                    v.getContext().startActivity(intent);
                } else if (menuListViewHolder.getAdapterPosition() == 1) {
                    // Event yang akan terjadi jika User menekan tombol Syarat & Ketentuan berada di sini
                    Intent intent = new Intent(v.getContext(), TermsAndConditionsActivity.class);
                    v.getContext().startActivity(intent);
                } else if (menuListViewHolder.getAdapterPosition() == 2) {
                    // Event yang akan terjadi jika User menekan tombol FAQ berada di sini
                    Intent intent = new Intent(v.getContext(), FAQActivity.class);
                    v.getContext().startActivity(intent);
                } else if (menuListViewHolder.getAdapterPosition() == 3) {
                    // Event yang akan terjadi jika User menekan tombol Logout berada di sini
                    loggingOut(v.getContext(), menuListViewHolder.listView, tag);
                }
            }
        });
    }

    private void loggingOut(Context c, final View listView, final String tag) {
        new AlertDialog.Builder(c)
                .setTitle("Logout")
                .setMessage("Apakah anda ingin logout?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (tag.equals("User"))
                            UserFragment.loggingOut(listView);
                        else
                            PartnerFragment.loggingOut(listView);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
                                    /*if (!menuText.equals("Logout")) dialogInterface.dismiss();
                                    else {
                                        SharedPreferences userData =
                                                PreferenceManager.getDefaultSharedPreferences(v.getContext());
                                        SharedPreferences.Editor userDataEdit = userData.edit();
                                        if (tag.equals("User"))
                                            userDataEdit.remove("ID_User");
                                        else userDataEdit.remove("ID_Mitra");
                                        userDataEdit.apply();
                                        pageview.setVisibility(View.GONE);
                                        pageview.setId(View.generateViewId());
                                        Fragment LoginFragment = new LoginFragment();
                                        FragmentManager fragmentManager = childfragment;
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(pageview.getId(), LoginFragment);
                                        fragmentTransaction.commit();
                                        //pageview = inflater.inflate(R.layout.fragment_login, container, false);
                                    //}
                            }})
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();}})
                            .show();
                                     */
                //}

    @Override
    public int getItemCount() {
        return menuListDataModel.size();
    }

    public class MenuListViewHolder extends RecyclerView.ViewHolder {

        TextView textMenuName;
        Button btnMenuAction;
        View listView;

        public MenuListViewHolder(@NonNull View itemView) {
            super(itemView);

            textMenuName = itemView.findViewById(R.id.textUserMenuName);
            btnMenuAction = itemView.findViewById(R.id.btnUserMenuAction);

        }
    }
}
