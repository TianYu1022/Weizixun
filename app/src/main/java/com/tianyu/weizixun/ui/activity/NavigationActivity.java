package com.tianyu.weizixun.ui.activity;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.NaviAdapter;
import com.tianyu.weizixun.base.BaseMvpActivity;
import com.tianyu.weizixun.bean.NaviBean;
import com.tianyu.weizixun.presenter.NaviPresenter;
import com.tianyu.weizixun.view.NaviView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import qdx.stickyheaderdecoration.NormalDecoration;

/**
 * @date：2020/6/21
 * @describe：粘性头布局，流式布局
 * @author：TianYu
 */
public class NavigationActivity extends BaseMvpActivity<NaviPresenter, NaviView> implements NaviView {

    @BindView(R.id.rv_nv)
    RecyclerView rvNv;
    private ArrayList<NaviBean.DataBean> list;
    private NaviAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected NaviView initMvpView() {
        return this;
    }

    @Override
    protected NaviPresenter initMvpPresenter() {
        return new NaviPresenter();
    }

    @Override
    public void onSuccess(NaviBean naviBean) {
        List<NaviBean.DataBean> data = naviBean.getData();
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        toast(error);
    }

    @Override
    protected void initView() {
        super.initView();
        rvNv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NaviAdapter(this, list);

        //返回头布局的内容
        NormalDecoration normalDecoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int i) {
                return list.get(i).getName();
            }
        };
        //为recycleview添加粘性头布局
        rvNv.addItemDecoration(normalDecoration);
        rvNv.setAdapter(adapter);

        //头布局的点击事件
        normalDecoration.setOnHeaderClickListener(new NormalDecoration.OnHeaderClickListener() {
            @Override
            public void headerClick(int i) {
                Toast.makeText(NavigationActivity.this, normalDecoration.getHeaderName(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getNaviDta();
    }
}