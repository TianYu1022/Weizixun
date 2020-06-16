package com.tianyu.weizixun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseMvpActivity;
import com.tianyu.weizixun.bean.DailyNewsDetailsBean;
import com.tianyu.weizixun.common.Constants;
import com.tianyu.weizixun.presenter.DailyNewsDetailsPresenter;
import com.tianyu.weizixun.view.DailyNewsDetailsView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;

/**
 * @date：2020/6/16
 * @describe：知乎详情
 * @author：TianYu
 */
public class DailyNewsDetailsActivity extends BaseMvpActivity<DailyNewsDetailsPresenter, DailyNewsDetailsView> implements DailyNewsDetailsView {

    @BindView(R.id.top_img)
    ImageView topImg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.html_text)
    HtmlTextView htmlText;
    @BindView(R.id.fbtn_button)
    FloatingActionButton fbtnButton;
    @BindView(R.id.clb)
    CollapsingToolbarLayout clb;

    @Override
    protected DailyNewsDetailsView initMvpView() {
        return this;
    }

    @Override
    protected DailyNewsDetailsPresenter initMvpPresenter() {
        return new DailyNewsDetailsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily_news_details;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.ID, 1);
        String title = intent.getStringExtra(Constants.TITLE);
        String imageUrl = intent.getStringExtra(Constants.IMAGE);

        clb.setTitle(title);
        Glide.with(this).load(imageUrl).into(topImg);
        //扩张时的颜色
        clb.setExpandedTitleColor(Color.RED);
        //收缩时的颜色
        clb.setCollapsedTitleTextColor(Color.WHITE);
        mPresenter.getData(id);
    }

    @Override
    protected void initListener() {
        super.initListener();
        /**
         * snackbar  类似于 toast
         */
        fbtnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.LENGTH_SHORT  短时间
                //Snackbar.LENGTH_LONG  长时间
                //Snackbar.LENGTH_INDEFINITE  永久存在
                Snackbar.make(fbtnButton,"我是snackbar",Snackbar.LENGTH_INDEFINITE)
                        .setAction("action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DailyNewsDetailsActivity.this, "点击了snackbar的action", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onSuccess(DailyNewsDetailsBean dailyNewsDetailsBean) {
        String body = dailyNewsDetailsBean.getBody();
        htmlText.setHtml(body, new HtmlHttpImageGetter(htmlText));
    }

    @Override
    public void onFail(String error) {
        toast(error);
    }
}