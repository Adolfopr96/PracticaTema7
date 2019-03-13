package com.example.adolfo.practicatema7.DatabaseManager;

import android.provider.BaseColumns;

public class Database_Schema {
    public Database_Schema() {
    }
    public static abstract class Sites implements BaseColumns {
        public static final String TABLE_NAME = "LUGARES";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NOMBRE = "name";
        public static final String COLUMN_NAME_LATITUD = "latitude";
        public static final String COLUMN_NAME_LONGITUD = "longitude";
        public static final String COLUMN_NAME_COMENTARIOS = "comments";
        public static final String COLUMN_NAME_VALORACION = "rating";
        public static final String COLUMN_NAME_CATEGORIA = "category";

        public static final String COLUMN_TYPE_ID = "INTEGER";
        public static final String COLUMN_TYPE_NOMBRE = "TEXT";
        public static final String COLUMN_TYPE_LATITUD = "DOUBLE";
        public static final String COLUMN_TYPE_LONGITUD = "DOUBLE";
        public static final String COLUMN_TYPE_COMENTARIOS = "TEXT";
        public static final String COLUMN_TYPE_VALORACION = "FLOAT";
        public static final String COLUMN_TYPE_CATEGORIA = "INTEGER";
    }
}