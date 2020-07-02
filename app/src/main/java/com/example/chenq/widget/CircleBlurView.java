package com.example.chenq.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.github.mmin18.widget.RealtimeBlurView;

/**
 * create by chenqi on 2020/7/1.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class CircleBlurView extends RealtimeBlurView {
    Paint mPaint;
    RectF mRectF;

    public CircleBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mRectF = new RectF();
        mRectF.right = 570;
        mRectF.bottom = 570;
    }

    /**
     * Custom oval shape
     */
    @Override
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurredBitmap, int overlayColor) {
        if (blurredBitmap != null) {
            mPaint.reset();
            mPaint.setAntiAlias(true);
            BitmapShader shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.postScale(mRectF.width() / blurredBitmap.getWidth(), mRectF.height() / blurredBitmap.getHeight());
            shader.setLocalMatrix(matrix);
            mPaint.setShader(shader);
            canvas.drawCircle(mRectF.width() / 2, mRectF.width() / 2, mRectF.width() / 2, mPaint);

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setColor(overlayColor);
            canvas.drawOval(mRectF, mPaint);
        }
    }
}
