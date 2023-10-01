package com.example.railwayticketingsystem.ticket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railwayticketingsystem.MainActivity;
import com.example.railwayticketingsystem.R;
import com.example.railwayticketingsystem.entity.Passanger;
import com.example.railwayticketingsystem.my.TicketBuy3ChooiceConcat;

import java.util.ArrayList;
import java.util.List;

public class TicketBuy3Activity extends AppCompatActivity {

    TextView tvAddPassanger;
    ListView lvTicket3Passanger;
    List<Passanger> passangers = new ArrayList<>();
    private MyListViewAdapter adapter;
    private Button btnSubmit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_ticket_buy3);
        lvTicket3Passanger = findViewById(R.id.lvTicket3Passanger);
        tvAddPassanger = findViewById(R.id.tvAddPassanger);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvAddPassanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开Activity期望另外一个Activity返回的时候 回传一个数据
                Intent intent = new Intent(TicketBuy3Activity.this, TicketBuy3ChooiceConcat.class);
                startActivityForResult(intent ,100);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketBuy3Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //给listView设置适配器
        adapter = new MyListViewAdapter(passangers);
        lvTicket3Passanger.setAdapter(adapter);
    }


    //注册接受回调数据Resultd 事件方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(data !=null){
            List<Passanger> datas = (List<Passanger>) data.getSerializableExtra("passanger");
            passangers.clear();
            passangers.addAll(datas);
            adapter.notifyDataSetChanged();//通知视图渲染
        }

        Toast.makeText(this, "resultCode: "+resultCode+"\r\nrequestCode:"+requestCode, Toast.LENGTH_SHORT).show();
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


            viewHolder.tvName.setText(passanger.getName()+"("+passanger.getType()+")");
            viewHolder.tvIdCard.setText(passanger.getIdCard());
            viewHolder.tvTel.setText(passanger.getTelphone());
            viewHolder.cbSelectPassanger.setVisibility(View.GONE);//让CheckBox隐藏
            return v;
        }

        class ViewHolder {
            CheckBox cbSelectPassanger ;
            TextView tvName ;
            TextView tvIdCard ;
            TextView tvTel ;
        }
    }
}