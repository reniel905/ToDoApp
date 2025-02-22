package com.example.todoapp;

import android.app.Application;

import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Note;
import com.example.todoapp.model.Task;

import java.util.ArrayList;

public class ToDoApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();


        ArrayList<Task> tasks = new ArrayList<>();
        AppRepository.tasks.setValue(tasks);

        ArrayList<Note> notes = new ArrayList<>();
        AppRepository.notes.setValue(notes);

    }



}
