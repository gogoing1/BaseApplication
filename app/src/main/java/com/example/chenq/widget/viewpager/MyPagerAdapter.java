package com.example.chenq.widget.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * create by chenqi on 2020/8/3.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private List<MyPagerFragment> mFragmentList;

    public MyPagerAdapter(@NonNull FragmentManager fm, List<MyPagerFragment> myPagerFragment) {
        super(fm);
        this.mFragmentList = myPagerFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mFragmentList != null && mFragmentList.size() > 0 && position < mFragmentList.size()) {
            return mFragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mFragmentList == null) {
            return 0;
        }
        return mFragmentList.size();
    }

}
