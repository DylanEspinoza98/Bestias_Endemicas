package com.example.bestiasendemicas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bestiasendemicas.database.AnimalContract.AnimalEntry;
import com.example.bestiasendemicas.database.AnimalContract.RegionEntry;

public class AnimalDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bestias_endemicas.db";
    private static final int DATABASE_VERSION = 4; // Versión 4 añade audio_uri

    public AnimalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** SQL para crear tabla regiones */
    private static final String SQL_CREATE_REGIONES =
            "CREATE TABLE " + RegionEntry.TABLE_NAME + " (" +
                    RegionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RegionEntry.COLUMN_NOMBRE + " VARCHAR(50) NOT NULL" +
                    ");";

    /** SQL para crear tabla animales con columnas tipo y audio_uri */
    private static final String SQL_CREATE_ANIMALES =
            "CREATE TABLE " + AnimalEntry.TABLE_NAME + " (" +
                    AnimalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AnimalEntry.COLUMN_NOMBRE + " VARCHAR(100) NOT NULL, " +
                    AnimalEntry.COLUMN_DESCRIPCION + " TEXT, " +
                    AnimalEntry.COLUMN_FOTO_URL + " VARCHAR(255), " +
                    AnimalEntry.COLUMN_REGION_ID + " INTEGER NOT NULL, " +
                    AnimalEntry.COLUMN_ES_FAVORITO + " BOOLEAN DEFAULT 0, " +
                    AnimalEntry.COLUMN_TIPO + " VARCHAR(20) NOT NULL DEFAULT 'terrestre', " +
                    AnimalEntry.COLUMN_AUDIO_URI + " VARCHAR(255), " +  // ← nueva columna
                    "FOREIGN KEY(" + AnimalEntry.COLUMN_REGION_ID + ") REFERENCES " +
                    RegionEntry.TABLE_NAME + "(" + RegionEntry._ID + ")" +
                    ");";

    /** SQL para insertar regiones por defecto */
    private static final String SQL_INSERT_REGIONES =
            "INSERT INTO " + RegionEntry.TABLE_NAME +
                    " (" + RegionEntry.COLUMN_NOMBRE + ") VALUES " +
                    "('Norte'), " +
                    "('Centro'), " +
                    "('Sur'), " +
                    "('Austral');";

    /** SQL para eliminar tabla animales */
    private static final String SQL_DELETE_ANIMALES =
            "DROP TABLE IF EXISTS " + AnimalEntry.TABLE_NAME + ";";

    /** SQL para eliminar tabla regiones */
    private static final String SQL_DELETE_REGIONES =
            "DROP TABLE IF EXISTS " + RegionEntry.TABLE_NAME + ";";

    /** Crea las tablas y carga regiones por defecto */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL(SQL_CREATE_REGIONES);
        db.execSQL(SQL_CREATE_ANIMALES);
        db.execSQL(SQL_INSERT_REGIONES);
    }

    /** Actualiza la base de datos según la versión */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se pasó a la versión 3 → columna tipo
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + AnimalEntry.TABLE_NAME +
                    " ADD COLUMN " + AnimalEntry.COLUMN_TIPO +
                    " VARCHAR(20) NOT NULL DEFAULT 'terrestre';");
        }

        //Se pasó a la versión 4 → columna audio_uri
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE " + AnimalEntry.TABLE_NAME +
                    " ADD COLUMN " + AnimalEntry.COLUMN_AUDIO_URI +
                    " VARCHAR(255);");
        }
    }

    /** Configura la base de datos antes de abrirla */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}