package com.example.adolfo.practicatema7.DatabaseManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Querys_Database {
    public static void insertSite(Context context, Sites s) {
        ContentValues content = new ContentValues();
        content.put(Database_Schema.Sites.COLUMN_NAME_NOMBRE, s.getNameSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_LATITUD, s.getLatitudeSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_LONGITUD, s.getLongitudeSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_COMENTARIOS, s.getCommentSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_VALORACION, s.getRatingSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_CATEGORIA,s.getCategorySite());
        SQLiteDatabase conn = DatabaseManager.conectar(context, DatabaseManager.OPEN_MODE_WRITE);
        conn.insert(Database_Schema.Sites.TABLE_NAME, null, content);
        DatabaseManager.desconectar(conn);
    }
    public static void deleteSite(Context context, Sites s) {
        String sqlWhere = Database_Schema.Sites.COLUMN_NAME_ID + " = " + s.getIdSite();
        SQLiteDatabase conn = DatabaseManager.conectar(context, DatabaseManager.OPEN_MODE_WRITE);
        conn.delete(Database_Schema.Sites.TABLE_NAME, sqlWhere, null);
        DatabaseManager.desconectar(conn);
    }
}
