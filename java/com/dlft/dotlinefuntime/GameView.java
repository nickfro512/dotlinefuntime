package com.dlft.dotlinefuntime;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.WHITE;

public class GameView extends View {

    private Line mLine;
    private Circle mCircle;

    public static Paint textPaint;
    public static Paint rectPaint;

    ArrayList<CoordSet> coords = new ArrayList<CoordSet>();


    public int drawmode = 1;

    public int base_dot_index = 0;
    public int target_dot_index = 1;

    // Coordinates of points to draw
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    static ArrayList<CoordSet> line_coords_array = new ArrayList<CoordSet>();
    static ArrayList<Dot> dot_coords_array = new ArrayList<Dot>();

    public Dot current_coords;
    public Dot target_coords;

    public float current_x;
    public float current_y;

    public float screen_width;
    public float screen_height;
    public float play_area_width;
    public float play_area_height;

    public RectF play_area;
    public float dot_diameter;
    int dot_diameter_int;
    public float dot_radius;
    public int dot_radius_int;
    public float hit_distance;
    public float dot_edge_spacing;

    public float dot_x_min;
    public float dot_x_max;
    public float dot_y_min;
    public float dot_y_max;

    public boolean currently_down = false;

    public static int score;

    public boolean rhony_mode = false;

    public Bitmap my_bm_source;
    public Bitmap my_bm_target;
    public static Paint BM_paint;

    public GameView(Context context) {

        super(context);

        textPaint = new Paint();
        rectPaint = new Paint();

        score = 0;
        mLine = new Line();
        mLine.setColor(Color.BLACK);
        mCircle = new Circle();
        /// NOTE, RES IS APPROX 600 x 1000


        Point size = new Point();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getSize(size);
        screen_width = (float) size.x;
        screen_height = (float) size.y;

        play_area = new RectF(screen_width * .05f, screen_height * .04f, screen_width * .95f, screen_height * .8f);
        play_area_width = play_area.width();
        play_area_height = play_area.height();

        dot_diameter = screen_height * .03f;
        dot_radius = dot_diameter / 2;
        hit_distance = dot_diameter * 3;
        dot_diameter_int = (int) dot_diameter;
        dot_edge_spacing = dot_diameter * 1.1f;

        dot_x_min = play_area.left + dot_edge_spacing;
        dot_x_max = play_area.right - dot_edge_spacing;

        dot_y_min = play_area.top + dot_edge_spacing;
        dot_y_max = play_area.bottom - dot_edge_spacing;

        Log.d("width: ", Double.toString(screen_width));
        Log.d("height: ", Double.toString(screen_height));

        /*
        dot_coords_array.add(new Dot(play_area.left + dot_edge_spacing, play_area.top + dot_edge_spacing));
        dot_coords_array.add(new Dot(play_area.right - dot_edge_spacing, play_area.bottom - dot_edge_spacing));
        */
        dot_coords_array.add(new Dot(play_area.left * 3, play_area.top * 3));
        dot_coords_array.add(new Dot(play_area.right * .7f, play_area.bottom * .7f));

        x1 = dot_coords_array.get(0).x + hit_distance * .55f;
        y1 = dot_coords_array.get(0).y + hit_distance;
        x2 = dot_coords_array.get(1).x - hit_distance * .5f;
        y2 = dot_coords_array.get(1).y - hit_distance;

        line_coords_array.add(new CoordSet(x2, y2, x2 * 1.02f, y2 - hit_distance));
        line_coords_array.add(new CoordSet(x2, y2, x2 - hit_distance, y2 * .95f));

        //my_bm_source = BitmapFactory.decodeResource(getResources(), R.drawable.target_img);
        my_bm_source = BitmapFactory.decodeResource(getResources(), R.drawable.dlft_source);
        my_bm_target = BitmapFactory.decodeResource(getResources(), R.drawable.dlft_target);
        BM_paint = new Paint();


        //BM_paint.setColor(0);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB(255, 255, 255);
        rectPaint.setColor(WHITE);
        canvas.drawRect(play_area, rectPaint);


        mLine.setColor(Color.LTGRAY);
        for (int i=0; i < line_coords_array.size(); i++)
        {
            CoordSet coords = line_coords_array.get(i);
            canvas.drawLine(coords.x1, coords.y1, coords.x2, coords.y2, mLine.getPaint());
        }

        if (currently_down)
        {
            mLine.setColor(Color.RED);
        } // otherwise will be gray
        canvas.drawLine(x1, y1, x2, y2, mLine.getPaint());

        for (int i = 0; i < dot_coords_array.size(); i++)
        {
            current_coords = dot_coords_array.get(i);
            target_coords = dot_coords_array.get(target_dot_index);

            if (i == base_dot_index && (Math.abs(target_coords.getDistanceFromPoint(x2, y2)) < hit_distance
                && Math.abs(current_coords.getDistanceFromPoint(x1, y1)) < hit_distance))
            {
                mCircle.setColor(Color.rgb(0,150,0));
            }
            else if (i == base_dot_index)
            {
                if (!rhony_mode)
                    mCircle.setColor(Color.BLUE);

            }
            else
            {
                mCircle.setColor(Color.RED);
            }

            current_x = current_coords.x;
            current_y = current_coords.y;

            if (!rhony_mode)
                canvas.drawCircle(current_x, current_y, dot_diameter, mCircle.getPaint());
            else if (i != base_dot_index) // RHONY_MODE
            {
                canvas.drawBitmap(my_bm_target, current_x - (my_bm_target.getWidth() / 2), current_y - (my_bm_target.getWidth() / 2), BM_paint);
                //canvas.drawCircle(current_x, current_y, dot_diameter, mCircle.getPaint());
            }
            else {
                canvas.drawBitmap(my_bm_source, current_x - (my_bm_source.getWidth() / 2), current_y - (my_bm_source.getWidth() / 2), BM_paint);
                //canvas.drawCircle(current_x, current_y, dot_diameter, mCircle.getPaint());
            }

            textPaint.setColor(Color.rgb(0,100,50));
            textPaint.setStrokeWidth(10);
            textPaint.setTextSize(screen_height * .07f);
            canvas.drawText(MyActivity.getTimeDisplay(), screen_width * .1f, screen_height * .88f, textPaint);
            canvas.drawText(Integer.toString(score), screen_width * .8f, screen_height * .88f, textPaint);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //TASK 1:  IDENTIFY THE TOUCH ACTION BEING PERFORMED
        int touchAction = event.getActionMasked();

        //TASK 2:  RESPOND TO TWO POSSIBLE TOUCH EVENTS
        switch (touchAction) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                x2 = x1;
                y2 = y1;
                currently_down = true;
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                currently_down = false;

                Dot base_coords = dot_coords_array.get(base_dot_index);
                Dot target_coords = dot_coords_array.get(target_dot_index);
                if (Math.abs(target_coords.getDistanceFromPoint(x2, y2)) < hit_distance
                        && Math.abs(base_coords.getDistanceFromPoint(x1, y1)) < hit_distance) // HIT
                {
                    Random r = new Random();

                    int new_x;
                    int new_y;

                    int reps = 0;

                    float measure_distance;

                    do {
                        //new_x = r.nextInt((int) play_area_width - ((int) dot_edge_spacing)) + ((int) play_area.left + (int) dot_edge_spacing);
                        //new_y = r.nextInt((int) play_area_height - ((int) dot_edge_spacing)) + ((int) play_area.top + (int) dot_edge_spacing);

                        new_x = r.nextInt((int) dot_x_max - (int) dot_x_min) + (int) dot_x_min;
                        new_y = r.nextInt((int) dot_y_max - (int) dot_y_min) + (int) dot_y_min;

                        Dot measureDot = new Dot(target_coords.x, target_coords.y);
                        measure_distance = measureDot.getDistanceFromPoint(new_x, new_y);
                        reps++;
                    }
                    while ( measure_distance < hit_distance * 2 && reps < 100);

                    dot_coords_array.add(new Dot((float) new_x, (float) new_y));
                    dot_coords_array.remove(0);

                    MyActivity.playSound();

                    score++;
                }

                line_coords_array.add(new CoordSet(x1,y1,x2,y2));
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                break;
        }

        //TASK 3: INVALIDATE THE VIEW
        invalidate();

        //TASK 4: RETURNS A TRUE AFTER HANDLING THE TOUCH ACTION EVENT
        return true;
    }


}
