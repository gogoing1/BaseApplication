package com.example.chenq.presenter;

import com.chenq.base.mvp.BaseView;
import com.example.chenq.util.LogUtil;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class CircleProgress2PresenterImpl extends CircleProgress2Contract.Presenter {

    public CircleProgress2PresenterImpl(BaseView view) {
        super(view);
    }

    @Override
    public void onStart() {
        LogUtil.e(TAG,"CircleProgress 2 on onStart");
    }

    @Override
    public void onDestroy() {
        LogUtil.e(TAG,"CircleProgress 2 on onDestroy");
    }

    @Override
    public void onRequestData() {
        LogUtil.e(TAG,"CircleProgress 2 on Request Data.");
    }
}
