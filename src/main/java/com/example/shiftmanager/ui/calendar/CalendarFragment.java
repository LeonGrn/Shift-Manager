package com.example.shiftmanager.ui.calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.EditShiftActivity;
import com.example.shiftmanager.MyAdapter;
import com.example.shiftmanager.MyAdapterAddShift;
import com.example.shiftmanager.MyDay;
import com.example.shiftmanager.MyHours;
import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarView calendarView;
    MySharePreferences msp;
    WorkerShiftCounter myDayInfo = null;
    private ListView listView;
    private MyAdapter adapter;
    private MyAdapterAddShift adapter2;
    Calendar cal = Calendar.getInstance();
    private ArrayList<TextView> addShift;
    private String currentDay = null;
    private String lDate = "";


    public CalendarFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.i("22222222222222222" , "Create");
        msp = new MySharePreferences(getActivity().getApplicationContext());
        myDayInfo = msp.readDataFromSP();
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = root.findViewById(R.id.calendarView);
        listView = root.findViewById(R.id.listView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                setAddShiftTxt();


                long startDate = 0;
                try
                {
                    int monthTemp = 1 + month;
                    String dateString = dayOfMonth+ "/" +monthTemp + "/" + year;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = sdf.parse(dateString);

                    startDate = date.getTime();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                initList(startDate);
            }
        });

        return root;
    }

    private void setAddShiftTxt()
    {
        addShift = new ArrayList<>();
        addShift.add(new TextView(getActivity().getApplicationContext()));
        adapter2 = new MyAdapterAddShift(getActivity().getApplicationContext(), addShift);
        listView.setAdapter(adapter2);
    }


    private void initList(Long lDay)
    {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(lDay);
        lDate = timeStamp;
        MyDay myDay = myDayInfo.getDay(timeStamp);
        currentDay = timeStamp;

        if (myDay != null)
        {
            adapter = new MyAdapter(getActivity().getApplicationContext(), myDay.getM_hours());
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Intent intent = new Intent(getActivity(), EditShiftActivity.class);

                    intent.putExtra("DayIndex", listView.getItemAtPosition(i).toString());
                    intent.putExtra("DayDate", lDate);

                    Log.i("22222222222222222" , listView.getItemAtPosition(i).toString());

                    startActivity(intent);
                    (getActivity()).overridePendingTransition(0, 0);
                }
            });
        }
        else
            setAddShiftTxt();
    }





    @Override
    public void onPause()
    {
        Log.i("22222222222222222" , "Pause");

        super.onPause();
    }

    @Override
    public void onStart()
    {
        Log.i("22222222222222222" , "Start");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.i("22222222222222222" , "Resume");
        msp = new MySharePreferences(getActivity().getApplicationContext());
        myDayInfo = msp.readDataFromSP();
        setAddShiftTxt();
        initList(cal.getTimeInMillis());
        super.onResume();
    }

    @Override
    public void onStop()
    {
        Log.i("22222222222222222" , "Stop");

        super.onStop();
    }
}