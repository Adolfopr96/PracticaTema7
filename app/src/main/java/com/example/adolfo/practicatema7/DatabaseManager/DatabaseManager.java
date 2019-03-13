package com.example.adolfo.practicatema7.DatabaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.adolfo.practicatema7.DatabaseManager.Database_Schema.Sites;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final int OPEN_MODE_READ = 1;
    public static final int OPEN_MODE_WRITE = 2;

    private static final String DATABASE_NAME = "PracticaTema7";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Sites.TABLE_NAME + " (" +
                    Sites.COLUMN_NAME_ID + " " + Sites.COLUMN_TYPE_ID + " PRIMARY KEY AUTOINCREMENT, " +
                    Sites.COLUMN_NAME_NOMBRE + " " + Sites.COLUMN_TYPE_NOMBRE + "," +
                    Sites.COLUMN_NAME_LATITUD + " " + Sites.COLUMN_TYPE_LATITUD +  "," +
                    Sites.COLUMN_NAME_LONGITUD + " " + Sites.COLUMN_TYPE_LONGITUD +  "," +
                    Sites.COLUMN_NAME_COMENTARIOS + " " + Sites.COLUMN_TYPE_COMENTARIOS +  "," +
                    Sites.COLUMN_NAME_VALORACION + " " + Sites.COLUMN_TYPE_VALORACION +  "," +
                    Sites.COLUMN_NAME_CATEGORIA + " " + Sites.COLUMN_TYPE_CATEGORIA + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Sites.TABLE_NAME;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static SQLiteDatabase conectar(Context context, int open_mode) {
        DatabaseManager db = new DatabaseManager(context);
        SQLiteDatabase conn;
        switch (open_mode) {
            case OPEN_MODE_READ:  conn = db.getReadableDatabase(); break;
            case OPEN_MODE_WRITE: conn = db.getWritableDatabase(); break;
            default:              conn = db.getReadableDatabase(); break;
        }
        return conn;
    }

    public static void desconectar(SQLiteDatabase conn) {
        conn.close();
    }
}