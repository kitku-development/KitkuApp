package com.kitku.kitku.Checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kitku.kitku.BackgroundProcess.ImageCaching;
import com.kitku.kitku.BackgroundProcess.z_AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;
import com.kitku.kitku.Cart.Cart_Map.Cart_Map_Activity;
import com.kitku.kitku.Checkout.SpinnerAdapter.Checkout_SetAdress_SpinnerAdapter;
import com.kitku.kitku.R;

import java.io.File;
import java.util.Objects;

public class Checkout_SetAddressActivity extends AppCompatActivity {

    private static SharedPreferences userData;

    //private Button buttonSaveAddress;
    private Spinner spinnerSetAddress, spinnerSetProvince, spinnerSetCity, spinnerSetDistrict, spinnerSetSubDistrict;
    private Checkout_SetAdress_SpinnerAdapter spinnerAdapter;
    private EditText textSetReceiver, textSetContact, textSetAddress, textSetAdditionalInfo;

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

        //buttonSaveAddress = findViewById(R.id.buttonCheckout_SetAddressSaveAddress);
        spinnerSetAddress = findViewById(R.id.spinnerAddressLabel);
        spinnerSetProvince = findViewById(R.id.spinnerAddressProvince);
        spinnerSetCity = findViewById(R.id.spinnerAddressCity);
        spinnerSetDistrict = findViewById(R.id.spinnerAddressDistrict);
        spinnerSetSubDistrict = findViewById(R.id.spinnerAddressSubDistrict);
        textSetReceiver = findViewById(R.id.edittextSetAddressName);
        textSetContact = findViewById(R.id.edittextSetAddressPhoneNumber);
        textSetAddress = findViewById(R.id.edittextSetAddress);
        textSetAdditionalInfo = findViewById(R.id.edittextSetMoreInfo);

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

        setPredefinedSettings();

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

        findViewById(R.id.buttonCheckout_SetAddressSetOnGMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout_SetAddressActivity.this, Cart_Map_Activity.class);
                startActivity(intent);
                //Checkout_SetAddressActivity.this.finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
        Log.d("info","resumed");
        setPredefinedSettings();
    }

    public void onRestart() {
        super.onRestart();
        Log.d("info","restarted");
        setPredefinedSettings();
    }

    /*public void saveAddress(View view) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }*/

    /*public void warningBackgroundColor() {
        spinnerSetProvince.setBackgroundColor(Color.RED);
        spinnerSetCity.setBackgroundColor(Color.RED);
        spinnerSetDistrict.setBackgroundColor(Color.RED);
        spinnerSetSubDistrict.setBackgroundColor(Color.RED);
    }

    public void normalizeBackgroundColor(View v) {
        spinnerSetProvince.setBackgroundColor(Color.WHITE);
        spinnerSetCity.setBackgroundColor(Color.WHITE);
        spinnerSetDistrict.setBackgroundColor(Color.WHITE);
        spinnerSetSubDistrict.setBackgroundColor(Color.WHITE);
    }*/

    private void setPredefinedSettings() {
        // set pre-defined setting
        userData = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        try {
            if (!userData.contains("namaPenerima")) {
                runAsync();
                sendData.execute(z_BackendPreProcessing.URL_UserData +
                        userData.getString("ID_User", null), null);
                //warningBackgroundColor();
            }
            Log.d("fullAlamat", Objects.requireNonNull(userData.getString("fullAlamat", null)));
            if (userData.contains("fullAlamat")) {
                int i;
                // set label alamat
                for(i = 0; i < setAddressText.length; i++) {
                    if (!Objects.equals(userData.getString("labelAlamat", null), "")) {
                        if (Objects.equals(userData.getString("labelAlamat", null),
                                setAddressText[i]))
                            spinnerSetAddress.setSelection(i);
                    }
                }
                // set nama penerima
                textSetReceiver.setText(userData.getString("namaPenerima", null));
                // set kontak penerima
                textSetContact.setText(userData.getString("kontakPenerima", null));
                // set provinsi
                for (i = 0; i < setAddressProvince.length; i++) {
                    if (Objects.requireNonNull(userData.getString("fullAlamat", null))
                            .contains(setAddressProvince[i])) {
                        spinnerSetProvince.setSelection(i);
                        break;
                    }
                }
                // set kota
                for (i = 0; i < setAddressCity.length; i++) {
                    if (Objects.requireNonNull(userData.getString("fullAlamat", null))
                            .contains(setAddressCity[i])) {
                        spinnerSetCity.setSelection(i);
                        break;
                    }
                }
                // set kecamatan
                for (i = 0; i < setAddressDistrict.length; i++) {
                    if (Objects.requireNonNull(
                            userData.getString("fullAlamat", null))
                            .contains(setAddressDistrict[i])) {
                        spinnerSetDistrict.setSelection(i);
                        break;
                    }
                }
                // set kelurahan
                for (i = 0; i < setAddressSubDistrict.length; i++) {
                    if (Objects.requireNonNull(
                            userData.getString("fullAlamat", null))
                            .contains(setAddressSubDistrict[i])) {
                        spinnerSetSubDistrict.setSelection(i);
                        break;
                    }
                }
                // set alamat
                textSetAddress.setText(userData.getString("alamatPenerima", null));
                // set info tambahan
                textSetAdditionalInfo.setText(userData.getString("infoPengiriman", null));
            }
        } catch (Exception e) { /* */ }

        try {
            String locationImagePath = this.getExternalFilesDir("Location").toString().concat("/lokasi");
            if (new ImageCaching().isExist(locationImagePath)) {
                Bitmap imageBitmap = new ImageCaching().getImage(locationImagePath);
                ImageView locationView = findViewById(R.id.locationView);
                locationView.setImageBitmap(imageBitmap);
                locationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        } catch (Exception e) { /* */ }
    }

    // button simpan alamat
    public void setButtonSaveAddress(View v) {
        String labelAlamat = "";
        try {
            labelAlamat = spinnerSetAddress.getSelectedItem().toString();
        } catch (Exception e) { Log.d("Error", spinnerSetAddress.toString()); }
        //spinnerSetAddress.getSelectedItem().
        String namaPenerima = textSetReceiver.getText().toString();
        String kontakPenerima = textSetContact.getText().toString();
        String provinsiAlamat = spinnerSetProvince.getSelectedItem().toString();
        String kotaAlamat = spinnerSetCity.getSelectedItem().toString();
        String kecamatanAlamat = spinnerSetDistrict.getSelectedItem().toString();
        String kelurahanAlamat = spinnerSetSubDistrict.getSelectedItem().toString();
        String alamatPenerima = textSetAddress.getText().toString();
        String infoPengiriman = "";
        try {
            infoPengiriman = textSetAdditionalInfo.getText().toString();
        } catch (Exception e) { /* */ }

        String fullAlamat = alamatPenerima.concat(" - ")
                .concat(kelurahanAlamat).concat(" - ")
                .concat(kecamatanAlamat).concat(" - ")
                .concat(kotaAlamat).concat(" - ")
                .concat(provinsiAlamat);

        SharedPreferences.Editor userDataEdit = userData.edit();
        userDataEdit.putString("labelAlamat", labelAlamat);
        userDataEdit.putString("namaPenerima", namaPenerima);
        userDataEdit.putString("kontakPenerima", kontakPenerima);
        userDataEdit.putString("fullAlamat", fullAlamat);
        userDataEdit.putString("infoPengiriman", infoPengiriman);
        userDataEdit.putString("alamatPenerima", alamatPenerima);
        userDataEdit.apply();

        String jsonData = "";
        try {
            jsonData = new z_BackendPreProcessing().updateUserInfo(namaPenerima, alamatPenerima, kontakPenerima);
        } catch (Exception e) { /* */ }
        runAsync();
        sendData.execute(
                z_BackendPreProcessing.URL_UserUpdate + userData.getString("ID_User", null),
                jsonData);

        Intent intent = new Intent(Checkout_SetAddressActivity.this, CheckoutActivity.class);
        startActivity(intent);
    }

    public static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }
    private backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    private void runAsync() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    String[] userData = new z_BackendPreProcessing().readUserData(output);
                    // set nama penerima
                    textSetReceiver.setText(userData[0]);
                    // set kontak penerima
                    textSetContact.setText(userData[2]);
                    // set alamat
                    textSetAddress.setText(userData[1]);
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }
}
