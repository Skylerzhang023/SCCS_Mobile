package com.example.skycro.myapplication.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liubei on 2017/4/7.
 */

public class NetWorkUtil {

    final static String server = "http://192.168.1.67/api/json?cmd=login&ctrl=user&version=1&lang=zh_CN";

    public static String getLoginResult(String username, String password){
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(server);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json;charset=utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            // 创建JSON对象
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("user", username);
            jsonParam.put("password", password);
            OutputStream stream = urlConnection.getOutputStream();
            DataOutputStream wr = new DataOutputStream(stream);
            wr.writeBytes(jsonParam.toString());
            wr.flush();
            wr.close();
            // 获取响应代码
            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            }else{
                // 返回响应信息
                return urlConnection.getResponseMessage();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            if(urlConnection!=null)
                // 断开连接
                urlConnection.disconnect();
        }
        // 判断数据是否为空
        if (sb != null){
            // 返回结果
            return sb.toString();
        }else {
            return "请检查登录信息";
        }
    }
}
