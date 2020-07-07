package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chenq.base.BaseActivity;
import com.chenq.library.utils.SoftInputUtils;
import com.example.chenq.R;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc:
 */
public final class SoftInputActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SoftInputActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_view);

        //SoftInputUtils.openSoftInput(this, findViewById(R.id.et_test));
        //SoftInputUtils.closeSoftInput(this,findViewById(R.id.et_test));
    }

}
