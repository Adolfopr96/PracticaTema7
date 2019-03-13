package com.example.adolfo.practicatema7;

import android.content.Context;

import com.example.adolfo.practicatema7.DatabaseManager.Sites;

import java.util.ArrayList;
import java.util.List;

public class App {
    public final static int INSERTAR = 1;
    public final static int EDITAR = 2;
    public final static int INFORMACION = 3;
    public static int SALIDAINFORMACION;
    public static int action;
    public static Sites activeSite;
    public static int SpinnerMap;
    public static List<String> getCategories(Context context) {
        List<String> list = new ArrayList<String>();
        list.add(context.getResources().getString(R.string.category1));
        list.add(context.getResources().getString(R.string.category2));
        list.add(context.getResources().getString(R.string.category3));
        list.add(context.getResources().getString(R.string.category4));
        list.add(context.getResources().getString(R.string.category5));

        return list;
    }
}
