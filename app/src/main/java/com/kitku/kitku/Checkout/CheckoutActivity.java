package com.kitku.kitku.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kitku.kitku.MainActivity;
import com.kitku.kitku.PaymentActivity;
import com.kitku.kitku.R;

public class CheckoutActivity extends AppCompatActivity {

    private TextView textGoToChangeAddress, textGoToVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        textGoToChangeAddress = findViewById(R.id.textviewCheckoutChangeAddress);
        textGoToVoucher = findViewById(R.id.textviewCheckoutGoToVoucher);

        textGoToChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Checkout_SetAddressActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setBackButton(View view) {
    }

    public void buttonToPayment(View view) {
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        startActivity(intent);
    }
}
