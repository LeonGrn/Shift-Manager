package com.example.shiftmanager.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shiftmanager.Keys;
import com.example.shiftmanager.MyDay;
import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.MySignal;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private TextView txt_currentday;
    private TextView currenttime;
    MySharePreferences msp;
    private LinearLayout home_lineartimeclock;
    private boolean flagStartStop = true;
    private final Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private Chronometer mChronometer;
    private View bar;
    private Animation animation;

    private long saveExistingCount = 0;
    private long saveStartTimeAsLong = 0;

    private WorkerShiftCounter worker;
    Calendar cal = Calendar.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagStartStop = true;
        timeShift();
        msp = new MySharePreferences(getActivity().getApplicationContext());
        worker = msp.readDataFromSP();
        saveStartTimeAsLong = msp.getLong(Keys.keySaveStartTime,0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(root);

        getCurrentDate();
        beforeStartCount();
        bar.setBackgroundColor(Color.rgb(150, 2, 31));
        animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim);

        saveExistingCount = msp.getLong(Keys.keyCheckExistingCount,0);
        if(saveExistingCount > 0)
        {
            mChronometer.setBase(saveExistingCount);
            mChronometer.start();
            flagStartStop = true;
        }


        home_lineartimeclock.setOnTouchListener(scanIt);

        return root;
    }

    View.OnTouchListener scanIt = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //First click
                if (flagStartStop == false)
                {
                    bar.setVisibility(View.VISIBLE);
                    bar.startAnimation(animation);
                    afterStartCount();


                    saveExistingCount = SystemClock.elapsedRealtime();
                    saveStartTimeAsLong = (new Date()).getTime();
                    msp.putLong(Keys.keyCheckExistingCount,saveExistingCount);
                    msp.putLong(Keys.keySaveStartTime,saveStartTimeAsLong);
                }
                else if (flagStartStop == true) //Second Click
                {
                    bar.setVisibility(View.GONE);
                    beforeStartCount();
                    animation.cancel();

                    //saveStartTimeAsLong = msp.getLong(keySaveStartTime,0);

                    String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format((new Date()).getTime());

                    Log.i("333333333333333" , " " + timeStamp);

                    Log.i("444444444444444" , " " + (new Date()).getTime());
                    worker.addHours(timeStamp , saveStartTimeAsLong , (new Date()).getTime() , MyDay.DayStatus.RegularDay);
                    msp.writeDataToSP(worker);

                    saveStartTimeAsLong = 0;

                    msp.putLong(Keys.keySaveStartTime,saveStartTimeAsLong);
                    saveExistingCount = 0;
                    msp.putLong(Keys.keyCheckExistingCount , saveExistingCount);
                }
            }
            return false;
        }
    };


    public void getCurrentDate()
    {
        int tempMonth = cal.get(Calendar.MONTH) + 1;
        txt_currentday.setText(cal.get(Calendar.DAY_OF_MONTH) + "/" + tempMonth + "/" + cal.get(Calendar.YEAR));
        txt_currentday.setBackgroundColor(Color.rgb(11 , 127 , 178));
        txt_currentday.setTextColor(Color.WHITE);
    }

    private void beforeStartCount()
    {
        mChronometer.stop();
        flagStartStop = false;
        mChronometer.setTextSize(25);
        mChronometer.setTypeface(null, Typeface.BOLD_ITALIC);
        mChronometer.setText("Welcome, Leon");
    }


    private void afterStartCount()
    {
        long saveTempTime = SystemClock.elapsedRealtime();
        mChronometer.setBase(saveTempTime);
        mChronometer.start();
        MySignal.vibrate(getContext() , 2000);
        flagStartStop = true;
    }

    //update Home fragment timeZone and shift count
    private void timeShift()//timer
    {
        timerRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                //time zone
                Locale currentLocale = Locale.getDefault();
                currenttime.setBackgroundColor(Color.rgb(11 , 127 , 178));
                currenttime.setTextColor(Color.WHITE);
                DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLocale);
                String zoneDate = dateFormat.format(new Date());
                currenttime.setText(zoneDate);

                timeShift();
            }
        };
        timerHandler.postDelayed(timerRunnable , 100);
    }

    private void findViews(View root)
    {
        bar = root.findViewById(R.id.bar);
        home_lineartimeclock = root.findViewById(R.id.home_lineartimeclock);
        mChronometer = root.findViewById(R.id.simpleChronometer);
        txt_currentday = root.findViewById(R.id.home_currentdate);
        currenttime = root.findViewById(R.id.home_currentTime);
    }
}