package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;

import com.chenq.base.BaseActivity;
import com.example.chenq.R;
import com.example.chenq.widget.DraggingBallRGBView;
import com.example.chenq.widget.DraggingBallView;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: 圆盘拖动球
 */
public class DraggingBallActivity extends BaseActivity {

    private static final String TAG = "DraggingBallActivity";

    private DraggingBallView mDragView;
    private DraggingBallRGBView mDragRGBView;

    public static void start(Context context) {
        Intent starter = new Intent(context, DraggingBallActivity.class);
        context.startActivity(starter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_seekbar);

        mDragView = findViewById(R.id.dbv);
        mDragView.setProgressBarListener(new DraggingBallView.DragListener() {
            @Override
            public void onDragCallBack(int progress) {
                //LogUtil.e(TAG, "onDragBallCallBack:" + progress);
            }
        });

        mDragRGBView = findViewById(R.id.dbv_rgb);

        SeekBar sb = findViewById(R.id.seek_bar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDragView.setProgress(progress);
                mDragRGBView.setProgress(progress);
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
