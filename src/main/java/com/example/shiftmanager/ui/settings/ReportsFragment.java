package com.example.shiftmanager.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportsFragment extends Fragment {

    private TextView txt_month , txt_year , txt_worked_days_number , txt_total_hours_number , txt_sick_days_number , txt_days_off_number , txt_work_on_rest_day_number;
    private Button txt_left , txt_rigth;
    private String[] months = {"January" , "February" , "March" , "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"};
    private int monthIndex = -1;
    private int currentYear = 2020;

    MySharePreferences msp;
    WorkerShiftCounter worker = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        msp = new MySharePreferences(getActivity().getApplicationContext());
        worker = msp.readDataFromSP();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reports, container, false);
        findViews(root);
        monthIndex = setCurrentMonth();
        txt_year.setText(currentYear + "");
        updateMonth(String.format("%02d/%d", monthIndex + 1 ,currentYear));

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

                    updateMonth(String.format("%02d/%d", monthIndex+1,currentYear));
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

                    updateMonth(String.format("%02d/%d", monthIndex+1,currentYear));
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
        int temp2 = calendar.get(Calendar.YEAR);
        txt_month.setText( months[temp] + "" );
        txt_year.setText(temp2 + "");
        return temp;
    }

    private void updateMonth(String month)
    {
        long[] monthInfo = worker.getTotalMonthHour(month);
        txt_worked_days_number.setText(monthInfo[0] + "");
        if(monthInfo[1] != 0)
        {
            long timeDifference = monthInfo[1] / 1000;
            int h = (int) (timeDifference / (3600));
            int m = (int) ((timeDifference - (h * 3600)) / 60);

            txt_total_hours_number.setText(String.format("%02d:%02d", h, m) + "");
        }
        else
            txt_total_hours_number.setText("00:00");

        txt_sick_days_number.setText(monthInfo[2] + "");
        txt_days_off_number.setText(monthInfo[3] + "");
        txt_work_on_rest_day_number.setText(monthInfo[4] + "");
    }

    private void findViews(View root)
    {
        txt_month = root.findViewById(R.id.txt_month);
        txt_left = root.findViewById(R.id.txt_left);
        txt_rigth = root.findViewById(R.id.txt_rigth);
        txt_year = root.findViewById(R.id.txt_year);
        txt_worked_days_number = root.findViewById(R.id.txt_worked_days_number);
        txt_total_hours_number = root.findViewById(R.id.txt_total_hours_number);
        txt_sick_days_number = root.findViewById(R.id.txt_sick_days_number);
        txt_days_off_number = root.findViewById(R.id.txt_days_off_number);
        txt_work_on_rest_day_number = root.findViewById(R.id.txt_work_on_rest_day_number);
    }
}