package com.kitku.kitku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    Button buttonregister_Register;
    TextView textviewLoginGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

    }

    /* Mengirimkan data intent untuk dapat menampilkan Fragment User kepada pengguna */

    public void goToLoginFragmentClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("goToUserFragment", true);
        startActivity(intent);
    }
}
