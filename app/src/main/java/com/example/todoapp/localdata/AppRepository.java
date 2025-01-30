package com.example.todoapp.localdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.model.Task;

import java.util.ArrayList;

public class AppRepository {

    public static MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();

}
