package com.example.androidart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.androidart.R;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class CircleView extends View {
    private static final String TAG = "CircleView";

    @ColorInt
    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mContentW, mContentH;
    private int mCx, mCy;
    private int mRadius;
    private int mDefaultSize;

    public CircleView(Context context) {
        super(context);
        init(null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = a.getColor(R.styleable.CircleView_circle_color, Color.RED);
        a.recycle();

        mDefaultSize = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec));
        Log.d(TAG, "onMeasure: heightMeasureSpec = " + MeasureSpec.toString(heightMeasureSpec));
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);

        // wrap_content情况：给出默认高度
        if (modeW == MeasureSpec.AT_MOST || modeH == MeasureSpec.AT_MOST) {
            int measuredW = modeW == MeasureSpec.AT_MOST ? mDefaultSize : sizeW;
            int measuredH = modeH == MeasureSpec.AT_MOST ? mDefaultSize : sizeH;

            setMeasuredDimension(measuredW, measuredH);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = "+w+", oldw = "+oldw);
        mContentW = getWidth() - getPaddingLeft() - getPaddingRight();
        mContentH = getHeight() - getPaddingTop() - getPaddingBottom();

        mCx = getPaddingLeft() + mContentW/2;
        mCy = getPaddingTop() + mContentH/2;

        mRadius = Math.min(mContentW, mContentH) / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: changed = " + changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: canvas = " + canvas);

        canvas.drawCircle(mCx, mCy, mRadius, mPaint);
    }
}
