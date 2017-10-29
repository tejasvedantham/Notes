package com.tejas.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tejas on 7/24/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final int DATABSE_VERSION = 1;

    public static final String NOTES_TABLE_NAME = "Notes";
    public static final String NOTES_COLUM_ID = "_id";

    public static final String NOTES_COLUMN_TITLE = "title";
    public static final String NOTES_COLUMN_NOTE = "note";

    public String query1 = "CREATE TABLE " + NOTES_TABLE_NAME + "(" +
            NOTES_COLUM_ID + " INTEGER PRIMARY KEY," +
            NOTES_COLUMN_TITLE + " TEXT," +
            NOTES_COLUMN_NOTE + " TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertNote(String title, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTES_COLUMN_TITLE, title);
        cv.put(NOTES_COLUMN_NOTE, note);
        db.insert(NOTES_TABLE_NAME, null, cv);
    }

    public void updateNote(Integer id, String nTitle, String nNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTES_COLUMN_TITLE, nTitle);
        cv.put(NOTES_COLUMN_NOTE, nNote);
        db.update(NOTES_TABLE_NAME, cv, NOTES_COLUM_ID + " = ? ", new String[]{Integer.toString(id)});

    }

    public Cursor getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + NOTES_TABLE_NAME + " WHERE " +
                NOTES_COLUM_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + NOTES_TABLE_NAME, null);
        return res;
    }

    public Integer deleteNote(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME,
                NOTES_COLUM_ID + " = ? ",
                new String[]{Integer.toString(id)});
    }


}
