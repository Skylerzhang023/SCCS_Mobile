package com.example.skycro.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.skycro.myapplication.service.ApiService;
import com.example.skycro.myapplication.uitl.ToastUtil;
import com.google.android.gms.maps.GoogleMap;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

public class Terminal extends AppCompatActivity {

     ProgressBar progressView;
     TextView textViewConcentratorName;
     EditText inputName;
     EditText inputUID;
     EditText inputLong;
     EditText inputLat;

    private GoogleMap mMap;
    int MY_PERMISSIONS_REQUEST_LOCATION;

    private static final String TAG = "EditTerminal";

    private LocationManager locationManager;
    private ApiService apiService;

    private String pid;
    private String cname;
    private String cuid;
    private double lat,lon;
    Location location;
    // lat 纬度 lon 精度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);

        //find the components
        progressView = (ProgressBar) findViewById(R.id.edit_progress);
        textViewConcentratorName = (TextView) findViewById(R.id.text_concentrator_name);
        inputName = (EditText) findViewById(R.id.input_name);
        inputUID = (EditText) findViewById(R.id.input_uid);
        inputLong = (EditText) findViewById(R.id.input_long);
        inputLat = (EditText) findViewById(R.id.input_lat);
        Button btnGetLocation = (Button) findViewById(R.id.btn_get_location);
        Button btnStartScan = (Button) findViewById(R.id.btn_start_scan);
        Button btnNext = (Button) findViewById(R.id.btn_next);

        //请求服务

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        LocationConfiguration configuration = new LocationConfiguration.Builder()
                .keepTracking(false)
                .useDefaultProviders(new DefaultProviderConfiguration.Builder()
                        .build())
                .build();

        locationManager = new LocationManager.Builder(getApplicationContext())
                .activity(this)
                .configuration(configuration)
                .notify(new LocationBaseActivity() {
                    @Override
                    public LocationConfiguration getLocationConfiguration() {
                        return null;
                    }

                    @Override
                    public void onLocationChanged(Location location) {
                        handleLocationChange(location);
                    }

                    @Override
                    public void onLocationFailed(int type) {
                        ToastUtil.toast(Terminal.this, "定位失败");
                    }
                })
                .build();


        locationManager.get();
        ToastUtil.toast(Terminal.this, "定位中");



        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getlocation();
                onMapReady();
                inputLat.setText(Double.toString(lat));
                inputLong.setText(Double.toString(lon));
            }
        });

        btnStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startscan();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // push();
            }
        });
    }

    public void startscan(){
        Intent intent = new Intent();
        intent.setClass(Terminal.this, scan.class);
        startActivity(intent);
        //如果不关闭当前的会出现好多个页面
        Terminal.this.finish();
    }
    private void handleLocationChange(Location location) {
        //btnGetLocation.setText("定位");
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        inputLat.setText("" + lat);
        inputLong.setText("" + lon);
    }

    public void onMapReady() {
        android.location.LocationManager locationManager = (android.location.LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION);
        Location location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
        lat = location.getLatitude();     //经度
        lon = location.getLongitude(); //纬度
        double altitude =  location.getAltitude();     //海拔
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(latitude, longitude);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("I'm HERE!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

}




