package com.example.shiftmanager;

import java.util.ArrayList;
import java.util.Calendar;



public class MyDay
{
    private String m_date;
    private ArrayList<MyHours> m_hours = new ArrayList<>();
    private int m_dayStatus;


    public MyDay(String date )
    {
        m_date = date;
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

    public int getM_dayStatus() {
        return m_dayStatus;
    }

    public void setM_dayStatus(int m_dayStatus) {
        this.m_dayStatus = m_dayStatus;
    }
}