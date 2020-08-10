package com.chenq.library.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

/**
 * create by chenqi on 2020/7/7
 * Email: chenqwork@gmail.com
 * Desc: 软键盘管理 工具类
 */
public class SoftInputUtils {

    /**
     * 打开软键盘
     *
     * @param context
     * @param focusView
     */
    public static void openSoftInput(Context context, final View focusView) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        focusView.post(new Runnable() {
            @Override
            public void run() {
                focusView.requestFocus();
                imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
            }
        });
    }

    /**
     * 隐藏软键盘(有输入框)
     *
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context, @NonNull EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static boolean isActive(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive(view);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
