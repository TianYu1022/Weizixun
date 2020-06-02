package com.tianyu.weizixun.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tianyu.weizixun.common.WzxApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    public Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initMvp();
        initView();
        initData();
        initListener();
    }

    protected void initMvp() {

    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initListener() {

    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public void toast(String toastMsg) {
        Toast.makeText(WzxApplication.getApp(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
