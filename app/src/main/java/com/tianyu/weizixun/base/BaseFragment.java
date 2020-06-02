package com.tianyu.weizixun.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter, V extends BaseView> extends Fragment {

    private Unbinder bind;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, view);
        mPresenter = initMvpPresenter();
        if (mPresenter != null) {
            mPresenter.setmView(initMvpView());
        }
        initView();
        initData();
        initListener();
        return view;
    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initListener() {

    }

    protected abstract V initMvpView();

    protected abstract P initMvpPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    protected abstract int getLayoutId();
}
