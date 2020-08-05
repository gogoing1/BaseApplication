package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chenq.base.BaseActivity;
import com.example.chenq.MainActivity;
import com.example.chenq.R;
import com.example.chenq.util.LogUtils;
import com.example.chenq.widget.viewpager.MyPagerAdapter;
import com.example.chenq.widget.viewpager.MyPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * create by chenqi on 2020/8/3.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class ViewPagerActivity extends BaseActivity {

    private static final String TAG = "ViewPagerActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
    }

    private int curPosition;

    private void initView() {
        final ViewPager mViewPager = findViewById(R.id.vp_test);
        final List<MyPagerFragment> mFragmentList = getFragmentList();
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //LogUtils.e(TAG, "onPageScrolled() -- position:" + position + "  positionOffset:" + positionOffset + "  positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e(TAG, "onPageSelected() -- position:" + position);
                curPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.e(TAG, "onPageScrollStateChanged() -- state:" + state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (curPosition == 0) {
                        //切换，不要动画效果
                        mViewPager.setCurrentItem(mFragmentList.size() - 2, false);
                    } else if (curPosition == mFragmentList.size() - 1) {
                        //切换，不要动画效果
                        mViewPager.setCurrentItem(1, false);
                    }
                }
            }
        });
    }

    private List<MyPagerFragment> getFragmentList() {
        List<MyPagerFragment> list = new ArrayList<>();
        MyPagerFragment fragment_5 = MyPagerFragment.newInstance(5);
        MyPagerFragment fragment1 = MyPagerFragment.newInstance(1);
        MyPagerFragment fragment2 = MyPagerFragment.newInstance(2);
        MyPagerFragment fragment3 = MyPagerFragment.newInstance(3);
        MyPagerFragment fragment4 = MyPagerFragment.newInstance(4);
        MyPagerFragment fragment5 = MyPagerFragment.newInstance(5);
        MyPagerFragment fragment_1 = MyPagerFragment.newInstance(1);
        list.add(fragment_5);
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        list.add(fragment5);
        list.add(fragment_1);
        return list;
    }

}
