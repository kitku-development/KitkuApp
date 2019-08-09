package com.kitku.kitku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class UserMenuAdapter extends RecyclerView.Adapter<UserMenuAdapter.ViewHolder> {
    private ArrayList<UserMenuModel> MenuModelArray;

    UserMenuAdapter(ArrayList<UserMenuModel> MenuModelArray) {
        this.MenuModelArray = MenuModelArray;
    }

    @NonNull
    @Override
    public UserMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_menu_view, viewGroup, false);
        return new UserMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMenuAdapter.ViewHolder ViewHolder, int position) {
        ViewHolder.menuText.setText(MenuModelArray.get(position).menuText_getText());
        ViewHolder.menuMessage = MenuModelArray.get(position).menuMessage_getText();
        ViewHolder.fragmentView = MenuModelArray.get(position).fragment_getView();
        ViewHolder.tag = MenuModelArray.get(position).tag_whichUser();
        MenuModelArray.get(position).set_upperBorderID(ViewHolder.upperBorder);
        MenuModelArray.get(position).set_lowerBorderID(ViewHolder.lowerBorder);
    }

    @Override
    public int getItemCount() { return MenuModelArray.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView menuText;
        String menuMessage, tag;
        Fragment fragmentView;
        View upperBorder, lowerBorder;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            menuText    = itemView.findViewById(R.id.UserMenuText);
            upperBorder = itemView.findViewById(R.id.UpperBorder);
            lowerBorder = itemView.findViewById(R.id.LowerBorder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(menuText.getText())
                            .setMessage(menuMessage)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                if (!menuText.getText().equals("Logout")) dialogInterface.dismiss();
                                else {
                                        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(
                                        itemView.getContext().getApplicationContext());
                                        SharedPreferences.Editor userDataEdit = userData.edit();
                                        if (tag.equals("User"))
                                            userDataEdit.remove("ID_User");
                                        else
                                            userDataEdit.remove("ID_Mitra");
                                        userDataEdit.apply();
                                        Objects.requireNonNull(fragmentView.getView())
                                                .setVisibility(View.GONE);
                                        Fragment LoginFragment = new LoginFragment();
                                        FragmentManager fragmentManager = fragmentView.getChildFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(fragmentView.getId(), LoginFragment);
                                        fragmentTransaction.commit();
                                }}})
                            .setNegativeButton(R.string.calcel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss(); }})
                            .show();
                }
            });

        }
    }
}
