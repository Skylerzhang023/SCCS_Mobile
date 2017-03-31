package com.example.skycro.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.RadioButton;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText account,userpassword;
    private CheckBox checkBox;
    String user,password,param;
    private URL posturl;
    private String address = "http://121.40.34.92:7070/web/api/json?cmd=login&ctrl=user&version=1&1lang=zh_CN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the ites registered
        login = (Button) findViewById(R.id.button);
        account = (EditText) findViewById(R.id.account);
        userpassword = (EditText) findViewById(R.id.userpassword);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        //重写点击事件的处理方法onClick()
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                user = account.getText().toString();
                password = userpassword.getText().toString();
                //check if user and password is blank
                if(("".equals(user)))
				{
					Toast.makeText(getApplicationContext(),"用户名不能为空", Toast.LENGTH_LONG).show();
				}
				else if(("".equals(password)))
				{
					Toast.makeText(getApplicationContext(),"密码不能为空", Toast.LENGTH_LONG).show();
				}
				else{
                    String params = "user"+":"+ user + "password" + "+" + password;
                    //set the url
                    try {
                        posturl = new URL(address);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        URLConnection rulConnection = posturl.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
            }
        });

    }


}
