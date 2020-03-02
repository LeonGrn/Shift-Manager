package com.example.shiftmanager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SetAdapterSickDay extends BaseAdapter {

    private Context context;
    private ArrayList<TextView> arrayList;
    private TextView txt_statusSickOff;

    public SetAdapterSickDay(Context context, ArrayList<TextView> arrayList)
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
        convertView = LayoutInflater.from(context).inflate(R.layout.setadaptersickday, parent, false);
        txt_statusSickOff = convertView.findViewById(R.id.txt_statusSickOff);
        txt_statusSickOff.setText("Sick Off(paid)");
        txt_statusSickOff.setTypeface(null, Typeface.BOLD);
        txt_statusSickOff.setTextSize(30);
        return convertView;
    }
}