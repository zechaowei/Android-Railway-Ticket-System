package com.example.railwayticketingsystem;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.railwayticketingsystem.ticket.StationActivity;
import com.example.railwayticketingsystem.ticket.TicketBuy2Activity;

import java.util.Date;

public class TicketFragment extends Fragment implements View.OnClickListener {

    int currentVersion = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    private TextView tvStationFrom;
    private TextView tvStationTo;

    private TextView tvFromStartTime;

    private TextView tvft1, tvft2;

    private Button button1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TicketFragment", "=====onActivityCreated=====");

        tvStationFrom = getActivity().findViewById(R.id.tvStationFrom);
        tvStationTo = getActivity().findViewById(R.id.tvStationTo);
        ImageView ivWf = getActivity().findViewById(R.id.ivWf);
        button1 = getActivity().findViewById(R.id.button1);
        tvft1 = getActivity().findViewById(R.id.tvft1);
        tvft2 = getActivity().findViewById(R.id.tvft2);

        tvFromStartTime = getActivity().findViewById(R.id.tvFromStartTime);


        ivWf.setOnClickListener(this);
        tvFromStartTime.setOnClickListener(this);

        tvStationFrom.setOnClickListener(this);
        tvStationTo.setOnClickListener(this);

        button1.setOnClickListener(this);

        //获取查询历史
        queryStationHistory();
    }

    private void queryStationHistory() {
        String fileName = "my_station";
        MyStationDataBaseOpenHelper helper = new MyStationDataBaseOpenHelper(getActivity(), fileName, null, currentVersion);
        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c = readDB.rawQuery("select id, station_from, station_to from station_log order by id desc", null);

        int row = 1;
        while (c.moveToNext()) {
            @SuppressLint("Range") String from = c.getString(c.getColumnIndex("station_from"));
            @SuppressLint("Range") String to = c.getString(c.getColumnIndex("station_to"));
            if (row == 1) {
                tvft1.setText(from + "---" + to);
            } else {
                tvft2.setText(from + "---" + to);
            }
            if (row >= 2) {
                break;
            }
            row++;
        }
        c.close();
        readDB.close();
        helper.close();
    }


    @Override
    public void onClick(View view) {
        //交换站点
        if (view.getId() == R.id.ivWf) {
            startTranslate();
        } else if (view.getId() == R.id.tvFromStartTime) {
            selectTime();
        } else if (view.getId() == R.id.tvStationFrom || view.getId() == R.id.tvStationTo) {
            Intent intent = new Intent(getActivity(), StationActivity.class);
//            getActivity().startActivity(intent);
            getActivity().startActivityForResult(intent, view.getId());
        } else if (view.getId() == R.id.button1) {
            Intent intent = new Intent(getActivity(), TicketBuy2Activity.class);
            getActivity().startActivity(intent);

            //将查询的出发城市、到达城市记录到SQLite数据库
            logStation();
        }
    }

    private void logStation() {
//打开数据库连接对象
//        SQLiteDatabase db = SQLiteDatabase.create(null);
//        db;//
//        int currentVersion = 1;
//        int currentVersion = 2;
        String fileName = "my_station";
        MyStationDataBaseOpenHelper helper = new MyStationDataBaseOpenHelper(getActivity(), fileName, null, currentVersion);
        SQLiteDatabase writeDB = helper.getWritableDatabase();


        //将出发城市、到达城市写入SQLite
        String from = tvStationFrom.getText().toString();
        String to = tvStationTo.getText().toString();
//        writeDB.execSQL("insert into station_log (station_from,station_to) values('"+from+"','"+to+"')");
//        writeDB.execSQL("insert into station_log (station_from,station_to) values(?,? )",   new Object[]{from ,to});

        ContentValues cv = new ContentValues();
        cv.put("station_from", from);
        cv.put("station_to", to);
        writeDB.insert("station_log", null, cv);

//        writeDB.update()
//        delete from station_log where id = ?
//        writeDB.delete("station_log"," id = ?" ,new String[]{"1"});

        writeDB.close();
        helper.close();
    }

    class MyStationDataBaseOpenHelper extends SQLiteOpenHelper {
        public MyStationDataBaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public MyStationDataBaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        public MyStationDataBaseOpenHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
            super(context, name, version, openParams);
        }

        //初始化的时候
        @Override
        public void onCreate(SQLiteDatabase db) {
            //需要创建表
            db.execSQL("CREATE TABLE \"station_log\" (\n" +
                    "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"station_from\"\tTEXT,\n" +
                    "\t\"station_to\"\tTEXT\n" +
                    ")");
        }

        //升级的时候触发
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //根据版本号的变化，不同的版本号，做一些更新、升级的操作（相对数据库）

            Log.d("SQLite", "oldVersion: " + oldVersion);
            Log.d("SQLite", "newVersion: " + oldVersion);
        }

        //降级的时候触发
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("SQLite", "oldVersion: " + oldVersion);
            Log.d("SQLite", "newVersion: " + oldVersion);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = data.getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            switch (requestCode) {
                case R.id.tvStationFrom:
                    tvStationFrom.setText(name);
                    break;
                case R.id.tvStationTo:
                    tvStationTo.setText(name);
                    break;
            }
        }
    }

    private void selectTime() {
        String srtTvFromStartTime = tvFromStartTime.getText().toString();
        //2023/10/1
        srtTvFromStartTime = srtTvFromStartTime.split(" ")[0];
        String[] time = srtTvFromStartTime.split("/");

        //选择出发时间
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                Date selectDate = new Date(year - 1900, month, dayOfMonth);
                String weekday = DateUtils.formatDateTime(getActivity(), selectDate.getTime(), DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY);
                Toast.makeText(getActivity(), "weekday:" + weekday, Toast.LENGTH_SHORT).show();
                Log.d("weekday", weekday);

                //选择日期后，调用此事件
                tvFromStartTime.setText("" + year + "/" + (month + 1) + "/" + dayOfMonth + " " + weekday);
            }
        }, Integer.parseInt(time[0]),
                Integer.parseInt(time[1]) - 1,//月份从0开始
                Integer.parseInt(time[2]));

        dialog.show();
    }

    private void startTranslate() {
        int duration = 500;
        //启动一个动画
        TranslateAnimation animationLeft = new TranslateAnimation(0, 320, 0, 0);
        //动画的速度
        animationLeft.setInterpolator(new DecelerateInterpolator());
        //动画持续的时间
        animationLeft.setDuration(duration);
        //出发城市，向右移动
        tvStationFrom.startAnimation(animationLeft);

        //启动一个动画
        TranslateAnimation animationRight = new TranslateAnimation(0, -320, 0, 0);
        //动画的速度
        animationRight.setInterpolator(new DecelerateInterpolator());
        //动画持续的时间
        animationRight.setDuration(duration);

        animationRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            //当动画执行完成，需要交换出发城市、到达城市
            @Override
            public void onAnimationEnd(Animation animation) {
                CharSequence temp = tvStationFrom.getText();
                tvStationFrom.setText(tvStationTo.getText());
                tvStationTo.setText(temp);
            }
        });

        //到达城市，向左移动
        tvStationTo.startAnimation(animationRight);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TicketFragment", "=======onResume=========");
        queryStationHistory();
    }
}