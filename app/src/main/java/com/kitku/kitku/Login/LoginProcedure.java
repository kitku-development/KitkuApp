package com.kitku.kitku.Login;

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

import com.kitku.kitku.BackgroundProcess.AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.BackendPreProcessing;
import com.kitku.kitku.PartnerFragment;
import com.kitku.kitku.R;
import com.kitku.kitku.User.UserFragment;

import org.json.JSONObject;

import java.util.Objects;

public class LoginProcedure {

    private View view;
    private Toast notifikasi;
    private ProgressDialog loadingIndicator;
    //private SharedPreferences userData;
    private SharedPreferences.Editor userDataEdit, mitraDataEdit;
    private FragmentManager fManager;
    private String condition;
    private Context context;

    public void loginActivity(View v, String condition, Context context, FragmentManager fragmentz){
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
                json = BackendPreProcessing.loginUserAndMitra(
                        emailbox.getText().toString(),
                        passbox.getText().toString()
                );
            } catch (Exception e) { e.printStackTrace(); }

            // Kirim data
            login();
            if (condition.equals("User"))
                sendData.execute(BackendPreProcessing.URL_UserLogin,json);
            else sendData.execute(BackendPreProcessing.URL_SupplierLogin,json);
            loadingIndicator.show();
        }
    }

    // Prosedur untuk menampilkan fragment lanjutan (baik user maupun mitra)
    void gotoPage(String condition, FragmentManager fManager, View v) {
        RelativeLayout layoutLoginFragment = v.findViewById(R.id.layoutLoginFragment);
        layoutLoginFragment.setVisibility(View.GONE);
        Fragment fragments;
        if (condition.equals("User"))
            fragments = new UserFragment();
        else fragments = new PartnerFragment();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameFragmentContainerLoginGoToUserDetailFragment, fragments);
        fragmentTransaction.commit();
    }

    // Inisiasi AsyncTask dari AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends AsyncServerAccess {
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
