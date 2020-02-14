package com.example.shiftmanager;

public class Note {

    private String title = "";

    public Note() {
    }

    public Note(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }
}