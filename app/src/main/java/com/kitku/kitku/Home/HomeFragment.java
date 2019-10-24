package com.kitku.kitku.Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
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
        sendData.execute(z_BackendPreProcessing.URL_GetBannerList, null);

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

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    public static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }
    private backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    private void runAsync() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.d("output", output);
                counterData = 0;
                imageSet = new HomeBannerSliderPagerDataset();
                try {
                    String[] linkList = new z_BackendPreProcessing().readBannerInfo(output);
                    maxData = linkList.length;
                    for (String s : linkList) {
                        new downloadImage(HomeFragment.this).execute(
                                s, s.replace("https://kitku.id/bannerpic/", "")
                        );
                    }
                    //new downloadImage(UserFragment.this).execute();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    // AsyncTask untuk download gambar dan simpan pada array
    static class downloadImage extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<HomeFragment> mParentActivity;

        downloadImage(HomeFragment parentActivity) {
            mParentActivity = new WeakReference<>(parentActivity);
        }

        @Override
        protected Bitmap doInBackground(String...url) {
            Bitmap mIcon11 = null;
            try {
                File folder = Objects.requireNonNull(
                        mParentActivity.get().getActivity()).getExternalFilesDir("Banner");
                assert folder != null;
                String dirLocation = folder.getAbsolutePath().concat("/");
                if (new ImageCaching().isExist(
                        Objects.requireNonNull(dirLocation.concat(url[1]))))
                    mIcon11 = new ImageCaching().getImage(dirLocation.concat(url[1]));
                else {
                    InputStream in = new java.net.URL(url[0]).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                    new ImageCaching().putImageWithFullPath(url[1], mIcon11,
                            mParentActivity.get().getContext(),"Banner");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mParentActivity.get().addBanner(result);
        }
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
}