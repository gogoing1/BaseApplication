package com.chenq.base.mvp;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class BasePresenterImpl<T extends BasePresenter> implements BasePresenter {

    private BaseView baseView;

    public BasePresenterImpl(BaseView view) {
        baseView = view;
    }

}
