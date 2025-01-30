package com.example.todoapp.ui.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Task;

import java.util.ArrayList;

public class TasksViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Task>> tasks;

    public TasksViewModel(){

        tasks = AppRepository.tasks;

    }

    public LiveData<ArrayList<Task>> getTasks(){

        return tasks;

    }

}
