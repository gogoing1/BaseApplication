package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chenq.base.activity.BaseActivity;
import com.example.chenq.R;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: 垂直进度条
 */
public class VerticalSeekBarActivity extends BaseActivity {

    private static final String TAG = "CircleProgressActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, VerticalSeekBarActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_seekbar);
    }

}
