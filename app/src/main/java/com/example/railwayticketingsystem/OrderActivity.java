package com.example.railwayticketingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class OrderActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunnable;
    private int countdown = 10; // 初始倒计时时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ImageView qrCode = findViewById(R.id.qrCode);

        Bitmap bitmap = createQRCodeImage("请支付100元", 280, 280);
        qrCode.setImageBitmap(bitmap);  //将内存中的数据渲染到ImageView中

        final TextView countdownTimer = findViewById(R.id.countdown_timer);

        // 更新倒计时时间和提示文本
        updateCountdownText(countdownTimer);

        // 在这里开始计时
        startTimer(countdownTimer);
    }

    public Bitmap createQRCodeImage(String txt, int w, int h) {
        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (txt == null || "".equals(txt) || txt.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(txt, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = 0xff000000;
                    } else {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            //显示到我们的ImageView上面
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private void startTimer(final TextView countdownTimer) {
        // 设置计时器，1秒执行一次，重复计时
        mRunnable = new Runnable() {
            @Override
            public void run() {
                countdown--;
                if (countdown >= 0) {
                    // 更新倒计时时间和提示文本
                    updateCountdownText(countdownTimer);
                    mHandler.postDelayed(this, 1000); // 1000毫秒 = 1秒
                } else {
                    // 倒计时结束，跳转到 MainActivity
                    Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 关闭 OrderActivity
                    // 显示 Toast 提示
                    Toast.makeText(OrderActivity.this, "订单支付超时", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // 第一次启动计时器
        mHandler.postDelayed(mRunnable, 1000); // 1000毫秒 = 1秒
    }

    @Override
    public void onUserInteraction() {
        // 用户与界面进行交互时，重置计时器
        mHandler.removeCallbacks(mRunnable); // 移除之前的计时任务
        countdown = 10; // 重置倒计时时间
        final TextView countdownTimer = findViewById(R.id.countdown_timer);
        // 更新倒计时时间和提示文本
        updateCountdownText(countdownTimer);
        startTimer(countdownTimer); // 重新启动计时器
    }

    private void updateCountdownText(TextView countdownTimer) {
        countdownTimer.setText("请在" + countdown + "秒内完成订单");
    }

}