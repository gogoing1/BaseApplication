package com.example.chenq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.base.activity.BaseActivity;
import com.example.chenq.base.util.FileUtils;
import com.example.chenq.base.util.LyricUtil;

import java.io.File;

import me.zhengken.lyricview.LyricView;

/**
 * create by chenqi on 2020/6/9
 * Email: chenqwork@gmail.com
 * Desc: 歌词实践类
 */
public final class LyricActivity extends BaseActivity {

    private static final String TAG = "LyricActivity";
    private TextView curLyricPlayText;
    private LyricView mLyricView;
    private String filePath = FileUtils.MUSIC_PATH + File.separator + "gem.lrc";

    public static void start(Context context) {
        Intent starter = new Intent(context, LyricActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric_test);
        mLyricView = findViewById(R.id.custom_lyric_view);
        curLyricPlayText = findViewById(R.id.tv_cur_lyric_text);

        //写入歌词文件
        //initFile(this);

        setLyricViewData();
    }


    /**
     * 更新歌词View控件
     */
    private void setLyricViewData() {
        long curPlayMillis = 56000;

        //给自定义歌词控件设置歌词文件数据
        File lyricFile = new File(filePath);
        mLyricView.setLyricFile(lyricFile);
        //mLyricView.setCurrentTimeMillis(curPlayMillis);

        /**
         * 由于自定义歌词控件将歌词控制逻辑封装在了内部
         * 这里我们把里面处理歌词的逻辑拆分出来，并放到了LyricUtil里面
         * 方便在不使用 {@link LyricView}的情况下单独跟进当前播放进度（毫秒）来获取当前播放的歌词对象
         */
        LyricUtil.LyricInfo lyricInfo = LyricUtil.getInstance().parse(filePath);
        String curLine = LyricUtil.getInstance().getCurLyricContent(lyricInfo, curPlayMillis);
        curLyricPlayText.setText(curLine);
    }

    /**
     * 将歌词文件写到SD卡
     *
     * @param context
     */
    private void initFile(Context context) {
        try {
            File mFile = new File(filePath);
            if (mFile.exists()) return;
            FileUtils.getInstance(context)
                    .copyAssetsToOtherPath("lyric", FileUtils.MUSIC_PATH)
                    .setCallback(new FileUtils.FileOperateCallback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "写入成功");
                        }

                        @Override
                        public void onFailed(String error) {
                            Log.d(TAG, "写入失败" + error);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
