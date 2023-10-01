package com.example.railwayticketingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置全屏模式
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        //启动画面设置为3秒
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    private void getHome() {
        //判断在SharedPreferences中存在的登录信息，则跳转到MainActivity，否则跳转到LoginActivity
        SharedPreferences sp = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        //判断Token，目前没有网络请求，暂时使用静态请求
        Intent intent = null;
        if (username != null && username.length() > 0 &&
                password != null && password.length() > 0) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        //启动Activity
        startActivity(intent);
        finish();//最后避免退回来
    }
}