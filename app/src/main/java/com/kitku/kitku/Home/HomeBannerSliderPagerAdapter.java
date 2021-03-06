package com.kitku.kitku.Home;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kitku.kitku.R;

import java.util.ArrayList;


public class HomeBannerSliderPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<HomeBannerSliderPagerDataModel> homeBannerSliderPagerDataModelArrayList;
    //private int custom_position = 0;

    /* Constructor untuk context */

    HomeBannerSliderPagerAdapter(Context context) {
        this.context = context;
    }

    /* Getter dan Setter untuk mengambil data pada Data Model */

    void setHomeBannerSliderPagerDataModelArrayList(ArrayList<HomeBannerSliderPagerDataModel> homeBannerSliderPagerDataModelArrayList) {
        this.homeBannerSliderPagerDataModelArrayList = homeBannerSliderPagerDataModelArrayList;
    }

    private ArrayList<HomeBannerSliderPagerDataModel> getHomeBannerSliderPagerDataModelArrayList() {
        return homeBannerSliderPagerDataModelArrayList;
    }

    /* Mendefinisikan view yang akan berada dalam Banner Slider ViewPager di Main Activity */

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_bannerslider_item_layout, container, false);
        ImageView imageBannerSliderItem = view.findViewById(R.id.imageBannerSliderItem);

        HomeBannerSliderPagerDataModel homeBannerSliderPagerDataModel = getHomeBannerSliderPagerDataModelArrayList().get(position);

        imageBannerSliderItem.setImageBitmap(homeBannerSliderPagerDataModel.getImage_item());
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return getHomeBannerSliderPagerDataModelArrayList().size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
