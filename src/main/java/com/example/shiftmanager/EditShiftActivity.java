package com.example.shiftmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
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
import java.util.Locale;

public class EditShiftActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button btn_save;
    private EditText txt_from , txt_to;
    private TextView txt_remove_shift , txt_shiftInfo , txt_view_time , txt_view_to;
    private WorkerShiftCounter worker;
    private Spinner spinner;
    private MyAdapter adapter;
    private int elementIndex = -1;
    private String elementDate = "";
    private MyHours myhours = null;
    private long tempStartTime = 0;
    private long tempStopTime = 0;
    MySharePreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift);
        txt_from = findViewById(R.id.txt_from);
        txt_to = findViewById(R.id.txt_to);
        btn_save = findViewById(R.id.btn_save);
        txt_view_time = findViewById(R.id.txt_view_time);
        txt_shiftInfo = findViewById(R.id.txt_shiftInfo);
        txt_remove_shift = findViewById(R.id.txt_remove_shift);
        spinner = findViewById(R.id.spinner);
        txt_view_to = findViewById(R.id.txt_view_to);
        msp = new MySharePreferences(getApplicationContext());
        worker = msp.readDataFromSP();

        Intent intent = getIntent();
        elementIndex = Integer.valueOf(intent.getStringExtra("DayIndex"));
        elementDate = intent.getStringExtra("DayDate");
        myhours = worker.getHourByIndex(elementIndex, elementDate);
        if (myhours != null) {
            String timeStamp = new SimpleDateFormat().format(myhours.getStart_time());
            String timeStamp2 = new SimpleDateFormat().format(myhours.getEnd_time());
            Log.i("22222222222222222", timeStamp + "  " + timeStamp2);

            txt_shiftInfo.setText("Choose work day");
            txt_shiftInfo.setTextSize(20);
            txt_from.setText(timeStamp);
            txt_to.setText(timeStamp2);

            txt_view_time.setText(timeDifference(myhours.getEnd_time() - myhours.getStart_time()));

        } else {
            txt_shiftInfo.setText("Add work day");
            txt_shiftInfo.setTextSize(20);
            txt_from.setText("Add start time");
            txt_to.setText("Add end time");
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
                        txt_to.setText("" + new SimpleDateFormat().format(myhours.getEnd_time()));
                        txt_view_time.setText(timeDifference(myhours.getEnd_time() - myhours.getStart_time()));
                    }

                    txt_view_to.setText("To");
                    break;

                case "Work on a rest day":
                    Log.i("22222222222222222", "cool2");
                    if(myhours != null)
                    {
                        txt_to.setText("" + new SimpleDateFormat().format(myhours.getEnd_time()));
                        txt_view_time.setText(timeDifference(myhours.getEnd_time() - myhours.getStart_time()));
                    }
                    txt_view_to.setText("To");
                    break;

                case "day off(paid)":
                    Log.i("22222222222222222", "cool3");
                    txt_to.setText("To");
                    txt_view_to.setText("");
                    txt_view_time.setText("");
                    break;

                case "Sick day(paid)":
                    Log.i("22222222222222222", "cool4");
                    txt_to.setText("To");
                    txt_view_to.setText("");
                    txt_view_time.setText("");
                    break;



        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    View.OnClickListener saveShift = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(myhours != null)
            {
                worker.addHourByIndex(myhours , elementDate);
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
            if(myhours != null)
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
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    txt_from.setText( selectedHour + ":" + selectedMinute);
                    String string_time = selectedHour + ":" + selectedMinute;
                    SimpleDateFormat f = new SimpleDateFormat("HH:mm");
                    try {
                        Date d = f.parse(string_time);
                        tempStartTime = d.getTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("22222222222222222", tempStartTime + "");

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
                    txt_to.setText( selectedHour + ":" + selectedMinute);
                    String string_time = selectedHour + ":" + selectedMinute;
                    SimpleDateFormat f = new SimpleDateFormat("HH:mm");
                    try {
                        Date d = f.parse(string_time);
                        tempStopTime = d.getTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("22222222222222222", timePicker.toString() + " " + timePicker.getMinute());

                    txt_view_time.setText(timeDifference(tempStopTime - tempStartTime));;
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
                    public void onClick(DialogInterface dialog, int which) {
                        worker.removeHourByIndex(elementIndex , elementDate);
                        msp.writeDataToSP(worker);
                        goToGameActivity();
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
}
