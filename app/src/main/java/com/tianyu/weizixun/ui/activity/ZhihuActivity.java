package com.tianyu.weizixun.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.ZhiHuVpAdapter;
import com.tianyu.weizixun.base.BaseActivity;
import com.tianyu.weizixun.ui.fragment.DailyNewsFragment;
import com.tianyu.weizixun.ui.fragment.HotFragment;
import com.tianyu.weizixun.ui.fragment.SpecilFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class ZhihuActivity extends BaseActivity {
    @BindView(R.id.vp_zhihu)
    ViewPager vp_zhihu;
    @BindView(R.id.tab)
    TabLayout tab;
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;
    private ZhiHuVpAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu;
    }

    @Override
    protected void initView() {
        super.initView();
        titles = new ArrayList<>();
        fragments = new ArrayList<>();

        titles.add("日报");
        titles.add("专栏");
        titles.add("热门");

        fragments.add(new DailyNewsFragment());
        fragments.add(new SpecilFragment());
        fragments.add(new HotFragment());

        adapter = new ZhiHuVpAdapter(getSupportFragmentManager(), titles, fragments);
        vp_zhihu.setAdapter(adapter);

        tab.setupWithViewPager(vp_zhihu);
    }
}