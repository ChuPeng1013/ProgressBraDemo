package com.example.chupeng.progressbrademo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ChuPeng on 2017/7/23.
 */

public class ProgressBarView extends View
{
    private int mRadius = 100;//半径
    private int mStrokeWidth = 20;//画笔宽度
    private int mSmallCircleColor = Color.WHITE;//内圆颜色
    private int mBigCircleColor = Color.BLUE;//外圆颜色
    private int mRingCircleColor = Color.RED;//圆环颜色
    private int mTotalProgressBar = 100;//总进度
    private int mCurrentProgressBar = 0;//当前进度
    private int mTextColor = Color.GREEN;//字体颜色
    private Paint mSmallPaint;
    private Paint mBigPaint;
    private Paint mRingPaint;
    private Paint mTextPaint;

    public ProgressBarView(Context context) {
        super(context);
        init();
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context, attrs);
        init();
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
        init();
    }

    //获取自定义属性
    private void initAttribute(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
        mRadius = a.getInteger(R.styleable.ProgressBarView_radius, 100);
        mStrokeWidth = a.getInteger(R.styleable.ProgressBarView_strokeWidth, 20);
        mSmallCircleColor = a.getColor(R.styleable.ProgressBarView_smallCircleColor, Color.WHITE);
        mBigCircleColor = a.getColor(R.styleable.ProgressBarView_bigCircleColor, Color.BLUE);
        mRingCircleColor = a.getColor(R.styleable.ProgressBarView_ringCircleColor, Color.RED);
        mTotalProgressBar = a.getColor(R.styleable.ProgressBarView_totalProgressBar, 100);
        mCurrentProgressBar = a.getInteger(R.styleable.ProgressBarView_currentProgressBar, 0);
        mTextColor = a.getColor(R.styleable.ProgressBarView_textColor, Color.GREEN);
        a.recycle();
    }

    //初始化Paint
    private void init()
    {
        mSmallPaint = new Paint();
        mSmallPaint.setColor(mSmallCircleColor);
        mSmallPaint.setStyle(Paint.Style.FILL);
        mSmallPaint.setAntiAlias(true);

        mBigPaint = new Paint();
        mBigPaint.setColor(mBigCircleColor);
        mBigPaint.setStyle(Paint.Style.FILL);
        mBigPaint.setAntiAlias(true);

        mRingPaint = new Paint();
        mRingPaint.setColor(mRingCircleColor);
        mRingPaint.setStrokeWidth(mStrokeWidth);
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(50);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //适配wrap_content属性
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST)
        {
            setMeasuredDimension(300, 300);
        }
        else if(widthMeasureMode == MeasureSpec.AT_MOST)
        {
            setMeasuredDimension(300, heightMeasureSize);
        }
        else if(heightMeasureMode == MeasureSpec.AT_MOST)
        {
            setMeasuredDimension(widthMeasureSize, 300);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //适配Padding属性
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        int width = getWidth() + paddingLeft + paddingRight;
        int height = getHeight() + paddingTop + paddingBottom;
        //得到圆形进度条的圆心坐标
        int xCenter = width/2;
        int yCenter = height/2;
        //绘制同心圆
        canvas.drawCircle(xCenter, yCenter, mRadius + mStrokeWidth, mBigPaint);//大圆
        canvas.drawCircle(xCenter, yCenter, mRadius, mSmallPaint);//小圆
        if(mTotalProgressBar > 0)
        {
            //圆弧所在区域
            RectF oval = new RectF();
            oval.left = xCenter - (mRadius + mStrokeWidth/2);
            oval.top = yCenter - (mRadius + mStrokeWidth/2);
            oval.right = (mRadius + mStrokeWidth/2) * 2 + oval.left;
            oval.bottom = (mRadius + mStrokeWidth/2) * 2 + oval.top;
            //绘制圆弧
            canvas.drawArc(oval, -90, ((float) mCurrentProgressBar / mTotalProgressBar) * 360, false, mRingPaint);
            //显示当前进度（10%这种形式的）
            String txt = (int) (((float)mCurrentProgressBar / (float)mTotalProgressBar) * 100) + "%";
            Rect rect = new Rect();
            mTextPaint.getTextBounds(txt, 0, txt.length(), rect);
            int mTxtWidth = rect.right - rect.left;
            int mTxtHeight = rect.bottom - rect.top;
            canvas.drawText(txt, xCenter - mTxtWidth / 2, yCenter + mTxtHeight / 4, mTextPaint);
        }
    }

    //设置当前进度并且请求重新绘制View
    public void setCurrentProgressBar(int mCurrentProgressBar)
    {
        this.mCurrentProgressBar = mCurrentProgressBar;
        invalidate();
    }
}