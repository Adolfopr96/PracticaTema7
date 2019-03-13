package com.example.adolfo.practicatema7;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adolfo.practicatema7.DatabaseManager.Querys_Database;

public class Edit_Site extends AppCompatActivity {
    EditText et_name, et_longitude, et_latitude, et_comments;
    RatingBar rb_site;
    Spinner sp_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__site);
        et_comments = findViewById(R.id.et_comments_site);
        et_name = findViewById(R.id.et_name_site);
        et_longitude = findViewById(R.id.et_longitude_site);
        et_latitude = findViewById(R.id.et_latitude_site);
        rb_site= findViewById(R.id.rating_site);
        sp_category = findViewById(R.id.sp_category_site);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(!et_name.getText().toString().isEmpty() && !et_longitude.getText().toString().isEmpty() && !et_latitude.toString().isEmpty() && !et_comments.toString().isEmpty())
        {
            if(App.SALIDAINFORMACION==1)
            {
                //Hacer aquí update
                App.activeSite.setNameSite(et_name.getText().toString());
                App.activeSite.setLongitudeSite(Double.parseDouble(et_longitude.getText().toString()));
                App.activeSite.setLatitudeSite(Double.parseDouble(et_latitude.getText().toString()));
                App.activeSite.setCommentSite(et_comments.getText().toString());
                App.activeSite.setRatingSite(rb_site.getRating());
                App.activeSite.setCategorySite(sp_category.getSelectedItemPosition()+1);
                Querys_Database.edit_site(this, App.activeSite );
                Toast.makeText(this, getResources().getString(R.string.toas_correct_insert)+"Edit", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(App.SALIDAINFORMACION==2)
            {
                App.activeSite.setNameSite(et_name.getText().toString());
                App.activeSite.setLatitudeSite(Double.parseDouble(et_latitude.getText().toString()));
                App.activeSite.setLongitudeSite(Double.parseDouble(et_longitude.getText().toString()));
                App.activeSite.setCommentSite(et_comments.getText().toString());
                App.activeSite.setRatingSite(rb_site.getRating());
                App.activeSite.setCategorySite(sp_category.getSelectedItemPosition()+1);
                Querys_Database.insertSite(this, App.activeSite );
                Toast.makeText(this, getResources().getString(R.string.toas_correct_insert), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return false;
    }
}
