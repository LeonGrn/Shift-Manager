package com.example.shiftmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditShiftActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button btn_save;
    private TextView txt_remove_shift , txt_shiftInfo;
    private WorkerShiftCounter worker;
    private Spinner spinner;
    private String elementIndex = "";
    private MyAdapter adapter;
//    private ListView listView;

    MySharePreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift);

        btn_save = findViewById(R.id.btn_save);

        txt_shiftInfo = findViewById(R.id.txt_shiftInfo);
        txt_remove_shift = findViewById(R.id.txt_remove_shift);
        spinner = findViewById(R.id.spinner);


//        listView = findViewById(R.id.listView);


//        MyDay myDay = worker.getDay(elementIndex);

//        adapter = new MyAdapter(getApplicationContext(), myDay.getM_hours());
//        listView.setAdapter(adapter);
//        txt_shiftInfo.setText("Hours\n" + myDay.getM_hours());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.status_shift, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        msp = new MySharePreferences(getApplicationContext());
        worker  = msp.readDataFromSP();

        btn_save.setOnClickListener(saveShift);
        txt_remove_shift.setOnClickListener(removeShift);

        spinner.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view,   int pos, long id)
    {
        String item = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
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
            //worker.m_arrDays.add(i);
            goToGameActivity();
        }
    };

    View.OnClickListener removeShift = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //worker.m_arrDays.remove(i);
            goToGameActivity();
        }
    };


    private  void goToGameActivity()
    {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        finish();
        super.onBackPressed();
    }
}
