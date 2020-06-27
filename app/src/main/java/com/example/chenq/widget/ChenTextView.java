package com.example.chenq.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.chenq.R;
import com.example.chenq.util.LogUtil;

/**
 * create by chenqi on 2020/6/15
 * Email: chenqwork@gmail.com
 * Desc: View 测试
 */
public class ChenTextView extends View {

    private static final String TAG = "ChenTextView";

    private Paint mPaint;

    public ChenTextView(Context context) {
        this(context, null);
        init();
    }

    public ChenTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public ChenTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //LogUtil.d(TAG, "onMeasure " + "widthMeasureSpec " + widthMeasureSpec + " widthMeasureSpec " + widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        LogUtil.d(TAG, "onMeasure " + "widthMode " + widthMode + " widthSize " + widthSize + " heightModel " + heightModel + " heightSize " + heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.d(TAG, "onLayout " + "changed " + changed + " left " + left + " top " + top + " right " + right + " bottom " + bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.d(TAG, "onSizeChanged " + "w " + w + " h " + h + " old_w " + oldw + " old_h " + oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        canvas.drawColor(getResources().getColor(R.color.color_black_20));
        //中线
        canvas.drawLine(0, getMeasuredWidth() / 2, getMeasuredWidth(), getMeasuredWidth(), mPaint);
        canvas.drawLine(300, 0, 300, 300, mPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
