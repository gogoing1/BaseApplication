package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import androidx.annotation.Nullable;

import com.chenq.base.AbstractActivity;
import com.chenq.base.BaseActivity;
import com.example.chenq.R;
import com.example.chenq.widget.GradientColorTextView;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc: 广播测试类
 */
public final class SwipeBackActivity extends AbstractActivity {

    private static final String TAG = "SwipeBackActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, SwipeBackActivity.class);
        context.startActivity(starter);
    }

    GradientColorTextView mPrgressText;

    @Override
    protected int setContentView() {
        return R.layout.activity_swipe_back;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {

    }

}
