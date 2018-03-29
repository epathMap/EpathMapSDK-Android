package com.shitu.epathmapguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shitu.epathmapguide.common.GlideImageLoaderBanner;
import com.shitu.epathmapguide.common.GlobalShare;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private TextView tvStart;
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        banner = findViewById(R.id.banner);
        tvStart = findViewById(R.id.tvStart);
        initBanner();
        tvStart.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            GuideActivity.this.finish();
        });
        SharedPreferences sharedPreferences = getSharedPreferences("epathMap", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(GlobalShare.ALREADY_OPEN,1);
        editor.commit();
    }

    private void initBanner() {
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.ic_guide_1);
        images.add(R.mipmap.ic_guide_2);
        banner.setImageLoader(new GlideImageLoaderBanner())
                .setBannerAnimation(Transformer.DepthPage)
                .isAutoPlay(false)
                .setViewPagerIsScroll(true)
                .setImages(images)
                .setDelayTime(2000)
                .setIndicatorGravity(BannerConfig.CENTER).start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tvStart.setVisibility(View.VISIBLE);
                } else {
                    tvStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
