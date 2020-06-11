package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.example.chenq.R;
import com.example.chenq.UI.CircleProgressView;
import com.example.chenq.base.activity.BaseActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: 旋钮
 */
public class CircleProgressActivity extends BaseActivity {

    private static final String TAG = "CircleProgressActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, CircleProgressActivity.class);
        context.startActivity(starter);
    }

    private CircleProgressView mCircleView;
    private AppCompatSeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);


        mCircleView = findViewById(R.id.circle_progress);
        mSeekBar = findViewById(R.id.sb_circle_progress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "progress：" + progress);
                mCircleView.onProgressChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
