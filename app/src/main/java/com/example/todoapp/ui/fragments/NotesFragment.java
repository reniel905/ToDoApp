package com.example.todoapp.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Note;
import com.example.todoapp.ui.adapters.NotesAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextInputEditText noteTitle;
    private TextInputEditText noteSubtitle;
    private LinearLayout emptyNotesText;
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private NotesViewModel notesViewModel;
    private FloatingActionButton addNoteButton;


    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notes.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        notesAdapter = new NotesAdapter();

        recyclerView = view.findViewById(R.id.notes_list);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyNotesText = view.findViewById(R.id.empty_notes_text);

        addNoteButton = view.findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.add_note_layout, null);

                noteTitle = view1.findViewById(R.id.edit_title);
                noteSubtitle = view1.findViewById(R.id.edit_subtitle);

                AlertDialog alertDialog = new MaterialAlertDialogBuilder(getContext())
                        .setView(view1)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                AppRepository.notes.getValue().add(0, new Note(noteTitle.getText().toString(), noteSubtitle.getText().toString()));
                                notesAdapter.notifyItemInserted(0);
                                notesAdapter.notifyItemRangeChanged(0, notesAdapter.getItemCount());
                                recyclerView.scrollToPosition(0);
                                updateNotes();
                                dialogInterface.dismiss();


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                dialogInterface.dismiss();

                            }
                        }).create();

                alertDialog.show();

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateNotes();

    }

    void updateNotes(){

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> notes) {

                if (notes.isEmpty()){

                    emptyNotesText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {

                    emptyNotesText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }
        });
    }
}