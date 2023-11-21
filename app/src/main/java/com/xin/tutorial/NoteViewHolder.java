package com.xin.tutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xin.tutorial.db.entity.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTitle;

    public TextView textViewDescription;

    public TextView textViewPriority;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewDescription = itemView.findViewById(R.id.text_view_description);
        textViewPriority = itemView.findViewById(R.id.text_view_priority);
    }


    public static NoteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
