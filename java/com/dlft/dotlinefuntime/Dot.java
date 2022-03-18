package com.dlft.dotlinefuntime;

import android.util.Log;

public class Dot {

    public float x;
    public float y;

    private int status;

    public Dot(float set_x, float set_y)
    {
        x = set_x;
        y = set_y;
        status = 0;
    };

    public void set(float set_x, float set_y)
    {
        x = set_x;
        y = set_y;
    }

    public float get_x() {return x;}
    public float get_y() {return y;}

    public void debug_dump()
    {
        Log.d("x", Float.toString(x));
        Log.d("y", Float.toString(y));
    }

    public float getDistanceFromPoint(float x2, float y2)
    {
        return (float) Math.sqrt(Math.pow(x2 - this.x, 2) + Math.pow(y2 - this.y, 2));
    }
}
