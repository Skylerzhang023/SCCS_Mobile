package com.example.skycro.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * Created by Naoki on 2017/4/7.
 */

public class InsertActivity extends AppCompatActivity {



    //======================
    private Context mContext;
    private SQLiteDatabase db;
    private MyDBOpenHelper dbhelper;

    //======================




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //=====================
        mContext = getApplicationContext();
        dbhelper = new MyDBOpenHelper(mContext, "wireless.db", null, 1);
        db = dbhelper.getWritableDatabase();
        //=====================



    }

    private int insertdata(){
        ContentValues values = new ContentValues();
        if(db.insert("task",null,values)>0) {
            Toast.makeText(mContext, "insert done", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(mContext,"insert failed",Toast.LENGTH_SHORT).show();
        return 0;
    }
}
