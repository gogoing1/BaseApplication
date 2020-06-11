package com.example.chenq.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.chenq.R;

/**
 * create by chenqi on 2020/6/1
 * Email: chenqwork@gmail.com
 * Desc: 音乐卡片-自定义播放进度条
 */
public class MusicProgressBar extends View {

    private static final String TAG = "MusicProgressBar";

    private int width;
    private int height;
    private Rect mFgRect;
    private Rect mBgRect;
    private Paint mPaint;
    private float touchX;
    private int progressBarHeight;
    private int progressBarWidth;
    private int progressWidth;
    private int progressBarIndex;
    private int curProgress = 0;
    private int maxProgress = 100;
    private int lastAction = -999;
    private Bitmap mProgressBarBitmap;

    public MusicProgressBar(Context context) {
        this(context, null);
    }

    public MusicProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * View 初始化
     */
    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.color_white));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mBgRect = new Rect();
        mFgRect = new Rect();

        mProgressBarBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.progress_bar);
        progressBarHeight = 22;
        progressBarWidth = 22;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        progressWidth = getWidth() - progressBarWidth;
        setMeasuredDimension(width, progressBarHeight);
    }

    /**
     * Tip：不要在onDraw里面创建对象
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制进度条背景
        mBgRect.set(progressBarWidth / 2, (getHeight() - height) / 2, width - progressBarWidth / 2, (getHeight() + height) / 2);
        mPaint.setColor(getResources().getColor(R.color.color_white_30));
        canvas.drawRect(mBgRect, mPaint);

        // 绘制进度条前景
        int rightFgX = (int) touchX;
        if (touchX >= getWidth() - progressBarWidth / 2) {
            rightFgX = getWidth() - progressBarWidth / 2;
        }
        mFgRect.set(progressBarWidth / 2, (getHeight() - height) / 2, rightFgX, (getHeight() + height) / 2);
        mPaint.setColor(getResources().getColor(R.color.color_white));
        //前景线性渐变
        LinearGradient forgroundLinear = new LinearGradient(0, 0, getWidth(), 0,
                getResources().getColor(R.color.color_white_30),
                getResources().getColor(R.color.color_white),
                Shader.TileMode.MIRROR);
        mPaint.setShader(forgroundLinear);
        canvas.drawRect(mFgRect, mPaint);
        mPaint.setShader(null);

        // 绘制拖动Bar
        progressBarIndex = getProgressBarLeft();
        canvas.drawBitmap(mProgressBarBitmap, progressBarIndex, height / 2, mPaint);
    }

    /**
     * 获取拖动按钮的绘制边界
     *
     * @return
     */
    private int getProgressBarLeft() {
        if (touchX <= 0) {
            return 0;
        } else if (touchX >= (getWidth() - progressBarWidth)) {
            return getWidth() - progressBarWidth;
        }
        return (int) touchX;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "Music ProgressBar ACTION_UP,and last action is:" + (lastAction == 0 ? "ACTION_DOWN" : "ACTION_MOVE"));
                if (lastAction == MotionEvent.ACTION_MOVE
                        || lastAction == MotionEvent.ACTION_DOWN) {
                    // 由滑动事件抬起时，通知外部接口当前进度变更
                    if (mProgressBarLisener != null) {
                        calculateCurrProgress();
                        Log.e(TAG, "currProgress:" + curProgress);
                        mProgressBarLisener.onProgressChanged(curProgress);
                    }
                }
                return true;
            case MotionEvent.ACTION_DOWN:
                lastAction = action;
                touchX = event.getX();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                lastAction = action;
                touchX = event.getX();
                if (touchX >= 0 && touchX <= (getWidth() - progressBarWidth)) {
                    postInvalidate();
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当前进度计算
     *
     * @return
     */
    private void calculateCurrProgress() {
        curProgress = (int) (100 * (1.00f * progressBarIndex / progressWidth));
    }

    /**
     * 模拟TouchX计算
     */
    private void calculateCurrTouchX() {
        touchX = (int) progressWidth * (1.00f * curProgress / maxProgress);
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    public int getProgress() {
        return curProgress;
    }


    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress <= maxProgress && progress >= 0) {
            curProgress = progress;
            //模拟点击事件计算touchX
            calculateCurrTouchX();
            //重绘
            postInvalidate();
        } else {
            throw new IllegalArgumentException("progress value is not Illegal.");
        }
    }


    /**
     * 外部进度监听事件
     */
    private ProgressBarListener mProgressBarLisener;

    public void setProgressBarLisener(ProgressBarListener lisener) {
        this.mProgressBarLisener = lisener;
    }

    /**
     * 音乐进度条进度变更事件
     */
    public interface ProgressBarListener {
        void onProgressChanged(int currProgress);
    }
}
