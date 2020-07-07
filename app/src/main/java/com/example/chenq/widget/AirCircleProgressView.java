package com.example.chenq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.util.LogUtils;

/**
 * create by chenqi on 2020/7/4
 * Email: chenqwork@gmail.com
 * Desc:  空调旋钮
 * 刻度数量为61跟
 */
public class AirCircleProgressView extends View {

    private static final String TAG = "AirCircleProgressView";

    private Context mContext;
    // 虚线弧
    private Paint mStrokePaint;
    private Paint mPointerPaint;
    private Paint mTestPaint;
    private int mStrokeColorNormal;
    private int mStrokeColorSelect;
    // 虚线弧线条数
    private int mLineCount = 72;
    // 虚线弧线条默认长度
    private float mLineLength = 60;
    private float mPointLength = 70;
    private float mStrokeWidthNormal = 3;
    private float mStrokeWidthSelect = 6;
    private float perDegrees;
    private float startDegrees;
    private float endDegrees;
    private float sumDegrees;
    // 内部虚线的外部半径
    private float mLineExternalRadius;
    // 内部虚线的内部半径
    private float mLineInsideRadius;
    // 指针外部半径
    private float mPointerExternalRadius;
    // 指针内部半径
    private float mPointerInnerRadius;

    private float mWidth = 650;

    private Point mCenterPoint;
    private boolean antiAlias = true;

    public AirCircleProgressView(Context context) {
        this(context, null);
    }

    public AirCircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AirCircleProgressView);
        mStrokeColorNormal = typedArray.getColor(R.styleable.AirCircleProgressView_color_normal, getResources().getColor(R.color.color_air_progress_nor_bottom));
        mStrokeColorSelect = typedArray.getColor(R.styleable.AirCircleProgressView_color_select, getResources().getColor(R.color.color_air_progress_select_top));
        typedArray.recycle();

        mCenterPoint = new Point();
        //虚线角度
        perDegrees = (float) (2.0f * Math.PI / mLineCount);
        startDegrees = (float) (150 * Math.PI / 180);
        endDegrees = (float) (210 * Math.PI / 180);
        sumDegrees = (float) (2.0f * Math.PI);

        // 210 * Math.PI / 180

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(antiAlias);
        mStrokePaint.setColor(mStrokeColorNormal);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mStrokeWidthNormal);

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(antiAlias);
        mPointerPaint.setColor(mStrokeColorSelect);
        mPointerPaint.setStyle(Paint.Style.STROKE);
        mPointerPaint.setStrokeWidth(mStrokeWidthSelect);


        mTestPaint = new Paint();
        mTestPaint.setAntiAlias(antiAlias);
        mTestPaint.setColor(Color.BLUE);
        mTestPaint.setStyle(Paint.Style.STROKE);
        mTestPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制刻度
        drawArcLine(canvas);
        // 绘制指针
        drawPointer(canvas);
        {
            float startX1 = mCenterPoint.x + (float) Math.sin(startDegrees) * mPointerInnerRadius;
            float startY1 = mCenterPoint.x - (float) Math.cos(startDegrees) * mPointerInnerRadius;
            float stopX1 = mCenterPoint.x + (float) Math.sin(startDegrees) * mPointerExternalRadius;
            float stopY1 = mCenterPoint.x - (float) Math.cos(startDegrees) * mPointerExternalRadius;
            canvas.drawLine(startX1, startY1, stopX1, stopY1, mTestPaint);

            float startX2 = mCenterPoint.x + (float) Math.sin(endDegrees) * mPointerInnerRadius;
            float startY2 = mCenterPoint.x - (float) Math.cos(endDegrees) * mPointerInnerRadius;
            float stopX2 = mCenterPoint.x + (float) Math.sin(endDegrees) * mPointerExternalRadius;
            float stopY2 = mCenterPoint.x - (float) Math.cos(endDegrees) * mPointerExternalRadius;
            canvas.drawLine(startX2, startY2, stopX2, stopY2, mTestPaint);
        }
    }

    private void drawPointer(Canvas canvas) {
        float degrees = endDegrees;
        for (int i = 0; i < mLineCount; i++) {
            degrees = i * perDegrees;
            if (degrees >= curPointerDegrees) {
                break;
            }
        }
        if (degrees > startDegrees && degrees < endDegrees) {
            degrees = startDegrees;
        }
        float startX = mCenterPoint.x + (float) Math.sin(degrees) * mPointerInnerRadius;
        float startY = mCenterPoint.x - (float) Math.cos(degrees) * mPointerInnerRadius;
        float stopX = mCenterPoint.x + (float) Math.sin(degrees) * mPointerExternalRadius;
        float stopY = mCenterPoint.x - (float) Math.cos(degrees) * mPointerExternalRadius;
        canvas.drawLine(startX, startY, stopX, stopY, mPointerPaint);
    }

    private int mProgress = 0;
    private float curPointerDegrees = 0;

    private void drawArcLine(Canvas canvas) {
        for (int i = 0; i < mLineCount; i++) {
            float degrees = i * perDegrees;
            if (degrees > startDegrees && degrees < endDegrees) {
                continue;
            }
            setStrokeStyle(degrees);
            float startX = mCenterPoint.x + (float) Math.sin(degrees) * mLineInsideRadius;
            float startY = mCenterPoint.x - (float) Math.cos(degrees) * mLineInsideRadius;
            float stopX = mCenterPoint.x + (float) Math.sin(degrees) * mLineExternalRadius;
            float stopY = mCenterPoint.x - (float) Math.cos(degrees) * mLineExternalRadius;
            //LogUtil.e(TAG, "degrees:" + degrees + " StartX:" + startX + " StartY:" + startY + " StopX:" + stopX + " stopY:" + stopY);
            canvas.drawLine(startX, startY, stopX, stopY, mStrokePaint);
        }
    }

    private void setStrokeStyle(float curDegrees) {
        boolean isSelect = false;
        if (mProgress >= 0 && mProgress < 50) {
            isSelect = curDegrees <= curPointerDegrees && curDegrees > endDegrees;
        } else if (mProgress > 50 && mProgress <= 100) {
            isSelect = !(curDegrees > curPointerDegrees && curDegrees <= startDegrees);
        } else if (mProgress == 50) {
            isSelect = !(curDegrees <= startDegrees);
        }
        if (isSelect) {
            LogUtils.e(TAG, "curDegrees:" + curDegrees + " curPointerDegrees:" + curPointerDegrees + " endDegrees:" + endDegrees);
        }

        mStrokePaint.setColor(isSelect ? mStrokeColorSelect : mStrokeColorNormal);
        mStrokePaint.setStrokeWidth(isSelect ? mStrokeWidthSelect : mStrokeWidthNormal);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (checkInArcArea(x, y)) {
                    double touchDegrees = getBallArc(x, y);
                    LogUtils.e(TAG, "touchDegrees:" + touchDegrees + " startDegrees:" + startDegrees + " endDegrees:" + endDegrees + " sumDegrees:" + sumDegrees);

                    if (touchDegrees >= endDegrees || touchDegrees <= startDegrees) {
                        curPointerDegrees = (float) touchDegrees;
                        if (touchDegrees >= endDegrees) {
                            mProgress = (int) ((touchDegrees - endDegrees) / (sumDegrees - endDegrees) * 50);
                        } else if (touchDegrees <= startDegrees) {
                            mProgress = 50 + (int) (touchDegrees / (sumDegrees - endDegrees) * 50);
                        }
                        LogUtils.e(TAG, "mProgress---" + mProgress + " curPointerDegrees:" + curPointerDegrees);
                        postInvalidate();
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 计算触摸点是否在圆弧内
     *
     * @param x
     * @param y
     */
    private boolean checkInArcArea(float x, float y) {
        float offsetX = Math.abs(x - mCenterPoint.x);
        float offsetY = Math.abs(y - mCenterPoint.y);
        double touchR = Math.sqrt(Math.pow(offsetX, 2) + Math.pow(offsetY, 2));
        return touchR >= mPointerInnerRadius && touchR <= mPointerExternalRadius;
    }

    /**
     * 计算触摸点到圆心连线到圆0度位置之间的弧度
     */
    private double getBallArc(float x, float y) {
        float r = mWidth / 2;
        float offX = 0;
        float offY = 0;
        //当球在XY轴上
        if (x == r && y < r) {
            return 0;
        } else if (x == r && y > r) {
            return 180;
        } else if (x < r && y == r) {
            return 270;
        } else if (x > r && y == r) {
            return 90;
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
        double tan = Math.atan2(offX, offY);
        double angleA = 180 * tan / Math.PI;
        if (quadrant == 1) {
            angleA = 360 - angleA;
        } else if (quadrant == 2) {
            angleA = angleA;
        } else if (quadrant == 3) {
            angleA = 180 + angleA;
        } else if (quadrant == 4) {
            angleA = 180 - angleA;
        }
        return angleA / 360.00f * 2.0f * Math.PI;
    }

    /**
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = " + w + "; h = " + h + "; oldw = " + oldw + "; oldh = " + oldh);
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;
        float r = mWidth / 2;
        mPointerExternalRadius = r;
        mPointerInnerRadius = r - mPointLength;
        mLineInsideRadius = mPointerInnerRadius;
        mLineExternalRadius = mLineInsideRadius + mLineLength;
    }

    /**
     * 布局测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        setMeasuredDimension(measureView(widthMeasureSpec, height), measureView(heightMeasureSpec, height));
    }

    /**
     * 测量 View
     *
     * @param measureSpec
     * @param defaultSize View 的默认大小
     * @return
     */
    private static int measureView(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public void onProgressChange(int progress) {
        LogUtils.e(TAG, "onProgressChange: " + progress);
        mProgress = progress;
        setCurPointerDegrees(mProgress);
        postInvalidate();
    }


    private void setCurPointerDegrees(int progress) {
        if (mProgress >= 0 && mProgress <= 50) {
            curPointerDegrees = endDegrees + perDegrees * progress / 100 * 60;
        } else if (mProgress > 50 && mProgress <= 100) {
            curPointerDegrees = perDegrees * (progress - 50) / 100 * 60;
        }
    }


}
