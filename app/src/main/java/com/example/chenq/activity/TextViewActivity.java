package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chenq.base.activity.BaseActivity;
import com.example.chenq.R;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc:
 */
public final class TextViewActivity extends BaseActivity {

    private static final String TAG = "TextViewActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, TextViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
    }

}
