package com.example.shiftmanager;

import java.util.ArrayList;
import java.util.Calendar;



public class MyDay
{
    public class myHours
    {
        long start_time;
        long end_time;
    }

    long m_date;
    ArrayList<myHours> m_hours = new ArrayList<>();

    public MyDay(long date)
    {
        m_date = date;
    }

    public void addHours(myHours hour)
    {
        m_hours.add(hour);
    }

    public long getCal() {
        return m_date;
    }

    public void setCal(long date) {
        this.m_date = date;
    }

}