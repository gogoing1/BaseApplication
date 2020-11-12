package com.example.chenq.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by chenqi on 2020/8/5.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MainConstant {

    public static final String BLUR_TEST = "BLUR_TEST";
    public static final String SWIPE_ACT = "SWIPE_ACT";
    public static final String BTN_DRAGGING_BALL = "BTN_DRAGGING_BALL";
    public static final String BTN_VERTICAL_SEEK_BAR = "BTN_VERTICAL_SEEK_BAR";
    public static final String BTN_CIRCLE_PROGRESS = "BTN_CIRCLE_PROGRESS";
    public static final String BTN_CIRCLE_PROGRESS2 = "BTN_CIRCLE_PROGRESS2";
    public static final String BTN_LYRIC = "BTN_LYRIC";
    public static final String BROADCAST = "BROADCAST";
    public static final String BTN_TEXT = "BTN_TEXT";
    public static final String BTN_SOFT_INPUT_TEST = "BTN_SOFT_INPUT_TEST";
    public static final String BTN_MUSIC = "BTN_MUSIC";
    public static final String BTN_VIEW_PAGER = "BTN_VIEW_PAGER";
    public static final String BTN_WIFI = "BTN_WIFI";
    public static final String BTN_INFINITE_RECYCLE_VIEW = "BTN_INFINITE_RECYCLE_VIEW";

    public static List<String> getItemList() {
        String[] s = new String[]{
                BTN_INFINITE_RECYCLE_VIEW,
                BTN_WIFI,
                BLUR_TEST,
                SWIPE_ACT,
                BTN_DRAGGING_BALL,
                BTN_VERTICAL_SEEK_BAR,
                BTN_CIRCLE_PROGRESS,
                BTN_CIRCLE_PROGRESS2,
                BTN_LYRIC,
                BROADCAST,
                BTN_TEXT,
                BTN_SOFT_INPUT_TEST,
                BTN_MUSIC,
                BTN_VIEW_PAGER
        };
        return Arrays.asList(s);
    }

}