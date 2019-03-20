package com.example.adolfo.practicatema7;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.adolfo.practicatema7.DatabaseManager.Querys_Database;
import com.example.adolfo.practicatema7.DatabaseManager.Sites;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    LatLng nuevaPosicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void mostrarTodo() {

        float colorMarcador[] = {0.0f, 0.0f, 210.0f, 240.0f,180.0f, 120.0f};

        Log.i("MyApp" , "Color: " + App.sp_map);
        if(App.sp_map==0)
        {
            List<Sites> lstLugar = Querys_Database.select_site(this);
            if (lstLugar == null)
            {

            }
            else {
                for (Sites p : lstLugar) {
                    Log.i("MyApp", p.toString());
                    nuevaPosicion = new LatLng(p.getLatitudeSite(), p.getLongitudeSite());
                    mMap.addMarker(new MarkerOptions().position(nuevaPosicion).snippet("" +p.getLongitudeSite() + "_" + p.getLatitudeSite()).title(p.getNameSite()).icon(BitmapDescriptorFactory.defaultMarker(colorMarcador[p.getCategorySite()])));
                }
            }
        }
        else
        {
            List<Sites> lstLugar = Querys_Database.select_site(this);
            if (lstLugar == null) {
            } else {
                for (Sites p : lstLugar) {
                    nuevaPosicion = new LatLng(p.getLatitudeSite(), p.getLongitudeSite());
                    mMap.addMarker(new MarkerOptions().position(nuevaPosicion).snippet("" +p.getLongitudeSite() + "_" + p.getLatitudeSite()).title(p.getNameSite()).icon(BitmapDescriptorFactory.defaultMarker(colorMarcador[p.getCategorySite()])));
                }
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mostrarTodo();
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        String cadena = marker.getSnippet();
        String[] separated = cadena.split("_");
        String longitud = separated[0];
        String latitud = separated[1];

        Log.i("MyaPP" , "Buscando Long: " + longitud + " Lat: " + latitud);
        Sites l = Querys_Database.getLugar(this, latitud, longitud);
        if(l==null) {
            Log.i("MyaPP" , "Lugar NO ENCONTRADO");
        } else {
            App.activeSite =l;
            Log.i("MyaPP" , "Lugar obtenido: " + App.activeSite.toString());
        }


        startActivity(new Intent(getApplicationContext(), Information_activity.class));
        return false;

    }
}