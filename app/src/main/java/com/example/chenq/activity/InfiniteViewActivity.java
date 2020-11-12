package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chenq.base.BaseActivity;
import com.example.chenq.R;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc:
 */
public final class InfiniteViewActivity extends BaseActivity {

    private static final String TAG = "InfiniteViewActivity";

    private RecyclerView mInfiniteView;

    public static void start(Context context) {
        Intent starter = new Intent(context, InfiniteViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite_view);

        initView();
    }

    private void initView() {
        mInfiniteView = findViewById(R.id.rv_infinite);
        //mInfiniteView.setAdapter();
    }

}
