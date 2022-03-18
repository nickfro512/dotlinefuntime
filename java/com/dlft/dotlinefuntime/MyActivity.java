package com.dlft.dotlinefuntime;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import static com.dlft.dotlinefuntime.R.layout.title_layout;

public class MyActivity extends Activity {

    public GameView myGameView;
    public TitleView myTitleView;

    public static String timeDisplay;

    // TIME ELEMENTS
    public WatchTime watchTime;
    public long timeInMilliseconds = 0L;

    public int displayMinutes;
    public int displaySeconds;

    public Handler mHandler;

    public boolean running = false;

    public int last_score;
    public int high_score;
    public boolean game_in_progress;

    public static MediaPlayer mMediaPlayer;

    public TextView scoreDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaPlayer = MediaPlayer.create(this, R.raw.click);

        if (!game_in_progress)
        {
            myTitleView = new TitleView(this);
            setContentView(title_layout);   // THIS IS VERY WEIRD AND IMPORTANT!!!!!!!!!!!!!!!!
        }

        else
        {
            myGameView = new GameView(this);
            game_in_progress = true;
            setContentView(myGameView);
        }

        watchTime = new WatchTime();

        mHandler = new Handler();

        int newMinutes = 0;
        int newSeconds = 60;
        watchTime.setEndTime((newSeconds * 1000) + (newMinutes * 1000 * 60));

        high_score = 0;
        //startTimer();
    }

    public static void playSound()
    {
        mMediaPlayer.start();
    }

    public void beginGame(View view)   /// SWTICH FOR NEW GAME!!!
    {
        startTimer();
        myGameView = new GameView(this);
        game_in_progress = true;
        setContentView(myGameView);
    }

    public void showTitleScreen()
    {
        setContentView(title_layout);   // THIS IS VERY WEIRD AND IMPORTANT!!!!!!!!!!!!!!!!
        scoreDisplay = (TextView) findViewById(R.id.textViewscore);
        scoreDisplay.setText("LAST FUNTIME: " + Integer.toString(last_score)
                                + "\nHIGH: " + Integer.toString(high_score));
    }

    public void resetGame()
    {
        game_in_progress = false;
        stopTimer();
        resetTimer();
        last_score = GameView.score;
        if (last_score > high_score)
            high_score = last_score;
        GameView.score = 0;
        GameView.line_coords_array.clear();
        GameView.dot_coords_array.clear();
        showTitleScreen();
    }

    public void startTimer() {

        if (watchTime.getEndTime() == 0)    // do nothing if timer isn't set
        {
            return;
        }

        running = true;

        // TASK 2: SET THE START TIME AND CALL THE CUSTOM HANDLER
        watchTime.setStartTime(SystemClock.uptimeMillis());
        mHandler.postDelayed(updateTimerRunnable, 20);



    }

    private Runnable updateTimerRunnable = new Runnable() {
        public void run() {

            // TASK 1: COMPUTE THE TIME DIFFERENCE
            timeInMilliseconds = SystemClock.uptimeMillis() - watchTime.getStartTime();
            watchTime.setTimeUpdate(watchTime.getStoredTime() + timeInMilliseconds);
            int time = (int) (watchTime.getTimeUpdate() / 1000);
            // TASK 2: COMPUTE MINUTES, SECONDS, AND MILLISECONDS

            int endTime = (int) (watchTime.getEndTime() / 1000);
            int timeDiff = endTime - time;

            displayMinutes = timeDiff / 60;
            displaySeconds = timeDiff % 60;

            // TASK 3: DISPLAY THE TIME IN THE TEXTVIEW


            // TASK 4: SPECIFY NO TIME LAPSE BETWEEN POSTING
            mHandler.postDelayed(this, 10);
            //Log.d("time", Integer.toString(displaySeconds));
            //if (1==0) /// DEBUG DEBUG DEBUG
            if (timeDiff <= 0)  // end of timer
            {
                mHandler.removeCallbacks(updateTimerRunnable);

                resetGame();
            }

            timeDisplay = (String.format("%02d", displayMinutes) + ":"
                    + String.format("%02d", displaySeconds));

            //GameView.drawFrame();

        }
    };

    public static String getTimeDisplay() {return timeDisplay;}

    public void stopTimer() {
        running = false;
        Log.d("dfsfsd", "timer stop");

        // TASK 1: DISABLE THE START BUTTON
        //         AND ENABLE THE STOP BUTTON

        // TASK 2: UPDATE THE TIME SWAP VALUE AND CALL THE HANDLER
        watchTime.addStoredTime(timeInMilliseconds);
        mHandler.removeCallbacks(updateTimerRunnable);

    }

    public void resetTimer() {
        stopTimer();
        watchTime = new WatchTime();
        watchTime.setStartTime(SystemClock.uptimeMillis());
        int newMinutes = 1;
        int newSeconds = 0;
        watchTime.setEndTime((newSeconds * 1000) + (newMinutes * 1000 * 60));
        mHandler.postDelayed(updateTimerRunnable, 20);
    }

    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onResume(){
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause(){
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected void onRestart(){
        super.onRestart();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            toggleActionBar();
        }
        return true;
    }

    private void toggleActionBar() {
        ActionBar actionBar = getActionBar();

        if(actionBar != null) {
            if(actionBar.isShowing()) {
                actionBar.hide();
            }
            else {
                actionBar.show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.menuitem_title:
                resetGame();
                break;
            case R.id.menuitem_toggle_timer:
                if (running)
                {
                    stopTimer();
                }
                else
                {
                    startTimer();
                }
                break;
            case R.id.menuitem_quit:
                System.exit(1);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
