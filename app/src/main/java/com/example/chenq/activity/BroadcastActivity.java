package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import androidx.annotation.Nullable;

import com.chenq.base.activity.AbstractActivity;
import com.example.chenq.R;
import com.example.chenq.widget.GradientColorTextView;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc: 广播测试类
 */
public final class BroadcastActivity extends AbstractActivity {

    private static final String TAG = "BroadcastActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, BroadcastActivity.class);
        context.startActivity(starter);
    }

    GradientColorTextView mPrgressText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        mPrgressText = findViewById(R.id.temperature_view);
        refreshProgressView(66);
    }

    private void refreshProgressView(float value) {
        SpannableString dateWeek = new SpannableString((int) value + "%");
        dateWeek.setSpan(new AbsoluteSizeSpan(134, false), 0, dateWeek.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dateWeek.setSpan(new AbsoluteSizeSpan(52, false), dateWeek.length() - 1, dateWeek.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (mPrgressText != null) {
            mPrgressText.setText(dateWeek);
        }
    }

}
