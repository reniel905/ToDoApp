package com.example.todoapp.ui.adapters;

import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Note;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import com.example.todoapp.ui.fragments.NotesFragment;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final ArrayList<Note> localDataSet = AppRepository.notes.getValue();

    public static class ViewHolder extends RecyclerView.ViewHolder{


        private final TextView noteTitle;
        private final TextView noteSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteSubtitle = itemView.findViewById(R.id.note_subtitle);

        }

        public TextView getNoteTitle() {
            return noteTitle;
        }

        public TextView getNoteSubtitle() {
            return noteSubtitle;
        }
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        String title = localDataSet.get(position).getTitle();
        String subtitle = localDataSet.get(position).getDescription();

        holder.getNoteTitle().setText(title);
        holder.getNoteSubtitle().setText(subtitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Note item = localDataSet.get(position);
                View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.add_note_layout, null);
                TextInputEditText noteTitle = view1.findViewById(R.id.edit_title);
                TextInputEditText noteSubtitle = view1.findViewById(R.id.edit_subtitle);

                noteTitle.setText(item.getTitle());
                noteSubtitle.setText(item.getDescription());

                AlertDialog alertDialog = new MaterialAlertDialogBuilder(v.getContext())
                        .setView(view1)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                item.setTitle(noteTitle.getText().toString());
                                item.setDescription(noteSubtitle.getText().toString());
                                notifyItemChanged(position);
                                AppRepository.notes.setValue(localDataSet);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                localDataSet.remove(item);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                AppRepository.notes.setValue(localDataSet);
                                dialog.dismiss();

                            }
                        })
                        .create();

                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
