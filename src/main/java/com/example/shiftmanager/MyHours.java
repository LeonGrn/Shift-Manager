package com.example.shiftmanager;


public class MyHours
{
    private String m_start_time;
    private String m_end_time;

    public MyHours(String start_time, String end_time)
    {
        m_start_time = start_time;
        m_end_time = end_time;
    }

    public String getStart_time() {
        return m_start_time;
    }

    public void setStart_time(String start_time) {
        m_start_time = start_time;
    }

    public String getEnd_time() {
        return m_end_time;
    }

    public void setEnd_time(String end_time) {
        m_end_time = end_time;
    }
}


