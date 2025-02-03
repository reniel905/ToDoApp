package com.example.todoapp.ui.fragments;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Task;
import com.example.todoapp.ui.adapters.TasksAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TasksViewModel tasksViewModel;
    private RecyclerView recyclerView;
    private TextView emptyTasksText;
    private FloatingActionButton addTaskButton;
    private TasksAdapter adapter = new TasksAdapter();

    public TasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tasks.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
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

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        emptyTasksText = view.findViewById(R.id.empty_tasks_text);

        recyclerView = view.findViewById(R.id.tasks_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        addTaskButton = view.findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.add_task_layout, null);
                TextInputEditText taskTitle = view1.findViewById(R.id.task_title);
                taskTitle.setHint("New Task");
                AlertDialog addTaskDialog = new MaterialAlertDialogBuilder(getContext())
                        .setView(view1)
                        .setTitle("Create")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                    AppRepository.tasks.getValue().add(0, new Task(taskTitle.getText().toString()));
                                    adapter.notifyItemInserted(0);
                                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                                    recyclerView.scrollToPosition(0);
                                    updateTasks();
                                    dialogInterface.dismiss();

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        }).create();

                addTaskDialog.show();

                taskTitle.addTextChangedListener(new TextWatcher() {

                    private void handleText(){

                        final Button saveButton = addTaskDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                        if (taskTitle.getText().length() == 0) {

                            saveButton.setEnabled(false);

                        } else {

                            saveButton.setEnabled(true);

                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        handleText();

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                addTaskDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);





        return view;
    }

    private Task deletedTask = null;
    private Snackbar snackbar;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {

                deletedTask = AppRepository.tasks.getValue().get(position);
                Log.d(TAG, "onSwiped: " + deletedTask);
                AppRepository.tasks.getValue().remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, AppRepository.tasks.getValue().size());
                updateTasks();

                View rootView = getView();

                if (rootView != null){

                    snackbar = Snackbar.make(rootView, deletedTask.getText(), Snackbar.LENGTH_LONG)
                            .setAnchorView(getActivity().findViewById(R.id.bottom_nav))
                            .setAction("Undo", new View.OnClickListener() {
                                @Override

                                public void onClick(View view) {

                                    if (getContext() == null) {

                                        return;

                                    }

                                    AppRepository.tasks.getValue().add(position, new Task(deletedTask.getText(), deletedTask.isDone()));
                                    adapter.notifyItemInserted(position);
                                    recyclerView.scrollToPosition(position);
                                    updateTasks();

                                }
                            })
                            .setText("1 Task Deleted");

                    snackbar.show();

                }
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);



        }
    };



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        updateTasks();

    }

    void updateTasks(){

        tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        tasksViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Task>>() {

            @Override
            public void onChanged(ArrayList<Task> tasks) {

                if (tasks.isEmpty()) {

                    emptyTasksText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);


                } else {

                    emptyTasksText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }


            }
        });

    }

    @Override
    public void onPause(){

        super.onPause();

        if (snackbar != null){

            snackbar.dismiss();

        }

    }

}