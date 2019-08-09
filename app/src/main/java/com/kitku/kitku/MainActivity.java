package com.kitku.kitku;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomnavigationviewMainOption;
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
            loadFragment(new UserFragment());
        }
        if (goToLoginFragment) {
            bottomnavigationviewMainOption.setSelectedItemId(R.id.botnav_menu_user);
            loadFragment(new LoginFragment());
        }


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


        }

        /* Akan mengembalikan nilai berupa method LoadFragment yang akan menampilkan Fragment sesuai Menu yang diclick */

        return loadFragment(fragment);
    }
}
