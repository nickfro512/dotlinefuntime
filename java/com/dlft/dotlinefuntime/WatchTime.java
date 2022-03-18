package com.dlft.dotlinefuntime;

public class WatchTime {

    // TIME ELEMENTS
    private long mStartTime;
    private long mTimeUpdate;
    private long mStoredTime;
    private long mEndTime;

    public WatchTime() {
        mStartTime = 0L;
        mTimeUpdate = 0L;
        mStoredTime = 0L;
        mEndTime = 0L;
    }

    public void resetWatchTime() {
        mStartTime = 0L;
        mStoredTime = 0L;
        mTimeUpdate = 0L;
        mEndTime = 0L;
    }

    public void setStartTime(long startTime){
        mStartTime = startTime;
    }
    public long getStartTime(){
        return mStartTime;
    }
    public void setTimeUpdate(long timeUpdate){
        mTimeUpdate = timeUpdate;
    }
    public long getTimeUpdate(){
        return mTimeUpdate;
    }
    public void addStoredTime(long timeInMilliseconds){
        mStoredTime += timeInMilliseconds;
    }
    public long getStoredTime(){
        return mStoredTime;
    }
    public void setEndTime(long endTime){
        mEndTime = endTime;
    }
    public long getEndTime(){
        return mEndTime;
    }
}
