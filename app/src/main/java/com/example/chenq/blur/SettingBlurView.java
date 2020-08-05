package com.example.chenq.blur;

import android.content.Context;
import android.content.res.TypedArray;
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
 * create by chenqi on 2020/8/4.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class SettingBlurView extends RealtimeBlurView {

    private Paint mPaint;
    private RectF mRectF;

    public SettingBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mRectF = new RectF();
    }

    /**
     * Custom oval shape
     */
    @Override
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurredBitmap, int overlayColor) {
        if (blurredBitmap != null) {
            mRectF.right = getWidth();
            mRectF.bottom = getHeight();
            mPaint.reset();
            mPaint.setAntiAlias(true);
            BitmapShader shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.postScale(mRectF.width() / blurredBitmap.getWidth(), mRectF.height() / blurredBitmap.getHeight());
            shader.setLocalMatrix(matrix);
            mPaint.setShader(shader);
            canvas.drawRoundRect(mRectF, 8, 8, mPaint);

			mPaint.reset();
			mPaint.setAntiAlias(true);
			mPaint.setColor(overlayColor);
			canvas.drawRoundRect(mRectF, 8, 8, mPaint);
        }
    }

}