package com.example.shiftmanager;

import com.example.shiftmanager.ui.calendar.CalendarFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MyDay
{
    public static enum DayStatus
    {
        RegularDay,
        WorkOnRestDay ,
        DayOff,
        SickDay
    }

    private String m_date ;
    private ArrayList<MyHours> m_hours = new ArrayList<>();
    private DayStatus m_dayStatus = DayStatus.RegularDay;

    public MyDay() {
    }

    public MyDay(String date , DayStatus dayStatus)
    {
        m_date = date;
        m_dayStatus = dayStatus;
    }

    public void addHours(MyHours hour)
    {
        m_hours.add(hour);
    }

    public String getDay() {
        return m_date;
    }

    public void setDay(String date) {
        this.m_date = date;
    }

    public ArrayList<MyHours> getM_hours()
    {
        return m_hours;
    }

    public void setM_hours(ArrayList<MyHours> m_hours) {
        this.m_hours = m_hours;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public DayStatus getM_dayStatus() {
        return m_dayStatus;
    }

    public void setM_dayStatus(DayStatus m_dayStatus) {
        this.m_dayStatus = m_dayStatus;
    }

    public void removeAllHours()
    {
        for(int i = 0 ; i < m_hours.size() ; i++)
        {
            m_hours.remove(i);
        }
    }
}