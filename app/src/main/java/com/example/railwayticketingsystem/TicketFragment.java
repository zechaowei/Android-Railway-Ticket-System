package com.example.railwayticketingsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    private TextView tvStationFrom;
    private TextView tvStationTo;

    private TextView tvFromStartTime;

    private Button button1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TicketFragment", "=====onActivityCreated=====");

        tvStationFrom = getActivity().findViewById(R.id.tvStationFrom);
        tvStationTo = getActivity().findViewById(R.id.tvStationTo);
        ImageView ivWf = getActivity().findViewById(R.id.ivWf);
        button1 = getActivity().findViewById(R.id.button1);

        tvFromStartTime = getActivity().findViewById(R.id.tvFromStartTime);

        ivWf.setOnClickListener(this);
        tvFromStartTime.setOnClickListener(this);

        tvStationFrom.setOnClickListener(this);
        tvStationTo.setOnClickListener(this);

        button1.setOnClickListener(this);
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
}
