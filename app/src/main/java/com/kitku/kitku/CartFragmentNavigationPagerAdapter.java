package com.kitku.kitku;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CartFragmentNavigationPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] cartFragments;
    private String[] tabTitle = new String[] {
            "Keranjang","Sedang Proses","Selesai"
    };

    public CartFragmentNavigationPagerAdapter(FragmentManager fm) {
        super(fm);
        cartFragments = new Fragment[] {
                new Cart_OrderFragment(),
                new Cart_OnProgressFragment(),
                new Cart_DoneFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return cartFragments[position];
    }

    @Override
    public int getCount() {
        return cartFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
