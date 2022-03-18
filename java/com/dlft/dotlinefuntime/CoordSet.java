package com.dlft.dotlinefuntime;

import android.util.Log;

public class CoordSet {

    public float x1;
    public float y1;
    public float x2;
    public float y2;

    public CoordSet(float set_x1, float set_y1, float set_x2, float set_y2) {
        x1 = set_x1;
        y1 = set_y1;
        x2 = set_x2;
        y2 = set_y2;
    };

    public void set(float set_x1, float set_y1, float set_x2, float set_y2)
    {
        this.x1 = set_x1;
        this.y1 = set_y1;
        this.x2 = set_x2;
        this.y2 = set_y2;
    }

    public float get_x1() {return x1;}
    public float get_y1() {return y1;}
    public float get_x2() {return x2;}
    public float get_y2() {return y2;}

    public void debug_dump(){
        Log.d("x1", Float.toString(x1));
        Log.d("y1", Float.toString(y1));
        Log.d("x2", Float.toString(x2));
        Log.d("y2", Float.toString(x2));

    }
}
