package com.example.adolfo.practicatema7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.adolfo.practicatema7.DatabaseManager.Querys_Database;
import com.example.adolfo.practicatema7.DatabaseManager.Sites;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private static List<Sites> all_sites;
    public  ListView lv_main;
    ImageView img1_main, img2_main;
    Intent i1, i2;
    Spinner sp_category_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        sp_category_main=findViewById(R.id.sp_category_main);
        img1_main = findViewById(R.id.img1_main);
        img2_main = findViewById(R.id.img2_main);
        i1 = new Intent(this, MapsActivity.class);
        i2 = new Intent(this, Edit_Site.class);
        final List<String> list = new ArrayList<String>();
        list.add(getResources().getString(R.string.default_category));
        list.add(getResources().getString(R.string.category1));
        list.add(getResources().getString(R.string.category2));
        list.add(getResources().getString(R.string.category3));
        list.add(getResources().getString(R.string.category4));
        list.add(getResources().getString(R.string.category5));

        lv_main = findViewById(R.id.lv_cards_main);
        lv_main.setClickable(true);
        lv_main.addHeaderView(new View(this)); // añade espacio arriba de la primera card
        lv_main.addFooterView(new View(this)); // añade espacio debajo de la última card
        lv_main.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        Log.i("APPV1","AQUIIII");
                        App.activeSite = all_sites.get(position-1);
                        App.action = App.INFORMACION;
                        startActivity(new Intent(getApplicationContext(), Information_activity.class));
                    }

                }
        );


        final int listsize = list.size();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return(listsize); // Truncate the list
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category_main.setAdapter(dataAdapter);
        Querys_Database.query1="";
//      select_all();

        img1_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp_category_main.getSelectedItem().toString() == getResources().getString(R.string.default_category))
                {
                    Querys_Database.query1="";
                    App.sp_map = (int) (long) sp_category_main.getSelectedItemId();
                    startActivity(i1);
                }
                else
                {
                    Querys_Database.query1="category="+sp_category_main.getSelectedItemPosition();
                    App.sp_map = (int) (long) sp_category_main.getSelectedItemId();
                    startActivity(i1);
                }

            }
        });
        img2_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicNuevo();
            }
        });

        sp_category_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sp_category_main.getSelectedItem().toString() == getResources().getString(R.string.default_category))
                {
                    Querys_Database.query1="";
                    select_all();
                }
                else
                {
                    Querys_Database.query1="category="+sp_category_main.getSelectedItemPosition();
                    select_all();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void clicNuevo() {
        App.activeSite = new Sites();
        App.action = App.INSERTAR;
        App.SALIDAINFORMACION=2 ;
        startActivity(i2);
    }
    public void select_all()
    {
        CardAdapter ca_main = new CardAdapter(getApplicationContext(), R.layout.list_item_card);
        all_sites = Querys_Database.select_site(this);
        if (all_sites == null) {
            lv_main.setAdapter(null);
        } else {
            for (Sites s : all_sites) {
                ca_main.add(s);
            }
            lv_main.setAdapter(ca_main);
        }
    }
    public void delete_all()
    {
        all_sites = Querys_Database.select_site(this);
                App.sp_map = (int) (long) sp_category_main.getSelectedItemId();
                startActivity(i1);
        for (Sites s : all_sites)
        {
            Querys_Database.deleteSite(this,s);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(sp_category_main.getSelectedItem().toString() == getResources().getString(R.string.default_category))
        {
            Querys_Database.query1="";
            select_all();
        }
        else
        {
            Querys_Database.query1="category="+sp_category_main.getSelectedItemPosition();
            select_all();
        }
    }
}
