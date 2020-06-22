package com.example.chenq.UI;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.base.Interface.AnimatorListenerImpl;
import com.example.chenq.base.util.DrawableUtil;

/**
 * create by chenqi on 2020/6/1
 * Email: chenqwork@gmail.com
 * Desc: 垂直进度条
 * 外部通过{@link #setProgress(int)}方法设置当前进度值
 * 通过{@link #setProgressBarLisener(ProgressBarListener)}监听滑动动作，并从 {@link ProgressBarListener#onProgressChanged(int currProgress)}回调中获取进度值
 * 注意绘制过程中Y坐标的合法性（Y坐标需要大于半个Bar的高度，小于背景条的高度加上半个Bar高度之和）
 */
public class VerticalSeekBar extends View {

    private static final String TAG = "MusicProgressBar";

    private RectF mFgRect;
    private RectF mBgRect;
    private Rect mTextRect;
    private Paint mFgPaint;
    private Paint mBgPaint;
    private Context mContext;
    private TextPaint mTextPaint;
    private float touchY;
    private int seekBgWidth;
    private int seekBgHeight;
    private int mSeekBarWidth;
    private int mSeekBarHeight;
    private int maxProgress = 100;
    private int lastAction = -999;
    private float mTextSize = 40f;
    private float mTextOffSetX = 16f;
    private float mSeekBgCorners = 22f;
    private Drawable mSeekBarDrawable;

    // 是否开启动画
    private boolean isOpenAnimate = false;

    public VerticalSeekBar(Context context) {
        this(context, null);
    }

    public VerticalSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        mBgPaint = new Paint();
        mBgPaint.setColor(getResources().getColor(R.color.color_white));
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgRect = new RectF();
        mFgPaint = new Paint();
        mFgPaint.setAntiAlias(true);
        mFgPaint.setStyle(Paint.Style.FILL);
        mFgRect = new RectF();
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(getResources().getColor(R.color.color_dimming_seek_percent));
        mTextRect = new Rect();
        mSeekBarDrawable = DrawableUtil.getDrawable(mContext, R.mipmap.bg_dimming_temp_seek_bar);

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.VerticalSeekBar);
        mSeekBarWidth = (int) a.getDimension(R.styleable.VerticalSeekBar_seekBarWidth,52);
        mSeekBarHeight = (int) a.getDimension(R.styleable.VerticalSeekBar_seekBarHeight,52);
        seekBgWidth = (int) a.getDimension(R.styleable.VerticalSeekBar_seekBgWidth,32);
        seekBgHeight = (int) a.getDimension(R.styleable.VerticalSeekBar_seekBgHeight,590);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTextPaint.getTextBounds("100%", 0, 4, mTextRect);
        int width = (int) (mSeekBarWidth + mTextOffSetX + mTextRect.width() + 3);
        int height = mSeekBarHeight + seekBgHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制进度条背景
        drawSeekBg(canvas);
        // 绘制进度条
        drawSeekFg(canvas);
        // 绘制拖动Bar
        drawSeekBar(canvas);
        // 绘制百分比
        drawPercent(canvas);
    }

    /**
     * 绘制进度条背景
     *
     * @param canvas
     */
    private void drawSeekBg(Canvas canvas) {
        mBgRect.set(
                (mSeekBarWidth - seekBgWidth) / 2,
                mSeekBarHeight / 2,
                (mSeekBarWidth + seekBgWidth) / 2,
                seekBgHeight + mSeekBarHeight / 2
        );
        mBgPaint.setColor(getResources().getColor(R.color.color_dimming_vertical_seek_bg));
        canvas.drawRoundRect(mBgRect, mSeekBgCorners, mSeekBgCorners, mBgPaint);
    }

    /**
     * 绘制进度条
     *
     * @param canvas
     */
    private void drawSeekFg(Canvas canvas) {
        mFgRect.set(
                (mSeekBarWidth - seekBgWidth) / 2,
                getProgressOffSetY(),
                (mSeekBarWidth + seekBgWidth) / 2,
                seekBgHeight + mSeekBarHeight / 2
        );
        //前景线性渐变
        LinearGradient foregroundLinear = new LinearGradient(
                0, mSeekBarHeight / 2, 0, seekBgHeight + mSeekBarHeight / 2,
                getResources().getColor(R.color.color_dimming_seek_offset_top),
                getResources().getColor(R.color.color_dimming_seek_offset_bottom),
                Shader.TileMode.REPEAT);
        mFgPaint.setShader(foregroundLinear);
        canvas.drawRoundRect(mFgRect, mSeekBgCorners, mSeekBgCorners, mFgPaint);
    }

    /**
     * 绘制拖动bar
     *
     * @param canvas
     */
    private void drawSeekBar(Canvas canvas) {
        float progressOffsetY = getProgressOffSetY();
        mSeekBarDrawable.setBounds(
                0,
                (int) (progressOffsetY - mSeekBarHeight / 2),
                mSeekBarWidth,
                (int) (progressOffsetY + mSeekBarHeight / 2));
        mSeekBarDrawable.draw(canvas);
    }

    /**
     * 绘制百分比
     *
     * @param canvas
     */
    private void drawPercent(Canvas canvas) {
        String curTxt = getCurProgressTxt();
        mTextPaint.getTextBounds(curTxt, 0, curTxt.length(), mTextRect);
        int mTextHeight = mTextRect.height();
        float x = mSeekBarWidth + mTextOffSetX;
        float pxY = (getProgressOffSetY() - mSeekBarHeight / 2);
        float y = mTextHeight / 2 + mSeekBarHeight / 2 + pxY;
        canvas.drawText(curTxt, x, y, mTextPaint);
    }

    /**
     * 获取当前进度文本
     *
     * @return
     */
    private String getCurProgressTxt() {
        return getCurProgress() + "%";
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    private int getCurProgress() {
        return (int) (1.00f * (getHeight() - mSeekBarHeight / 2 - getProgressOffSetY()) / seekBgHeight * 100);
    }

    /**
     * 模拟点击计算TouchY
     *
     * @param progress
     */
    private void calculateCurTouchY(int progress) {
        progress = progress < 0 ? 0 : (progress > 100 ? 100 : progress);
        touchY = mSeekBarHeight / 2 + seekBgHeight * (1.00f * progress / 100);
    }

    /**
     * 检查Y坐标是否合法
     *
     * @return true为合法
     */
    private boolean checkIllegalOfY() {
        return touchY > mSeekBarHeight / 2 && touchY < (getHeight() - mSeekBarHeight / 2);
    }

    /**
     * 计算当前SeekBar圆心Y坐标
     * 注意，绘制过程中需要用到Y坐标的一律通过 getProgressOffSetY 方法获取，保证Y坐标的合法性
     *
     * @return
     */
    private float getProgressOffSetY() {
        if (touchY < mSeekBarHeight / 2) {
            touchY = mSeekBarHeight / 2;
        } else if (touchY > getHeight() - mSeekBarHeight / 2) {
            touchY = getHeight() - mSeekBarHeight / 2;
        }
        return touchY;
    }

    /**
     * 动画，仅用于点击 - 抬起事件
     */
    private void startAnimator() {
        final float transitOffsetY = seekBgHeight * 1.00f * getCurProgress() / 100;
        final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(600);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = ((int) animation.getAnimatedValue()) / 100.00f;
                //点击任意区域，开始动画后Y值应该是由高到低的过程
                float bottomY = mSeekBarHeight / 2 + seekBgHeight;
                touchY = bottomY - transitOffsetY * percent;
                if (checkIllegalOfY()) {
                    postInvalidate();
                }
            }
        });
        animator.addListener(new AnimatorListenerImpl() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animator.removeAllListeners();
                animator.removeAllUpdateListeners();
                //postInvalidate();
            }
        });
        animator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastAction = action;
                touchY = event.getY();
                if(!isOpenAnimate){
                    postInvalidate();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                lastAction = action;
                touchY = event.getY();
                if (touchY >= mSeekBarHeight / 2 && touchY <= (getHeight() - mSeekBarHeight / 2)) {
                    postInvalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_MOVE || lastAction == MotionEvent.ACTION_DOWN) {
                    if (lastAction == MotionEvent.ACTION_DOWN && isOpenAnimate) {
                        startAnimator();
                    }
                    // 进度接口回调
                    if (mProgressBarListener != null) {
                        int curProgress = getCurProgress();
                        Log.e(TAG, "currProgress:" + curProgress);
                        mProgressBarListener.onProgressChanged(curProgress);
                    }
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress <= maxProgress && progress >= 0) {
            //模拟点击事件计算touchY
            calculateCurTouchY(progress);
            //重绘
            postInvalidate();
        } else {
            throw new IllegalArgumentException("progress value is not Illegal.");
        }
    }

    /**
     * 进度监听事件
     */
    private ProgressBarListener mProgressBarListener;

    public void setProgressBarLisener(ProgressBarListener lisener) {
        this.mProgressBarListener = lisener;
    }

    /**
     * 进度监听事件
     */
    public interface ProgressBarListener {
        void onProgressChanged(int currProgress);
    }

}
