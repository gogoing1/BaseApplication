package com.example.chenq.widget.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chenq.R;

/**
 * create by chenqi on 2020/8/3.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MyPagerFragment extends Fragment {

    private TextView mFragName;
    private int curFragIndex;

    public static MyPagerFragment newInstance(int position) {
        MyPagerFragment fragment = new MyPagerFragment();
        Bundle args = new Bundle();
        args.putInt("frag", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_pager, container, false);
        curFragIndex = getArguments().getInt("frag");
        initView(contentView);
        return contentView;
    }

    private void initView(View contentView) {
        mFragName = contentView.findViewById(R.id.tv_frag);
        mFragName.setText(String.valueOf(curFragIndex));
    }

}
