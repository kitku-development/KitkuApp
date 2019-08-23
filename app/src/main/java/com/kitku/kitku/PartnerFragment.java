package com.kitku.kitku;


import android.content.SharedPreferences;
//import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;
import com.kitku.kitku.BackgroundProcess.z_AsyncServerAccess;
//import com.kitku.kitku.Login.LoginFragment;
import com.kitku.kitku.Login.LoginProcedure;

//import java.lang.ref.WeakReference;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {

    private TextView userNameText;
    private ProgressBar loadingIndicator;

    public PartnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(
                Objects.requireNonNull(getContext()).getApplicationContext());
        // Jika mitra sudah login -> load layout fragment partner,
        // jika tidak -> load layout fragment login
        if (data.contains("ID_Mitra")) {
            view = inflater.inflate(R.layout.fragment_partner, container, false);
            userNameText     = view.findViewById(R.id.textviewUser_Username);
            loadingIndicator = view.findViewById(R.id.loadingIndicator);
            runAsync();
            loadData.execute(z_BackendPreProcessing.URL_SupplierData +
                    data.getString("ID_Mitra", null), null);
        }
        else {
            view = inflater.inflate(R.layout.fragment_login, container, false);
            loginLayoutMod(view);
        }

        // Inflate the layout for this fragment
        return view;
    }

    static class backgroundTask extends z_AsyncServerAccess {
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
                    String[] supplierData = new z_BackendPreProcessing().readSupplierData(output);
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
}
