package com.tianyu.weizixun.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tianyu.weizixun.common.WzxApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter, V extends BaseView> extends AppCompatActivity {

    private Unbinder bind;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        mPresenter = initMvpPresenter();
        if (mPresenter != null) {
            mPresenter.setmView(initMvpView());
        }
        initView();
        initData();
        initListener();
    }

    protected abstract V initMvpView();

    protected abstract P initMvpPresenter();

    private void initView() {

    }

    private void initData() {

    }

    private void initListener() {

    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        mPresenter.onDestroy();
    }

    public void toast(String toastMsg) {
        Toast.makeText(WzxApplication.getApp(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
