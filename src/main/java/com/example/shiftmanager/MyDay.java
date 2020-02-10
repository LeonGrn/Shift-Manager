package com.example.shiftmanager;

import java.util.Calendar;

public class MyDay
{
    Calendar cal;
    long min;

    public MyDay(Calendar cal , long min)
    {
        this.cal = cal;
        this.min = min;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

}