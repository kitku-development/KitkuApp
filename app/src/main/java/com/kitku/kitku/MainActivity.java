package com.kitku.kitku;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kitku.kitku.Cart.CartFragment;
import com.kitku.kitku.Home.HomeFragment;
import com.kitku.kitku.Login.LoginFragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    static BottomNavigationView bottomnavigationviewMainOption;
    boolean goToUserFragment, goToLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Fragment yang akan muncul pertama kali dipanggil oleh Method LoadFragment*/

        loadFragment(new HomeFragment());

        bottomnavigationviewMainOption = findViewById(R.id.bottomnavigationviewMainOption);
        bottomnavigationviewMainOption.setOnNavigationItemSelectedListener(this);

        /* Mendapatkan nilai dari Intent yang dikirim pada Register Activity */

        Bundle bundleUser = getIntent().getExtras();
        Bundle bundleLogin = getIntent().getExtras();

        /* Kondisional dari Fragment yang akan tampil berdasarkan nilai Intent yang didapat*/

        if (bundleUser != null && bundleUser.containsKey("goToUserFragment")) {
            goToUserFragment = bundleUser.getBoolean("goToUserFragment");
        }

        if (bundleLogin != null && bundleLogin.containsKey("goToLoginFragment")) {
            goToLoginFragment = bundleLogin.getBoolean("goToLoginFragment");
        }

        /* Kondisional yang akan mengarahkan pengguna ke fragment tertentu berdasarkan nilai Boolean yang didapat */

        if (goToUserFragment) {
            bottomnavigationviewMainOption.setSelectedItemId(R.id.botnav_menu_user);
            //loadFragment(new UserFragment());
        }
        if (goToLoginFragment) {
            bottomnavigationviewMainOption.setSelectedItemId(R.id.botnav_menu_user);
            //loadFragment(new LoginFragment());
        }

        // ask permission to utilize caching image
        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //        != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        //}

        // delete data if one day old
        try {
            dataExpire();
        } catch (Exception e) { /*e.printStackTrace();*/ }

        // used to redirect from Detail_Item to Cart
        try {
            Bundle bundleMessage = getIntent().getExtras();
            assert bundleMessage != null;
            if (bundleMessage.containsKey("goToCart"))
                ToCart();
        } catch (Exception e) { /*e.printStackTrace();*/ }
    }

    /* Method untuk meload Fragment saat aplikasi pertama kali dibuka */

    boolean loadFragment (Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameFragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /* Method untuk mengarahkan pengguna ke Fragment tertentu jika menekan salah satu menu pada Bottom Navigation View */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.botnav_menu_home:
                fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameFragmentContainer, fragment)
                        .commit();
                break;

            case R.id.botnav_menu_cart:
                fragment = new CartFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameFragmentContainer, fragment)
                        .commit();
                break;

            case R.id.botnav_menu_user:
                fragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameFragmentContainer, fragment)
                        .commit();
                break;

            case R.id.botnav_menu_partner:
                fragment = new PartnerFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameFragmentContainer, fragment)
                        .commit();
                break;

        }

        /* Akan mengembalikan nilai berupa method LoadFragment yang akan menampilkan Fragment sesuai Menu yang diclick */

        return loadFragment(fragment);
    }

    // permission external storage
    int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE;
    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } /*else {
            // Permission has already been granted
        }*/
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }*/

    // used to delete "SharedPreference of Cart data" and "product image cache"
    // will be delete automatically if Cart data is a day old already (taken from Last Update)
    // this procedure only occur when the app opened
    public void dataExpire() {//throws Exception {
        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(this);
        long dates = System.currentTimeMillis();
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ROOT);
        String date = sdf.format(dates);*/
        // get User data
        SharedPreferences.Editor userDataEdit = userData.edit();
        long dateStored;
        // if LastUpdate is exist, get data. else, create LastUpdate
        try { dateStored = userData.getLong("Last Update", 0); }
        catch (Exception e) { e.printStackTrace(); dateStored = 0; }
        //Log.d("date",String.valueOf(dates));
        // this is a bit mistake, it should directly execute if (dates - dateStored > 86400000)
        // because we already check LastUpdate above
        if (userData.contains("Last Update")) {
            // check if LastUpdate is one days old
            // 1 day = 86400 second ; * 1000 msecond
            if (dates - dateStored > 86400000) {
                // remove Cart and LastUpdate
                userDataEdit.remove("Last Update");
                userDataEdit.remove("Cart");
                // delete all product image cache
                String dirString = Objects.requireNonNull(getExternalFilesDir("Images")).getAbsolutePath();
                File dir = new File(dirString);
                File[] files = dir.listFiles();
                assert files != null;
                for (File file1 : files) {
                    Log.d("loc", file1.getAbsolutePath());
                    if (file1.delete()) Log.d("stat", "true");
                    else Log.d("stat", "false");
                }
            }
        }

        // create new LastUpdate data
        userDataEdit.putLong("Last Update", dates);
        userDataEdit.apply();

        //dates = System.currentTimeMillis();
        //Log.d("date",String.valueOf(dates));
    }

    // used to redirect from Detail_Item to Cart
    private void ToCart() {
        bottomnavigationviewMainOption.setSelectedItemId(R.id.botnav_menu_cart);
    }

    // used to redirect from Cart to Home
    public static void default_home_button() {
        bottomnavigationviewMainOption.setSelectedItemId(R.id.botnav_menu_home);
    }
    /*private static FragmentManager getTheFragmentManager() {
        return fManager;
    }*/
}
