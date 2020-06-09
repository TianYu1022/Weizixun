package com.tianyu.weizixun.presenter;

import android.text.TextUtils;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.RegisterBean;
import com.tianyu.weizixun.callback.RegisterCallback;
import com.tianyu.weizixun.model.RegisterModel;

public class RegisterPresenter extends BasePresenter implements RegisterCallback {

    private RegisterModel mModel;

    public void register(String userid, String psd, String pwd) {
        if (TextUtils.isEmpty(userid)) {
            mView.onFail("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(psd)) {
            mView.onFail("密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            mView.onFail("请确认密码");
            return;
        }

        if (!psd.equals(pwd)) {
            mView.onFail("两次密码不同");
            return;
        }

        if (pwd.length() < 6) {
            mView.onFail("密码长度必须大于6位");
            return;
        }
        mModel.register(userid, psd, pwd, this);
    }

    @Override
    protected void initModel() {
        mModel = new RegisterModel();
        addModel(mModel);
    }

    @Override
    public void onSuccess(RegisterBean registerBean) {
        mView.onSuccess(registerBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
