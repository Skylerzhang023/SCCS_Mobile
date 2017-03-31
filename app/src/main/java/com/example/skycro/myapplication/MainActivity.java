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

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText account,userpassword;
    private CheckBox checkBox;
    String user,password;
    private String addr = "http://192.168.2.101:80/serlet/loginServlet";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
				if(("".equals(password)))
				{
					Toast.makeText(getApplicationContext(),"密码不能为空", Toast.LENGTH_LONG).show();
				}
            }
        });

    }


}
