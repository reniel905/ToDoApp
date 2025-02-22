package com.example.todoapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.localdata.AppRepository;
import com.example.todoapp.model.Note;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> localDataSet = AppRepository.notes.getValue();

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
                .inflate(R.layout.list_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        String title = localDataSet.get(position).getTitle();
        String subtitle = localDataSet.get(position).getDescription();

        holder.noteTitle.setText(title);
        holder.noteSubtitle.setText(subtitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
