package com.kitku.kitku;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);
        ViewPager viewpagerCartFragmentNavigation = cartView.findViewById(R.id.viewpagerCartFragmentNavigation);
        viewpagerCartFragmentNavigation.setAdapter(new CartFragmentNavigationPagerAdapter(getChildFragmentManager()));

        TabLayout tablayoutCartFragmentNavigation = cartView.findViewById(R.id.tablayoutCartFragmentNavigation);
        tablayoutCartFragmentNavigation.setupWithViewPager(viewpagerCartFragmentNavigation);

        return cartView;
    }

}
