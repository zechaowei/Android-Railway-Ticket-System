package com.example.railwayticketingsystem.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.railwayticketingsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ConcatActivity extends AppCompatActivity {
    private ListView listView;
    //    private ArrayAdapter adapter;
    private SimpleAdapter adapter;

    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat);

        listView = findViewById(R.id.list);

//        String[] datas = new String[]{
//                "陈伟霆",
//                "谢霆锋",
//                "刘德华",
//                "张学友"
//        };

        data = new ArrayList();
        Random rnd = new Random();

        for (int i = 0; i < 20; i++) {
            Map row = new HashMap();

            String type = rnd.nextInt() % 2 == 0 ? "成人" : "学生";
            String name = "张三" + i;
            String idType = "身份证";
            String idNo = "" + rnd.nextLong();
            String telNo = "" + rnd.nextLong();
            row.put("nameKey", name);
            row.put("idType", idType);
            row.put("idNo", idNo);
            row.put("telNo", telNo);

            row.put("name", "张三" + i + "(" + type + ")");
            row.put("idCard", idType + "(" + idNo + ")");
            row.put("tel", "电话" + telNo);
            row.put("type", type);

            data.add(row);
        }


        String[] from = {"name", "idCard", "tel"};
        int[] to = {R.id.tvName, R.id.tvIdCard, R.id.tvTel};
        adapter = new SimpleAdapter(this,
                data,
                R.layout.activity_concat_listvie_item,
                from,
                to
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ConcatActivity.this, ConcatEditActivity.class);

                //获取点击的行的数据
                //position是点击ListView的位置
                HashMap row = (HashMap) data.get(position);
                intent.putExtra("person", row);

                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);
    }
}