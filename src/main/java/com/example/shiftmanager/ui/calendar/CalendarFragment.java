package com.example.shiftmanager.ui.calendar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiftmanager.Adapter_NoteModel;
import com.example.shiftmanager.MySharePreferences;
import com.example.shiftmanager.Note;
import com.example.shiftmanager.R;
import com.example.shiftmanager.WorkerShiftCounter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarView calendarView;
    MySharePreferences msp;
    ArrayList<WorkerShiftCounter> timeWorked = null;

    private RecyclerView list_LST_notes;
    private Adapter_NoteModel adapter_noteModel;

    private String keyInfo = "key_time";

    public CalendarFragment()
    {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        msp = new MySharePreferences(getActivity().getApplicationContext());

        try
        {
            timeWorked = new Gson().fromJson(msp.getString(keyInfo,""), new TypeToken<List<WorkerShiftCounter>>(){}.getType());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            timeWorked = new ArrayList<>();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = root.findViewById(R.id.calendarView);

        list_LST_notes = root.findViewById(R.id.list_LST_notes);
        initList();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        Toast.makeText(getContext(), ""+selectedDate, Toast.LENGTH_LONG).show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                Toast.makeText(getContext(), ""+view.getDate(), Toast.LENGTH_LONG).show();

            }
        });

        return root;
    }


    private void initList() {
        ArrayList<Note> notes = getNotes();
        adapter_noteModel = new Adapter_NoteModel(getActivity().getApplicationContext(), notes);
        list_LST_notes.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list_LST_notes.setLayoutManager(layoutManager);
        list_LST_notes.setAdapter(adapter_noteModel);

        adapter_noteModel.SetOnItemClickListener(new Adapter_NoteModel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Note note) {
                openNote(note);
            }
        });
    }

    private void openNote(Note note) {

    }

    private ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        Note note1 = new Note("Guy");
        notes.add(note1);
        Note note2 = new Note("Avi");
        notes.add(note2);
        Note note3 = new Note("Ran");
        notes.add(note3);
        Note note4 = new Note("Ran");
        notes.add(note4);
        Note note5 = new Note("Ran");
        notes.add(note5);
        return notes;
    }

}