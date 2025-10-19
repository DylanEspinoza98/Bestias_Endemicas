package com.example.bestiasendemicas.database;

import android.provider.BaseColumns;

public final class AnimalContract {
    private AnimalContract() {}  // Constructor privado para evitar instancias

    /** Definición de la tabla regiones */
    public static class RegionEntry implements BaseColumns {
        public static final String TABLE_NAME = "regiones";
        public static final String COLUMN_NOMBRE = "nombre";
    }

    /** Definición de la tabla animales con foreign key a regiones */
    public static class AnimalEntry implements BaseColumns {
        public static final String TABLE_NAME = "animales";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_FOTO_URL = "foto_url";     // URI imagen externa
        public static final String COLUMN_REGION_ID = "region_id";
        public static final String COLUMN_ES_FAVORITO = "es_favorito";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_AUDIO_URI = "audio_uri";   // Nueva columna para MP3
    }
}