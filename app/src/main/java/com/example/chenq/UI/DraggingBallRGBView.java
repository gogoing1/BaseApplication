package com.example.chenq.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.base.util.ColorUtils;
import com.example.chenq.base.util.DrawableUtil;
import com.example.chenq.base.util.LogUtil;

/**
 * create by chenqi on 2020/6/22.
 * Email: chenqwork@gmail.com
 * Desc: 色盘选择器
 */
public class DraggingBallRGBView extends DraggingBallView {

    private static final String TAG = "DraggingBallRGBView";

    private Drawable mBgDrawable;
    private int rgbColors[];

    public DraggingBallRGBView(Context context) {
        this(context, null);
    }

    public DraggingBallRGBView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggingBallRGBView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBgDrawable = DrawableUtil.getDrawable(getContext(), R.mipmap.bg_rgb);
        mBgDrawable.setBounds(new Rect(0, 0, mWidth, mHeight));

        rgbColors = ColorUtils.getDimmingRGBColor();
    }


    /**
     * 绘制背景圆
     *
     * @param canvas
     */
    @Override
    public void drawBg(Canvas canvas) {
        drawRGB2(canvas);
    }

    private void drawRGB2(Canvas canvas) {
        mBgDrawable.draw(canvas);
    }

    @Override
    public int getBallColor() {
        double degrees = getBallArc();
        int colorIndex = (int) (degrees / 2);
        return rgbColors[colorIndex];
    }

    /**
     * 计算球心到圆心连线到圆0度位置之间的弧度
     * 待优化
     */
    private double getBallArc() {
        float r = mWidth / 2;
        float x = mBallCenterPoint.x;
        float y = mBallCenterPoint.y;
        float offX = 0;
        float offY = 0;

        //当球在XY轴上
        if (x == r && y < r) {
            return 270;
        } else if (x == r && y > r) {
            return 90;
        } else if (x < r && y == r) {
            return 180;
        } else if (x > r && y == r) {
            return 0;
        }

        //其他象限
        int quadrant = 0;
        // quadrant1
        if (x < r && y < r) {
            offX = r - x;
            offY = r - y;
            quadrant = 1;
        }
        // quadrant2
        if (x > r && x < mWidth && y < r) {
            offX = x - r;
            offY = r - y;
            quadrant = 2;
        }
        // quadrant3
        if (x < r && y > r && y < mWidth) {
            offX = r - x;
            offY = y - r;
            quadrant = 3;
        }
        // quadrant4
        if (x > r && x < mWidth && y > r && y < mWidth) {
            offX = x - r;
            offY = y - r;
            quadrant = 4;
        }

        //计算角度
        double tan = Math.atan2(offX, offY);
        double angleA = 180 * tan / Math.PI;
        if (quadrant == 1) {
            angleA = 270 - angleA;
        } else if (quadrant == 2) {
            angleA = 270 + angleA;
        } else if (quadrant == 3) {
            angleA = 90 + angleA;
        } else if (quadrant == 4) {
            angleA = 90 - angleA;
        }

        LogUtil.e(TAG, "angleA:" + angleA);
        return angleA;
    }

    private float perDegrees;
    private int mDottedLineCount = 180;

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        int r = getWidth() / 2 / 2;
        int max = 180;
        perDegrees = (float) (2.0f * Math.PI / mDottedLineCount);
        float degrees = progress * max / 100 * perDegrees + perDegrees * 45;
        float startX = mCenterPoint.x + (float) Math.sin(degrees) * r;
        float startY = mCenterPoint.x - (float) Math.cos(degrees) * r;
        mBallCenterPoint.set((int) startX, (int) startY);
        postInvalidate();
    }
}
