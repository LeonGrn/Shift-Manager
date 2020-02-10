package com.example.shiftmanager;

import java.util.ArrayList;
import java.util.Calendar;


public class WorkerShiftCounter
{
    private int id;
    ArrayList<MyDay> currentDay;

    public WorkerShiftCounter(int id , ArrayList<MyDay> currentDay)
    {
        this.id = id;
        this.currentDay = currentDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MyDay> getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(ArrayList<MyDay> currentDay) {
        this.currentDay = currentDay;
    }
}
