package com.example.shiftmanager.ui.settings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;

public class SettingsFragment extends Fragment {

    private TextView txt_month;
    private Button txt_left , txt_rigth;
    private String[] months = { null , "January" , "February" , "March" , "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"};

    MySharePreferences msp;
    WorkerShiftCounter myDayInfo = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myDayInfo = msp.readDataFromSP();
        msp = new MySharePreferences(getActivity().getApplicationContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        findViews(root);


        return root;
    }

    private void findViews(View root)
    {
        txt_month = root.findViewById(R.id.txt_month);
        txt_left = root.findViewById(R.id.txt_left);
        txt_rigth = root.findViewById(R.id.txt_rigth);
    }
}