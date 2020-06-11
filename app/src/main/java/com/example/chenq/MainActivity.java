package com.example.chenq;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chenq.activity.CircleProgressActivity;
import com.example.chenq.activity.LyricActivity;
import com.example.chenq.base.activity.BaseActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mCircleProgressBtn;
    private Button mLyricBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCircleProgressBtn = findViewById(R.id.btn_circle_progress);
        mCircleProgressBtn.setOnClickListener(this);
        mLyricBtn = findViewById(R.id.btn_lyric);
        mLyricBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_circle_progress:
                CircleProgressActivity.start(this);
                break;
            case R.id.btn_lyric:
                LyricActivity.start(this);
                break;
            default:
                break;

        }
    }
}
