package com.example.bestiasendemicas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bestiasendemicas.database.AnimalContract.AnimalEntry;

public class AnimalDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "animals.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public AnimalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Consulta SQL para crear la tabla
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AnimalEntry.TABLE_NAME + " (" +
                    AnimalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AnimalEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    AnimalEntry.COLUMN_PHOTO_PATH + " TEXT," +
                    AnimalEntry.COLUMN_DESCRIPTION + " TEXT," +
                    AnimalEntry.COLUMN_REGION + " TEXT," +
                    AnimalEntry.COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0," +
                    AnimalEntry.COLUMN_CREATED_DATE + " INTEGER DEFAULT (strftime('%s','now'))" +
                    ");";

    //Consulta SQL para eliminar la tabla si existe
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AnimalEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borra la tabla y la vuelve a crear
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
