package com.kitku.kitku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import org.json.JSONObject;

import java.util.Objects;

class LoginProcedure {

    View view;
    private RelativeLayout layoutLoginFragment;
    private Toast notifikasi;
    private ProgressDialog loadingIndicator;
    //private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit, mitraDataEdit;
    private FragmentManager fManager;
    private String condition;
    private Context context;

    void loginActivity(View v, String condition, Context context, FragmentManager fragmentz){
        this.condition  = condition;
        this.view       = v;
        this.context    = context;
        this.fManager   = fragmentz;
        loadingIndicator = new ProgressDialog(view.getContext());
        loadingIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingIndicator.setIndeterminate(true);

        EditText emailbox = v.findViewById(R.id.edittextLoginEmail);
        EditText passbox = v.findViewById(R.id.edittextLoginPassword);

        notifikasi = Toast.makeText(v.getContext()," ",Toast.LENGTH_LONG);

        if(
                emailbox.getText().toString().matches("") ||
                passbox.getText().toString().matches("")
        ){
            notifikasi.setText("Silakan isi email dan password anda.");
            notifikasi.show();
        }
        else {
            // Ubah data menjadi JSON
            String json = null;
            try {
                json = new z_BackendPreProcessing().loginUser(
                        emailbox.getText().toString(),
                        passbox.getText().toString()
                );
            } catch (Exception e) { e.printStackTrace(); }

            // Kirim data
            login();
            if (condition.equals("User"))
                sendData.execute(z_BackendPreProcessing.URL_UserLogin,json);
            else sendData.execute(z_BackendPreProcessing.URL_SupplierLogin,json);
            loadingIndicator.show();
        }
    }

    void gotoPage(String condition, FragmentManager fManager, View v) {
        layoutLoginFragment = v.findViewById(R.id.layoutLoginFragment);
        layoutLoginFragment.setVisibility(View.GONE);
        Fragment fragments;
        if (condition.equals("User"))
            fragments = new UserFragment();
        else fragments = new PartnerFragment();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameFragmentContainerLoginGoToUserDetailFragment, fragments);
        fragmentTransaction.commit();
    }

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    private backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    private void login() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                String response;
                try {
                    response = new JSONObject(output).getString("message");
                    if (response.contains("PEL-") && condition.equals("User")) {
                        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(
                                Objects.requireNonNull(context.getApplicationContext()));
                        userDataEdit = userData.edit();
                        userDataEdit.putString("ID_User", response);
                        userDataEdit.apply();
                        gotoPage(condition, fManager, view);
                    }
                    else if (response.contains("SUP-") && condition.equals("Mitra")) {
                        SharedPreferences mitraData = PreferenceManager.getDefaultSharedPreferences(
                                Objects.requireNonNull(context.getApplicationContext()));
                        mitraDataEdit = mitraData.edit();
                        mitraDataEdit.putString("ID_Mitra", response);
                        mitraDataEdit.apply();
                        gotoPage(condition, fManager, view);
                    }
                    else {
                        notifikasi.setText("Login gagal!\n" +
                                "Silakan periksa kembali data anda.");
                        notifikasi.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loadingIndicator.dismiss();
            }
        });
    }
}
