package com.example.railwayticketingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.railwayticketingsystem.my.ConcatActivity;

public class MyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btnConcat;
    private Button btnAccount;
    private Button btnPassword;
    private Button btnLogout;
    private ListView lvMain;

    private ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        TextView tv = new TextView(getActivity());
//        tv.setText("车票");
//        tv.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        tv.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        tv.setGravity(Gravity.CENTER);
//        return tv;
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnLogout = getActivity().findViewById(R.id.btnLogout);
        lvMain = getActivity().findViewById(R.id.lvMain);


        //定义一个列表的显示内容
        String[] datas = new String[]{"我的联系人", "我的账户", "我的密码"};
        //准备适配器
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, datas);
        //将适配器绑定到ListView
        lvMain.setAdapter(adapter);

        //给ListView的每个条目添加点击事件
        lvMain.setOnItemClickListener(this);

        //修改退出登录页面的跳转
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
                intent = new Intent(getActivity(), ConcatActivity.class);
                break;
            case 1:
                Log.i("AccountActivity", "进入我的账户页面....");
                intent = new Intent(getActivity(), AccountActivity.class);
                break;
            case 2:
                Log.i("PasswordActivity", "进入密码页面....");
                intent = new Intent(getActivity(), PasswordActivity.class);
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
            case R.id.btnLogout:
                intent = new Intent(getActivity(), LoginActivity.class);
                logout();
                break;
        }
        //如果退出登录，需要结束当前Activity
        if (id == R.id.btnLogout)
            getActivity().finish();
        startActivity(intent);
    }


    /**
     * 退出清空配置文件
     */
    private void logout() {
        SharedPreferences sp = getActivity().getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
