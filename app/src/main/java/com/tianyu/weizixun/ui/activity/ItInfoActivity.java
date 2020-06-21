package com.tianyu.weizixun.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.ITInfoVpAdapter;
import com.tianyu.weizixun.base.BaseMvpActivity;
import com.tianyu.weizixun.bean.ITTabBean;
import com.tianyu.weizixun.bean.ItInfoBean;
import com.tianyu.weizixun.common.Constants;
import com.tianyu.weizixun.presenter.ItInfoPresenter;
import com.tianyu.weizixun.ui.fragment.WxArticleListFragment;
import com.tianyu.weizixun.view.ItInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date：2020/6/21
 * @describe：IT咨询界面：tablayout+fragment复用
 * @author：TianYu
 */
public class ItInfoActivity extends BaseMvpActivity<ItInfoPresenter, ItInfoView> implements ItInfoView {

    @BindView(R.id.tab_it)
    TabLayout tabIt;
    @BindView(R.id.iv_it)
    ImageView ivIt;
    @BindView(R.id.cl_it)
    ConstraintLayout clIt;
    @BindView(R.id.vp_it)
    ViewPager vpIt;
    private ITInfoVpAdapter adapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private ArrayList<ITTabBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_it_info;
    }

    @Override
    protected ItInfoView initMvpView() {
        return this;
    }

    @Override
    protected ItInfoPresenter initMvpPresenter() {
        return new ItInfoPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        fragments = new ArrayList<>();//fragment
        titles = new ArrayList<>();//标题
        list = new ArrayList<>();//自定义bean类，和网络请求来的bean类一样
    }

    @Override
    public void onSuccess(ItInfoBean itInfoBean) {
        List<ItInfoBean.DataBean> data = itInfoBean.getData();
        for (int i = 0; i < data.size(); i++) {
            ItInfoBean.DataBean dataBean = data.get(i);
            //默认都是显示，SwitchCompat 都是打开的 为： true
            ITTabBean itTabBean = new ITTabBean(dataBean.getName(), dataBean.getId(), true);
            list.add(itTabBean);
        }
        refreshUI();
    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        for (int i = 0; i < list.size(); i++) {
            ITTabBean itTabBean = list.get(i);
            boolean show = itTabBean.isShow();
            //如果SwitchCompat是选择了的，才往集合里边添加
            // 实际上实现的效果就是让人以为关了SwitchCompat之后就删除了，其实就没往集合中放，所以不显示
            if (show) {
                fragments.add(new WxArticleListFragment(itTabBean.getId()));
                titles.add(itTabBean.getName());
            }
        }
        adapter = new ITInfoVpAdapter(getSupportFragmentManager(), fragments, titles);
        vpIt.setAdapter(adapter);
        tabIt.setupWithViewPager(vpIt);
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(ItInfoActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getItInfoData();
    }

    @OnClick({R.id.iv_it, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_it:
                Intent intent = new Intent(ItInfoActivity.this, ItInfoTabUpdataActivity.class);
                //把自定义的集合传过去
                intent.putExtra(Constants.LIST, list);
                startActivityForResult(intent, 100);
                break;
            case R.id.iv_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }

    /**
     * 返回之后保存SwitchCompat的状态
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            list = (ArrayList<ITTabBean>) data.getSerializableExtra(Constants.LIST);
            fragments.clear();
            titles.clear();
            //从新刷新UI保证SwitchCompat的状态
            refreshUI();
        }
    }
}