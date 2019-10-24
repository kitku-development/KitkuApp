package com.kitku.kitku.Home;

import android.graphics.Bitmap;

import com.kitku.kitku.R;

import java.util.ArrayList;

public class HomeBannerSliderPagerDataset {

    HomeBannerSliderPagerDataset(){
        list = new ArrayList<>();
    }
    /* Dataset (Tempat data) untuk item-item pada Banner Slider di Main Activity */

    /*public static int[] imageData = new int[] {
            R.drawable.contoh1, R.drawable.contoh2, R.drawable.contoh1,
            R.drawable.contoh2, R.drawable.contoh1
    };*/

    HomeBannerSliderPagerDataModel homeBannerSliderPagerDataModel;
    ArrayList<HomeBannerSliderPagerDataModel> list;
    /* Method untuk set Image yang ada di Dataset menuju Data Model */

    public ArrayList<HomeBannerSliderPagerDataModel> getImageData() {
        return list;
    }

    public void addImageData(Bitmap image){
        homeBannerSliderPagerDataModel = new HomeBannerSliderPagerDataModel();
        homeBannerSliderPagerDataModel.setImage_item(image);
        list.add(homeBannerSliderPagerDataModel);
    }
}
