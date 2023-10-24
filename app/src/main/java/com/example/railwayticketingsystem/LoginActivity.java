package com.example.railwayticketingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;

    private Button btnRegister;
    private TextView tvForgetPassword;

    private TextView etUsername;
    private TextView etPassword;
    private CheckBox autoLogin;

    private TextView tvWelcome;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置布局文件
        setContentView(R.layout.activity_login);
        //找到按钮
        btnLogin = findViewById(R.id.btnLogin);
        //注册按钮
        btnRegister = findViewById(R.id.bntRegister);

        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        autoLogin = findViewById(R.id.autoLogin);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.requestFocus();

        //点击其他组件时，保持跑马灯不变
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    tvWelcome.requestFocus();
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    tvWelcome.requestFocus();
                }
            }
        });

        //给按钮添加点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LoginActivity", "按钮被点击了，准备登录....");

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username == null || "".equals(username)) {
                    etUsername.setError("用户名不能为空");
                    return;
                }
                if (password == null || "".equals(password)) {
                    etPassword.setError("密码不能为空");
                    return;
                }

//                login();

                //TODO 将用户名密码发送到 后端验证
                //admin 123456
                if (!"admin".equals(username) || !"123456".equals(password)) {
                    etUsername.setError("用户名或密码不正确");
                    return;
                }

                //保存自动登录信息
                saveAutoLogin();

                //从当前Activity跳转到MainActivity页面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //关闭当前Activity
                finish();
                //使用意图启动Activity
                startActivity(intent);
            }
        });

        //注册页面
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Toast.makeText(LoginActivity.this, "跳转到注册页面",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        //设置点击忘记密码时跳转到对应网站，例如http://www.baidu.com
        tvForgetPassword.setText(Html.fromHtml("<a href=\"http://www.baidu.com\">忘记密码？</a>"));
        tvForgetPassword.setMovementMethod(LinkMovementMethod.getInstance());


    }


    //用于网络登陆
//    private void login() {
//        String username = etUsername.getText().toString();
//        String password = etPassword.getText().toString();
//
//        new Thread(){
//            @Override
//            public void run() {
//
//                try {
//                    URL url = new URL("http://192.168.126.105:3408/android-server/loginXml?username=admin&password=123456");
//                    URLConnection urlConnection;
//                } catch (MalformedURLException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }.start();
//    }

    //自动登录保存
    private void saveAutoLogin() {
        if (autoLogin.isChecked()) {
            Log.d("LoginActivity", "自动登录");
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            SharedPreferences sp = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", username);
            editor.putString("password", password);

            editor.apply();//将数据写入到文件中
        } else {
            Log.d("LoginActivity", "非自动登录");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回跑马灯页面时，保证跑马灯继续运行
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setSelected(true);
    }
}