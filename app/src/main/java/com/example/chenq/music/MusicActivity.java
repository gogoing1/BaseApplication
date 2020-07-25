package com.example.chenq.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chenq.base.BaseActivity;
import com.example.chenq.R;

/**
 * create by chenqi on 2020/7/25.
 * Email: chenqwork@gmail.com
 * Desc: 音乐播放器
 */
public class MusicActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MusicActivity";
    private boolean isPlaying;

    public static void start(Context context) {
        Intent starter = new Intent(context, MusicActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_music_player);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_previous).setOnClickListener(this);
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_previous:
                Toast.makeText(this, "上一首", Toast.LENGTH_SHORT).show();
                previous();
                break;
            case R.id.btn_play:
                Toast.makeText(this, "播放/暂停", Toast.LENGTH_SHORT).show();
                if (isPlaying) {
                    pause();
                } else {
                    play();
                }
                break;
            case R.id.btn_next:
                Toast.makeText(this, "下一首", Toast.LENGTH_SHORT).show();
                next();
                break;
            case R.id.btn_stop:
                Toast.makeText(this, "停止播放", Toast.LENGTH_SHORT).show();
                stop();
                break;
            default:
                break;
        }
    }

    /**
     * 上一首
     */
    private void previous() {
    }

    /**
     * 下一首
     */
    private void next() {
    }

    /**
     * 播放
     */
    private void play() {
    }

    /**
     * 暂停
     */
    private void pause() {
    }

    /**
     * 停止
     */
    private void stop() {
    }


}
