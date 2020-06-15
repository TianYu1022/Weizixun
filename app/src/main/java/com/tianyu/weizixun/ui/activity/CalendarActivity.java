package com.tianyu.weizixun.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarActivity extends BaseActivity {

    @BindView(R.id.calendar_view)
    MaterialCalendarView calendarView;
    @BindView(R.id.btn_choose)
    Button btnChoose;
    private String newDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initView() {
        super.initView();
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
        Intent intent = new Intent();
        intent.putExtra("date", newDate);
        setResult(200, intent);
        finish();
    }
}