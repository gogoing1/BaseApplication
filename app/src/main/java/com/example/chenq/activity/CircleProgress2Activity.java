package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.chenq.base.mvp.AbstractMVPActivity;
import com.chenq.base.mvp.BasePresenter;
import com.example.chenq.R;
import com.example.chenq.presenter.CircleProgress2Contract;
import com.example.chenq.presenter.CircleProgress2PresenterImpl;
import com.example.chenq.util.LogUtil;
import com.example.chenq.widget.AirCircleProgressView;
import com.example.chenq.widget.CircleProgressView;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc: 圆弧进度条测试2
 */
public class CircleProgress2Activity extends AbstractMVPActivity implements CircleProgress2Contract.View {


    private AirCircleProgressView mAcProgressView;
    private AppCompatSeekBar mSeekBar;

    public static void start(Context context) {
        Intent starter = new Intent(context, CircleProgress2Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected BasePresenter newPresenter() {
        return new CircleProgress2PresenterImpl(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.layout_circle_progress_2_activity;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAcProgressView = findViewById(R.id.ac_progress_view);
        mSeekBar = findViewById(R.id.sb_circle_progress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "progress：" + progress);
                mAcProgressView.onProgressChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void refreshView(Object o) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void setCircleProgress(int progress) {
        LogUtil.e(TAG, "setCircleProgress .");
    }
}
