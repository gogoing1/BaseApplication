package com.example.chenq.base.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class DrawableUtil {

    /**
     * Drawable 资源加载
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        if (resId <= 0) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT == 21) {
            return context.getDrawable(resId);
        } else {
            return context.getResources().getDrawable(resId);
        }
    }


}
