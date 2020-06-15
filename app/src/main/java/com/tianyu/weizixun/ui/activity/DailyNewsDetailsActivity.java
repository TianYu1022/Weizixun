package com.tianyu.weizixun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.tianyu.weizixun.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyNewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.top_img)
    ImageView topImg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_news_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient());
        toolbar.setTitle(getIntent().getStringExtra("title"));
        Glide.with(this).load(getIntent().getStringExtra("img")).into(topImg);
    }
}