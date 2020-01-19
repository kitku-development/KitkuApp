package com.kitku.kitku;


import android.content.SharedPreferences;
//import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kitku.kitku.BackgroundProcess.AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.BackendPreProcessing;
//import com.kitku.kitku.Login.LoginFragment;
import com.kitku.kitku.Login.LoginProcedure;
import com.kitku.kitku.User.UserMenuListCardViewAdapter;
import com.kitku.kitku.User.UserMenuListCardViewDataModel;

//import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {

    private TextView userNameText;
    private ProgressBar loadingIndicator;
    private RecyclerView recyclerViewMenu;
    private ArrayList<UserMenuListCardViewDataModel> menuListArrayList = new ArrayList<>();
    private UserMenuListCardViewAdapter userMenuListAdapter;
    private View menuListView;
    private static SharedPreferences data;

    public PartnerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        data = PreferenceManager.getDefaultSharedPreferences(
                Objects.requireNonNull(getContext()).getApplicationContext());
        // Jika mitra sudah login -> load layout fragment partner,
        // jika tidak -> load layout fragment login
        if (data.contains("ID_Mitra")) {
            view = inflater.inflate(R.layout.fragment_partner, container, false);
            userNameText     = view.findViewById(R.id.textviewUser_Username);
            loadingIndicator = view.findViewById(R.id.loadingIndicator);
            runAsync();
            loadData.execute(BackendPreProcessing.URL_SupplierData +
                    data.getString("ID_Mitra", null), null);
            view.findViewById(R.id.buttonToolbarOrderTrackBack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.default_home_button();
                }
            });
            menuListView = view;
            recyclerViewMenu = menuListView.findViewById(R.id.recyclerviewUserMenu);
            recyclerViewMenu.setHasFixedSize(true);
            addMenuData();
            showMenuData();
        }
        else {
            view = inflater.inflate(R.layout.fragment_login, container, false);
            loginLayoutMod(view);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    static class backgroundTask extends AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    private backgroundTask loadData;
    // Alur setelah AsyncTask selesai
    private void runAsync() {
        loadData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    //Log.d("out",output);
                    String[] supplierData = BackendPreProcessing.readSupplierData(output);
                    userNameText.setText(supplierData[0]);
                    loadingIndicator.setVisibility(View.GONE);
                    //new downloadImage(UserFragment.this).execute();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    // Modifikasi layout login untuk mitra
    private void loginLayoutMod(final View v) {
        // Modifikasi pesan yang ditampilkan
        TextView messageText = v.findViewById(R.id.txtLogin1);
        TextView mitraText   = v.findViewById(R.id.txtLogin2);
        messageText.setText("Silakan login untuk dapat mengelola akun mitra.");
        mitraText.setText("Login Mitra");
        // WARNING : Use this 4 lines code in development only!!!
        EditText emailMitra = v.findViewById(R.id.edittextLoginEmail);
        EditText passMitra = v.findViewById(R.id.edittextLoginPassword);
        emailMitra.setText("tester@kitku.id");
        passMitra.setText("tester");

        // Hilangkan tombol
        v.findViewById(R.id.txtLogin3).setVisibility(View.GONE);
        v.findViewById(R.id.textviewLoginGoToRegister).setVisibility(View.GONE);

        v.findViewById(R.id.buttonfragmentlogin_Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginProcedure().loginActivity(
                        v,
                        "Mitra",
                        getContext(),
                        getChildFragmentManager());
            }
        });
    }

    private void showMenuData() {
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        userMenuListAdapter = new UserMenuListCardViewAdapter(menuListArrayList);
        recyclerViewMenu.setAdapter(userMenuListAdapter);
    }

    private void addMenuData() {
        menuListArrayList = new ArrayList<>();
        String tag = "Mitra";
        menuListArrayList.add(new UserMenuListCardViewDataModel("Blog", menuListView, tag));
        menuListArrayList.add(new UserMenuListCardViewDataModel("Syarat dan Ketentuan", menuListView, tag));
        menuListArrayList.add(new UserMenuListCardViewDataModel("FAQ", menuListView, tag));
        menuListArrayList.add(new UserMenuListCardViewDataModel("Logout", menuListView, tag));
    }

    // used to logout mitra
    public static void loggingOut(View menuListView) {
        SharedPreferences.Editor userDataEdit = data.edit();
        userDataEdit.remove("ID_Mitra");
        userDataEdit.apply();
        menuListView.setVisibility(View.GONE);
        //menuListView.setId(View.generateViewId());
        /*Fragment LoginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(FragmentLayout, LoginFragment);
        fragmentTransaction.commit();*/
        MainActivity.default_home_button();
    }
}
