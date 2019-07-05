package com.kitku.kitku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager viewpagerHomeBannerSlider;
    private CircleIndicator circleindicatorHomeBannerSlider;
    private HomeBannerSliderPagerAdapter homeBannerSliderPagerAdapter;
    private MaterialCardView materialcardviewVegetableButtonMain, materialcardviewMeatButtonMain;
    private MaterialCardView materialcardviewFishButtonMain, materialcardviewWheatButtonMain;
    private ArrayList<HomeBannerSliderPagerDataModel> list = new ArrayList<>();

    private Timer timer;
    private int current_position = 0;

    /* Method untuk create view yang ada di layout fragment_home */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewpagerHomeBannerSlider = view.findViewById(R.id.viewpagerHomeBannerSlider);
        circleindicatorHomeBannerSlider = view.findViewById(R.id.circleindicatorHomeBannerSlider);
        materialcardviewVegetableButtonMain = view.findViewById(R.id.materialcardviewVegetableButtonMain);
        materialcardviewMeatButtonMain = view.findViewById(R.id.materialcardviewMeatButtonMain);
        materialcardviewFishButtonMain = view.findViewById(R.id.materialcardviewFishButtonMain);
        materialcardviewWheatButtonMain = view.findViewById(R.id.materialcardviewWheatButtonMain);

        list.addAll(HomeBannerSliderPagerDataset.getImageData());

        homeBannerSliderPagerAdapter = new HomeBannerSliderPagerAdapter(this.getActivity());
        homeBannerSliderPagerAdapter.setHomeBannerSliderPagerDataModelArrayList(list);
        viewpagerHomeBannerSlider.setAdapter(homeBannerSliderPagerAdapter);
        circleindicatorHomeBannerSlider.setViewPager(viewpagerHomeBannerSlider);

        createSlideShow();

        /* Method untuk mengarahkan user ke halaman List Item jika menekan salah satu tombol kategori*/

        materialcardviewVegetableButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("titletextVegetable", "Sayur");
                startActivity(intent);
            }
        });
        materialcardviewMeatButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("titletextMeat", "Daging, Unggas dan Telur");
                startActivity(intent);
            }
        });
        materialcardviewFishButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("titletextFish", "Ikan");
                startActivity(intent);
            }
        });
        materialcardviewWheatButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("titletextWheat", "Beras dan Biji-bijian");
                startActivity(intent);
            }
        });

        return view;
    }

    /* Method untuk membuat Banner Slider Viewpager berjalan secara otomatis dalam jangka waktu tertentu */

    private void createSlideShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == list.size()) {
                    current_position = 0;
                }

                viewpagerHomeBannerSlider.setCurrentItem(current_position++, true);

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 500, 2500);
    }


}