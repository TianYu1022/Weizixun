package com.tianyu.weizixun.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CalendarActivity extends BaseActivity {

    @BindView(R.id.calendar_view)
    MaterialCalendarView calendarView;
    @BindView(R.id.btn_choose)
    Button btnChoose;
    private String newDate = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }


    @Override
    protected void initView() {
        super.initView();
        //设置
        //获取当前时间 如果没有对Calendar设置时间，将会默认是系统时间System.currentTimeMillis()，默认当前时区，默认当前语言
        Calendar mCalendar = Calendar.getInstance();
        Date time = mCalendar.getTime();
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)//把那一天作为一周的第一天
                .setMinimumDate(CalendarDay.from(2016, 4, 3))//最小的日期
                .setMaximumDate(CalendarDay.from(time))//最大到今天
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        //对calendarView进行选择监听
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //格式化时间，yyyy,MM,dd,HH,mm,ss
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();//设置显示时间格式
                simpleDateFormat.applyPattern("yyyyMMdd");//20200101
                Date _date = date.getDate();
                newDate = simpleDateFormat.format(_date);
            }
        });
    }

    @OnClick(R.id.btn_choose)
    public void onViewClicked() {
        if (newDate.isEmpty()) {
            toast("未选择日期");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("date", newDate);
        setResult(200, intent);
        finish();
    }
}