package com.tianyu.weizixun.ui.activity;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseActivity;

import butterknife.BindView;

public class SmartActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart;
    }

    @Override
    protected void initView() {
        super.initView();
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).into(iv);
    }
}