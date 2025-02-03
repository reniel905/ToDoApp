package com.example.todoapp.ui.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Note;
import com.example.todoapp.model.Task;

import java.util.ArrayList;

public class NotesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Note>> notes;

    public NotesViewModel(){

        notes = AppRepository.notes;

    }

    public MutableLiveData<ArrayList<Note>> getNotes() {

        return notes;

    }
}
