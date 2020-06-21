package com.tianyu.weizixun.ui.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseFragment;
import com.tianyu.weizixun.ui.activity.ItInfoActivity;
import com.tianyu.weizixun.ui.activity.LoginActivity;
import com.tianyu.weizixun.ui.activity.MainActivity;
import com.tianyu.weizixun.ui.activity.ZhihuActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscoveryFragment extends BaseFragment {
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.iv_it_zixun)
    ImageView ivItZixun;
    @BindView(R.id.iv_right_it)
    ImageView ivRightIt;
    @BindView(R.id.cl_it_zixun)
    ConstraintLayout clItZixun;
    @BindView(R.id.iv_tencent_news)
    ImageView ivTencentNews;
    @BindView(R.id.iv_right_tencent)
    ImageView ivRightTencent;
    @BindView(R.id.cl_tencent)
    ConstraintLayout clTencent;
    @BindView(R.id.iv_zhihu)
    ImageView ivZhihu;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.cl_zhihu)
    ConstraintLayout clZhihu;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @OnClick({R.id.btn_logout, R.id.cl_it_zixun, R.id.cl_tencent, R.id.cl_zhihu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                logout();
                break;
            case R.id.cl_it_zixun:
                startActivity(new Intent(getActivity(), ItInfoActivity.class));
                break;
            case R.id.cl_tencent:
                break;
            case R.id.cl_zhihu:
                Intent intent = new Intent(getActivity(), ZhihuActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "登出成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "登出失败：" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}