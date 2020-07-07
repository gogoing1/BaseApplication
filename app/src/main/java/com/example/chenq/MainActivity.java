package com.example.chenq;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chenq.base.AbstractActivity;
import com.example.chenq.activity.BroadcastActivity;
import com.example.chenq.activity.CircleProgress2Activity;
import com.example.chenq.activity.CircleProgressActivity;
import com.example.chenq.activity.DraggingBallActivity;
import com.example.chenq.activity.LyricActivity;
import com.example.chenq.activity.SoftInputActivity;
import com.example.chenq.activity.SwipeBackActivity;
import com.example.chenq.activity.TextViewActivity;
import com.example.chenq.activity.VerticalSeekBarActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MainActivity extends AbstractActivity implements View.OnClickListener {

    private Button mCircleProgressBtn;
    private Button mLyricBtn;
    private Button mBroadcastBtn;
    private Button mTextBtn;
    private Button mVerticalSeekBarBtn;
    private Button mDraggingBallBtn;
    private Button mSwipeBtn;
    private Button mProgressBar2Btn;
    private Button mSoftTestBtn;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mCircleProgressBtn = findViewById(R.id.btn_circle_progress);
        mCircleProgressBtn.setOnClickListener(this);
        mLyricBtn = findViewById(R.id.btn_lyric);
        mLyricBtn.setOnClickListener(this);
        mBroadcastBtn = findViewById(R.id.btn_broadcast);
        mBroadcastBtn.setOnClickListener(this);
        mTextBtn = findViewById(R.id.btn_text);
        mTextBtn.setOnClickListener(this);
        mVerticalSeekBarBtn = findViewById(R.id.btn_vertical_seek_bar);
        mVerticalSeekBarBtn.setOnClickListener(this);
        mDraggingBallBtn = findViewById(R.id.btn_dragging_ball);
        mDraggingBallBtn.setOnClickListener(this);
        mSwipeBtn = findViewById(R.id.btn_swipe_act);
        mSwipeBtn.setOnClickListener(this);
        mProgressBar2Btn = findViewById(R.id.btn_circle_progress2);
        mProgressBar2Btn.setOnClickListener(this);
        mSoftTestBtn = findViewById(R.id.btn_soft_input_test);
        mSoftTestBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_soft_input_test:
                SoftInputActivity.start(this);
                break;
            case R.id.btn_circle_progress:
                CircleProgressActivity.start(this);
                break;
            case R.id.btn_lyric:
                LyricActivity.start(this);
                break;
            case R.id.btn_broadcast:
                BroadcastActivity.start(this);
                break;
            case R.id.btn_text:
                TextViewActivity.start(this);
                break;
            case R.id.btn_vertical_seek_bar:
                VerticalSeekBarActivity.start(this);
                break;
            case R.id.btn_dragging_ball:
                DraggingBallActivity.start(this);
                break;
            case R.id.btn_swipe_act:
                SwipeBackActivity.start(this);
                break;
            case R.id.btn_circle_progress2:
                CircleProgress2Activity.start(this);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean setSwipeBack() {
        return false;
    }
}
