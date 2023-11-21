package com.xin.tutorial.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.xin.tutorial.db.dao.NoteDao;
import com.xin.tutorial.db.entity.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase instance;

    public abstract NoteDao noteDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NoteDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    Log.d("wanxin", "create database");
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note.db")
                            .addCallback(roomCallBack)
                            .build();
                }
            }
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("wanxin", "database create");
            databaseWriteExecutor.execute(() -> {
                NoteDao noteDao = instance.noteDao();
                noteDao.deleteAllNotes();

                noteDao.insert(new Note("title 1", "Description 1", 1));
                noteDao.insert(new Note("title 2", "Description 2", 2));
                noteDao.insert(new Note("title 3", "Description 3", 3));
            });

        }

    };
}
