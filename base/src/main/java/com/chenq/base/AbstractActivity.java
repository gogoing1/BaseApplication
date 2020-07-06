package com.chenq.base;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: Activity抽象父类，做一些统一的全局操作
 */
public abstract class AbstractActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    protected AbstractActivity mContext;
    private float startTouchOffset;
    private float mStartTouchX;
    private float workedTouchDistance;
    private boolean swipeBackEnable = true;
    private boolean isGoingSwipeBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView();
        mContext = this;
        ActivityStack.getInstance().addActivity(this);
        bindViews(savedInstanceState);
        setSwipeBackAction(setSwipeBack());
    }

    protected void setSwipeBackAction(boolean swipeBack) {
        this.swipeBackEnable = swipeBack;
        if (swipeBackEnable) {
            // 手机左侧占屏幕宽度15%的区域内为有效滑动区
            startTouchOffset = getResources().getDisplayMetrics().widthPixels / 15;
            // 从有效区手指down开始，向右侧move距离为4分之一个屏幕宽度，认为是右滑关闭操作
            workedTouchDistance = getResources().getDisplayMetrics().widthPixels / 4;
        }
    }

    public boolean setSwipeBack() {
        return true;
    }

    private void setBaseContentView() {
        setContentView(setContentView());
    }

    protected abstract int setContentView();

    protected abstract void bindViews(Bundle savedInstanceState);


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (swipeBackEnable) {
            if (isGoingSwipeBack) {
                if (ev.getAction() == MotionEvent.ACTION_UP) isGoingSwipeBack = false;
                return true;
            }
            float touchX = ev.getX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (touchX <= startTouchOffset) {
                        mStartTouchX = touchX;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mStartTouchX > 0) {
                        if (touchX - mStartTouchX >= workedTouchDistance) {
                            isGoingSwipeBack = true;
                            finish();
                            mStartTouchX = 0;
                            return true;
                        }
                    } else if (touchX < startTouchOffset) {
                        mStartTouchX = touchX;
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mStartTouchX = 0;
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_in_from_right, R.anim.alpha_out);
    }

}
