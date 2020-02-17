package com.example.shiftmanager.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.MyAdapter;
import com.example.shiftmanager.MyDay;
import com.example.shiftmanager.MyHours;
import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarView calendarView;
    MySharePreferences msp;

    private String keyInfoDay = "key_time_day";
    WorkerShiftCounter myDayInfo = null;

    private ListView listView;
    private MyAdapter adapter;

    public CalendarFragment() {
        super();
    }
    Calendar cal = Calendar.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        msp = new MySharePreferences(getActivity().getApplicationContext());
        readDataFromSP();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = root.findViewById(R.id.calendarView);
        listView = root.findViewById(R.id.listView);
        initList(cal.getTimeInMillis());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                listView.setAdapter(null);

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

    private void initList(Long lDay)
    {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(lDay);
        MyDay myDay = myDayInfo.getDay(timeStamp);

        if (myDay != null)
        {
            adapter = new MyAdapter(getActivity().getApplicationContext(), myDay.getM_hours());
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {

                }
            });
        }
    }

    private void readDataFromSP()
    {
        try
        {
            myDayInfo = new Gson().fromJson(msp.getString(keyInfoDay, ""), new TypeToken<WorkerShiftCounter>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(myDayInfo == null)
            myDayInfo = new WorkerShiftCounter(msp.getInt("personalID", 6666666));
    }

    private void writeDataToSP()
    {
        msp.putString(keyInfoDay,new Gson().toJson(myDayInfo));
    }
}
