package com.kitku.kitku.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kitku.kitku.R;

public class Checkout_SetAddressActivity extends AppCompatActivity {

    private Button buttonSaveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__set_address);

        buttonSaveAddress = findViewById(R.id.buttonCheckout_SetAddressSaveAddress);

    }

    public void saveAddress(View view) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }
}
