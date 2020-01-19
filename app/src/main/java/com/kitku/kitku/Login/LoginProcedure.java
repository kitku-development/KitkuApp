package com.kitku.kitku.Login;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kitku.kitku.BackgroundProcess.AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.BackendPreProcessing;
import com.kitku.kitku.Model.BaseModel;
import com.kitku.kitku.PartnerFragment;
import com.kitku.kitku.R;
import com.kitku.kitku.User.UserFragment;

import org.json.JSONObject;

public class LoginProcedure {

    private View view;
    private Toast notification;
    private ProgressDialog loadingIndicator;
    private FragmentManager fManager;
    private String condition;
    private BaseModel model;

    public void loginActivity(View v, String condition, FragmentManager fragments,
                              BaseModel model) throws Exception {
        this.condition  = condition;
        this.view       = v;
        this.fManager   = fragments;
        this.model      = model;
        loadingIndicator = new ProgressDialog(view.getContext());
        loadingIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingIndicator.setIndeterminate(true);

        EditText emailBox = v.findViewById(R.id.edittextLoginEmail);
        EditText passBox = v.findViewById(R.id.edittextLoginPassword);

        notification = Toast.makeText(v.getContext()," ",Toast.LENGTH_LONG);

        if(
                emailBox.getText().toString().matches("") ||
                passBox.getText().toString().matches("")
        ){
            notification.setText("Silakan isi email dan password anda.");
            notification.show();
        }
        else {
            // Kirim data
            login();
            if (condition.equals("User")) {
                sendData.execute(BackendPreProcessing.URL_UserLogin, model.loginJson());
            } else {
                sendData.execute(BackendPreProcessing.URL_SupplierLogin, model.loginJson());
            }
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
                        model.setId(response);
                        gotoPage(condition, fManager, view);
                    } else if (response.contains("SUP-") && condition.equals("Mitra")) {
                        model.setId(response);
                        gotoPage(condition, fManager, view);
                    } else {
                        notification.setText("Login gagal!\n" + "Silakan periksa kembali data anda.");
                        notification.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loadingIndicator.dismiss();
            }
        });
    }
}
