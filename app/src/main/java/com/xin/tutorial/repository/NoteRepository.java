package com.xin.tutorial.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.xin.tutorial.db.NoteDatabase;
import com.xin.tutorial.db.dao.NoteDao;
import com.xin.tutorial.db.entity.Note;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.queryAllNotes();

    }

    public void insert(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.insert(note);
        });

    }

    public void update(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.update(note);
        });
    }

    public void delete(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.delete(note);
        });
    }

    public void deleteAllNotes() {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.deleteAllNotes();
        });
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

}
