package com.kitku.kitku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.card.MaterialCardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager viewpagerHomeBannerSlider;
    private ArrayList<HomeBannerSliderPagerDataModel> list = new ArrayList<>();

    private Timer timer;
    private int current_position = 0;

    /* Method untuk create view yang ada di layout fragment_home */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewpagerHomeBannerSlider = view.findViewById(R.id.viewpagerHomeBannerSlider);
        CircleIndicator circleindicatorHomeBannerSlider = view.findViewById(R.id.circleindicatorHomeBannerSlider);
        MaterialCardView materialcardviewVegetableButtonMain = view.findViewById(R.id.materialcardviewVegetableButtonMain);
        MaterialCardView materialcardviewMeatButtonMain = view.findViewById(R.id.materialcardviewMeatButtonMain);
        MaterialCardView materialcardviewFishButtonMain = view.findViewById(R.id.materialcardviewFishButtonMain);
        MaterialCardView materialcardviewWheatButtonMain = view.findViewById(R.id.materialcardviewWheatButtonMain);

        list.addAll(HomeBannerSliderPagerDataset.getImageData());

        HomeBannerSliderPagerAdapter homeBannerSliderPagerAdapter = new HomeBannerSliderPagerAdapter(this.getActivity());
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