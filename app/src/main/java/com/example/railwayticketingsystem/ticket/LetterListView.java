package com.example.railwayticketingsystem.ticket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.nio.file.attribute.AttributeView;

public class LetterListView  extends View {

    OnTouchingLetterChangedListener listener;
    //需要绘制的26个字母
    String[] b = { "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z" };
    Paint paint = new Paint();
    int choose = -1;
    boolean showBkg;


    public LetterListView(Context context) {
        super(context);
    }

    public LetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);//防止锯齿
            paint.setTextSize(30);

            //如果是选中的位置，则突出显示
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);//画字母的
            paint.reset();
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
//        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if(oldChoose != c){
                    if (c >= 0 && c < b.length) {
                        choose = c;
                        invalidate();//让整个canvas绘制的View失效  重新布局 ，重新绘制
                    }
                }
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:

                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }



    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.listener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}