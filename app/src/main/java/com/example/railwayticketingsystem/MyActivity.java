package com.example.railwayticketingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.railwayticketingsystem.my.ConcatActivity;

public class MyActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btnConcat;
    private Button btnAccount;
    private Button btnPassword;
    private Button btnLogout;
    private ListView lvMain;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

//        btnConcat = findViewById(R.id.btnConcat);
//        btnAccount = findViewById(R.id.btnAccount);
//        btnPassword = findViewById(R.id.btnPassword);
        btnLogout = findViewById(R.id.btnLogout);
        lvMain = findViewById(R.id.lvMain);

//        btnConcat.setOnClickListener(new BtnOnClickListener());
//        btnAccount.setOnClickListener(new BtnOnClickListener());
//        btnPassword.setOnClickListener(new BtnOnClickListener());

        //定义一个列表的显示内容
        String[] datas = new String[]{"我的联系人", "我的账户", "我的密码"};
        //准备适配器
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datas);
        //将适配器绑定到ListView
        lvMain.setAdapter(adapter);

        //给ListView的每个条目添加点击事件
        lvMain.setOnItemClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    //ListView条目点击的事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d("MainActivity", "点击的position  " + position + "号");
        Intent intent = null;
        switch (position) {
            case 0:
                Log.i("ConcatActivity", "进入联系人页面....");
                intent = new Intent(MyActivity.this, ConcatActivity.class);
                break;
            case 1:
                Log.i("AccountActivity", "进入我的账户页面....");
                intent = new Intent(MyActivity.this, AccountActivity.class);
                break;
            case 2:
                Log.i("PasswordActivity", "进入密码页面....");
                intent = new Intent(MyActivity.this, PasswordActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        //根据组件获取id
        int id = view.getId();
        //根据按钮确定意图
        Intent intent = null;
        switch (id) {
//                case R.id.btnConcat:
//                    Log.i("ConcatActivity","进入联系人页面....");
//                    intent = new Intent(MainActivity.this, ConcatActivity.class);
//                    break;
//                case R.id.btnAccount:
//                    Log.i("AccountActivity","进入我的账户页面....");
//                    intent = new Intent(MainActivity.this, AccountActivity.class);
//                    break;
//                case R.id.btnPassword:
//                    Log.i("PasswordActivity","进入密码页面....");
//                    intent = new Intent(MainActivity.this, PasswordActivity.class);
//                    break;
            case R.id.btnLogout:
                intent = new Intent(MyActivity.this, LoginActivity.class);
                logout();
                break;
        }
        //如果退出登录，需要结束当前Activity
        if (id == R.id.btnLogout) finish();
        startActivity(intent);
    }

    /**
     * 退出清空配置文件
     */
    private void logout() {
        SharedPreferences sp = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}