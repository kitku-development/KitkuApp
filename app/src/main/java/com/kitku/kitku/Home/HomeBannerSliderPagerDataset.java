package com.kitku.kitku.Home;

import com.kitku.kitku.R;

import java.util.ArrayList;

public class HomeBannerSliderPagerDataset {

    /* Dataset (Tempat data) untuk item-item pada Banner Slider di Main Activity */

    public static int[] imageData = new int[] {
            R.drawable.contoh1, R.drawable.contoh2, R.drawable.contoh1,
            R.drawable.contoh2, R.drawable.contoh1
    };

    /* Method untuk set Image yang ada di Dataset menuju Data Model */

    public static ArrayList<HomeBannerSliderPagerDataModel> getImageData() {
        HomeBannerSliderPagerDataModel homeBannerSliderPagerDataModel = null;
        ArrayList<HomeBannerSliderPagerDataModel> list = new ArrayList<>();
        for (int arrayData : imageData) {
            homeBannerSliderPagerDataModel = new HomeBannerSliderPagerDataModel();
            homeBannerSliderPagerDataModel.setImage_item(arrayData);

            list.add(homeBannerSliderPagerDataModel);
        }

        return list;

    }

}
