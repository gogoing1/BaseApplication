package com.example.chenq.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.base.util.LogUtil;

/**
 * create by chenqi on 2020/6/22.
 * Email: chenqwork@gmail.com
 * Desc: 拖动球
 * 注意绘制过程中 X，Y坐标的合法性（）
 */
public class DraggingBallView extends View {

    private static final String TAG = "DraggingBallView";

    private Context mContext;

    //用图片画背景边缘会稍微有一点点糊
    //private Drawable mBgDrawable;

    private final int mWidth = 570;
    private final int mHeight = 570;
    private final int mBallWidth = 90;
    private int lastAction = -999;

    private final int mBallShadowOffsetX = -2;
    private final int mBallShadowOffsetY = 4;
    private Paint mBgPaint;
    private Paint mBallPaint;
    private Point mCenterPoint;
    private Point mTouchPoint;
    private Point mBallCenterPoint;

    public DraggingBallView(Context context) {
        this(context, null);
    }

    public DraggingBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggingBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        //mBgDrawable = DrawableUtil.getDrawable(mContext, R.mipmap.bg_dimming_rgb_color_temp);
        //mBgDrawable.setBounds(0, 0, mBgWidth, mBgHeight);
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);

        mBallPaint = new Paint();
        mBallPaint.setAntiAlias(true);
        mCenterPoint = new Point(mWidth / 2, mHeight / 2);
        mTouchPoint = new Point(mCenterPoint);
        mBallCenterPoint = new Point(mCenterPoint);

        //硬件加速会导致阴影不起作用，这里对其禁用，切勿在onDraw里面设置，那样会引起无限重绘
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mBgDrawable.draw(canvas)
        // 背景
        drawBg(canvas);
        // 小球
        drawBall(canvas);
    }

    /**
     * 绘制拖动圆球
     *
     * @param canvas
     */
    private void drawBall(Canvas canvas) {
        mBallPaint.setColor(getResources().getColor(R.color.blue));
        mBallPaint.setShadowLayer(5, mBallShadowOffsetX, mBallShadowOffsetY, getResources().getColor(R.color.color_black_30));
        canvas.drawCircle(mBallCenterPoint.x, mBallCenterPoint.y, mBallWidth / 2, mBallPaint);
    }


    /**
     * 获取点击的X坐标
     *
     * @return
     */
    private float getTouchX() {
        LogUtil.e(TAG, "x:" + mTouchPoint.x);
        return mTouchPoint.x;
    }

    /**
     * 获取点击的Y坐标
     *
     * @return
     */
    private float getTouchY() {
        LogUtil.e(TAG, "y:" + mTouchPoint.y);
        return mTouchPoint.y;
    }

    /**
     * 绘制背景圆
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        LinearGradient mGradient = new LinearGradient(
                mCenterPoint.x, 0,
                mCenterPoint.x, mHeight,
                new int[]{getResources().getColor(R.color.color_dimming_offset_1),
                        getResources().getColor(R.color.color_dimming_offset_2),
                        getResources().getColor(R.color.color_dimming_offset_3)},
                null, Shader.TileMode.MIRROR);
        mBgPaint.setShader(mGradient);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mWidth / 2, mBgPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
//                lastAction = action;
                setPoint(event);
                postInvalidate();
                return true;
            case MotionEvent.ACTION_UP:
//                if (lastAction == MotionEvent.ACTION_MOVE || lastAction == MotionEvent.ACTION_DOWN) {
//                    if (mDragListener != null) {
//                        Log.e(TAG, "x:" + mTouchPoint.x + " y:" + mCenterPoint.y);
//                        mDragListener.onDragCallBack(mTouchPoint.x, mCenterPoint.y);
//                    }
//                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 根据点击事件，设置小球的 X，Y坐标
     *
     * @param event
     */
    private void setPoint(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float r = mWidth / 2;
        float xb, yb;
        float rb = mBallWidth / 2;

        //X，Y的坐标应在圆内
        float absX = Math.abs(x - r);
        float absY = Math.abs(y - r);
        float absX2 = (float) Math.pow(absX, 2);
        float absY2 = (float) Math.pow(absY, 2);
        float r2 = (float) Math.pow(r, 2);
        float ballR2 = (float) Math.pow(r - rb, 2);

        // 触摸范围，任意一点到圆心的距离小于半径即可
        if (absX2 + absY2 <= r2) {
            mTouchPoint.set((int) x, (int) y);
            // 计算球的位置
            if (absX2 + absY2 <= ballR2) {
                mBallCenterPoint.set((int) x, (int) y);
            } else {
                // 当触摸区域处于圆环(当球处于内边缘时，球心到圆内边这块区域)内，计算球的圆心所在圆环内圆上的坐标，原理：r3:r4 = x3:x4 = y3:y4
                float rate = (float) ((r - rb) / Math.sqrt(absX2 + absY2));
                xb = (absX * rate);
                yb = (absY * rate);
                // quadrant1
                if (x <= r && y <= r) {
                    xb = r - xb;
                    yb = r - yb;
                    mBallCenterPoint.set((int) xb, (int) yb);
                    return;
                }
                // quadrant2
                if (x >= r && x <= mWidth && y <= r) {
                    xb += r;
                    yb = r - yb;
                    mBallCenterPoint.set((int) xb, (int) yb);
                    return;
                }
                // quadrant3
                if (x <= r && y >= r && y <= mWidth) {
                    xb = r - xb;
                    yb += r;
                    mBallCenterPoint.set((int) xb, (int) yb);
                    return;
                }
                // quadrant4
                if (x >= r && x <= mWidth && y >= r && y <= mWidth) {
                    xb += r;
                    yb += r;
                    mBallCenterPoint.set((int) xb, (int) yb);
                    return;
                }
            }
        }
    }

    /**
     * 进度监听事件
     */
    private DragListener mDragListener;

    public void setProgressBarListener(DragListener listener) {
        this.mDragListener = listener;
    }

    /**
     * 进度监听事件
     */
    public interface DragListener {
        void onDragCallBack(int x, int y);
    }

}
