package com.tianyu.weizixun.ui.fragment;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.DailyNewRvAdapter;
import com.tianyu.weizixun.base.BaseMvpFragment;
import com.tianyu.weizixun.bean.DailyNewsBean;
import com.tianyu.weizixun.presenter.DailyNewPresenter;
import com.tianyu.weizixun.view.DailyNewView;

import java.util.ArrayList;

import butterknife.BindView;

public class DailyNewsFragment extends BaseMvpFragment<DailyNewPresenter, DailyNewView> implements DailyNewView {
    @BindView(R.id.rv_daily_new)
    RecyclerView rvDailyNew;
    private ArrayList<DailyNewsBean.StoriesBean> datas;
    private ArrayList<DailyNewsBean.TopStoriesBean> bannerDatas;
    private DailyNewRvAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily_news;
    }

    @Override
    protected void initView() {
        super.initView();
        rvDailyNew.setLayoutManager(new LinearLayoutManager(getActivity()));
        datas = new ArrayList<>();
        bannerDatas = new ArrayList<>();
        adapter = new DailyNewRvAdapter(getContext(),datas,bannerDatas);
        rvDailyNew.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getData();
    }

    @Override
    protected DailyNewView initMvpView() {
        return this;
    }

    @Override
    protected DailyNewPresenter initMvpPresenter() {
        return new DailyNewPresenter();
    }

    @Override
    public void onSuccess(DailyNewsBean dailyNewsBean) {
        datas.addAll(dailyNewsBean.getStories());
        bannerDatas.addAll(dailyNewsBean.getTop_stories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}