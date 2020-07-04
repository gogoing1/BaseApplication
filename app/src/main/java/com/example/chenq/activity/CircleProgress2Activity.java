package com.example.chenq.activity;

import android.os.Bundle;

import com.chenq.base.mvp.AbstractMVPActivity;
import com.chenq.base.mvp.BasePresenter;
import com.example.chenq.R;
import com.example.chenq.presenter.CircleProgress2Contract;
import com.example.chenq.presenter.CircleProgress2PresenterImpl;
import com.example.chenq.util.LogUtil;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc: 圆弧进度条测试2
 */
public class CircleProgress2Activity extends AbstractMVPActivity implements CircleProgress2Contract.View{

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

    }

    @Override
    public void refreshView(Object o) {

    }

    @Override
    public void setCircleProgress(int progress) {
        LogUtil.e(TAG,"setCircleProgress .");
    }
}
