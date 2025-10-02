package com.example.bestiasendemicas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bestiasendemicas.database.AnimalContract.AnimalEntry;
import com.example.bestiasendemicas.database.AnimalContract.RegionEntry;

public class AnimalDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bestias_endemicas.db";
    private static final int DATABASE_VERSION = 2; // Incrementamos la versión

    // Constructor
    public AnimalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // SQL para crear tabla regiones
    private static final String SQL_CREATE_REGIONES =
            "CREATE TABLE " + RegionEntry.TABLE_NAME + " (" +
                    RegionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RegionEntry.COLUMN_NOMBRE + " VARCHAR(50) NOT NULL" +
                    ");";

    // SQL para crear tabla animales con foreign key
    private static final String SQL_CREATE_ANIMALES =
            "CREATE TABLE " + AnimalEntry.TABLE_NAME + " (" +
                    AnimalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AnimalEntry.COLUMN_NOMBRE + " VARCHAR(100) NOT NULL," +
                    AnimalEntry.COLUMN_DESCRIPCION + " TEXT," +
                    AnimalEntry.COLUMN_FOTO_URL + " VARCHAR(255)," +
                    AnimalEntry.COLUMN_REGION_ID + " INTEGER NOT NULL," +
                    AnimalEntry.COLUMN_ES_FAVORITO + " BOOLEAN DEFAULT 0," +
                    AnimalEntry.COLUMN_FECHA_INGRESO + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (" + AnimalEntry.COLUMN_REGION_ID + ") REFERENCES " +
                    RegionEntry.TABLE_NAME + "(" + RegionEntry._ID + ")" +
                    ");";

    // SQL para insertar regiones por defecto
    private static final String SQL_INSERT_REGIONES =
            "INSERT INTO " + RegionEntry.TABLE_NAME + " (" + RegionEntry.COLUMN_NOMBRE + ") VALUES " +
                    "('Norte'), " +
                    "('Centro'), " +
                    "('Sur'), " +
                    "('Austral');";

    // SQL para eliminar tablas
    private static final String SQL_DELETE_ANIMALES =
            "DROP TABLE IF EXISTS " + AnimalEntry.TABLE_NAME;
    private static final String SQL_DELETE_REGIONES =
            "DROP TABLE IF EXISTS " + RegionEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Habilitar foreign keys
        db.execSQL("PRAGMA foreign_keys=ON;");

        // Crear tablas (orden importante: primero regiones, luego animales)
        db.execSQL(SQL_CREATE_REGIONES);
        db.execSQL(SQL_CREATE_ANIMALES);

        // Insertar regiones por defecto
        db.execSQL(SQL_INSERT_REGIONES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas existentes (orden importante: primero animales, luego regiones)
        db.execSQL(SQL_DELETE_ANIMALES);
        db.execSQL(SQL_DELETE_REGIONES);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}