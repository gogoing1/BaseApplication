package com.example.chenq;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chenq.activity.CircleProgressActivity;
import com.example.chenq.base.activity.BaseActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mCircleProgressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleProgressBtn = findViewById(R.id.btn_circle_progress);
        mCircleProgressBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_circle_progress:
                CircleProgressActivity.start(this);
                break;
            default:
                break;

        }
    }
}
