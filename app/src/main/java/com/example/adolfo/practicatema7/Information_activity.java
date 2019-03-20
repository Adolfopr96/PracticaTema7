package com.example.adolfo.practicatema7;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adolfo.practicatema7.DatabaseManager.DatabaseManager;
import com.example.adolfo.practicatema7.DatabaseManager.Database_Schema;

import java.util.List;

public class Information_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_activity);
        TextView txt1 = findViewById(R.id.et_name_information);
        TextView txt2 = findViewById(R.id.et_category_information);
        TextView txt3 = findViewById(R.id.et_longitude_information);
        TextView txt4 = findViewById(R.id.et_latitude_information);
        TextView txt5 = findViewById(R.id.et_comments_information);
        RatingBar rb = findViewById(R.id.rb_information);
        if(App.activeSite!=null)
        {
            txt2.setText(App.getCategories(this).get(App.activeSite.getCategorySite()-1));
            txt1.setText(App.activeSite.getNameSite());
            txt3.setText(App.activeSite.getLongitudeSite().toString());
            txt4.setText(App.activeSite.getLatitudeSite().toString());
            txt5.setText(App.activeSite.getCommentSite());
            rb.setRating(App.activeSite.getRatingSite());
        }
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.information_menu, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;
    }
    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.opcion1:
                App.SALIDAINFORMACION=1;
                startActivity(new Intent(getApplicationContext(), Edit_Site.class));
                finish();
                break;
            case R.id.opcion2:
                confirmacion();
                break;
        }

        return false;
    }
    private void confirmacion()
    {
        new AlertDialog.Builder(this)
                .setTitle("Borrar datos")
                .setMessage("¿Está seguro de borrar los datos?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Borrar aquí todos estos datos
                        borrarBD();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Principal.class));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void borrarBD()
    {
        DatabaseManager db = new DatabaseManager(this);
        SQLiteDatabase conn = db.getWritableDatabase();
        String sqlWhere = Database_Schema.Sites.COLUMN_NAME_ID + " LIKE '" + App.activeSite.getIdSite() + "'";
        conn.delete(Database_Schema.Sites.TABLE_NAME, sqlWhere, null);
        conn.close();
    }
}
