package com.tianyu.weizixun.ui.fragment;


import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.HotRvAdapter;
import com.tianyu.weizixun.base.BaseMvpFragment;
import com.tianyu.weizixun.bean.HotNewsBean;
import com.tianyu.weizixun.presenter.HotPresenter;
import com.tianyu.weizixun.view.HotView;

import java.util.ArrayList;

import butterknife.BindView;

public class HotFragment extends BaseMvpFragment<HotPresenter, HotView> implements HotView {
    @BindView(R.id.rv_hotnews)
    RecyclerView rvHotnews;
    private ArrayList<HotNewsBean.RecentBean> datas;
    private HotRvAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initView() {
        super.initView();
        rvHotnews.setLayoutManager(new LinearLayoutManager(getActivity()));
        datas = new ArrayList<>();
        adapter = new HotRvAdapter(getActivity(), datas);
        rvHotnews.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getData();
    }

    @Override
    protected HotView initMvpView() {
        return this;
    }

    @Override
    protected HotPresenter initMvpPresenter() {
        return new HotPresenter();
    }

    @Override
    public void onSuccess(HotNewsBean hotNewsBean) {
        datas.addAll(hotNewsBean.getRecent());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}