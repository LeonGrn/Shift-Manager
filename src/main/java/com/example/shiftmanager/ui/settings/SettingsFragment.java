package com.example.shiftmanager.ui.settings;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsFragment extends Fragment {

    private TextView txt_month , txt_year;
    private Button txt_left , txt_rigth;
    private String[] months = {"January" , "February" , "March" , "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"};
    private int monthIndex = -1;
    private int currentYear = 2020;

    MySharePreferences msp;
    WorkerShiftCounter myDayInfo = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        msp = new MySharePreferences(getActivity().getApplicationContext());
        myDayInfo = msp.readDataFromSP();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        findViews(root);
        monthIndex = setCurrentMonth();
        txt_year.setText(currentYear + "");



        txt_left.setOnClickListener(move);
        txt_rigth.setOnClickListener(move);

        return root;
    }

    View.OnClickListener move = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String choosenMode = v.getResources().getResourceEntryName(v.getId());
            switch(choosenMode)
            {
                case "txt_left":
                    if(monthIndex == 0)
                    {
                        currentYear--;
                        txt_year.setText("" + currentYear);
                        monthIndex = 12;
                    }

                    if(monthIndex > 0)
                    {
                        setCurrentMonthTxt(--monthIndex);
                    }
                    break;

                case "txt_rigth":
                    if(monthIndex == 11)
                    {
                        currentYear++;
                        txt_year.setText("" + currentYear);
                        monthIndex = -1;
                    }


                    if(monthIndex < 11)
                    {
                        setCurrentMonthTxt(++monthIndex);
                    }
                    break;
            }
        }
    };

    private void setCurrentMonthTxt(int index)
    {
        txt_month.setText( months[index] + "" );
    }
    private int setCurrentMonth()
    {
        Calendar calendar = Calendar.getInstance();
        int temp = calendar.get(Calendar.MONTH);
        txt_month.setText( months[temp] + "" );
        return temp;
    }

    private void findViews(View root)
    {
        txt_month = root.findViewById(R.id.txt_month);
        txt_left = root.findViewById(R.id.txt_left);
        txt_rigth = root.findViewById(R.id.txt_rigth);
        txt_year = root.findViewById(R.id.txt_year);
    }
}