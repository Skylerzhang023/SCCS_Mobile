package com.example.skycro.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.skycro.myapplication.service.ApiService;
import com.example.skycro.myapplication.uitl.ServiceUtil;
import com.example.skycro.myapplication.uitl.ToastUtil;
import com.google.android.gms.maps.GoogleMap;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Terminal extends AppCompatActivity {

     private SQLiteDatabase db;
     private MyDBOpenHelper dbhelper;
     private Context mContext;
     ProgressBar progressView;
     EditText textViewConcentratorName;
     EditText inputName;
     EditText inputUID;
     EditText inputLong;
     EditText inputLat;
     public boolean isInternet;
    ContentValues values1;





    private GoogleMap mMap;
    int MY_PERMISSIONS_REQUEST_LOCATION;

    private static final String TAG = "EditTerminal";

    private LocationManager locationManager;
    private ApiService apiService;

    private String pid;
    private String cname;
    private String cuid,luid;
    private double lat,lon;
    Location location;
    // lat 纬度 lon 精度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);

        //find the components
        progressView = (ProgressBar) findViewById(R.id.edit_progress);
        textViewConcentratorName = (EditText) findViewById(R.id.text_concentrator_name);
        inputName = (EditText) findViewById(R.id.input_name);
        inputUID = (EditText) findViewById(R.id.input_uid);
        inputLong = (EditText) findViewById(R.id.input_long);
        inputLat = (EditText) findViewById(R.id.input_lat);
        Button btnGetLocation = (Button) findViewById(R.id.btn_get_location);
        Button btnStartScanter = (Button) findViewById(R.id.btn_start_scan_ter);
        Button btnStartScancon = (Button) findViewById(R.id.btn_start_scan_con);
        Button btnNext = (Button) findViewById(R.id.btn_next);


        //接收终端luid
        luid =getIntent().getStringExtra("luid");
        //luid = bundle.getString("luid");
        inputUID.setText(luid);

        //接受集中器cuid
        cuid =getIntent().getStringExtra("cuid");
        //Bundle bundle1 = this.getIntent().getExtras();
        //cuid = bundle1.getString("cuid");
        textViewConcentratorName.setText(cuid);



        //////////////////

        mContext = getApplicationContext();
        dbhelper = new MyDBOpenHelper(mContext, "test.db", null, 1);
        db = dbhelper.getWritableDatabase();
        values1 = new ContentValues();

        //pid = getIntent().getStringExtra("pid");
        //cname = getIntent().getStringExtra("cname");
        //cuid = getIntent().getStringExtra("cuid");
        //textViewConcentratorName.setText(cname);

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

        btnStartScanter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startscan();
            }
        });
        btnStartScancon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startscan_con();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               push();
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
    public void startscan_con(){
        Intent intent = new Intent();
        intent.setClass(Terminal.this, scan_cuid.class);
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
        //接收终端luid
        luid =getIntent().getStringExtra("luid");
        //luid = bundle.getString("luid");
        inputUID.setText(luid);

        //接受集中器cuid
        cuid =getIntent().getStringExtra("cuid");
        //Bundle bundle1 = this.getIntent().getExtras();
        //cuid = bundle1.getString("cuid");
        textViewConcentratorName.setText(cuid);
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

    private void checkNetwork() {
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            //执行相关操作
            Toast.makeText(getApplicationContext(),"网络已连接", Toast.LENGTH_LONG).show();
            isInternet = true;
        }else{
            Toast.makeText(getApplicationContext(),"网络未连接", Toast.LENGTH_LONG).show();
            isInternet = false;
        }
    }

    public void push(){
        progressView.setVisibility(View.VISIBLE);
        //String name = inputName.getText().toString();
        //String luid = inputUID.getText().toString();
        //String cuid = textViewConcentratorName.getText().toString();
        String name = "aaaa";
        String luid = "aaaa";
        String cuid = "aaaa";
        String pid = "aaaa";
        double lat = 2.111;
        double lon = 2.111;
        if(!isInternet){
            values1.put("pid",pid);
            values1.put("cuid",cuid);
            values1.put("name",name);
            values1.put("luid",luid);
            values1.put("lat",lat);
            values1.put("lon",lon);
            if(db.insert("task",null,values1)>0) {
                Toast.makeText(mContext, "插入数据完毕", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(mContext,"插入失败啦",Toast.LENGTH_SHORT).show();
        }


        if (name.isEmpty() || cuid.isEmpty() || lat == 0 || lon == 0||pid.isEmpty()||luid.isEmpty()) {
            progressView.setVisibility(View.GONE);
            ToastUtil.toast(Terminal.this, "表单信息不完整");
            ToastUtil.toast(Terminal.this, Double.toString(lat));
            ToastUtil.toast(Terminal.this, pid);
            ToastUtil.toast(Terminal.this, Double.toString(lon));
            ToastUtil.toast(Terminal.this, Double.toString(lat));
            return;
        }

        apiService.addTerminal(pid, cuid, name, luid, lat, lon, new Callback<String>() {
            @Override

            public void onResponse(Call<String> call, Response<String> response) {
                progressView.setVisibility(View.GONE);
                String res = response.body();
                //ToastUtil.toast(Terminal.this,"alala");
                int code = ServiceUtil.parseResponseForCode(res);

                if (code == 5) {
                    ToastUtil.toast(Terminal.this, "请先登录");
                    Intent intent = new Intent(Terminal.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                if (code != 1) {
                    String message = ServiceUtil.parseResponseForResult(res, String.class);
                    ToastUtil.toast(Terminal.this, message);
                    return;
                }

                ToastUtil.toast(Terminal.this, "添加成功！");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressView.setVisibility(View.GONE);
                ToastUtil.showOperationError(Terminal.this, TAG, t);
            }
        });
    }

}




