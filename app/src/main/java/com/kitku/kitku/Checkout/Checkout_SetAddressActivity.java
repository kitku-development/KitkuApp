package com.kitku.kitku.Checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.kitku.kitku.Checkout.SpinnerAdapter.Checkout_SetAdress_SpinnerAdapter;
import com.kitku.kitku.R;

public class Checkout_SetAddressActivity extends AppCompatActivity {

    private Button buttonSaveAddress;
    private Spinner spinnerSetAddress, spinnerSetProvince, spinnerSetCity, spinnerSetDistrict, spinnerSetSubDistrict;
    private Checkout_SetAdress_SpinnerAdapter spinnerAdapter;

    private String[] setAddressText = {"Rumah", "Kantor", "Takeaway"};
    private int[] setAddressImage = {   R.drawable.setaddress_sethome_icon,
                                        R.drawable.setaddress_setoffice_icon,
                                        R.drawable.setaddress_set_takeaway_icon
                                    };
    private String[] setAddressProvince = {
            "Aceh", "Bengkulu", "Jambi", "Kep. Bangka Belitung", "Kep. Riau",
            "Lampung", "Riau", "Sumatera Barat", "Sumatera Selatan", "Sumatera Utara"};
    private String[] setAddressCity = {
            "Kabupaten Agam", "Kabupaten Dharmasraya", "Kabupaten Kep. Mentawai", "Kabupaten Lima Puluh Kota",
            "Kabupaten Padang Pariaman", "Kabupaten Pasaman", "Kabupaten Pasaman Barat", "Kabupaten Pesisir Selatan",
            "Kabupaten Sijunjung", "Kabupaten Solok", "Kabupaten Solok Selatan", "Kabupaten Tanah Datar",
            "Kota Bukittinggi", "Kota Padang", "Kota Padang Panjang", "Kota Pariaman",
            "Kota Payakumbuh", "Kota Sawahlunto", "Kota Solok"};
    private String[] setAddressDistrict = {
            "Bungus Teluk Kabung", "Koto Tangah", "Kuranji", "Lubuk Begalung", "Lubuk Kilangan", "Nanggalo",
            "Padang Barat", "Padang Selatan", "Padang Timur", "Padang Utara", "Pauh"};
    private String[] setAddressSubDistrict = {
            "Air Manis", "Air Pacah", "Air Tawar Barat", "Air Tawar Timur", "Alai Parak Kopi", "Alang Laweh",
            "Ampang", "Andaleh", "Anduring", "Balai Gadang", "Bandar Buat", "Banuaran Nan XX",
            "Batang Arau", "Batang Kabung", "Batipuh Panjang", "Batu Gadang", "Batuang Taba Nan XX", "Belakang Pondok",
            "Belakang Tangsi", "Beringin", "Berok Nipah", "Binuang Kampung", "Bukik Gado-gado", "Bungo Pasang",
            "Bungus Barat", "Bungus Selatan", "Bungus Timur", "Cengkeh Nan XX", "Cupak Tangah", "Dadok Tunggul Hitam",
            "Flamboyan Baru", "Ganting Parak Gadang", "Gateh Nan XX", "Gunung Pangilun", "Gunung Sarik", "Gurun Laweh Nan XX",
            "Gurun Laweh", "Indarung", "Jati Baru", "Jati", "Kalumbuk", "Kampung Baru Nan XX",
            "Kampung Jao", "Kampung Jua Nan XX", "Kampung Lapai", "Kampung Olo", "Kampung Pondok", "Kapalo Koto",
            "Korong Gadang", "Koto Baru Nan XX", "Koto Lalang", "Koto Luar", "Koto Panjang Ikua Koto", "Koto Pulai",
            "Kubu Marapalam", "Kubu Parak Karakah", "Kuranji", "Kurao Pagang", "Lambung Bukit", "Limau Manis Selatan",
            "Limau Manis", "Lolong Belanti", "Lubuk Begalung Nan XX", "Lubuk Buaya", "Lubuk Lintah", "Lubuk Minturun",
            "Mato Aie", "Olo Ladang", "Padang Besi", "Padang Pasir", "Padang Sarai", "Pagambiran Ampalu Nan XX",
            "Pampangan Nan XX", "Parak Gadang Timur", "Parak Laweh Pulau Air Nan XX", "Parupuk Tabing", "Pasa Gadang",
            "Pasar Ambacang", "Pasir Nan Tigo", "Piai Tangah", "Pisang", "Pitameh Tanjung Saba Nan XX", "Purus",
            "Ranah Parak Rumbio", "Rawang", "Rimbo Kaluang", "Sawahan Timur", "Sawahan", "Seberang Padang",
            "Seberang Palinggam", "Simpang Haru", "Sungai Sapih", "Surau Gadang", "Tabing Banda Gadang", "Tanah Sirah Piai Nan XX",
            "Tanjung Aur Nan XX", "Tarantang", "Teluk Bayur", "Teluk Kabung Selatan", "Teluk Kabung Tengah", "Teluk Kabung Utara",
            "Ujung Gurun", "Ulak Karang Selatan", "Ulak Karang Utara"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__set_address);

        buttonSaveAddress = findViewById(R.id.buttonCheckout_SetAddressSaveAddress);
        spinnerSetAddress = findViewById(R.id.spinnerAddressLabel);
        spinnerSetProvince = findViewById(R.id.spinnerAddressProvince);
        spinnerSetCity = findViewById(R.id.spinnerAddressCity);
        spinnerSetDistrict = findViewById(R.id.spinnerAddressDistrict);
        spinnerSetSubDistrict = findViewById(R.id.spinnerAddressSubDistrict);

        spinnerSetAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),
                        "Set Alamat Pengiriman : Alamat" + setAddressText[position],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = new Checkout_SetAdress_SpinnerAdapter(getApplicationContext(), setAddressImage, setAddressText);
        spinnerSetAddress.setAdapter(spinnerAdapter);

        final ArrayAdapter<String> spinnerAdapterProvince = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, setAddressProvince);
        final ArrayAdapter<String> spinnerAdapterCity = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, setAddressCity);
        final ArrayAdapter<String> spinnerAdapterDistrict = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, setAddressDistrict);
        final ArrayAdapter<String> spinnerAdapterSubDistrict = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, setAddressSubDistrict);

        spinnerSetProvince.setAdapter(spinnerAdapterProvince);
        spinnerSetCity.setAdapter(spinnerAdapterCity);
        spinnerSetDistrict.setAdapter(spinnerAdapterDistrict);
        spinnerSetSubDistrict.setAdapter(spinnerAdapterSubDistrict);

    }

    public void saveAddress(View view) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }
}
