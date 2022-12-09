package com.example.hilibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MyView extends androidx.appcompat.widget.AppCompatTextView {

    private int lastX = 0;
    private int lastY = 0;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                //Layout 设置
//                layout(getLeft() + offsetX,getTop() + offsetY,getRight()+offsetX,getBottom()+offsetY);

                //设置偏移量
                //offsetLeftAndRight(offsetX);
                //offsetTopAndBottom(offsetY);

//                //设置laiyoutParams ,设置margins的值，边距
//                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
////                 layoutParams.setMargins(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);
//                layoutParams.leftMargin = getLeft()+offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);

//                scrollBy(100,100);

//                (getParent()).scrollTo(-offsetX,-offsetY);

                break;
        }
        return true;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("View", "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("View", "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("View", "onDraw");
    }
}