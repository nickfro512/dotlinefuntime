package com.dlft.dotlinefuntime;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Circle {

    private Paint mPaint;
    private float mRadius;
    private float centerX;
    private float centerY;
    private RectF boundedRect;

    public Circle() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    public void setBounds(RectF bRect) {
        boundedRect = bRect;
        boundedRect.sort();
        centerX = boundedRect.centerX();
        centerY = boundedRect.centerY();
        Log.d("rect x", Float.toString(boundedRect.centerX()));
        Log.d("rect y", Float.toString(boundedRect.centerY()));
        mRadius = Math.max(boundedRect.height(), boundedRect.width())/2;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public void setColor(int new_color){
        mPaint.setColor(new_color);
    }

    public Paint getPaint() {
        return mPaint;
    }

    public float getRadius() {
        return  mRadius;
    }

    public float getX() {
        return  centerX;
    }

    public float getY() {
        return  centerY;
    }
}
