package com.example.adolfo.practicatema7.DatabaseManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class Querys_Database {
    public static String query1="";
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
    public static void deleteSite(Context context, Sites p) {
        String sqlWhere = Database_Schema.Sites.COLUMN_NAME_ID + " = " + p.getIdSite();
        SQLiteDatabase conn = DatabaseManager.conectar(context, DatabaseManager.OPEN_MODE_WRITE);
        conn.delete(Database_Schema.Sites.TABLE_NAME, sqlWhere, null);
        DatabaseManager.desconectar(conn);
    }
    public static List select_site(Context context){
        List prod = new ArrayList<>();
        String[] sqlFields = {Database_Schema.Sites.COLUMN_NAME_ID, Database_Schema.Sites.COLUMN_NAME_NOMBRE, Database_Schema.Sites.COLUMN_NAME_LATITUD, Database_Schema.Sites.COLUMN_NAME_LONGITUD, Database_Schema.Sites.COLUMN_NAME_COMENTARIOS, Database_Schema.Sites.COLUMN_NAME_VALORACION, Database_Schema.Sites.COLUMN_NAME_CATEGORIA};
        String sqlWhere = query1;
        String sqlOrderBy = Database_Schema.Sites.COLUMN_NAME_NOMBRE + " ASC";

        SQLiteDatabase conn = DatabaseManager.conectar(context, DatabaseManager.OPEN_MODE_READ);
        Cursor cursor = conn.query(Database_Schema.Sites.TABLE_NAME, sqlFields, query1, null, null, null, sqlOrderBy);
        if (cursor.getCount() == 0) {
            prod = null;
        } else {
            cursor.moveToFirst();
            do {
                Long dataId = cursor.getLong(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_NOMBRE));
                Double dataLatitud = cursor.getDouble(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_LATITUD));
                Double dataLongitud = cursor.getDouble(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_LONGITUD));
                String dataComentarios = cursor.getString(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_COMENTARIOS));
                Float dataValoracion = cursor.getFloat(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_VALORACION));
                int dataCategoria = cursor.getInt(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_CATEGORIA));
                prod.add(new Sites(dataId, dataNombre, dataLatitud, dataLongitud, dataComentarios, dataValoracion, dataCategoria));
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.desconectar(conn);
        return prod;
    }
    public static void edit_site(Context context, Sites s)
    {
        ContentValues content = new ContentValues();
        content.put(Database_Schema.Sites.COLUMN_NAME_NOMBRE, s.getNameSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_LATITUD, s.getLatitudeSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_LONGITUD, s.getLongitudeSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_COMENTARIOS, s.getCommentSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_VALORACION, s.getRatingSite());
        content.put(Database_Schema.Sites.COLUMN_NAME_CATEGORIA, s.getCategorySite());
        String sqlWhere = Database_Schema.Sites.COLUMN_NAME_ID + " = " + s.getIdSite();
        SQLiteDatabase conn = DatabaseManager.conectar(context, DatabaseManager.OPEN_MODE_WRITE);
        conn.update(Database_Schema.Sites.TABLE_NAME, content, sqlWhere, null);
        DatabaseManager.desconectar(conn);
    }
    public static Sites getLugar(Context context, String latitud, String longitud) {
        Sites lugar = null;
        String[] sqlFields = {Database_Schema.Sites.COLUMN_NAME_ID, Database_Schema.Sites.COLUMN_NAME_NOMBRE, Database_Schema.Sites.COLUMN_NAME_LATITUD, Database_Schema.Sites.COLUMN_NAME_LONGITUD, Database_Schema.Sites.COLUMN_NAME_COMENTARIOS, Database_Schema.Sites.COLUMN_NAME_VALORACION, Database_Schema.Sites.COLUMN_NAME_CATEGORIA};
        String sqlWhere = "latitude='" + latitud + "' and longitude='" + longitud + "'";
        String sqlOrderBy = Database_Schema.Sites.COLUMN_NAME_NOMBRE + " ASC";

        SQLiteDatabase conn = DatabaseManager.conectar(context, DatabaseManager.OPEN_MODE_READ);
        Cursor cursor = conn.query(Database_Schema.Sites.TABLE_NAME, sqlFields, sqlWhere, null, null, null, sqlOrderBy);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Long dataId = cursor.getLong(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_ID));
            String dataNombre = cursor.getString(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_NOMBRE));
            Double dataLatitud = cursor.getDouble(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_LATITUD));
            Double dataLongitud = cursor.getDouble(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_LONGITUD));
            String dataComentarios = cursor.getString(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_COMENTARIOS));
            Float dataValoracion = cursor.getFloat(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_VALORACION));
            int dataCategoria = cursor.getInt(cursor.getColumnIndex(Database_Schema.Sites.COLUMN_NAME_CATEGORIA));
            lugar = new Sites(dataId, dataNombre, dataLatitud, dataLongitud, dataComentarios, dataValoracion, dataCategoria);
        }

        cursor.close();
        DatabaseManager.desconectar(conn);
        return lugar;
    }
}
