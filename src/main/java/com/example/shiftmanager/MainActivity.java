package com.example.shiftmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//
//      private UserDBManager userDBManager;
//
//    private String mobileNumber;
//    private DatabaseReference mUserProfile;
//    private WorkerShiftCounter worker;
//    private MySharePreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_reports).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//                msp = new MySharePreferences(getApplicationContext());
//        Intent intent = getIntent();
//        mobileNumber = intent.getStringExtra("mobile");
//        if(FirebaseDatabase.getInstance().getReference("Worker") != null)
//            mUserProfile = FirebaseDatabase.getInstance().getReference().child("Worker");
        //mUsersDB = FirebaseDatabase.getInstance().getReference().child("Worker");
        //userDBManager = new UserDBManager(getApplicationContext());

//        mUserProfile.addListenerForSingleValueEvent(valueEventListener);


    }

//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            //ArrayList<WorkerShiftCounter> list = new ArrayList<>();
//            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                //worker = ds.getValue(WorkerShiftCounter.class);
//                //worker = dataSnapshot.get(WorkerShiftCounter.class);
//                if(ds.getKey().equalsIgnoreCase(mobileNumber))
//                {
//                    worker = ds.getValue(WorkerShiftCounter.class);
//                    msp.writeDataToSP(worker);
//                    break;
//                }
//            }
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//        }
//    };
}
