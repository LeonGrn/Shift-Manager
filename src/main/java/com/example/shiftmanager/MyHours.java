package com.example.shiftmanager;


public class MyHours
{
    private long m_start_time;
    private long m_end_time;

    public MyHours()
    {
    }

    public MyHours(long start_time, long end_time)
    {
        m_start_time = start_time;
        m_end_time = end_time;
    }

    public long getStart_time()
    {
        return m_start_time;
    }

    public void setStart_time(long start_time) {
        m_start_time = start_time;
    }

    public long getEnd_time() {
        return m_end_time;
    }

    public void setEnd_time(long end_time) {
        m_end_time = end_time;
    }

    public long getTotal_time() {
        return m_end_time - m_start_time;
    }
}


