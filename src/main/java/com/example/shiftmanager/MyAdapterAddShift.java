package com.example.shiftmanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyAdapterAddShift extends BaseAdapter {

    private Context context;
    private ArrayList<TextView> arrayList;
    private TextView addShift, addShift2 ,addShift3;

    public MyAdapterAddShift(Context context, ArrayList<TextView> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public void clear() {
        arrayList.clear();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_addshift, parent, false);
        addShift = convertView.findViewById(R.id.txt_addshift);
        addShift2 = convertView.findViewById(R.id.txt_addshift2);
        addShift3 = convertView.findViewById(R.id.txt_addshift3);
        addShift.setText("Add Shift +");
        addShift.setTypeface(null, Typeface.ITALIC);
        addShift.setTextSize(20);
        addShift.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return convertView;
    }
}