package com.laibatour.autoviewpager;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private int[] imageIds;
    private LinearLayout main_ll_dots;
    private ViewPager main_vp;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            main_vp.setCurrentItem(main_vp.getCurrentItem() + 1,true);
            handler.sendEmptyMessageDelayed(0,5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageIds = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3};
        main_ll_dots = (LinearLayout) findViewById(R.id.main_ll_dots);

        initDot();

        main_vp = ((ViewPager) findViewById(R.id.main_vp));

        main_vp.setAdapter(new MyPagerAdapter());

        main_vp.setCurrentItem(imageIds.length * 10000);

        updateDescAndDot();

        handler.sendEmptyMessageDelayed(0, 5000);

        main_vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        handler.sendEmptyMessageDelayed(0, 5000);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(0, 5000);
                        break;
                }
                return false;
            }
        });

        main_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateDescAndDot();
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
    }

    /**
     * 动态添加点
     */
    private void initDot() {
        for (int i = 0; i < imageIds.length; i++) {
            View view = new View(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            params.leftMargin = (i == 0 ? 0 : 20);//给除了第一个点之外的点都加上marginLeft
            view.setLayoutParams(params);//设置宽高

            view.setBackgroundResource(R.drawable.selector_dot);//设置背景图片

            main_ll_dots.addView(view);
        }
    }

    /**
     * 根据当前page来显示不同的文字和点
     */
    private void updateDescAndDot() {
        int currentPage = main_vp.getCurrentItem() % imageIds.length;
        // tv_desc.setText(list.get(currentPage).getDesc());

        //更新点
        //遍历所有的点，当点的位置和currentPage相当的时候，则设置为可用，否则是禁用
        for (int i = 0; i < main_ll_dots.getChildCount(); i++) {
            main_ll_dots.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("current position =====", position + "");
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(imageIds[position % imageIds.length]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
