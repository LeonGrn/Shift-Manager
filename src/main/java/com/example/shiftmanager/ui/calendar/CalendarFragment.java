package com.example.shiftmanager.ui.calendar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.MyDay;
import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarView calendarView;
    MySharePreferences msp;
    private LinearLayout myLinearTextViewDay;

    private String keyInfoDay = "key_time_day";
    WorkerShiftCounter myDayInfo = null;

    public CalendarFragment() {
        super();
    }

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
        myLinearTextViewDay = root.findViewById(R.id.myLiniarTextViewDay);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                //String clickedDate = dayOfMonth + "/" + month + 1 + "/" + year;
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
        clearAllNotes();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(lDay);
        MyDay myDay = myDayInfo.getDay(timeStamp);

        if (myDay != null) {
            for (int i = 0; i < myDay.getM_hours().size(); i++) {

                TextView textView = new TextView(getActivity().getApplicationContext());
                String convertEndTime = new SimpleDateFormat("HH:mm:ss").format(myDay.getM_hours().get(i).getEnd_time());
                String convertStartTime = new SimpleDateFormat("HH:mm:ss").format(myDay.getM_hours().get(i).getStart_time());

                textView.setText("Worked time:\n" + "   " + convertStartTime + " - " + convertEndTime);
                textView.setTextSize(20);
                textView.setTextColor(Color.parseColor("#0045AD"));
                textView.setTypeface(null, Typeface.BOLD);
                textView.setPadding(20 , 20 , 20 , 20);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                myLinearTextViewDay.addView(textView);


            }
        }
    }


    private void clearAllNotes()
    {
        myLinearTextViewDay.removeAllViews();
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
