package com.example.bestiasendemicas.database;

import android.provider.BaseColumns;

public final class AnimalContract {

    private AnimalContract() {}

    public static class AnimalEntry implements BaseColumns {
        public static final String TABLE_NAME = "animals";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHOTO_PATH = "photo_path";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_REGION = "region";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";
        public static final String COLUMN_CREATED_DATE = "created_date";
    }
}
