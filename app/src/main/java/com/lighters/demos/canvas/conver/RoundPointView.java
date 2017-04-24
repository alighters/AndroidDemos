package com.lighters.demos.canvas.conver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by david on 2017/4/23.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */

public class RoundPointView extends View {

    /**
     * 是否可以操作
     */
    private boolean mHandled;

    private Paint mPaint;

    public RoundPointView(Context context) {
        this(context, null);
    }

    public RoundPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        if (!mHandled) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, getHeight() / 3, mPaint);
            mPaint.setXfermode(null);
        } else {
            mPaint.setColor(Color.LTGRAY);
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, getHeight() / 2, mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, getHeight() / 4, mPaint);
        }
    }

    public void setHandled(boolean handled) {
        mHandled = handled;
    }
}
