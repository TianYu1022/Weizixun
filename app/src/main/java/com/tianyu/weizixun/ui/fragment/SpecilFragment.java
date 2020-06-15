package com.tianyu.weizixun.ui.fragment;


import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.SpecilRvAdapter;
import com.tianyu.weizixun.base.BaseMvpFragment;
import com.tianyu.weizixun.bean.SpecilBean;
import com.tianyu.weizixun.presenter.SpecilPresenter;
import com.tianyu.weizixun.view.SpecilView;

import java.util.ArrayList;

import butterknife.BindView;

public class SpecilFragment extends BaseMvpFragment<SpecilPresenter, SpecilView> implements SpecilView {

    @BindView(R.id.rv_specil)
    RecyclerView rvSpecil;
    private ArrayList<SpecilBean.DataBean> datas;
    private SpecilRvAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_specil;
    }

    @Override
    protected void initView() {
        super.initView();
        rvSpecil.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        datas = new ArrayList<>();
        adapter = new SpecilRvAdapter(getActivity(), datas);
        rvSpecil.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getData();
    }

    @Override
    protected SpecilView initMvpView() {
        return this;
    }

    @Override
    protected SpecilPresenter initMvpPresenter() {
        return new SpecilPresenter();
    }

    @Override
    public void onSuccess(SpecilBean specilBean) {
        datas.addAll(specilBean.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}