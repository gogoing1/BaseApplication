package com.chenq.base.mvp;


import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chenq.base.AbstractActivity;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc: MVP基础父类
 * MVP 模式下各个组件负责的功能明确，Mode负责数据与接口请求，View负责页面渲染，Presenter负责逻辑处理与接口调用，合理衔接Mode与View
 */
public abstract class AbstractMVPActivity<T extends BasePresenter> extends AbstractActivity implements BaseView {

    protected static String TAG = AbstractMVPActivity.class.getName();

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mPresenter ==null){
            mPresenter = newPresenter();
        }
    }

    protected abstract T newPresenter();

    @Override
    protected void onStart() {
        super.onStart();
        if(mPresenter!=null){
            mPresenter.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
        }
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLoadingDialog() {

    }
}
