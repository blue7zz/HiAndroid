package com.example.hilibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InvalidTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public InvalidTextView(@NonNull Context context) {
        this(context,null);
    }

    public InvalidTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InvalidTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }
    private void initDraw(){
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth((float)1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(0,height/2,width,height/2,mPaint);
    }
}
