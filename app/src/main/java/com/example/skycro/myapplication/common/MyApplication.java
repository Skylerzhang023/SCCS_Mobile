package com.example.skycro.myapplication.common;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.example.skycro.myapplication.uitl.ServiceUtil;
/**
 * Created by konie on 2017/4/8.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ServiceUtil.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
