package com.example.skycro.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlstr = "CREATE TABLE temp" +
                "(cuid VARCHAR(9), " +
                "ctype INTEGER, "+
                "luid VARCHAR(4), " +
                "ltype INTEGER," +
                "name VARCHAR(6)," +
                "lat DOUBLE," +
                "lng DOUBLE ," +
                "ctime LONG)";
        db.execSQL(sqlstr);
        Log.i("SQLite",sqlstr);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE temp");
        db.execSQL("CREATE TABLE temp" +
                "(cuid VARCHAR(9), " +
                "ctype INTEGER, "+
                "luid VARCHAR(4), " +
                "ltype INTEGER," +
                "name VARCHAR(6)," +
                "lat DOUBLE," +
                "lng DOUBLE ," +
                "ctime LONG)");
    }
}
