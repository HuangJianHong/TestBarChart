package com.cncn.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by T163 on 2017/1/9.
 */

public class PieView extends View {

    private Paint mPaint;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    public PieView(Context context) {
        super(context);
        initPaint();


    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.BLACK);

        for (int i = 0; i < 4; i++) {
            double random = Math.random();
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 400, 200, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(300-200, 400-200, 300+200, 400+200);
        canvas.drawArc(rect , 0, 90 , true, mPaint);

        mPaint.setColor(mColors[3]);
        canvas.drawArc(rect ,90, 30 , true, mPaint);


        mPaint.setColor(mColors[7]);
        canvas.drawArc(rect ,120, 30 , true, mPaint);

        mPaint.setColor(mColors[1]);
        canvas.drawArc(rect ,180, 60 , true, mPaint);

    }


}
