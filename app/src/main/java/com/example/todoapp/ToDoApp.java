package com.example.todoapp;

import android.app.Application;

import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Task;

import java.util.ArrayList;

public class ToDoApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();


        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1"));
        tasks.add(new Task("Task 2"));
        tasks.add(new Task("Task 3"));

        AppRepository.tasks.setValue(tasks);

    }



}
