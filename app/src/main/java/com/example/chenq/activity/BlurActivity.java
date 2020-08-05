package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chenq.base.BaseActivity;
import com.example.chenq.R;

/**
 * create by chenqi on 2020/8/5.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class BlurActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, BlurActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulr);
    }

}
