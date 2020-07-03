package com.example.chenq.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.chenq.R;

/**
 * create by chenqi on 2020/7/2.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class BlurView extends View {

    private static final String TAG = "BlurView";
    private Bitmap mBlueBitmap;
    private Paint mBgPaint;

    private int mWidth;
    private int mHeight;

    public BlurView(Context context) {
        this(context, null);
    }

    public BlurView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mBlueBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Paint mBluePaint = new Paint();
        mBluePaint.setAntiAlias(true);
        mBluePaint.setColor(getContext().getResources().getColor(R.color.color_knob_bg));
        Canvas canvas = new Canvas(mBlueBitmap);
        canvas.drawRect(new Rect(0, 0, mWidth, mHeight), mBluePaint);
        mBlueBitmap = BlurBitmapUtil.blurBitmap(getContext(), mBlueBitmap, 20);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(
                mBlueBitmap,
                new Rect(0, 0, mBlueBitmap.getWidth(), mBlueBitmap.getHeight()),
                new Rect(0, 0, mWidth, mHeight), mBgPaint);
    }
}
