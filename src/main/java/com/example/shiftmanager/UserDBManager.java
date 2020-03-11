package com.example.shiftmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDBManager {

    private DatabaseReference mUsersDB;
    private Context mContext;
    private WorkerShiftCounter worker;


    public UserDBManager(Context context) {
        mUsersDB = FirebaseDatabase.getInstance().getReference().child("Worker");
        mContext = context;
    }

    /**
     * @param mobileNumber
     */
    public void signInToProfile(final String mobileNumber, final String firstName, final String lastName) {
        Query query = mUsersDB.orderByChild("id").equalTo(mobileNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    moveToAnotherActivity(mobileNumber, MainActivity.class);
                } else {
                    signUpAndCreateNewUser(mobileNumber, firstName, lastName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * @param mobileNumber
     */
    public void checkIfNumberExist(final String mobileNumber) {
        Query query = mUsersDB.orderByChild("id").equalTo(mobileNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    worker = dataSnapshot.getValue(WorkerShiftCounter.class);

                    moveToAnotherActivity(mobileNumber, VerifyCodeActivity.class);
                } else {
                    Toast.makeText(mContext, "User not exist. Sign-up Please!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     */
    private void signUpAndCreateNewUser(final String mobileNumber, final String firstName, String lastName)
    {
        mUsersDB.child(mobileNumber).setValue(new WorkerShiftCounter(mobileNumber , firstName)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, firstName + " signed-up!!", Toast.LENGTH_SHORT).show();
                    moveToAnotherActivity(mobileNumber, MainActivity.class);
                } else {
                    Toast.makeText(mContext, firstName + " DIDNT signed-up!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @param user
     */
    public void saveValveArrayListToUser(WorkerShiftCounter user) {
        mUsersDB.child(user.getId() + "").setValue(user.getArrDays()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(mContext, "Something went wrong..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @param mobileNumber
     * @param activity
     */
    private void moveToAnotherActivity(String mobileNumber, Class activity) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtra("mobile", mobileNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}