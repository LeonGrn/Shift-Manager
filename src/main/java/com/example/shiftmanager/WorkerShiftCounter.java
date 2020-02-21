package com.example.shiftmanager;

import java.util.ArrayList;

public class WorkerShiftCounter
{
    private int id;
    private ArrayList<MyDay> m_arrDays;

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

    public void addHours(String szCurrentDay, long start, long stop)
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

    public MyHours getHourByIndex(int index , String selectedDay)
    {
        for (MyDay day : m_arrDays)
            if(day.getDay().equals(selectedDay))
            {
                for(int j = 0; j < day.getM_hours().size() ; j++)
                {
                    if(day.getM_hours().get(j) == day.getM_hours().get(index))
                        return day.getM_hours().get(j);
                }
            }

        return null;
    }

    public void removeHourByIndex(int index , String selectedDay)
    {
        MyDay day = getDay(selectedDay);
        day.getM_hours().remove(index);
        if(day.getM_hours().size() == 0)
            m_arrDays.remove(day);
    }

    public void addHourByIndex(MyHours myhours, String selectedDay)
    {
        MyDay day = getDay(selectedDay);
        day.getM_hours().add(myhours);
    }
}
