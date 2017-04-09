package com.example.skycro.myapplication.api;


import com.example.skycro.myapplication.model.ConcentratorBean;
import com.example.skycro.myapplication.model.LoginBean;
import com.example.skycro.myapplication.model.TerminalBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by konie on 2017/4/8.
 */

public interface Api {

    @POST("api/json?cmd=login&ctrl=user&version=1&lang=zh_CN")
    Call<String> login(@Body LoginBean loginBean);

    @POST("api/json?cmd=project&ctrl=list&version=1&lang=zh_CN")
    Call<String> getProjectList(@Body String body);

    @POST("api/json?cmd=stations&ctrl=add&version=1&lang=zh_CN")
    Call<String> addConcentrator(@Body ConcentratorBean concentratorBean);

    @POST("api/json?cmd=lamps&ctrl=add&version=1&lang=zh_CN")
    Call<String> addTerminal(@Body TerminalBean terminalBean);

}
