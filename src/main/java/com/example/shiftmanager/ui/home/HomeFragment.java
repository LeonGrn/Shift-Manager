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
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.MyDay;
import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.MySignal;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
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
    private String keyInfoDay = "key_time_day";

    private String saveStartTimeAsString = "";
    private String keySaveStartTime = "key_start_time";


    private WorkerShiftCounter worker;
    ArrayList<MyDay> currentDay = new ArrayList<>();
    Calendar cal = Calendar.getInstance();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagStartStop = true;
        timeShift();
        msp = new MySharePreferences(getActivity().getApplicationContext());
        readDataFromSP();

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        bar = root.findViewById(R.id.bar);
        home_lineartimeclock = root.findViewById(R.id.home_lineartimeclock);
        mChronometer = root.findViewById(R.id.simpleChronometer);
        txt_currentday = root.findViewById(R.id.home_currentdate);
        currenttime = root.findViewById(R.id.home_currentTime);

        getCurrentDate();
        beforeStartCount();
        bar.setBackgroundColor(Color.rgb(150, 2, 31));
        animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim);

        home_lineartimeclock.setOnTouchListener(scanIt);

        return root;
    }

    View.OnTouchListener scanIt = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (flagStartStop == false)
                {
                    bar.setVisibility(View.VISIBLE);
                    bar.startAnimation(animation);
                    afterStartCount();

                    //read from SP
                    saveStartTimeAsString = new Gson().fromJson(msp.getString(keySaveStartTime,getTime()), new TypeToken<String>() {}.getType());

                    //Save day info to SP
                    String tempConvert = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.YEAR);
                    worker.addHours(tempConvert , saveStartTimeAsString , getTime());

                    Log.i("1111111111" , "" + worker);

                    writeDataToSP();
                }
                else if (flagStartStop == true)
                {
                    bar.setVisibility(View.GONE);
                    beforeStartCount();
                    animation.cancel();

                    saveStartTimeAsString = getTime();
                    msp.putString(keySaveStartTime,new Gson().toJson(saveStartTimeAsString));

                    //Parse time
//                    long time = SystemClock.elapsedRealtime() - mChronometer.getBase();
//                    int hours = (int)(time /3600000);
//                    int min = (int)(time - hours*3600000)/60000;
//                    int sec = (int)(time - hours*3600000- min*60000)/1000 ;
//                    Log.i("1111111111" , "" + hours);
//                    Log.i("1111111111" , "" + min);
//                    Log.i("1111111111" , "" + sec);


                    writeDataToSP();
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
        mChronometer.setBase(SystemClock.elapsedRealtime());
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
//                Locale currentLocale = Locale.getDefault();
                currenttime.setBackgroundColor(Color.rgb(11 , 127 , 178));
                currenttime.setTextColor(Color.WHITE);
//                DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLocale);
//                String zoneDate = dateFormat.format(new Date());
                currenttime.setText(getTime());

                timeShift();
            }
        };
        timerHandler.postDelayed(timerRunnable , 100);
    }

    private String getTime()
    {
        Locale currentLocale = Locale.getDefault();
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLocale);
        String zoneDate = dateFormat.format(new Date());

        return zoneDate;
    }


    private void readDataFromSP()
    {
        try
        {
            worker = new Gson().fromJson(msp.getString(keyInfoDay, ""), new TypeToken<WorkerShiftCounter>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(worker == null)
            worker = new WorkerShiftCounter(msp.getInt("personalID" , 6666666));
    }

    private void writeDataToSP()
    {
        msp.putString(keyInfoDay,new Gson().toJson(worker));
    }
}