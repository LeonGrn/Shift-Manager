package com.example.shiftmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditShiftActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button btn_save;
    private EditText txt_from , txt_to;
    private TextView txt_remove_shift , txt_shiftInfo , txt_view_time , txt_view_to , txt_view_from;
    private WorkerShiftCounter worker;
    private Spinner spinner;
    private int elementIndex = -1;
    private String elementDate = "";
    private MyHours myhours = null;
    private long tempStartTime = 0;
    private long tempStopTime = 0;
    MySharePreferences msp;
    private MyDay.DayStatus choosenStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift);
        findViews();
        msp = new MySharePreferences(getApplicationContext());
        worker = msp.readDataFromSP();

        Intent intent = getIntent();
        elementIndex = Integer.valueOf(intent.getStringExtra("DayIndex"));
        elementDate = intent.getStringExtra("DayDate");
        choosenStatus = (MyDay.DayStatus) intent.getSerializableExtra("DayStatus");
        myhours = worker.getHourByIndex(elementIndex, elementDate);

        if (choosenStatus == MyDay.DayStatus.RegularDay || choosenStatus == MyDay.DayStatus.WorkOnRestDay)
        {
            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(myhours.getStart_time());
            String timeStamp2 = new SimpleDateFormat("HH:mm:ss").format(myhours.getEnd_time());

            txt_shiftInfo.setText("Choose work day");
            txt_shiftInfo.setTextSize(20);
            txt_from.setText(timeStamp);
            txt_to.setText(timeStamp2);

            txt_view_time.setText(timeDifference(myhours.getEnd_time() - myhours.getStart_time()));
            btn_save.setEnabled(true);

        }
        else if(choosenStatus == MyDay.DayStatus.SickDay || choosenStatus == MyDay.DayStatus.DayOff)
        {
            txt_shiftInfo.setText("Add work day");
            txt_shiftInfo.setTextSize(20);
            txt_to.setEnabled(false);
            txt_from.setEnabled(false);
            txt_view_to.setText("");
            txt_view_from.setText("");
            txt_view_time.setText(elementDate);
            txt_remove_shift.setEnabled(true);
            btn_save.setEnabled(true);
        }
        else
        {
            txt_shiftInfo.setText("Add work day");
            txt_shiftInfo.setTextSize(20);
            txt_from.setText("Add start time");
            txt_to.setText("Add end time");
            btn_save.setEnabled(false);
            txt_remove_shift.setEnabled(false);
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_shift, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btn_save.setOnClickListener(saveShift);
        txt_remove_shift.setOnClickListener(removeShift);
        txt_from.setOnClickListener(setTimeFrom);
        txt_to.setOnClickListener(setTimeTo);

        spinner.setOnItemSelectedListener(this);
    }


    public String timeDifference(long timeDifference1) {
        long timeDifference = timeDifference1 / 1000;
        int h = (int) (timeDifference / (3600));
        int m = (int) ((timeDifference - (h * 3600)) / 60);
        int s = (int) (timeDifference - (h * 3600) - m * 60);

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public void onItemSelected(AdapterView<?> parent, View view,   int pos, long id)
    {
        String item = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


            switch (item) {
                case "Work on a regular day":
                    Log.i("22222222222222222", "cool");
                    if(myhours != null)
                    {
                        txt_from.setText("" + new SimpleDateFormat("HH:mm").format(myhours.getStart_time()));
                        txt_to.setText("" + new SimpleDateFormat("HH:mm").format(myhours.getEnd_time()));
                        txt_view_time.setText(timeDifference(myhours.getEnd_time() - myhours.getStart_time()));
                    }
                    txt_from.setEnabled(true);
                    txt_to.setEnabled(true);
                    txt_view_from.setText("From");
                    txt_view_to.setText("To");
                    choosenStatus = MyDay.DayStatus.RegularDay;
                    break;

                case "Work on a rest day":
                    Log.i("22222222222222222", "cool2");
                    if(myhours != null)
                    {
                        txt_from.setText("" + new SimpleDateFormat("HH:mm").format(myhours.getStart_time()));
                        txt_to.setText("" + new SimpleDateFormat("HH:mm").format(myhours.getEnd_time()));
                        txt_view_time.setText(timeDifference(myhours.getEnd_time() - myhours.getStart_time()));
                    }
                    txt_from.setEnabled(true);
                    txt_to.setEnabled(true);
                    txt_view_from.setText("From");
                    txt_view_to.setText("To");
                    choosenStatus = MyDay.DayStatus.WorkOnRestDay;
                    break;

                case "Day off(paid)":
                    Log.i("22222222222222222", "cool3"); if(myhours != null)
                    if(myhours != null)
                    {
                        txt_from.setText(new SimpleDateFormat("dd/MM/yyyy").format(myhours.getStart_time()));
                        txt_remove_shift.setEnabled(true);
                    }
                    txt_from.setText("From");
                    txt_to.setText("To");
                    txt_to.setEnabled(false);
                    txt_from.setEnabled(false);
                    txt_view_to.setText("");
                    txt_view_from.setText("");
                    txt_view_time.setText(elementDate);
                    txt_remove_shift.setEnabled(false);
                    btn_save.setEnabled(true);
                    choosenStatus = MyDay.DayStatus.DayOff;
                    break;

                case "Sick day(paid)":
                    Log.i("22222222222222222", "cool4");
                    if(myhours != null)
                    {
                        txt_from.setText(new SimpleDateFormat("dd/MM/yyyy").format(myhours.getStart_time()));
                        txt_remove_shift.setEnabled(true);
                    }
                    txt_from.setText("From");
                    txt_to.setText("To");
                    txt_to.setEnabled(false);
                    txt_from.setEnabled(false);
                    txt_view_to.setText("");
                    txt_view_from.setText("");
                    txt_view_time.setText(elementDate);
                    txt_remove_shift.setEnabled(false);
                    btn_save.setEnabled(true);
                    choosenStatus = MyDay.DayStatus.SickDay;
                    break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    View.OnClickListener saveShift = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(myhours == null && (choosenStatus == MyDay.DayStatus.WorkOnRestDay || choosenStatus == MyDay.DayStatus.RegularDay))
            {
                worker.removeDayIndex(elementDate);
                worker.addHours(elementDate , tempStartTime , tempStopTime , choosenStatus);
                msp.writeDataToSP(worker);
                goToGameActivity();
            }
            else
            {
                worker.removeDayIndex(elementDate);
                worker.addDayByStatus(elementDate , choosenStatus);
                msp.writeDataToSP(worker);
                goToGameActivity();
            }
        }
    };

    View.OnClickListener removeShift = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(choosenStatus != null)
            {
                openAlertDialog();
            }
        }
    };


    View.OnClickListener setTimeFrom = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker = new TimePickerDialog(txt_from.getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                {
                    timePicker.setIs24HourView(true);
                    String string_time = timePicker.getHour() + ":" + timePicker.getMinute();
                    SimpleDateFormat f = new SimpleDateFormat("HH:mm");
                    try
                    {
                        Date d = f.parse(string_time);
                        tempStartTime = d.getTime();
                        txt_from.setText("" + new SimpleDateFormat("HH:mm:ss").format(d.getTime()));
                        myhours.setStart_time(tempStartTime);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    txt_view_time.setText("");
                    btn_save.setEnabled(false);
                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
    };


    View.OnClickListener setTimeTo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker = new TimePickerDialog(txt_from.getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    timePicker.setIs24HourView(true);
                    String string_time = timePicker.getHour() + ":" + timePicker.getMinute();
                    SimpleDateFormat f = new SimpleDateFormat("HH:mm");
                    try
                    {
                        Date d = f.parse(string_time);
                        tempStopTime = d.getTime();
                        txt_to.setText("" + new SimpleDateFormat("HH:mm:ss").format(d.getTime()));
                        myhours.setEnd_time(tempStopTime);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    txt_view_time.setText(timeDifference(tempStopTime - tempStartTime));
                    btn_save.setEnabled(true);

                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();


        }
    };

    private  void goToGameActivity()
    {
        FragmentTransaction transaction = null;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.commit();
        finish();
    }

    private void openAlertDialog() {
        new AlertDialog.Builder(this , R.style.AlertDialogStyle).setTitle("Delete entry")

                .setMessage("Are you sure you want to delete this entry?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if((choosenStatus == MyDay.DayStatus.WorkOnRestDay || choosenStatus == MyDay.DayStatus.RegularDay))
                        {
                            worker.removeDayIndex(elementDate);
                            msp.writeDataToSP(worker);
                            goToGameActivity();
                        }
                        else
                        {
                            worker.removeDayIndex(elementDate);
                            msp.writeDataToSP(worker);
                            goToGameActivity();
                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .setCancelable(false)
                .show();
    }

    private void findViews()
    {
        txt_from = findViewById(R.id.txt_from);
        txt_to = findViewById(R.id.txt_to);
        btn_save = findViewById(R.id.btn_save);
        txt_view_time = findViewById(R.id.txt_view_time);
        txt_shiftInfo = findViewById(R.id.txt_shiftInfo);
        txt_remove_shift = findViewById(R.id.txt_remove_shift);
        spinner = findViewById(R.id.spinner);
        txt_view_to = findViewById(R.id.txt_view_to);
        txt_view_from = findViewById(R.id.txt_view_from);
    }
}
