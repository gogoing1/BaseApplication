package com.example.chenq.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.base.activity.BaseActivity;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc: 广播测试类
 */
public final class BroadcastActivity extends BaseActivity {

    private static final String TAG = "BroadcastActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, BroadcastActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

//        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d(TAG, "mBroadcastReceiver onReceived action :" + intent.getAction());
//                intent.getAction();
//
//            }
//        };
    }

}
