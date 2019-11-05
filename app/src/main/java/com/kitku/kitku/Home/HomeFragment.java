package com.kitku.kitku.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.card.MaterialCardView;
import com.kitku.kitku.List_Item.ListItemActivity;
import com.kitku.kitku.R;
import com.kitku.kitku.BackgroundProcess.*;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager viewpagerHomeBannerSlider;
    private ArrayList<HomeBannerSliderPagerDataModel> list = new ArrayList<>();

    private int current_position = 0;
    private CircleIndicator circleIndicatorHomeBannerSlider;

    /* Method untuk create view yang ada di layout fragment_home */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewpagerHomeBannerSlider = view.findViewById(R.id.viewpagerHomeBannerSlider);
        circleIndicatorHomeBannerSlider = view.findViewById(R.id.circleindicatorHomeBannerSlider);
        MaterialCardView materialcardviewVegetableButtonMain = view.findViewById(R.id.materialcardviewVegetableButtonMain);
        MaterialCardView materialcardviewMeatButtonMain = view.findViewById(R.id.materialcardviewMeatButtonMain);
        MaterialCardView materialcardviewFishButtonMain = view.findViewById(R.id.materialcardviewFishButtonMain);
        MaterialCardView materialcardviewWheatButtonMain = view.findViewById(R.id.materialcardviewWheatButtonMain);
        MaterialCardView materialcardviewFastFoodButtonMain = view.findViewById(R.id.materialcardviewFastFoodButtonMain);
        MaterialCardView materialcardviewOthersButtonMain = view.findViewById(R.id.materialcardviewOthersButtonMain);


        //list.addAll(HomeBannerSliderPagerDataset.getImageData());

        runAsync();
        sendData.execute(BackendPreProcessing.URL_GetBannerList, null);

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
        materialcardviewFastFoodButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("titletextFastFood", "Makanan Siap Saji");
                startActivity(intent);*/
                showComingSoonMessage();
            }
        });
        materialcardviewOthersButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("titletextOthers", "Lainnya");
                startActivity(intent);*/
                showComingSoonMessage();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 500, 2500);
    }

    private void showComingSoonMessage() {
        new AlertDialog.Builder(getContext())
                .setTitle("Pesan")
                .setMessage("Coming Soon!")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    static class backgroundImageDownloader extends ImageDownloader {
        backgroundImageDownloader(AsyncResponse delegate) { this.delegate = delegate; }
    }
    private backgroundImageDownloader downloadImage;

    private void loadImage() {
        downloadImage = new backgroundImageDownloader(new backgroundImageDownloader.AsyncResponse() {
            @Override
            public void processFinish(Bitmap output, Integer index) {
                addBanner(output);
            }
        });
    }

    // Inisiasi AsyncTask dari AsyncServerAccess supaya dapat mengakses Activity
    public static class backgroundTask extends AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }
    private backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    private void runAsync() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                counterData = 0;
                imageSet = new HomeBannerSliderPagerDataset();
                try {
                    String[] linkList = BackendPreProcessing.readBannerInfo(output);
                    maxData = linkList.length;
                    String bannerDirLocation = getStringOfBannerDirLocation();
                    for (String s : linkList) {
                        loadImage();
                        downloadImage.execute(
                                bannerDirLocation,
                                s.replace(BackendPreProcessing.URL_LinkToBanner, ""),
                                s,
                                null
                        );
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    private HomeBannerSliderPagerDataset imageSet;
    private int counterData, maxData;
    private void addBanner(Bitmap image){
        imageSet.addImageData(image);
        counterData++;
        if (counterData == maxData){
            list.addAll(imageSet.getImageData());
            HomeBannerSliderPagerAdapter homeBannerSliderPagerAdapter = new HomeBannerSliderPagerAdapter(this.getActivity());
            homeBannerSliderPagerAdapter.setHomeBannerSliderPagerDataModelArrayList(list);
            viewpagerHomeBannerSlider.setAdapter(homeBannerSliderPagerAdapter);
            circleIndicatorHomeBannerSlider.setViewPager(viewpagerHomeBannerSlider);

            createSlideShow();
        }
    }

    private String getStringOfBannerDirLocation() {
        File folder = null;
        String location = "";
        try {
            folder = Objects.requireNonNull(this.getActivity()).getExternalFilesDir("Banner");
            if (folder != null) {
                location = folder.getAbsolutePath().concat("/");
            }
        } catch (Exception e) {
            ImageCaching.createDir(folder);
            location = getStringOfBannerDirLocation();
        }
        return location;
    }
}