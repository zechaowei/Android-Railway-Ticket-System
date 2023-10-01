package com.example.railwayticketingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout; // 导入正确的TabLayout类

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    String tabTitles[] = {"车票", "订单", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        // 初始化TabLayout
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPage);

        //设置ViewPager之后，Tab的内容由ViewPage（适配器）提供
        tabLayout.setupWithViewPager(viewPager);

        // 创建并添加选项卡
//        tabLayout.addTab(tabLayout.newTab().setText("车票"));
//        tabLayout.addTab(tabLayout.newTab().setText("订单"));
//        tabLayout.addTab(tabLayout.newTab().setText("@我的"));

        //提前创建好3个viewpager页面
//        List<TextView> viewPagerItem = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            TextView tv = new TextView(this);
//            tv.setText("第"+ i + "个页面内容");
//            tv.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//            tv.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//            tv.setGravity(Gravity.CENTER);
//            viewPagerItem.add(tv);
//        }


//        for (int i = 0; i < 3; i++) {
//            fragments.add(new TicketFragment());
//        }
        fragments.add(new TicketFragment());
        fragments.add(new OrderFragment());
        fragments.add(new MyFragment());

//        viewPager.setAdapter(new PagerAdapter() {
//            //告诉viewPage一共有多少个页面
//            @Override
//            public int getCount() {
//                return viewPagerItem.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//                return view == object;
//            }
//
//            //用于真正设置具体一个位置的视图
//            @NonNull
//            @Override
//            public Object instantiateItem(@NonNull ViewGroup container, int position) {
//                TextView item = viewPagerItem.get(position);
//                container.addView(item);
//                return item;
////                return super.instantiateItem(container, position);
//            }
//
//            @Override
//            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                ViewPager vp = (ViewPager) container;
//                vp.removeView(container);
//                System.out.println(container);
//                System.out.println(position);
//                System.out.println(object);
//            }
//        });

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragments.get(0).onActivityResult(requestCode, resultCode, data);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter{

        //构造器
        public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, MyFragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        //关联之后的TabLayout中题目(Item)，有ViewPager的适配器方法getPageTitle提供
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];     //展示导航栏文字
//            return "";                //不展示导航栏文字
        }
    }


    /**
     * 设置点击两次返回键就结束app进程
     */
    long startTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            long current = System.currentTimeMillis();
            Log.d("MainActivity", "startTime:" + startTime);
            Log.d("MainActivity", "current:" + current);
            Log.d("MainActivity", "(startTime - startTime):" + (current - startTime));
            if (current - startTime < 1500) {
                finish();
            } else {
                startTime = current;
            }
        }
        Toast.makeText(this,"KeyCode:" + keyCode,Toast.LENGTH_SHORT).show();
        return true;
    }
}
