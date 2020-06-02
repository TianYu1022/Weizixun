package com.tianyu.weizixun.ui.activity;

import android.widget.Button;
import android.widget.EditText;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseMvpActivity;
import com.tianyu.weizixun.bean.LoginBean;
import com.tianyu.weizixun.presenter.LoginPresenter;
import com.tianyu.weizixun.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter, LoginView> implements LoginView {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected LoginView initMvpView() {
        return this;
    }

    @Override
    protected LoginPresenter initMvpPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        mPresenter.login(name, pwd);
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        toast("登陆成功");
    }

    @Override
    public void onFail(String error) {
        toast(error);
    }
}