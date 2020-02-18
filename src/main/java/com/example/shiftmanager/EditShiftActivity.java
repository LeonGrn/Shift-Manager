package com.example.shiftmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class EditShiftActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift);

        button = findViewById(R.id.button);



        button.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
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
