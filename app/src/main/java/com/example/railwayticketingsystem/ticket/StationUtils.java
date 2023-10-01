package com.example.railwayticketingsystem.ticket;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StationUtils {
    public static final String DB_NAME = "station.db";
    public static final String DB_PATH = "/data/data/com.example.railwayticketingsystem/databases";

    private static void init(Context ctx) {
        // 转移数据文件
        try {
            // 创建目录
            File file = new File(DB_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }

            // 判断station.db是否存在
            File file2 = new File(DB_PATH + "/" + DB_NAME);

            if (!file2.exists()) {
                // 读
                InputStream is = ctx.getAssets().open(DB_NAME);
                BufferedInputStream bi = new BufferedInputStream(is);

                // 写
                FileOutputStream fo = new FileOutputStream(DB_PATH + "/"
                        + DB_NAME);
                BufferedOutputStream bo = new BufferedOutputStream(fo);

                // 读写
                byte[] buff = new byte[1024];
                int len = 0;
                while ((len = bi.read(buff)) != -1) {
                    bo.write(buff, 0, len);
                }

                // 关闭
                bi.close();
                is.close();
                bo.close();
                fo.close();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ArrayList<Station> getAllStations(Context ctx) {
        init(ctx);

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/"
                + DB_NAME, null);
        ArrayList<Station> stations = new ArrayList<Station>();

        Cursor c = db.rawQuery("SELECT * FROM station ORDER BY sort_order",
                null);

        while (c.moveToNext()) {
            Station station = new Station();
            int index = c.getColumnIndex("station_name");
            station.setStation_name(c.getString(index));
            index = c.getColumnIndex("sort_order");
            String tmp = c.getString(index);
            if (TextUtils.isEmpty(tmp)) {
                station.setSort_order("热门");
            } else {
                station.setSort_order(tmp);
            }
            stations.add(station);
        }

        c.close();
        db.close();

        return stations;
    }

    public static Station getStationFromCity(Context ctx, String city) {
        init(ctx);

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/"
                + DB_NAME, null);
        Station station = null;

        Cursor c = db
                .rawQuery(
                        "SELECT * FROM station WHERE city=? ORDER BY sort_order limit 1",
                        new String[] { city });

        if (c.moveToNext()) {
            station = new Station();
            int index = c.getColumnIndex("station_name");
            station.setStation_name(c.getString(index));
        }

        c.close();
        db.close();

        return station;
    }

}
