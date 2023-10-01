package com.example.railwayticketingsystem.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railwayticketingsystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StationActivity extends AppCompatActivity {
    private BaseAdapter adapter;
    private ListView mStationListView;
    private TextView overlay;
    private LetterListView letterListView;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;
    private Handler handler;
    private OverlayThread overlayThread;
    private ArrayList<Station> stations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_station);
        mStationListView = findViewById(R.id.station_list);
        LetterListView letterListView = findViewById(R.id.letterListView);
        //当字母有变化了，监听一下
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());

        //车站的数据
        stations = StationUtils.getAllStations(this);


        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();

        initOverlay();


        //显示一堆的车站
//        if (stations != null) {
        adapter = new ListAdapter(this, stations);
        mStationListView.setAdapter(adapter);
//        }


        mStationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = stations.get(position);
                String name = station.getStation_name();
                Toast.makeText(StationActivity.this, "name: "+name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("name",name);
                setResult(300,intent);
                finish();
            }
        });

    }


    //点击右侧的字母，自动动定位到左侧  字母开始的车站
    private class LetterListViewListener implements
            LetterListView.OnTouchingLetterChangedListener {

        //选中的字母
        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                mStationListView.setSelection(position);//定位字母对应的开始车站的位置
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                handler.postDelayed(overlayThread, 1500);
            }
        }
    }


    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Station> list;

        public ListAdapter(Context context, List<Station> list) {

            this.inflater = LayoutInflater.from(context);
            this.list = list;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = list.get(i).getSort_order();
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? list.get(i - 1)
                        .getSort_order() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = list.get(i).getSort_order();
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater
                        .inflate(R.layout.station_list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(list.get(position).getStation_name());
            String currentStr = list.get(position).getSort_order();
            String previewStr = (position - 1) >= 0 ? list.get(position - 1) .getSort_order() : " ";
            if (!previewStr.equals(currentStr)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha;
            TextView name;
        }

    }

    // 初始化汉语拼音首字母弹出提示框(TextView)
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.station_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);

    }

    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            intent.putExtra("name", "");
            setResult(200, intent);

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}