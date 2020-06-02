package com.tianyu.weizixun.presenter;

import android.text.TextUtils;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.LoginBean;
import com.tianyu.weizixun.callback.LoginCallback;
import com.tianyu.weizixun.model.LoginModel;

public class LoginPresenter extends BasePresenter implements LoginCallback {

    private LoginModel mModel;

    public void login(String name, String password) {
        if (TextUtils.isEmpty(name)) {
            mView.onFail("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mView.onFail("密码不能为空");
            return;
        }

        mModel.login(name, password, this);
    }

    @Override
    protected void initModel() {
        mModel = new LoginModel();
        addModel(mModel);
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        mView.onSuccess(loginBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
