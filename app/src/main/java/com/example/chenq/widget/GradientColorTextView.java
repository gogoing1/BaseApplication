package com.example.chenq.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.chenq.R;


@SuppressLint("AppCompatCustomView")
public class GradientColorTextView extends TextView {
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private Rect mTextBound = new Rect();
    private int colorStart,colorEnd;
    private boolean isPortrait;

    public GradientColorTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GradientView);
        colorStart = array.getColor(R.styleable.GradientView_from,0xFFFFFFFF);
        colorEnd = array.getColor(R.styleable.GradientView_to,0xFFFFFFFF);
        String direct = array.getString(R.styleable.GradientView_direct);
        isPortrait = direct.equals("V");
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);

        int x1 = isPortrait ? 0 : getWidth();
        int y1 = isPortrait ? getHeight() : 0;
        mLinearGradient = new LinearGradient(0, 0, x1, y1, new int[]{colorStart, colorEnd}, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

}