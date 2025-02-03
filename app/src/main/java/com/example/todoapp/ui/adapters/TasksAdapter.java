package com.example.todoapp.ui.adapters;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private final ArrayList<Task> localDataSet = AppRepository.tasks.getValue();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String itemText = localDataSet.get(position).getText();

        if (itemText.length() > 24){

            holder.getItemText().setText(itemText.substring(0, 24) + " ..." );

        } else {

            holder.getItemText().setText(itemText);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getCheckBox().setChecked(!holder.checkBox.isChecked());

            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (holder.checkBox.isChecked()){

                    localDataSet.get(position).setDone(true);
                    AppRepository.tasks.setValue(localDataSet);
                    holder.getItemText().setPaintFlags(holder.getItemText().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                } else {

                    localDataSet.get(position).setDone(false);
                    AppRepository.tasks.setValue(localDataSet);
                    holder.getItemText().setPaintFlags(holder.getItemText().getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                }

            }
        });


        holder.checkBox.setChecked(localDataSet.get(position).isDone());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Task item = localDataSet.get(position);

                View view1 = LayoutInflater.from(view.getContext()).inflate(R.layout.add_task_layout, null);
                TextInputEditText taskTitle = view1.findViewById(R.id.task_title);
                taskTitle.setHint("Task");
                taskTitle.setText(item.getText());

                AlertDialog alertDialog = new MaterialAlertDialogBuilder(view.getContext())
                        .setView(view1)
                        .setTitle("Edit Task")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                item.setText(taskTitle.getText().toString());
                                notifyItemChanged(position);
                                AppRepository.tasks.setValue(localDataSet);
                                dialogInterface.dismiss();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                localDataSet.remove(item);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                AppRepository.tasks.setValue(localDataSet);
                                dialogInterface.dismiss();

                            }
                        }).create();

                alertDialog.show();

                taskTitle.addTextChangedListener(new TextWatcher() {

                    private void handleText(){

                        final Button saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

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

                // alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                return false;
            }

        });

    }

    @Override
    public int getItemCount() {

        return localDataSet.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView itemText;
        private final CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.item);
            checkBox = itemView.findViewById(R.id.checkbox);

        }

        public TextView getItemText(){

            return itemText;

        }

        public CheckBox getCheckBox(){

            return checkBox;

        }


    }


}
