package com.chenq.base.mvp;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc:
 */
public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {

    protected static final String TAG = BasePresenterImpl.class.getName();

    protected V mView;

    public BasePresenterImpl(V view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

}
