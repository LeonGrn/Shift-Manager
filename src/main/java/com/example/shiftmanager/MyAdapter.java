package com.example.shiftmanager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyHours> arrayList;
    private TextView startTime, endTime;

    public MyAdapter(Context context, ArrayList<MyHours> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_listview, parent, false);
        startTime = convertView.findViewById(R.id.txt_startTime);
        endTime = convertView.findViewById(R.id.txt_endTime);

        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(arrayList.get(position).getStart_time());
        String timeStamp2 = new SimpleDateFormat("HH:mm:ss").format(arrayList.get(position).getEnd_time());

        startTime.setTypeface(null, Typeface.BOLD);
        endTime.setTypeface(null, Typeface.BOLD);

        startTime.setText("Start Shift " + timeStamp);
        endTime.setText("End Shift    " + timeStamp2);

        return convertView;
    }
}