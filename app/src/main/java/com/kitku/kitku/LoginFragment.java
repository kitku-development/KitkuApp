package com.kitku.kitku;

import android.app.Notification;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    Button buttonfragmentlogin_Login;
    TextView textviewLoginGoToRegister;
    RelativeLayout layoutLoginFragment;
    Toast notifikasi;
    View view;
    ProgressBar loadingIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        buttonfragmentlogin_Login = view.findViewById(R.id.buttonfragmentlogin_Login);
        textviewLoginGoToRegister = view.findViewById(R.id.textviewLoginGoToRegister);
        layoutLoginFragment = view.findViewById(R.id.layoutLoginFragment);
        loadingIndicator = new ProgressBar(view.getContext(), null, android.R.attr.progressBarStyleLarge);
        loadingIndicator.setVisibility(View.GONE);
        layoutLoginFragment.addView(loadingIndicator);

        textviewLoginGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        /* Method yang akan mengarahkan pengguna ke Fragment User jika menekan tombol login pada Fragment Login */

        buttonfragmentlogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*layoutLoginFragment.setVisibility(View.GONE);

                Fragment userDetailFragment = new UserFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameFragmentContainerLoginGoToUserDetailFragment, userDetailFragment);
                fragmentTransaction.commit();*/
                loginActivity(view);
            }
        });

        return view;
    }

    protected void loginActivity(View v){
        EditText
                emailbox    = v.findViewById(R.id.edittextLoginEmail),
                passbox     = v.findViewById(R.id.edittextLoginPassword);

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
                json = new z_BackendPreProcessing().loginPelanggan(
                        emailbox.getText().toString(),
                        passbox.getText().toString()
                );
            } catch (Exception e) { e.printStackTrace(); }

            // Kirim data
            login();
            sendData.execute(z_BackendPreProcessing.URL_LoginPelanggan,json);
            loadingIndicator.setVisibility(View.VISIBLE);
        }
    }


    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    protected void login() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                String response;
                try {
                    response = new JSONObject(output).getString("message");
                    if (response.equals("Login Success.")) {
                        layoutLoginFragment.setVisibility(View.GONE);

                        Fragment userDetailFragment = new UserFragment();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameFragmentContainerLoginGoToUserDetailFragment, userDetailFragment);
                        fragmentTransaction.commit();
                    } else {
                        notifikasi.setText("Login gagal!\n" +
                                "Silakan periksa kembali data anda.");
                        notifikasi.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loadingIndicator.setVisibility(View.GONE);
            }
        });
    }
}
