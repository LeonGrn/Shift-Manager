package com.example.shiftmanager;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class MySignal
{
    public static void vibrate(Context context, int duration)
    {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.PARCELABLE_WRITE_RETURN_VALUE));
        }
        else
        {
            //deprecated in API 26
            v.vibrate(duration);
        }
    }
}