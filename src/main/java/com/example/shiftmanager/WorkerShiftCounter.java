package com.example.shiftmanager;

import java.util.ArrayList;
import java.util.Calendar;


public class WorkerShiftCounter
{
    private int id;
    ArrayList<MyDay> m_arrDays;

    public WorkerShiftCounter(int id)
    {
        this.id = id;
        this.m_arrDays = new ArrayList<>();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ArrayList<MyDay> getArrDays()
    {
        return m_arrDays;
    }

    public void addHours(String szCurrentDay, String start, String stop)
    {
        MyDay myDay = null;
        for (MyDay day : m_arrDays)
        {
            if (day.getDay().equals(szCurrentDay))
            {
                myDay = day;
                break;
            }
        }

        if(myDay == null)
        {
            myDay = new MyDay(szCurrentDay);
            m_arrDays.add(myDay);
        }

        myDay.getM_hours().add(new MyHours(start, stop));
    }

    public MyDay getDay(String szCurrentDay)
    {
        for (MyDay day : m_arrDays)
            if (day.getDay().equals(szCurrentDay))
                return day;

        return null;
    }
}
