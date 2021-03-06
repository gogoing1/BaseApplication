package com.chenq.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chenq.base.R;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_in_from_right, R.anim.alpha_out);
    }
}
