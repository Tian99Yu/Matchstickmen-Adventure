package com.example.game.gamecode.CustomControls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class JoystickView extends View {
    private double angle;
    private float viewWidth;
    private float handleWidth;
    private float viewCenterX, viewCenterY;
    private float handleCenterX, handleCenterY;
    private final Paint backgroundPaint = new Paint();
    private final Paint controlPaint = new Paint();

    public JoystickView(Context context) {
        super(context);
        createJoystick();
    }

    public JoystickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createJoystick();
    }

    public JoystickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createJoystick();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int radius;
        if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            radius = 25;
        }
        else {
            radius = Math.min(widthSize, heightSize);
        }
        viewWidth = radius;

        handleWidth = radius / 4;

        setMeasuredDimension((int) viewWidth, (int) viewWidth);
    }

    private void createJoystick() {
        setFocusable(true);

        backgroundPaint.setAlpha(10);
        backgroundPaint.setColor(Color.GRAY);
        backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        backgroundPaint.setStrokeWidth(1);

        controlPaint.setAlpha(255);
        controlPaint.setColor(Color.WHITE);
        controlPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewCenterX = getMeasuredWidth() / 2;
        viewCenterY = getMeasuredHeight() / 2;
        if (handleCenterX == 0 && handleCenterY == 0) {
            handleCenterX = viewCenterX;
            handleCenterY = viewCenterY;
        }

        canvas.drawCircle(viewCenterX, viewCenterY, viewWidth / 2, backgroundPaint);
        canvas.drawCircle(handleCenterX, handleCenterY, handleWidth, controlPaint);

        canvas.save();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float touchX = event.getX();
            float touchY = event.getY();
            angle = (float) Math.atan2(-(touchY - viewCenterY), touchX - viewCenterX);
            if (angle < 0) {
                angle = 2 * Math.PI + angle;
            }
            handleCenterX = touchX;
            handleCenterY = touchY;
            invalidate();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            handleCenterX = viewCenterX;
            handleCenterY = viewCenterY;
            invalidate();
        }
        return true;
    }

    public double getAngle() {
        return 2 * Math.PI - angle;
    }
}
