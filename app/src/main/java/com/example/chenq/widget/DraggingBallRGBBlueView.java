package com.example.chenq.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.util.BlurBitmapUtil;
import com.example.chenq.util.DimmingLampConfig;
import com.example.chenq.util.DrawableUtil;

/**
 * create by chenqi on 2020/6/22.
 * Email: chenqwork@gmail.com
 * Desc: 色盘选择器-模糊效果
 */
public class DraggingBallRGBBlueView extends DraggingBallView {

    private static final String TAG = "DraggingBallRGBBlueView";

    private Drawable mBgDrawable;
    private Bitmap mBlueBitmap;
    private Paint mBluePaint;
    private float perDegrees;
    private float allDegrees = (float) (2.0f * Math.PI);
    private RectF mRectF = new RectF(0, 0, 570, 570);
    private int mDottedLineCount = 180;


    public DraggingBallRGBBlueView(Context context) {
        this(context, null);
    }

    public DraggingBallRGBBlueView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggingBallRGBBlueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBgDrawable = DrawableUtil.getDrawable(getContext(), R.mipmap.bg_rgb);
        mBgDrawable.setBounds(new Rect(0, 0, mWidth, mHeight));

        mBluePaint = new Paint();
        mBluePaint.setAntiAlias(true);
        mBlueBitmap = Bitmap.createBitmap(570, 570, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBlueBitmap);
        SweepGradient mSweepGradient = new SweepGradient(
                mCenterPoint.x, mCenterPoint.y, DimmingLampConfig.mRgbColors, null);
        mBgPaint.setShader(mSweepGradient);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mWidth / 2, mBgPaint);
        mBlueBitmap = BlurBitmapUtil.blurBitmap(getContext(), mBlueBitmap, 20);
    }

    /**
     * 绘制背景圆
     *
     * @param canvas
     */
    @Override
    public void drawBg(Canvas canvas) {
        // 直接画背景图
        //mBgDrawable.draw(canvas);
//        SweepGradient mSweepGradient = new SweepGradient(
//                mCenterPoint.x, mCenterPoint.y, DimmingLampConfig.mRgbColors, null);
//        mBgPaint.setShader(mSweepGradient);
//        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mWidth / 2, mBgPaint);

        //蒙版
        if (mBlueBitmap != null) {
            BitmapShader shader = new BitmapShader(mBlueBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.postScale(mRectF.width() / mBlueBitmap.getWidth(), mRectF.height() / mBlueBitmap.getHeight());
            shader.setLocalMatrix(matrix);
            mBluePaint.setShader(shader);
            canvas.drawCircle(mRectF.width() / 2, mRectF.width() / 2, mRectF.width() / 2-5, mBluePaint);
        }
    }

    private int curColorIndex;

    @Override
    public int getBallColor() {
        double curDegrees = getBallArc();
        curColorIndex = (int) (curDegrees / 2);
        int color = DimmingLampConfig.getRGBColorValue(curColorIndex);
        return color;
    }

    @Override
    public void onProgressCallBack() {
        if (lastColorIndex != curColorIndex) {
            lastColorIndex = curColorIndex;
            if (mDragListener != null) {
                mDragListener.onDragCallBack(lastColorIndex);
            }
        }
    }

    /**
     * 计算球心到圆心连线到圆0度位置之间的弧度
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

        //LogUtil.e(TAG, "angleA:" + angleA);
        return angleA;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        int r = mWidth / 4;
        perDegrees = (allDegrees / mDottedLineCount);
        float degrees = progress * perDegrees + perDegrees * 45;
        if (degrees >= allDegrees) {
            degrees -= allDegrees;
        }
        float startX = mCenterPoint.x + (float) Math.sin(degrees) * r;
        float startY = mCenterPoint.x - (float) Math.cos(degrees) * r;
        mBallCenterPoint.set((int) startX, (int) startY);
        postInvalidate();
    }
}
