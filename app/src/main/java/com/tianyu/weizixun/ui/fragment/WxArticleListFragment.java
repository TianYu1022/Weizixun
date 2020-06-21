package com.tianyu.weizixun.ui.fragment;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.WxArticleListAdapter;
import com.tianyu.weizixun.base.BaseMvpFragment;
import com.tianyu.weizixun.bean.WxArticleBean;
import com.tianyu.weizixun.presenter.WxArticleLisPresenter;
import com.tianyu.weizixun.view.WxArticleListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WxArticleListFragment extends BaseMvpFragment<WxArticleLisPresenter, WxArticleListView> implements WxArticleListView {

    @BindView(R.id.rv_wx_article)
    RecyclerView rvWxArticle;
    private int id;
    private int page = 0;
    private ArrayList<WxArticleBean.DataBean.DatasBean> list;
    private WxArticleListAdapter adapter;

    public WxArticleListFragment(int id) {
        this.id = id;
    }

    public WxArticleListFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article_list;
    }

    @Override
    protected WxArticleListView initMvpView() {
        return this;
    }

    @Override
    protected WxArticleLisPresenter initMvpPresenter() {
        return new WxArticleLisPresenter();
    }

    @Override
    public void onSuccess(WxArticleBean wxArticleBean) {
        List<WxArticleBean.DataBean.DatasBean> datas = wxArticleBean.getData().getDatas();
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initView() {
        super.initView();
        rvWxArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new WxArticleListAdapter(getActivity(), list);
        rvWxArticle.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getWxArticleLisData(id, page);
    }
}