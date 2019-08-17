package com.kitku.kitku;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kitku.kitku.BackgroundProcess.z_AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    Button buttonregister_Register;
    TextView textviewLoginGoToRegister;
    Toast notifikasi;
    private ProgressDialog loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingIndicator.setIndeterminate(true);
        //loadingIndicator.show();

        buttonregister_Register = findViewById(R.id.buttonregister_Register);
        textviewLoginGoToRegister = findViewById(R.id.textviewLoginGoToRegister);

        /* Mengirimkan data intent untuk dapat menampilkan Fragment Login kepada pengguna */

        textviewLoginGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(RegisterActivity.this, MainActivity.class);
                intentLogin.putExtra("goToLoginFragment", true);
                startActivity(intentLogin);
            }
        });

        // Button Register kirim data
        buttonregister_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText
                        namebox     = findViewById(R.id.edittextRegisterName),
                        emailbox    = findViewById(R.id.edittextRegisterEmail),
                        passbox     = findViewById(R.id.edittextRegisterPassword),
                        addressbox  = findViewById(R.id.edittextRegisterAddress),
                        contactbox  = findViewById(R.id.edittextRegisterPhoneNumber);

                notifikasi = Toast.makeText(RegisterActivity.this," ",Toast.LENGTH_LONG);
                // Cek apakah data sudah terisi
                if (
                        namebox.getText().toString().matches("")      ||
                                emailbox.getText().toString().matches("")     ||
                                passbox.getText().toString().matches("")      ||
                                addressbox.getText().toString().matches("")   ||
                                contactbox.getText().toString().matches("")
                ) {
                    notifikasi.setText("Maaf. Mohon lengkapi kembali data anda.");
                    notifikasi.show();
                }
                else {
                    // Convert data dari string menjadi JSON
                    String json = null;
                    try {
                        json = new z_BackendPreProcessing().registerUser(
                                namebox.getText().toString(),
                                emailbox.getText().toString(),
                                passbox.getText().toString(),
                                addressbox.getText().toString(),
                                contactbox.getText().toString()
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Kirim data
                    register();
                    sendData.execute(z_BackendPreProcessing.URL_UserRegister,json);
                    loadingIndicator.show();
                }
            }
        });
    }

    /* Mengirimkan data intent untuk dapat menampilkan Fragment User kepada pengguna */

    public void goToLoginFragmentClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("goToUserFragment", true);
        startActivity(intent);
    }

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    protected void register() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                String response;
                Log.d("",output);
                try {
                    response = new JSONObject(output).getString("message");
                    if (response.equals("Pelanggan registered.")) {
                        notifikasi.setText("Berhasil mendaftar! Silakan Login.");
                        notifikasi.show();
                    } else {
                        notifikasi.setText("Registrasi gagal!\n" +
                                "Apakah anda sudah pernah mendaftar sebelumnya?");
                        notifikasi.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    notifikasi.setText("Registrasi gagal!\n" +
                            "Apakah anda sudah pernah mendaftar sebelumnya?");
                    notifikasi.show();
                }
                loadingIndicator.dismiss();
            }
        });
    }
}
