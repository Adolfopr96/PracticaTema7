package com.example.adolfo.practicatema7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    ListView lv_main;
    ImageView img1_main, img2_main;
    Intent i1, i2;
    Spinner sp_category_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        img1_main = findViewById(R.id.img1_main);
        img2_main = findViewById(R.id.img2_main);
      /*  i1 = new Intent(this, Map.class);*/
        i2 = new Intent(this, Edit_Site.class);
        img1_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.SpinnerMap = (int) (long) sp_category_main.getSelectedItemId();
                startActivity(i1);
            }
        });
        img2_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicNuevo();
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

    @Override
    protected void onResume() {
        super.onResume();
        select_all();
    }
}
