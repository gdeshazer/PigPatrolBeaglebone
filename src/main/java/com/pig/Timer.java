package com.pig;

/**
 * Created by grantdeshazer on 3/15/16.
 *
 *
 */

public class Timer {

    public Timer(){
        _t = 0;
        startTime = System.currentTimeMillis();
    }

    public void setStartTime(){
        startTime = System.currentTimeMillis();
    }

    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public long getDeltaTime(){
        _t = System.currentTimeMillis() - startTime;
        return _t;
    }

    private long startTime;
    private long _t;

}
