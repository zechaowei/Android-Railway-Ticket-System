package com.example.railwayticketingsystem.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.railwayticketingsystem.R;
import com.example.railwayticketingsystem.entity.Passanger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketBuy3ChooiceConcat extends AppCompatActivity {


    private Button addPassanger;
    private ListView lvChooicePassanger;
    List data = new ArrayList();
    ArrayList<Passanger> selectPassanger = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_buy3_chooice_concat);

        addPassanger = findViewById(android.R.id.button1);
        lvChooicePassanger = findViewById(R.id.lvChooicePassanger);
        addPassanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///回传结果  TicketBuy3ChooiceConcat 999
                Intent intent = new Intent();
                intent.putExtra("passanger", selectPassanger);
                setResult(999, intent);
                //结束当前Activity
                finish();
            }
        });


        initData();

        //给listView设置适配器
        lvChooicePassanger.setAdapter(new MyListViewAdapter(data));

    }

    private void initData() {
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            //idCard, String type, String telphone
            data.add(new Passanger(i, "乘客" + i, rnd.nextLong() + "", "成人", rnd.nextLong() + ""));
        }

        //打开Dialog
//        new Thread
//        Handler
    }

    class MyListViewAdapter extends BaseAdapter {
        private List myData;

        public MyListViewAdapter(List myData) {
            this.myData = myData;
        }

        @Override
        public int getCount() {
            return myData == null ? 0 : myData.size();
        }

        @Override
        public Object getItem(int position) {
            return myData == null ? null : myData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            ViewHolder viewHolder;
            //没有缓存过
            if (convertView == null) {
                //使用xml渲染
                v = getLayoutInflater().inflate(R.layout.activity_ticket_buy3__chooice_list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.cbSelectPassanger = v.findViewById(R.id.cbSelectPassanger);
                viewHolder.tvName = v.findViewById(R.id.tvName);
                viewHolder.tvIdCard = v.findViewById(R.id.tvIdCard);
                viewHolder.tvTel = v.findViewById(R.id.tvTel);
                v.setTag(viewHolder);
            } else {
                v = convertView;
                viewHolder = (ViewHolder) v.getTag();
            }


            Passanger passanger = (Passanger) getItem(position);


            viewHolder.tvName.setText(passanger.getName() + "(" + passanger.getType() + ")");
            viewHolder.tvIdCard.setText(passanger.getIdCard());
            viewHolder.tvTel.setText(passanger.getTelphone());
            viewHolder.cbSelectPassanger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.cbSelectPassanger.isChecked()) {
//                        选中的乘客信息passanger
                        selectPassanger.add(passanger);
                    } else {
                        selectPassanger.remove(passanger);
                    }
                }
            });
            return v;
        }

        class ViewHolder {
            CheckBox cbSelectPassanger;
            TextView tvName;
            TextView tvIdCard;
            TextView tvTel;
        }

    }
}