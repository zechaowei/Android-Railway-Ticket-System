package com.example.railwayticketingsystem.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railwayticketingsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketBuy2Activity extends AppCompatActivity {

    ListView lvSeat;

    private MyListViewAdapter adapter;
    private ArrayList<Map<String, Object>> data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_ticket_buy2);


        lvSeat = findViewById(R.id.lvSeat);

        data = new ArrayList<Map<String,Object>>();
        Map<String,Object> item = new HashMap<>();
        item.put("name","无座");
        item.put("num","39张");
        item.put("price","￥158.0");
        data.add(item);

        item = new HashMap<>();
        item.put("name","硬座");
        item.put("num","3张");
        item.put("price","￥258.0");
        data.add(item);

        item = new HashMap<>();
        item.put("name","软卧");
        item.put("num","2张");
        item.put("price","￥368.0");
        data.add(item);

        adapter = new MyListViewAdapter(data);

        //lvSeat 设置适配器
        lvSeat.setAdapter(adapter);
    }


    //自定义适配器
    class MyListViewAdapter extends BaseAdapter {

        List<Map<String,Object>> myData ;
        public MyListViewAdapter(List<Map<String,Object>> data) {
            this.myData = data;
        }

        //通知ListView应该显示几条数据
        @Override
        public int getCount() {
            return myData.size();
        }

        //获取ListVIew中每一行的数据
        @Override
        public Object getItem(int position) {
            return myData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        /**
         * 用于渲染每一行的 视图
         * @param position  第几行
         * @param convertView : 上一次缓存的 视图
         * @param parent  ListView
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;  //单行的组件
            ViewHolder viewHolder;
            if(convertView ==  null) {
                //使用xml渲染一个组件
                v = getLayoutInflater().inflate(R.layout.activity_ticket_buy2_list_item, parent, false);
                //当前的View 指的是 LinearLayout
                viewHolder = new ViewHolder();
                LinearLayout ll = (LinearLayout) v;
                viewHolder.tvSeatName = ll.findViewById(R.id.tvSeatName);
                viewHolder.tvSeatNum = ll.findViewById(R.id.tvSeatNum);
                viewHolder.tvSeatPrice = ll.findViewById(R.id.tvSeatPrice);
                viewHolder.btnBootTicket = ll.findViewById(R.id.btnBootTicket);
                v.setTag(viewHolder);

            }else{
                v = convertView;
                viewHolder = (ViewHolder) v.getTag();
            }

            //在当前行组件内查找
            TextView tvSeatName = viewHolder.tvSeatName;
            TextView tvSeatNum = viewHolder.tvSeatNum ;
            TextView tvSeatPrice  = viewHolder.tvSeatPrice;
            Button btnBootTicket  =viewHolder.btnBootTicket;

            //获取单行数据
            HashMap<String,String> rowData = (HashMap) getItem(position);
            String seatName = rowData.get("name");
            tvSeatName.setText(seatName);
            tvSeatNum.setText(rowData.get("num"));
            tvSeatPrice.setText(rowData.get("price"));
            btnBootTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TicketBuy2Activity.this, "seatName: "+seatName, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TicketBuy2Activity.this,TicketBuy3Activity.class);
                    startActivity(intent);
                }
            });
            return v;
        }

        class ViewHolder{
            TextView tvSeatName;
            TextView tvSeatNum ;
            TextView tvSeatPrice ;
            Button btnBootTicket ;
        }
    }
}