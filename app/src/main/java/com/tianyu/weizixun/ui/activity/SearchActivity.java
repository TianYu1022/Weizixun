package com.tianyu.weizixun.ui.activity;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.SearchAdapter;
import com.tianyu.weizixun.base.BaseMvpActivity;
import com.tianyu.weizixun.bean.SearchBean;
import com.tianyu.weizixun.presenter.SearchPresenter;
import com.tianyu.weizixun.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date：2020/6/21
 * @describe：三方框架实现搜索框搜索
 * @author：TianYu
 */
public class SearchActivity extends BaseMvpActivity<SearchPresenter, SearchView> implements SearchView {

    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.msv)
    MaterialSearchView msv;
    @BindView(R.id.fl_search)
    FrameLayout flSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    private ArrayList<SearchBean.DataBean.DatasBean> list;
    private SearchAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchView initMvpView() {
        return this;
    }

    @Override
    protected SearchPresenter initMvpPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onSuccess(SearchBean searchBean) {
        List<SearchBean.DataBean.DatasBean> datas = searchBean.getData().getDatas();
        if (list.size() > 0) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
        if (datas.size() <= 0) {
            toast("搜索为空");
            return;
        }
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        toast(error);
    }

    @Override
    protected void initView() {
        super.initView();
        toolbarSearch.setTitle("搜索");
        setSupportActionBar(toolbarSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SearchAdapter(this, list);
        rvSearch.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_search_menu, menu);
        MenuItem action_search = menu.findItem(R.id.action_search);
        //将OptionsMenu添加到MaterialSearchView
        msv.setMenuItem(action_search);
        return true;
    }

    /**
     * MaterialSearchView的监听事件
     */
    @Override
    protected void initListener() {
        super.initListener();
        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击搜索按钮提交搜索内容  query
                mPresenter.getSearchData(0, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //搜索框中内容发生改变
                return false;
            }
        });

        msv.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //搜索框展开
            }

            @Override
            public void onSearchViewClosed() {
                //搜索框折叠
            }
        });

        //搜索框是否展开
        //msv.isSearchOpen();
        //折叠搜索框
        //msv.closeSearch();
    }
}