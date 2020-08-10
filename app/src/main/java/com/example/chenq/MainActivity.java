package com.example.chenq;

import android.graphics.fonts.SystemFonts;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.chenq.base.AbstractActivity;
import com.example.chenq.activity.BlurActivity;
import com.example.chenq.activity.BroadcastActivity;
import com.example.chenq.activity.CircleProgress2Activity;
import com.example.chenq.activity.CircleProgressActivity;
import com.example.chenq.activity.DraggingBallActivity;
import com.example.chenq.activity.LyricActivity;
import com.example.chenq.activity.SoftInputActivity;
import com.example.chenq.activity.SwipeBackActivity;
import com.example.chenq.activity.TextViewActivity;
import com.example.chenq.activity.VerticalSeekBarActivity;
import com.example.chenq.activity.ViewPagerActivity;
import com.example.chenq.activity.WifiActivity;
import com.example.chenq.adapter.MainListAdapter;
import com.example.chenq.constant.MainConstant;
import com.example.chenq.music.MusicActivity;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MainActivity extends AbstractActivity {
    private ListView listView;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.lv_main_list);
        final MainListAdapter adapter = new MainListAdapter(mContext, R.layout.layout_list_item, MainConstant.getItemList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                switch (item) {
                    case MainConstant.BTN_WIFI:
                        WifiActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BLUR_TEST:
                        BlurActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_SOFT_INPUT_TEST:
                        SoftInputActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_CIRCLE_PROGRESS:
                        CircleProgressActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_LYRIC:
                        LyricActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BROADCAST:
                        BroadcastActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_TEXT:
                        TextViewActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_VERTICAL_SEEK_BAR:
                        VerticalSeekBarActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_DRAGGING_BALL:
                        DraggingBallActivity.start(MainActivity.this);
                        break;
                    case MainConstant.SWIPE_ACT:
                        SwipeBackActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_CIRCLE_PROGRESS2:
                        CircleProgress2Activity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_MUSIC:
                        MusicActivity.start(MainActivity.this);
                        break;
                    case MainConstant.BTN_VIEW_PAGER:
                        ViewPagerActivity.start(MainActivity.this);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean setSwipeBack() {
        return false;
    }
}
