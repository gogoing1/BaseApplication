package com.chenq.base.mvp;


import android.os.Bundle;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc: MVP基础父类
 * MVP 模式下各个组件负责的功能明确，Mode负责数据与接口请求，View负责页面渲染，Presenter负责逻辑处理与接口调用，合理衔接Mode与View
 */
public class AbstractMVPActivity<T extends BaseView> extends com.chenq.base.AbstractActivity implements BaseView {

    private T basePresenter;

    @Override
    protected int setContentView() {
        return 0;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        if(basePresenter ==null){
            basePresenter = new BasePresenterImpl(this);
        }
    }

    @Override
    public void refreshView(Object o) {

    }


}
