package com.example.todoapp.localdata;

import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.model.Note;
import com.example.todoapp.model.Task;

import java.util.ArrayList;

public class AppRepository {

    public static MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();
    public static MutableLiveData<ArrayList<Note>> notes = new MutableLiveData<>();

}
