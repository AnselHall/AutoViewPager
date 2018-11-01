package com.laibatour.autoviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.reflect.Field;

/**
 * 自定义轮播ViewPager
 *
 * 控制切换的速度
 */

public class AutoViewPager extends ViewPager {

    public AutoViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViewPagerScroll();
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            BannerScroller bannerScroller = new BannerScroller(getContext(), new AccelerateDecelerateInterpolator());

            mScroller.set(this, bannerScroller);

        } catch (Exception e) {
            Log.e("Tag", "error  e = " + e.getMessage());
        }
    }
}
