package com.tianyu.weizixun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseActivity;
import com.tianyu.weizixun.ui.fragment.ContactsFragment;
import com.tianyu.weizixun.ui.fragment.ConversationFragment;
import com.tianyu.weizixun.ui.fragment.DiscoveryFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.cl_main)
    ConstraintLayout clMain;
    @BindView(R.id.nv_main)
    NavigationView nvMain;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    private FragmentManager fragmentManager;
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;
    private int hideFragmentPosition;

    @Override
    protected void initView() {
        super.initView();
        //设置toolbar
        toolbarMain.setLogo(R.drawable.ic_icon);
        toolbarMain.setTitle(getResources().getString(R.string.conversation));
        toolbarMain.setTitleTextColor(Color.WHITE);
        //设置支持toolbar
        setSupportActionBar(toolbarMain);
        //侧滑icon能够正常显示
        nvMain.setItemIconTintList(null);
        //设置横线
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dlMain, toolbarMain, R.string.app_name, R.string.app_name);
        dlMain.addDrawerListener(toggle);
        toggle.syncState();

        //获取fragment管理器
        fragmentManager = getSupportFragmentManager();
        //获取权限
        initPermissions();
        //获取tab标题
        initTabTitles();
        //获取tab
        initTabs();
        //获取fragment
        initFragment();
        //设置显示fragment
        showDefaultFragment();
    }

    private void showDefaultFragment() {
        switchFragment(0);
    }

    private void switchFragment(int showFragmntPosition) {
        //开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //获取fragment
        Fragment showFragment = fragments.get(showFragmntPosition);//当前显示fragment
        Fragment hideFragment = fragments.get(hideFragmentPosition);//隐藏fragment

        //判断是否添加
        if (!showFragment.isAdded()) {
            transaction.add(R.id.fl_container, showFragment);
        }

        //隐藏显示fragment
        transaction.hide(hideFragment).show(showFragment);
        transaction.commit();

        hideFragmentPosition = showFragmntPosition;
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ConversationFragment());//会话
        fragments.add(new ContactsFragment());//联系人
        fragments.add(new DiscoveryFragment());//发现
    }

    private void initTabs() {
        for (int i = 0; i < titles.size(); i++) {
            tabMain.addTab(tabMain.newTab().setText(titles.get(i)));
        }
    }

    private void initTabTitles() {
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.conversation));
        titles.add(getResources().getString(R.string.contacts));
        titles.add(getResources().getString(R.string.discovery));
    }

    private void initPermissions() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换fragment
                switchFragment(tab.getPosition());
                //切换toolbar标题对应tab
                toolbarMain.setTitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //侧滑菜单划出
        dlMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                clMain.setX(nvMain.getRight());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //头部点击事件
        View headerView = nvMain.getHeaderView(0);
        ImageView iv_dl_header = headerView.findViewById(R.id.iv_dl_header);
        TextView tv_dl_header = headerView.findViewById(R.id.tv_dl_header);
        iv_dl_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("点击了头部");
            }
        });

        tv_dl_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //侧滑点击事件
        nvMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_zhihu:

                        break;
                    case R.id.item_wechat:

                        break;
                    case R.id.item_logout:
                        logout();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 退出登录
     */
    private void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "登出成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "登出失败：" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}