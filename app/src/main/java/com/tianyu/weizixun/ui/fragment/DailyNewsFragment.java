package com.tianyu.weizixun.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.DailyNewRvAdapter;
import com.tianyu.weizixun.base.BaseMvpFragment;
import com.tianyu.weizixun.bean.DailyNewsBean;
import com.tianyu.weizixun.presenter.DailyNewPresenter;
import com.tianyu.weizixun.ui.activity.CalendarActivity;
import com.tianyu.weizixun.ui.activity.DailyNewsDetailsActivity;
import com.tianyu.weizixun.view.DailyNewView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyNewsFragment extends BaseMvpFragment<DailyNewPresenter, DailyNewView> implements DailyNewView {
    @BindView(R.id.rv_daily_new)
    RecyclerView rvDailyNew;
    @BindView(R.id.fbtn_calendar)
    FloatingActionButton fbtnCalendar;
    private ArrayList<DailyNewsBean.StoriesBean> datas;
    private ArrayList<DailyNewsBean.TopStoriesBean> bannerDatas;
    private DailyNewRvAdapter adapter;
    private ArrayList<String> title;
    private String date;

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
        title = new ArrayList<>();
        title.add("今日新闻");
        adapter = new DailyNewRvAdapter(getContext(), datas, bannerDatas, title);
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
        if (dailyNewsBean.getTop_stories() != null) {
            datas.addAll(dailyNewsBean.getStories());
            bannerDatas.addAll(dailyNewsBean.getTop_stories());
            adapter.notifyDataSetChanged();
        } else {
            datas.clear();
            title.clear();
            title.add(date);
            datas.addAll(dailyNewsBean.getStories());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fbtn_calendar)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == 200) {
                    date = data.getStringExtra("date");
                    mPresenter.getBeforeData(date);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener(new DailyNewRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DailyNewsDetailsActivity.class);
                intent.putExtra("url",datas.get(position).getUrl());
                intent.putExtra("img",datas.get(position).getImages().get(0));
                intent.putExtra("title",datas.get(position).getTitle());
                startActivity(intent);
            }
        });
    }
}