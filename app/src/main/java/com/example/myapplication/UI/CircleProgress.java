package com.example.myapplication.UI;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.myapplication.R;
import com.example.myapplication.base.Interface.AnimatorListenerImpl;
import com.example.myapplication.base.utils.LogUtil;

/**
 * 旋钮 自定义View
 */
public class CircleProgress extends View {

    private static final String TAG = CircleProgress.class.getSimpleName();
    private boolean isDebug = true;
    private Context mContext;

    public static final int DEFAULT_PROGRESS = 50;

    //提示
    private TextPaint mHintPaint;
    private CharSequence mHint;
    private int mHintColor;
    private float mHintSize;
    private float mHintOffset;

    //单位(亮度)
    private TextPaint mUnitPaint;
    private CharSequence mUnit;
    private int mUnitColor;
    private float mUnitSize;
    private float mUnitOffset;

    //logo
    private Paint imgPaint;
    private Bitmap defBitmap = null;
    private float mImgLeftSet;
    private float mImgTopSet;

    //百分比
    private TextPaint mValuePaint;
    private float mValue;
    private float mMaxValue;
    private float mValueOffset;
    private int mPrecision;
    private String mPrecisionFormat;
    private int mValueColor;
    private float mValueSize;
    private float valueUnitSize;

    //彩虹条
    private Paint mArcPaint;
    private float mArcWidth;
    private float mStartAngle, mSweepAngle;
    private RectF mRectF;
    private RectF mBitmapRectF;
    private float mPercent;
    private long mAnimTime;
    private ValueAnimator mAnimator;

    //虚线弧
    private Paint mBgArcPaint;
    private int mBgArcColor;
    private int mArcColor;
    private float mBgArcWidth;
    //线条数
    private int mDottedLineCount = 270 / 2;
    //内圈边缘跟虚线之间的距离
    private int mLineDistance = 20;
    //线条长度
    private float mDottedLineLength = 40;
    //是否使用渐变
    protected boolean useGradient = true;
    // 内部虚线的外部半径
    private float mExternalDottedLineRadius;
    // 内部虚线的内部半径
    private float mInsideDottedLineRadius;

    //内圈指针
    private Paint mPointerPaint;
    private int mPointerColor;
    //指针外圈半径
    private float mOuterPointerRadius = 90;
    private float curPointerDegrees;

    //圆心坐标，半径
    private Point mCenterPoint;
    private float mRadius;
    private float mTextOffsetPercentInRadius;

    //前景色起始颜色
    private int foreStartColor;
    //前景色结束颜色
    private int foreEndColor;
    //其他
    private boolean antiAlias;
    private int mOuterBgRadius;
    private int mPadding;
    private int mMidBgRadius;
    private int mMidRainbowRadius;
    private int mInnerBgRadius;
    private int mInnerRainRadius;
    private int rainbowWidth = 26;
    private Drawable mOuterBgDrawable;
    private Drawable mMidBgDrawable;
    private Drawable mMidRainbowDrawable;
    private Bitmap mMidRainBowBitmap;
    private Drawable mInnerBgDrawable;
    private float perDegrees;
    private float startDegrees;
    private float endDegrees;
    private float sumDegreed;

    //动画
    //此次动画旋转的角度（正整数与负整数），加上当前所在旋转角度之后的值不能小于90度，不能大于360度
    private float curAnimRotateDegrees;
    //进度为0的时候需要旋转的角度
    private float startRotateDegrees = 90;
    //进度为100的时候需要旋转的角度
    private float endRotateDegrees = 360;
    //当前进度的旋转角度
    private float curRotateDegrees;


    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        imgPaint = new Paint();
        mAnimator = new ValueAnimator();
        mRectF = new RectF();
        mBitmapRectF = new RectF();
        mCenterPoint = new Point();

        //虚线角度
        perDegrees = (float) (2.0f * Math.PI / mDottedLineCount);
        startDegrees = (float) (135 * Math.PI / 180);
        endDegrees = (float) (225 * Math.PI / 180);
        sumDegreed = (float) (2.0f * Math.PI);

        initAttrs(attrs);
        initPaint();
        initResource();
    }

    private void initResource() {
        mOuterBgDrawable = getDrawable(mContext, R.mipmap.knob_outer_bg_icon);
        mMidBgDrawable = getDrawable(mContext, R.mipmap.knob_mid_bg_icon);
        mMidRainbowDrawable = getDrawable(mContext, R.mipmap.knob_mid_rainbow_icon);
        mMidRainBowBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.knob_mid_rainbow_icon);
        mInnerBgDrawable = getDrawable(mContext, R.mipmap.knob_inner_bg_icon);
    }


    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        antiAlias = typedArray.getBoolean(R.styleable.CircleProgressBar_antiAlias, true);

        mHint = typedArray.getString(R.styleable.CircleProgressBar_hint);
        mHintColor = typedArray.getColor(R.styleable.CircleProgressBar_hintColor, Color.BLACK);
        mHintSize = typedArray.getDimension(R.styleable.CircleProgressBar_hintSize, 15);

        mValue = typedArray.getFloat(R.styleable.CircleProgressBar_value, 50);
        mMaxValue = typedArray.getFloat(R.styleable.CircleProgressBar_maxValue, 50);
        //内容数值精度格式
        mPrecision = typedArray.getInt(R.styleable.CircleProgressBar_precision, 0);
        mPrecisionFormat = getPrecisionFormat(mPrecision);
        mValueColor = typedArray.getColor(R.styleable.CircleProgressBar_valueColor, Color.BLACK);
        mValueSize = typedArray.getDimension(R.styleable.CircleProgressBar_valueSize, 15);
        valueUnitSize = typedArray.getDimension(R.styleable.CircleProgressBar_valueUnitSize, 15);

        mUnit = typedArray.getString(R.styleable.CircleProgressBar_unit);
        mUnitColor = typedArray.getColor(R.styleable.CircleProgressBar_unitColor, Color.BLACK);
        mUnitSize = typedArray.getDimension(R.styleable.CircleProgressBar_unitSize, 30);

        mArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_arcWidth, 15);
        mStartAngle = typedArray.getFloat(R.styleable.CircleProgressBar_startAngle, 270);
        mSweepAngle = typedArray.getFloat(R.styleable.CircleProgressBar_sweepAngle, 360);

        mBgArcColor = typedArray.getColor(R.styleable.CircleProgressBar_bgArcColor, Color.WHITE);
        mPointerColor = typedArray.getColor(R.styleable.CircleProgressBar_pointerColor, Color.WHITE);
        mArcColor = typedArray.getColor(R.styleable.CircleProgressBar_arcColors, Color.RED);
        mBgArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_bgArcWidth, 15);
        mTextOffsetPercentInRadius = typedArray.getFloat(R.styleable.CircleProgressBar_textOffsetPercentInRadius, 0.33f);
        mAnimTime = typedArray.getInt(R.styleable.CircleProgressBar_animTime, 1000);
        mDottedLineCount = typedArray.getInteger(R.styleable.CircleProgressBar_dottedLineCount, mDottedLineCount);
        mLineDistance = typedArray.getInteger(R.styleable.CircleProgressBar_lineDistance, mLineDistance);
        mDottedLineLength = typedArray.getDimension(R.styleable.CircleProgressBar_dottedLineWidth, mDottedLineLength);
        foreStartColor = typedArray.getColor(R.styleable.CircleProgressBar_foreStartColor, Color.BLUE);
        foreEndColor = typedArray.getColor(R.styleable.CircleProgressBar_foreEndColor, Color.BLUE);
        defBitmap = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.CircleProgressBar_drawbleIcon, R.mipmap.knob_brightness_icon));

        //资源释放
        typedArray.recycle();
    }

    private void initPaint() {
        mHintPaint = new TextPaint();
        // 设置抗锯齿,会消耗较大资源，绘制图形速度会变慢。
        mHintPaint.setAntiAlias(antiAlias);
        // 设置绘制文字大小
        mHintPaint.setTextSize(mHintSize);
        // 设置画笔颜色
        mHintPaint.setColor(mHintColor);
        // 从中间向两边绘制，不需要再次计算文字
        mHintPaint.setTextAlign(Paint.Align.CENTER);

        mValuePaint = new TextPaint();
        mValuePaint.setAntiAlias(antiAlias);
        mValuePaint.setTextSize(mValueSize);
        mValuePaint.setColor(mValueColor);
        // 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
        mValuePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mValuePaint.setTextAlign(Paint.Align.CENTER);

        mUnitPaint = new TextPaint();
        mUnitPaint.setAntiAlias(antiAlias);
        mUnitPaint.setTextSize(mUnitSize);
        mUnitPaint.setColor(mUnitColor);
        mUnitPaint.setTextAlign(Paint.Align.CENTER);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(antiAlias);
        // 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
        mArcPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔粗细
        mArcPaint.setStrokeWidth(mArcWidth);
        // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
        // Cap.ROUND(圆形样式)或Cap.SQUARE(方形样式)
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgArcPaint = new Paint();
        mBgArcPaint.setAntiAlias(antiAlias);
        mBgArcPaint.setColor(mBgArcColor);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mBgArcWidth);
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(antiAlias);
        mPointerPaint.setColor(mPointerColor);
        mPointerPaint.setStyle(Paint.Style.STROKE);
        mPointerPaint.setStrokeWidth(mBgArcWidth);
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
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
     * onSizeChanged
     * 会在onDraw方法执行前调用
     * <p>
     * R外 = width * 0.92 / 2
     * padding = width * 0.08 / 2
     * R中 = width * 0.6f / 2   > 0.772
     * RRainbow = width * 0.6f / 2
     * R内 = width * 0.4f / 2   > 0.521
     *
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

        mPadding = (int) (h * 0.08 / 2);
        mOuterBgRadius = (int) (h * 0.92 / 2);
        mMidBgRadius = (int) (h * 0.772 / 2);
        mMidRainbowRadius = (int) ((h * 0.60 / 2) + rainbowWidth / 2);
        mInnerBgRadius = (int) (h * 0.521 / 2);
        mInnerRainRadius = (int) (h * 0.4 / 2);

        {
            //求圆弧和背景圆弧的最大宽度
            float maxArcWidth = Math.max(mArcWidth, mBgArcWidth);

            //求最小值作为实际值
            int minSize = Math.min(
                    w - getPaddingLeft() - getPaddingRight() - 2 * (int) maxArcWidth,
                    h - getPaddingTop() - getPaddingBottom() - 2 * (int) maxArcWidth);

            //减去圆弧的宽度，否则会造成部分圆弧绘制在外围
            mRadius = minSize / 2;

            //绘制圆弧的边界
            // mRectF.left = mCenterPoint.x - mRadius - maxArcWidth / 2;
            // mRectF.top = mCenterPoint.y - mRadius - maxArcWidth / 2;
            // mRectF.right = mCenterPoint.x + mRadius + maxArcWidth / 2;
            // mRectF.bottom = mCenterPoint.y + mRadius + maxArcWidth / 2;

            mRectF.set(84, 84, w - 84, h - 84);
            mBitmapRectF.set(84, 84, w - 84, h - 84);

            //计算文字绘制时的 baseline,由于文字的baseline、descent、ascent等属性只与textSize和typeface有关，所以此时可以直接计算
            //若value、hint、unit由同一个画笔绘制或者需要动态设置文字的大小，则需要在每次更新后再次计算
            mValueOffset = mCenterPoint.y + getBaselineOffsetFromY(mValuePaint);

            mHintOffset = mCenterPoint.y - mInnerRainRadius * mTextOffsetPercentInRadius + getBaselineOffsetFromY(mHintPaint);
            mUnitOffset = mCenterPoint.y + mInnerRainRadius * mTextOffsetPercentInRadius + getBaselineOffsetFromY(mUnitPaint);

            //图标的坐标
            mImgLeftSet = mCenterPoint.x - defBitmap.getWidth() / 2;
            mImgTopSet = mUnitOffset + 18;

            if (useGradient) {
                LinearGradient gradient = new LinearGradient(0, 0, w, h, foreEndColor, foreStartColor, Shader.TileMode.CLAMP);
                mArcPaint.setShader(gradient);
            } else {
                mArcPaint.setColor(mArcColor);
            }

            Log.d(TAG, "onSizeChanged: 控件大小 = " + "(" + w + ", " + h + ")"
                    + "圆心坐标 = " + mCenterPoint.toString()
                    + ";圆半径 = " + mRadius
                    + ";圆的外接矩形 = " + mRectF.toString());
        }

        mOuterBgDrawable.setBounds(
                mCenterPoint.x - mOuterBgRadius, mPadding,
                mCenterPoint.x + mOuterBgRadius, mCenterPoint.y + mOuterBgRadius);
        mMidBgDrawable.setBounds(
                mCenterPoint.x - mMidBgRadius, mCenterPoint.y - mMidBgRadius,
                mCenterPoint.x + mMidBgRadius, mCenterPoint.y + mMidBgRadius);
        mMidRainbowDrawable.setBounds(
                mCenterPoint.x - mMidRainbowRadius, mCenterPoint.y - mMidRainbowRadius,
                mCenterPoint.x + mMidRainbowRadius, mCenterPoint.y + mMidRainbowRadius);
        mInnerBgDrawable.setBounds(
                mCenterPoint.x - mInnerBgRadius, mCenterPoint.y - mInnerBgRadius,
                mCenterPoint.x + mInnerBgRadius, mCenterPoint.y + mInnerBgRadius);

        //虚线弧外圈半径
        mExternalDottedLineRadius = (float) (mInnerRainRadius - 24);
        //虚线弧内圈半径
        mInsideDottedLineRadius = mExternalDottedLineRadius - mDottedLineLength;
        //指针外圈半径
        mOuterPointerRadius = (float) (mInnerRainRadius - 12);
    }

    private float getBaselineOffsetFromY(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return ((Math.abs(fontMetrics.ascent) - fontMetrics.descent)) / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //外圈
        mOuterBgDrawable.draw(canvas);

        canvas.save();
        // - 3 是调试过程中产生的误差
        canvas.rotate(curRotateDegrees - 3, mCenterPoint.x, mCenterPoint.y);
        //中圈
        mMidBgDrawable.draw(canvas);
        //彩虹条
        mMidRainbowDrawable.draw(canvas);
        canvas.restore();

        //内圈
        mInnerBgDrawable.draw(canvas);

        //logo
        drawBottomImage(canvas);

        //百分比
        drawText(canvas);

        //虚线
        drawArc(canvas);

        //指针
        drawPointer(canvas);
    }

    /**
     * 指针
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        float startX = mCenterPoint.x + (float) Math.sin(curPointerDegrees) * mInsideDottedLineRadius;
        float startY = mCenterPoint.x - (float) Math.cos(curPointerDegrees) * mInsideDottedLineRadius;
        float stopX = mCenterPoint.x + (float) Math.sin(curPointerDegrees) * mOuterPointerRadius;
        float stopY = mCenterPoint.x - (float) Math.cos(curPointerDegrees) * mOuterPointerRadius;
        canvas.drawLine(startX, startY, stopX, stopY, mPointerPaint);
    }

    /**
     * 绘制内容文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //百分比
        //Rect mProgressRect = new Rect();
        //mValuePaint.getTextBounds(String.valueOf(mValue), 0, String.valueOf(mValue).length(), mProgressRect);
        //canvas.drawText(String.format(mPrecisionFormat, mValue), mCenterPoint.x, mValueOffset, mValuePaint);

        //提示
        if (mHint != null) {
            canvas.drawText(mHint.toString(), mCenterPoint.x, mHintOffset, mHintPaint);
        }

        //单位
        if (mUnit != null) {
            canvas.drawText(mUnit.toString(), mCenterPoint.x, mUnitOffset, mUnitPaint);
        }
    }

    /**
     * 绘制图标
     *
     * @param canvas
     */
    private void drawBottomImage(Canvas canvas) {
        canvas.drawBitmap(defBitmap, mImgLeftSet, mImgTopSet, imgPaint);
    }

    /**
     * 绘制虚线弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        for (int i = 0; i < mDottedLineCount; i++) {
            float degrees = i * perDegrees;
            if (degrees > startDegrees && degrees < endDegrees) {
                continue;
            }
            float startX = mCenterPoint.x + (float) Math.sin(degrees) * mInsideDottedLineRadius;
            float startY = mCenterPoint.x - (float) Math.cos(degrees) * mInsideDottedLineRadius;
            float stopX = mCenterPoint.x + (float) Math.sin(degrees) * mExternalDottedLineRadius;
            float stopY = mCenterPoint.x - (float) Math.cos(degrees) * mExternalDottedLineRadius;
            canvas.drawLine(startX, startY, stopX, stopY, mBgArcPaint);
        }
    }

    /**
     * 获取当前旋转角度
     *
     * @return
     */
    private float getCurRotateDegrees() {
        return (float) (mValue * 2.7 + 90);
    }

    /**
     * 动画旋转角度检测
     *
     * @return
     */
    private boolean checkRotateDegrees() {
        return curRotateDegrees >= startRotateDegrees && curRotateDegrees <= endRotateDegrees;
    }

    /**
     * 外部旋钮进度变更接口
     *
     * @param progress
     */
    public void onProgressChange(int progress) {
        mValue = progress;
//        mValue += progress;
//        if (mValue < 0) {
//            mValue = 0;
//        }
//        if (mValue > 100) {
//            mValue = 100;
//        }
        refreshView((int) mValue);
    }

    /**
     * 更新数值，并播放动画
     *
     * @param targetProgress
     */
    public void refreshView(int targetProgress) {

        if (targetProgress < 0 || targetProgress > 100) {
            throw new IllegalArgumentException("progress index out of bounds exception");
        }

        curRotateDegrees = getCurRotateDegrees();
        mValue = targetProgress;
        curAnimRotateDegrees = getCurRotateDegrees() - curRotateDegrees;
        final float curDegrees = curRotateDegrees;

        LogUtil.e(TAG, "-------------------------------------------------");
        LogUtil.e(TAG, "curDegrees:-------- " + curDegrees + " curAnimRotateDegrees:" + curAnimRotateDegrees);

        //startAnimator(curDegrees);

        //不使用动画，直接绘制
        curRotateDegrees = curDegrees + curAnimRotateDegrees;
        LogUtil.e(TAG, "curRotateDegrees:" + curRotateDegrees);
        //指针
        curPointerDegrees = getCurPointerDegrees();
        invalidate();
    }

    private void startAnimator(final float curDegrees) {
        final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((int) animation.getAnimatedValue()) / 100.00f;
                curRotateDegrees = curDegrees + curAnimRotateDegrees * value;
                LogUtil.e(TAG, "curRotateDegrees:" + curRotateDegrees);
                if (checkRotateDegrees()) {
                    invalidate();
                }
            }
        });
        animator.addListener(new AnimatorListenerImpl() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animator.removeAllListeners();
                animator.removeAllUpdateListeners();

                //指针
                curPointerDegrees = getCurPointerDegrees();
                invalidate();
            }
        });
        animator.start();
    }

    /**
     * 获取当前指针角度
     *
     * @return
     */
    private float getCurPointerDegrees() {
        float degrees = 0;
        if (mValue == 50) {
            degrees = 0;
        }

        // 0 ~ 2.3561945   50 ~ 100
        if (mValue > 50 && mValue <= 100) {
            degrees = (1 - (100 - mValue) / 50) * startDegrees;
        }

        // 3.9269907 ~ 6.236643   0 ~ 50
        if (mValue < 50 && mValue >= 0) {
            //degrees = (mValue - 50) / 50 * 270 / 2;
            degrees = mValue / 50 * (sumDegreed - endDegrees) + endDegrees;
        }
        LogUtil.e(TAG, "mPointerDegrees " + degrees);
        return degrees;
    }

    /**--------------------------- Get Set ------------------------------*/

    /**
     * 获取最大值
     *
     * @return
     */
    public float getMaxValue() {
        return mMaxValue;
    }

    /**
     * 设置最大值
     *
     * @param maxValue
     */
    public void setMaxValue(float maxValue) {
        mMaxValue = maxValue;
    }

    /**
     * 获取精度
     *
     * @return
     */
    public int getPrecision() {
        return mPrecision;
    }

    public void setPrecision(int precision) {
        mPrecision = precision;
        mPrecisionFormat = getPrecisionFormat(precision);
    }

    public long getAnimTime() {
        return mAnimTime;
    }

    public void setAnimTime(long animTime) {
        mAnimTime = animTime;
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public CharSequence getHint() {
        return mHint;
    }

    public void setHint(CharSequence hint) {
        mHint = hint;
    }

    public CharSequence getUnit() {
        return mUnit;
    }

    public void setUnit(CharSequence unit) {
        mUnit = unit;
    }

    public float getValue() {
        return mValue;
    }


    /**
     * 重置
     */
    public void reset() {
        //startAnimator(mPercent, 0.0f, 1000L);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //释放资源
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
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 获取数值精度格式化字符串
     *
     * @param precision
     * @return
     */
    public static String getPrecisionFormat(int precision) {
        return "%." + precision + "f";
    }

    /**
     * Drawable 资源加载
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        if (resId <= 0) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT == 21) {
            return context.getDrawable(resId);
        } else {
            return context.getResources().getDrawable(resId);
        }
    }
}
