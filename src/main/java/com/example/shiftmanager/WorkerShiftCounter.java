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

    public void addHours(String szCurrentDay, long start, long stop , MyDay.DayStatus dayStatus)
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
            myDay = new MyDay(szCurrentDay , dayStatus);
            m_arrDays.add(myDay);
        }

        myDay.getM_hours().add(new MyHours(start, stop));
    }

    public void addDayByStatus(String szCurrentDay, MyDay.DayStatus dayStatus)
    {
        MyDay myDay = null;
        for (MyDay day : m_arrDays)
        {
            if (day.getDay().equals(szCurrentDay))
            {
                day.removeAllHours();
                break;
            }
        }

        if(myDay == null)
        {
            myDay = new MyDay(szCurrentDay , dayStatus);
            m_arrDays.add(myDay);
        }
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
                for(int j = 0; j < day.getM_hours().size() ; j++)
                    if(day.getM_hours().get(j) == day.getM_hours().get(index))
                        return day.getM_hours().get(j);
        return null;
    }

    public void removeHourByIndex(int index , String selectedDay)
    {
        MyDay day = getDay(selectedDay);
        if(day.getM_hours().size() == 0)
            day.getM_hours().remove(index);
        m_arrDays.remove(day);
    }

    public void removeDayIndex(String selectedDay)
    {
        MyDay day = getDay(selectedDay);
        m_arrDays.remove(day);
    }

    public long[] getTotalMonthHour(String szDate)
    {
        long[] monthInfo = new long[5];
        long totalHours = 0;
        long workOnRegularDay = 0;
        long sickDays = 0;
        long daysOff = 0;
        long workOnRestOff = 0;
        for(int i = 0 ; i < m_arrDays.size() ; i++)
        {
            if(m_arrDays.get(i).getDay().contains(szDate) == true)
            {
                if(m_arrDays.get(i).getM_dayStatus() == MyDay.DayStatus.RegularDay)
                    workOnRegularDay++;
                if(m_arrDays.get(i).getM_dayStatus() == MyDay.DayStatus.SickDay)
                    sickDays++;
                if(m_arrDays.get(i).getM_dayStatus() == MyDay.DayStatus.DayOff)
                    daysOff++;
                if(m_arrDays.get(i).getM_dayStatus() == MyDay.DayStatus.WorkOnRestDay)
                    workOnRestOff++;

                for (int j = 0; j < m_arrDays.get(i).getM_hours().size(); j++)
                {
                    totalHours += m_arrDays.get(i).getM_hours().get(j).getTotal_time();
                }
            }
        }

        monthInfo[0] = workOnRegularDay;
        monthInfo[1] = totalHours;
        monthInfo[2] = sickDays;
        monthInfo[3] = daysOff;
        monthInfo[4] = workOnRestOff;

        return monthInfo;
    }
}
