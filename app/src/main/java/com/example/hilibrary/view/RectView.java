package com.example.hilibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.hilibrary.R;

public class RectView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @ColorInt
    private int mColor = Color.RED;


    public RectView(Context context) {
        this(context,null);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        analysisAttributesSet(attrs, R.styleable.RectView);
        initDraw();
    }

    private void analysisAttributesSet(AttributeSet attrs, int[] rectView) {
        TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs,rectView);
        //提取RectView属性集合
        mColor = mTypedArray.getColor(R.styleable.RectView_rect_color,Color.RED);
        mTypedArray.recycle();
    }


    private void initDraw() {
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth((float) 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        //处理padding
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        canvas.drawLine(0 + paddingLeft, height / 2 + paddingTop, width + paddingRight, height / 2 + paddingBottom, mPaint);
    }


}
