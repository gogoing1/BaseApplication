package com.example.chenq.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.chenq.interfaces.OnTouchCallBackListener;


/**
 * create by chenqi on 2020/6/28.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class TouchCallbackView extends View {

    protected OnTouchCallBackListener mTouchCallBackListener;

    public TouchCallbackView(Context context) {
        super(context);
    }

    public TouchCallbackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchCallbackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTouchCallBackListener(OnTouchCallBackListener listener) {
        this.mTouchCallBackListener = listener;
    }

    public void onTouchCallBack(MotionEvent event) {
        this.onTouchCallBack(this, event);
    }

    public void onTouchCallBack(View v, MotionEvent event) {
        if (mTouchCallBackListener != null) {
            mTouchCallBackListener.onTouchEvent(v, event);
        }
    }
}
