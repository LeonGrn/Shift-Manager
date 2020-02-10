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
    private TextView currentday;
    private TextView currenttime;
    MySharePreferences msp;
    private LinearLayout home_lineartimeclock;
    private boolean flagStartStop = true;
    private final Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private Chronometer mChronometer;
    private View bar;
    private Animation animation;
    private String keyInfo = "key_time";

    private int totalDayMin = 0;
    private int id = 111;
    private MyDay countThisDay;
    private WorkerShiftCounter worker;
    ArrayList<MyDay> currentDay = new ArrayList<>();
    Calendar cal = Calendar.getInstance();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msp = new MySharePreferences(getContext());
        worker = new WorkerShiftCounter(id , currentDay );
        flagStartStop = true;
        timeShift();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        currentday = root.findViewById(R.id.home_currentdate);
        currenttime = root.findViewById(R.id.home_currentTime);
        bar = root.findViewById(R.id.bar);
        bar.setBackgroundColor(Color.rgb(150, 2, 31));
        animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim);
        home_lineartimeclock = root.findViewById(R.id.home_lineartimeclock);
        mChronometer = root.findViewById(R.id.simpleChronometer);

        //Log.i("2312312312",keyInfo);
        if(msp.getString(keyInfo,"") == "")
        {
            beforeStartCount();
            getCurrentDate();
        }
        else
        {
            Log.i("2312312312","llllllllllllll");
        }

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
                }
                else if (flagStartStop == true)
                {
                    bar.setVisibility(View.GONE);
                    beforeStartCount();
                    animation.cancel();

                    //Parse time
                    long time = SystemClock.elapsedRealtime() - mChronometer.getBase();
                    int hours = (int)(time /3600000);
                    int min = (int)(time - hours*3600000)/60000;
                    int sec = (int)(time - hours*3600000- min*60000)/1000 ;
                    Log.i("1111111111" , "" + hours);
                    Log.i("1111111111" , "" + min);
                    Log.i("1111111111" , "" + sec);
                    //Save day info to SP
                    countThisDay = new MyDay(cal , time);

                    saveInformationToSP(worker);
                }
            }
            return false;
        }
    };


    public void getCurrentDate()
    {
        int tempMonth = cal.get(Calendar.MONTH) + 1;
        currentday.setText(cal.get(Calendar.DAY_OF_MONTH) + "/" + tempMonth + "/" + cal.get(Calendar.YEAR));
        currentday.setBackgroundColor(Color.rgb(11 , 127 , 178));
        currentday.setTextColor(Color.WHITE);
    }

    private void saveInformationToSP(WorkerShiftCounter workerInfo)
    {
        ArrayList<WorkerShiftCounter> list = null;

        Gson gson = new Gson();
        try
        {
            list = gson.fromJson(msp.getString(keyInfo,""),new TypeToken<List<WorkerShiftCounter>>(){}.getType());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            list = new ArrayList<>();
        }

        list.add(workerInfo);
        msp.putString(keyInfo,gson.toJson(list));
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
}