package com.example.shiftmanager.ui.calendar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarView calendarView;
//    MySharePreferences msp;
    private String keyInfo = "key_time";

//    public CalendarFragment(Context cntx)
//    {
//        super();
//        msp = new MySharePreferences(cntx);
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = root.findViewById(R.id.calendarView);


//        ArrayList<WorkerShiftCounter> timeWorked = null;
//        try
//        {
//            timeWorked = new Gson().fromJson(msp.getString(keyInfo,"")
//                    , new TypeToken<List<WorkerShiftCounter>>(){}.getType());
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            timeWorked = new ArrayList<>();
//        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                Toast.makeText(getContext(), ""+dayOfMonth, Toast.LENGTH_LONG).show();

            }
        });

        Log.i("dfsfsdfsdf" , selectedDate + "");


        return root;
    }

}