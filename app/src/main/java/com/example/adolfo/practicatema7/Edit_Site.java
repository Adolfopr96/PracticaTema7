package com.example.adolfo.practicatema7;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adolfo.practicatema7.DatabaseManager.Querys_Database;

import java.util.ArrayList;
import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static com.example.adolfo.practicatema7.DatabaseManager.Querys_Database.*;
import static com.example.adolfo.practicatema7.R.*;
import static com.example.adolfo.practicatema7.R.id.*;

public class Edit_Site extends AppCompatActivity implements  LocationListener {
    EditText et_name, et_longitude, et_latitude, et_comments;
    ImageView img_map_edit;
    RatingBar rb_site;
    Spinner sp_category;

    final String TAG = "GPS";

    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    LocationManager locationManager;
    Location loc;

    ArrayList permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();

    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation;

    {
        canGetLocation = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_edit__site);
        et_comments = findViewById(et_comments_site);
        et_name = findViewById(et_name_site);
        et_longitude = findViewById(et_longitude_site);
        et_latitude = findViewById(et_latitude_site);
        rb_site = findViewById(rating_site);
        sp_category = findViewById(sp_category_site);
        img_map_edit = findViewById(imageView2);

        List<String> list = App.getCategories(this);
        final int listsize = list.size();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return (listsize); // Truncate the list
            }
        };

        img_map_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGPS && !isNetwork) {
                    Log.i(TAG, "Conexión OFF");
                    showSettingsAlert();
                    getLastLocation();
                } else {
                    Log.i(TAG, "Conexión ON");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionsToRequest.size() > 0) {
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                            canGetLocation = false;
                        }
                    }
                    getLocation();
                }
            }
        });
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(dataAdapter);


        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (App.SALIDAINFORMACION == 1) {
            et_name.setText(App.activeSite.getNameSite());
            sp_category.setSelection(App.activeSite.getCategorySite() - 1);
            et_longitude.setText(App.activeSite.getLongitudeSite().toString());
            et_latitude.setText(App.activeSite.getLatitudeSite().toString());
            rb_site.setRating(App.activeSite.getRatingSite());
            et_comments.setText(App.activeSite.getCommentSite());
        }


    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (!et_name.getText().toString().isEmpty() && !et_longitude.getText().toString().isEmpty() && !et_latitude.toString().isEmpty() && !et_comments.toString().isEmpty()) {
            if (App.SALIDAINFORMACION == 1) {
                //Hacer aquí update
                App.activeSite.setNameSite(et_name.getText().toString());
                App.activeSite.setLongitudeSite(Double.parseDouble(et_longitude.getText().toString()));
                App.activeSite.setLatitudeSite(Double.parseDouble(et_latitude.getText().toString()));
                App.activeSite.setCommentSite(et_comments.getText().toString());
                App.activeSite.setRatingSite(rb_site.getRating());
                App.activeSite.setCategorySite(sp_category.getSelectedItemPosition() - 1);
                Querys_Database.edit_site(this, App.activeSite);
                Toast.makeText(this, getResources().getString(string.toas_correct_insert) + "Edit", Toast.LENGTH_SHORT).show();
                finish();
            } else if (App.SALIDAINFORMACION == 2) {
                App.activeSite.setNameSite(et_name.getText().toString());
                App.activeSite.setLatitudeSite(Double.parseDouble(et_latitude.getText().toString()));
                App.activeSite.setLongitudeSite(Double.parseDouble(et_longitude.getText().toString()));
                App.activeSite.setCommentSite(et_comments.getText().toString());
                App.activeSite.setRatingSite(rb_site.getRating());
                App.activeSite.setCategorySite(sp_category.getSelectedItemPosition() + 1);
                insertSite(this, App.activeSite);
                Toast.makeText(this, getResources().getString(string.toas_correct_insert), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                if (isGPS) { // recibiendo señal desde GPS_PROVIDER
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) { // recibiendo señal desde NETWORK_PROVIDER
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("Se requiere permisos para ejecutar la aplicación.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("El GPS no está activado");
        alertDialog.setMessage("¿Quieres activar el GPS?");
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        et_latitude.setText(Double.toString(loc.getLatitude()));
        et_longitude.setText(Double.toString(loc.getLongitude()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}