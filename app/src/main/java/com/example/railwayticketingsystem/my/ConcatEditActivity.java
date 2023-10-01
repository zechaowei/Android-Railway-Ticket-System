package com.example.railwayticketingsystem.my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.railwayticketingsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcatEditActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private SimpleAdapter adapter;
    List<Map<String, Object>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置ActionBar
        ActionBar actionBar = getSupportActionBar();
        //设置回退按钮
        actionBar.setDisplayHomeAsUpEnabled(true);//实际上是在菜单项目最左边显示

        setContentView(R.layout.activity_concat_edit);
        listView = findViewById(android.R.id.list);

        Intent intent = getIntent();
        HashMap<String, String> row = (HashMap) intent.getSerializableExtra("person");

        String name = row.get("nameKey");
        String idType = row.get("idType");
        String idNo = row.get("idNo");
        String type = row.get("type");
        String telNo = row.get("telNo");


        Map row1 = new HashMap();
        row1.put("key", "姓名");
        row1.put("value", name);
        row1.put("src", R.drawable.right_32);
        data.add(row1);

        Map row2 = new HashMap();
        row2.put("key", "证件类型");
        row2.put("value", idType);
        data.add(row2);


        Map row3 = new HashMap();
        row3.put("key", "证件号码");
        row3.put("value", idNo);
        data.add(row3);

        Map row4 = new HashMap();
        row4.put("key", "乘客类型");
        row4.put("value", type);
        row4.put("src", R.drawable.right_32);
        data.add(row4);

        Map row5 = new HashMap();
        row5.put("key", "电话");
        row5.put("value", telNo);
        row5.put("src", R.drawable.right_32);
        data.add(row5);


        String[] from = {"key", "value", "src"};
        int[] to = {R.id.key, R.id.value, R.id.src};
        adapter = new SimpleAdapter(this, data, R.layout.activity_concat_edit_listview_item, from, to);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    /**
     * ListView单条点击事件
     *
     * @param adapterView
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0: //修改姓名
                modifyName();
                break;
            case 3: //修改乘客类型
                modifyType();
                break;
        }
    }

    //修改乘客类型
    private void modifyType() {
        String types[] = {"成人","学生","儿童","其他"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请选择乘客类型")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Log.d("ConcatEditActivity", types[which]);
                        data.get(3).put("value",types[which]);
                        //通知ListView重新渲染
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void modifyName() {
        //创建文本框，用于输入修改后的姓名
        EditText et = new EditText(this);
        et.setSingleLine();//单行输入文本
        et.requestFocus();//获取焦点

        String name = (String) data.get(0).get("value");
        et.setText(name);   //原始的名字
        et.setSelection(0, name.length());

        Dialog d = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("请输入姓名")
//                .setMessage("确定退出吗？")
                .setView(et)    //自定义视图内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("ConcatEditActivity", "确定按钮被点击了");

                        //保存到数据库

                        //修改ListView中的数据(data)
                        data.get(0).put("value", et.getText().toString());
                        //通知ListView重新渲染
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //将资源文件(xml形式的菜单内容)渲染到menu对象中
        getMenuInflater().inflate(R.menu.my_contact_edit, menu);
        return true;
    }

    //重写方法，监听菜单的选中
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delConcat){
            //TODO 发起网络请求
            Toast.makeText(this, "删除数据",Toast.LENGTH_SHORT).show();
            finish();
        }else if (item.getItemId() == android.R.id.home){
            Toast.makeText(this, "返回上一页",Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}