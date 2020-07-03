package com.example.chenq.presenter;

import com.chenq.base.mvp.BasePresenter;
import com.chenq.base.mvp.BasePresenterImpl;
import com.chenq.base.mvp.BaseView;

/**
 * create by chenqi on 2020/7/3
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class CircleProgress2Contract {

    public static abstract class Presenter extends BasePresenterImpl {

        public Presenter(BaseView view) { super(view); }

    }

    public interface View extends BaseView{

       void setCircleProgress(int progress);
    }
}
