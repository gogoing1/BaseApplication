package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.chenq.R;
import com.example.chenq.base.activity.BaseActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: 拖动球
 */
public class DraggingBallActivity extends BaseActivity {

    private static final String TAG = "DraggingBallActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, DraggingBallActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_seekbar);
    }

}