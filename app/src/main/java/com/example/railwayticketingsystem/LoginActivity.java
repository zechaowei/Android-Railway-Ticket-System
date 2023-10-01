package com.example.railwayticketingsystem;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvForgetPassword;

    private TextView etUsername;
    private TextView etPassword;
    private CheckBox autoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //设置布局文件
        setContentView(R.layout.activity_login);
        //找到按钮
        btnLogin = findViewById(R.id.btnLogin);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        autoLogin = findViewById(R.id.autoLogin);
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

                //TODO 将用户名密码发送到后端验证
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
        //设置点击忘记密码时跳转到对应网站，例如http://www.baidu.com
        tvForgetPassword.setText(Html.fromHtml("<a href=\"http://www.baidu.com\">忘记密码？</a>"));
        tvForgetPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }

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
}